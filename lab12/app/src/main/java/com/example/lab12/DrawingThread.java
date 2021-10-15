package com.example.lab12;

//******************  Класс потока рисования ********************************

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class DrawingThread extends HandlerThread implements Handler.Callback {
    private static final int MSG_ADD = 100;
    private static final int MSG_MOVE = 101;

    private SurfaceHolder mDrawingSurface;
    private Handler mReceiver;
    private Bitmap mIcon;
    private ArrayList<Position> mLocations;
    private int width;
    private int height;

    // Позиция изображения
    private class Position {
        int x, y; // текущая позиция

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //Конструктор потока рисования. Создает перо и список объектов рисования
    public DrawingThread(SurfaceHolder holder, Bitmap icon) {
        super("DrawingThread");
        // ширина и высота
        width = holder.getSurfaceFrame().right;
        height = holder.getSurfaceFrame().bottom;

        mDrawingSurface = holder; // предоставляет холст для отрисовки
        mLocations = new ArrayList<>();
        mIcon = icon; // снежинка
    }

    // Метод onLooperPrepared вызывается системой
    // один раз перед началом цикла рисования
    @Override
    protected void onLooperPrepared() {
        // установка обработчика сообщений
        mReceiver = new Handler(getLooper(), this);
        // Начало отрисовки
        mReceiver.sendEmptyMessage(MSG_MOVE);
    }

    @Override
    public boolean quit() {
        // Очистить сообщения перед остановкой
        mReceiver.removeCallbacksAndMessages(null);
        return super.quit();
    }

    //Обработчик сообщений
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_ADD:
                // Создание новой снежинки
                Position newItem = new Position(msg.arg1, msg.arg2);
                mLocations.add(newItem);
                break;
            case MSG_MOVE:
                // Получить Сanvas и заблокировать его
                Canvas canvas = mDrawingSurface.lockCanvas();
                if (canvas == null) break;
                // очистка экрана
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                // Прорисовка каждого объекта
                for (Position item : mLocations) {
                    final int OFFSET = 150;
                    final int SPEED = 20;
                    if (item.y > height) {
                        item.x = rnd(OFFSET, width - OFFSET);
                        item.y = -OFFSET;
                    }
                    Log.d("TAG", "handleMessage: " + mLocations.size() + " " + item.y);
                    item.y += SPEED;

                    // Прорисовка на Canvas
                    canvas.drawBitmap(mIcon, item.x, item.y, new Paint());
                }
                // Разрешить перерисовку экрана
                mDrawingSurface.unlockCanvasAndPost(canvas);
                break;
        }
        mReceiver.sendEmptyMessage(MSG_MOVE);
        return true;
    }

    public void addItem(int x, int y) {
        // создание сообщения для добавления
        // изображения с заданными координатами
        Message msg = Message.obtain(mReceiver, MSG_ADD, x, y);
        mReceiver.sendMessage(msg);
    }

    // функция возвращаяющая случайное число в заданном диапазоне
    static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}