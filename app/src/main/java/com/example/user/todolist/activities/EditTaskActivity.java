package com.example.user.todolist.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.user.todolist.R;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    EditText titleTextEdit;
    EditText descriptionTextEdit;
    EditText dateEditText;
    ImageButton calendarButton;
    private DatePickerDialog.OnDateSetListener myDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // get ahold of views
        titleTextEdit = (EditText) findViewById(R.id.taskTitleEditText);
        descriptionTextEdit = (EditText) findViewById(R.id.taskDescriptionEditText);
        dateEditText = (EditText) findViewById(R.id.taskDateEditText);
        calendarButton = (ImageButton) findViewById(R.id.calendarButton);

        // Calendar and datePicker Set up
        calendarButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
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
        });
        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "-" + month + "-" + year;
                dateEditText.setText(date);

            }
        };

    }


}
