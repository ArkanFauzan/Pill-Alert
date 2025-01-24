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

public class MedicineEditActivity extends AppCompatActivity {

    private DatabaseHelperMedicineTable medicineTable;
    private EditText nameEditText, descriptionEditText, dosePerDayEditText, dosePerConsumeEditText, amountEditText, startDateEditText, endDateEditText;
    private Spinner unitSpinner;
    private Button updateButton, cancelButton;
    private int medicineId, diseaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_edit);

        // Initialize database
        medicineTable = new DatabaseHelperMedicineTable(this);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        descriptionEditText = findViewById(R.id.editTextDescription);
        unitSpinner = findViewById(R.id.spinnerUnit);
        dosePerDayEditText = findViewById(R.id.editTextDosePerDay);
        dosePerConsumeEditText = findViewById(R.id.editTextDosePerConsume);
        amountEditText = findViewById(R.id.editTextAmount);
        startDateEditText = findViewById(R.id.editTextStartDate);
        endDateEditText = findViewById(R.id.editTextEndDate);
        updateButton = findViewById(R.id.buttonUpdate);
        cancelButton = findViewById(R.id.buttonCancel);

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

        // Populate fields with existing data
        loadMedicineData(medicineId);

        // Set up DatePickers for date fields
        startDateEditText.setOnClickListener(v -> showDatePicker(startDateEditText));
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
            String endDate = endDateEditText.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || dosePerDay.isEmpty() || dosePerConsume.isEmpty() || amount.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
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
                        startDate,
                        endDate
                );

                if (result < 0) {
                    Toast.makeText(MedicineEditActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Navigate to Disease Detail Activity
                Intent intentToDiseaseDetail = new Intent(this, DiseaseDetailActivity.class);
                intentToDiseaseDetail.putExtra("id", diseaseId); // parsing id to Disease Detail Activity
                startActivity(intentToDiseaseDetail);
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
            startDateEditText.setText(medicine.getStartDate());
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
                }, year, month, day);

        datePickerDialog.show();
    }
}
