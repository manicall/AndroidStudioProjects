package com.example.lab12;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
public class SnowFlakeService extends Service {
    private WindowManager windowManager;
    private ImageView snowFlake;

    public void onCreate() {
        super.onCreate();
        snowFlake = new ImageView(this);
        //a face floating bubble as imageView
        snowFlake.setImageResource(R.drawable.ic_snowflake);

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        //here is all the science of params
        final LayoutParams myParams = new WindowManager.LayoutParams(
                100,
                100,
                500,
                500,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        myParams.gravity = Gravity.TOP | Gravity.LEFT;


        // add a floatingfacebubble icon in window
        windowManager.addView(snowFlake, myParams);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (myParams.y < 1000) {
                    myParams.y += 5;
                    SystemClock.sleep(50);
                    // отображаем в текстовом поле snowFlake.
                    snowFlake.post(new Runnable() {
                        public void run() {
                            windowManager.updateViewLayout(snowFlake, myParams);
                            snowFlake.invalidate();
                        }
                    });
                }
            }
        };
        // Определяем объект Thread - новый поток
        Thread thread = new Thread(runnable);
        // Запускаем поток
        thread.start();


        try{
            //for moving the picture on touch and slide
            snowFlake.setOnTouchListener(new View.OnTouchListener() {
                WindowManager.LayoutParams paramsT = myParams;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private long touchStartTime = 0;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //remove face bubble on long press
                    if(System.currentTimeMillis()-touchStartTime>ViewConfiguration.getLongPressTimeout() && initialTouchX== event.getX()){
                        windowManager.removeView(snowFlake);
                        stopSelf();
                        return false;
                    }
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            touchStartTime = System.currentTimeMillis();
                            initialX = myParams.x;
                            initialY = myParams.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            myParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                            myParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(v, myParams);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}