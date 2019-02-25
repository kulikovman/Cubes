package ru.kulikovman.cubes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.kulikovman.cubes.model.ThrowResult;

@Dao
public interface ThrowResultDao {

    // Возвращает список отсортированный по дате (по убыванию)
    @Query("SELECT * FROM ThrowResult ORDER BY time DESC")
    List<ThrowResult> getAll();

    // Если записей больше, чем limitRecords, то удаляет самые старые по времени
    @Query("DELETE FROM ThrowResult WHERE time IN (SELECT time FROM ThrowResult ORDER BY time DESC LIMIT -1 OFFSET :limitRecords)")
    void deleteOldestRecords(int limitRecords);

    @Insert()
    void insert(ThrowResult settings);

    @Update
    void update(ThrowResult settings);

    @Delete
    void delete(ThrowResult settings);

}
