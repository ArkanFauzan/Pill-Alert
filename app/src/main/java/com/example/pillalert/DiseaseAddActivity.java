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

public class DiseaseAddActivity extends AppCompatActivity {

    private DatabaseHelperDiseaseTable diseaseTable;
    private EditText nameEditText, descriptionEditText;
    private EditText dateEditText;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_add);

        // Initialize database
        diseaseTable = new DatabaseHelperDiseaseTable(this);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        descriptionEditText = findViewById(R.id.editTextDescription);
        dateEditText = findViewById(R.id.editTextDate);
        saveButton = findViewById(R.id.buttonUpdate);
        cancelButton = findViewById(R.id.buttonCancel);

        // Set up DatePicker for the date field
        dateEditText.setOnClickListener(v -> showDatePicker());

        // Handle save button click
        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || date.isEmpty()) {
                Toast.makeText(DiseaseAddActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = diseaseTable.addDisease(name, description, date);
            if (result == -1) {
                Toast.makeText(DiseaseAddActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Navigate back
            finish(); // Close activity
        });

        // Handle cancel button click
        cancelButton.setOnClickListener(v -> finish());
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
                    String monthTwoDigit = selectedMonth + 1 < 10 ? "0"+(selectedMonth + 1) : String.valueOf(selectedMonth + 1);
                    String dayTwoDigit = selectedDay < 10 ? "0"+selectedDay : String.valueOf(selectedDay);

                    String selectedDate = selectedYear + "-" + monthTwoDigit + "-" + dayTwoDigit;
                    dateEditText.setText(selectedDate);
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}
