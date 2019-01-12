package ru.kulikovman.cubes.model;

import java.util.Random;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

    // Базовые значения
    private Calculation calculation;
    private final Random random;

    // Параметры для вью
    private Skin skin;
    private int value;
    private int degrees;
    private int marginStart, marginTop;

    // Центр кубика
    private int x, y;

    // Размер и угол поворота
    private int size;
    private double radians;

    // Вершины прямоугольника
    private int x1, y1; // верхняя левая
    private int x2, y2; // верхняя правая
    private int x3, y3; // нижняя правая
    private int x4, y4; // нижняя левая

    public Cube(Calculation calculation, Skin skin) {
        this.calculation = calculation;
        this.skin = skin;
        this.size = calculation.getSize();
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
            getScreenPosition();
            isCorrectPosition = checkPosition();
        }

        // Макс./мин. координаты вершин
        int minX = x - calculation.getHalfSize();
        int maxX = x + calculation.getHalfSize();
        int minY = y - calculation.getHalfSize();
        int maxY = y + calculation.getHalfSize();

        // Подсчет отступов
        marginStart = minX;
        marginTop = minY;

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
        x1 = getXRotation(x1, y1);
        y1 = getYRotation(x1, y1);
        x2 = getXRotation(x2, y2);
        y2 = getYRotation(x1, y1);
        x3 = getXRotation(x1, y1);
        y3 = getYRotation(x1, y1);
        x4 = getXRotation(x1, y1);
        y4 = getYRotation(x1, y1);

        // !!!! Нихуя не правильно! Нужно сразу две точки поворачивать и уже
        // только потом сохранять новые координаты, иначе У будет повернут исходя из
        // новых координат Х


    }

    private boolean checkPosition() {
        // Расчет расстояния от центра кнопки настроек до центра кубика
        int distance = (int) Math.sqrt((Math.pow(Math.abs(x - calculation.getSx()), 2) +
                Math.pow(Math.abs(y - calculation.getSy()), 2)));

        // Должно быть больше, чем сумма радиусов
        return distance > calculation.getCubeRadius() + calculation.getSettingRadius();
    }

    private void getScreenPosition() {
        x = calculation.getRollArea().getMinX() + random.nextInt(calculation.getRollArea().getMaxX() + 1);
        y = calculation.getRollArea().getMinY() + random.nextInt(calculation.getRollArea().getMaxY() + 1);
    }

    private int getXRotation(int px, int py) {
        return (int) (x + (px - x) * Math.cos(radians) - (py - y) * Math.sin(radians));
    }

    private int getYRotation(int px, int py) {
        return (int) (y + (py - y) * Math.cos(radians) + (px - x) * Math.sin(radians));
    }

    public boolean intersection (Rectangle rectangle) {


        return true;
    }

    /*private boolean isIntersection(List<Dice> coordinates) {
        // Расстояние между центрами кубиков
        int dist = convertDpToPx(110);

        // Проверка пересечений
        for (Dice d1 : coordinates) {
            for (Dice d2 : coordinates) {
                if (!d1.equals(d2)) {
                    int dX = Math.abs(d1.getX() - d2.getX());
                    int dY = Math.abs(d1.getY() - d2.getY());
                    if (dX < dist && dY < dist) {
                        return true;
                    }
                }
            }
        }
        return false;
    }*/
}
