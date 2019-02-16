package ru.kulikovman.cubes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.kulikovman.cubes.database.converter.CubeHistoryConverter;
import ru.kulikovman.cubes.database.dao.SettingsDao;
import ru.kulikovman.cubes.model.RollHistory;
import ru.kulikovman.cubes.model.Settings;


@Database(entities = {Settings.class, RollHistory.class}, version = 1)
@TypeConverters(CubeHistoryConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SettingsDao settingsDao();

    public abstract RollHistory rollHistoryDao();

}
