package com.example.pillalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelperDiseaseTable {
    private DatabaseHelper dbHelper;

    // Table and column definitions specific to Disease
    public static final String TABLE_DISEASE = "disease";
    public static final String DISEASE_ID = "id";
    public static final String DISEASE_NAME = "name";
    public static final String DISEASE_DESCRIPTION = "description";
    public static final String DISEASE_TREATMENT_DATE = "treatment_date";

    public DatabaseHelperDiseaseTable(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static String sqlCreateTable() {
        return "CREATE TABLE disease (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT, " +
                "treatment_date TEXT)"
        ;
    }

    public long add (String name, String description, String treatmentDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISEASE_NAME, name);
        values.put(DISEASE_DESCRIPTION, description);
        values.put(DISEASE_TREATMENT_DATE, treatmentDate);
        return db.insert(TABLE_DISEASE, null, values);
    }

    public Cursor getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DISEASE, null);
    }

    public int update(int id, String name, String description, String treatmentDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISEASE_NAME, name);
        values.put(DISEASE_DESCRIPTION, description);
        values.put(DISEASE_TREATMENT_DATE, treatmentDate);
        return db.update(TABLE_DISEASE, values, DISEASE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(TABLE_DISEASE, DISEASE_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
