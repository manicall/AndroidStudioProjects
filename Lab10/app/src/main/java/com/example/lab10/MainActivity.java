package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        createTable(db);
        addStudent(db, "Ivanov.MA", "8VT");

        Cursor query = db.rawQuery("SELECT * FROM student;", null);
        if(query.moveToFirst()){
            String name = query.getString(0);
            String sGroup = query.getString(1);
            Log.d("MyApp", name + " " + sGroup);
        }

        query.close();
        db.close();
    }

    private void createTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS student (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "sGroup TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS subject (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "studentId INTEGER," +
                "FOREIGN KEY (studentId) REFERENCES subject(id) ON DELETE CASCADE ON UPDATE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS mark (" +
                "id INTEGER PRIMARY KEY, " +
                "value INTEGER NOT NULL," +
                "subjectId INTEGER UNIQUE," +
                "FOREIGN KEY (subjectId) REFERENCES subject(id) ON DELETE CASCADE ON UPDATE CASCADE)");
    }
    private void addStudent(SQLiteDatabase db, String _name, String _sGroup){
        db.execSQL("INSERT INTO student (name, sGroup) VALUES (@_name, @_sGroup);");
    }
    private void addSubject(SQLiteDatabase db, String _name, int _studentId){
        db.execSQL("INSERT INTO subject (name, studentId) VALUES (@_name, @_studentId);");
    }
    private void addMark(SQLiteDatabase db, int _value, String _subjectId){
        db.execSQL("INSERT INTO mark (value, subjectId) VALUES (@_value, @_subjectId);");
    }


}