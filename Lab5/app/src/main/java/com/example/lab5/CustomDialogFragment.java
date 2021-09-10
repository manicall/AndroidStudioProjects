package com.example.lab5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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
                        Dialog dialg = (Dialog) dialog;

                        EditText nameText = dialg.findViewById(R.id.name);
                        EditText passwordText = dialg.findViewById(R.id.password);

                        String name = nameText.getText().toString();
                        String password = passwordText.getText().toString();

                        User user = new User(name, password);

                        Class activityClass;
                        switch (getTag()) {
                            case "first":
                                activityClass = SecondActivity.class;
                                break;
                            case "second":
                                activityClass = ThirdActivity.class;
                                break;
                            default:
                                activityClass = MainActivity.class;
                        }
                        
                        Intent intent = new Intent(getActivity(), activityClass);
                        intent.putExtra(User.class.getSimpleName(), user);
                        startActivity(intent);

                        String s = (String) getActivity().getLocalClassName();

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