package ru.kulikovman.cubes.data;

public enum CubeType {

    WHITE(6),
    BLACK(6),
    RED(6);

    private int numberOfSides;

    CubeType(int numberOfSides) {
        this.numberOfSides = numberOfSides;
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }
}
