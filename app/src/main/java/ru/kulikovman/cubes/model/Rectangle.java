package ru.kulikovman.cubes.model;

import android.util.Log;

public class Rectangle {

    // Центр прямоугольника
    private Point base;

    // Размер и угол поворота
    private int size;
    private double angle;

    // Расстояние от центра до вершины
    private int radius;

    // Вершины прямоугольника
    private Point a = new Point(); // верхняя левая
    private Point b = new Point(); // верхняя правая
    private Point c = new Point(); // нижняя правая
    private Point d = new Point(); // нижняя левая

    public Rectangle(Point base, int size, int angle) {
        Log.d("myLog", "base = " + base.x + "-" + base.y + " | size = " + size + " | angle = " + angle);
        this.base = base;
        this.size = size;

        // Перевод угла из градусов в радианы
        this.angle = angle * Math.PI / 180;

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
        pointRotation(a);
        pointRotation(b);
        pointRotation(c);
        pointRotation(d);

        // Расчет радиуса
        // r = (x1 - x2)^2 + (y1 - y2)^2
        radius = (int) Math.sqrt((Math.pow(Math.abs(base.x - a.x), 2) + Math.pow(Math.abs(base.y - a.y), 2)));
        Log.d("myLog", "radius = " + radius);
    }

    private void pointRotation(Point point) {
        // Поворот точки вокруг другой точки
        // x/y - точка, которую вращаем
        // x0/y0 - точка вокруг которой происходит вращение
        // а - угол поворота в радианах (отрицательный угол вращает против часовой)
        // x1/y1 - новые координаты
        // -------------------
        // x1 = x0 + (x - x0) * cos(a) - (y - y0) * sin(a);
        // y1 = y0 + (y - y0) * cos(a) + (x - x0) * sin(a);

        Point p = new Point();

        // Вращение вокруг точки base
        p.x = (int) (base.x + (a.x - base.x) * Math.cos(angle) - (a.y - base.y) * Math.sin(angle));
        p.y = (int) (base.y + (a.y - base.y) * Math.cos(angle) + (a.x - base.x) * Math.sin(angle));

        // Сохранение новых координат
        point.x = p.x;
        point.y = p.y;
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
