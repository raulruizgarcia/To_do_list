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

        // initialise items and get a hold of views


        listView = (ListView) findViewById(R.id.listView);
        sqlRunner = new SqlRunner(this);

        sqlRunner.deleteAllTasks();

        sqlRunner.save(new Task("First task", " ", "07-10-2017", Category.ADULT_STUFF));
        sqlRunner.save(new Task("Second task", " ", "07-11-2017", Category.ADULT_STUFF));
        sqlRunner.save(new Task("Third task", " ", "07-12-2017", Category.ADULT_STUFF));
        tasks = sqlRunner.getAllTasks();

        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);

        listView.setAdapter(taskAdapter);
    }
}
