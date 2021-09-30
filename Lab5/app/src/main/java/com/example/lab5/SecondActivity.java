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
        User passedUser = (User) arguments.getSerializable(User.class.getSimpleName());
        String userInformation =
                "Имя: " + passedUser.getName() + "; пароль: " + passedUser.getPassword();

        showNotification("Успех", userInformation);
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
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,"default");
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        MyNotification mNotification = new MyNotification(builder, notificationManager);
        mNotification.showNotification(title, text);
    }
    //
    private void sendMessage(String message){
        Intent data = new Intent();
        data.putExtra(MainActivity.MESSAGE, message);
        setResult(RESULT_OK, data);
    }

    private ActivityResultLauncher<Intent> registerStartForResult(){
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent intent = result.getData();
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