package ru.kulikovman.cubes;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.fragment.NavHostFragment;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.databinding.FragmentCubesOnBoardBinding;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Cube;
import ru.kulikovman.cubes.model.RollArea;
import ru.kulikovman.cubes.view.CubeView;
import ru.kulikovman.cubes.view.ShadowView;


public class CubesOnBoardFragment extends Fragment {

    private FragmentCubesOnBoardBinding binding;
    private Context context;

    private Calculation calculation;

    private Skin skin;
    private int numberOfCubes;
    private List<Cube> cubes;
    private List<CubeView> cubeViews;
    private List<ShadowView> shadowViews;

    private SoundPool mSoundPool;
    private int mRollDiceSound;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cubes_on_board, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Хардкод (это должно приходить с базы данных - настройки приложения)
        numberOfCubes = 6; // количество кубиков
        skin = Skin.WHITE; // белый

        // Предварительные расчеты всего, что можно подсчитать заранее
        calculation = new Calculation(getResources());

        // Инициализация списков
        cubes = new ArrayList<>();
        cubeViews = new ArrayList<>();
        shadowViews = new ArrayList<>();

        // Инициализация SoundPool
        initSoundPool();

        // Обновление переменной в макете
        binding.setModel(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Очищаем SoundPool
        clearSoundPool();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Создаем SoundPool
        initSoundPool();
    }

    private void initSoundPool() {
        if (mSoundPool == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Создаем SoundPool для Android API 21 и выше
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();

                mSoundPool = new SoundPool.Builder()
                        .setAudioAttributes(attributes)
                        .build();
            } else {
                // Создаем SoundPool для старых версий Android
                mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            }

            // Получаем id звуковых файлов
            mRollDiceSound = mSoundPool.load(context, R.raw.roll_dice, 1);
        }
    }

    private void clearSoundPool() {
        mSoundPool.release();
        mSoundPool = null;
    }

    public void openSetting(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    public void rollCubes(View view) {
        // Удаляем старые кубики с доски
        binding.topBoard.removeAllViews();
        binding.bottomBoard.removeAllViews();
        clearLists();

        // Генирируем новые кубики
        while (cubes.size() < numberOfCubes) {
            // Создаем кубик
            Cube cube = new Cube(calculation, skin);

            if (cubes.isEmpty()) {
                cubes.add(cube);
                Log.d("myLog", "Add cube " + cubes.size() + ": " + cube.getX() + ", " + cube.getY());
            } else {
                // Проверяем пересечение с другими кубиками
                int count = 0;
                boolean intersection = true;
                while (intersection) {
                    for (Cube c : cubes) {
                        if (cube.intersection(c)) {
                            intersection = true;
                            break;
                        } else {
                            intersection = false;
                        }
                    }

                    // Если есть пересечение
                    if (intersection) {
                        count++;
                        Log.d("myLog", "intersection count = " + count);
                        if (count < 30) { // Защита от неудачного разброса кубиков
                            // Двигаем кубик
                            cube.moveCube();
                        } else {
                            Log.d("myLog", "Сработала защита от неудачного разброса!");
                            Log.d("myLog", "---------------------------");
                            // Очищаем списки и начинаем заново
                            clearLists();
                            cubes.add(cube);
                            Log.d("myLog", "Add cube " + cubes.size() + ": " + cube.getX() + ", " + cube.getY());
                            intersection = false;
                        }
                    } else {
                        cubes.add(cube);
                        Log.d("myLog", "Add cube " + cubes.size() + ": " + cube.getX() + ", " + cube.getY());
                    }
                }
            }

            // Создаем вью из кубика
            CubeView cubeView = new CubeView(context, cube);
            ShadowView shadowView = new ShadowView(context, cube);

            cubeViews.add(cubeView);
            shadowViews.add(shadowView);
        }

        // Размещаем созданные вью на экране
        for (CubeView cubeView : cubeViews) {
            binding.topBoard.addView(cubeView);
        }

        for (ShadowView shadowView : shadowViews) {
            binding.bottomBoard.addView(shadowView);
        }

        // Воспроизводим звук броска
        if (mSoundPool == null) {
            initSoundPool();
        }

        mSoundPool.play(mRollDiceSound, 1, 1, 1, 0, 1);

        // Сохраняем результаты текущего броска в базу

        Log.d("myLog", "---------------------------");
    }

    private void clearLists() {
        shadowViews.clear();
        cubeViews.clear();
        cubes.clear();
    }
}
