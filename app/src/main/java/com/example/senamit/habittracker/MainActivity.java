package com.example.senamit.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senamit.habittracker.data.HabitTrackerDbHelper;
import com.example.senamit.habittracker.data.HabitContract.*;

public class MainActivity extends AppCompatActivity {

    private HabitTrackerDbHelper habitTrackerDbHelper;
    private SQLiteDatabase db;
    private EditText edtHabitName;
    private EditText edtDays;
    Cursor cursor;
    private TextView displayHabit;
    private int habitDays = 0;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHabitName = (EditText) findViewById(R.id.edt_habit_name);
        edtDays = (EditText) findViewById(R.id.edt_days);
        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        displayHabit = (TextView) findViewById(R.id.display_habit);

        habitTrackerDbHelper = new HabitTrackerDbHelper(MainActivity.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                cursor = queryAllHabits();
                displayDatabaseInfo(cursor);
                edtHabitName.setText("");
                edtDays.setText("");
            }

            private void insertData() {
                db = habitTrackerDbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                String habitName = edtHabitName.getText().toString().trim();
                if (!TextUtils.isEmpty(habitName)) {
                    contentValues.put(Habitentry.COLUMN_HABIT_NAME, habitName);
                }

                String habitNumberOfDays = edtDays.getText().toString();
                if (!TextUtils.isEmpty(habitNumberOfDays)) {
                    habitDays = Integer.parseInt(habitNumberOfDays);
                    contentValues.put(Habitentry.COLUMN_NUMBER_OF_DAYS, habitDays);
                }

                long RowId = db.insert(Habitentry.TABLE_NAME, null, contentValues);
                if (RowId == -1) {
                    Toast.makeText(MainActivity.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cursor = queryAllHabits();
        displayDatabaseInfo(cursor);
    }

    private Cursor queryAllHabits() {
        db = habitTrackerDbHelper.getReadableDatabase();
        String[] projection = {Habitentry._ID, Habitentry.COLUMN_HABIT_NAME, Habitentry.COLUMN_NUMBER_OF_DAYS};
        Cursor cursor = db.query(
                Habitentry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private void displayDatabaseInfo(Cursor cursor) {

        displayHabit.setText("the number of rows is " + cursor.getCount());
        try {
            int idColumnIndex = cursor.getColumnIndex(Habitentry._ID);
            int habitNameColumnIndex = cursor.getColumnIndex(Habitentry.COLUMN_HABIT_NAME);
            int numberOfDaysColumnIndex = cursor.getColumnIndex(Habitentry.COLUMN_NUMBER_OF_DAYS);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentHabitName = cursor.getString(habitNameColumnIndex);
                String currentNumberOfDays = cursor.getString(numberOfDaysColumnIndex);

                displayHabit.append(("\n" + currentID + " - " +
                        currentHabitName + " - " +
                        currentNumberOfDays));
            }
        } finally {
            cursor.close();
        }
    }

}
