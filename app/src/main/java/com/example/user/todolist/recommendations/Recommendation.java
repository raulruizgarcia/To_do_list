package com.example.user.todolist.recommendations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.todolist.sqlRunner.SqlRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Recommendation implements Comparable<Recommendation> {

    private int id;
    private String title;
    private String description;
    private String date;
    private int categoryId;
    private static SqlRunner sqlRunner;

    // Recommendations Table Name
    private static final String TABLE_RECOMMENDATIONS = "recommendations";
    // Recommendations Table Columns names
    private static final String RECOMMENDATIONS_COLUMN_ID = "id";
    private static final String RECOMMENDATIONS_COLUMN_TITLE = "title";
    private static final String RECOMMENDATIONS_COLUMN_DESCRIPTION = "description";
    private static final String RECOMMENDATIONS_COLUMN_DATE = "date";
    private static final String RECOMMENDATIONS_COLUMN_CATEGORY_ID = "categoryId";
    // Categories Table Columns names
    private static final String CATEGORIES_COLUMN_ID = "id";
    private static final String CATEGORIES_COLUMN_TITLE = "title";


    public Recommendation() {

    }

    public Recommendation(String title, String description, String date, int categoryId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
    }


    public Recommendation(int id, String title, String description, String date, int categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long daysLeft() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long diff = 0;
        try {
            Date taskDate = myFormat.parse(date);
            diff = (long) taskDate.getTime() - currentDate.getTime();
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Recommendation otherRecommendation) {
        int myTime = (int) this.daysLeft();
        int otherTaskTime = (int) otherRecommendation.daysLeft();
        int difference = myTime - otherTaskTime;
        if (difference == 0) {
            return 0;
        } else if (difference > 0){
            return 1;
        } else {
            return -1;
        }
    }

//    public static ArrayList<Recommendation> returnTestArray(){
//        ArrayList<Recommendation> result = new ArrayList<>();
//        Recommendation recommendation1 = new Recommendation("It, a novel", "", "", Category.BOOKS);
//        Recommendation recommendation2 = new Recommendation("The handmaid's tale", "", "", Category.BOOKS);
//        Recommendation recommendation3 = new Recommendation("Queens of Stone Age: Villains", "", "", Category.MUSIC);
//        Recommendation recommendation4 = new Recommendation("Buy Tickets for Kasabian", "", "2-10-2017", Category.GIGS);
//        Recommendation recommendation8 = new Recommendation("Learn Android Studio", "", "", Category.CODING);
//        Recommendation recommendation5 = new Recommendation("Learn C#", "", "", Category.CODING);
//        Recommendation recommendation6 = new Recommendation("Cameo Cinema", "", "", Category.MOVIES);
//        Recommendation recommendation7 = new Recommendation("Ramen", "", "", Category.FOOD);
//        result.add(recommendation1);
//        result.add(recommendation2);
//        result.add(recommendation3);
//        result.add(recommendation4);
//        result.add(recommendation5);
//        result.add(recommendation6);
//        result.add(recommendation7);
//        result.add(recommendation8);
//        return result;
//    }

//    public static ArrayList<Recommendation> returnTaskOfCategory (ArrayList<Recommendation> arrayList, Category category){
//        ArrayList<Recommendation> resultArray = new ArrayList<>();
//        for (Recommendation recommendation : arrayList){
//            if (recommendation.getCategory().equals(category)){
//                resultArray.add(recommendation);
//            }
//        }
//        return resultArray;
//    }

    // CRUD OPERATIONS:

    public boolean save (){
        SQLiteDatabase db = Recommendation.sqlRunner.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECOMMENDATIONS_COLUMN_TITLE, this.getTitle());
        contentValues.put(RECOMMENDATIONS_COLUMN_DESCRIPTION, this.getDescription());

        if (this.getDate() != null) {
            contentValues.put(RECOMMENDATIONS_COLUMN_DATE, this.getDate());
        }

        contentValues.put(RECOMMENDATIONS_COLUMN_CATEGORY_ID, this.getCategoryId());

        long result = db.insert(TABLE_RECOMMENDATIONS, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            this.setId((int) (long) result);
            return true;
        }
    }

    public static ArrayList<Recommendation> getAllRecommendations() {
        ArrayList<Recommendation> recommendations = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECOMMENDATIONS;
        SQLiteDatabase db = Recommendation.sqlRunner.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Recommendation recommendation = new Recommendation();
                recommendation.setId(Integer.parseInt(cursor.getString(0)));
                recommendation.setTitle(cursor.getString(1));
                recommendation.setDescription(cursor.getString(2));
                recommendation.setDate(cursor.getString(3));
                recommendation.setCategoryId(Integer.parseInt(cursor.getString(4)));

                recommendations.add(recommendation);
            } while (cursor.moveToNext());
        }
        // return contact list
        return recommendations;
    }

    public static Recommendation getRecommendation(int id) {
        SQLiteDatabase db = Recommendation.sqlRunner.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECOMMENDATIONS, new String[] {RECOMMENDATIONS_COLUMN_ID, RECOMMENDATIONS_COLUMN_TITLE, RECOMMENDATIONS_COLUMN_DESCRIPTION, RECOMMENDATIONS_COLUMN_DATE, RECOMMENDATIONS_COLUMN_CATEGORY_ID},
                RECOMMENDATIONS_COLUMN_ID + "=?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Recommendation recommendation = new Recommendation(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.valueOf(cursor.getString(4)));
        // return recommendation
        return recommendation;
    }

    public int updateRecommendation() {
        SQLiteDatabase db = Recommendation.sqlRunner.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECOMMENDATIONS_COLUMN_TITLE, this.getTitle());
        values.put(RECOMMENDATIONS_COLUMN_DESCRIPTION, this.getDescription());
        values.put(RECOMMENDATIONS_COLUMN_DATE, this.getDate());
        values.put(RECOMMENDATIONS_COLUMN_CATEGORY_ID, this.getCategoryId());
        // updating row
        return db.update(TABLE_RECOMMENDATIONS, values, RECOMMENDATIONS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(this.getId())});
    }


    public void deleteRecommendation(Recommendation recommendation) {
        SQLiteDatabase db = Recommendation.sqlRunner.getWritableDatabase();
        db.delete(TABLE_RECOMMENDATIONS, RECOMMENDATIONS_COLUMN_ID + " = ?",
                new String[] { String.valueOf(recommendation.getId()) });
        db.close();
    }

    public void deleteAllTasks(){
        SQLiteDatabase db = Recommendation.sqlRunner.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_RECOMMENDATIONS);
    }


}