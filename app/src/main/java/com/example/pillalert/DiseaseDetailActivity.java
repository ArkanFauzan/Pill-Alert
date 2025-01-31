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
    private RecyclerView recyclerViewDisease, recyclerViewMedicine;
    private DiseaseCardAdapter diseaseCardAdapter;
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
        recyclerViewDisease = findViewById(R.id.recyclerViewDisease);
        recyclerViewMedicine = findViewById(R.id.recyclerViewMedicine);
        btnaddMedicine = findViewById(R.id.btnAddMedicine);
        backButton = findViewById(R.id.backButton);

        recyclerViewDisease.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMedicine.setLayoutManager(new LinearLayoutManager(this));

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
        List<DiseaseModel> diseaseModelList = new ArrayList<>();
        diseaseModelList.add(diseaseTable.getDiseaseById(diseaseId));

        // Show detail disease data in card format
        diseaseCardAdapter = new DiseaseCardAdapter(this, diseaseModelList);
        recyclerViewDisease.setAdapter(diseaseCardAdapter);

        // Show medicine data in card format
        medicineCardAdapter = new MedicineCardAdapter(this, medicineTable.getAllMedicine(diseaseId));
        recyclerViewMedicine.setAdapter(medicineCardAdapter);
    }
}
