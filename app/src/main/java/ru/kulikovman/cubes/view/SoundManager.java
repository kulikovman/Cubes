package ru.kulikovman.cubes.view;

public class SoundManager {
    private static final SoundManager ourInstance = new SoundManager();

    public static SoundManager getInstance() {
        return ourInstance;
    }

    private SoundManager() {
    }


}
