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


public class CubesOnBoardFragment extends Fragment {

    private FragmentCubesOnBoardBinding binding;
    private Context context;

    private Calculation calculation;

    private Skin skin;
    private int numberOfCubes;
    private List<Cube> cubes;
    private List<CubeView> cubeViews;


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

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private void preparatoryCalculations() {
        // Размеры экрана
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Координаты кнопки настроек
        int settingSize = (int) getResources().getDimension(R.dimen.button_setting_size);
        int settingPadding = (int) getResources().getDimension(R.dimen.button_setting_padding);
        int settingMarginTop = (int) getResources().getDimension(R.dimen.button_setting_marginTop);
        int sx = screenWidth / 2;
        int sy = settingSize / 2 + settingPadding + settingMarginTop;

        // Размеры вью кубика
        int viewSize = (int) getResources().getDimension(R.dimen.white_cube_view_size);
        int halfViewSize = viewSize / 2;

        // Размер кубика
        int size = (int) Math.sqrt((Math.pow(halfViewSize, 2) + Math.pow(halfViewSize, 2)));
        int halfSize = size / 2;

        // Радиусы кнопки настроек и кубика
        int settingRadius = settingSize / 2 + settingPadding;
        int cubeRadius = halfViewSize;

        // Зона возможного расположения кубика
        RollArea rollArea = new RollArea(cubeRadius, screenWidth - cubeRadius,
                cubeRadius, screenHeight - cubeRadius);

        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "settingSize = " + settingSize);
        Log.d("myLog", "settingPadding = " + settingPadding);
        Log.d("myLog", "viewSize = " + viewSize);
        Log.d("myLog", "halfViewSize = " + halfViewSize);
        Log.d("myLog", "size = " + size);
        Log.d("myLog", "halfSize = " + halfSize);
        Log.d("myLog", "settingRadius = " + settingRadius);
        Log.d("myLog", "cubeRadius = " + cubeRadius);
        Log.d("myLog", "rollArea = " + rollArea.getMinX() + " - " + rollArea.getMaxX() + " | " + rollArea.getMinY() + " - " + rollArea.getMaxY());

        // Сохраняем все полученные размеры
        calculation = new Calculation(screenWidth, screenHeight, viewSize, halfViewSize, size, halfSize, sx, sy, cubeRadius, settingRadius, rollArea);
    }

    public void openSetting(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    public void rollCubes(View view) {
        // Удаляем старые кубики с доски
        binding.topBoard.removeAllViews();
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

            cubeViews.add(cubeView);
        }

        // Размещаем созданные вью на экране
        for (CubeView cubeView : cubeViews) {
            binding.topBoard.addView(cubeView);
        }

        // Воспроизводим звук броска


        // Сохраняем результаты текущего броска в базу


    }
}
