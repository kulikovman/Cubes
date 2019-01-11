package ru.kulikovman.cubes.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import ru.kulikovman.cubes.R;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.databinding.ViewCubeBinding;
import ru.kulikovman.cubes.model.Cube;


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
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CubeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // Конструктор для генерации кубика через код
    public CubeView(@NonNull Context context, Cube cube) {
        super(context);
        init(context, cube);
    }

    private void init(Context context, Cube cube) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_cube, this);

        if (!isInEditMode()) {
            // Подключение биндинга
            binding = DataBindingUtil.bind((findViewById(R.id.cube_view_container)));

            // Ставим значения
            setCube(cube);
        }
    }

    public void setCube(Cube cube) {
        skin = cube.getSkin();
        value = cube.getValue();
        angle = cube.getAngle();
        marginStart = cube.getMarginStart();
        marginTop = cube.getMarginTop();

        // Отрисовка кубика
        drawCube();
    }

    private void drawCube() {
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
