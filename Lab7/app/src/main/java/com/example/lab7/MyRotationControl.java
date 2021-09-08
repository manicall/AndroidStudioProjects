package com.example.lab7;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

class MyRotationControl {
    private static final boolean UP = false;
    private static final boolean DOWN = true;
    private static final int DX = 500;
    private static final int max = 3000;
    private static final int min = 1000;
    private boolean direction; // определяет, ускоряется или замедляется анимация
    private int duration;

    private RotateAnimation rotateAhead;
    private RotateAnimation rotateBackward;

    public MyRotationControl() {
        direction = DOWN;
        duration = 3000;

        rotateAhead = new RotateAnimation(0.0f,
                360.0f, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF,
                0.5f
        );

        rotateBackward = new RotateAnimation(0.0f,
                -360.0f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );

    }

    public RotateAnimation getRotateAhead() {
        return rotateAhead;
    }

    public RotateAnimation getRotateBackward() {
        return rotateBackward;
    }

    public void setAndApplyDuration() {
        setDuration();
        applyDuration();
    }

    public void setAndApplyDuration(int newDuration) {
        setDuration(newDuration);
        applyDuration();
    }

    public int getDuration() {
        return duration;
    }

    private void applyDuration() {
        rotateAhead.setDuration(duration);
        rotateBackward.setDuration(duration);
    }

    // автоматическое изменение длительности
    private void setDuration() {
        if (isWrongDirection()) {
            direction = !direction;
        }
        if (direction) {
            duration -= DX;
        } else {
            duration += DX;
        }
    }

    private void setDuration(int newDuration) {
        duration = newDuration;
    }

    // проверяет вышла ли длительность за допустимые границы
    private boolean isWrongDirection() {
        if (direction) {
            return duration < min; // минимально возможная длительность: min - DX
        } else {
            return duration > max; // максимально возможная длительность: max + DX
        }
    }
}

