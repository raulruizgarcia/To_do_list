package com.example.user.todolist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        // get tasks from the DB
        tasks = sqlRunner.getAllTasks();


        // display tasks in the list
        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);
        listView.setAdapter(taskAdapter);
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//
//    }

    public void onNewTaskFloatingButtonPressed(View floatingButton){
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivity(intent);
    }
}
