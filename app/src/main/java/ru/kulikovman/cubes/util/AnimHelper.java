package ru.kulikovman.cubes.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ru.kulikovman.cubes.CubeApp;
import ru.kulikovman.cubes.R;

public class AnimHelper {

    public static void appearance(View view) {
        Animation animation = AnimationUtils.loadAnimation(CubeApp.getContext(), R.anim.appearance);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

}
