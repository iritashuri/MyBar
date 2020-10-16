package com.example.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_Profile extends AppCompatActivity {

    TextView Profile_TXT_Name;
    TextView Profile_TXT_email;
    TextView Profile_TXT_phone;
    Button Profile_BTN_edit;
    Button Profile_BTN_LogOut;
    Button Profile_BTN_delete;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void findViews(){
         Profile_TXT_Name = findViewById(R.id.Profile_TXT_Name);
         Profile_TXT_email= findViewById(R.id.Profile_TXT_email);
         Profile_TXT_phone= findViewById(R.id.Profile_TXT_phone);
         Profile_BTN_edit= findViewById(R.id.Profile_BTN_edit);
         Profile_BTN_LogOut= findViewById(R.id.Profile_BTN_LogOut);
         Profile_BTN_delete= findViewById(R.id.Profile_BTN_delete);
    }
}