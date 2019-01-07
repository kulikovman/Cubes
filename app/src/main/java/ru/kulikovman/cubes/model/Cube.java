package ru.kulikovman.cubes.model;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

    private Skin skin;
    private int value;
    private int angle;
    private int marginStart;
    private int marginTop;

    public Cube(Skin skin, int value, int angle, int marginStart, int marginTop) {
        this.skin = skin;
        this.value = value;
        this.angle = angle;
        this.marginStart = marginStart;
        this.marginTop = marginTop;
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
}
