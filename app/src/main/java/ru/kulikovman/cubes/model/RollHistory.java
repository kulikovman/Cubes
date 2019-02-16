package ru.kulikovman.cubes.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class RollHistory {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long time;
    private List<CubeHistory> cubeHistories;

    public RollHistory() {
        time = System.currentTimeMillis();
        cubeHistories = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<CubeHistory> getCubeHistories() {
        return cubeHistories;
    }

    public void setCubeHistories(List<CubeHistory> cubeHistories) {
        this.cubeHistories = cubeHistories;
    }

    public void addCubeHistory(CubeHistory cubeHistory) {
        cubeHistories.add(cubeHistory);
    }
}
