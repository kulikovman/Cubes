package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.navigation.fragment.NavHostFragment;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.databinding.FragmentCubesOnBoardBinding;
import ru.kulikovman.cubes.dialog.RateDialog;
import ru.kulikovman.cubes.helper.AnimHelper;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Cube;
import ru.kulikovman.cubes.model.CubeLite;
import ru.kulikovman.cubes.model.Settings;
import ru.kulikovman.cubes.model.ThrowResult;
import ru.kulikovman.cubes.view.CubeView;
import ru.kulikovman.cubes.view.ShadowView;


public class CubesOnBoardFragment extends Fragment implements RateDialog.Listener {

    private static final int LIMIT_OF_THROW = 500; // Теоретически 500 бросков, это две-три игры
    private static final int DISPLAY_TIME = 1800;  // Время показа поля с суммой/временем

    private FragmentCubesOnBoardBinding binding;
    private Context context;

    private CubesViewModel model;
    private DataRepository repository;
    private Settings settings;
    private Calculation calculation;

    private Skin skin;
    private int numberOfCubes;
    private int sumOfCubes;
    private boolean isReadyForThrow;
    private int delayAfterThrow;
    private int throwResultOnScreen;

    private List<Cube> cubes;
    private List<CubeView> cubeViews;
    private List<ShadowView> shadowViews;
    private List<ThrowResult> throwResults;

    private Timer sumInfoTimer;
    private Timer timeInfoTimer;

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

        // Получение репозитория
        repository = DataRepository.get();

        // Инициализация
        cubes = new ArrayList<>();
        cubeViews = new ArrayList<>();
        shadowViews = new ArrayList<>();
        throwResults = new ArrayList<>();
        isReadyForThrow = true;

        // Подключение звука и ShakeDetector
        SoundManager.initialize();
        initShakeDetector();

        // Отрисовываем предыдущий бросок
        showLastThrowResult();

        // Применение настроек
        loadSettings();

        // Долгое нажатие по экрану
        binding.buttonOfThrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hideAllInfo();
                showPreviousThrowResult();
                return true;
            }
        });

        // Показ диалога с оценкой
        showRateDialog();

        // Предварительные расчеты всего, что можно подсчитать заранее
        calculation = new Calculation(getResources());

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private void loadSettings() {
        // Количество кубиков и цвет
        numberOfCubes = settings.getNumberOfCubes();
        skin = Skin.valueOf(settings.getCubeColor());

        // Задержка после броска
        int[] delays = getResources().getIntArray(R.array.delay_after_throw);
        delayAfterThrow = delays[settings.getDelayAfterThrow()];
    }

    private void showRateDialog() {
        // Если диалог еще не показывался и было сделано достаточно бросков
        if (!settings.isRated() && settings.getNumberOfThrow() > LIMIT_OF_THROW) {
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
        settings.setNumberOfThrow(0);
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
                throwCubes();
            }
        });
    }

    public void openSetting() {
        SoundManager.get().playSound(SoundManager.TOP_BUTTON_CLICK_SOUND);
        NavHostFragment.findNavController(this).navigate(R.id.action_cubesOnBoardFragment_to_settingFragment);
    }

    public void showSumInfo() {
        // Скрываем время
        binding.timeInfo.setVisibility(View.INVISIBLE);

        // Показываем сумму кубиков
        binding.sumInfo.setText(String.valueOf(sumOfCubes));
        AnimHelper.appearance(binding.sumInfo);

        // Отменяем старый таймер
        if (sumInfoTimer != null) {
            sumInfoTimer.cancel();
            sumInfoTimer = null;
        }

        // Убираем инфо по таймеру
        sumInfoTimer = new Timer();
        sumInfoTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                binding.sumInfo.setVisibility(View.INVISIBLE);
            }
        }, DISPLAY_TIME);
    }

    public void showTimeInfo() {
        // Скрываем сумму
        binding.sumInfo.setVisibility(View.INVISIBLE);

        // Показываем время
        DateFormat dateFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        String time = dateFormat.format(System.currentTimeMillis());
        binding.timeInfo.setText(time);
        AnimHelper.appearance(binding.timeInfo);

        // Отменяем старый таймер
        if (timeInfoTimer != null) {
            timeInfoTimer.cancel();
            timeInfoTimer = null;
        }

        // Убираем инфо по таймеру
        timeInfoTimer = new Timer();
        timeInfoTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                binding.timeInfo.setVisibility(View.INVISIBLE);
            }
        }, DISPLAY_TIME);
    }

    private void hideAllInfo() {
        // Скрываем поля с информацией
        binding.sumInfo.setVisibility(View.INVISIBLE);
        binding.timeInfo.setVisibility(View.INVISIBLE);
    }

    public void showLastThrowResult() {
        // Номер текущего броска
        throwResultOnScreen = 0;

        // Получаем историю бросков
        throwResults.clear();
        throwResults = repository.getThrowResultList();

        if (throwResults.size() != 0) {
            // Отрисовываем последний бросок
            drawCubeFromHistory(throwResultOnScreen);
        }
    }

    public void showPreviousThrowResult() {
        // Получаем список последних бросков
        if (throwResultOnScreen == 0) {
            throwResults.clear();
            throwResults = repository.getThrowResultList();
        }

        // Номер предыдущего броска
        throwResultOnScreen++;

        // Если в истории есть броски и текущий бросок не последний в списке
        if (throwResults.size() != 0 && throwResultOnScreen < throwResults.size()) {
            // Показываем иконку перемотки
            binding.rewindIcon.setVisibility(View.VISIBLE);

            // Звук перемотки
            SoundManager.get().playSound(SoundManager.TAPE_REWIND_SOUND);

            // Ждем когда проиграется звук перемотки
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Скрываем иконку перемотки
                    binding.rewindIcon.setVisibility(View.INVISIBLE);

                    // Удаляем кубики с доски
                    clearBoards();

                    // Отрисовываем кубики предыдущего броска
                    drawCubeFromHistory(throwResultOnScreen);
                }
            }, 600); // 600 - длительность звука перемотки
        }
    }

    private void drawCubeFromHistory(int rollResultNumber) {
        // Сбрасываем сумму и убираем инфо
        sumOfCubes = 0;
        hideAllInfo();

        // Размещаем кубики на доске + подсчет их суммы
        List<CubeLite> cubeLites = throwResults.get(rollResultNumber).getCubeLites();
        for (CubeLite cubeLite : cubeLites) {
            binding.topBoard.addView(new CubeView(context, cubeLite));
            binding.bottomBoard.addView(new ShadowView(context, cubeLite));
            sumOfCubes += cubeLite.getValue();
        }
    }

    public void throwCubes() {
        // Если время задержки не прошло, то выходим
        if (!isReadyForThrow) {
            return;
        }

        // Подготовка к броску
        throwResultOnScreen = 0;
        sumOfCubes = 0;

        hideAllInfo();
        clearBoards();
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

            // Учитываем значение кубика
            sumOfCubes += cube.getValue();

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
        SoundManager.get().playSound(SoundManager.THROW_CUBES_SOUND);

        // Засчитываем бросок
        settings.setNumberOfThrow(settings.getNumberOfThrow() + 1);

        // Сохраняем результаты текущего броска в базу
        ThrowResult throwResult = new ThrowResult();
        for (CubeView cubeView : cubeViews) {
            throwResult.addCubeLite(cubeView.getCubeLite());
        }

        repository.saveThrowResult(throwResult);

        // Задержка после броска
        isReadyForThrow = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isReadyForThrow = true;
            }
        }, delayAfterThrow);

        Log.d("myLog", "---------------------------");
    }

    private void clearBoards() {
        binding.topBoard.removeAllViews();
        binding.bottomBoard.removeAllViews();
    }

    private void clearLists() {
        shadowViews.clear();
        cubeViews.clear();
        cubes.clear();
    }
}
