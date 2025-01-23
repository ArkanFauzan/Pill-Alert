package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bypass
        // Navigate to Dashboard Activity
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();

        // Bellow code is for login page, but not used again

//        setContentView(R.layout.activity_main);
//
//        // Initialize Views
//        emailEditText = findViewById(R.id.emailEditText);
//        passwordEditText = findViewById(R.id.passwordEditText);
//        signInButton = findViewById(R.id.signInButton);
//        signUpTextView = findViewById(R.id.signUpTextView);
//
//        // Sign-in Button Click Event
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailEditText.getText().toString().trim();
//                String password = passwordEditText.getText().toString().trim();
//
//                if (email.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Proceed with sign-in logic
//                    Toast.makeText(MainActivity.this, "Sign-in berhasil", Toast.LENGTH_SHORT).show();
//
//                    // Dummy bypass
//                    // Navigate to Create Dashboard Activity
//                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
//
//        // Sign-up Text Click Event
//        signUpTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Navigate to Sign-up screen
//                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
