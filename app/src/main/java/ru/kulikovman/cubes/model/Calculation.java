package ru.kulikovman.cubes.model;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

import ru.kulikovman.cubes.R;

public class Calculation {

    private final Random random;

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
        int settingMarginTop = resources.getDimensionPixelSize(R.dimen.button_setting_marginTop);
        sx = screenWidth / 2;
        sy = settingSize / 2 + settingMarginTop;

        // Радиус кнопки настроек
        settingRadius = settingSize / 2 + settingMarginTop;

        // Размер/полуразмер вью кубика
        int cubeViewSize = resources.getDimensionPixelSize(R.dimen.cube_view_size);
        cubeViewHalfSize = cubeViewSize / 2;

        // Размер/полуразмер кубика
        int cubeSize = resources.getDimensionPixelSize(R.dimen.cube_size);
        cubeHalfSize = cubeSize / 2;

        // Размер/полуразмер вью тени
        int shadowSize = resources.getDimensionPixelSize(R.dimen.shadow_view_size);
        shadowHalfSize = shadowSize / 2;

        // Радиусы кубика и тени
        cubeInnerRadius = cubeHalfSize;
        cubeOuterRadius = (int) Math.sqrt((Math.pow(cubeHalfSize, 2) + Math.pow(cubeHalfSize, 2)));
        int shadowRadius = (int) Math.sqrt((Math.pow(shadowHalfSize, 2) + Math.pow(shadowHalfSize, 2)));

        // Формирование области расположения кубика
        rollArea = new RollArea(shadowRadius, screenWidth - shadowRadius, shadowRadius, screenHeight - shadowRadius);

        // Контроль полученных размеров
        Log.d("myLog", "----------------Screen----------------");
        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "----------------Sizes-----------------");
        Log.d("myLog", "cubeSize = " + cubeSize + " / 2 = " + cubeHalfSize);
        Log.d("myLog", "cubeViewSize = " + cubeViewSize + " / 2 = " + cubeViewHalfSize);
        Log.d("myLog", "shadowSize = " + shadowSize + " / 2 = " + shadowHalfSize);
        Log.d("myLog", "----------------Radius----------------");
        Log.d("myLog", "cubeRadius = " + cubeInnerRadius + " | " + cubeOuterRadius);
        Log.d("myLog", "shadowRadius = " + shadowRadius);
        Log.d("myLog", "settingRadius = " + settingRadius);
        Log.d("myLog", "--------------------------------------");
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
