package ru.kulikovman.dices.helper;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ru.kulikovman.dices.App;
import ru.kulikovman.dices.R;

public class AnimHelper {

    public static void appearance(View view) {
        Animation animation = AnimationUtils.loadAnimation(App.getContext(), R.anim.appearance);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

}
