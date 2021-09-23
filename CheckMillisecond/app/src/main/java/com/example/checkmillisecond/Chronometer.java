package com.example.checkmillisecond;


/*
 * The Android chronometer widget revised so as to count milliseconds
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;

import java.text.DecimalFormat;



public class Chronometer extends androidx.appcompat.widget.AppCompatTextView {
    @SuppressWarnings("unused")
    private static final String TAG = "Chronometer";

    public interface OnChronometerTickListener {
        void onChronometerTick(Chronometer chronometer);
    }

    private long base;
    private boolean mVisible;
    private boolean mStarted;
    private boolean mRunning;
    private OnChronometerTickListener mOnChronometerTickListener;

    private static final int TICK_WHAT = 2;

    private long timeElapsed;

    /*конструктор класса*/
    public Chronometer(Context context) {
        this (context, null, 0);
    }

    public Chronometer(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Chronometer(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);

        init();
    }

    public long getBase() {
        return base;
    }

    public void setBase(long base) {
        this.base = base;
        dispatchChronometerTick();
        updateText(SystemClock.elapsedRealtime());
    }

    // инициализируем секундомер системным временем
    private void init() {
        base = SystemClock.elapsedRealtime();
        updateText(base);
    }
    // устанавливаем слушатель на тик секудомера
    public void setOnChronometerTickListener(
            OnChronometerTickListener listener) {
        mOnChronometerTickListener = listener;
    }
    // активируем секундомер
    public void start() {
        mStarted = true;
        updateRunning();
    }
    // останавливаем секундомер
    public void stop() {
        mStarted = false;
        updateRunning();
    }
    // если главное окно активности отключается от оконного менеджера
    @Override
    protected void onDetachedFromWindow() {
        super .onDetachedFromWindow();
        mVisible = false;
        updateRunning();
    }
    // если изменилась видимость виджета
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super .onWindowVisibilityChanged(visibility);
        mVisible = visibility == VISIBLE;
        updateRunning();
    }
    // обновляем текст
    private synchronized void updateText(long now) {
        // разность между времени от запуска секундомера
        // и системным временем
        timeElapsed = now - base;

        DecimalFormat df = new DecimalFormat("00");

        // вычисляем часы
        int hours = (int)(timeElapsed / (3600 * 1000));
        int remaining = (int)(timeElapsed % (3600 * 1000));
        // вычисляем минуты
        int minutes = (int)(remaining / (60 * 1000));
        remaining = (int)(remaining % (60 * 1000));
        // вычисляем секунды
        int seconds = (int)(remaining / 1000);
        remaining = (int)(remaining % (1000));
        // вычисляем милисекунды
        int milliseconds = (int)(remaining / 10);
        // приводим полученные данные к строке,
        // правильного формата
        String text = "";
        text += df.format(hours) + ":";
        text += df.format(minutes) + ":";
        text += df.format(seconds) + ".";
        text +=  df.format(milliseconds);
        // устанавливаем количество пройденного времени
        setText(text);
    }

    private void updateRunning() {
        boolean running = mVisible && mStarted;
        if (running != mRunning) {
            if (running) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                //
                mHandler.sendMessageDelayed(Message.obtain(mHandler, TICK_WHAT), 10);
            } else {
                mHandler.removeMessages(TICK_WHAT);
            }
            mRunning = running;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message m) {
            if (mRunning) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                sendMessageDelayed(Message.obtain(this , TICK_WHAT),
                        10); // откладываем
            }
        }
    };

    void dispatchChronometerTick() {
        if (mOnChronometerTickListener != null) {
            mOnChronometerTickListener.onChronometerTick(this);
        }
    }


}