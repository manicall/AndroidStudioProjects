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

        String studentNameToFind = "'Ivanov.A'";

        Cursor query = db.rawQuery("SELECT Student.name, Subject.name, mark.value FROM student " +
                "join mark on student.id = studentId " +
                "join subject on subject.id = subjectId " +
                "where student.name = " + studentNameToFind  + "  ;", null);

        query.moveToFirst();
        while(!query.isAfterLast()){
            String studentName = query.getString(0);
            String subjectName = query.getString(1);
            String markValue = query.getString(2);
            Log.d("MyApp", studentName + " " + subjectName + " " + markValue);
            query.moveToNext();
        }
        query.close();
/*        query = db.rawQuery("SELECT * FROM student;", null);
        query.moveToFirst();
        while(!query.isAfterLast()){
            String id = query.getString(0);
            String name = query.getString(1);
            String group = query.getString(2);
            Log.d("MyApp", id + name + " " + group);
            query.moveToNext();
        }
        query.close();
        query = db.rawQuery("SELECT * FROM student;", null);
        query.moveToFirst();
        while(!query.isAfterLast()){
            String id = query.getString(0);
            String name = query.getString(1);
            String group = query.getString(2);
            Log.d("MyApp", id + name + " " + group);
            query.moveToNext();
        }
        query.close();*/

        //db.close();
    }

    private void createTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS student (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "_group TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS subject (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS mark (" +
                "value TEXT NOT NULL," +
                "studentId INTEGER," +
                "subjectId INTEGER," +
                "UNIQUE  (studentId, subjectId), " +
                "FOREIGN KEY (studentId) REFERENCES subject(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (subjectId) REFERENCES subject(id) ON DELETE CASCADE ON UPDATE CASCADE)");
    }
    private void addStudent(SQLiteDatabase db, String _name, String _group){
        db.execSQL("INSERT INTO student (name, _group) VALUES (" + _name + ", " + _group + ");");
    }
    private void addSubject(SQLiteDatabase db, String _name){
        db.execSQL("INSERT INTO subject (name) VALUES (" + _name + ");");
    }
    private void addMark(SQLiteDatabase db, String _value, int _studentId, int _subjectId){
        db.execSQL("INSERT INTO mark (value, studentId,  subjectId) " +
                "VALUES (" + _value + ", "   + _studentId +  ", " + _subjectId  + ");");
    }


}