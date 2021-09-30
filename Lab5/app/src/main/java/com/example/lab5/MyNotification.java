package com.example.lab5;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyNotification {
    private static int id = 0; // счетчик созданных экземпляров текущего класса
    int notifyId; // идентификатор уведомления
    String channelId; // идентификатор канала

    //  создает макет уведомления
    NotificationCompat.Builder builder;
    // класс для уведомления пользователя о происходящих событиях
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
        /*проверка андроид версии устройства,
        на котором будет вызвано уведомление  */
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
        // создание уведомления с указанным идентификатором и
        // с установленными параметрами
        notificationManager.notify(notifyId,builder.build());
    }
}
