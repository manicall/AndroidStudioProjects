package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener {
    boolean mRotate; // определяет будет ли вращаться текст

    FrameLayout frameLayout;
    TextView textView;

    Animation.AnimationListener outListener;
    Animation.AnimationListener inListener;

    MyRotationControl rotationControl = new MyRotationControl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.setOnTouchListener(this);

        setOutListener();
        setInListener();

        rotationControl.getRotateAhead().setAnimationListener(inListener);
        rotationControl.getRotateBackward().setAnimationListener(outListener);

    }

    private Animation.AnimationListener getListener(RotateAnimation rotateAnimation) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (mRotate)
                    textView.startAnimation(rotateAnimation);
                rotationControl.setAndApplyDuration();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        };
    }

    public void setInListener() {
        inListener = getListener(rotationControl.getRotateBackward());
    }

    public void setOutListener() {
        outListener = getListener(rotationControl.getRotateAhead());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRotate = !mRotate;
                if (mRotate) {
                    rotationControl.setAndApplyDuration();
                    textView.startAnimation(rotationControl.getRotateBackward());
                }
                break;
        }
        return true;
    }
}

