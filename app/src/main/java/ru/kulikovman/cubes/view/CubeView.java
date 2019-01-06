package ru.kulikovman.cubes.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.Random;

import ru.kulikovman.cubes.R;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.databinding.ViewCubeBinding;


public class CubeView extends FrameLayout {

    private ViewCubeBinding binding;
    private Context context;

    private int value;
    public int angle;
    private Skin skin;

    public CubeView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        // Получение значений из аттрибутов вью
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CubeView, defStyleAttr, defStyleRes);
        value = a.getInt(R.styleable.CubeView_value, 1);
        angle = a.getInt(R.styleable.CubeView_angle, 0);
        skin = Skin.values()[a.getInt(R.styleable.CubeView_skin, 1)];
        a.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_cube, this);

        if (!isInEditMode()) {
            this.context = context;

            // Подключение биндинга
            binding = DataBindingUtil.bind((findViewById(R.id.cube_view_container)));

            // Отрисовка кубика
            initCube();

            // Обновление переменной в макете
            binding.setModel(this);
        }
    }

    private void initCube() {
        // Выбор цвета кубика
        String skinName;
        if (skin == Skin.RANDOM) {
            Random random = new Random();
            int skinIndex = 1 + random.nextInt(Skin.values().length); // случайный цвет
            skinName = String.valueOf(skinIndex);
        } else {
            skinName = skin.name().toLowerCase();
        }

        // Назначение картинок в соответствии с цветом
        binding.cube.setImageResource(getDrawableIdByName(skinName + "_cube"));
        binding.shadow.setImageResource(getDrawableIdByName(skinName + "_shadow"));
        binding.dots.setImageResource(getDrawableIdByName(skinName + "_dot_" + String.valueOf(value)));
    }

    private int getDrawableIdByName(String resourceName) {
        return getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }
}
