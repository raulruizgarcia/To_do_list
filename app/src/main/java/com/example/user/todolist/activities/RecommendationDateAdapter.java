package com.example.user.todolist.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.recommendations.Recommendation;

import java.util.ArrayList;


public class RecommendationDateAdapter extends ArrayAdapter<Recommendation> {

    public RecommendationDateAdapter(Context context, ArrayList recommendations){
        super(context, 0, recommendations);
    }



    public View getView(int position, View recommendationItem, ViewGroup parent){
        if (recommendationItem == null){
            recommendationItem = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_view, parent, false);
        }
        Recommendation currentRecommendation = getItem(position);

        TextView daysLeft = (TextView) recommendationItem.findViewById(R.id.daysLeftView);
        daysLeft.setText(String.valueOf(currentRecommendation.daysLeft()));

        TextView title = (TextView) recommendationItem.findViewById(R.id.titleListView);
        title.setText(currentRecommendation.getTitle());

        TextView category = (TextView) recommendationItem.findViewById(R.id.categoryListView);
        category.setText((Category.getCategory(currentRecommendation.getCategoryId()).getTitle()));

        recommendationItem.setTag(currentRecommendation);
        return recommendationItem;
    }

//    public String removeUnderscore(String string){
//        return string.replace("_", " ");
//    }

//    public String capitalizeCategory(Category category) {
//        String inputString = removeUnderscore(category.toString());
//        StringBuilder categoryCapitalized = new StringBuilder(inputString.toLowerCase());
//        categoryCapitalized.setCharAt(0, Character.toUpperCase(categoryCapitalized.charAt(0)));
//        return categoryCapitalized.toString();
//    }

}
