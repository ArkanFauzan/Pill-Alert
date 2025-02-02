package com.example.pillalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
                "FOREIGN KEY(medicine_id) REFERENCES medicine(id) ON DELETE CASCADE)"
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

    public List<MedicineTrackingModel> getAllMedicineTracking() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEDICINE_TRACKING, null);
        return mapDataToModel(cursor);
    }

    public List<MedicineTrackingModel> getAllMedicineTrackingByTargetDate(String date) { // 2025-01-21 16:00
        String[] split = date.split("\\s+");
        date = split.length > 0 ? split[0] : ""; // validate to get only date, not include time

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEDICINE_TRACKING + " WHERE " + MEDICINE_TRACKING_TARGET_DATE + " LIKE '"+date+"%' ORDER by " + MEDICINE_TRACKING_TARGET_DATE + " ASC", null);
        return mapDataToModel(cursor);
    }

    public List<MedicineTrackingModel> getMedicineTrackingByMedicineId(int medicineId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEDICINE_TRACKING,
                null,
                MEDICINE_TRACKING_MEDICINE_ID + " = ?",
                new String[]{String.valueOf(medicineId)},
                null, null, null);
        return mapDataToModel(cursor);
    }

    public MedicineTrackingModel getMedicineTrackingById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEDICINE_TRACKING,
                null,
                MEDICINE_TRACKING_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
        List<MedicineTrackingModel> data = mapDataToModel(cursor);
        return ((long) data.size()) > 0 ? data.get(0) : null;
    }

    public int updateMedicineTracking(int id, int medicineId, String consumeDate, MedicineTrackingTypeEnum trackingType, String notes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MEDICINE_TRACKING_MEDICINE_ID, medicineId);
        // values.put(MEDICINE_TRACKING_TARGET_DATE, targetDate);
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

    private List<MedicineTrackingModel> mapDataToModel(Cursor data) {
        List<MedicineTrackingModel> result = new ArrayList<>();
        try {
            if (data != null && data.moveToFirst()) {
                do {
                    // Extract data from the cursor
                    int id = data.getInt(data.getColumnIndexOrThrow("id"));
                    int medicineId = data.getInt(data.getColumnIndexOrThrow("medicine_id"));
                    String targetDate = data.getString(data.getColumnIndexOrThrow("target_date"));
                    String consumeDate = data.getString(data.getColumnIndexOrThrow("consume_date"));
                    MedicineTrackingTypeEnum trackingType = MedicineTrackingTypeEnum.fromValue(data.getInt(data.getColumnIndexOrThrow("tracking_type")));
                    String notes = data.getString(data.getColumnIndexOrThrow("notes"));

                    result.add(new MedicineTrackingModel(id, medicineId, targetDate, consumeDate, trackingType, notes));

                } while (data.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error fetching data: " + e.getMessage());
        }
        return result;
    }
}
