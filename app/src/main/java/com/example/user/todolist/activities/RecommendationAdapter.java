package com.example.user.todolist.activities;

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

public class RecommendationAdapter extends ArrayAdapter<Recommendation> {

    public RecommendationAdapter (Context context, ArrayList tasks){
        super(context, 0, tasks);
    }



    public View getView(int position, View recommendationItem, ViewGroup parent){
        if (recommendationItem == null){
            recommendationItem = LayoutInflater.from(getContext()).inflate(R.layout.activity_recommendations_list_view, parent, false);
        }
        Recommendation currentRecommendation = getItem(position);

        TextView category = (TextView) recommendationItem.findViewById(R.id.categoryListView);
        Log.d("category title", Category.getCategory(currentRecommendation.getCategoryId()).getTitle() );
        category.setText(Category.getCategory(currentRecommendation.getCategoryId()).getTitle());

        TextView title = (TextView) recommendationItem.findViewById(R.id.titleListView);
        title.setText(currentRecommendation.getTitle());

        recommendationItem.setTag(currentRecommendation);
        return recommendationItem;
    }

//    public String removeUnderscore(String string){
//        return string.replace("_", " ");
//    }
//
//    public String capitalizeCategory(Category category) {
//        String inputString = removeUnderscore(category.toString());
//        StringBuilder categoryCapitalized = new StringBuilder(inputString.toLowerCase());
//        categoryCapitalized.setCharAt(0, Character.toUpperCase(categoryCapitalized.charAt(0)));
//        return categoryCapitalized.toString();
//    }

}
