package ru.kulikovman.cubes.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ru.kulikovman.cubes.data.CubeType;

@Entity
public class Settings {

    @PrimaryKey
    public long id;

    private int numberOfCubes;
    private int delayAfterThrow;
    private boolean isBlockSleepingMode;
    private String cubeColor;

    private int numberOfThrow;
    private boolean isRated;

    public Settings() {
        id = 0;
        numberOfCubes = 2;
        delayAfterThrow = 0;
        isBlockSleepingMode = false;
        cubeColor = CubeType.WHITE.name();
        numberOfThrow = 0;
        isRated = false;
    }

    public int getNumberOfCubes() {
        return numberOfCubes;
    }

    public void setNumberOfCubes(int numberOfCubes) {
        this.numberOfCubes = numberOfCubes;
    }

    public int getDelayAfterThrow() {
        return delayAfterThrow;
    }

    public void setDelayAfterThrow(int delayAfterRoll) {
        this.delayAfterThrow = delayAfterRoll;
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

    public int getNumberOfThrow() {
        return numberOfThrow;
    }

    public void setNumberOfThrow(int numberOfThrow) {
        this.numberOfThrow = numberOfThrow;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }
}
