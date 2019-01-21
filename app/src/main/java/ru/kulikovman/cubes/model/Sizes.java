package ru.kulikovman.cubes.model;

public class Sizes {

    // Размеры
    private int cubeHalfSize;
    private int cubeViewHalfSize;
    private int shadowViewHalfSize;

    // Радиусы
    private int cubeInnerRadius;
    private int cubeOuterRadius;

    // Зона возможного расположения кубика
    private RollArea rollArea;

    public Sizes(int screenWidth, int screenHeight, int shadowRadius, int cubeHalfSize, int cubeViewHalfSize, int shadowViewHalfSize, int cubeInnerRadius, int cubeOuterRadius) {
        this.cubeHalfSize = cubeHalfSize;
        this.cubeViewHalfSize = cubeViewHalfSize;
        this.shadowViewHalfSize = shadowViewHalfSize;
        this.cubeInnerRadius = cubeInnerRadius;
        this.cubeOuterRadius = cubeOuterRadius;

        rollArea = new RollArea(shadowRadius, screenWidth - shadowRadius, shadowRadius, screenHeight - shadowRadius);
    }

    public RollArea getRollArea() {
        return rollArea;
    }

    public int getCubeHalfSize() {
        return cubeHalfSize;
    }

    public int getCubeViewHalfSize() {
        return cubeViewHalfSize;
    }

    public int getShadowViewHalfSize() {
        return shadowViewHalfSize;
    }

    public int getCubeInnerRadius() {
        return cubeInnerRadius;
    }

    public int getCubeOuterRadius() {
        return cubeOuterRadius;
    }
}
