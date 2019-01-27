package ru.kulikovman.cubes;

import android.content.Context;
import android.content.res.Resources;
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
        calculation = new Calculation(getResources());

        // Инициализация списков
        cubes = new ArrayList<>();
        cubeViews = new ArrayList<>();
        shadowViews = new ArrayList<>();

        // Обновление переменной в макете
        binding.setModel(this);
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
        while (cubes.size() < numberOfCubes) {
            // Создаем кубик
            Cube cube = new Cube(calculation, skin);

            if (cubes.isEmpty()) {
                cubes.add(cube);
                Log.d("myLog", "Add cube " + cubes.size() + ": " + cube.getX() + ", " + cube.getY());
            } else {
                // Проверяем пересечение с другими кубиками
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

                    // Если пересечение, то двигаем
                    if (intersection) {
                        cube.moveCube();
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


        // Сохраняем результаты текущего броска в базу

        Log.d("myLog", "---------------------------");
    }
}
