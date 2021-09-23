package com.example.checkmillisecond;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.checkmillisecond.R;

public class MainActivity extends Activity {
    private boolean flag = false;
    private long timePassed;

    private Chronometer chronometer;
    private ImageButton buttonStart;
    private ImageButton buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.chronometer =  findViewById(R.id.chronometer);

        this.buttonStart = findViewById(R.id.button_start_pause);
        this.buttonStop = findViewById(R.id.button_pause);

        chronometer.setOnChronometerTickListener(new com.example.checkmillisecond.Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(com.example.checkmillisecond.Chronometer chronometer) {
                timePassed = chronometer.getBase() - SystemClock.elapsedRealtime();
            }
        });

        this.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = !flag;
                if (flag) {
                    doStart();
                } else {
                    doPause();
                }
            }
        });

        this.buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doReset();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("timePassed", timePassed);
        outState.putBoolean("flag", flag);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timePassed = savedInstanceState.getLong("timePassed");
        flag = savedInstanceState.getBoolean("flag");
        chronometer.setBase(SystemClock.elapsedRealtime() + timePassed);
        if (flag) {
            doStart();
        }
    }

    private void doStart() {
        chronometer.setBase(SystemClock.elapsedRealtime() + timePassed);
        chronometer.start();
        buttonStart.setImageResource(R.drawable.ic_pause);
    }
    private void doPause(){
        chronometer.stop();
        buttonStart.setImageResource(R.drawable.ic_play);
    }
    private void doReset(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        timePassed = 0;
        flag = false;
        doPause();
    }
}