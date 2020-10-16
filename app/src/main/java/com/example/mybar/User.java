package com.example.mybar;

public class User {
    private String full_name;
    private String email;
    private String phone;

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

    public User(String full_name, String email, String phone) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
    }
}
