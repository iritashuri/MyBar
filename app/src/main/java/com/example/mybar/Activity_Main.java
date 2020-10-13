package com.example.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_Main extends AppCompatActivity {

    private TextView Main_TXT_Welcome;
    private Button Main_BTN_My_Orders;
    private Button Main_BTN_Daily_deals;
    private Button Main_BTN_LogOut;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);

        findViews();

        mAuth = FirebaseAuth.getInstance();


        // Log out
        Main_BTN_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                openMain();
            }
        });



    }
    private void openMain(){
        Intent intent = new Intent(Activity_Main.this, Activity_Welcome.class);
        startActivity(intent);
        finish();
    }

    private void findViews(){
        Main_TXT_Welcome = findViewById(R.id.Main_TXT_Welcome);
        Main_BTN_My_Orders = findViewById(R.id.Main_BTN_My_Orders);
        Main_BTN_Daily_deals = findViewById(R.id.Main_BTN_Daily_deals);
        Main_BTN_LogOut = findViewById(R.id.Main_BTN_LogOut);
    }

}