package ru.kulikovman.cubes.model;

import ru.kulikovman.cubes.data.Skin;

public class Settings {

    private int numberOfCubes;
    private int delayAfterRoll;
    private boolean isBlockSleepingMode;
    private String cubeColor;

    private int numberOfRoll;
    private boolean isRated;

    public Settings() {
        numberOfCubes = 2;
        delayAfterRoll = 0;
        isBlockSleepingMode = false;
        cubeColor = Skin.WHITE.name();
        numberOfRoll = 0;
        isRated = false;
    }

    public int getNumberOfCubes() {
        return numberOfCubes;
    }

    public void setNumberOfCubes(int numberOfCubes) {
        this.numberOfCubes = numberOfCubes;
    }

    public int getDelayAfterRoll() {
        return delayAfterRoll;
    }

    public void setDelayAfterRoll(int delayAfterRoll) {
        this.delayAfterRoll = delayAfterRoll;
    }

    public boolean isBlockSleepingMode() {
        return isBlockSleepingMode;
    }

    public void setBlockSleepingMode(boolean blockSleepingMode) {
        isBlockSleepingMode = blockSleepingMode;
    }

    public String getCubeColor() {
        return cubeColor;
    }

    public void setCubeColor(String cubeColor) {
        this.cubeColor = cubeColor;
    }

    public int getNumberOfRoll() {
        return numberOfRoll;
    }

    public void setNumberOfRoll(int numberOfRoll) {
        this.numberOfRoll = numberOfRoll;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }
}
