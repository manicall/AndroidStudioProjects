package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle arguments = getIntent().getExtras();

        User user;
        if(arguments!=null) {
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            String userInformation = "Имя: " + user.getName() + " пароль: " + user.getPassword();
            Toast.makeText(this, userInformation, Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "second");
    }
}