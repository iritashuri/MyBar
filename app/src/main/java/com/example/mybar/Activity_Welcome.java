package com.example.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_Welcome extends AppCompatActivity {

    private TextView Welcome_TXT_Welcome;
    private Button Welcome_BTN_My_Orders;
    private Button Welcome_BTN_Daily_deals;
    private Button Welcome_BTN_LogOut;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__welcome);

        Welcome_TXT_Welcome = findViewById(R.id.Welcome_TXT_Welcome);
        Welcome_BTN_My_Orders = findViewById(R.id.Welcome_BTN_My_Orders);
        Welcome_BTN_Daily_deals = findViewById(R.id.Welcome_BTN_Daily_deals);
        Welcome_BTN_LogOut = findViewById(R.id.Welcome_BTN_LogOut);

        mAuth = FirebaseAuth.getInstance();


        // Log out
        Welcome_BTN_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                openMain();
            }
        });



    }
    private void openMain(){
        Intent intent = new Intent(Activity_Welcome.this, Activity_Main.class);
        startActivity(intent);
        finish();
    }
}