package com.example.mybar;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

public class Activity_Welcome extends AppCompatActivity {
    private Button Main_BTN_signIn;
    private Button Main_BTN_signUp;

    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Main_BTN_signIn = findViewById(R.id.Main_BTN_signIn);
        Main_BTN_signUp = findViewById(R.id.Main_BTN_signUp);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            openSWelcome();
        }

        Main_BTN_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogIn();
            }
        });

        Main_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });
    }


    // Open Log In activity
    private void openLogIn(){
        Intent intent = new Intent(Activity_Welcome.this, Activity_LogIn.class);
        startActivity(intent);
    }

    // Open Sign Up activity
    private void openSignUp(){
        Intent intent = new Intent(Activity_Welcome.this, Activity_SignUp.class);
        startActivity(intent);
    }
    // Open Welcome activity
    private void openSWelcome(){
        Intent intent = new Intent(Activity_Welcome.this, Activity_Main.class);
        startActivity(intent);
        finish();
    }

}