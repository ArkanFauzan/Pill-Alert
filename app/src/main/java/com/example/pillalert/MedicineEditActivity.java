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

public class MedicineEditActivity extends BaseActivity {

    private DatabaseHelperMedicineTable medicineTable;
    private LinearLayout editMedicineScheduleContainer;
    private TextView dosePerConsumeText, amountText;
    private EditText nameEditText, descriptionEditText, dosePerDayEditText, dosePerConsumeEditText, amountEditText, startDateEditText, startTimeEditText, endDateEditText;
    private Spinner unitSpinner;
    private Button updateButton, cancelButton;
    private int medicineId, diseaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_edit);
        setupBottomNavigation(R.id.menu_manage_schedule);

        // Initialize database
        medicineTable = new DatabaseHelperMedicineTable(this);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        descriptionEditText = findViewById(R.id.editTextDescription);
        unitSpinner = findViewById(R.id.spinnerUnit);
        dosePerDayEditText = findViewById(R.id.editTextDosePerDay);
        dosePerConsumeText = findViewById(R.id.textDosePerConsume);
        dosePerConsumeEditText = findViewById(R.id.editTextDosePerConsume);
        amountText = findViewById(R.id.textAmount);
        amountEditText = findViewById(R.id.editTextAmount);
        startDateEditText = findViewById(R.id.editTextStartDate);
        startTimeEditText = findViewById(R.id.editTextStartTime);
        endDateEditText = findViewById(R.id.editTextEndDate);
        updateButton = findViewById(R.id.buttonUpdate);
        cancelButton = findViewById(R.id.buttonCancel);

        // Hide all setting for edit schedule
        editMedicineScheduleContainer = findViewById(R.id.editMedicineScheduleContainer);
        editMedicineScheduleContainer.setVisibility(View.GONE);

        // Retrieve the medicine ID from the intent
        Intent intent = getIntent();
        medicineId = intent.getIntExtra("id", -1);
        diseaseId = intent.getIntExtra("diseaseId", -1);

        if (medicineId == -1) {
            Toast.makeText(this, "Invalid Medicine ID", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if ID is invalid
            return;
        }

        // Populate the unit spinner
        populateUnitSpinner();

        // Set onChange unitSpinner
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUnit = parent.getItemAtPosition(position).toString();

                dosePerConsumeText.setText("Dosis per Konsumsi (" + selectedUnit +")");
                amountText.setText("Jumlah Obat yang Diberikan (" + selectedUnit +")");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Populate fields with existing data
        loadMedicineData(medicineId);

        // Set up DatePickers for date fields
        startDateEditText.setOnClickListener(v -> showDatePicker(startDateEditText));
        startTimeEditText.setOnClickListener(v -> showTimePicker(startTimeEditText));
        endDateEditText.setOnClickListener(v -> showDatePicker(endDateEditText));

        // Handle update button click
        updateButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String unitString = unitSpinner.getSelectedItem().toString();
            String dosePerDay = dosePerDayEditText.getText().toString().trim();
            String dosePerConsume = dosePerConsumeEditText.getText().toString().trim();
            String amount = amountEditText.getText().toString().trim();
            String startDate = startDateEditText.getText().toString().trim();
            String startTime = startTimeEditText.getText().toString().trim();
            String endDate = endDateEditText.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || dosePerDay.isEmpty() || dosePerConsume.isEmpty() || amount.isEmpty() || startDate.isEmpty() || startTime.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(MedicineEditActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                MedicineUnitEnum unit = getSelectedUnit(unitString);

                int result = medicineTable.updateMedicine(
                        medicineId,
                        diseaseId,
                        name,
                        description,
                        unit.getValue(),
                        Integer.parseInt(dosePerDay),
                        Integer.parseInt(dosePerConsume),
                        Integer.parseInt(amount),
                        startDate + " " + startTime,
                        endDate
                );

                if (result < 0) {
                    Toast.makeText(MedicineEditActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Navigate Back
                finish();
            } catch (IllegalArgumentException e) {
                Toast.makeText(MedicineEditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Handle cancel button click
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadMedicineData(int id) {
        MedicineModel medicine = medicineTable.getMedicineById(id);
        if (medicine != null) {
            nameEditText.setText(medicine.getName());
            descriptionEditText.setText(medicine.getDescription());
            dosePerDayEditText.setText(String.valueOf(medicine.getDosePerDay()));
            dosePerConsumeEditText.setText(String.valueOf(medicine.getDosePerConsume()));
            amountEditText.setText(String.valueOf(medicine.getAmount()));
            startDateEditText.setText(medicine.getStartDateOnlyDate());
            startTimeEditText.setText(medicine.getStartDateOnlyTime());
            endDateEditText.setText(medicine.getEndDate());
            unitSpinner.setSelection(getUnitPosition(medicine.getUnit()));
        } else {
            Toast.makeText(this, "Medicine not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void populateUnitSpinner() {
        List<String> unitList = new ArrayList<>();
        for (MedicineUnitEnum unit : MedicineUnitEnum.values()) {
            unitList.add(unit.getEnglishTranslation());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
    }

    private int getUnitPosition(MedicineUnitEnum unit) {
        for (int i = 0; i < MedicineUnitEnum.values().length; i++) {
            if (MedicineUnitEnum.values()[i] == unit) {
                return i;
            }
        }
        return 0; // Default to the first unit
    }

    private MedicineUnitEnum getSelectedUnit(String unitString) {
        for (MedicineUnitEnum unit : MedicineUnitEnum.values()) {
            if (unit.getEnglishTranslation().equals(unitString)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid unit: " + unitString);
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
                    if (targetEditText == startDateEditText) {
                        showTimePicker(startTimeEditText);
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
