package com.example.senamit.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.senamit.habittracker.data.HabitContract.*;

public class HabitTrackerDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "habit";
    public static final int DATABASE_VERSION = 5;

    private static final String SQL_CREATE_ENTERIES = "CREATE TABLE " +
            Habitentry.TABLE_NAME + "(" + Habitentry._ID + " INTEGER PRIMARY KEY," +
            Habitentry.COLUMN_HABIT_NAME + " TEXT NOT NULL, " + Habitentry.COLUMN_NUMBER_OF_DAYS + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Habitentry.TABLE_NAME;

    public HabitTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

