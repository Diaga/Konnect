package com.pika.knct;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private final int LOGIN_REQUEST = 0;
    private UserViewModel userViewModel;
    private QuestionViewModel questionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Connect with Database
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        // Clear tables
        questionViewModel.clearTable();

        // SignIn user
        if (!signedIn()) {
            signIn();
        } else {
            // Start main activity
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra("fromCode", "SplashActivity");
            startActivity(mainIntent);
            finish();
        }
    }

    private boolean signedIn() {
        return (userViewModel.getSignedInUser() != null);
    }

    private void signIn() {
        Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
        loginIntent.putExtra("requestCode", LOGIN_REQUEST);
        startActivity(loginIntent);
        finish();
    }
}
