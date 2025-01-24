package com.example.pillalert;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicineAddActivity extends AppCompatActivity {

    private DatabaseHelperMedicineTable medicineTable;
    private EditText nameEditText, descriptionEditText, dosePerDayEditText, dosePerConsumeEditText, amountEditText, startDateEditText, endDateEditText;
    private Spinner unitSpinner;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);

        // Initialize database
        medicineTable = new DatabaseHelperMedicineTable(this);

        // Get data from intent
        int diseaseId = getIntent().getIntExtra("diseaseId", -1);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        descriptionEditText = findViewById(R.id.editTextDescription);
        unitSpinner = findViewById(R.id.spinnerUnit);
        dosePerDayEditText = findViewById(R.id.editTextDosePerDay);
        dosePerConsumeEditText = findViewById(R.id.editTextDosePerConsume);
        amountEditText = findViewById(R.id.editTextAmount);
        startDateEditText = findViewById(R.id.editTextStartDate);
        endDateEditText = findViewById(R.id.editTextEndDate);
        saveButton = findViewById(R.id.buttonUpdate);
        cancelButton = findViewById(R.id.buttonCancel);

        // Populate the unit spinner
        populateUnitSpinner();

        // Set up DatePickers for date fields
        startDateEditText.setOnClickListener(v -> showDatePicker(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePicker(endDateEditText));

        // Handle save button click
        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String unitString = unitSpinner.getSelectedItem().toString(); // Get selected unit as string
            String dosePerDay = dosePerDayEditText.getText().toString().trim();
            String dosePerConsume = dosePerConsumeEditText.getText().toString().trim();
            String amount = amountEditText.getText().toString().trim();
            String startDate = startDateEditText.getText().toString().trim();
            String endDate = endDateEditText.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || dosePerDay.isEmpty() || dosePerConsume.isEmpty() || amount.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(MedicineAddActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                MedicineUnitEnum unit = getSelectedUnit(unitString); // Convert string to MedicineUnitEnum

                long result = medicineTable.addMedicine(
                        diseaseId,
                        name,
                        description,
                        unit, // Use MedicineUnitEnum here
                        Integer.parseInt(dosePerDay),
                        Integer.parseInt(dosePerConsume),
                        Integer.parseInt(amount),
                        startDate,
                        endDate
                );

                if (result == -1) {
                    Toast.makeText(MedicineAddActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Navigate to Disease Detail Activity
                Intent intentToDiseaseDetail = new Intent(this, DiseaseDetailActivity.class);
                intentToDiseaseDetail.putExtra("id", diseaseId); // parsing id to Disease Detail Activity
                startActivity(intentToDiseaseDetail);
                finish();
            } catch (IllegalArgumentException e) {
                Toast.makeText(MedicineAddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Handle cancel button click
        cancelButton.setOnClickListener(v -> finish());
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

    private MedicineUnitEnum getSelectedUnit(String unitString) {
        for (MedicineUnitEnum unit : MedicineUnitEnum.values()) {
            if (unit.getEnglishTranslation().equals(unitString)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid unit: " + unitString);
    }

    private void showDatePicker(EditText targetEditText) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Set selected date to the EditText
                    String monthTwoDigit = selectedMonth + 1 < 10 ? "0" + (selectedMonth + 1) : String.valueOf(selectedMonth + 1);
                    String dayTwoDigit = selectedDay < 10 ? "0" + selectedDay : String.valueOf(selectedDay);

                    String selectedDate = selectedYear + "-" + monthTwoDigit + "-" + dayTwoDigit;
                    targetEditText.setText(selectedDate);
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}
