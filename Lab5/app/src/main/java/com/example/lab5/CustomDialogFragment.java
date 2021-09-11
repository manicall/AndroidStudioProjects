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
    Dialog dialog = null;
    User user = null;

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
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialog = (Dialog) dialogInterface;
                        startNewActivity(getActivityClass());
                    }
                })
                .setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //вызов активити
                        Toast.makeText(getContext(), "Ввод пользователя отменен", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
    }

    // получить класс активити для запуска
    public Class getActivityClass(){
        Class activityClass = null;
        switch (getTag()) {
            case "first":
                if (isUserExist(MainActivity.getUser())) {
                    activityClass = SecondActivity.class;
                }
                break;
            case "second":
                if (isUserExist(MainActivity.getUser())) { /*!!! исправить на SecondActivity !!!*/
                    activityClass = ThirdActivity.class;
                }
                break;
        }
        return activityClass;
    }
    //запустить новое активити с передачей ему данных пользователя
    public void startNewActivity(Class activityClass){
        if (activityClass == null) {
            Toast.makeText(getContext(), "Доступ запрещен", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getActivity(), activityClass);
            intent.putExtra(User.class.getSimpleName(), user);
            startActivity(intent);
            String s = (String) getActivity().getLocalClassName();
        }

    }
    // проверить существует ли пользователь
    public boolean isUserExist(User existingUser) {
        String name = getTextViewData(R.id.name);
        String password = getTextViewData(R.id.password);

        user = new User(name, password);
        return existingUser.isEqual(user);
    }
    // получить информацию из поля TextView
    public String getTextViewData(int id){
        return ((EditText) dialog.findViewById(id)).getText().toString();
    }
}