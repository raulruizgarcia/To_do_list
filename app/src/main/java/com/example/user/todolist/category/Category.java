package com.example.user.todolist.category;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.todolist.recommendations.Recommendation;
import com.example.user.todolist.sqlRunner.SqlRunner;

import java.util.ArrayList;

public class Category {
    int id;
    String title;
    private static SqlRunner sqlRunner;

    private static final String TABLE_CATEGORIES = "categories";
    // Categories Table Columns names
    private static final String CATEGORIES_COLUMN_ID = "id";
    private static final String CATEGORIES_COLUMN_TITLE = "title";


    public Category(){

    }

    public Category(String title){
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category(int id, String title){
        this.id = id;
        this.title = title;
    }

    // CRUD ACTIONS:

    public boolean save (){
        SQLiteDatabase db = Category.sqlRunner.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORIES_COLUMN_TITLE, this.getTitle());

        long result = db.insert(TABLE_CATEGORIES, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            this.setId((int) (long) result);
            return true;
        }
    }

    public static ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db = Category.sqlRunner.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setTitle(cursor.getString(1));

                categories.add(category);
            } while (cursor.moveToNext());
        }
        // return categories list
        return categories;
    }

    public static Category getCategory(int id) {
        SQLiteDatabase db = Category.sqlRunner.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES, new String[] {CATEGORIES_COLUMN_ID, CATEGORIES_COLUMN_TITLE},
                CATEGORIES_COLUMN_ID + "=?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return recommendation
        return category;
    }

    public static Category getCategoryByTitle(String title){
        SQLiteDatabase db = Category.sqlRunner.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES, new String[] {CATEGORIES_COLUMN_ID, CATEGORIES_COLUMN_TITLE},
                CATEGORIES_COLUMN_TITLE + "=?", new String[] { title},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return recommendation
        return category;

    }


    public int updateCategory() {
        SQLiteDatabase db = Category.sqlRunner.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORIES_COLUMN_TITLE, this.getTitle());

        // updating row
        return db.update(TABLE_CATEGORIES, values, CATEGORIES_COLUMN_ID + " = ?",
                new String[]{String.valueOf(this.getId())});
    }


    public void deleteCategory(Category category) {
        SQLiteDatabase db = Category.sqlRunner.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, CATEGORIES_COLUMN_ID + " = ?",
                new String[] { String.valueOf(category.getId()) });
        db.close();
    }

    public void deleteAllCategories(){
        SQLiteDatabase db = Category.sqlRunner.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CATEGORIES);
    }

}
