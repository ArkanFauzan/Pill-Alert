package com.example.pillalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public long addDisease (String name, String description, String treatmentDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISEASE_NAME, name);
        values.put(DISEASE_DESCRIPTION, description);
        values.put(DISEASE_TREATMENT_DATE, treatmentDate);
        return db.insert(TABLE_DISEASE, null, values);
    }

    public List<DiseaseModel> getAllDisease() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_DISEASE + " ORDER BY treatment_date DESC", null);
        return mapDataToModel(result);
    }

    public DiseaseModel getDiseaseById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_DISEASE + " WHERE  id = " + id, null);
        List<DiseaseModel> data = mapDataToModel(result);
        return ((long) data.size()) > 0 ? data.get(0) : null;
    }

    public int updateDisease(int id, String name, String description, String treatmentDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISEASE_NAME, name);
        values.put(DISEASE_DESCRIPTION, description);
        values.put(DISEASE_TREATMENT_DATE, treatmentDate);
        return db.update(TABLE_DISEASE, values, DISEASE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteDisease(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(TABLE_DISEASE, DISEASE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    private List<DiseaseModel> mapDataToModel(Cursor data) {
        List<DiseaseModel> result = new ArrayList<>();
        try {
            if (data != null && data.moveToFirst()) {
                do {
                    // Extract data from the cursor
                    int id = data.getInt(data.getColumnIndexOrThrow("id"));
                    String name = data.getString(data.getColumnIndexOrThrow("name"));
                    String description = data.getString(data.getColumnIndexOrThrow("description"));
                    String treatmentDate = data.getString(data.getColumnIndexOrThrow("treatment_date"));

                    result.add(new DiseaseModel(id, name, description, treatmentDate));

                } while (data.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error fetching data: " + e.getMessage());
        }
        return result;
    }
}
