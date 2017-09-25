package com.example.user.todolist.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
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

import java.util.ArrayList;

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

        // Set back to visible after troubleshooting
        deleteAllButton = (FloatingActionButton) findViewById(R.id.deleteAllFloatingButton);
        deleteAllButton.setVisibility(View.INVISIBLE);
        displayTasks();

        // Register contextual menu
        registerForContextMenu(listView);
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

    // All the commented out code below was responsible for the contextual menu



//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_item, menu);
//
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =
//                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int itemSelected = adapterContextMenuInfo.position;
//        Task taskToActUpon = tasks.get(itemSelected);
//        switch (item.getItemId()){
//            case R.id.edit_menu_item:
//                editTask(taskToActUpon);
//                return true;
//            case R.id.delete_menu_item:
//                Toast.makeText(this, "Deleting item: " + itemSelected, Toast.LENGTH_LONG).show();
//                deleteTask(taskToActUpon);
//                return true;
//        }
//        return true;
//    }

}
