package com.example.viewslisteners_lab4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VerticalSeekBar;
import android.widget.VerticalSeekBar_Reverse;

public class MainActivity extends Activity {

    VerticalSeekBar verticalSeekBar=null;
    VerticalSeekBar_Reverse verticalSeekBar_Reverse=null;
    TextView vsProgress,vs_reverseProgress=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        verticalSeekBar=(VerticalSeekBar)findViewById(R.id.vertical_Seekbar);
        vs_reverseProgress=(TextView)findViewById(R.id.reverse_sb_progresstext);


        verticalSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                vsProgress.setText(progress+"");

            }
        });


    }
}