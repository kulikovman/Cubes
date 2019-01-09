package ru.kulikovman.cubes.model;

import android.util.Log;

public class Rectangle {

    // Центр прямоугольника
    private Point base;

    // Размер и угол поворота
    private int size;
    private int angle;

    // Расстояние от центра до вершины
    private int radius;

    // Вершины прямоугольника
    private Point a = new Point(); // верхняя левая
    private Point b = new Point(); // верхняя правая
    private Point c = new Point(); // нижняя правая
    private Point d = new Point(); // нижняя левая

    public Rectangle(Point base, int size, int angle) {
        this.base = base;
        this.size = size;
        this.angle = angle;

        Log.d("myLog", "base.x/base.y = " + base.x + "/" + base.y + " | size = " + size + " | angle = " + angle);

        // Расчет положения вершин до поворота
        int halfSize = size / 2;
        int minX = base.x - halfSize;
        int maxX = base.x + halfSize;
        int minY = base.y - halfSize;
        int maxY = base.y + halfSize;

        a.x = minX;
        a.y = minY;

        b.x = maxX;
        b.y = minY;

        c.x = maxX;
        c.y = maxY;

        d.x = minX;
        d.y = maxY;

        // Расчет положения вершин после поворота
        // ...

        // Расчет радиуса
        // radius = (x1 - x2)^2 + (y1 - y2)^2
        radius = (int) Math.sqrt((Math.pow(Math.abs(base.x - a.x), 2) + Math.pow(Math.abs(base.y - a.y), 2)));
        Log.d("myLog", "radius = " + radius);
    }

    public boolean intersection (Rectangle rectangle) {


        return true;
    }
}
