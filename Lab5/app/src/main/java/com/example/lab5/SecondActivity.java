package com.example.lab5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    static final String ACCESS_MESSAGE = "ACCESS_MESSAGE";
    public static ActivityResultLauncher<Intent> mStartForResult;
    private static User maximIvanov = new User("Admin", "Admin");

    public static User getUser() {
        return maximIvanov;
    }

    static void activityLaunch(Intent intent) {
        mStartForResult.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle arguments = getIntent().getExtras();
        // получение информации из предыдущего активити
        User passedUser = (User) arguments.getSerializable(User.class.getSimpleName());
        String userInformation =
                "Имя: " + passedUser.getName() + "; пароль: " + passedUser.getPassword();
        // передача заголовка, и текста уведомления
        showNotification("Успех", userInformation);
        // для регистрации возврата результата из следующего активити
        mStartForResult = registerStartForResult();
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

    private ActivityResultLauncher<Intent> registerStartForResult(){
        // метод регистрирующий функцию, которая будет обрабатывать результат
        return registerForActivityResult(
                // в качестве входного объекта устанавливает объект Intent
                // а в качестве типа результата - тип ActivityResult
                new ActivityResultContracts.StartActivityForResult(),
                // обработка полученного результата
                result -> {
                    Intent intent = result.getData();
                    // текст который вернулся из следующего активити
                    String message = intent.getStringExtra(ACCESS_MESSAGE);
                    if (!message.isEmpty()) {
                        switch (result.getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(
                                        SecondActivity.this,
                                        message,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(
                                        SecondActivity.this,
                                        "Ошибка возврата результата",
                                        Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        showNotification(
                                "Ошибка", "Поле для возврата результата оказалось пустым");
                    }
                });
    }
}