package com.example.attendenceproject.Models;

public class AdminModel {
    private String Name;
    private String Password;
    private String Phone;
    private boolean IsAdmin;

    public AdminModel() {
        // Default constructor required for Firebase
    }

    public AdminModel(String name, String password, String phone, boolean isAdmin) {
        Name = name;
        Password = password;
        Phone = phone;
        IsAdmin = isAdmin;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setAdmin(boolean admin) {
        IsAdmin = admin;
    }
}