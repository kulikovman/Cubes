package ru.kulikovman.dices.helper;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utils {

    public static int convertDpToPx(int dp) {
        return Math.round(dp * (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    public static int convertPxToDp(int px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
