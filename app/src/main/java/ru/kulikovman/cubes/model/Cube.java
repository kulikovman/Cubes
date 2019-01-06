package ru.kulikovman.cubes.model;

import java.util.Random;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

    private Skin skin;
    private int value;
    private int angle;
    private int x;
    private int y;

    public Cube(Skin skin, RollArea rollArea) {
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
        x = rollArea.getMinX() + random.nextInt(rollArea.getMaxX() + 1);
        y = rollArea.getMinY() + random.nextInt(rollArea.getMaxY() + 1);
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
