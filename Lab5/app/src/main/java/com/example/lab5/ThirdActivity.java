package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;
    // Идентификатор канала
    private static String CHANNEL_ID = "Cat channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Bundle arguments = getIntent().getExtras();

        User user;
        if(arguments!=null) {
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            String userInformation = "Имя: " + user.getName() + " пароль: " + user.getPassword();
            Toast.makeText(this, userInformation, Toast.LENGTH_SHORT).show();
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Check1");
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(ThirdActivity.this, CHANNEL_ID)
                                //.setSmallIcon(R.drawable.ic_pets_black_24dp)
                                .setContentTitle("Напоминание")
                                .setContentText("Пора покормить кота")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(ThirdActivity.this);
                notificationManager.notify(NOTIFY_ID, builder.build());
                Log.d("myApp", "Check2");
            }
        });

    }
}