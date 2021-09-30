package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Bundle arguments = getIntent().getExtras();
        User passedUser = (User) arguments.getSerializable(User.class.getSimpleName());
        String userInformation =
                "Имя: " + passedUser.getName() + "; пароль: " + passedUser.getPassword();

        showNotification("Успех", userInformation);
    }

    @Override
    public void onBackPressed() {
        EditText editText = findViewById(R.id.editText);
        sendMessage(editText.getText().toString());
        super.onBackPressed();
    }

    public void showDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "second");
    }

    private void showNotification(String title, String text){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,"default");
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        MyNotification mNotification = new MyNotification(builder, notificationManager);
        mNotification.showNotification(title, text);
    }
    private void sendMessage(String message){
        Intent data = new Intent();
        data.putExtra(MainActivity.MESSAGE, message);
        setResult(RESULT_OK, data);
    }

}