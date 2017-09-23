package com.example.user.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.task.Task;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {

    ArrayList<Task> tasks;
    ListView listView;
    SqlRunner sqlRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // initialise items and get ahold of views

        listView = (ListView) findViewById(R.id.listView);
        sqlRunner = new SqlRunner(this);
        tasks = sqlRunner.getAllTasks();

        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);

        listView.setAdapter(taskAdapter);
    }
}
