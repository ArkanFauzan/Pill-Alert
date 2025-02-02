package com.example.pillalert;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DiseaseEditActivity extends BaseActivity {

    private DatabaseHelperDiseaseTable diseaseTable;
    private EditText nameEditText, descriptionEditText;
    private EditText dateEditText;
    private Button updateButton, cancelButton;
    private int diseaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_edit);
        setupBottomNavigation(R.id.menu_manage_schedule);

        // Initialize database
        diseaseTable = new DatabaseHelperDiseaseTable(this);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        descriptionEditText = findViewById(R.id.editTextDescription);
        dateEditText = findViewById(R.id.editTextDate);
        updateButton = findViewById(R.id.buttonUpdate);
        cancelButton = findViewById(R.id.buttonCancel);

        // Retrieve the disease ID from the intent
        Intent intent = getIntent();
        diseaseId = intent.getIntExtra("id", -1);

        if (diseaseId == -1) {
            Toast.makeText(this, "Invalid Disease ID", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if ID is invalid
            return;
        }

        // Populate fields with existing data
        loadDiseaseData(diseaseId);

        // Set up DatePicker for the date field
        dateEditText.setOnClickListener(v -> showDatePicker());

        // Handle save button click
        updateButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || date.isEmpty()) {
                Toast.makeText(DiseaseEditActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = diseaseTable.updateDisease(diseaseId, name, description, date);
            if (result < 0) {
                Toast.makeText(DiseaseEditActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Navigate back
            finish(); // Close the activity
        });

        // Handle cancel button click
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadDiseaseData(int id) {
        // Fetch disease data from the database
        DiseaseModel disease = diseaseTable.getDiseaseById(id);
        if (disease != null) {
            nameEditText.setText(disease.getName());
            descriptionEditText.setText(disease.getDescription());
            dateEditText.setText(disease.getDate());
        } else {
            Toast.makeText(this, "Disease not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no data is found
        }
    }

    private void showDatePicker() {
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
                    dateEditText.setText(selectedDate);
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}
