package com.example.pillalert;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicineTrackingEditActivity extends AppCompatActivity {

    private DatabaseHelperMedicineTrackingTable medicineTrackingTable;
    private TextView targetDateText;
    private LinearLayout dateContainer;
    private EditText consumeDateEditText, consumeDateTimeEditText;
    private Spinner typeSpinner;
    private Button updateButton, cancelButton;
    private int medicineTrackingId, medicineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_tracking_edit);

        // Initialize database
        medicineTrackingTable = new DatabaseHelperMedicineTrackingTable(this);

        // Initialize views
        targetDateText = findViewById(R.id.targetDateText);
        typeSpinner = findViewById(R.id.spinnerType);
        dateContainer = findViewById(R.id.dateContainer);
        consumeDateEditText = findViewById(R.id.editTextConsumeDate);
        consumeDateTimeEditText = findViewById(R.id.editTextConsumeDateTime);
        updateButton = findViewById(R.id.buttonUpdate);
        cancelButton = findViewById(R.id.buttonCancel);

        // Retrieve the medicine ID from the intent
        Intent intent = getIntent();
        medicineTrackingId = intent.getIntExtra("id", -1);
        medicineId = intent.getIntExtra("medicineId", -1);

        if (medicineTrackingId == -1) {
            Toast.makeText(this, "Invalid Medicine Tracking ID", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if ID is invalid
            return;
        }

        // Populate the type spinner
        populateTypeSpinner();

        // Set onChange unitSpinner
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUnit = parent.getItemAtPosition(position).toString();

                if (selectedUnit.equals("Ya")) {
                    dateContainer.setVisibility(View.VISIBLE);
                }
                else {
                    consumeDateEditText.setText("");
                    consumeDateTimeEditText.setText("");
                    dateContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Populate fields with existing data
        loadMedicineTrackingData(medicineTrackingId);

        // Set up DatePickers for date fields
        consumeDateEditText.setOnClickListener(v -> showDatePicker(consumeDateEditText));
        consumeDateTimeEditText.setOnClickListener(v -> showTimePicker(consumeDateTimeEditText));

        // Handle update button click
        updateButton.setOnClickListener(v -> {
            String consumeDate = consumeDateEditText.getText().toString().trim();
            String consumeDateTime = consumeDateTimeEditText.getText().toString().trim();
            String typeString = typeSpinner.getSelectedItem().toString();

            if (typeString.equals("Ya") && (consumeDate.isEmpty() || consumeDateTime.isEmpty())) {
                Toast.makeText(MedicineTrackingEditActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                MedicineTrackingTypeEnum trackingTypeEnum;
                if (typeString.equals("Tidak")) {
                    trackingTypeEnum = MedicineTrackingTypeEnum.Forgot;
                }
                else {
                    // dummy, next: with real calculation by consume date
                    trackingTypeEnum = MedicineTrackingTypeEnum.OnTime;
                }

                int result = medicineTrackingTable.updateMedicineTracking(
                        medicineTrackingId,
                        medicineId,
                        consumeDate + " " + consumeDateTime,
                        trackingTypeEnum,
                        ""
                );

                if (result < 0) {
                    Toast.makeText(MedicineTrackingEditActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Navigate Back
                finish();
            } catch (IllegalArgumentException e) {
                Toast.makeText(MedicineTrackingEditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Handle cancel button click
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadMedicineTrackingData(int id) {
        MedicineTrackingModel medicineTracking = medicineTrackingTable.getMedicineTrackingById(id);
        if (medicineTracking != null) {
            targetDateText.setText(medicineTracking.getTargetDate());
            consumeDateEditText.setText(medicineTracking.getConsumeDateOnlyDate());
            consumeDateTimeEditText.setText(medicineTracking.getConsumeDateOnlyTime());

            if (medicineTracking.getTrackingType() == MedicineTrackingTypeEnum.Forgot) {
                typeSpinner.setSelection(1); // Tidak
            }
            else {
                typeSpinner.setSelection(0); // Ya
            }
        } else {
            Toast.makeText(this, "Medicine tracking not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void populateTypeSpinner() {
        List<String> unitList = new ArrayList<>();
        unitList.add("Ya");
        unitList.add("Tidak");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private MedicineTrackingTypeEnum getSelectedType(String typeString) {
        for (MedicineTrackingTypeEnum unit : MedicineTrackingTypeEnum.values()) {
            if (unit.getEnglishTranslation().equals(typeString)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid unit: " + typeString);
    }

    private void showDatePicker(EditText targetEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String monthTwoDigit = selectedMonth + 1 < 10 ? "0" + (selectedMonth + 1) : String.valueOf(selectedMonth + 1);
                    String dayTwoDigit = selectedDay < 10 ? "0" + selectedDay : String.valueOf(selectedDay);

                    String selectedDate = selectedYear + "-" + monthTwoDigit + "-" + dayTwoDigit;
                    targetEditText.setText(selectedDate);

                    // Automatically show time picker after date selection
                    if (targetEditText == consumeDateEditText) {
                        showTimePicker(consumeDateTimeEditText);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker(EditText targetTimeEditText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", selectedHour, selectedMinute);
            targetTimeEditText.setText(time);
        }, hour, minute, true);
        timePickerDialog.show();
    }
}
