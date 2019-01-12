package ru.kulikovman.cubes.model;

import java.util.Random;

public class Calculation {

    private final Random random;

    // Размер экрана
    private int screenWidth;
    private int screenHeight;

    // Размер кубика
    private int size;
    private int halfSize;

    // Центр кнопки настроек
    private int sx, sy;

    // Базовые радиусы
    private int cubeRadius;
    private int settingRadius;

    // Зона возможного расположения кубика
    private RollArea rollArea;

    public Calculation(int screenWidth, int screenHeight, int size, int halfSize, int sx, int sy, int cubeRadius, int settingRadius, RollArea rollArea) {
        this.random = new Random();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.size = size;
        this.halfSize = halfSize;
        this.sx = sx;
        this.sy = sy;
        this.cubeRadius = cubeRadius;
        this.settingRadius = settingRadius;
        this.rollArea = rollArea;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getSize() {
        return size;
    }

    public int getHalfSize() {
        return halfSize;
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
