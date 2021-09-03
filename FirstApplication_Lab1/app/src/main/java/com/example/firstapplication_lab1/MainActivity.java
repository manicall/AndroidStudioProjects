package com.example.firstapplication_lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        TextView textView = findViewById(R.id.textView);
        String Number =  Integer.toString(rnd(0, 1000000));
        textView.setText(Number);
        textView.setTextColor(0);
        textView.setBackgroundColor(getResources().getColor(R.color.yellow));
        int color = Color.argb(255, 255, 0, 0);
        textView.setTextColor(color);
        textView.setTextSize(100);
    }

    static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}