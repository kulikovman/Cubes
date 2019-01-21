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

    // Центр кнопки настроек
    private int sx, sy;





    // Размеры кубиков и теней
    private int whiteCubeHalfSize, blackCubeHalfSize, redCubeHalfSize;
    private int whiteCubeViewHalfSize, blackCubeViewHalfSize, redCubeViewHalfSize;
    private int whiteShadowViewHalfSize, blackShadowViewHalfSize, redShadowViewHalfSize;


    // Радиусы кубиков и теней
    private int whiteCubeInnerRadius, blackCubeInnerRadius, redCubeInnerRadius ;
    private int whiteCubeOuterRadius, blackCubeOuterRadius, redCubeOuterRadius;
    private int whiteShadowRadius, blackShadowRadius, redShadowRadius;

    // Прочие размеры
    private int settingButtonRadius;

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

        // Размеры вью кубика
        // Размер вью кубика
        int cubeViewSize = resources.getDimensionPixelSize(R.dimen.white_cube_view_size);
        whiteCubeViewHalfSize = cubeViewSize / 2;

        // Размеры кубика с учетом буферного расстояния (+3% от ширины кубика)
        int cubeSize = (int) (Math.sqrt((Math.pow(whiteCubeViewHalfSize, 2) + Math.pow(whiteCubeViewHalfSize, 2))) * 0.03);
        whiteCubeHalfSize = cubeSize / 2;

        // Размер тени кубика
        // Размер тени
        int shadowViewSize = resources.getDimensionPixelSize(R.dimen.white_shadow_view_size);
        whiteShadowViewHalfSize = shadowViewSize / 2;

        // Базовые радиусы
        settingButtonRadius = settingSize / 2 + settingPadding;
        whiteCubeInnerRadius = cubeSize;
        whiteCubeOuterRadius = whiteCubeViewHalfSize;
        whiteShadowRadius = (int) Math.sqrt((Math.pow(whiteShadowViewHalfSize, 2) + Math.pow(whiteShadowViewHalfSize, 2)));





        Log.d("myLog", "screenWidth = " + screenWidth);
        Log.d("myLog", "screenHeight = " + screenHeight);
        Log.d("myLog", "---------------------------");
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public Random getRandom() {
        return random;
    }

    public int getSettingButtonRadius() {
        return settingButtonRadius;
    }





    public Sizes getSizes(Skin skin) {
        // Возвращает комплект размеров
        // в соответствии с цветом/скином кубика
        switch (skin) {
            case WHITE:
                return new Sizes(screenWidth, screenHeight, whiteShadowRadius, whiteCubeHalfSize, whiteCubeViewHalfSize, whiteShadowViewHalfSize, whiteCubeInnerRadius, whiteCubeOuterRadius);
            case BLACK:
                return new Sizes();
            case RED:
                return new Sizes();
            default:
                return new Sizes();
        }
    }
}
