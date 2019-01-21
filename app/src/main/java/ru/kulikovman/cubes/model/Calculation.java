package ru.kulikovman.cubes.model;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

import ru.kulikovman.cubes.R;
import ru.kulikovman.cubes.data.Skin;

public class Calculation {

    private final Random random;

    // Размер экрана
    private int screenWidth;
    private int screenHeight;

    // Кнопка настроек
    private int sx, sy;
    private int settingButtonRadius;

    // Размеры кубиков и теней
    private int whiteCubeHalfSize, blackCubeHalfSize, redCubeHalfSize;
    private int whiteCubeViewHalfSize, blackCubeViewHalfSize, redCubeViewHalfSize;
    private int whiteShadowViewHalfSize, blackShadowViewHalfSize, redShadowViewHalfSize;

    // Радиусы кубиков и теней
    private int whiteCubeInnerRadius, blackCubeInnerRadius, redCubeInnerRadius ;
    private int whiteCubeOuterRadius, blackCubeOuterRadius, redCubeOuterRadius;
    private int whiteShadowRadius, blackShadowRadius, redShadowRadius;

    public Calculation(Resources resources) {
        random = new Random();

        // Размеры экрана
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Координаты кнопки настроек
        int settingSize = resources.getDimensionPixelSize(R.dimen.button_setting_size);
        int settingPadding = resources.getDimensionPixelSize(R.dimen.button_setting_padding);
        int settingMarginTop = resources.getDimensionPixelSize(R.dimen.button_setting_marginTop);
        sx = screenWidth / 2;
        sy = settingSize / 2 + settingPadding + settingMarginTop;

        // Радиус кнопки настроек
        settingButtonRadius = settingSize / 2 + settingPadding;

        // Размеры вью кубиков
        int whiteCubeViewSize = resources.getDimensionPixelSize(R.dimen.white_cube_view_size);
        int blackCubeViewSize = resources.getDimensionPixelSize(R.dimen.black_cube_view_size);
        int redCubeViewSize = resources.getDimensionPixelSize(R.dimen.red_cube_view_size);

        // Полуразмеры вью кубиков
        whiteCubeViewHalfSize = whiteCubeViewSize / 2;
        blackCubeViewHalfSize = blackCubeViewSize / 2;
        redCubeViewHalfSize = redCubeViewSize / 2;

        // Размеры кубиков с учетом буферного расстояния (+3% от ширины кубика)
        int whiteCubeSize = (int) (Math.sqrt((Math.pow(whiteCubeViewHalfSize, 2) + Math.pow(whiteCubeViewHalfSize, 2))) * 0.03);
        int blackCubeSize = (int) (Math.sqrt((Math.pow(blackCubeViewHalfSize, 2) + Math.pow(blackCubeViewHalfSize, 2))) * 0.03);
        int redCubeSize = (int) (Math.sqrt((Math.pow(redCubeViewHalfSize, 2) + Math.pow(redCubeViewHalfSize, 2))) * 0.03);

        // Полуразмеры кубиков
        whiteCubeHalfSize = whiteCubeSize / 2;
        blackCubeHalfSize = blackCubeSize / 2;
        redCubeHalfSize = redCubeSize / 2;

        // Размер вью теней
        int whiteShadowViewSize = resources.getDimensionPixelSize(R.dimen.white_shadow_view_size);
        int blackShadowViewSize = resources.getDimensionPixelSize(R.dimen.black_shadow_view_size);
        int redShadowViewSize = resources.getDimensionPixelSize(R.dimen.red_shadow_view_size);

        // Полуразмеры вью теней
        whiteShadowViewHalfSize = whiteShadowViewSize / 2;
        blackShadowViewHalfSize = blackShadowViewSize / 2;
        redShadowViewHalfSize = redShadowViewSize / 2;

        // Внутренние радиусы кубиков
        whiteCubeInnerRadius = whiteCubeSize;
        blackCubeInnerRadius = blackCubeSize;
        redCubeInnerRadius = redCubeSize;

        // Внешние радиусы кубиков
        whiteCubeOuterRadius = whiteCubeViewHalfSize;
        blackCubeOuterRadius = blackCubeViewHalfSize;
        redCubeOuterRadius = redCubeViewHalfSize;

        // Радиусы теней
        whiteShadowRadius = (int) Math.sqrt((Math.pow(whiteShadowViewHalfSize, 2) + Math.pow(whiteShadowViewHalfSize, 2)));
        blackShadowRadius = (int) Math.sqrt((Math.pow(blackShadowViewHalfSize, 2) + Math.pow(blackShadowViewHalfSize, 2)));
        redShadowRadius = (int) Math.sqrt((Math.pow(redShadowViewHalfSize, 2) + Math.pow(redShadowViewHalfSize, 2)));



        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "---------------------------");
    }

    public Random getRandom() {
        return random;
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public int getSettingButtonRadius() {
        return settingButtonRadius;
    }

    public Sizes getSizes(Skin skin) {
        // Возвращает комплект размеров
        // в соответствии с цветом/скином кубика
        switch (skin) {
            case WHITE:
                return new Sizes(screenWidth, screenHeight, whiteShadowRadius,
                        whiteCubeHalfSize, whiteCubeViewHalfSize, whiteShadowViewHalfSize, whiteCubeInnerRadius, whiteCubeOuterRadius);
            case BLACK:
                return new Sizes(screenWidth, screenHeight, blackShadowRadius,
                        blackCubeHalfSize, blackCubeViewHalfSize, blackShadowViewHalfSize, blackCubeInnerRadius, blackCubeOuterRadius);
            case RED:
                return new Sizes(screenWidth, screenHeight, redShadowRadius,
                        redCubeHalfSize, redCubeViewHalfSize, redShadowViewHalfSize, redCubeInnerRadius, redCubeOuterRadius);
        }

        return null;
    }
}
