package com.example.pillalert;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleSummaryActivity extends BaseActivity {
    private DatabaseHelperMedicineTrackingTable medicineTrackingTable;
    private EditText dateEditText;
    private Button searchButton;
    private RecyclerView recyclerViewMedicineTracking;
    private MedicineTrackingCardAdapter medicineTrackingCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_summary);
        setupBottomNavigation(R.id.menu_schedule_summary);

        // Initialize database
        medicineTrackingTable = new DatabaseHelperMedicineTrackingTable(this);

        // Initialize views
        dateEditText = findViewById(R.id.editTextDate);
        searchButton = findViewById(R.id.buttonSearch);

        // Set up DatePickers for date fields
        dateEditText.setOnClickListener(v -> showDatePicker(dateEditText));

        // Show data in card format
        recyclerViewMedicineTracking = findViewById(R.id.recyclerViewMedicineTracking);
        recyclerViewMedicineTracking.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(v -> {
            String date = dateEditText.getText().toString().trim();

            if (date.isEmpty()) {
                Toast.makeText(ScheduleSummaryActivity.this, "Please fill date field", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                medicineTrackingCardAdapter = new MedicineTrackingCardAdapter(this, medicineTrackingTable.getAllMedicineTrackingByTargetDate(dateEditText.getText().toString().trim()), true);
                recyclerViewMedicineTracking.setAdapter(medicineTrackingCardAdapter);
            } catch (IllegalArgumentException e) {
                Toast.makeText(ScheduleSummaryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dateEditText.getText().toString().trim().isEmpty()) {
            // Auto search by current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            String monthTwoDigit = month + 1 < 10 ? "0" + (month + 1) : String.valueOf(month + 1);
            String dayTwoDigit = day < 10 ? "0" + day : String.valueOf(day);
            String selectedDate = year + "-" + monthTwoDigit + "-" + dayTwoDigit;

            dateEditText.setText(selectedDate);

            // Trigger search button
            searchButton.callOnClick();
        }
        else {
            // Trigger search button
            searchButton.callOnClick();
        }
    }

    private void showDatePicker(EditText targetEditText) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Set selected date to the EditText
                    String monthTwoDigit = selectedMonth + 1 < 10 ? "0" + (selectedMonth + 1) : String.valueOf(selectedMonth + 1);
                    String dayTwoDigit = selectedDay < 10 ? "0" + selectedDay : String.valueOf(selectedDay);

                    String selectedDate = selectedYear + "-" + monthTwoDigit + "-" + dayTwoDigit;
                    targetEditText.setText(selectedDate);

                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}