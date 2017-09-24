package com.example.user.todolist.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.task.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    SqlRunner sqlRunner;
    EditText titleTextEdit;
    EditText descriptionTextEdit;
    EditText dateEditText;
    ImageButton calendarButton;
    private DatePickerDialog.OnDateSetListener myDateSetListener;
    Spinner category_spinner;
    ArrayAdapter<String> categoryAdapter;
    ArrayList<String> categories;
    int temporaryTaskId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // get a hold of views
        titleTextEdit = (EditText) findViewById(R.id.taskTitleEditText);
        descriptionTextEdit = (EditText) findViewById(R.id.taskDescriptionEditText);
        dateEditText = (EditText) findViewById(R.id.taskDateEditText);
        calendarButton = (ImageButton) findViewById(R.id.calendarButton);
        myDateSetListener = getDatePicker();
        category_spinner = (Spinner) findViewById(R.id.task_category_dropdown);

        sqlRunner = new SqlRunner(this);

        populateCategorySpinner();

        if (bundleHasContents()){
            populateFieldsWithExtras();
        }

    }

    public void populateFieldsWithExtras(){
        Intent intent = getIntent();
        temporaryTaskId = Integer.parseInt(intent.getStringExtra("id"));
        titleTextEdit.setText(intent.getStringExtra("title"));
        descriptionTextEdit.setText(intent.getStringExtra("description"));
        dateEditText.setText(intent.getStringExtra("date"));
        int categoryPosition = Category.valueOf(intent.getStringExtra("category")).ordinal();
        category_spinner.setSelection(categoryPosition);
    }

    public boolean bundleHasContents(){
        return getIntent().hasExtra("id");
    }

    public void populateCategorySpinner(){
        categories = new ArrayList<>();
        for (Category category: Category.values()){
            categories.add(category.toString());
        }

        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category_spinner.setAdapter(categoryAdapter);
    }

    public void onSaveNoteButtonPressed(View button){
        String title = titleTextEdit.getText().toString();
        String description = descriptionTextEdit.getText().toString();
        String date = dateEditText.getText().toString();
        String category = category_spinner.getSelectedItem().toString();
        Log.d("Title", title);
        Log.d("Description", description);
        Log.d("Date", date);
        Log.d("Category", category);
        Task task = new Task(title, description,date,Category.valueOf(category));
        sqlRunner.save(task);
        System.out.println(task);
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }

    public DatePickerDialog.OnDateSetListener getDatePicker(){
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "-" + month + "-" + year;
                dateEditText.setText(date);
            }
        };
    }

    public void onUpdateNoteButtonPressed(View button){
        Toast.makeText(this, "Update button pressed", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("id"));
        String title = titleTextEdit.getText().toString();
        String description = descriptionTextEdit.getText().toString();
        String date = dateEditText.getText().toString();
        Category category = Category.valueOf(category_spinner.getSelectedItem().toString());

        Task taskToUpdate = new Task(id, title, description, date, category);
        sqlRunner.updateTask(taskToUpdate);

        Intent goBackToHomePage = new Intent(this, TasksActivity.class);
        startActivity(goBackToHomePage);
    }



    public void onCalendarButtonPressed(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                EditTaskActivity.this,
                android.R.style.Theme_Holo_Light,
                myDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}
