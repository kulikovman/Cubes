package ru.kulikovman.cubes.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ThrowResult {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long time;
    private List<CubeLite> cubeLites;

    public ThrowResult() {
        time = System.currentTimeMillis();
        cubeLites = new ArrayList<>();
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

    public List<CubeLite> getCubeLites() {
        return cubeLites;
    }

    public void setCubeLites(List<CubeLite> cubeLites) {
        this.cubeLites = cubeLites;
    }

    public void addCubeLite(CubeLite cubeLite) {
        cubeLites.add(cubeLite);
    }
}
