package ru.kulikovman.cubes.model;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

import ru.kulikovman.cubes.R;

public class Calculation {

    private final Random random;

    private final int screenWidth;
    private final int screenHeight;
    private int titleHeight;

    private int cubeHalfSize;
    private int cubeViewHalfSize;
    private int cubeInnerRadius;
    private int cubeOuterRadius;

    private Area rollArea;
    private Area totalArea;

    private int maxCubesPerWidth;
    private int maxCubesPerHeight;
    private int maxCubes;
    private int spaceBetweenCentersOfCubes;

    public Calculation(Resources resources) {
        random = new Random();

        // Размеры экрана
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Область элементов интерфейса
        int settingSize = resources.getDimensionPixelSize(R.dimen.button_title_size);
        int marginTop = resources.getDimensionPixelSize(R.dimen.margin_20);
        titleHeight = resources.getDimensionPixelSize(R.dimen.title_container_height);
        totalArea = new Area(screenWidth / 2 - settingSize / 2, screenWidth / 2 + settingSize / 2, 0, titleHeight + marginTop);

        // Размер/полуразмер кубика
        int cubeSize = resources.getDimensionPixelSize(R.dimen.cube_size);
        cubeHalfSize = cubeSize / 2;

        // Размер/полуразмер вью кубика
        int cubeViewSize = resources.getDimensionPixelSize(R.dimen.cube_view_size);
        cubeViewHalfSize = cubeViewSize / 2;

        // Радиусы кубика и тени
        cubeInnerRadius = cubeHalfSize;
        cubeOuterRadius = (int) Math.sqrt((Math.pow(cubeHalfSize, 2) + Math.pow(cubeHalfSize, 2)));
        int shadowRadius = (int) Math.sqrt((Math.pow(cubeViewHalfSize, 2) + Math.pow(cubeViewHalfSize, 2)));

        // Формирование области расположения кубика
        rollArea = new Area(shadowRadius, screenWidth - shadowRadius, shadowRadius, screenHeight - shadowRadius);

        // Расстояние между центрами кубиков (при разбросе рядами)
        spaceBetweenCentersOfCubes = cubeSize + cubeSize / 3;

        // Максимальное количество упорядоченных кубиков
        maxCubesPerWidth = (screenWidth - cubeViewSize) / spaceBetweenCentersOfCubes + 1;
        maxCubesPerHeight = (screenHeight - cubeViewSize - titleHeight * 2) / spaceBetweenCentersOfCubes + 1;
        maxCubes = maxCubesPerWidth * maxCubesPerHeight;


        // Контроль полученных размеров
        Log.d("myLog", "----------------Screen----------------");
        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "----------------Sizes-----------------");
        Log.d("myLog", "cubeSize = " + cubeSize + " / 2 = " + cubeHalfSize);
        Log.d("myLog", "cubeViewSize = " + cubeViewSize + " / 2 = " + cubeViewHalfSize);
        Log.d("myLog", "----------------Radius----------------");
        Log.d("myLog", "cubeRadius = " + cubeInnerRadius + " | " + cubeOuterRadius);
        Log.d("myLog", "shadowRadius = " + shadowRadius);
        Log.d("myLog", "------------Number of cubes-----------");
        Log.d("myLog", "maxCubesPer = Width | Height = " + maxCubesPerWidth + " | " + maxCubesPerHeight);
        Log.d("myLog", "maxCubes = " + maxCubes);
        Log.d("myLog", "--------------------------------------");
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getTitleHeight() {
        return titleHeight;
    }

    public Random getRandom() {
        return random;
    }

    public int getCubeHalfSize() {
        return cubeHalfSize;
    }

    public int getCubeViewHalfSize() {
        return cubeViewHalfSize;
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

    public Area getTotalArea() {
        return totalArea;
    }

    public int getMaxCubesPerWidth() {
        return maxCubesPerWidth;
    }

    public int getMaxCubesPerHeight() {
        return maxCubesPerHeight;
    }

    public int getMaxCubes() {
        return maxCubes;
    }

    public int getSpaceBetweenCentersOfCubes() {
        return spaceBetweenCentersOfCubes;
    }
}
