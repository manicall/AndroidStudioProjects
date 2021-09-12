package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        ConstraintLayout constraint = findViewById(R.id.constraint);
        constraint.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeftToRight() {
                finish();
                overridePendingTransition(Animations.BACK_TO_SECOND_ENTER, Animations.BACK_TO_SECOND_EXIT);
            }
            @Override
            public void onSwipeRigthToLeft() {
            }
        });
    }

}