package ru.kulikovman.cubes;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;

import ru.kulikovman.cubes.database.AppDatabase;


public class CubeApp extends Application {

    private static CubeApp instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Подключение базы данных
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries() // разрешает операции в основном потоке
                .fallbackToDestructiveMigration() // обнуляет базу, если нет подходящей миграции
                .addMigrations(AppDatabase.MIGRATION_4_5, AppDatabase.MIGRATION_5_6)
                .build();
    }

    public static CubeApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
