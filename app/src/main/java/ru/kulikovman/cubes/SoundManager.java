package ru.kulikovman.cubes;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundManager {

    private static SoundManager instance;

    private SoundPool soundPool;

    public static int TOP_BUTTON_CLICK_SOUND;
    public static int THROW_CUBES_SOUND;
    public static int TAPE_REWIND_SOUND;
    public static int SEEKBAR_CLICK_SOUND;
    public static int SWITCH_CLICK_SOUND;
    public static int CUBE_CLICK_SOUND;

    private SoundManager() {
        // Создание SoundPool
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(1)
                .build();
    }

    public static SoundManager get() {
        if (instance == null) {
            synchronized (SoundManager.class) {
                if (instance == null) {
                    instance = new SoundManager();
                }
            }
        }

        return instance;
    }

    public static void initialize(){
        SoundManager soundManager = get();
        soundManager.loadSounds(App.getContext());
    }

    private void loadSounds(Context context) {
        THROW_CUBES_SOUND = soundPool.load(context, R.raw.throw_cubes, 1);
        TOP_BUTTON_CLICK_SOUND = soundPool.load(context, R.raw.top_button_click, 1);
        TAPE_REWIND_SOUND = soundPool.load(context, R.raw.tape_rewind, 1);
        SEEKBAR_CLICK_SOUND = soundPool.load(context, R.raw.seekbar_click, 1);
        SWITCH_CLICK_SOUND = soundPool.load(context, R.raw.switch_click, 1);
        CUBE_CLICK_SOUND = soundPool.load(context, R.raw.cube_click, 1);
    }

    public void playSound(int soundId) {
        // Воспроизводим указанный звук
        soundPool.play(soundId, 1, 1, 1, 0, 1);
    }
}
