package com.example.myapp;

public class UserProfile {

    public String userName;
    public String educLevel;
    public String userPhone;
    public String userAddress;


    public UserProfile() {

    }


    public UserProfile(String userName, String educLevel, String userPhone, String userAddress) {
        this.userName = userName;
        this.educLevel = educLevel;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEducLevel() {
        return educLevel;
    }

    public void setEducLevel(String educLevel) {
        this.educLevel = educLevel;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
