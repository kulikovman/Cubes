package ru.kulikovman.cubes.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.kulikovman.cubes.R;


public class HelpMessageDialog extends DialogFragment {

    public static final String KEY_MESSAGE = "message";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String message = getArguments().getString(KEY_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ничего не делаем...

                    }
                });

        return builder.create();
    }
}
