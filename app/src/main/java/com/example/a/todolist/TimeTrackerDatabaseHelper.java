package com.example.a.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by A on 2017-01-01.
 */
public class TimeTrackerDatabaseHelper extends Activity{
    public static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "timetracker.db";
    private static final String TABLE_NAME = "timerecords";

    public static final String TIMETRACKER_COLUMN_ID = "_id";
    public static final String TIMETRACKER_COLUMN_TIME = "time";
    public static final String TIMETRACKER_COLUMN_DATE = "date";
    public static final String TIMETRACKER_COLUMN_NOTES = "notes";

    private TimeTrackerOpenHelper openHelper;
    private SQLiteDatabase database;

    public TimeTrackerDatabaseHelper(Context context) {
        openHelper = new TimeTrackerOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public void saveTimeRecord(String time, String notes, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMETRACKER_COLUMN_TIME, time);
        contentValues.put(TIMETRACKER_COLUMN_DATE, date);
        contentValues.put(TIMETRACKER_COLUMN_NOTES, notes);
        database.insert(TABLE_NAME, null, contentValues);
    }


    public Cursor getTimeRecordList() {
        return database.rawQuery("select * from " + TABLE_NAME, null);

    }

    public int clearTimeRecord() {
        return database.delete(TABLE_NAME, null, null);

    }

    public boolean delteTimeRecord(long id) {
        return database.delete(TABLE_NAME, "_id= " + id , null) > 0;
    }

    public boolean updateTimeRecord(String time, String notes, String date, long id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMETRACKER_COLUMN_TIME, time);
        contentValues.put(TIMETRACKER_COLUMN_DATE, date);
        contentValues.put(TIMETRACKER_COLUMN_NOTES, notes);
        return database.update(TABLE_NAME, contentValues, TIMETRACKER_COLUMN_ID + "=" + id, null) != 0;

    }


    private class TimeTrackerOpenHelper extends SQLiteOpenHelper {
        TimeTrackerOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE " + TABLE_NAME + "( "
                            + TIMETRACKER_COLUMN_ID + " INTEGER PRIMARY KEY, "
                            + TIMETRACKER_COLUMN_TIME + " TEXT, "
                            + TIMETRACKER_COLUMN_DATE + " TEXT, "
                            + TIMETRACKER_COLUMN_NOTES + " TEXT )"
            );
        }

        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(database);
        }
    }
}

