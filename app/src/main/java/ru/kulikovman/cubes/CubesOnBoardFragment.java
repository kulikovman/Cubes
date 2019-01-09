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
import ru.kulikovman.cubes.model.Point;
import ru.kulikovman.cubes.model.Rectangle;
import ru.kulikovman.cubes.model.RollArea;
import ru.kulikovman.cubes.view.CubeView;


public class CubesOnBoardFragment extends Fragment {

    private FragmentCubesOnBoardBinding binding;

    private Context context;

    private int numberOfCubes;
    private RollArea rollArea;
    private Skin skin;
    private int screenWidth;
    private int screenHeight;

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

        // Получение размеров экрана
        initScreenSizes();

        putSettingButtonOnField();

        // Зона возможного расположения кубика
        rollArea = getRollArea();


        // Обновление переменной в макете
        binding.setModel(this);
    }

    private void putSettingButtonOnField() {
        int settingSize = (int) getResources().getDimension(R.dimen.button_setting_size);
        int settingPadding = (int) getResources().getDimension(R.dimen.button_setting_padding);
        int settingMarginTop = (int) getResources().getDimension(R.dimen.button_setting_marginTop);

        int x = screenWidth / 2;
        int y = settingSize / 2 + settingPadding + settingMarginTop;

        // Создаем объект описывающий кнопку настроек
        Rectangle rectangle = new Rectangle(new Point(x, y), settingSize, 0);






        // Поворот точки вокруг другой точки
        // x0/y0 - точка вокруг которой происходит вращение
        /*int x1 = x0 + (x - x0) * cos(45) - (y - y0) * sin(45);
        int y1 = y0 + (y - y0) * cos(45) + (x - x0) * sin(45);

        // 500/500 - точка вокруг которой происходит вращение
        double xt = 500 + (x1 - 500.00) * cos(0.05) + (500.00 - y1) * sin(0.05);
        double yt = 500 + (x1 - 500.00) * sin(0.05) + (y1 - 500.00) * cos(0.05);
        x1 = xt;
        y1 = yt;*/


    }

    /*private boolean isIntersection(List<Dice> coordinates) {
        // Расстояние между центрами кубиков
        int dist = convertDpToPx(110);

        // Проверка пересечений
        for (Dice d1 : coordinates) {
            for (Dice d2 : coordinates) {
                if (!d1.equals(d2)) {
                    int dX = Math.abs(d1.getX() - d2.getX());
                    int dY = Math.abs(d1.getY() - d2.getY());
                    if (dX < dist && dY < dist) {
                        return true;
                    }
                }
            }
        }
        return false;
    }*/

    public void openSetting(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    private void initScreenSizes() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    private RollArea getRollArea() {
        int screenOffset = (int) getResources().getDimension(R.dimen.indent_from_screen_edge);
        int cubeSize = (int) getResources().getDimension(R.dimen.shadow_size);

        return new RollArea(screenOffset, screenWidth - screenOffset - cubeSize, screenOffset, screenHeight - screenOffset - cubeSize);
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
