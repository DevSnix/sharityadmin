package com.example.sharityadmin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

    public class AddUser extends AppCompatActivity {

        private EditText etUserId, etUsername, etEmail, etPassword, etAddress, etPhoneNumber;
        private CheckBox checkBoxActivateUser;
        private Button btnAddUser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_user);

            // Initialize views
            etUserId = findViewById(R.id.etUserId);
            etUsername = findViewById(R.id.etUsername);
            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            etAddress = findViewById(R.id.etAddress);
            etPhoneNumber = findViewById(R.id.etPhoneNumber);
            checkBoxActivateUser = findViewById(R.id.checkBoxActivateUser);
            btnAddUser = findViewById(R.id.btnAddUser);

            // Set onClickListener for Add User button
            btnAddUser.setOnClickListener(v -> {
                addUserToFirebase();
                finish();
            });
        }

        // Method to capture user input and save the new user to Firebase
        private void addUserToFirebase() {
            String userIdStr = etUserId.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            // Validate input
            if (TextUtils.isEmpty(userIdStr) || TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert userId to an integer
            int userId = Integer.parseInt(userIdStr);

            // Check if user should be activated
            boolean isActivated = checkBoxActivateUser.isChecked();

            // Create the User object with userType set to "Admin" (or other type if needed)
            User newUser = new User();
            newUser.setUserId(userId);
            newUser.setUserName(username);
            newUser.setUserPassword(password);
            newUser.setUserEmail(email);
            newUser.setUserPhoneNumber(phoneNumber);
            newUser.setUserAddress(address);
            newUser.setUserStatus(isActivated);  // Set user status based on checkbox
            newUser.setUserType("Donor");  // You can modify this if different types of users are allowed

            newUser.saveToFirebase();
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
            clearInputFields();
        }

        // Clear all input fields after adding the user
        private void clearInputFields() {
            etUserId.setText("");
            etUsername.setText("");
            etEmail.setText("");
            etPassword.setText("");
            etAddress.setText("");
            etPhoneNumber.setText("");
            checkBoxActivateUser.setChecked(false);
        }
    }
