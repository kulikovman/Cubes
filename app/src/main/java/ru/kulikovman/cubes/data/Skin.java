package ru.kulikovman.cubes.data;

public enum Skin {

    WHITE(1, 6),
    BLACK(1, 6),
    RED(1, 6);

    private int minValue;
    private int maxValue;

    Skin(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
