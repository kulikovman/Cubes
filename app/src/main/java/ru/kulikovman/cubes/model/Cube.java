package ru.kulikovman.cubes.model;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

    private Skin skin;
    private int value;
    private int angle;

    // Верхняя левая точка
    private Point point;

    public Cube(Skin skin, int value, int angle, Point point) {
        this.skin = skin;
        this.value = value;
        this.angle = angle;
        this.point = point;
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
