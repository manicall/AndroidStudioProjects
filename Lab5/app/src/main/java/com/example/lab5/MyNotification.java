package com.example.lab5;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyNotification {
    private static int id = 0;
    int notifyId;
    String channelId;

    NotificationCompat.Builder builder;
    NotificationManager notificationManager;

    MyNotification(NotificationCompat.Builder builder, NotificationManager notificationManager){
        this.builder = builder;
        this.notificationManager = notificationManager;

        notifyId = ++id;
        channelId = Integer.toString(id);
        builder.setChannelId(channelId);
    }

    public void showNotification(String title, String text){
        builder
            .setSmallIcon(android.R.drawable.alert_dark_frame)
            .setContentTitle(title)
            .setContentText(text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "APP_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        notificationManager.notify(notifyId,builder.build());
    }
}
