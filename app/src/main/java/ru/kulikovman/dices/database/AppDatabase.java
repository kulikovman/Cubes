package ru.kulikovman.dices.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.kulikovman.dices.database.converter.CubeLiteConverter;
import ru.kulikovman.dices.database.dao.ThrowResultDao;
import ru.kulikovman.dices.database.dao.SettingsDao;
import ru.kulikovman.dices.model.ThrowResult;
import ru.kulikovman.dices.model.Settings;


@Database(entities = {Settings.class, ThrowResult.class}, version = 1)
@TypeConverters(CubeLiteConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SettingsDao settingsDao();

    public abstract ThrowResultDao throwResultDao();

}
