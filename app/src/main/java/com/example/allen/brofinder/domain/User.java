package com.example.allen.brofinder.domain;

public class User {
    private String displayName;
    private String email;

    public User(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }
}
