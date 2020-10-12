package com.example.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_SignUp extends AppCompatActivity {

    private EditText SignUp_EDT_full_name;
    private EditText SignUp_EDT_Id;
    private EditText SignUp_EDT_email;
    private EditText SignUp_EDT_password;
    private EditText SignUp_EDT_password_confirm;
    private Button SignUp_BTN_SignUp;
    private ProgressBar SignUp_PRBR_progressBar;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();

        SignUp_BTN_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               insertDetails();
            }
        });



    }

    private void insertDetails() {
        String full_name = SignUp_EDT_full_name.getText().toString().trim();
        String id = SignUp_EDT_Id.getText().toString().trim();
        String email = SignUp_EDT_email.getText().toString().trim();
        String password = SignUp_EDT_password.getText().toString().trim();
        String password2 = SignUp_EDT_password_confirm.getText().toString().trim();


        if(checkErrors(full_name, id, email, password, password2)){
            return;
        }

        // Show progress bar
        SignUp_PRBR_progressBar.setVisibility(View.VISIBLE);

        // Register the user in firebase


    }
    boolean checkErrors(String full_name, String id, String email, String password, String password2){

        // Check if there is no empty value
        if(checkEmpties(full_name, id, email, password, password2)){
            return true;
        }

        // validate password length
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

    boolean checkEmpties(String full_name, String id, String email, String password, String password2){
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
        if (checkEmptyTxt(id, SignUp_EDT_Id, "Id")){
            to_return = true;
        }

        return to_return;
    }

    boolean checkEmptyTxt(String str, EditText editText, String missing){
        if(TextUtils.isEmpty(str)){
            editText.setError(missing + "is Required. ");
            return true;
        }
        return false;
    }

    private void findViews(){
        SignUp_EDT_full_name = findViewById(R.id.SignUp_EDT_full_name);
        SignUp_EDT_Id = findViewById(R.id.SignUp_EDT_Id);
        SignUp_EDT_email = findViewById(R.id.SignUp_EDT_email);
        SignUp_EDT_password = findViewById(R.id.SignUp_EDT_password);
        SignUp_EDT_password_confirm = findViewById(R.id.SignUp_EDT_password_confirm);
        SignUp_BTN_SignUp = findViewById(R.id.SignUp_BTN_SignUp);
        SignUp_PRBR_progressBar = findViewById(R.id.SignUp_PRBR_progressBar);
    }
}
