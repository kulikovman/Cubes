package ru.kulikovman.cubes;

import android.content.Context;
import android.databinding.DataBindingUtil;
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
        preparatoryCalculations();

        // Инициализация списков
        cubes = new ArrayList<>();
        cubeViews = new ArrayList<>();
        shadowViews = new ArrayList<>();

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private void preparatoryCalculations() {
        // Размеры экрана
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Координаты кнопки настроек
        int settingSize = getResources().getDimensionPixelSize(R.dimen.button_setting_size);
        int settingPadding = getResources().getDimensionPixelSize(R.dimen.button_setting_padding);
        int settingMarginTop = getResources().getDimensionPixelSize(R.dimen.button_setting_marginTop);
        int sx = screenWidth / 2;
        int sy = settingSize / 2 + settingPadding + settingMarginTop;

        // Размеры вью кубика
        int cubeViewSize = getResources().getDimensionPixelSize(R.dimen.white_cube_view_size);
        int cubeHalfViewSize = cubeViewSize / 2;

        // Размер кубика
        int cubeSize = (int) Math.sqrt((Math.pow(cubeHalfViewSize, 2) + Math.pow(cubeHalfViewSize, 2)));
        int cubeHalfSize = cubeSize / 2;

        // Размер тени кубика
        int shadowViewSize = getResources().getDimensionPixelSize(R.dimen.white_shadow_view_size);
        int shadowHalfViewSize = shadowViewSize / 2;

        // Радиусы кнопки настроек и кубика
        int settingRadius = settingSize / 2 + settingPadding;
        int cubeRadius = cubeHalfViewSize;
        int shadowRadius = (int) Math.sqrt((Math.pow(shadowHalfViewSize, 2) + Math.pow(shadowHalfViewSize, 2)));

        // Зона возможного расположения кубика
        RollArea rollArea = new RollArea(shadowRadius, screenWidth - shadowRadius,
                shadowRadius, screenHeight - shadowRadius);

        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "settingSize = " + settingSize);
        Log.d("myLog", "settingPadding = " + settingPadding);
        Log.d("myLog", "cubeViewSize = " + cubeViewSize);
        Log.d("myLog", "cubeHalfViewSize = " + cubeHalfViewSize);
        Log.d("myLog", "cubeSize = " + cubeSize);
        Log.d("myLog", "cubeHalfSize = " + cubeHalfSize);
        Log.d("myLog", "shadowViewSize = " + shadowViewSize);
        Log.d("myLog", "shadowHalfViewSize = " + shadowHalfViewSize);
        Log.d("myLog", "settingRadius = " + settingRadius);
        Log.d("myLog", "cubeRadius = " + cubeRadius);
        Log.d("myLog", "rollArea = " + rollArea.getMinX() + " - " + rollArea.getMaxX() + " | " + rollArea.getMinY() + " - " + rollArea.getMaxY());

        // Сохраняем все полученные размеры
        calculation = new Calculation(screenWidth, screenHeight, cubeViewSize, cubeHalfViewSize, cubeSize, cubeHalfSize,
                shadowViewSize, shadowHalfViewSize, sx, sy, cubeRadius, settingRadius, shadowRadius, rollArea);
    }

    public void openSetting(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    public void rollCubes(View view) {
        // Удаляем старые кубики с доски
        binding.topBoard.removeAllViews();
        binding.bottomBoard.removeAllViews();
        shadowViews.clear();
        cubeViews.clear();
        cubes.clear();

        // Генирируем новые кубики
        while (cubeViews.size() < numberOfCubes) {
            // Создаем кубик
            Cube cube = new Cube(calculation, skin);

            /*if (cubes.isEmpty()) {
                cubes.add(cube);
            } else {
                // Проверяем пересечение с другими кубиками
                boolean intersection = true;
                while (intersection) {
                    for (Cube c : cubes) {

                    }
                }
            } */

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


        // Сохраняем результаты текущего броска в базу


    }
}
