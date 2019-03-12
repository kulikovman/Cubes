package ru.kulikovman.cubes.model;

import java.util.Random;

import ru.kulikovman.cubes.data.CubeType;
import ru.kulikovman.cubes.helper.Intersection;

public class Cube {

    private final Random random;
    private Calculation calculation;

    // Параметры для вью
    private CubeType cubeType;
    private int value;
    private int marginStart;
    private int marginTop;

    // Угол поворота
    private int degrees;
    private double radians;

    // Точки прямоугольника
    private int x, y; // центр
    private int x1, y1; // верхняя левая
    private int x2, y2; // верхняя правая
    private int x3, y3; // нижняя правая
    private int x4, y4; // нижняя левая

    public Cube(Calculation calculation, CubeType cubeType) {
        this.calculation = calculation;
        this.random = calculation.getRandom();
        this.cubeType = cubeType;

        // Количество точек
        value = 1 + random.nextInt(cubeType.getNumberOfSides()); // от 1 до numberOfSides

        // Угол поворота в градусах и радианах
        degrees = random.nextInt(360); // от 0 до 359
        radians = degrees * Math.PI / 180;

        // Расположение кубика на экране
        setNewCubePosition();
    }

    public void moveCube() {
        setNewCubePosition();
    }

    private void setNewCubePosition() {
        int minX = calculation.getRollArea().getMinX();
        int maxX = calculation.getRollArea().getMaxX();
        int minY = calculation.getRollArea().getMinY();
        int maxY = calculation.getRollArea().getMaxY();

        // Координаты кубика
        do {
            x = minX + random.nextInt(maxX - minX);
            y = minY + random.nextInt(maxY - minY);
        } while (Intersection.withTotalArea(this));

        // Расчет отступов
        calculateMargins();

        // Расет положения вершин после поворота
        calculatePointLocations();
    }

    private void calculateMargins() {
        marginStart = x - calculation.getCubeViewHalfSize();
        marginTop = y - calculation.getCubeViewHalfSize();
    }

    private void calculatePointLocations() {
        // Макс./мин. координаты вершин
        int minX = x - calculation.getCubeHalfSize();
        int maxX = x + calculation.getCubeHalfSize();
        int minY = y - calculation.getCubeHalfSize();
        int maxY = y + calculation.getCubeHalfSize();

        // Положение вершин до поворота
        x1 = minX;
        y1 = minY;
        x2 = maxX;
        y2 = minY;
        x3 = maxX;
        y3 = maxY;
        x4 = minX;
        y4 = maxY;

        // Расчет положения вершин после поворота
        int tx1 = getXRotation(x1, y1);
        int ty1 = getYRotation(x1, y1);
        int tx2 = getXRotation(x2, y2);
        int ty2 = getYRotation(x2, y2);
        int tx3 = getXRotation(x3, y3);
        int ty3 = getYRotation(x3, y3);
        int tx4 = getXRotation(x4, y4);
        int ty4 = getYRotation(x4, y4);

        // Сохраняем новые координаты
        x1 = tx1;
        y1 = ty1;
        x2 = tx2;
        y2 = ty2;
        x3 = tx3;
        y3 = ty3;
        x4 = tx4;
        y4 = ty4;
    }

    private int getXRotation(int px, int py) {
        return (int) (x + (px - x) * Math.cos(radians) - (py - y) * Math.sin(radians));
    }

    private int getYRotation(int px, int py) {
        return (int) (y + (py - y) * Math.cos(radians) + (px - x) * Math.sin(radians));
    }

    public CubeLite getCubeLite() {
        return new CubeLite(cubeType.name(), value, degrees, marginStart, marginTop);
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CubeType getCubeType() {
        return cubeType;
    }

    public int getValue() {
        return value;
    }

    public int getMarginStart() {
        return marginStart;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public int getDegrees() {
        return degrees;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getX3() {
        return x3;
    }

    public int getY3() {
        return y3;
    }

    public int getX4() {
        return x4;
    }

    public int getY4() {
        return y4;
    }
}
