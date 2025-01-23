package com.example.pillalert;

import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    DatabaseHelperDiseaseTable DiseaseTable;
    DatabaseHelperMedicineTable MedicineTable;
    private RecyclerView recyclerView;
    private CardDiseaseAdapter cardDiseaseAdapter;
    private List<CardDiseaseModel> cardList;
    private Button createScheduleButton, checkScheduleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        cardList = new ArrayList<>();
        cardList.add(new CardDiseaseModel("Radang tenggorokan 1", "Gejalanya batuk-batuk, sulit menelan, amandel terlihat membengkak", "2 Januari 2025"));
        cardList.add(new CardDiseaseModel("Radang tenggorokan 2", "Gejalanya batuk-batuk, sulit menelan, amandel terlihat membengkak", "2 Januari 2025"));
        cardList.add(new CardDiseaseModel("Radang tenggorokan 3", "Gejalanya batuk-batuk, sulit menelan, amandel terlihat membengkak", "2 Januari 2025"));
        cardList.add(new CardDiseaseModel("Radang tenggorokan 4", "Gejalanya batuk-batuk, sulit menelan, amandel terlihat membengkak", "2 Januari 2025"));
        cardList.add(new CardDiseaseModel("Radang tenggorokan 5", "Gejalanya batuk-batuk, sulit menelan, amandel terlihat membengkak", "2 Januari 2025"));

        cardDiseaseAdapter = new CardDiseaseAdapter(this, cardList);
        recyclerView.setAdapter(cardDiseaseAdapter);

//
//        // Initialize database
//        DiseaseTable = new DatabaseHelperDiseaseTable(this);
//        MedicineTable = new DatabaseHelperMedicineTable(this);
//
//        // Initialize Views
//        createScheduleButton = findViewById(R.id.createScheduleButton);
//        checkScheduleButton = findViewById(R.id.checkScheduleButton);
//        ImageView menuIcon = findViewById(R.id.menuIcon);
//        ImageView profileIcon = findViewById(R.id.profileIcon);
//
//        // Create Schedule Button Click Event
//        createScheduleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Navigate to Create Schedule Activity
//                Intent intent = new Intent(DashboardActivity.this, CreateScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Check Schedule Button Click Event
//        checkScheduleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Navigate to Check Schedule Activity
//                Intent intent = new Intent(DashboardActivity.this, CheckScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Menu Icon Click Event
//        menuIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(DashboardActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Profile Icon Click Event
//        profileIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(DashboardActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
