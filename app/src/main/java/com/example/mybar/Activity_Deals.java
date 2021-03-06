package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Deals extends AppCompatActivity {

    private RecyclerView Deals_LST_dealsList;
    private Button Deals_BTN_back;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__deals);

        // Set cards list and back button
        findViews();

        // Set real time Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("deals");

        showDeals();

        Deals_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        Deals_LST_dealsList = findViewById(R.id.Deals_LST_dealsList);
        Deals_BTN_back = findViewById(R.id.Deals_BTN_back);
    }

    private void showDeals() {

        //Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Deal> myDeals = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Deal temp = new Deal(postSnapshot.child("description").getValue().toString(), postSnapshot.child("price").getValue().toString());
                    myDeals.add(temp);
                }
                Adapter_Deals adapter_deals = new Adapter_Deals(Activity_Deals.this, myDeals);
                adapter_deals.setClickListeners(dealItemClickListener);
                // Display data
                Deals_LST_dealsList.setLayoutManager(new LinearLayoutManager(Activity_Deals.this));
                Deals_LST_dealsList.setAdapter(adapter_deals);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("pttt", "loadPost:onCancelled", databaseError.toException());
            }
        });
        myRef.getDatabase();
    }

    Adapter_Deals.DealItemClickListener dealItemClickListener = new Adapter_Deals.DealItemClickListener() {
        @Override
        public void itemClicked(Deal exercise, int position) {
        }
    };

}