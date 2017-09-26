package com.example.user.todolist.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.todolist.R;
import com.example.user.todolist.task.Task;

import java.util.ArrayList;

/**
 * Created by user on 26/09/2017.
 */

public class RecommendationAdapter extends ArrayAdapter<Task> {

    public RecommendationAdapter (Context context, ArrayList tasks){
        super(context, 0, tasks);
    }



    public View getView(int position, View taskItem, ViewGroup parent){
        if (taskItem == null){
            taskItem = LayoutInflater.from(getContext()).inflate(R.layout.activity_recommendations_list_view, parent, false);
        }
        Task currentTask = getItem(position);

        TextView category = (TextView) taskItem.findViewById(R.id.categoryListView);
        category.setText(String.valueOf(currentTask.getCategory()));

        TextView title = (TextView) taskItem.findViewById(R.id.titleListView);
        title.setText(currentTask.getTitle());

        taskItem.setTag(currentTask);
        return taskItem;
    }

}
