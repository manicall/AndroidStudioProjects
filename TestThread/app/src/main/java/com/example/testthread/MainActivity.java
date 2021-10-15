 package com.example.testthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

 public class MainActivity extends Activity implements View.OnTouchListener,
         SurfaceHolder.Callback {

     private SurfaceView mSurface;
     private DrawingThread mThread;

     /** Called when the activity is first created. */
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         setContentView(R.layout.activity_main);

         mSurface = (SurfaceView) findViewById(R.id.surface);
         mSurface.setOnTouchListener(this);
         mSurface.getHolder().addCallback(this);
     }

     public void onClick(View v) {
         mThread.clearItems();
     }

     public boolean onTouch(View v, MotionEvent event) {
         if (event.getAction() == MotionEvent.ACTION_DOWN) {
             mThread.addItem((int) event.getX(), (int) event.getY());
         }
         return true;
     }

     @Override
     public void surfaceCreated(SurfaceHolder holder) {
         mThread = new DrawingThread(holder, BitmapFactory.decodeResource(
                 getResources(), R.mipmap.ic_launcher));
         mThread.start();
     }

     @Override
     public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                int height) {
         mThread.updateSize(width, height);
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
                     // Очистка Canvas
                     //c.drawColor(Color.BLACK);
                     // Прорисовка каждого объекта
                     for (DrawingItem item : mLocations) {
                         // Обновление положения объекта
                         item.x += (item.horizontal ? 10 : -10);
                         if (item.x >= (mDrawingWidth - mIcon.getWidth())) {
                             item.horizontal = false;
                         } else if (item.x <= 0) {
                             item.horizontal = true;
                         }
                         item.y += (item.vertical ? 10 : -10);
                         if (item.y >= (mDrawingHeight - mIcon.getHeight())) {
                             item.vertical = false;
                         } else if (item.y <= 0) {
                             item.vertical = true;
                         }
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
 }
