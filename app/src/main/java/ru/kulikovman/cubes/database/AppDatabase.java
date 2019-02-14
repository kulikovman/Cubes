package ru.kulikovman.cubes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.kulikovman.cubes.model.Settings;


@Database(entities = {Settings.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SettingsDao settingsDao();

}
