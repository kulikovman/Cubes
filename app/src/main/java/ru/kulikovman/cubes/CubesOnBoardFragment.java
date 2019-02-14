package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.navigation.fragment.NavHostFragment;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.database.AppDatabase;
import ru.kulikovman.cubes.database.SettingsDao;
import ru.kulikovman.cubes.databinding.FragmentCubesOnBoardBinding;
import ru.kulikovman.cubes.dialog.RateDialog;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Cube;
import ru.kulikovman.cubes.model.Settings;
import ru.kulikovman.cubes.view.CubeView;
import ru.kulikovman.cubes.view.ShadowView;


public class CubesOnBoardFragment extends Fragment implements RateDialog.Listener {

    private static final int LIMIT_OF_ROLLS = 500; // Теоретически 500 бросков, это две-три игры

    private FragmentCubesOnBoardBinding binding;
    private Context context;

    private CubesViewModel model;
    private Settings settings;
    private Calculation calculation;

    private Skin skin;
    private int numberOfCubes;
    private List<Cube> cubes;
    private List<CubeView> cubeViews;
    private List<ShadowView> shadowViews;

    private int delayAfterRoll;
    private boolean isReadyForRoll;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;


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
        // Получение вью модел
        model = ViewModelProviders.of(getActivity()).get(CubesViewModel.class);
        settings = model.getSettings();

        // Подключение звука
        SoundManager.initialize(context);

        // Инициализация ShakeDetector
        initShakeDetector();

        // Применение настроек
        applySettings();

        // Показ диалога с оценкой
        showRateDialog();

        // Предварительные расчеты всего, что можно подсчитать заранее
        calculation = new Calculation(getResources());

        // Инициализация списков
        cubes = new ArrayList<>();
        cubeViews = new ArrayList<>();
        shadowViews = new ArrayList<>();

        isReadyForRoll = true;

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private void applySettings() {
        // Количество кубиков и цвет
        numberOfCubes = settings.getNumberOfCubes();
        skin = Skin.valueOf(settings.getCubeColor());

        // Задержка после броска
        int[] delays = getResources().getIntArray(R.array.delay_after_roll);
        delayAfterRoll = delays[settings.getDelayAfterRoll()];
    }

    private void showRateDialog() {
        // Если диалог еще не показывался и было сделано достаточно бросков
        if (!settings.isRated() && settings.getNumberOfRoll() > LIMIT_OF_ROLLS) {
            // Показываем диалог с просьбой оценить приложение
            DialogFragment rateDialog = new RateDialog();
            rateDialog.setCancelable(false);
            rateDialog.show(this.getChildFragmentManager(), "rateDialog");
        }
    }

    @Override
    public void rateButtonPressed() {
        // Отмечаем, что приложение оценено
        settings.setRated(true);
    }

    @Override
    public void cancelButtonPressed() {
        // Сбрасываем счетчик, чтобы показать деалог позже
        settings.setNumberOfRoll(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Отключаем shakeDetector
        sensorManager.unregisterListener(shakeDetector);

        // Сохранение настроек в базу
        model.saveSettings(settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Подключаем shakeDetector
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void initShakeDetector() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                // Действие при встряхивании устройства
                Log.d("log", "Обнаружена тряска - " + count);
                rollCubes(null);
            }
        });
    }

    public void openSetting(View view) {
        SoundManager.getInstance().playSettingButtonSound();
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    public void rollCubes(View view) {
        // Если время задержки не прошло, то выходим
        if (!isReadyForRoll) {
            return;
        }

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
        SoundManager.getInstance().playDropSound();

        // Сохраняем результаты текущего броска


        // Засчитываем бросок
        settings.setNumberOfRoll(settings.getNumberOfRoll() + 1);

        // Задержка после броска
        isReadyForRoll = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isReadyForRoll = true;
            }
        }, delayAfterRoll);

        Log.d("myLog", "---------------------------");
    }

    private void clearLists() {
        shadowViews.clear();
        cubeViews.clear();
        cubes.clear();
    }
}
