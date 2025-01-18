package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateScheduleActivity extends AppCompatActivity {

    private EditText medicineNameEditText, medicineQuantityEditText, scheduleEditText, durationEditText;
    private Spinner timeSpinner, conditionSpinner;
    private Button addButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        // Initialize Views
        medicineNameEditText = findViewById(R.id.medicineNameEditText);
        medicineQuantityEditText = findViewById(R.id.medicineQuantityEditText);
        scheduleEditText = findViewById(R.id.scheduleEditText);
        durationEditText = findViewById(R.id.durationEditText);
        timeSpinner = findViewById(R.id.timeSpinner);
        conditionSpinner = findViewById(R.id.conditionSpinner);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);

        // Setup Spinners
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.time_options, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(this, R.array.condition_options, android.R.layout.simple_spinner_item);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        // Add Button Click Event
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicineName = medicineNameEditText.getText().toString().trim();
                String medicineQuantity = medicineQuantityEditText.getText().toString().trim();
                String schedule = scheduleEditText.getText().toString().trim();
                String duration = durationEditText.getText().toString().trim();
                String time = timeSpinner.getSelectedItem().toString();
                String condition = conditionSpinner.getSelectedItem().toString();

                if (medicineName.isEmpty() || medicineQuantity.isEmpty() || schedule.isEmpty() || duration.isEmpty()) {
                    Toast.makeText(CreateScheduleActivity.this, "Harap isi semua field!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateScheduleActivity.this, "Obat berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                    // Save the data to database or proceed with further logic
                }
            }
        });

        // Add Back Button Click Event
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateScheduleActivity.this, DashboardActivity.class);
                startActivity(intent); // Start the previous activity explicitly
                finish(); // Optional, closes the current activity
            }
        });
    }
}
