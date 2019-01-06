package ru.kulikovman.cubes;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.fragment.NavHostFragment;
import ru.kulikovman.cubes.databinding.FragmentCubesOnBoardBinding;
import ru.kulikovman.cubes.model.Cube;


public class CubesOnBoardFragment extends Fragment {

    private FragmentCubesOnBoardBinding binding;

    private Context context;

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
        // Хардкод - количество кубиков
        numberOfCubes = 4;


        // Обновление переменной в макете
        binding.setModel(this);
    }

    public void openSetting(View view){
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    public void rollCubes(View view){
        // Удаляем старые элементы с экрана


        // Генирируем новые кубики


        List<View> viewList = new ArrayList<>();
        viewList.add(binding.buttonSetting);

        while (viewList.size() < numberOfCubes + 1) {
            Cube cube = new Cube();



        }


    }
}
