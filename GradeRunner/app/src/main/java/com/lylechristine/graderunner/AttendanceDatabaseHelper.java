package com.lylechristine.graderunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AttendanceDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "attendance.db";
    public static final String TABLE_NAME = "attendance_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "COURSENAME";
    public static final String COL3 = "LECTURES";
    public static final String COL4 = "TUTORIALS";
    public static final String COL5 = "LABS";

    public AttendanceDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Initialises the database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " COURSENAME TEXT, LECTURES TEXT, TUTORIALS TEXT, LABS TEXT)";
        db.execSQL(createTable);
    }

    //Assists in upgrading the database to a new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Writes the text values to the approprate database columns
    public boolean addData(String attCourse, String attLectures, String attTutorials, String attLabs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, attCourse);
        contentValues.put(COL3, attLectures);
        contentValues.put(COL4, attTutorials);
        contentValues.put(COL5, attTutorials);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Cursor object returns all data from the database
    public Cursor showData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //Writes new data to the database while ignoring any empty text fields
    public boolean updateData(String id, String courseName, String lectures, String tutorials, String labs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (courseName != null && courseName.length() > 0) {
            contentValues.put(COL2, courseName);
        }
        if (lectures != null && lectures.length() > 0) {
            contentValues.put(COL3, lectures);
        }
        if (tutorials != null && tutorials.length() > 0) {
            contentValues.put(COL4, tutorials);
        }
        if (labs != null && labs.length() > 0) {
            contentValues.put(COL5, labs);
        }
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    //Assists in deleting a row from the database based on an ID
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
