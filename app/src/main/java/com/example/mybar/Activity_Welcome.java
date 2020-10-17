package com.example.mybar;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

public class Activity_Welcome extends AppCompatActivity {
    private Button Welcome_BTN_signIn;
    private Button Welcome_BTN_signUp;

    FirebaseAuth mAuth;

    static Activity_Welcome activity_welcome;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Welcome_BTN_signIn = findViewById(R.id.Welcome_BTN_signIn);
        Welcome_BTN_signUp = findViewById(R.id.Welcome_BTN_signUp);
        activity_welcome = this;

        mAuth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            openMain();
        }

        Welcome_BTN_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogIn();
            }
        });

        Welcome_BTN_signUp.setOnClickListener(new View.OnClickListener() {
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
    private void openMain(){
        Intent intent = new Intent(Activity_Welcome.this, Activity_Main.class);
        startActivity(intent);
        finish();
    }

    public static Activity_Welcome getInstance(){
        return activity_welcome;
    }

}