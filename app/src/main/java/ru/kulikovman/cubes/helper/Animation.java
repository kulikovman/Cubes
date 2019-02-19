package ru.kulikovman.cubes.helper;

import android.view.View;

public class Animation {

    public static void smoothAppearance(View view, int time) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        view.animate()
                .alpha(1f)
                .setDuration(time);
    }
}
