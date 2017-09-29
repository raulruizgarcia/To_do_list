package com.example.user.todolist.sqlRunner;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.todolist.category.Category;
import com.example.user.todolist.task.Recommendation;

import java.util.ArrayList;

public class SqlRunner extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "taskdb";
    // Contacts table name
    private static final String TABLE_TASKS = "tasks_table";
    // Tasks Table Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CATEGORY = "category";


    public SqlRunner(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_CATEGORY + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Creating tables again
        onCreate(db);
    }

    public boolean save (Recommendation recommendation){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, recommendation.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, recommendation.getDescription());

        if (recommendation.getDate() != null) {
            contentValues.put(COLUMN_DATE, recommendation.getDate());
        }
        if (recommendation.getCategory() != null) {
            contentValues.put(COLUMN_CATEGORY, recommendation.getCategory().toString());
        }

        long result = db.insert(TABLE_TASKS, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            recommendation.setId((int) (long) result);
            return true;
        }
    }

    public ArrayList<Recommendation> getAllTasks() {
        ArrayList<Recommendation> recommendations = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Recommendation recommendation = new Recommendation();
                recommendation.setId(Integer.parseInt(cursor.getString(0)));
                recommendation.setTitle(cursor.getString(1));
                recommendation.setDescription(cursor.getString(2));
                recommendation.setDate(cursor.getString(3));
                recommendation.setCategory(Category.valueOf(cursor.getString(4)));

                recommendations.add(recommendation);
            } while (cursor.moveToNext());
        }
        // return contact list
        return recommendations;
    }

    public Recommendation getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_CATEGORY },
                COLUMN_ID + "=?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Recommendation recommendation = new Recommendation(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), Category.valueOf(cursor.getString(4)));
        // return recommendation
        return recommendation;
    }

    public int updateTask(Recommendation recommendation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, recommendation.getTitle());
        values.put(COLUMN_DESCRIPTION, recommendation.getDescription());
        values.put(COLUMN_DATE, recommendation.getDate());
        values.put(COLUMN_CATEGORY, recommendation.getCategory().toString());
        // updating row
        return db.update(TABLE_TASKS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(recommendation.getId())});
    }


    public void deleteTask(Recommendation recommendation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?",
                new String[] { String.valueOf(recommendation.getId()) });
        db.close();
    }

    public void deleteAllTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASKS);
    }




}