package com.example.user.todolist.sqlRunner;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlRunner extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "recommendationsdb";
    // Contacts table name
    private static final String TABLE_RECOMMENDATIONS = "recommendations";
    private static final String TABLE_CATEGORIES = "categories";
    // Tasks Table Columns names
    private static final String RECOMMENDATIONS_COLUMN_ID = "id";
    private static final String RECOMMENDATIONS_COLUMN_TITLE = "title";
    private static final String RECOMMENDATIONS_COLUMN_DESCRIPTION = "description";
    private static final String RECOMMENDATIONS_COLUMN_DATE = "date";
    private static final String RECOMMENDATIONS_COLUMN_CATEGORY_ID = "category_id";
    // Categories Table Columns names
    private static final String CATEGORIES_COLUMN_ID = "id";
    private static final String CATEGORIES_COLUMN_TITLE = "title";


    public SqlRunner(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECOMMENDATIONS_TABLE = "CREATE TABLE " + TABLE_RECOMMENDATIONS + "("
                + RECOMMENDATIONS_COLUMN_ID + " INTEGER PRIMARY KEY," + RECOMMENDATIONS_COLUMN_TITLE + " TEXT,"
                + RECOMMENDATIONS_COLUMN_DESCRIPTION + " TEXT,"
                + RECOMMENDATIONS_COLUMN_DATE + " TEXT,"
                + RECOMMENDATIONS_COLUMN_CATEGORY_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + RECOMMENDATIONS_COLUMN_CATEGORY_ID + ") REFERENCES "
                + TABLE_CATEGORIES + " ("
                + ")";

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + CATEGORIES_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + CATEGORIES_COLUMN_TITLE+ " TEXT,"
                + "CONSTRAINT title_unique UNIQUE (" + CATEGORIES_COLUMN_TITLE
                + ")";

        db.execSQL(CREATE_RECOMMENDATIONS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECOMMENDATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        // Creating tables again
        onCreate(db);
    }



}