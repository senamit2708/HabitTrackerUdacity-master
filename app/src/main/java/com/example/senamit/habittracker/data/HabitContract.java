package com.example.senamit.habittracker.data;

import android.provider.BaseColumns;


public class HabitContract {

    private HabitContract() {
    }

    public static class Habitentry implements BaseColumns {
        public static final String TABLE_NAME = "Entry";
        public static final String COLUMN_HABIT_NAME = "HabitName";
        public static final String COLUMN_NUMBER_OF_DAYS = "NoOfDays";
    }
}

