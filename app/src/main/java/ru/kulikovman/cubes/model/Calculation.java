package ru.kulikovman.cubes.model;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

import ru.kulikovman.cubes.R;

public class Calculation {

    private final Random random;

    // Размеры кубика и тени
    private int cubeHalfSize;
    private int shadowHalfSize;

    // Радиусы кубика и кнопки настроек
    private int cubeInnerRadius;
    private int cubeOuterRadius;
    private int settingRadius;

    // Зона возможного расположения кубика
    private Area rollArea;

    // Зона элементов интерфейса
    private Area settingTotalArea;

    public Calculation(Resources resources) {
        random = new Random();

        // Размеры экрана
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Область элементов интерфейса
        int settingSize = resources.getDimensionPixelSize(R.dimen.button_title_size);
        int settingMarginTop = resources.getDimensionPixelSize(R.dimen.button_title_marginTop);
        int totalMarginTop = resources.getDimensionPixelSize(R.dimen.throw_amount_marginTop);
        settingTotalArea = new Area(screenWidth / 2 - settingSize / 2, screenWidth / 2 + settingSize / 2, settingMarginTop, totalMarginTop + settingSize);

        // Радиус кнопки настроек
        settingRadius = settingSize / 2 + settingMarginTop;

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
        rollArea = new Area(shadowRadius, screenWidth - shadowRadius, shadowRadius, screenHeight - shadowRadius);

        // Контроль полученных размеров
        Log.d("myLog", "----------------Screen----------------");
        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "----------------Sizes-----------------");
        Log.d("myLog", "cubeSize = " + cubeSize + " / 2 = " + cubeHalfSize);
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

    public int getSettingRadius() {
        return settingRadius;
    }

    public int getCubeHalfSize() {
        return cubeHalfSize;
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

    public Area getRollArea() {
        return rollArea;
    }

    public Area getSettingTotalArea() {
        return settingTotalArea;
    }
}
