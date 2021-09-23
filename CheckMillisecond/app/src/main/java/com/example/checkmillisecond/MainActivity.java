package com.example.checkmillisecond;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.checkmillisecond.Chronometer;
import com.example.checkmillisecond.R;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Chronometer mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer.start();

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().toString().equalsIgnoreCase("00:05:0")) { //When reaches 5 seconds.
                    //Define here what happens when the Chronometer reaches the time above.
                    chronometer.stop();
                    Toast.makeText(getBaseContext(),"Reached the end.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}