package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class Activity_Profile extends AppCompatActivity {

    private TextView Profile_TXT_Name;
    private TextView Profile_TXT_email;
    private TextView Profile_TXT_phone;
    private Button Profile_BTN_edit;
    private Button Profile_BTN_LogOut;
    private Button Profile_BTN_delete;
    private Button Profile_BTN_back;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private MySPV mySPV;
    private Gson gson = new Gson();

    static Activity_Profile activity_profile;


    User current_user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);

        // Set firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Set SP
        mySPV = new MySPV(this);

        activity_profile = this;

        findViews();

        getUserFromSP();

        displayUser();

        // Update details
        Profile_BTN_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEdit();
            }
        });

        // Delete account
        Profile_BTN_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccount();
            }
        });

        // Log out
        Profile_BTN_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        // Go back and close activity
        Profile_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openEdit() {
        Intent intent = new Intent(Activity_Profile.this, Activity_UpdateUser.class);
        startActivity(intent);
    }

    private void DeleteAccount() {
        FirebaseUser user = mAuth.getCurrentUser();

        // Delete from FireStore
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("pttt", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("pttt", "Error deleting document", e);
                    }
                });

        // Delete from Firebase Authenticator
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("pttt", "User account deleted.");
                            //Open Welcome
                            finish();
                            openWelcome();
                        }else {
                            Toast.makeText(Activity_Profile.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void logOut() {
        mAuth.signOut();
        openWelcome();
        finish();
    }

    private void displayUser() {
        Profile_TXT_Name.setText(current_user.getFull_name());
        Profile_TXT_email.setText(current_user.getEmail());
        Profile_TXT_phone.setText(current_user.getPhone());
    }

    // Set current_user with information from SP
    private void getUserFromSP() {
        String json_user = mySPV.getString(MySPV.KEYS.CURRENT_USER, "No User");
        current_user = gson.fromJson(json_user, User.class);
    }

    private void findViews(){
         Profile_TXT_Name = findViewById(R.id.Profile_TXT_Name);
         Profile_TXT_email= findViewById(R.id.Profile_TXT_email);
         Profile_TXT_phone= findViewById(R.id.Profile_TXT_phone);
         Profile_BTN_edit= findViewById(R.id.Profile_BTN_edit);
         Profile_BTN_LogOut= findViewById(R.id.Profile_BTN_LogOut);
         Profile_BTN_delete= findViewById(R.id.Profile_BTN_delete);
        Profile_BTN_back = findViewById(R.id.Profile_BTN_back);
    }

    private void openWelcome(){
        // Finish main activity
        Activity_Main.getInstance().finish();
        // Open welcome activity
        Intent intent = new Intent(Activity_Profile.this, Activity_Welcome.class);
        startActivity(intent);
        finish();
    }

    public static Activity_Profile getInstance(){
        return activity_profile;
    }

}