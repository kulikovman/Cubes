package ru.kulikovman.cubes.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import ru.kulikovman.cubes.R;


public class RateDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Получаем макет
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rateMessage = inflater.inflate(R.layout.rate_message, null);

        // Подключаем кнопки
        Button buttonRate = rateMessage.findViewById(R.id.button_rate);
        Button buttonNoThanks = rateMessage.findViewById(R.id.button_no_thanks);

        // Слушатели кнопок
        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переадресация на страницу приложения в маркете
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=ru.kulikovman.dices"));
                startActivity(intent);
            }
        });

        buttonNoThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Закрываем диалог
                dismiss();
            }
        });

        // Формируем диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rateMessage);

        return builder.create();
    }
}
