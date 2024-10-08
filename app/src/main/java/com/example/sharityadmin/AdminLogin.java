package com.example.sharityadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    private EditText etAdminId, etLicenseNumber, etPassword;
    private Button btnLogin;
    private DatabaseReference databaseReference;

    // SharedPreferences for tracking login attempts
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginAttempts";
    private static final String ATTEMPT_COUNT = "attemptCount";
    private static final int MAX_ATTEMPTS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        databaseReference = FirebaseDatabase.getInstance().getReference("admins");
        etAdminId = findViewById(R.id.etAdminId);
        etLicenseNumber = findViewById(R.id.etLicenseNumber);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Check login attempts
        int attemptCount = sharedPreferences.getInt(ATTEMPT_COUNT, 0);
        if (attemptCount >= MAX_ATTEMPTS) {
            disableLoginButton();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAdminDetails();
            }
        });
    }

    // Method to check admin details from Firebase
    private void checkAdminDetails() {
        String adminId = etAdminId.getText().toString().trim();
        String licenseNumberStr = etLicenseNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(adminId)) {
            etAdminId.setError("Admin ID is required");
            return;
        }
        if (TextUtils.isEmpty(licenseNumberStr)) {
            etLicenseNumber.setError("License number is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return;
        }

        int licenseNumber = Integer.parseInt(licenseNumberStr);

        // Check the details in Firebase
        databaseReference.child(adminId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Admin exists, now validate credentials
                    Admin admin = dataSnapshot.getValue(Admin.class);

                    if (admin != null && admin.getLicenseNumber() == licenseNumber && admin.getPassword().equals(password)) {
                        // Successful login
                        Toast.makeText(AdminLogin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        resetLoginAttempts();
                        // Move to AppManagement activity
                        moveToAppManagement();
                    } else {
                        // Unsuccessful login attempt
                        incrementLoginAttempts();
                        Toast.makeText(AdminLogin.this, "Incorrect details. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Admin does not exist
                    incrementLoginAttempts();
                    Toast.makeText(AdminLogin.this, "Admin ID not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminLogin.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Increment login attempts and disable the button if max attempts reached
    private void incrementLoginAttempts() {
        int attemptCount = sharedPreferences.getInt(ATTEMPT_COUNT, 0);
        attemptCount++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ATTEMPT_COUNT, attemptCount);
        editor.apply();

        if (attemptCount >= MAX_ATTEMPTS) {
            disableLoginButton();
        }
    }

    // Disable the login button
    private void disableLoginButton() {
        btnLogin.setEnabled(false);
        Toast.makeText(this, "Too many unsuccessful attempts. Login disabled.", Toast.LENGTH_SHORT).show();
    }

    // Reset login attempts after a successful login
    private void resetLoginAttempts() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ATTEMPT_COUNT, 0);
        editor.apply();
    }

    // Navigate to AppManagement activity
    private void moveToAppManagement() {
        Intent intent = new Intent(AdminLogin.this, AppManagement.class);
        startActivity(intent);
        finish();  // Optionally finish the login activity so the user cannot go back to it
    }
}
