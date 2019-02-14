package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModel;

import ru.kulikovman.cubes.database.AppDatabase;
import ru.kulikovman.cubes.database.SettingsDao;
import ru.kulikovman.cubes.model.Settings;


public class CubesViewModel extends ViewModel {

    private SettingsDao settingsDao;

    private Settings settings;

    public CubesViewModel() {
        super();

        // Получение базы данных
        AppDatabase db = App.getInstance().getDatabase();
        settingsDao = db.settingsDao();
    }

    Settings getSettings() {
        if (settings == null) {
            // Получение настроек, если есть
            if (settingsDao.getById(0) != null) {
                settings = settingsDao.getById(0);
            } else {
                settings = new Settings();
                settingsDao.insert(settings);
            }
        }

        return settings;
    }

    void saveSettings(Settings settings) {
        settingsDao.update(settings);
    }



}
