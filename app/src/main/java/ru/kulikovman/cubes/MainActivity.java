package ru.kulikovman.cubes;

import androidx.lifecycle.ViewModelProviders;
import android.media.AudioManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private CubesViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(CubesViewModel.class);

        // Кнопки громкости меняют громкость медиа
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    public void changeTheme() {
        boolean isDark = model.getSettings().isDarkTheme();

        // Применяем темную или светлую тему
        getDelegate().setLocalNightMode(isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
