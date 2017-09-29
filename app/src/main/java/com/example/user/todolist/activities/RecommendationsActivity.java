package com.example.user.todolist.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ListView;
import com.example.user.todolist.R;
import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;
import com.example.user.todolist.recommendations.Recommendation;

import java.util.ArrayList;
import java.util.Collections;

public class RecommendationsActivity extends AppCompatActivity {

    ArrayList<Recommendation> recommendations;
    ListView listView;
    SqlRunner sqlRunner;
    private boolean coding = false;
    private boolean movies = false;
    private boolean food = false;
    private boolean books = false;
    private boolean gigs = false;
    private boolean music = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    private void loadActivity(){
        setContentView(R.layout.activity_tasks);

        listView = (ListView) findViewById(R.id.listView);
        sqlRunner = new SqlRunner(this);
        Category.sqlRunner = new SqlRunner(this);
        Recommendation.sqlRunner = new SqlRunner(this);
        Log.d("test recommendations", Recommendation.getRecommendation(1).getTitle());
        Log.d("test categories", Category.getCategory(1).getTitle());
        displayTasks();
    }

    @Override
    protected void onResume(){
        resetFilters();
        super.onResume();
    }

    public void resetFilters(){
        coding = false;
        movies = false;
        food = false;
        books = false;
        gigs = false;
        music = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_show_all:
                resetFilters();
                displayTasks();
                break;
            case R.id.filter_show_urgent_recommendations:
                displayUrgentTasks();
                break;
            case R.id.generate_test_array:
                generateTestArray();
                break;
            case R.id.filter_food:
                food = !item.isChecked();
                item.setChecked(food);
//                applyFilters();
                break;
            case R.id.filter_coding:
                coding = !item.isChecked();
                item.setChecked(coding);
//                applyFilters();
                break;
            case R.id.filter_movies:
                movies = !item.isChecked();
                item.setChecked(movies);
//                applyFilters();
                break;
            case R.id.filter_books:
                books = !item.isChecked();
                item.setChecked(books);
//                applyFilters();
                break;
            case R.id.filter_gigs:
                gigs = !item.isChecked();
                item.setChecked(gigs);
//                applyFilters();
                break;
            case R.id.filter_music:
                music = !item.isChecked();
                item.setChecked(music);
//                applyFilters();
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean checkFiltersAreApplied(){
        if (coding || movies || food || books || gigs || music){
            return true;
        } else {
            displayTasks();
            return false;
        }
    }

//    public void applyFilters(){
//        ArrayList<Recommendation> origin = sqlRunner.getAllTasks();
//        ArrayList<Recommendation> result = new ArrayList<>();
//        if (checkFiltersAreApplied()) {
//            if (coding) {
//                result.addAll(Recommendation.returnTaskOfCategory(origin, Category.CODING));
//            }
//            if (food) {
//                result.addAll(Recommendation.returnTaskOfCategory(origin, Category.FOOD));
//            }
//            if (movies) {
//                result.addAll(Recommendation.returnTaskOfCategory(origin, Category.MOVIES));
//            }
//            if (books) {
//                result.addAll(Recommendation.returnTaskOfCategory(origin, Category.BOOKS));
//            }
//            if (gigs) {
//                result.addAll(Recommendation.returnTaskOfCategory(origin, Category.GIGS));
//            }
//            if (music) {
//                result.addAll(Recommendation.returnTaskOfCategory(origin, Category.MUSIC));
//            }
//            recommendations = result;
//            RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, recommendations);
//            listView.setAdapter(recommendationAdapter);
//        }
//    }

    public void generateTestArray(){
        for (Recommendation recommendation : Recommendation.returnTestArray()){
             recommendation.save();
        }
        displayTasks();
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

    public void displayTasks(){
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
        displayTasks();
//        applyFilters();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.filter_food);
        checkable.setChecked(food);
        checkable = menu.findItem(R.id.filter_books);
        checkable.setChecked(books);
        checkable = menu.findItem(R.id.filter_coding);
        checkable.setChecked(coding);
        checkable = menu.findItem(R.id.filter_movies);
        checkable.setChecked(movies);
        checkable = menu.findItem(R.id.filter_gigs);
        checkable.setChecked(gigs);
        checkable = menu.findItem(R.id.filter_music);
        checkable.setChecked(music);
        return true;
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
                        resetFilters();
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

}
