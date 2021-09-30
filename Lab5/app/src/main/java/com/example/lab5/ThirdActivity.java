package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Bundle arguments = getIntent().getExtras();
        // получение информации из предыдущего активити
        User passedUser = (User) arguments.getSerializable(User.class.getSimpleName());
        String userInformation =
                "Имя: " + passedUser.getName() + "; пароль: " + passedUser.getPassword();
        // передача заголовка, и текста уведомления
        showNotification("Успех", userInformation);
    }
    // переопределение кнопки back
    @Override
    public void onBackPressed() {
        EditText editText = findViewById(R.id.editText);
        sendMessage(editText.getText().toString());
        super.onBackPressed();
    }
    // отображение диалогового окна
    public void showDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "second");
    }
    // отображение уведомления
    private void showNotification(String title, String text){
        //  создает макет уведомления
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,"default");
        // класс для уведомления пользователя о происходящих событиях
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        // экземпляр пользовательского класса MyNotification
        MyNotification mNotification = new MyNotification(builder, notificationManager);
        // показывает уведомление
        mNotification.showNotification(title, text);
    }
    // возвращение сообщение на предыдущее активити
    private void sendMessage(String message){
        Intent data = new Intent();
        data.putExtra(MainActivity.MESSAGE, message);
        setResult(RESULT_OK, data);
    }

}