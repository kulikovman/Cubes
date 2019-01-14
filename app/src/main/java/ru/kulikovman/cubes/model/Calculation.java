package ru.kulikovman.cubes.model;

import java.util.Random;

public class Calculation {

    private final Random random;

    // Размер экрана
    private int screenWidth;
    private int screenHeight;

    // Размер вью кубика
    private int cubeViewSize;
    private int cubeHalfViewSize;

    // Размер кубика
    private int cubeSize;
    private int cubeHalfSize;

    // Размер тени
    private int shadowViewSize;
    private int shadowHalfViewSize;

    // Центр кнопки настроек
    private int sx, sy;

    // Базовые радиусы
    private int cubeRadius;
    private int settingRadius;
    private int shadowRadius;

    // Зона возможного расположения кубика
    private RollArea rollArea;

    public Calculation(int screenWidth, int screenHeight, int cubeViewSize, int cubeHalfViewSize, int cubeSize, int cubeHalfSize,
                       int shadowViewSize, int shadowHalfViewSize, int sx, int sy, int cubeRadius, int settingRadius, int shadowRadius, RollArea rollArea) {

        this.random = new Random();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.cubeViewSize = cubeViewSize;
        this.cubeHalfViewSize = cubeHalfViewSize;
        this.cubeSize = cubeSize;
        this.cubeHalfSize = cubeHalfSize;
        this.shadowViewSize = shadowViewSize;
        this.shadowHalfViewSize = shadowHalfViewSize;
        this.sx = sx;
        this.sy = sy;
        this.cubeRadius = cubeRadius;
        this.settingRadius = settingRadius;
        this.shadowRadius = shadowRadius;
        this.rollArea = rollArea;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getCubeViewSize() {
        return cubeViewSize;
    }

    public int getCubeHalfViewSize() {
        return cubeHalfViewSize;
    }

    public int getCubeSize() {
        return cubeSize;
    }

    public int getCubeHalfSize() {
        return cubeHalfSize;
    }

    public int getShadowViewSize() {
        return shadowViewSize;
    }

    public int getShadowHalfViewSize() {
        return shadowHalfViewSize;
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public int getCubeRadius() {
        return cubeRadius;
    }

    public int getSettingRadius() {
        return settingRadius;
    }

    public RollArea getRollArea() {
        return rollArea;
    }

    public Random getRandom() {
        return random;
    }
}
