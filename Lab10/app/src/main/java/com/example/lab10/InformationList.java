package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class InformationList extends ListActivity {
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> studentsRecordsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_list);

        SQLiteDatabase db =
                getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Bundle arguments = getIntent().getExtras();
        String studentNameToFind = "'" + arguments.getString("name") + "'";

        Cursor query = db.rawQuery("SELECT Student.name, Subject.name, mark.value FROM student " +
                "join mark on student.id = studentId " +
                "join subject on subject.id = subjectId " +
                "where student.name = " + studentNameToFind  + "  ;", null);

        query.moveToFirst();
        while(!query.isAfterLast()){
            String studentName = query.getString(0);
            String subjectName = query.getString(1);
            String markValue = query.getString(2);

            studentsRecordsList.add("Имя: " + studentName + "\n" +
                    "Предмет:" + subjectName + "\n" +
                    "Оценка:" + markValue);

            query.moveToNext();
        }
        query.close();

        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, studentsRecordsList);
        setListAdapter(mAdapter);
    }
}