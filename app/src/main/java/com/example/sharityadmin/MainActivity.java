package com.example.sharityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView textViewUserEmail;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextView
        textViewUserEmail = findViewById(R.id.textViewUserEmail);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Fetch user email based on userId
        String userId = "16890"; // Replace this with the actual userId
        getUserEmail(userId);
    }

    private void getUserEmail(String userId) {
        databaseReference.child("users").child(userId).child("userEmail")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get the userEmail value
                        String userEmail = dataSnapshot.getValue(String.class);
                        if (userEmail != null) {
                            textViewUserEmail.setText(userEmail);
                        } else {
                            textViewUserEmail.setText("No email found for this user.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("MainActivity", "Failed to read email", databaseError.toException());
                        textViewUserEmail.setText("Failed to retrieve email.");
                    }
                });
    }
}
