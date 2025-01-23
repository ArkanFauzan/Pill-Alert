package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private DatabaseHelperDiseaseTable DiseaseTable;
    private RecyclerView recyclerView;
    private DiseaseCardAdapter diseaseCardAdapter;
    private Button btnAddDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize database
        DiseaseTable = new DatabaseHelperDiseaseTable(this);

        // Show data in card format
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        diseaseCardAdapter = new DiseaseCardAdapter(this, DiseaseTable.getAllDisease());
        recyclerView.setAdapter(diseaseCardAdapter);

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
}
