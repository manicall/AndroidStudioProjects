package com.example.lab11;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> directories;
    File currentPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // создания файлов, для демонстрации выполнения копирования
        try {
            createFilesToCopy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // получаем путь к каталогу назначения из файла конфигурации
        currentPath = new File (Helper.getSettings(this));
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinnerSetAdapter(spinner, currentPath);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // обработка выбора элемента из списка
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                // проверяем что выбранный путь из списка не равен текущему пути
                if (!item.equals(currentPath.toString())) {
                    // если был выбран родительский каталог
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
        // обработка нажатия на кнопку копирования
        copyButton.setOnClickListener(view -> {
            try {
                copyFiles(getFilesDir(), currentPath);
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

    private void createFilesToCopy() throws IOException {
        String applicationFilesDir = getFilesDir().toString();
        for (int i = 1; i < 6; i++) {
            Log.d("Files", "createFilesToCopy: " + applicationFilesDir + "/example" + i + ".txt");
            FileOutputStream fos = new FileOutputStream (applicationFilesDir + "/example" + i + ".txt");
            String text = i + " This is the text";
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            fos.close();
        }
        for (int j = 1; j < 4; j++) {
            // создание директории
            File dir = new File(applicationFilesDir + "/exampleDir" + j);
            dir.mkdir();
            for (int i = 1; i < 6; i++) {
                FileOutputStream fos =  new FileOutputStream (dir.toString() + "/example" + i + ".txt");
                String text = j + i + " This is the text";
                fos.write(text.getBytes(StandardCharsets.UTF_8));
                fos.close();
            }
        }
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
        // хранит путь к каждому каталогу в директории переданной в параметре
        ArrayList<String> dirNames = new ArrayList<>();
        // передаем текущую директорию
        dirNames.add(currentDir.toString());
        // если текущая директория не является корневой,
        // то устанавливаем возможность перехода на родительский каталог
        if (!currentDir.equals(Environment.getExternalStorageDirectory())){
            dirNames.add("/..");
        }
        // получаем название всех каталогов
        String[] subFiles = currentDir.list((file, s) ->
                new File(file.toString() + "/" + s).isDirectory());
        // добавляем название всех каталогов в директории переданной в параметре
        for (String subFile : subFiles) dirNames.add(currentDir + "/" + subFile);
        return dirNames;
    }

    private static void copyFiles(File source, File dest) throws IOException {
        // файлы из источника
        String[] subFiles = source.list((file, s) ->
                new File(file.toString() + "/" + s).isFile());
        // копируем каждый файл из источника
        for (String subFile : subFiles) {
            copyFile(
                    source.toString() + "/" + subFile, dest.toString() + "/" + subFile);
        }
        // каталоги из источника
        String [] subDirs = source.list((file, s) ->
                new File(file.toString() + "/" + s).isDirectory());
        // рекурсивное копирование подкаталогов из источника
        for (String subDir : subDirs){
            File newDest = new File(dest.toString() + "/" + subDir);
            File newSource = new File (source.toString() + "/" + subDir);
            // создаем подкаталог в папке назначения
            newDest.mkdir();
            copyFiles(newSource, newDest);
        }
    }
    private static void copyFile(String source, String dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;

        try {
            // открываем файловые потоки
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            // считываем данные из файла источника в размере 1024 байта за раз
            // и записываем эти данные в файл назначения
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (Exception e) {
            // перехват исключения с выводом ошибки
            e.printStackTrace();
        } finally {
            // закрытие потоков
            if (is != null) is.close();
            if (os != null) os.close();
        }
    }
}