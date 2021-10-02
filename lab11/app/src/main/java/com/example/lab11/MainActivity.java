package com.example.lab11;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> directories;
    File currentPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentPath = new File (Helper.getSettings(this));
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinnerSetAdapter(spinner, currentPath);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                if (!item.equals(currentPath.toString())) {
                    if (item.equals("/..")) {
                        currentPath = new File(currentPath.getParent());
                    } else {
                        currentPath = new File(item);
                    }
                    spinnerSetAdapter(spinner, currentPath);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button copyButton = findViewById(R.id.copyButton);
        copyButton.setOnClickListener(view -> {
            try {
                copyFileUsingStream(getFilesDir(), currentPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Helper.setSettings(this, currentPath.toString());
    }

    private static void spinnerSetAdapter(Spinner spinner, File currentPath){
        //Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(),
                android.R.layout.simple_spinner_item, getDirList(currentPath));
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
    }

    private static ArrayList<String> getDirList(File currentDir){
        ArrayList<String> dirNames = new ArrayList<>();
        dirNames.add(currentDir.toString());
        if (!currentDir.equals(Environment.getExternalStorageDirectory())){
            dirNames.add("/..");
        }

        String[] subFiles = currentDir.list((file, s) -> new File(file.toString() + "/" + s).isDirectory());
        for (String subFile : subFiles) dirNames.add(currentDir + "/" + subFile);
        return dirNames;
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;

        File [] files = source.listFiles(file -> file.isFile());
        String[] subFiles = source.list((file, s) -> new File(file.toString() + "/" + s).isFile());

        for (String subFile : subFiles) {
            try {
                Log.d("Files", "copyFileUsingStream: " + subFile);
                is = new FileInputStream(source.toString() + "/" + subFile);
                os = new FileOutputStream(dest.toString() + "/" + subFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
            }
        }
    }

}