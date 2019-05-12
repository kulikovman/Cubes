package ru.kulikovman.cubes.ui;

import androidx.lifecycle.ViewModel;

import ru.kulikovman.cubes.CubeApp;
import ru.kulikovman.cubes.repository.DataRepository;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Settings;


public class CubesViewModel extends ViewModel {

    private DataRepository repository;
    private Calculation calculation;
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

    public Calculation getCalculation() {
        if (calculation == null) {
            calculation = new Calculation(CubeApp.getContext().getResources());
        }

        return calculation;
    }

    void saveSettings() {
        repository.saveSettings(settings);
    }
}
