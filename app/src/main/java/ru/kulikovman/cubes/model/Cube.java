package ru.kulikovman.cubes.model;

import java.util.Random;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

    private Skin skin;
    private int value;
    private int angle;
    private int size;
    private int marginStart;
    private int marginTop;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMarginStart() {
        return marginStart;
    }

    public void setMarginStart(int marginStart) {
        this.marginStart = marginStart;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    // Верхняя левая точка
    private Point point;

    public Cube(Skin skin, int value, int angle, Point point) {
        this.skin = skin;
        this.value = value;
        this.angle = angle;
        this.point = point;



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
        angle = random.nextInt(360); // от 0 до 359

        // Расположение на экране
        point.x = rollArea.getMinX() + random.nextInt(rollArea.getMaxX() + 1);
        point.y = rollArea.getMinY() + random.nextInt(rollArea.getMaxY() + 1);
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
