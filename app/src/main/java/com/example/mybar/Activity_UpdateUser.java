package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Activity_UpdateUser extends AppCompatActivity {

    private EditText UpdateUser_EDT_full_name;
    private EditText UpdateUser_EDT_email;
    private EditText UpdateUser_EDT_phone;
    private EditText UpdateUser_EDT_password;
    private Button UpdateUser_BTN_SignUp;
    private ProgressBar UpdateUser_PRBR_progressBar;
    private Button UpdateUser_BTN_back;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser mAuth_user;
    private String userId;



    private MySPV mySPV;
    Gson gson = new Gson();

    User current_user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__update_user);

        findViews();

        // Set Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Set SP
        mySPV = new MySPV(this);

        getUserFromSP();

        UpdateUser_BTN_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user information
                mAuth_user = mAuth.getCurrentUser();
                userId = mAuth.getCurrentUser().getUid();
                updateUser();
            }
        });

        // Go back and close activity
        UpdateUser_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void findViews() {
        UpdateUser_EDT_full_name = findViewById(R.id.UpdateUser_EDT_full_name);
        UpdateUser_EDT_email = findViewById(R.id.UpdateUser_EDT_email);
        UpdateUser_EDT_phone = findViewById(R.id.UpdateUser_EDT_phone);
        UpdateUser_EDT_password = findViewById(R.id.UpdateUser_EDT_password);
        UpdateUser_BTN_SignUp = findViewById(R.id.UpdateUser_BTN_SignUp);
        UpdateUser_PRBR_progressBar = findViewById(R.id.UpdateUser_PRBR_progressBar);
        UpdateUser_BTN_back = findViewById(R.id.UpdateUser_BTN_back);
    }

    // Set current_user with information from SP
    private void getUserFromSP() {
        String json_user = mySPV.getString(MySPV.KEYS.CURRENT_USER, "No User");
        current_user = gson.fromJson(json_user, User.class);
    }
    private void updateUser() {
        String new_name = UpdateUser_EDT_full_name.getText().toString().trim();
        String new_email = UpdateUser_EDT_email.getText().toString().trim();
        String new_phone = UpdateUser_EDT_phone.getText().toString().trim();
        String new_password = UpdateUser_EDT_password.getText().toString().trim();
        // Show progress bar
        UpdateUser_PRBR_progressBar.setVisibility(View.VISIBLE);

        boolean valid_params = true;
        boolean update_password = true;
        boolean update_email = true;

        // Set full name to old one if not changed
        if(new_name.isEmpty())
            new_name = current_user.getFull_name();
        // Set email to old one if not changed
        if(new_email.isEmpty()){
            new_email = current_user.getEmail();
            // set boolean value in order to validate if email need to be change in Authenticator
            update_email = false;
        }
        // Set phone to old one if not changed
        if(new_phone.isEmpty())
            new_phone = current_user.getPhone();
        // Test password length and set boolean value in order to validate if password need to be change in Authenticator
        if(new_password.length() <= 0)
            update_password = false;
            // If password is not valid notify user
        else if(new_password.length() < 6) {
            Toast.makeText(Activity_UpdateUser.this, "Password need to contain at least 6 characters", Toast.LENGTH_LONG).show();
            valid_params = false;
            // Stop showing progress bar
            UpdateUser_PRBR_progressBar.setVisibility(View.GONE);
        }

        if(valid_params){
            // Update FireStore with new values
            updateDB(new_name, new_email, new_phone, new_password ,update_email, update_password);
        }

    }

    private void updateAut(boolean update_email, String new_email, boolean update_password, String new_password){
        if(update_email)
            mAuth_user.updateEmail(new_email);
        if(update_password)
            mAuth_user.updatePassword(new_password);

    }

    private void updateSP(String new_name, String new_email, String new_phone) {
        current_user.setFull_name(new_name).setEmail(new_email).setPhone(new_phone);
        String json = gson.toJson(current_user);
        mySPV.putString(MySPV.KEYS.CURRENT_USER, json);
    }

    private void updateDB(final String new_name, final String new_email,final String new_phone, final String new_password ,final boolean update_email,final boolean update_password) {
        DocumentReference documentReference = db.collection("users").document(userId);
        Map<String,Object> user = new HashMap<>();
        user.put("full_name" , new_name);
        user.put("email", new_email);
        user.put("phone", new_phone);
        user.put("drinks", current_user.getDrinks());
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Firestore was update
                Toast.makeText(Activity_UpdateUser.this, "details updated successfully", Toast.LENGTH_LONG).show();
                Log.d("pttt", "OnSuccess");
                // Update Authenticator
                updateAut(update_email, new_email, update_password, new_password);
                // Update SP with new values
                updateSP(new_name, new_email, new_phone);
                // Reload activity_profile
                Activity_Profile.getInstance().recreate();
                // Close activity
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            // Firestore was not update - make toast to notify user
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Activity_UpdateUser.this, "Error", Toast.LENGTH_LONG).show();
                Log.w("pttt", "Error adding document", e);
                // Stop showing progress bar
                UpdateUser_PRBR_progressBar.setVisibility(View.GONE);
            }
        });
    }
}