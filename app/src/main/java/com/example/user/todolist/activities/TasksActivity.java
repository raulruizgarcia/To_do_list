package com.example.user.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.task.Task;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {

    ArrayList<Task> tasks;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        listView = (ListView) findViewById(R.id.listView);

        // Test seed

        tasks = new ArrayList<>();
        tasks.add(new Task("A title", "Description", "17-10-2017", Category.ADULT_STUFF));
        tasks.add(new Task("A title", "Description", "24-10-2017", Category.ADULT_STUFF));
        tasks.add(new Task("A title", "Description", "5-10-2017", Category.ADULT_STUFF));
        tasks.add(new Task("A title", "Description", "30-10-2017", Category.ADULT_STUFF));

        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);

        listView.setAdapter(taskAdapter);
    }
}
