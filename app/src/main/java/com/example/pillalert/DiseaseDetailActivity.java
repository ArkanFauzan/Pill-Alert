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

public class DiseaseDetailActivity extends AppCompatActivity {

    private DatabaseHelperDiseaseTable diseaseTable;
    private DatabaseHelperMedicineTable medicineTable;
    private TextView nameTextView, descriptionTextView, dateTextView;
    private RecyclerView recyclerView;
    private MedicineCardAdapter medicineCardAdapter;
    private Button btnaddMedicine;
    private ImageView backButton;
    private int diseaseId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        // Initialize database
        diseaseTable = new DatabaseHelperDiseaseTable(this);
        medicineTable = new DatabaseHelperMedicineTable(this);

        // Initialize views
        nameTextView = findViewById(R.id.diseaseName);
        dateTextView = findViewById(R.id.diseaseDate);
        descriptionTextView = findViewById(R.id.diseaseDescription);
        recyclerView = findViewById(R.id.recyclerViewMedicine);
        btnaddMedicine = findViewById(R.id.btnAddMedicine);
        backButton = findViewById(R.id.backButton);

        // Get data from intent
        diseaseId = getIntent().getIntExtra("id", -1);

        // Load data
        loadData();

        // Create Medicine Button Click Event
        btnaddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Create Medicine
                Intent intent = new Intent(DiseaseDetailActivity.this, MedicineAddActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                startActivity(intent);
            }
        });

        // Add Back Button Click Event
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // A method to reload data
    }

    private void loadData() {
        // Get disease data
        DiseaseModel disease= diseaseTable.getDiseaseById(diseaseId);
        String name = disease.getName();
        String description = disease.getDescription();
        String date = disease.getDate();

        // Set data to views
        nameTextView.setText(name);
        dateTextView.setText(date);
        descriptionTextView.setText(description);

        // Show data in card format
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicineCardAdapter = new MedicineCardAdapter(this, medicineTable.getAllMedicine(diseaseId));
        recyclerView.setAdapter(medicineCardAdapter);
    }
}
