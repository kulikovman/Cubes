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
    public boolean isShadow;
    public boolean isSelected;
    public int marginStart;
    public int marginTop;

    public CubeView(@NonNull Context context) {
        super(context);
    }

    // Конструкторы для создания кубика через макет
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

    // Конструктор для генерации кубика через код
    public CubeView(@NonNull Context context, Cube cube) {
        super(context);
        init(context, cube);
    }

    private void init(Context context) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_cube, this);
    }

    private void init(Context context, Cube cube) {
        // Инициализация
        init(context);

        if (!isInEditMode()) {
            // Подключение биндинга
            binding = DataBindingUtil.bind((findViewById(R.id.cube_view_container)));

            // Ставим значения
            setCube(cube);
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        // Инициализация
        init(context);

        // Получение значений из аттрибутов вью
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CubeView, defStyleAttr, defStyleRes);
        value = a.getInt(R.styleable.CubeView_value, 1);
        angle = a.getInt(R.styleable.CubeView_angle, 0);
        isShadow = a.getBoolean(R.styleable.CubeView_shadow, false);
        isSelected = a.getBoolean(R.styleable.CubeView_selected, false);
        skin = Skin.values()[a.getInt(R.styleable.CubeView_skin, 0)];
        a.recycle();

        if (!isInEditMode()) {
            // Подключение биндинга
            binding = DataBindingUtil.bind((findViewById(R.id.cube_view_container)));

            // Отрисовка кубика
            drawCube();
        }
    }

    public void setCube(Cube cube) {
        skin = cube.getSkin();
        value = cube.getValue();
        angle = cube.getDegrees();
        marginStart = cube.getMarginStart();
        marginTop = cube.getMarginTop();

        // Отрисовка кубика
        drawCube();
    }

    private void drawCube() {
        // Назначение картинок в соответствии с цветом
        String skinName = skin.name().toLowerCase();
        binding.cube.setImageResource(getDrawableIdByName(skinName + "_cube"));
        binding.dots.setImageResource(getDrawableIdByName(skinName + "_dot_" + String.valueOf(value)));

        // Показываем тень, если указана
        // Это собственная тень кубика, для отображения через макет
        // Для кубиков на поле отображаться не должна, там свои тени в другом слое
        if (isShadow) {
            binding.shadow.setImageResource(getDrawableIdByName(skinName + "_shadow"));
            binding.shadow.setVisibility(VISIBLE);
        }

        // Показываем маркер, если есть
        binding.selection.setVisibility(isSelected ? VISIBLE : INVISIBLE);

        // Обновление переменной в макете
        binding.setModel(this);
    }

    private int getDrawableIdByName(String resourceName) {
        return getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    public void setChooseMarker(boolean isSelected) {
        // Показываем маркер
        this.isSelected = isSelected;

        // Показываем маркер, если есть
        binding.selection.setVisibility(isSelected ? VISIBLE : INVISIBLE);
    }

    public String getCubeColor() {
        return skin.name();
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
