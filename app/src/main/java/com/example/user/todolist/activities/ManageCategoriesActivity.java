package com.example.user.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.todolist.R;
import com.example.user.todolist.adapter.CategoryAdapter;
import com.example.user.todolist.category.Category;

import java.util.ArrayList;
import java.util.List;

public class ManageCategoriesActivity extends AppCompatActivity {

    EditText newCategoryTitle;
    ArrayList<Category> categories;
    ListView categoryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        newCategoryTitle = (EditText) findViewById(R.id.categoryNameEditText);
        categoryListView = (ListView) findViewById(R.id.categoryListView);
        inflateCategoryListView();
        setListViewListener();
    }

    public void onCreateCategoryButtonClicked(View view){
        if (newCategoryTitle.length() > 0) {
            String categoryTitle = newCategoryTitle.getText().toString();
            Category category = new Category(categoryTitle);
            category.save();
            newCategoryTitle.setText("");
            inflateCategoryListView();
        }
    }

    public void inflateCategoryListView(){
        categories = Category.getAllCategories();
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        categoryListView.setAdapter(categoryAdapter);
    }

    private void setListViewListener(){
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) view.getTag();
                Category.deleteCategory(category);
                inflateCategoryListView();
            }
        });
    }

}
