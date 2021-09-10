package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    public void showDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "second");
    }
}