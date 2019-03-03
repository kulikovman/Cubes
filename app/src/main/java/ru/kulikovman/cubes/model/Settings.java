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
    private boolean isKeepScreenOn;
    private boolean isShownThrowAmount;
    private String cubeType;

    private int numberOfThrow;
    private boolean isRated;

    public Settings() {
        id = 0;
        numberOfCubes = 4;
        delayAfterThrow = 0;
        isKeepScreenOn = false;
        isShownThrowAmount = false;
        cubeType = CubeType.WHITE.name();
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

    public boolean isKeepScreenOn() {
        return isKeepScreenOn;
    }

    public void setKeepScreenOn(boolean keepScreenOn) {
        isKeepScreenOn = keepScreenOn;
    }

    public boolean isShownThrowAmount() {
        return isShownThrowAmount;
    }

    public void setShownThrowAmount(boolean shownThrowAmount) {
        isShownThrowAmount = shownThrowAmount;
    }

    public String getCubeType() {
        return cubeType;
    }

    public void setCubeType(String cubeType) {
        this.cubeType = cubeType;
    }

    public int getNumberOfThrow() {
        return numberOfThrow;
    }

    public void setNumberOfThrow(int numberOfThrow) {
        if (numberOfThrow == Integer.MAX_VALUE) {
            this.numberOfThrow = 0;
        } else {
            this.numberOfThrow = numberOfThrow;
        }
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }
}
