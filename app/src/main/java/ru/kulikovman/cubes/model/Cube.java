package ru.kulikovman.cubes.model;

import java.util.Random;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

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

    // Расстояние от центра до вершины
    private int radius;

    // Вершины прямоугольника
    private int x1, y1; // верхняя левая
    private int x2, y2; // верхняя правая
    private int x3, y3; // нижняя правая
    private int x4, y4; // нижняя левая

    public Cube(Skin skin, RollArea rollArea, int size) {
        this.skin = skin;
        this.size = size;

        // Генератор случайных чисел
        Random random = new Random();

        // Цвет кубика
        if (skin == Skin.RANDOM) {
            int skinIndex = 1 + random.nextInt(Skin.values().length); // случайный цвет
            this.skin = Skin.values()[skinIndex];
        } else {
            this.skin = skin;
        }

        // Количество точек
        value = 1 + random.nextInt(6); // от 1 до 6

        // Угол поворота
        degrees = random.nextInt(360); // от 0 до 359

        // Перевод угла из градусов в радианы
        radians = degrees * Math.PI / 180;

        // Расположение на экране
        x = rollArea.getMinX() + random.nextInt(rollArea.getMaxX() + 1);
        y = rollArea.getMinY() + random.nextInt(rollArea.getMaxY() + 1);

        // Макс./мин. координаты вершин
        int halfSize = size / 2;
        int minX = x - halfSize;
        int maxX = x + halfSize;
        int minY = y - halfSize;
        int maxY = y + halfSize;

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
        // Поворот точки вокруг другой точки
        // x/y - точка, которую вращаем
        // x0/y0 - точка вокруг которой происходит вращение
        // а - угол поворота в радианах (отрицательный угол вращает против часовой)
        // x1/y1 - новые координаты
        // -------------------
        // x1 = x0 + (x - x0) * cos(a) - (y - y0) * sin(a);
        // y1 = y0 + (y - y0) * cos(a) + (x - x0) * sin(a);
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

        // Расчет радиуса
        // r = (x1 - x2)^2 + (y1 - y2)^2
        radius = (int) Math.sqrt((Math.pow(Math.abs(x - x1), 2) + Math.pow(Math.abs(y - y1), 2)));
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
