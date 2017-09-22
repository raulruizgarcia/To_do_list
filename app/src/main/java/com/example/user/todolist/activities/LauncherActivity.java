package com.example.user.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.task.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class LauncherActivity extends AppCompatActivity {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "taskDataBase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

//        Task task = new Task("Another complete task" , "description...", "a date whatever", Category.ADULT_STUFF);
        SqlRunner sqlRunner = new SqlRunner(this);

        Task task = sqlRunner.getTask(2);
        sqlRunner.deleteTask(task);



        ArrayList<Task> tasks = sqlRunner.getAllTasks();
        for (Task currentTask: tasks){
            System.out.println("Task ID: " + currentTask.getId());
            System.out.println("Task ID: " + currentTask.getTitle());
        }



    }
}
