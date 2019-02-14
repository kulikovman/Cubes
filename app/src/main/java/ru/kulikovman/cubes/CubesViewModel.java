package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModel;

import ru.kulikovman.cubes.database.AppDatabase;
import ru.kulikovman.cubes.database.SettingsDao;
import ru.kulikovman.cubes.model.Settings;


public class CubesViewModel extends ViewModel {

    private Settings settings;

    Settings getSettings() {
        if (settings == null) {
            // Получение базы данных
            AppDatabase db = App.getInstance().getDatabase();
            SettingsDao settingsDao = db.settingsDao();

            // Получение настроек, если есть
            if (settingsDao.getById(0) != null) {
                settings = settingsDao.getById(0);
            } else {
                settings = new Settings();
            }
        }

        return settings;
    }

    public void saveSettings(Settings settings) {
        // Получение базы данных
        AppDatabase db = App.getInstance().getDatabase();
        SettingsDao settingsDao = db.settingsDao();

        settingsDao.update(settings);
    }

    @Override
    protected void onCleared() {

        super.onCleared();
    }
}
