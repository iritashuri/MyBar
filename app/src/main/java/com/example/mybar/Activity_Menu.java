package com.example.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;


public class Activity_Menu extends AppCompatActivity {

    // Set Buttons
    private Button Menu_BTN_cocktails;
    private Button Menu_BTN_Wines;
    private Button Menu_BTN_Daily_Beers;
    private Button Menu_BTN_Chasers;
    private Button Menu_BTN_Daily_SoftDrinks;
    private Button Menu_BTN_Food;
    private Button Menu_BTN_back;

    // Set FirebaseAuth
    private FirebaseFirestore db;

    // Set SP
    private MySPV mySPV;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__menu);

        finViews();

        // Set SP
        mySPV = new MySPV(this);

        // Set Firebase
        db = FirebaseFirestore.getInstance();

        // Open cocktail list
        Menu_BTN_cocktails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemCategory(Item.CATEGORIES.COCKTAILS);
            }
        });

        // Open wines list
        Menu_BTN_Wines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemCategory(Item.CATEGORIES.WINES);
            }
        });

        // Open beers list
        Menu_BTN_Daily_Beers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemCategory(Item.CATEGORIES.BEERS);
            }
        });

        // Open chasers list
        Menu_BTN_Chasers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemCategory(Item.CATEGORIES.CHASERS);
            }
        });

        // Open soft drinks list
        Menu_BTN_Daily_SoftDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemCategory(Item.CATEGORIES.SOFT_DRINKS);
            }
        });

        // Open soft food list
        Menu_BTN_Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemCategory(Item.CATEGORIES.FOOD);
            }
        });

        // Go back and log out
        Menu_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    private void finViews() {
        Menu_BTN_cocktails = findViewById(R.id.Menu_BTN_cocktails);
        Menu_BTN_Wines = findViewById(R.id.Menu_BTN_Wines);
        Menu_BTN_Daily_Beers = findViewById(R.id.Menu_BTN_Daily_Beers);
        Menu_BTN_Chasers = findViewById(R.id.Menu_BTN_Chasers);
        Menu_BTN_Daily_SoftDrinks = findViewById(R.id.Menu_BTN_Daily_SoftDrinks);
        Menu_BTN_Food = findViewById(R.id.Menu_BTN_Food);
        Menu_BTN_back = findViewById(R.id.Menu_BTN_back);
    }

    private void openItemCategory(String category) {
        // Set category on SP
        mySPV.putString(MySPV.KEYS.CATEGORY, category);

        // Open ItemCategory Activity
        Intent intent = new Intent(Activity_Menu.this, Activity_ItemCategory.class);
        startActivity(intent);

    }
}