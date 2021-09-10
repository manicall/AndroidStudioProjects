package com.example.amd_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.util.Log;

import com.example.amd_lab3.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView headerView = (TextView) findViewById(R.id.selectedMenuItem);
        // передача заголовка меню в текстовое поле
        setTitle(headerView, item);
        // смена состояния флага
        item.setChecked(!item.isChecked());

        switch (item.getItemId()){
            case R.id.save:
                Log.v(TAG, (String) item.getTitle());
                break;
            case R.id.open:
                Log.d(TAG, (String) item.getTitle());
                break;
            case R.id.cat:
                Log.i(TAG, (String) item.getTitle());
                break;
            case R.id.dog:
                Log.w(TAG, (String) item.getTitle());
                break;
            case R.id.parrot:
                Log.e(TAG, (String) item.getTitle());
                break;
            case R.id.hamster:
                Log.wtf(TAG, (String) item.getTitle());
                break;
            case R.id.cheese:
                try {
                    int var = 10 / 0;
                } catch (Exception e) {
                    Log.e(TAG, "Деление на ноль!", e);
                }
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    CharSequence getItemState(MenuItem item){
        return item.isChecked() == true ? "выбран" : "не выбран";
    }
    void setTitle(TextView headerView, MenuItem item){
        if (item.isCheckable()) {
            headerView.setText("заголовок меню: " + item.getTitle() + " состояние флажка: " + getItemState(item));
        } else {
            headerView.setText("заголовок меню: " + item.getTitle());
        }
    }

}

