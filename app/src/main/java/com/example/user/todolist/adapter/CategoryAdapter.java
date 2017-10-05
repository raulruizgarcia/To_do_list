package com.example.user.todolist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.recommendations.Recommendation;

import java.util.ArrayList;

/**
 * Created by user on 26/09/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter (Context context, ArrayList tasks){
        super(context, 0, tasks);
    }



    public View getView(int position, View recommenndationItem, ViewGroup parent){
        if (recommenndationItem == null){
            recommenndationItem = LayoutInflater.from(getContext()).inflate(R.layout.adapter_category, parent, false);
        }
        Category currentCategory = getItem(position);

        TextView category = (TextView) recommenndationItem.findViewById(R.id.categoryTitle);
//        Log.d("category title", Category.getCategory(currentCategory.getCategoryId()).getTitle() );
        category.setText(currentCategory.getTitle());

        recommenndationItem.setTag(currentCategory);
        return recommenndationItem;
    }



}
