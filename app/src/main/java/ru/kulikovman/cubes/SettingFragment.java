package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import ru.kulikovman.cubes.dialog.HelpDialog;
import ru.kulikovman.cubes.helper.sweet.SweetOnSeekBarChangeListener;
import ru.kulikovman.cubes.model.Settings;
import ru.kulikovman.cubes.view.CubeView;


public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    private CubesViewModel model;
    private Settings settings;

    private List<CubeView> cubeViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Получение вью модел
        model = ViewModelProviders.of(getActivity()).get(CubesViewModel.class);
        settings = model.getSettings();

        // Подключение звука
        SoundManager.initialize();

        initCubeList();
        initUI();
        restoreSettings();

        // Обновление переменной в макете
        binding.setModel(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Сохранение настроек в базу
        model.saveSettings(settings);
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
        binding.delay.setProgress(settings.getDelayAfterThrow());
        binding.keepScreenOn.setChecked(settings.isKeepScreenOn());
        binding.showThrowAmount.setChecked(settings.isShownThrowAmount());
        binding.divideScreen.setChecked(settings.isDivideScreen());

        // Отмечаем сохраненный кубик
        String color = settings.getCubeType();
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
                // Воспроизводим соответствующий звук
                SoundManager.get().playSound(SoundManager.SEEKBAR_CLICK_SOUND);

                // Сохраняем состояние
                settings.setNumberOfCubes(progress + 1);
            }
        });

        // Слушатель выбора задержки
        binding.delay.setOnSeekBarChangeListener(new SweetOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Воспроизводим соответствующий звук
                SoundManager.get().playSound(SoundManager.SEEKBAR_CLICK_SOUND);

                // Сохраняем состояние
                settings.setDelayAfterThrow(progress);
            }
        });

        // Переключатель блокировки спящего режима
        binding.keepScreenOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Воспроизводим соответствующий звук
                SoundManager.get().playSound(SoundManager.SWITCH_CLICK_SOUND);

                // Сохраняем состояние
                settings.setKeepScreenOn(isChecked);
            }
        });

        // Переключатель отображения суммы броска
        binding.showThrowAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Воспроизводим соответствующий звук
                SoundManager.get().playSound(SoundManager.SWITCH_CLICK_SOUND);

                // Сохраняем состояние
                settings.setShownThrowAmount(isChecked);
            }
        });

        // Переключатель деления экрана
        binding.divideScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Воспроизводим соответствующий звук
                SoundManager.get().playSound(SoundManager.SWITCH_CLICK_SOUND);

                // Сохраняем состояние
                settings.setDivideScreen(isChecked);
            }
        });
    }

    public void clickComeBackButton() {
        SoundManager.get().playSound(SoundManager.TOP_BUTTON_CLICK_SOUND);

        NavHostFragment.findNavController(this).popBackStack();
    }

    public void clickChooseCube(View view) {
        // Сначала снимаем все галки
        for (CubeView cubeView : cubeViews) {
            cubeView.setChooseMarker(false);
        }

        // Потом ставим галку на выбранном кубике
        CubeView cubeView = (CubeView) view;
        cubeView.setChooseMarker(true);

        // Воспроизводим соответствующий звук
        SoundManager.get().playSound(SoundManager.CUBE_CLICK_SOUND);

        // Сохраняем выбранный цвет
        settings.setCubeType(cubeView.getCubeColor());
    }

    public void clickHelpButton(View view) {
        Bundle args = new Bundle();
        DialogFragment helpMessageDialog = new HelpDialog();

        // Выбираем сообщение
        switch (view.getId()) {
            case R.id.help_number_of_cubes:
                args.putString(HelpDialog.KEY_MESSAGE, getString(R.string.help_number_of_cubes));
                break;
            case R.id.help_delay_after_throw:
                args.putString(HelpDialog.KEY_MESSAGE, getString(R.string.help_delay_after_roll));
                break;
            case R.id.help_keep_screen_on:
                args.putString(HelpDialog.KEY_MESSAGE, getString(R.string.help_keep_screen_on));
                break;
            case R.id.help_show_throw_amount:
                args.putString(HelpDialog.KEY_MESSAGE, getString(R.string.help_show_throw_amount));
                break;
            case R.id.help_divide_screen:
                args.putString(HelpDialog.KEY_MESSAGE, getString(R.string.help_divide_screen));
                break;
            case R.id.help_choose_cube:
                args.putString(HelpDialog.KEY_MESSAGE, getString(R.string.help_choose_cube));
                break;
        }

        // Показываем диалог
        helpMessageDialog.setArguments(args);
        helpMessageDialog.show(getActivity().getSupportFragmentManager(), "helpMessageDialog");
    }
}
