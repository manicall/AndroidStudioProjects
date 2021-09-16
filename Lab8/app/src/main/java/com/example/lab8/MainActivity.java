package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraint = findViewById(R.id.constraint);
        Intent Intent = new Intent(this, SecondActivity.class);
        constraint.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeftToRight() {

            }
            @Override
            public void onSwipeRigthToLeft() {
                startActivity(Intent);
                overridePendingTransition(Animations.NEXT_TO_SECOND_ENTER, Animations.NEXT_TO_SECOND_EXIT);
            }
        });
    }

}