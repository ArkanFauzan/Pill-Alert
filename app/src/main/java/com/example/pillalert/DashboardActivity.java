package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private DatabaseHelperDiseaseTable diseaseTable;
    private RecyclerView recyclerView;
    private DiseaseCardAdapter diseaseCardAdapter;
    private Button btnAddDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize database
        diseaseTable = new DatabaseHelperDiseaseTable(this);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);

        // Create Disease Button Click Event
        btnAddDisease = findViewById(R.id.btnAddDisease);
        btnAddDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Create Disease
                Intent intent = new Intent(DashboardActivity.this, DiseaseAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // A method to reload data
    }

    private void loadData() {
        // Show data in card format
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        diseaseCardAdapter = new DiseaseCardAdapter(this, diseaseTable.getAllDisease());
        recyclerView.setAdapter(diseaseCardAdapter);
    }
}
