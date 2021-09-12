package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ConstraintLayout constraint = findViewById(R.id.constraint);
        Intent Intent = new Intent(this, ThirdActivity.class);
        constraint.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeftToRight() {
                finish();
                overridePendingTransition(Animations.BACK_TO_MAIN_ENTER, Animations.BACK_TO_MAIN_EXIT);
            }
            @Override
            public void onSwipeRigthToLeft() {
                startActivity(Intent);
                overridePendingTransition(Animations.NEXT_TO_THIRD_ENTER, Animations.NEXT_TO_THIRD_EXIT);
            }
        });
    }
}