package com.example.attendenceproject;

public class UserData {
    private String UserName;
    private String UserEmail;
    private String UserMobile;
    private String Password;
    private boolean isAdmin;

    public UserData() {
        // Default constructor required for Firebase
    }

    public UserData(String userName, String userEmail, String userMobile, String password, boolean isAdmin) {
        this.UserName = userName;
        this.UserEmail = userEmail;
        this.UserMobile = userMobile;
        this.Password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}