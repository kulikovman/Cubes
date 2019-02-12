package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.fragment.NavHostFragment;
import ru.kulikovman.cubes.databinding.FragmentSettingBinding;
import ru.kulikovman.cubes.model.Settings;
import ru.kulikovman.cubes.sweet.SweetOnSeekBarChangeListener;
import ru.kulikovman.cubes.view.CubeView;


public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    private Context context;

    private Settings settings;
    private List<CubeView> cubeViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Получение вью модел
        CubesViewModel model = ViewModelProviders.of(getActivity()).get(CubesViewModel.class);
        settings = model.getSettings();

        // Подключение звука
        SoundManager.initialize(context);

        initCubeList();
        restoreSettings();
        initUI();

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private void initCubeList() {
        cubeViews = new ArrayList<>();
        cubeViews.add(binding.whiteCube);
        cubeViews.add(binding.redCube);
        cubeViews.add(binding.blackCube);
    }

    private void restoreSettings() {
        // Востанавливаем состояние элементов на экране
        binding.cubes.setProgress(settings.getNumberOfCubes() - 1);
        binding.delay.setProgress(settings.getDelayAfterRoll());
        binding.blockScreen.setChecked(settings.isBlockSleepingMode());

        // Отмечаем сохраненный кубик
        String color = settings.getCubeColor();
        for (CubeView cubeView : cubeViews) {
            if (cubeView.getCubeColor().equals(color)) {
                cubeView.setChooseMarker(true);
                break;
            }
        }
    }

    private void initUI() {
        // Слушатель выбора количества кубиков
        binding.cubes.setOnSeekBarChangeListener(new SweetOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Сохраняем состояние
                settings.setNumberOfCubes(progress + 1);
            }
        });

        // Слушатель выбора задержки
        binding.delay.setOnSeekBarChangeListener(new SweetOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Сохраняем состояние
                settings.setDelayAfterRoll(progress);
            }
        });

        // Слушатель переключателя блокировки экрана
        binding.blockScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Сохраняем состояние
                settings.setBlockSleepingMode(isChecked);
            }
        });
    }

    public void comeBack(View view) {
        SoundManager.getInstance().playSettingButtonSound();

        NavHostFragment.findNavController(this).popBackStack();
    }

    public void chooseCube(View view) {
        // Сначала снимаем все галки
        for (CubeView cubeView : cubeViews) {
            cubeView.setChooseMarker(false);
        }

        // Потом ставим галку на выбранном кубике
        CubeView cubeView = (CubeView) view;
        cubeView.setChooseMarker(true);

        // Сохраняем выбранный цвет
        settings.setCubeColor(cubeView.getCubeColor());
    }
}
