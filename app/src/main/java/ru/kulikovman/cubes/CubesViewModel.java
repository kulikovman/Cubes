package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModel;

import ru.kulikovman.cubes.model.Settings;


public class CubesViewModel extends ViewModel {

    private DataRepository repository;
    private Settings settings;

    public CubesViewModel() {
        super();

        // Получение репозитория
        repository = DataRepository.get();
    }

    public DataRepository getRepository() {
        return repository;
    }

    public Settings getSettings() {
        if (settings == null) {
            settings = repository.getSettings();
        }

        return settings;
    }

    void saveSettings() {
        repository.saveSettings(settings);
    }
}
