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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String MESSAGE = "MESSAGE";
    public static ActivityResultLauncher<Intent> mStartForResult;
    private static User maximIvanov = new User("Maxim", "123");

    public static User getUser() {
        return maximIvanov;
    }

    static void activityLaunch(Intent intent) {
        mStartForResult.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartForResult = registerStartForResult();
    }

    public void showDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "first");
    }

    private void showNotification(String title, String text) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "default1");
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        MyNotification mNotification = new MyNotification(builder, notificationManager);
        mNotification.showNotification(title, text);
    }
    private ActivityResultLauncher<Intent> registerStartForResult(){
       return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent intent = result.getData();
                    // текст который вернулся из второго активити
                    String message = intent.getStringExtra(MESSAGE);
                    if (!message.isEmpty()) {
                        switch (result.getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(
                                        MainActivity.this,
                                        message,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(
                                        MainActivity.this,
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