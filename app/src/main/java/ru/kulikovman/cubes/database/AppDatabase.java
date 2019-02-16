package ru.kulikovman.cubes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.kulikovman.cubes.database.converter.CubeLiteConverter;
import ru.kulikovman.cubes.database.dao.SettingsDao;
import ru.kulikovman.cubes.model.RollResult;
import ru.kulikovman.cubes.model.Settings;


@Database(entities = {Settings.class, RollResult.class}, version = 1)
@TypeConverters(CubeLiteConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SettingsDao settingsDao();

    public abstract RollResult rollHistoryDao();

}
