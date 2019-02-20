package ru.kulikovman.cubes.helper;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ru.kulikovman.cubes.App;
import ru.kulikovman.cubes.R;

public class AnimHelper {

    public static void appearance(View view) {
        Animation animation = AnimationUtils.loadAnimation(App.getContext(), R.anim.appearance);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

}
