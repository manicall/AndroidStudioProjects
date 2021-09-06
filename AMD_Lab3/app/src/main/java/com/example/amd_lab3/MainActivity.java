package com.example.amd_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.amd_lab3.R;

public class MainActivity extends AppCompatActivity {

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
        setTitle(headerView, item);
        item.setChecked(!item.isChecked());

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