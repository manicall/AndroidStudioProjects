package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> studentsNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        createTable(db);

        //String studentNameToFind = "'Ivanov.A'";

        /*Cursor query = db.rawQuery("SELECT Student.name, Subject.name, mark.value FROM student " +
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
        query.close();*/

        Cursor studentCursor = db.rawQuery("SELECT * FROM student;", null);
        studentCursor.moveToFirst();
        while(!studentCursor.isAfterLast()){
            // добавление имени студента
            studentsNameList.add(studentCursor.getString(1));
            studentCursor.moveToNext();
        }
        studentCursor.close();

        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, studentsNameList);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getApplicationContext(),
                "Вы выбрали " + l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
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