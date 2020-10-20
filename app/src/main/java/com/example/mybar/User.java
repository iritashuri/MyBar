package com.example.mybar;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class User {
    private String full_name;
    private String email;
    private String phone;
    private int drinks = 0;


    public User() {
    }


    public User(String full_name, String email, String phone, String password) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
    }

    public int getDrinks() {
        return drinks;
    }

    public User setDrinks(int drinks) {
        this.drinks = drinks;
        return this;
    }

    public String getFull_name() {
        return full_name;
    }

    public User setFull_name(String full_name) {
        this.full_name = full_name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }


    public User setPhone(String phone) {
        this.phone = phone;

        return this;
    }
}
