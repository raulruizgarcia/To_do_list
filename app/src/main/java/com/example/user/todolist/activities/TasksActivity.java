package com.example.user.todolist.activities;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // initialise items and get a hold of views
        listView = (ListView) findViewById(R.id.listView);
        sqlRunner = new SqlRunner(this);
        displayTasks();

        // Register contextual menu
        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemSelected = adapterContextMenuInfo.position;
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "item clicked" + itemSelected, Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "item clicked" + itemSelected, Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "item clicked" + itemSelected, Toast.LENGTH_LONG).show();
                return true;
            case R.id.item4:
                Toast.makeText(this, "item clicked" + itemSelected, Toast.LENGTH_LONG).show();
                return true;
        }
        return true;
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
}
