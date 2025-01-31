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

    private DatabaseHelperMedicineTable medicineTable;
    private DatabaseHelperMedicineTrackingTable medicineTrackingTable;
    private RecyclerView recyclerViewMedicine;
    private RecyclerView recyclerViewMedicineTracking;
    private MedicineCardAdapter medicineCardAdapter;
    private MedicineTrackingCardAdapter medicineTrackingCardAdapter;
    private ImageView backButton;
    private int medicineId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        // Initialize database
        medicineTable = new DatabaseHelperMedicineTable(this);
        medicineTrackingTable = new DatabaseHelperMedicineTrackingTable(this);

        // Initialize views
        recyclerViewMedicine = findViewById(R.id.recyclerViewMedicine);
        recyclerViewMedicineTracking = findViewById(R.id.recyclerViewMedicineTracking);
        backButton = findViewById(R.id.backButton);

        recyclerViewMedicine.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMedicineTracking.setLayoutManager(new LinearLayoutManager(this));

        // Get data from intent
        medicineId = getIntent().getIntExtra("id", -1);

        // Load data
        loadData();

        // Add Back Button Click Event
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // A method to reload data
    }

    private void loadData() {
        // Get detail data
        List<MedicineModel> medicineModelList = new ArrayList<>();
        medicineModelList.add(medicineTable.getMedicineById(medicineId));

        // Show detail data in card format
        medicineCardAdapter = new MedicineCardAdapter(this, medicineModelList);
        recyclerViewMedicine.setAdapter(medicineCardAdapter);

        // Show medicine tracking data in card format
        medicineTrackingCardAdapter = new MedicineTrackingCardAdapter(this, medicineTrackingTable.getMedicineTrackingByMedicineId(medicineId));
        recyclerViewMedicineTracking.setAdapter(medicineTrackingCardAdapter);
    }

}
