package ru.kulikovman.cubes.repository;

import java.util.List;

import ru.kulikovman.cubes.CubeApp;
import ru.kulikovman.cubes.database.AppDatabase;
import ru.kulikovman.cubes.model.Settings;
import ru.kulikovman.cubes.model.ThrowResult;

public class DataRepository {

    private static DataRepository instance;

    private final AppDatabase database;

    private DataRepository() {
        database = CubeApp.getInstance().getDatabase();
    }

    public static DataRepository get() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }

        return instance;
    }

    public Settings getSettings() {
        // Получение настроек
        Settings settings = database.settingsDao().getById(0);
        if (settings == null) {
            settings = new Settings();
            database.settingsDao().insert(settings);
        }

        return settings;
    }

    public void saveSettings(Settings settings) {
        database.settingsDao().update(settings);
    }

    public void saveThrowResult(ThrowResult throwResult) {
        // Записываем результат броска и удаляем старые записи
        database.throwResultDao().insert(throwResult);
        database.throwResultDao().deleteOldestRecords(10);
    }

    public List<ThrowResult> getThrowResultList() {
        return database.throwResultDao().getAll();
    }
}
