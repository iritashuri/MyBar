package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_SignUp extends AppCompatActivity {

    private EditText SignUp_EDT_full_name;
    private EditText SignUp_EDT_email;
    private EditText SignUp_EDT_password;
    private EditText SignUp_EDT_password_confirm;
    private EditText SignUp_EDT_phone;
    private Button SignUp_BTN_SignUp;
    private ProgressBar SignUp_PRBR_progressBar;
    private TextView SignUp_TXT_logIn;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Activity_Main.class));
            finish();
        }

        SignUp_BTN_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               RegisterUser();
            }
        });

        SignUp_TXT_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogIn();
            }
        });

    }

    private void findViews(){
        SignUp_EDT_full_name = findViewById(R.id.SignUp_EDT_full_name);
        SignUp_EDT_email = findViewById(R.id.SignUp_EDT_email);
        SignUp_EDT_phone = findViewById(R.id.SignUp_EDT_phone);
        SignUp_EDT_password = findViewById(R.id.SignUp_EDT_password);
        SignUp_EDT_password_confirm = findViewById(R.id.SignUp_EDT_password_confirm);
        SignUp_BTN_SignUp = findViewById(R.id.SignUp_BTN_SignUp);
        SignUp_PRBR_progressBar = findViewById(R.id.SignUp_PRBR_progressBar);
        SignUp_TXT_logIn = findViewById(R.id.SignUp_TXT_logIn);
    }

    // Insert new user details
    private void RegisterUser() {
        // Get users data
        final String full_name = SignUp_EDT_full_name.getText().toString().trim();
        final String email = SignUp_EDT_email.getText().toString().trim();
        final String phone = SignUp_EDT_phone.getText().toString().trim();
        String password = SignUp_EDT_password.getText().toString().trim();
        String password2 = SignUp_EDT_password_confirm.getText().toString().trim();

        // Check if there are errors and return if there is
        if(checkErrors(full_name, email, password, password2, phone)){
            return;
        }

        // Show progress bar
        SignUp_PRBR_progressBar.setVisibility(View.VISIBLE);

        // Register the user in firebase
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Activity_SignUp.this, "Signed up successfully", Toast.LENGTH_LONG).show();

                    // Get uuid from firebase auth
                    userId = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("users").document(userId);
                    Map<String,Object> user = new HashMap<>();
                    user.put("full_name" , full_name);
                    user.put("email", email);
                    user.put("phone", phone);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("pttt", "OnSuccess: user sugned up -- > "+ full_name);
                        }
                    });


                    Intent intent = new Intent(getApplicationContext(), Activity_Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Show err message and set progress bar to invisible
                    Toast.makeText(Activity_SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    // Stop showing progress bar
                    SignUp_PRBR_progressBar.setVisibility(View.GONE);
                }

            }
        });

    }

    // Check if there are errors and return true if there is
    private boolean checkErrors(String full_name, String email, String password, String password2, String phone){

        // Check if there is no empty value
        if(checkEmpties(full_name, email, password, password2, phone)){
            return true;
        }

        // Validate password length
        if(password.length() < 6){
            SignUp_EDT_password.setError("Password Must include at least 6 characters. ");
            return true;
        }

        // Validate password mach
        if(!password.equals(password2)){
            SignUp_EDT_password_confirm.setError("Passwords does not match. ");
            return true;
        }
        return false;
    }

    // Check if email or password was not insert
    private boolean checkEmpties(String full_name, String email, String password, String password2, String phone){
        boolean to_return = false;

        if (checkEmptyTxt(email, SignUp_EDT_email, "Email")){
            to_return = true;
        }
        if (checkEmptyTxt(password, SignUp_EDT_password, "Password")){
            to_return = true;
        }
        if (checkEmptyTxt(password2, SignUp_EDT_password_confirm, "Password")){
            to_return = true;
        }
        if (checkEmptyTxt(full_name, SignUp_EDT_full_name, "Full name")){
            to_return = true;
        }

        if (checkEmptyTxt(phone, SignUp_EDT_full_name, "Phone number")){
            to_return = true;
        }

        return to_return;
    }

    // Check if edit text is empty
    private boolean checkEmptyTxt(String str, EditText editText, String missing){
        if(TextUtils.isEmpty(str)){
            editText.setError(missing + "is Required. ");
            return true;
        }
        return false;
    }


    private void openLogIn() {
        Intent intent = new Intent(getApplicationContext(), Activity_LogIn.class);
        startActivity(intent);
        finish();
    }
}
