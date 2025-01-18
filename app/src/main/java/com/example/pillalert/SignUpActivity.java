package com.example.pillalert;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private TextView logInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        logInTextView = findViewById(R.id.logInTextView);

        // Sign Up Button Click Listener
        btnSignUp.setOnClickListener(view -> {
            String fullName = etFullName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // Validation
            if (TextUtils.isEmpty(fullName)) {
                etFullName.setError("Full Name is required");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password is required");
                return;
            }

            if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords do not match");
                return;
            }

            // Success Message
            Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

            // Redirect to MainActivity (Sign In)
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Sign In Text Click Listener
        logInTextView.setOnClickListener(view -> {
            // Redirect to SignInActivity
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
