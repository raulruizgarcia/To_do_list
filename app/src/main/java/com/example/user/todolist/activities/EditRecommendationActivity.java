package com.example.user.todolist.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.recommendations.Recommendation;

import java.util.ArrayList;
import java.util.Calendar;

public class EditRecommendationActivity extends AppCompatActivity {

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
    Button saveButton;
    Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // get a hold of views
        updateButton = (Button) findViewById(R.id.updateNoteButton);
        saveButton = (Button) findViewById(R.id.saveNoteButton);
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
            setTitle("Edit recommendation");
            saveButton.setVisibility(View.INVISIBLE);
        } else {
            updateButton.setVisibility(View.INVISIBLE);
            setTitle("New recommendation");
        }

    }

    public void populateFieldsWithExtras(){
        Intent intent = getIntent();
        temporaryTaskId = Integer.parseInt(intent.getStringExtra("id"));
        titleTextEdit.setText(intent.getStringExtra("title"));
        descriptionTextEdit.setText(intent.getStringExtra("description"));
        dateEditText.setText(intent.getStringExtra("date"));
//        int categoryPosition = Category.valueOf(intent.getStringExtra("category")).ordinal();
//        category_spinner.setSelection(categoryPosition);
    }

    public boolean bundleHasContents(){
        return getIntent().hasExtra("id");
    }

//    public String removeUnderscore(String string){
//        return string.replace("_", " ");
//    }

//    public String addUnderscoreAndUpperCase(String string){
//        return string.replace(" ", "_").toUpperCase();
//    }

    public void populateCategorySpinner(){
        categories = new ArrayList<>();
        for (Category category: Category.getAllCategories()){
            categories.add(category.getTitle());
        }

        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category_spinner.setAdapter(categoryAdapter);
    }

    public boolean isTitleFieldValid(){
        if (titleTextEdit.getText().toString().equals("")){
            Toast.makeText(this, "Title field cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    public void onSaveNoteButtonPressed(View button){
        if (isTitleFieldValid()) {
            String title = titleTextEdit.getText().toString();
            String description = descriptionTextEdit.getText().toString();
            String date = dateEditText.getText().toString();
            String categoryString = (category_spinner.getSelectedItem().toString());
            int categoryId = Category.getCategoryByTitle(categoryString).getId();
            Recommendation recommendation = new Recommendation(title, description, date, categoryId);
            recommendation.save();
            Intent intent = new Intent(this, RecommendationsActivity.class);
            startActivity(intent);
        }
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

    public void onCalendarButtonPressed(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                EditRecommendationActivity.this,
                android.R.style.Theme_Holo_Light,
                myDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void onUpdateNoteButtonPressed(View button){
        Toast.makeText(this, "Recommendation updated", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("id"));
        String title = titleTextEdit.getText().toString();
        String description = descriptionTextEdit.getText().toString();
        String date = dateEditText.getText().toString();
        Category category = Category.getCategoryByTitle((category_spinner.getSelectedItem().toString()));

        Recommendation recommendationToUpdate = new Recommendation(id, title, description, date, category.getId());
        recommendationToUpdate.update();

        Intent goBackToHomePage = new Intent(this, RecommendationsActivity.class);
        startActivity(goBackToHomePage);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean exitByBackKey() {
        if (titleTextEdit.getText().toString().equals("")){
            finish();
            return true;
        }
        String message = null;
        if (bundleHasContents()){
            finish();
        } else {
            message = "Do you want to discard the current task?";
        }
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitByBackKey();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
