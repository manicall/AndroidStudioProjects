package com.example.lab5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = "Приложение андроид";

        String button1String = "Войти";
        String button2String = "Отмена";


        return builder
                .setTitle(title)
                .setView(R.layout.dialog)
                .setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String s = (String) getActivity().getLocalClassName();
                        switch (getTag()) {
                            case "first":
                                startActivity(new Intent(getActivity(), SecondActivity.class));
                                break;
                            case "second":
                                startActivity(new Intent(getActivity(), ThirdActivity.class));
                                break;
                            }
                    }
                })
                .setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //вызов активити

                    }
                })
                .create();
    }
}