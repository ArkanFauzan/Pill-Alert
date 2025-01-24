package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicineDetailActivity extends AppCompatActivity {

    private DatabaseHelperMedicineTable MedicineTable;
    private TextView nameTextView, descriptionTextView, dateTextView;
    private RecyclerView recyclerView;
    private MedicineCardAdapter medicineCardAdapter;
    private Button btnaddMedicine;
    private ImageView backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        // Initialize views
        nameTextView = findViewById(R.id.diseaseName);
        dateTextView = findViewById(R.id.diseaseDate);
        descriptionTextView = findViewById(R.id.diseaseDescription);
        btnaddMedicine = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);

        // Get data from intent
        int id = getIntent().getIntExtra("id", -1);
        String name = getIntent().getStringExtra("name");
        String title = getIntent().getStringExtra("description");
        String phone = getIntent().getStringExtra("date");

        // Set data to views
        nameTextView.setText(name);
        dateTextView.setText(phone);
        descriptionTextView.setText(title);

        // Initialize database
        MedicineTable = new DatabaseHelperMedicineTable(this);

        // Show data in card format
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // dummy data
        List<MedicineModel> data = new ArrayList<>();
        data.add(new MedicineModel(1,1, "Paracetamol 1", "Obat pereda nyeri", MedicineUnitEnum.Capsule, 3, 1, 10, "2025-01-21", "2025-01-23"));
        data.add(new MedicineModel(2,1, "Paracetamol 2", "Obat pereda nyeri", MedicineUnitEnum.Capsule, 3, 1, 10, "2025-01-21", "2025-01-23"));
        data.add(new MedicineModel(3,1, "Paracetamol 3", "Obat pereda nyeri", MedicineUnitEnum.Capsule, 3, 1, 10, "2025-01-21", "2025-01-23"));
        data.add(new MedicineModel(4,1, "Paracetamol 4", "Obat pereda nyeri", MedicineUnitEnum.Capsule, 3, 1, 10, "2025-01-21", "2025-01-23"));
        data.add(new MedicineModel(5,1, "Paracetamol 5", "Obat pereda nyeri", MedicineUnitEnum.Capsule, 3, 1, 10, "2025-01-21", "2025-01-23"));

        medicineCardAdapter = new MedicineCardAdapter(this, data);
        recyclerView.setAdapter(medicineCardAdapter);

        // Create Medicine Button Click Event
        btnaddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Create Medicine
                Intent intent = new Intent(MedicineDetailActivity.this, MedicineAddActivity.class);
                startActivity(intent);
            }
        });

        // Add Back Button Click Event
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Optional, closes the current activity
            }
        });
    }
}
