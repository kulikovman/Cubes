package ru.kulikovman.cubes.model;

import android.util.Log;

import java.util.Random;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

    // Базовые значения
    private Calculation calculation;
    private final Random random;

    // Параметры для вью
    private Skin skin;
    private int value;

    // Отступы
    private int cubeMarginStart;
    private int cubeMarginTop;
    private int shadowMarginStart;
    private int shadowMarginTop;

    // Центр кубика
    private int x, y;

    // Угол поворота
    private int degrees;
    private double radians;

    // Вершины прямоугольника
    private int x1, y1; // верхняя левая
    private int x2, y2; // верхняя правая
    private int x3, y3; // нижняя правая
    private int x4, y4; // нижняя левая

    public Cube(Calculation calculation, Skin skin) {
        this.calculation = calculation;
        this.random = calculation.getRandom();
        this.skin = skin;

        // Количество точек
        value = 1 + random.nextInt(6); // от 1 до 6

        // Угол поворота в градусах и радианах
        degrees = random.nextInt(360); // от 0 до 359
        radians = degrees * Math.PI / 180;

        // Расположение кубика на экране
        boolean isCorrectStartPosition = false;
        while (!isCorrectStartPosition) {
            setNewCubePosition();
            isCorrectStartPosition = checkStartPosition();
        }
    }

    private boolean checkStartPosition() {
        // Расчет расстояния от центра кнопки настроек до центра кубика
        int distance = (int) Math.sqrt((Math.pow(Math.abs(x - calculation.getSx()), 2) +
                Math.pow(Math.abs(y - calculation.getSy()), 2)));
        Log.d("myLog", "cube to setting distance = " + distance + " | cubeOuterRadius + settingRadius = " +
                calculation.getCubeOuterRadius() + " + " + calculation.getSettingRadius() + " = " +
                calculation.getCubeOuterRadius() + calculation.getSettingRadius());

        return distance > calculation.getCubeOuterRadius() + calculation.getSettingRadius();
    }

    public void moveCube() {
        setNewCubePosition();
    }

    private void setNewCubePosition() {
        int minX = calculation.getRollArea().getMinX();
        int maxX = calculation.getRollArea().getMaxX();
        int minY = calculation.getRollArea().getMinY();
        int maxY = calculation.getRollArea().getMaxY();

        x = minX + random.nextInt(maxX - minX);
        y = minY + random.nextInt(maxY - minY);

        // Расчет отступов
        calculateMargins();

        // Расет положения вершин после поворота
        calculatePointLocations();
    }

    private void calculateMargins() {
        cubeMarginStart = x - calculation.getCubeViewHalfSize();
        cubeMarginTop = y - calculation.getCubeViewHalfSize();

        shadowMarginStart = x - calculation.getShadowHalfSize();
        shadowMarginTop = y - calculation.getShadowHalfSize();
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

    public boolean intersection(Cube cube) {
        // Расчет растояния между центрами кубиков
        int distance = (int) Math.sqrt((Math.pow(Math.abs(x - cube.getX()), 2) + Math.pow(Math.abs(y - cube.getY()), 2)));
        Log.d("myLog", "Distance: " + x + ", " + y + " | " + cube.getX() + ", " + cube.getY() + " ---> " + distance);

        // ЭТАП 1: предварительная упрощенная проверка
        // Если больше суммы внешних радиусов, то все ок
        if (distance > calculation.getCubeOuterRadius() + cube.getCubeOuterRadius()) {
            Log.d("myLog", "Stage 1: " + distance + " > " + calculation.getCubeOuterRadius() + " + " + cube.getCubeOuterRadius());
            return false;
        }

        // ЭТАП 2: проверка минимально допустимого расстояния
        if (distance < calculation.getCubeInnerRadius() + cube.getCubeInnerRadius()) {
            Log.d("myLog", "Stage 2: " + distance + " < " + calculation.getCubeInnerRadius() + " + " + cube.getCubeInnerRadius());
            return true;
        }

        // ЭТАП 3: проверка расположения вершин кубиков
        // Если точки одного кубика находятся за пределами второго и наоборот, то все ок
        if (!pointInsideCube(cube.getX1(), cube.getY1()) && !pointInsideCube(cube.getX2(), cube.getY2()) &&
                !pointInsideCube(cube.getX3(), cube.getY3()) && !pointInsideCube(cube.getX4(), cube.getY4()) &&
                !cube.pointInsideCube(x1, y1) && !cube.pointInsideCube(x2, y2) &&
                !cube.pointInsideCube(x3, y3) && !cube.pointInsideCube(x4, y4)) {
            return false;
        }

        // Одна или несколько точек пересекаются с кубом
        return true;
    }

    public boolean pointInsideCube(int px, int py) {
        // Делим куб на два треугольника и проверяем принадлежность
        // точек одного куба к треугольникам другого

        // Обозначение вершин куба
        // a - b
        // | \ |
        // d - c

        // Треугольник ABC
        int ab = (x1 - px) * (y2 - y1) - (x2 - x1) * (y1 - py);
        int bc = (x2 - px) * (y3 - y2) - (x3 - x2) * (y2 - py);
        int ca = (x3 - px) * (y1 - y3) - (x1 - x3) * (y3 - py);

        if ((ab >= 0 && bc >= 0 && ca >= 0) || (ab <= 0 && bc <= 0 && ca <= 0)) {
            Log.d("myLog", "Точка внутри ABC!");
            return true;
        }

        // Треугольник ACD
        int ac = (x1 - px) * (y3 - y1) - (x3 - x1) * (y1 - py);
        int cd = (x3 - px) * (y4 - y3) - (x4 - x3) * (y3 - py);
        int da = (x4 - px) * (y1 - y4) - (x1 - x4) * (y4 - py);

        if ((ac >= 0 && cd >= 0 && da >= 0) || (ac <= 0 && cd <= 0 && da <= 0)) {
            Log.d("myLog", "Точка внутри ACD!");
            return true;
        }

        // Точка находится вне куба
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Skin getSkin() {
        return skin;
    }

    public int getValue() {
        return value;
    }

    public int getCubeMarginStart() {
        return cubeMarginStart;
    }

    public int getCubeMarginTop() {
        return cubeMarginTop;
    }

    public int getShadowMarginStart() {
        return shadowMarginStart;
    }

    public int getShadowMarginTop() {
        return shadowMarginTop;
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

    public int getCubeInnerRadius() {
        return calculation.getCubeInnerRadius();
    }

    public int getCubeOuterRadius() {
        return calculation.getCubeOuterRadius();
    }
}
