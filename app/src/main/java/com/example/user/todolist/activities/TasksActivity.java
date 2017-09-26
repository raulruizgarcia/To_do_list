package com.example.user.todolist.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.task.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class TasksActivity extends AppCompatActivity {

    ArrayList<Task> tasks;
    ListView listView;
    SqlRunner sqlRunner;
    FloatingActionButton deleteAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // initialise items and get a hold of views
        listView = (ListView) findViewById(R.id.listView);
        sqlRunner = new SqlRunner(this);

        deleteAllButton = (FloatingActionButton) findViewById(R.id.deleteAllFloatingButton);
        displayTasks();

    }


    public boolean filterContentByCategory(Category category){
        ArrayList<Task> result = new ArrayList<>();
        for (Task task: tasks){
            if (task.getCategory() == category){
                result.add(task);
            }
        }
        TaskAdapter taskAdapter = new TaskAdapter(this, result);
        listView.setAdapter(taskAdapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        switch (selectedItemId) {
            case R.id.filter_show_all:
                displayTasks();
                break;
            case R.id.filter_adult_stuff:
                filterContentByCategory(Category.ADULT_STUFF);
                break;
            case R.id.filter_coding:
                filterContentByCategory(Category.CODING);
                break;
            case R.id.filter_food:
                filterContentByCategory(Category.FOOD);
                break;
            case R.id.filter_movies:
                filterContentByCategory(Category.MOVIES);
                break;
            case R.id.filter_books:
                filterContentByCategory(Category.BOOKS);
                break;
            case R.id.filter_games:
                filterContentByCategory(Category.GAMES);
                break;
            case R.id.filter_gigs:
                filterContentByCategory(Category.GIGS);
                break;
            case R.id.filter_music:
                filterContentByCategory(Category.MUSIC);
                break;
            case R.id.filter_fun_stuff:
                filterContentByCategory(Category.FUN_STUFF);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    public void editTask(Task task){
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("id", String.valueOf(task.getId()));
        intent.putExtra("title", task.getTitle());
        intent.putExtra("description", task.getDescription());
        intent.putExtra("date", task.getDate());
        intent.putExtra("category", task.getCategory().toString());
        startActivity(intent);
    }

    public void displayTasks(){
        try {
            tasks = sqlRunner.getAllTasks();
            Collections.sort(tasks);
        } catch (Exception e){
            tasks = new ArrayList<>();
        }
        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);
        listView.setAdapter(taskAdapter);

    }

    public void deleteTask(Task task) {
        sqlRunner.deleteTask(task);
        displayTasks();
    }

    public void onNewTaskFloatingButtonPressed(View floatingButton){
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivity(intent);
    }

    public void onDeleteAllButtonPressed(View floatingButton){
        sqlRunner.deleteAllTasks();
        tasks = new ArrayList<>();
        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);
        listView.setAdapter(taskAdapter);
    }

    public void onTaskPressed(View view){
        View parentRow = (View) view.getParent();
        Task task = (Task)parentRow.getTag();
        editTask(task);
    }

    public void onDeleteItemButtonPressed(View view){
        View parentRow = (View) view.getParent();
        Task task = (Task) parentRow.getTag();
        deleteTask(task);

    }

}
