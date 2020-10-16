package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_LogIn extends AppCompatActivity {

    private Button LogIn_BTN_LogIn;

    private EditText LogIn_EDT_email;
    private EditText LogIn_EDT_password;
    private ProgressBar SignIn_PRBR_progressBar;
    private FirebaseAuth mAuth;
    private TextView SignIn_TXT_signUp;
    private TextView SignIn_TXT_forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__log_in);

        mAuth = FirebaseAuth.getInstance();

        findViews();

        LogIn_BTN_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn();
            }
        });

        SignIn_TXT_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });

        // Reset password
        SignIn_TXT_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswordDialog(view);
            }
        });

    }

    private void findViews(){
        LogIn_BTN_LogIn = findViewById(R.id.LogIn_BTN_LogIn);
        LogIn_EDT_email = findViewById(R.id.LogIn_EDT_email);
        LogIn_EDT_password = findViewById(R.id.LogIn_EDT_password);
        SignIn_PRBR_progressBar = findViewById(R.id.SignIn_PRBR_progressBar);
        SignIn_TXT_signUp = findViewById(R.id.SignIn_TXT_signUp);
        SignIn_TXT_forgotPassword = findViewById(R.id.SignIn_TXT_forgotPassword);
    }


    private void LogIn() {
        // Get users data
        String email = LogIn_EDT_email.getText().toString().trim();
        String password = LogIn_EDT_password.getText().toString().trim();

        // Check if there are errors and return if there is
        if(checkErrors(email, password)){
            return;
        }

        // Show progress bar
        SignIn_PRBR_progressBar.setVisibility(View.VISIBLE);

        // Authenticate user
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Activity_Welcome.getInstance().finish();
                    Toast.makeText(Activity_LogIn.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Activity_Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Show err message and set progress bar to invisible
                    Toast.makeText(Activity_LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    SignIn_PRBR_progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    // Check if there are errors and return true if there is
    boolean checkErrors(String email, String password){

        if(checkEmpties(email,password)){
            return true;
        }
        // Validate password length
        if(password.length() < 6){
            LogIn_EDT_password.setError("Password Must include at least 6 characters. ");
            return true;
        }
        return false;
    }

    // Check if email or password was not insert
    boolean checkEmpties(String email, String password){
        boolean to_return = false;

        if (checkEmptyTxt(email, LogIn_EDT_email, "Email")){
            to_return = true;
        }
        if (checkEmptyTxt(password, LogIn_EDT_password, "Password")){
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



    private void openSignUp() {
        Intent intent = new Intent(getApplicationContext(), Activity_SignUp.class);
        startActivity(intent);
        finish();
    }

  private void resetPasswordDialog(View view){
      final EditText resetMail = new EditText(view.getContext());
      AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
      passwordResetDialog.setTitle("Reset password ?");
      passwordResetDialog.setMessage("Enter your email to receive a link. ");
      passwordResetDialog.setView(resetMail);

      passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              // Extract the email and send reset link
              String mail = resetMail.getText().toString();
              mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      Toast.makeText(Activity_LogIn.this, "Reset link sent to your email. ", Toast.LENGTH_LONG).show();
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      // Display error
                      Toast.makeText(Activity_LogIn.this, "Error! Reset link is not sent " + e.getMessage(), Toast.LENGTH_LONG).show();
                  }
              });
          }
      });
      passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              // Close the dialog
          }
      });

      passwordResetDialog.create().show();

  }


}