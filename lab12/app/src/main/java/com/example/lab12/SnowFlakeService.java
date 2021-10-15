package com.example.lab12;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class SnowFlakeService extends Service implements SurfaceHolder.Callback {
    private Thread thread;
    private WindowManager windowManager;
    private SurfaceView surface;
    private Button button;
    private DrawingThread mThread;

    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        createSurface();
        createButton();
    }

    // функция возвращаяющая случайное число в заданном диапазоне
    static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    // создание и запуск потоков при создании поверхности
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new DrawingThread(holder, BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_snow));
        thread = getSnowCreationThread();
        mThread.start();
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // уничтожение потока и элементов управления
    // при завершении работы службы
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

    void createSurface(){
        surface = new SurfaceView(getApplicationContext());
        LayoutParams surfaceParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        windowManager.addView(surface, surfaceParams);
        surface.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surface.getHolder().addCallback((SurfaceHolder.Callback) this);
    }
    void createButton(){
        button = new Button(getApplicationContext());

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
    }

    // создание и возврат потока добавляющего снежинки
    Thread getSnowCreationThread(){
        Runnable runnable = () -> {
            int i = 0;
            final int OFFSET = 150;
            final int SECOND = 1000;
            try {
                while (i < 15) { // создаем 15 снежинок
                    mThread.addItem(rnd(OFFSET, surface.getWidth() - OFFSET), -OFFSET);
                    SystemClock.sleep(SECOND); // с интервалом 1 сек.
                    i++;
                }
            } catch (Exception e){ }
        };
        return new Thread(runnable);
    }

    // завершение потока отрисовки при уничтожении поверхности
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.quit();
        mThread = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
