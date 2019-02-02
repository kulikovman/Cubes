package ru.kulikovman.cubes;

import android.arch.lifecycle.ViewModel;

import ru.kulikovman.cubes.view.SoundManager;


public class CubesViewModel extends ViewModel {

    /*private SoundManager soundManager;

    SoundManager getSoundManager() {
        soundManager

        return soundManager;
    }*/


    @Override
    protected void onCleared() {
        //soundManager.releaseSoundPool();

        super.onCleared();
    }
}
