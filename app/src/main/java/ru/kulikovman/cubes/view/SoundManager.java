package ru.kulikovman.cubes.view;

import android.media.AudioAttributes;
import android.media.SoundPool;

import ru.kulikovman.cubes.App;
import ru.kulikovman.cubes.R;

public class SoundManager {

    private SoundPool soundPool;

    private int dropSound;
    private int buttonSound;

    public SoundManager() {
        initSoundPool();
    }

    private void initSoundPool() {
        // Инициализация SoundPool
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        // Получаем id звуковых файлов
        dropSound = soundPool.load(App.getContext(), R.raw.roll_dice, 1);
        buttonSound = soundPool.load(App.getContext(), R.raw.button_click, 1);
    }

    public void playDropSound() {
        /*if (soundPool == null) {
            initSoundPool();
        }*/

        // Воспроизводим звук броска
        soundPool.play(dropSound, 1, 1, 1, 0, 1);
    }

    public void playButtonSound() {
        /*if (soundPool == null) {
            initSoundPool();
        }*/

        // Воспроизводим звук нажатия кнопки
        soundPool.play(buttonSound, 1, 1, 1, 0, 1);
    }

    public void releaseSoundPool() {
        soundPool.release();
        soundPool = null;
    }
}
