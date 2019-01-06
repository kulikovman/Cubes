package ru.kulikovman.cubes.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import ru.kulikovman.cubes.R;
import ru.kulikovman.cubes.data.Color;
import ru.kulikovman.cubes.databinding.ViewCubeBinding;


public class CubeView extends FrameLayout {

    private ViewCubeBinding binding;
    private Context context;

    private int value;
    private int angle;
    private Color color;

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
        color = Color.values()[a.getInt(R.styleable.CubeView_angle, 0)];
        a.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_cube, this);

        if (!isInEditMode()) {
            // Подключение биндинга
            binding = DataBindingUtil.bind((findViewById(R.id.cube_view_container)));

            // Прочие настройки
            this.context = context;

            initCube();
        }
    }

    private void initCube() {
        // Установка картинки для выбранных параметров
        String cubeResourceName = color.name().toLowerCase() + "_cube";
        int cubeResourceId = getResources().getIdentifier(cubeResourceName, "drawable", context.getPackageName());
        binding.cube.setImageResource(cubeResourceId);

        String shadowResourceName = color.name().toLowerCase() + "_shadow";
        int shadowResourceId = getResources().getIdentifier(shadowResourceName, "drawable", context.getPackageName());
        binding.shadow.setImageResource(shadowResourceId);

        String dotsResourceName = color.name().toLowerCase() + "_dot_" + String.valueOf(value);
        int dotsResourceId = getResources().getIdentifier(dotsResourceName, "drawable", context.getPackageName());
        binding.dots.setImageResource(dotsResourceId);

        // Поворот


    }

}
