package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    DatabaseHelperDiseaseTable DiseaseTable;
    DatabaseHelperMedicineTable MedicineTable;
    private Button createScheduleButton, checkScheduleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize database
        DiseaseTable = new DatabaseHelperDiseaseTable(this);
        MedicineTable = new DatabaseHelperMedicineTable(this);

        // Initialize Views
        createScheduleButton = findViewById(R.id.createScheduleButton);
        checkScheduleButton = findViewById(R.id.checkScheduleButton);
        ImageView menuIcon = findViewById(R.id.menuIcon);
        ImageView profileIcon = findViewById(R.id.profileIcon);

        // Create Schedule Button Click Event
        createScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Create Schedule Activity
                Intent intent = new Intent(DashboardActivity.this, CreateScheduleActivity.class);
                startActivity(intent);
            }
        });

        // Check Schedule Button Click Event
        checkScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long disease_id = DiseaseTable.add("Batuk", "deskripsi", "2025-01-21");
                if(disease_id != -1) {
                    long medicine_id = MedicineTable.add((int)disease_id, "Paracetamol", "ini obat pusing", MedicineUnitEnum.Tablet, 3, 1, 10,  "2025-01-21", "2025-01-23");
                    if(medicine_id != -1) {
                        Toast.makeText(DashboardActivity.this,"Data Inserted (disease & medicine)", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(DashboardActivity.this,"Medicine not inserted", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(DashboardActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
                }


                // Navigate to Check Schedule Activity
//                Intent intent = new Intent(DashboardActivity.this, CheckScheduleActivity.class);
//                startActivity(intent);
            }
        });

        // Menu Icon Click Event
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Profile Icon Click Event
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
