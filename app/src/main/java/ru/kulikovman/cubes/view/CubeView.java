package ru.kulikovman.cubes.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Random;

import ru.kulikovman.cubes.R;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.databinding.ViewCubeBinding;
import ru.kulikovman.cubes.model.Cube;
import ru.kulikovman.cubes.model.RollArea;


public class CubeView extends FrameLayout {

    private ViewCubeBinding binding;
    private Context context;

    private Skin skin;
    private int value;
    public int angle;
    public int marginStart;
    public int marginTop;

    public CubeView(@NonNull Context context) {
        super(context);
        init(context);
        initWithAttribute(null, 0, 0);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initWithAttribute(attrs, 0, 0);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initWithAttribute(attrs, defStyleAttr, 0);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initWithAttribute(attrs, defStyleAttr, defStyleRes);
    }

    // Конструктор для генерации кубика через код
    public CubeView(@NonNull Context context, Skin skin, RollArea rollArea) {
        super(context);
        init(context);
        initWithData(skin, rollArea);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_cube, this);

        if (!isInEditMode()) {
            this.context = context;

            // Подключение биндинга
            binding = DataBindingUtil.bind((findViewById(R.id.cube_view_container)));
        }
    }

    private void initWithAttribute(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        // Получение значений из аттрибутов вью
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CubeView, defStyleAttr, defStyleRes);
        value = a.getInt(R.styleable.CubeView_value, 1);
        angle = a.getInt(R.styleable.CubeView_angle, 0);
        skin = Skin.values()[a.getInt(R.styleable.CubeView_skin, 1)];
        a.recycle();

        if (!isInEditMode()) {
            // Отрисовка кубика
            createCube();
        }
    }

    private void initWithData(Skin skin, RollArea rollArea) {
        if (!isInEditMode()) {
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
            marginStart = rollArea.getMinX() + random.nextInt(rollArea.getMaxX() + 1);
            marginTop = rollArea.getMinY() + random.nextInt(rollArea.getMaxY() + 1);

            // Отрисовка кубика
            createCube();
        }
    }

    private void createCube() {
        // Назначение картинок в соответствии с цветом
        String skinName = skin.name().toLowerCase();
        binding.cube.setImageResource(getDrawableIdByName(skinName + "_cube"));
        binding.shadow.setImageResource(getDrawableIdByName(skinName + "_shadow"));
        binding.dots.setImageResource(getDrawableIdByName(skinName + "_dot_" + String.valueOf(value)));

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private int getDrawableIdByName(String resourceName) {
        return getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    public void setCube(Cube cube) {
        skin = cube.getSkin();
        value = cube.getValue();
        angle = cube.getAngle();
        marginStart = cube.getMarginStart();
        marginTop = cube.getMarginTop();

        // Отрисовка кубика
        createCube();
    }

    public Cube getCube() {
        return new Cube(skin, value, angle, marginStart, marginTop);
    }

    @BindingAdapter({"android:layout_marginStart"})
    public static void setMarginStart(View view, int margin) {
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.leftMargin = margin;
        view.setLayoutParams(params);
    }

    @BindingAdapter({"android:layout_marginTop"})
    public static void setMarginTop(View view, int margin) {
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.topMargin = margin;
        view.setLayoutParams(params);
    }
}
