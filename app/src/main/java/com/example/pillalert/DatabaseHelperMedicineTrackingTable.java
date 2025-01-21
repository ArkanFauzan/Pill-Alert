package com.example.pillalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelperMedicineTrackingTable {
    private DatabaseHelper dbHelper;

    // Table and column definitions specific to Medicine
    public static final String TABLE_MEDICINE_TRACKING = "medicine_tracking";
    public static final String MEDICINE_TRACKING_ID = "id";
    public static final String MEDICINE_TRACKING_MEDICINE_ID = "medicine_id";
    public static final String MEDICINE_TRACKING_TARGET_DATE = "target_date";
    public static final String MEDICINE_TRACKING_CONSUME_DATE = "consume_date";
    public static final String MEDICINE_TRACKING_TYPE = "tracking_type";
    public static final String MEDICINE_TRACKING_NOTES = "notes";

    public DatabaseHelperMedicineTrackingTable(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static String sqlCreateTable() {
        return "CREATE TABLE medicine_tracking (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "medicine_id INTEGER, " +
                "target_date TEXT, " +
                "consume_date TEXT, " +
                "tracking_type INTEGER, " +
                "notes TEXT, " +
                "FOREIGN KEY(medicine_id) REFERENCES medicine(id))"
        ;
    }

    public long addMedicineTracking(int medicineId, String targetDate, String consumeDate, MedicineTrackingTypeEnum trackingType, String notes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MEDICINE_TRACKING_MEDICINE_ID, medicineId);
        values.put(MEDICINE_TRACKING_TARGET_DATE, targetDate);
        values.put(MEDICINE_TRACKING_CONSUME_DATE, consumeDate);
        values.put(MEDICINE_TRACKING_TYPE, trackingType.getValue());
        values.put(MEDICINE_TRACKING_NOTES, notes);
        return db.insert(TABLE_MEDICINE_TRACKING, null, values);
    }

    public Cursor getAllMedicineTracking() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEDICINE_TRACKING, null);
    }

    public Cursor getMedicineTrackingByMedicineId(int medicineId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(TABLE_MEDICINE_TRACKING,
                null,
                MEDICINE_TRACKING_MEDICINE_ID + " = ?",
                new String[]{String.valueOf(medicineId)},
                null, null, null);
    }

    public int updateMedicineTracking(int id, int medicineId, String targetDate, String consumeDate, MedicineTrackingTypeEnum trackingType, String notes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MEDICINE_TRACKING_MEDICINE_ID, medicineId);
        values.put(MEDICINE_TRACKING_TARGET_DATE, targetDate);
        values.put(MEDICINE_TRACKING_CONSUME_DATE, consumeDate);
        values.put(MEDICINE_TRACKING_TYPE, trackingType.getValue());
        values.put(MEDICINE_TRACKING_NOTES, notes);
        return db.update(TABLE_MEDICINE_TRACKING, values, MEDICINE_TRACKING_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteMedicineTracking(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(TABLE_MEDICINE_TRACKING, MEDICINE_TRACKING_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteMedicineTrackingByMedicineId(int medicineId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(TABLE_MEDICINE_TRACKING, MEDICINE_TRACKING_MEDICINE_ID + " = ?", new String[]{String.valueOf(medicineId)});
    }
}
