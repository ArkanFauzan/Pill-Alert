package com.example.pillalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PillAlert.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute create query from all table
        db.execSQL(DatabaseHelperDiseaseTable.sqlCreateTable());
        db.execSQL(DatabaseHelperMedicineTable.sqlCreateTable());
        db.execSQL(DatabaseHelperMedicineTrackingTable.sqlCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS medicine_tracking");
        db.execSQL("DROP TABLE IF EXISTS medicine");
        db.execSQL("DROP TABLE IF EXISTS disease");
        onCreate(db);
    }
}
