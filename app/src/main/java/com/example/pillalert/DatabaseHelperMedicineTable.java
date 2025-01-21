package com.example.pillalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelperMedicineTable {
    private DatabaseHelper dbHelper;

    // Table and column definitions specific to Medicine
    public static final String TABLE_MEDICINE = "medicine";
    public static final String MEDICINE_ID = "id";
    public static final String MEDICINE_DISEASE_ID = "disease_id";
    public static final String MEDICINE_NAME = "name";
    public static final String MEDICINE_DESCRIPTION = "description";
    public static final String MEDICINE_UNIT = "unit";
    public static final String MEDICINE_DOSE_PER_DAY = "dose_perday";
    public static final String MEDICINE_DOSE_PER_CONSUME = "dose_perconsume";
    public static final String MEDICINE_AMOUNT = "medicine_amount";
    public static final String MEDICINE_CONSUME_START_DATE = "consume_start_date";
    public static final String MEDICINE_CONSUME_END_DATE = "consume_end_date";

    public DatabaseHelperMedicineTable(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static String sqlCreateTable() {
        return "CREATE TABLE medicine (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "disease_id INTEGER, " +
                "name TEXT, " +
                "description TEXT, " +
                "unit INTEGER, " +
                "dose_perday INTEGER, " +
                "dose_perconsume INTEGER, " +
                "medicine_amount INTEGER, " +
                "consume_start_date TEXT, " +
                "consume_end_date TEXT, " +
                "FOREIGN KEY(disease_id) REFERENCES disease(id))"
        ;
    }

    public long addMedicine(int diseaseId, String name, String description, MedicineUnitEnum unit, int dosePerDay, int dosePerConsume, int amount, String startDate, String endDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MEDICINE_DISEASE_ID, diseaseId);
        values.put(MEDICINE_NAME, name);
        values.put(MEDICINE_DESCRIPTION, description);
        values.put(MEDICINE_UNIT, unit.getValue());
        values.put(MEDICINE_DOSE_PER_DAY, dosePerDay);
        values.put(MEDICINE_DOSE_PER_CONSUME, dosePerConsume);
        values.put(MEDICINE_AMOUNT, amount);
        values.put(MEDICINE_CONSUME_START_DATE, startDate);
        values.put(MEDICINE_CONSUME_END_DATE, endDate);
        return db.insert(TABLE_MEDICINE, null, values);
    }

    public Cursor getAllMedicine() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEDICINE, null);
    }

    public int updateMedicine(int id, int diseaseId, String name, String description, int unit, int dosePerDay, int dosePerConsume, int amount, String startDate, String endDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MEDICINE_DISEASE_ID, diseaseId);
        values.put(MEDICINE_NAME, name);
        values.put(MEDICINE_DESCRIPTION, description);
        values.put(MEDICINE_UNIT, unit);
        values.put(MEDICINE_DOSE_PER_DAY, dosePerDay);
        values.put(MEDICINE_DOSE_PER_CONSUME, dosePerConsume);
        values.put(MEDICINE_AMOUNT, amount);
        values.put(MEDICINE_CONSUME_START_DATE, startDate);
        values.put(MEDICINE_CONSUME_END_DATE, endDate);
        return db.update(TABLE_MEDICINE, values, MEDICINE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteMedicine(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(TABLE_MEDICINE, MEDICINE_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
