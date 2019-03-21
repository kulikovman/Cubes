package ru.kulikovman.cubes.model;

import android.graphics.Point;

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
    private Point center = new Point(); // центр
    private Point a = new Point(); // верхняя левая
    private Point b = new Point(); // верхняя правая
    private Point c = new Point(); // нижняя правая
    private Point d = new Point(); // нижняя левая

    // Создание кубика с указание координат
    public Cube(Calculation calculation, CubeType cubeType, Point center) {
        this.calculation = calculation;
        this.random = calculation.getRandom();
        this.cubeType = cubeType;
        this.center = center;

        // Количество точек
        value = 1 + random.nextInt(cubeType.getNumberOfSides()); // от 1 до numberOfSides

        // Угол поворота в градусах и радианах
        degrees = 0;

        // Расчет отступов
        calculateMargins();
    }

    // Создание кубика с генерацией случайных координат
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
            center.x = minX + random.nextInt(maxX - minX);
            center.y = minY + random.nextInt(maxY - minY);
        } while (Intersection.withTotalArea(this));

        // Расчет отступов
        calculateMargins();

        // Расчет положения вершин после поворота
        calculatePointLocations();
    }

    private void calculateMargins() {
        marginStart = center.x - calculation.getCubeViewHalfSize();
        marginTop = center.y - calculation.getCubeViewHalfSize();
    }

    private void calculatePointLocations() {
        // Макс./мин. координаты вершин
        int minX = center.x - calculation.getCubeHalfSize();
        int maxX = center.x + calculation.getCubeHalfSize();
        int minY = center.y - calculation.getCubeHalfSize();
        int maxY = center.y + calculation.getCubeHalfSize();

        // Положение вершин до поворота
        a.x = minX;
        a.y = minY;
        b.x = maxX;
        b.y = minY;
        c.x = maxX;
        c.y = maxY;
        d.x = minX;
        d.y = maxY;

        // Расчет положения вершин после поворота
        int tx1 = getXRotation(a.x, a.y);
        int ty1 = getYRotation(a.x, a.y);
        int tx2 = getXRotation(b.x, b.y);
        int ty2 = getYRotation(b.x, b.y);
        int tx3 = getXRotation(c.x, c.y);
        int ty3 = getYRotation(c.x, c.y);
        int tx4 = getXRotation(d.x, d.y);
        int ty4 = getYRotation(d.x, d.y);

        // Сохраняем новые координаты
        a.x = tx1;
        a.y = ty1;
        b.x = tx2;
        b.y = ty2;
        c.x = tx3;
        c.y = ty3;
        d.x = tx4;
        d.y = ty4;
    }

    private int getXRotation(int px, int py) {
        return (int) (center.x + (px - center.x) * Math.cos(radians) - (py - center.y) * Math.sin(radians));
    }

    private int getYRotation(int px, int py) {
        return (int) (center.y + (py - center.y) * Math.cos(radians) + (px - center.x) * Math.sin(radians));
    }

    public CubeLite getCubeLite() {
        return new CubeLite(cubeType.name(), value, degrees, marginStart, marginTop);
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public int getX() {
        return center.x;
    }

    public int getY() {
        return center.y;
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

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Point getC() {
        return c;
    }

    public Point getD() {
        return d;
    }
}
