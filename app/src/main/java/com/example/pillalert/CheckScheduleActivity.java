package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckScheduleActivity extends AppCompatActivity {

    private Button addNewScheduleButton;
    private CheckBox morningCheckBox, afternoonCheckBox, eveningCheckBox;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_schedule);

        // Initialize Views
        addNewScheduleButton = findViewById(R.id.addNewScheduleButton);
        morningCheckBox = findViewById(R.id.morningCheckBox);
        afternoonCheckBox = findViewById(R.id.afternoonCheckBox);
        eveningCheckBox = findViewById(R.id.eveningCheckBox);
        backButton = findViewById(R.id.backButton);

        // Morning checkbox click listener
        morningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(CheckScheduleActivity.this, "Pagi schedule checked!", Toast.LENGTH_SHORT).show();
            }
        });

        // Button Click Listener
        addNewScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CreateScheduleActivity
                Intent intent = new Intent(CheckScheduleActivity.this, CreateScheduleActivity.class);
                startActivity(intent);
            }
        });

        // Add Back Button Click Event
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckScheduleActivity.this, DashboardActivity.class);
                startActivity(intent); // Start the previous activity explicitly
                finish(); // Optional, closes the current activity
            }
        });
    }
}
