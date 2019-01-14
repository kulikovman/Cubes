package ru.kulikovman.cubes.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import ru.kulikovman.cubes.R;
import ru.kulikovman.cubes.data.Skin;
import ru.kulikovman.cubes.databinding.ViewShadowBinding;
import ru.kulikovman.cubes.model.Cube;

public class ShadowView  extends FrameLayout {

    private ViewShadowBinding binding;
    private Context context;

    private Skin skin;
    public int angle;
    public int marginStart;
    public int marginTop;

    public ShadowView(@NonNull Context context) {
        super(context);
    }

    public ShadowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShadowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ShadowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // Конструктор для генерации кубика через код
    public ShadowView(@NonNull Context context, Cube cube) {
        super(context);
        init(context, cube);
    }

    private void init(Context context, Cube cube) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_shadow, this);

        if (!isInEditMode()) {
            // Подключение биндинга
            binding = DataBindingUtil.bind((findViewById(R.id.shadow_view_container)));

            // Ставим значения
            setCube(cube);
        }
    }

    public void setCube(Cube cube) {
        skin = cube.getSkin();
        angle = cube.getDegrees();
        marginStart = cube.getShadowMarginStart();
        marginTop = cube.getShadowMarginTop();

        // Отрисовка кубика
        drawShadow();
    }

    private void drawShadow() {
        // Назначение тени в соответствии с цветом
        String skinName = skin.name().toLowerCase();
        binding.shadow.setImageResource(getDrawableIdByName(skinName + "_shadow"));

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
