package com.example.user.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.task.Task;

public class LauncherActivity extends AppCompatActivity {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "taskDataBase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Task task = new Task("The newly added task" , "some descri", "a date whatever");

        Log.d("task before saving", String.valueOf(task.getId()));
        SqlRunner sqlRunner = new SqlRunner(this);
        sqlRunner.save(task);
        Log.d("task after saving", String.valueOf(task.getId()));



    }
}
