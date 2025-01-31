package com.example.pillalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
    public static final String MEDICINE_HAS_TRACKING_DATA = "has_tracking_data";

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
                "has_tracking_data INTEGER DEFAULT 0, " +
                "FOREIGN KEY(disease_id) REFERENCES disease(id) ON DELETE CASCADE)"
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
        values.put(MEDICINE_HAS_TRACKING_DATA, Boolean.FALSE);
        return db.insert(TABLE_MEDICINE, null, values);
    }

    public List<MedicineModel> getAllMedicine(int diseaseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEDICINE  + " WHERE  disease_id = " + diseaseId, null);
        return mapDataToModel(cursor);
    }

    public MedicineModel getMedicineById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_MEDICINE + " WHERE  id = " + id, null);
        List<MedicineModel> data = mapDataToModel(result);
        return ((long) data.size()) > 0 ? data.get(0) : null;
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

    public int updateMedicineHasTrackingData(int id, Boolean hasTrackingData) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MEDICINE_HAS_TRACKING_DATA, hasTrackingData);
        return db.update(TABLE_MEDICINE, values, MEDICINE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteMedicine(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(TABLE_MEDICINE, MEDICINE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    private List<MedicineModel> mapDataToModel(Cursor data) {
        List<MedicineModel> result = new ArrayList<>();
        try {
            if (data != null && data.moveToFirst()) {
                do {
                    // Extract data from the cursor
                    int id = data.getInt(data.getColumnIndexOrThrow("id"));
                    int diseaseId = data.getInt(data.getColumnIndexOrThrow("disease_id"));
                    String name = data.getString(data.getColumnIndexOrThrow("name"));
                    String description = data.getString(data.getColumnIndexOrThrow("description"));
                    MedicineUnitEnum unit = MedicineUnitEnum.fromValue(data.getInt(data.getColumnIndexOrThrow("unit")));
                    int dosePerDay = data.getInt(data.getColumnIndexOrThrow("dose_perday"));
                    int dosePerConsume = data.getInt(data.getColumnIndexOrThrow("dose_perconsume"));
                    int amount = data.getInt(data.getColumnIndexOrThrow("medicine_amount"));
                    String startDate = data.getString(data.getColumnIndexOrThrow("consume_start_date"));
                    String endDate = data.getString(data.getColumnIndexOrThrow("consume_end_date"));

                    result.add(new MedicineModel(id, diseaseId, name, description, unit, dosePerDay, dosePerConsume, amount, startDate, endDate));

                } while (data.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error fetching data: " + e.getMessage());
        }
        return result;
    }
}
