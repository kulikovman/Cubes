package ru.kulikovman.cubes.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Random;

import ru.kulikovman.cubes.data.Skin;

public class Cube {

    private Skin skin;
    private int value;
    private int angle;
    private int x;
    private int y;

    public Cube(Skin skin, int x1, int x2, int y1, int y2) {


        // Генератор случайных чисел
        Random random = new Random();

        // Цвет кубика
        if (skin == Skin.RANDOM) {
            int skinIndex = 1 + random.nextInt(Skin.values().length); // случайный цвет
            this.skin = Skin.values()[skinIndex];
        } else {
            this.skin = skin;
        }

        // Количество точек
        value = 1 + random.nextInt(6); // от 1 до 6

        // Угол поворота
        angle = random.nextInt(360); // от 0 до 359

        // Расположение на экране




        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int diceSize = convertDpToPx(140);
        int buttonPanelHeight = convertDpToPx(90);
        mWidth = displayMetrics.widthPixels - diceSize;
        mHeight = displayMetrics.heightPixels - diceSize - buttonPanelHeight - getStatusBarHeight();*/

    }
}
