package ru.kulikovman.cubes;

import android.content.Context;
import android.media.SoundPool;

public class SoundManager {

    private static SoundManager ourInstance;
    private SoundPool soundPool;

    private int DROP_SOUND;
    private int SETTING_BUTTON_SOUND;

    public static synchronized SoundManager getInstance(){
        if(ourInstance == null){
            ourInstance = new SoundManager();
        }

        return ourInstance;
    }

    public static void initialize(Context context){
        SoundManager soundManager = getInstance();
        soundManager.loadSound(context);
    }

    private SoundManager() {
        soundPool = (new SoundPool.Builder()).setMaxStreams(2).build();
    }

    private void loadSound(Context context){
        // Получаем id звуковых файлов
        DROP_SOUND = soundPool.load(context, R.raw.roll_dice, 1);
        SETTING_BUTTON_SOUND = soundPool.load(context, R.raw.button_click, 1);
    }

    public void playDropSound() {
        // Воспроизводим звук броска
        soundPool.play(DROP_SOUND, 1, 1, 1, 0, 1);
    }

    public void playSettingButtonSound() {
        // Воспроизводим звук нажатия кнопки
        soundPool.play(SETTING_BUTTON_SOUND, 1, 1, 1, 0, 1);
    }
}
