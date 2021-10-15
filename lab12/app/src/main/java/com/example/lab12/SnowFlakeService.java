package com.example.lab12;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SnowFlakeService extends Service implements View.OnTouchListener,
        SurfaceHolder.Callback {
    private Thread thread;
    private WindowManager windowManager;
    private Display display;
    private static int width;   // deprecated
    private static int height;  // deprecated

    private SurfaceView surface;
    private Button button;
    private DrawingThread mThread;

    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        surface = new SurfaceView(getApplicationContext());
        button = new Button(getApplicationContext());

        LayoutParams surfaceParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        windowManager.addView(surface, surfaceParams);
        surface.setOnTouchListener(this::onTouch);
        //surface.setBackgroundColor(Color.TRANSPARENT);
        surface.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surface.getHolder().addCallback((SurfaceHolder.Callback) this);


        LayoutParams buttonParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        buttonParams.gravity = Gravity.TOP | Gravity.RIGHT;

        button.setText("Остановить службу");
        windowManager.addView(button, buttonParams);
        button.setOnClickListener(view -> stopService(new Intent(getBaseContext(),
                SnowFlakeService.class)));




        // определение потока добавляющего снежинки
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while (i < 15) {
                        mThread.addItem(rnd(0, width), -10);
                        SystemClock.sleep(1000);
                        i++;
                        // отображаем в текстовом поле s nowFlake.
                    }
                } catch (Exception e){ }
            }
        };
        // Определяем объект Thread - новый поток
        thread = new Thread(runnable);

    }


    // функция возвращаяющая случайное число в заданном диапазоне
    static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void onClick(View v) {
        mThread.clearItems();
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {


        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new DrawingThread(holder, BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_snow));
        mThread.start();
        /*if (!thread.isAlive())*/ thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        mThread.updateSize(width, height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (thread != null) {
            Thread dummy = thread;
            thread = null;
            dummy.interrupt();
        }
        windowManager.removeView(surface);
        windowManager.removeView(button);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.quit();
        mThread = null;
    }

//******************  Класс потока рисования ********************************

    private static class DrawingThread extends HandlerThread implements Handler.Callback {
        private static final int MSG_ADD = 100;
        private static final int MSG_MOVE = 101;
        private static final int MSG_CLEAR = 102;
        private int mDrawingWidth, mDrawingHeight;
        private SurfaceHolder mDrawingSurface;
        private Paint mPaint;
        private Handler mReceiver;
        private Bitmap mIcon;
        private ArrayList<DrawingItem> mLocations;


        // Класс (объект изображения) отрисовки
        private class DrawingItem {

            int x, y; // Current location marker
            boolean horizontal, vertical; // Direction markers for motion

            public DrawingItem(int x, int y, boolean horizontal,
                               boolean vertical) {
                this.x = x;
                this.y = y;
                this.horizontal = horizontal;
                this.vertical = vertical;
            }
        }

        //Конструктор потока рисования. Создает перо и список объектов рисования
        public DrawingThread(SurfaceHolder holder, Bitmap icon) {
            super("DrawingThread");
            mDrawingSurface = holder;
            mLocations = new ArrayList<DrawingItem>();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.STROKE);
            mIcon = icon;

        }

        //Метод onLooperPrepared вызывается системой один раз перед началом цикла //рисования
        @Override
        protected void onLooperPrepared() {
            mReceiver = new Handler(getLooper(), this);
            // Start the rendering
            mReceiver.sendEmptyMessage(MSG_MOVE);
        }


        @Override
        public boolean quit() {
            // Clear all messages before dying
            mReceiver.removeCallbacksAndMessages(null);
            return super.quit();
        }


        //Обработчик сообщений, поступающих от Activity
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ADD:
                    // Create a new item at the touch location,
                    // with a randomized start direction
                    DrawingItem newItem = new DrawingItem(msg.arg1, msg.arg2,
                            Math.round(Math.random()) == 0, Math.round(Math
                            .random()) == 0);
                    mLocations.add(newItem);
                    break;
                case MSG_CLEAR:
                    // Remove all objects
                    mLocations.clear();
                    break;
                case MSG_MOVE:
                    // Получить Сanvas и заблокировать его
                    Canvas c = mDrawingSurface.lockCanvas();
                    if (c == null) {
                        break;
                    }

                    c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    // Прорисовка каждого объекта
                    for (DrawingItem item : mLocations) {

                        if (item.y > height) {
                            item.x = rnd(0, width);
                            item.y = -10;
                        }
                        Log.d("TAG", "handleMessage: " + mLocations.size() + " " + item.y);
                        item.y += 20;

                        // Прорисовка на Canvas
                        c.drawBitmap(mIcon, item.x, item.y, mPaint);
                    }
                    // Разрешить перерисовку экрана
                    mDrawingSurface.unlockCanvasAndPost(c);
                    break;
            }
            // Post the next frame
            mReceiver.sendEmptyMessage(MSG_MOVE);
            return true;
        }

        public void updateSize(int width, int height) {
            mDrawingWidth = width;
            mDrawingHeight = height;
        }

        public void addItem(int x, int y) {
            // Pass the location into the Handler using Message arguments
            Message msg = Message.obtain(mReceiver, MSG_ADD, x, y);
            mReceiver.sendMessage(msg);
        }

        public void clearItems() {
            mReceiver.sendEmptyMessage(MSG_CLEAR);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
