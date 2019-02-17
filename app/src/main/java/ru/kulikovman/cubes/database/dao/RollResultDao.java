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

    // Возвращает список отсортированный по дате (по убыванию)
    @Query("SELECT * FROM RollResult ORDER BY time DESC")
    List<RollResult> getAll();

    // Если записей больше, чем limitRecords, то удаляет самые старые по времени
    @Query("DELETE FROM RollResult WHERE time IN (SELECT time FROM RollResult ORDER BY time DESC LIMIT -1 OFFSET :limitRecords)")
    void deleteOldestRecords(int limitRecords);

    @Insert()
    void insert(RollResult settings);

    @Update
    void update(RollResult settings);

    @Delete
    void delete(RollResult settings);

}
