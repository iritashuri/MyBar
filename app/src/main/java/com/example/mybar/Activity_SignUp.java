package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_SignUp extends AppCompatActivity {

    private EditText SignUp_EDT_full_name;
    private EditText SignUp_EDT_email;
    private EditText SignUp_EDT_password;
    private EditText SignUp_EDT_password_confirm;
    private Button SignUp_BTN_SignUp;
    private ProgressBar SignUp_PRBR_progressBar;
    private TextView SignUp_TXT_logIn;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Activity_Welcome.class));
            finish();
        }

        SignUp_BTN_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               insertDetails();
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
        SignUp_EDT_password = findViewById(R.id.SignUp_EDT_password);
        SignUp_EDT_password_confirm = findViewById(R.id.SignUp_EDT_password_confirm);
        SignUp_BTN_SignUp = findViewById(R.id.SignUp_BTN_SignUp);
        SignUp_PRBR_progressBar = findViewById(R.id.SignUp_PRBR_progressBar);
        SignUp_TXT_logIn = findViewById(R.id.SignUp_TXT_logIn);
    }

    // insert new user details
    private void insertDetails() {
        // Get users data
        String full_name = SignUp_EDT_full_name.getText().toString().trim();
        String email = SignUp_EDT_email.getText().toString().trim();
        String password = SignUp_EDT_password.getText().toString().trim();
        String password2 = SignUp_EDT_password_confirm.getText().toString().trim();

        // Check if there are errors and return if there is
        if(checkErrors(full_name, email, password, password2)){
            return;
        }

        // Show progress bar
        SignUp_PRBR_progressBar.setVisibility(View.VISIBLE);

        // Register the user in firebase
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Activity_SignUp.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Activity_Welcome.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Show err message and set progress bar to invisible
                    Toast.makeText(Activity_SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    SignUp_PRBR_progressBar.setVisibility(View.GONE);
                }

            }
        });

    }

    // Check if there are errors and return true if there is
    boolean checkErrors(String full_name, String email, String password, String password2){

        // Check if there is no empty value
        if(checkEmpties(full_name, email, password, password2)){
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
    boolean checkEmpties(String full_name, String email, String password, String password2){
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

        return to_return;
    }

    // Check if edit test is empty
    boolean checkEmptyTxt(String str, EditText editText, String missing){
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
