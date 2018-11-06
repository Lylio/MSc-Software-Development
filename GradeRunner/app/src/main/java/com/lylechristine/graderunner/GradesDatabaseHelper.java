package com.lylechristine.graderunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GradesDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "grades.db";
    public static final String TABLE_NAME = "grades_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "COURSENAME";
    public static final String COL3 = "ASSIGNMENT";
    public static final String COL4 = "GRADE";
    public static final String COL5 = "FEEDBACK";

    public GradesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Initialises the database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " COURSENAME TEXT, ASSIGNMENT TEXT, GRADE TEXT, FEEDBACK TEXT)";
        db.execSQL(createTable);

    }

    //Assists in upgrading the database to a new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Writes the text values to the approprate database columns
    public boolean addData(String courseName, String assignment, String grade, String feedback) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, courseName);
        contentValues.put(COL3, assignment);
        contentValues.put(COL4, grade);
        contentValues.put(COL5, feedback);

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
    public boolean updateData(String id, String courseName, String assignment, String grade, String feedback) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (courseName != null && courseName.length() > 0) {
            contentValues.put(COL2, courseName);
        }
        if (assignment != null && assignment.length() > 0) {
            contentValues.put(COL3, assignment);
        }
        if (grade != null && grade.length() > 0) {
            contentValues.put(COL4, grade);
        }
        if (feedback != null && feedback.length() > 0) {
            contentValues.put(COL5, feedback);
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
