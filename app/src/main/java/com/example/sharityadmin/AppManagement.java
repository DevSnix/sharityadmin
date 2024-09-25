package com.example.sharityadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppManagement extends AppCompatActivity {

    private TextView tvWelcomeAdmin;
    private Button btnAddUser, btnAddCharity, btnRemoveUser, btnRemoveCharity;
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
        btnViewProfileRequests = findViewById(R.id.btnViewProfileRequests);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUser.class);
            startActivity(intent);
        });

        btnAddCharity.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCharity.class);
            startActivity(intent);
        });

        btnRemoveUser.setOnClickListener(v -> showRemoveUserPopup("user"));
        btnRemoveCharity.setOnClickListener(v -> showRemoveUserPopup("charity"));
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clears the activity stack
            startActivity(intent);
            finish();
        });
    }

    // Show popup and remove user or charity
    private void showRemoveUserPopup(String type) {
        // Create the popup
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle("Deactivate " + (type.equals("user") ? "User" : "Charity"));

        // Inflate the popup
        final EditText input = new EditText(this);
        input.setHint("Enter " + (type.equals("user") ? "User" : "Charity") + " ID");
        builder.setView(input);

        // Set dialog buttons
        builder.setPositiveButton("Yes", (dialog, which) -> {
            String userId = input.getText().toString().trim();

            if (!userId.isEmpty()) {
                deactivateUser(userId);
            } else {
                Toast.makeText(AppManagement.this, "Please enter a valid ID", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }

    // Deactivate user in Firebase
    private void deactivateUser(String userId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Set userStatus to false (deactivate account)
        usersRef.child(userId).child("userStatus").setValue(false)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AppManagement.this, "User/Charity deactivated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AppManagement.this, "Failed to deactivate. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
