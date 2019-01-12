package ru.kulikovman.cubes;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.fragment.NavHostFragment;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.databinding.FragmentCubesOnBoardBinding;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Point;
import ru.kulikovman.cubes.model.Rectangle;
import ru.kulikovman.cubes.model.RollArea;
import ru.kulikovman.cubes.view.CubeView;


public class CubesOnBoardFragment extends Fragment {

    private FragmentCubesOnBoardBinding binding;
    private Context context;

    private Calculation calculation;
    private RollArea rollArea;

    private Skin skin;
    private int numberOfCubes;


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

        // Предварительные расчеты
        // все что можно подсчитать заранее
        calculation = getCalculation();

        // Зона возможного расположения кубика
        rollArea = getRollArea();

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private Calculation getCalculation() {
        Calculation c = new Calculation();

        // Размеры экрана
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        c.screenWidth = displayMetrics.widthPixels;
        c.screenHeight = displayMetrics.heightPixels;

        // Координаты кнопки настроек
        int settingSize = (int) getResources().getDimension(R.dimen.button_setting_size);
        int settingPadding = (int) getResources().getDimension(R.dimen.button_setting_padding);
        int settingMarginTop = (int) getResources().getDimension(R.dimen.button_setting_marginTop);

        c.sx = c.screenWidth / 2;
        c.sy = settingSize / 2 + settingPadding + settingMarginTop;

        // Размеры кубика
        c.offset = (int) getResources().getDimension(R.dimen.screen_offset);
        c.size = (int) getResources().getDimension(R.dimen.cube_size);
        c.halfSize = c.size / 2;

        // Радиус кнопки настроек
        c.settingRadius = settingSize / 2 + settingPadding;

        // Радиус кубика
        c.cubeRadius = (int) Math.sqrt((Math.pow(c.halfSize, 2) + Math.pow(c.halfSize, 2)));




        return c;
    }

    public void openSetting(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    private RollArea getRollArea() {
        int offset = (int) getResources().getDimension(R.dimen.screen_offset);
        int size = (int) getResources().getDimension(R.dimen.cube_size);
        int halfSize = size / 2;
        return new RollArea(offset + halfSize / 2, screenWidth - offset - halfSize,
                offset + halfSize, screenHeight - offset - halfSize);
    }

    public void rollCubes(View view) {
        // Удаляем старые кубики с экрана
        binding.board.removeAllViews();

        // Генирируем новые кубики
        List<CubeView> cubeViewList = new ArrayList<>();

        while (cubeViewList.size() < numberOfCubes) {
            CubeView cubeView = new CubeView(context, skin, rollArea);

            // Проверка пересечения с другими вью
            // ...

            cubeViewList.add(cubeView);
        }


        // Кидаем кубики на экран
        for (CubeView cubeView : cubeViewList) {
            binding.board.addView(cubeView);
        }


        // Воспроизводим звук броска


        // Сохраняем асинхронно текущий бросок в базу


    }
}
