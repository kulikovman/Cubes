package ru.kulikovman.cubes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.kulikovman.cubes.model.RollHistory;

@Dao
public interface RollHistoryDao {

    @Query("SELECT * FROM RollHistory")
    List<RollHistory> getAll();

    @Query("SELECT * FROM RollHistory WHERE id = :id")
    RollHistory getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RollHistory settings);

    @Update
    void update(RollHistory settings);

    @Delete
    void delete(RollHistory settings);

}
