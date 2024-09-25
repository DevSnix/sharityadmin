package com.example.sharityadmin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppManagement extends AppCompatActivity {

    private TextView tvWelcomeAdmin;
    private Button btnAddUser, btnAddCharity, btnRemoveUser, btnRemoveCharity;
    private Button btnDoneeApproval, btnCharityApproval, btnViewCharityReports;
    private Button btnViewProfileRequests, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_management);

        tvWelcomeAdmin = findViewById(R.id.tvWelcomeAdmin);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnAddCharity = findViewById(R.id.btnAddCharity);
        btnRemoveUser = findViewById(R.id.btnRemoveUser);
        btnRemoveCharity = findViewById(R.id.btnRemoveCharity);
        btnDoneeApproval = findViewById(R.id.btnDoneeApproval);
        btnCharityApproval = findViewById(R.id.btnCharityApproval);
        btnViewCharityReports = findViewById(R.id.btnViewCharityReports);
        btnViewProfileRequests = findViewById(R.id.btnViewProfileRequests);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUser.class);
            startActivity(intent);
            finish();
        });



        btnLogout.setOnClickListener(v -> {
            // Navigate back to login
            Intent intent = new Intent(this, AdminLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clears the activity stack
            startActivity(intent);
        });
    }

}
