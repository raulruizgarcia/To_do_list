package com.example.user.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

//        Task task = new Task("Task title", "task description", "The date", Category.ADULT_STUFF);
//        SqlRunner sqlRunner = new SqlRunner(this);
//
//        sqlRunner.save(task);
//        System.out.println(sqlRunner.getAllTasks());
    }
}
