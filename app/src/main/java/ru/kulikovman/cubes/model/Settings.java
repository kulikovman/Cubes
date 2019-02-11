package ru.kulikovman.cubes.model;

import ru.kulikovman.cubes.data.Skin;

public class Settings {

    private int numberOfCubes;
    private float delayAfterRoll;
    private boolean isBlockSleepingMode;
    private String cubeColor;
    private int numberOfRoll;

    public Settings() {
        numberOfCubes = 2;
        delayAfterRoll = 0.3f;
        isBlockSleepingMode = false;
        cubeColor = Skin.WHITE.name();
        numberOfRoll = 0;
    }

    public int getNumberOfCubes() {
        return numberOfCubes;
    }

    public void setNumberOfCubes(int numberOfCubes) {
        this.numberOfCubes = numberOfCubes;
    }

    public float getDelayAfterRoll() {
        return delayAfterRoll;
    }

    public void setDelayAfterRoll(float delayAfterRoll) {
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
}
