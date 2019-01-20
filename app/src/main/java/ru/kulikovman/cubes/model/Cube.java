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
        this.skin = skin;
        this.random = calculation.getRandom();

        // Цвет кубика
        if (skin == Skin.RANDOM) {
            int skinIndex = 1 + random.nextInt(Skin.values().length); // случайный цвет
            this.skin = Skin.values()[skinIndex];
        } else {
            this.skin = skin;
        }

        // Количество точек
        value = 1 + random.nextInt(6); // от 1 до 6

        // Угол поворота в градусах и радианах
        degrees = random.nextInt(360); // от 0 до 359
        radians = degrees * Math.PI / 180;

        // Расположение кубика на экране
        boolean isCorrectPosition = false;
        while (!isCorrectPosition) {
            setNewCubePosition();
            isCorrectPosition = checkPosition();
        }

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
        int ty2 = getYRotation(x1, y1);
        int tx3 = getXRotation(x1, y1);
        int ty3 = getYRotation(x1, y1);
        int tx4 = getXRotation(x1, y1);
        int ty4 = getYRotation(x1, y1);

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

    private boolean checkPosition() {
        // Расчет расстояния от центра кнопки настроек до центра кубика
        int distance = (int) Math.sqrt((Math.pow(Math.abs(x - calculation.getSx()), 2) +
                Math.pow(Math.abs(y - calculation.getSy()), 2)));

        // Должно быть больше, чем сумма радиусов
        return distance > calculation.getCubeRadius() + calculation.getSettingRadius();
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
    }

    private void calculateMargins() {
        cubeMarginStart = x - calculation.getCubeHalfViewSize();
        cubeMarginTop = y - calculation.getCubeHalfViewSize();
        shadowMarginStart = x - calculation.getShadowHalfViewSize();
        shadowMarginTop = y - calculation.getShadowHalfViewSize();
    }

    public void moveCube() {
        setNewCubePosition();
    }

    private int getXRotation(int px, int py) {
        return (int) (x + (px - x) * Math.cos(radians) - (py - y) * Math.sin(radians));
    }

    private int getYRotation(int px, int py) {
        return (int) (y + (py - y) * Math.cos(radians) + (px - x) * Math.sin(radians));
    }

    public boolean intersection (Cube cube) {
        // ЭТАП 1: предварительная упрощенная проверка
        // Расстояние между центрами кубиков
        int distance = (int) Math.sqrt((Math.pow(Math.abs(x - cube.getX()), 2) + Math.pow(Math.abs(y - cube.getY()), 2)));

        Log.d("myLog", "Distance: " + x + ", " + y + " | " + cube.getX() + ", " + cube.getY() + " ---> " + distance);
        // Должно быть больше, чем сумма радиусов
        if (distance > calculation.getCubeRadius() * 2) {
            return false;
        }

        // ЭТАП 2: проверка положения всех точек
        // ...

        // заглушка
        return true;
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
}
