package com.example.sharityadmin;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class SharityAdminApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize Firebase
        FirebaseApp.initializeApp(this);

        //If the user is offline, the data changes will be queued and automatically synchronized when the user goes online.
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
