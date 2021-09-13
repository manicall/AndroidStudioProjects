package com.example.firstapplication_lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // вызов
        setContentView(R.layout.activity_main); // установка
    }

    // обработка нажатия на кнопку
    public void onClick(View view) {
        TextView textView = findViewById(R.id.textView);
        String Number =  Integer.toString(rnd(0, 1000000)); // случайное число
        /*настройка элемента textview*/
        textView.setText(Number);
        textView.setTextColor(0);
        textView.setBackgroundColor(getResources().getColor(R.color.yellow));
        textView.setTextColor(Color.argb(255, 255, 0, 0));
        textView.setTextSize(100);
    }
    // функция возвращаяющая случайное число в заданном диапазоне
    static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}