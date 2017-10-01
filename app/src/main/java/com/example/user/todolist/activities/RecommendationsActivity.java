package com.example.user.todolist.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.recommendations.Recommendation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class RecommendationsActivity extends AppCompatActivity {

    ArrayList<Recommendation> recommendations;
    ListView listView;
    SqlRunner sqlRunner;
    private Spinner filterByCategorySpinner;
    private ArrayList<String> categories;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    private void loadActivity(){
        setContentView(R.layout.activity_recommendations);
        listView = (ListView) findViewById(R.id.listView);
        sqlRunner = new SqlRunner(this);
        Category.sqlRunner = new SqlRunner(this);
        Recommendation.sqlRunner = new SqlRunner(this);
        filterByCategorySpinner = (Spinner) findViewById(R.id.filterByCategorySpinner);
        populateFilterByCategorySpinner();
        displayRecommendations();

        filterByCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0){
                    Toast.makeText(parentView.getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                    Toast.makeText(parentView.getContext(), categories.get(position), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parentView.getContext(), "See all the recommendations", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_show_all:
                displayRecommendations();
                break;
            case R.id.filter_show_urgent_recommendations:
                displayUrgentTasks();
                break;
            case R.id.generate_test_array:
                generateTestArray();
                break;
            case R.id.manage_categories:
                Intent intent = new Intent(this, ManageCategoriesActivity.class);
                startActivity(intent);
            default:
                return false;
        }
        return true;
    }

    public void filterRecommendationsByCategory(int categoryIndex){
        ArrayList<Recommendation> result = new ArrayList<>();
        Category categoryToFilter = Category.getCategoryByTitle(categories.get(categoryIndex));
        int categoryId = categoryToFilter.getId();

        for (Recommendation recommendation: Recommendation.getAllRecommendations()){
            if (recommendation.getCategoryId() == categoryId){
                result.add(recommendation);
            }
        }
        recommendations = result;
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, recommendations);
        listView.setAdapter(recommendationAdapter);

    }

    public void generateTestArray(){
        for (Recommendation recommendation : Recommendation.returnTestArray()){
             recommendation.save();
        }
        displayRecommendations();
    }

    public void displayUrgentTasks() {
        ArrayList<Recommendation> recommendations = Recommendation.getAllRecommendations();
        ArrayList<Recommendation> result = new ArrayList<>();
        for (Recommendation recommendation : recommendations){
            if (recommendation.daysLeft() <= 7 && recommendation.daysLeft() > 0){
                result.add(recommendation);
            }
        }
        RecommendationDateAdapter recommendationDateAdapter = new RecommendationDateAdapter(this, result);
        listView.setAdapter(recommendationDateAdapter);
    }

    public void editTask(Recommendation recommendation){
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("id", String.valueOf(recommendation.getId()));
        intent.putExtra("title", recommendation.getTitle());
        intent.putExtra("description", recommendation.getDescription());
        intent.putExtra("date", recommendation.getDate());
        intent.putExtra("category_id", recommendation.getCategoryId());
        startActivity(intent);
    }

    public void displayRecommendations(){
        try {
            recommendations = Recommendation.getAllRecommendations();
            Collections.sort(recommendations);
        } catch (Exception e){
            recommendations = new ArrayList<>();
        }
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, recommendations);
        listView.setAdapter(recommendationAdapter);

    }

    public void deleteRecommendation(Recommendation recommendation) {
        recommendation.delete();
        displayRecommendations();
    }


    public void onNewTaskFloatingButtonPressed(View floatingButton){
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivity(intent);
    }

    public void onDeleteAllButtonPressed(final View floatingButton){
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete all your recommendations?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Recommendation.deleteAll();
                        recommendations = new ArrayList<>();
                        RecommendationDateAdapter recommendationDateAdapter = new RecommendationDateAdapter(floatingButton.getContext(), recommendations);
                        listView.setAdapter(recommendationDateAdapter);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    public void onTaskPressed(View view){
        View parentRow = (View) view.getParent();
        Recommendation recommendation = (Recommendation)parentRow.getTag();
        editTask(recommendation);
    }

    public void onDeleteItemButtonPressed(final View view){
        final View parentRow = (View) view.getParent();

        Animation fadeout = new AlphaAnimation(1.f, 0.f);
        fadeout.setDuration(1000);
        parentRow.startAnimation(fadeout);
        parentRow.postDelayed(new Runnable() {
            @Override
            public void run() {
                parentRow.setVisibility(View.GONE);
                Recommendation recommendation = (Recommendation) parentRow.getTag();
                deleteRecommendation(recommendation);
            }
        }, 1000);

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    public void populateFilterByCategorySpinner(){
        categories = new ArrayList<>();
        categories.add("See all recommendations");
        for (Category category: Category.getAllCategories()){
            categories.add(category.getTitle());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        filterByCategorySpinner.setAdapter(categoryAdapter);
    }


}
