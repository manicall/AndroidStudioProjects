package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // виджет шкалы закрузки
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.circularProgressIndicator);
        // виджет ползунка
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        // виджет текстового поля
        TextView textView = (TextView) findViewById(R.id.textView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int minimumValue = -30;
            // текущий прогресс со смещением
            int progressChanged = minimumValue;

            // вызывается при перемещении ползунка
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = minimumValue + progress;
                // установка текста в текстовое поле
                textView.setText(String.valueOf(progressChanged));
                // установка прогресса в шкалу загрузки
                progressBar.setProgress(progress);
            }
            // вызывается при нажатии на ползунок
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            // вызывается при отпускании ползунка
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}