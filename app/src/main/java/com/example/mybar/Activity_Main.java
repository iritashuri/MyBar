package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;


public class Activity_Main extends AppCompatActivity {

    private TextView Main_TXT_Welcome;
    private Button Main_BTN_My_Orders;
    private Button Main_BTN_Daily_deals;
    private Button Main_BTN_LogOut;
    private CardView Main_Card_user;
    private Button Main_BTN_Menu;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userId;
    private DocumentReference documentReference;
    public User current_user = new User();

    private MySPV mySPV;
    Gson gson = new Gson();

    static Activity_Main activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);

        findViews();
        activity_main = this;

        // Set Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Set SP
        mySPV = new MySPV(this);

        // get current user uuid from mAuth
        userId = mAuth.getCurrentUser().getUid();

        // set documentReference with the mach user according to userId
        documentReference = db.collection("users").document(userId);

        // Set current_user details
        setDetailsFromFB(new CustomCallback(){
            @Override
            public void onComplete(User user) {
                Main_TXT_Welcome.setText(user.getFull_name());
                setUserOnSP(current_user);

            }
        });

        // Log out
        Main_BTN_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        // Open profile activity
        Main_Card_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });

        Main_BTN_Daily_deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeals();
            }
        });

        // Open menu
        Main_BTN_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });

        // Open orders history
        Main_BTN_My_Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrdersHistory();
            }
        });

    }

    private void logOut() {
        mAuth.signOut();
        mySPV.deleteString(MySPV.KEYS.CURRENT_USER);
        openWelcome();
        finish();
    }

    private void openOrdersHistory() {
        Intent intent = new Intent(Activity_Main.this, Activity_OrdersHistory.class);
        startActivity(intent);
    }


    private void openMenu() {
        Intent intent = new Intent(Activity_Main.this, Activity_Menu.class);
        startActivity(intent);
    }

    private void openProfile(){
        Intent intent = new Intent(Activity_Main.this, Activity_Profile.class);
        startActivity(intent);
    }

    private void openWelcome(){
        Intent intent = new Intent(Activity_Main.this, Activity_Welcome.class);
        startActivity(intent);
        finish();
    }

    private void openDeals() {
        Intent intent = new Intent(Activity_Main.this, Activity_Deals.class);
        startActivity(intent);
    }


    private void findViews(){
        Main_TXT_Welcome = findViewById(R.id.Main_TXT_Welcome);
        Main_BTN_My_Orders = findViewById(R.id.Main_BTN_My_Orders);
        Main_BTN_Daily_deals = findViewById(R.id.Main_BTN_Daily_deals);
        Main_BTN_LogOut = findViewById(R.id.Main_BTN_LogOut);
        Main_Card_user = findViewById(R.id.Main_Card_user);
        Main_BTN_Menu = findViewById(R.id.Main_BTN_Menu);
    }

    // Set current_user With data from the firebase
    private void setDetailsFromFB(final CustomCallback customCallback){
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    current_user.setFull_name(doc.getString("full_name")).setEmail(doc.getString("email")).setPhone(doc.getString("phone"));
                    if(customCallback!=null){
                        customCallback.onComplete(current_user);
                    }

                }
            }
        });
    }

    // Set winner in current sp in order to display it in Activity_End_Game page
    private void setUserOnSP(User user) {
        String json = gson.toJson(user);
        mySPV.putString(MySPV.KEYS.CURRENT_USER, json);
        // Set user id
        mySPV.putString(MySPV.KEYS.CURRENT_USER_ID, userId);
    }

    public static Activity_Main getInstance(){
        return activity_main;
    }


}