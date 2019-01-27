package ru.kulikovman.cubes.model;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

import ru.kulikovman.cubes.R;

public class Calculation {

    private final Random random;
    private final double BUFFER = 0.02; // 2% от ширины кубика

    // Положение кнопки настроек
    private int sx, sy;

    // Размеры кубика и тени
    private int cubeHalfSize;
    private int cubeViewHalfSize;
    private int shadowHalfSize;

    // Радиусы кубика и кнопки настроек
    private int cubeInnerRadius;
    private int cubeOuterRadius;
    private int settingRadius;

    // Зона возможного расположения кубика
    private RollArea rollArea;

    public Calculation(Resources resources) {
        random = new Random();

        // Размеры экрана
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        // Размер экрана
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Координаты кнопки настроек
        int settingSize = resources.getDimensionPixelSize(R.dimen.button_setting_size);
        int settingPadding = resources.getDimensionPixelSize(R.dimen.button_setting_padding);
        int settingMarginTop = resources.getDimensionPixelSize(R.dimen.button_setting_marginTop);
        sx = screenWidth / 2;
        sy = settingSize / 2 + settingPadding + settingMarginTop;

        // Радиус кнопки настроек
        settingRadius = settingSize / 2 + settingPadding;

        // Размер/полуразмер вью кубика
        int whiteCubeViewSize = resources.getDimensionPixelSize(R.dimen.cube_view_size);
        cubeViewHalfSize = whiteCubeViewSize / 2;

        // Размер/полуразмер кубика с учетом буферного расстояния
        int whiteCubeSize = (int) Math.sqrt((Math.pow(cubeViewHalfSize, 2) + Math.pow(cubeViewHalfSize, 2)));
        whiteCubeSize = (int) (whiteCubeSize + whiteCubeSize * BUFFER);
        cubeHalfSize = whiteCubeSize / 2;

        // Размер/полуразмер вью тени
        int whiteShadowSize = resources.getDimensionPixelSize(R.dimen.shadow_view_size);
        shadowHalfSize = whiteShadowSize / 2;

        // Радиусы кубика и тени
        cubeInnerRadius = whiteCubeSize;
        cubeOuterRadius = cubeViewHalfSize;
        int shadowRadius = (int) Math.sqrt((Math.pow(shadowHalfSize, 2) + Math.pow(shadowHalfSize, 2)));

        // Формирование области расположения кубика
        rollArea = new RollArea(shadowRadius, screenWidth - shadowRadius, shadowRadius, screenHeight - shadowRadius);


        Log.d("myLog", "-------------Calculation--------------");
        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "whiteCubeSize = " + whiteCubeSize);
        Log.d("myLog", "cubeHalfSize = " + cubeHalfSize);
        Log.d("myLog", "cubeViewHalfSize = " + cubeViewHalfSize);
        Log.d("myLog", "shadowHalfSize = " + shadowHalfSize);
        Log.d("myLog", "cubeInnerRadius = " + cubeInnerRadius);
        Log.d("myLog", "cubeOuterRadius = " + cubeOuterRadius);
        Log.d("myLog", "shadowRadius = " + shadowRadius);
        Log.d("myLog", "-------------Calculation--------------");
    }

    public Random getRandom() {
        return random;
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public int getSettingRadius() {
        return settingRadius;
    }

    public int getCubeHalfSize() {
        return cubeHalfSize;
    }

    public int getCubeViewHalfSize() {
        return cubeViewHalfSize;
    }

    public int getShadowHalfSize() {
        return shadowHalfSize;
    }

    public int getCubeInnerRadius() {
        return cubeInnerRadius;
    }

    public int getCubeOuterRadius() {
        return cubeOuterRadius;
    }

    public RollArea getRollArea() {
        return rollArea;
    }
}
