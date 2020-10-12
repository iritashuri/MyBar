package com.example.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Activity_LogIn extends AppCompatActivity {

    private Button LogIn_BTN_LogIn;

    private EditText LogIn_EDT_email;
    private EditText LogIn_EDT_password;
    private TextView SignUp_TXT_signUp;
    private ProgressBar SignIn_PRBR_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__log_in);

        findViews();
    }

    private void findViews(){
        LogIn_BTN_LogIn = findViewById(R.id.LogIn_BTN_LogIn);
        LogIn_EDT_email = findViewById(R.id.LogIn_EDT_email);
        LogIn_EDT_password = findViewById(R.id.LogIn_EDT_password);
        SignUp_TXT_signUp = findViewById(R.id.SignUp_TXT_signUp);
        SignIn_PRBR_progressBar = findViewById(R.id.SignIn_PRBR_progressBar);
    }
}