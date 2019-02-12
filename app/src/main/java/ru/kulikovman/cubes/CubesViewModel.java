package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModel;

import ru.kulikovman.cubes.model.Settings;


public class CubesViewModel extends ViewModel {

    private Settings settings;

    public Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
        }

        return settings;
    }

    @Override
    protected void onCleared() {

        super.onCleared();
    }
}
