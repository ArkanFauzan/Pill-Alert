package com.example.pillalert;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiseaseDetailActivity extends AppCompatActivity {

    private TextView nameTextView, titleTextView, phoneTextView, emailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        // Initialize views
        nameTextView = findViewById(R.id.name);
        titleTextView = findViewById(R.id.description);
        phoneTextView = findViewById(R.id.date);

        // Get data from intent
        String name = getIntent().getStringExtra("name");
        String title = getIntent().getStringExtra("description");
        String phone = getIntent().getStringExtra("date");

        // Set data to views
        nameTextView.setText(name);
        titleTextView.setText(title);
        phoneTextView.setText(phone);
    }
}
