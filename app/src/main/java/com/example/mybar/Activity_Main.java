package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Activity_Main extends AppCompatActivity {

    private TextView Main_TXT_Welcome;
    private Button Main_BTN_My_Orders;
    private Button Main_BTN_Daily_deals;
    private Button Main_BTN_LogOut;
    private CardView Main_Card_user;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);

        findViews();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        displayName();

        // Log out
        Main_BTN_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                openWelcome();
                finish();
            }
        });

        // Open profile activity
        Main_Card_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });



    }
    private void openProfile(){
        Intent intent = new Intent(Activity_Main.this, Activity_Profile.class);
        startActivity(intent);
        finish();
    }
    private void openWelcome(){
        Intent intent = new Intent(Activity_Main.this, Activity_Welcome.class);
        startActivity(intent);
        finish();
    }

    private void findViews(){
        Main_TXT_Welcome = findViewById(R.id.Main_TXT_Welcome);
        Main_BTN_My_Orders = findViewById(R.id.Main_BTN_My_Orders);
        Main_BTN_Daily_deals = findViewById(R.id.Main_BTN_Daily_deals);
        Main_BTN_LogOut = findViewById(R.id.Main_BTN_LogOut);
        Main_Card_user = findViewById(R.id.Main_Card_user);

    }

    private void displayName(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String current_user_mail = mAuth.getCurrentUser().getEmail();
                            ArrayList<User> users = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("email").equals(current_user_mail)){
                                    // Show full name
                                    Main_TXT_Welcome.setText(document.getString("full_name"));
                                }
                            }

                        } else {
                            Log.w("pttt", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}