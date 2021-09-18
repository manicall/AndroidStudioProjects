package com.example.lab6;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  implements CalendarFragment.OnFragmentSendDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSendData(String selectedItem) {
        // получаем фрагмент который получит данные
        BackgroundFragment fragment = (BackgroundFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailFragment);
        // отправляем данные в получателя с помощью определенного в нем метода
        if (fragment != null)
            fragment.setSelectedItem(selectedItem);
    }
}