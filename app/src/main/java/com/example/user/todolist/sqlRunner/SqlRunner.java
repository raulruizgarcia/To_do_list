package com.example.user.todolist.sqlRunner;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.todolist.category.Category;
import com.example.user.todolist.task.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlRunner extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "taskDb";
    // Contacts table name
    private static final String TABLE_TASKS = "tasks";
    // Shops Table Columns names
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
                + COLUMN_DESCRIPTION + " TEXT"
                + COLUMN_DATE + " TEXT"
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

    public boolean save (Task task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, task.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, task.getDescription());
        contentValues.put(COLUMN_DATE, task.getDate().toString());
        contentValues.put(COLUMN_CATEGORY, task.getCategory().toString());

        long result = db.insert(TABLE_TASKS, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public List<Task> getAllShops() {
        List<Task> tasks = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTitle(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setDate(cursor.getString(3));
                task.setCategory(Category.valueOf(cursor.getString(4)));
                // Adding contact to list
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        // return contact list
        return tasks;
    }


}