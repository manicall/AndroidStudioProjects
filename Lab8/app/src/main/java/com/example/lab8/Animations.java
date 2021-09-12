package com.example.lab8;

public  class Animations {
    //переход с главного на второе активити
    static final int NEXT_TO_SECOND_ENTER = R.anim.activity_down_up_enter;
    static final int NEXT_TO_SECOND_EXIT  = R.anim.activity_down_up_exit;
    //переход со второго на главное активити
    static final int BACK_TO_MAIN_ENTER   = R.anim.activity_down_up_close_enter;
    static final int BACK_TO_MAIN_EXIT    = R.anim.activity_down_up_close_exit;
    //переход со второго на третье активити
    static final int NEXT_TO_THIRD_ENTER  = R.anim.right_in;
    static final int NEXT_TO_THIRD_EXIT   = R.anim.left_out;
    //переход с третьего на второе активити
    static final int BACK_TO_SECOND_ENTER = R.anim.diagonaltranslate;
    static final int BACK_TO_SECOND_EXIT  = R.anim.alpha;
}
