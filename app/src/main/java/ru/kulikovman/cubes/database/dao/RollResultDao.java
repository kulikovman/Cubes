package ru.kulikovman.cubes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.kulikovman.cubes.model.RollResult;

@Dao
public interface RollResultDao {

    @Query("SELECT * FROM RollResult")
    List<RollResult> getAll();

    @Query("SELECT * FROM RollResult WHERE id = :id")
    RollResult getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RollResult settings);

    @Update
    void update(RollResult settings);

    @Delete
    void delete(RollResult settings);

}
