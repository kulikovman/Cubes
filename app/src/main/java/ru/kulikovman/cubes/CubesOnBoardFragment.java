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
import ru.kulikovman.cubes.model.Cube;
import ru.kulikovman.cubes.model.RollArea;
import ru.kulikovman.cubes.view.CubeView;


public class CubesOnBoardFragment extends Fragment {

    private FragmentCubesOnBoardBinding binding;

    private Context context;

    private int numberOfCubes;
    private RollArea rollArea;
    private Skin skin;

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

        // Зона возможного расположения кубика
        rollArea = getRollArea();


        // Обновление переменной в макете
        binding.setModel(this);
    }

    public void openSetting(View view){
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    private RollArea getRollArea() {
        // Базовые размеры
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        int offset = (int) getResources().getDimension(R.dimen.indent_from_screen_edge);
        int cubeSize = (int) getResources().getDimension(R.dimen.shadow_size);

        return new RollArea(offset, screenWidth - offset - cubeSize, offset, screenHeight - offset - cubeSize);
    }

    public void rollCubes(View view){
        // Удаляем старые кубики с экрана
        binding.board.removeAllViews();

        // Генирируем новые кубики


        List<View> viewList = new ArrayList<>();
        viewList.add(binding.buttonSetting);

        while (viewList.size() < numberOfCubes + 1) {
            //Cube cube = new Cube(skin, rollArea);
            CubeView cubeView = new CubeView(context, skin, rollArea);
            //cubeView.setCube(cube);


            viewList.add(cubeView);
        }

        // Кидаем кубики на экран
        for (View v : viewList) {
            if (v instanceof CubeView) {
                binding.board.addView(v);
            }
        }

        // Воспроизводим звук броска


        // Сохраняем асинхронно текущий бросок в базу


    }
}
