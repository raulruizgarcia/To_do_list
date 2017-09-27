package com.example.user.todolist.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuItemImpl;
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
import android.widget.ToggleButton;

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
    private boolean coding = false;
    private boolean movies = false;
    private boolean food = false;
    private boolean books = false;
    private boolean games = false;
    private boolean gigs = false;
    private boolean music = false;
    private boolean fun_stuff = false;


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
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, result);
        listView.setAdapter(recommendationAdapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_show_all:
                displayTasks();
                break;
            case R.id.filter_show_urgent_recommendations:
                displayUrgentTasks();
                break;
            case R.id.filter_food:
                food = !item.isChecked();
                item.setChecked(food);
                applyFilters();
                break;
            case R.id.filter_coding:
                coding = !item.isChecked();
                item.setChecked(coding);
                applyFilters();
                break;
            case R.id.filter_movies:
                movies = !item.isChecked();
                item.setChecked(movies);
                applyFilters();
                break;
            case R.id.filter_books:
                books = !item.isChecked();
                item.setChecked(books);
                applyFilters();
                break;
            case R.id.filter_games:
                games = !item.isChecked();
                item.setChecked(games);
                applyFilters();
                break;
            case R.id.filter_gigs:
                gigs = !item.isChecked();
                item.setChecked(gigs);
                applyFilters();
                break;
            case R.id.filter_music:
                music = !item.isChecked();
                item.setChecked(music);
                applyFilters();
                break;
            case R.id.filter_fun_stuff:
                fun_stuff = !item.isChecked();
                item.setChecked(fun_stuff);
                applyFilters();
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean checkFiltersAreApplied(){
        if (coding || movies || food || books || games || gigs || music || fun_stuff){
            return true;
        } else {
            displayTasks();
            return false;
        }
    }

    public void applyFilters(){
        ArrayList<Task> origin = sqlRunner.getAllTasks();
        ArrayList<Task> result = new ArrayList<>();
        if (checkFiltersAreApplied()) {
            if (coding) {
                    result.addAll(Task.returnTaskOfCategory(origin, Category.CODING));
            }
            if (food) {
                result.addAll(Task.returnTaskOfCategory(origin, Category.FOOD));
            }
            if (movies) {
                result.addAll(Task.returnTaskOfCategory(origin, Category.MOVIES));
            }
            if (books) {
                result.addAll(Task.returnTaskOfCategory(origin, Category.BOOKS));
            }
            if (games) {
                result.addAll(Task.returnTaskOfCategory(origin, Category.GAMES));
            }
            if (gigs) {
                result.addAll(Task.returnTaskOfCategory(origin, Category.GIGS));
            }
            if (music) {
                result.addAll(Task.returnTaskOfCategory(origin, Category.MUSIC));
            }
            if (fun_stuff) {
                result.addAll(Task.returnTaskOfCategory(origin, Category.FUN_STUFF));
            }
            tasks = result;
            RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, tasks);
            listView.setAdapter(recommendationAdapter);
        }


    }


    public void generateTestArray(){
        for (Task task: Task.returnTestArray()){
            sqlRunner.save(task);
        }
        displayTasks();
    }

    public void displayUrgentTasks() {
        ArrayList<Task> tasks = sqlRunner.getAllTasks();
        ArrayList<Task> result = new ArrayList<>();
        for (Task task: tasks){
            if (task.daysLeft() <= 7 && task.daysLeft() > 0){
                result.add(task);
            }
        }
        TaskAdapter taskAdapter = new TaskAdapter(this, result);
        listView.setAdapter(taskAdapter);
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
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, tasks);
        listView.setAdapter(recommendationAdapter);

    }

    public void deleteTask(Task task) {
        sqlRunner.deleteTask(task);
        displayTasks();
    }

    public void onNewTaskFloatingButtonPressed(View floatingButton){
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivity(intent);
    }

    public void onDeleteAllButtonPressed(final View floatingButton){
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete all your tasks?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        sqlRunner.deleteAllTasks();
                        tasks = new ArrayList<>();
                        TaskAdapter taskAdapter = new TaskAdapter(floatingButton.getContext(), tasks);
                        listView.setAdapter(taskAdapter);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    public void switchToggle(ToggleButton toggleButton){
        if (toggleButton.isChecked()){
            toggleButton.setChecked(false);
        } else {
            toggleButton.setChecked(true);
        }
    }

}
