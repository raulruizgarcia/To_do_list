package com.example.user.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;

public class ManageCategoriesActivity extends AppCompatActivity {

    EditText newCategoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        newCategoryTitle = (EditText) findViewById(R.id.categoryNameEditText);
    }

    public void onCreateCategoryButtonClicked(View view){
        if (newCategoryTitle.length() > 0) {
            String categoryTitle = newCategoryTitle.getText().toString();
            Category category = new Category(categoryTitle);
            category.save();
            newCategoryTitle.setText("");
        }
    }
}
