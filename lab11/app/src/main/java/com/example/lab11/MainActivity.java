package com.example.lab11;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> directories;
    File currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Properties properties = Helper.getConfigProperties(this, "path");

        currentPath = Environment.getExternalStorageDirectory();// new File(properties.getProperty("path"));

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinnerSetAdapter(spinner, currentPath);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
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
                    Log.d("Files", "onItemSelected: " + item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);




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

        File[] subFiles = currentDir.listFiles();
        for (File subFile : subFiles){
            if(subFile.isDirectory()){
                dirNames.add(subFile.toString());
            }
        }
        return dirNames;
    }



/*        Button copyButton = findViewById(R.id.copyButton);
        // обработка нажатия на кнопку
        copyButton.setOnClickListener(view -> {
            File filesDirPath = getFilesDir();
            File sdcardPath =
                    new File(Environment.getExternalStorageDirectory().toString() +
                            editText.getText());

            *//*try {
                copy(filesDirPath, sdcardPath);
            } catch (IOException e) {
                e.printStackTrace();
            }*//*
        });*/


    public static void printContent(File file) {
        String path = file.toString();
            Log.d("Files","Path: "+path);
        File directory = new File(path);
        File[] files = directory.listFiles();
            Log.d("Files","Size: "+files.length);
            for(
        int i = 0;
        i<files.length;i++)

        {
            Log.d("Files", "FileName:" + files[i].getName());
        }

    }

    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}