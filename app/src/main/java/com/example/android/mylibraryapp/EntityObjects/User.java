package com.example.android.mylibraryapp.EntityObjects;


import com.example.android.mylibraryapp.EntityObjects.Favorite;
import com.example.android.mylibraryapp.EntityObjects.Payment;
import com.example.android.mylibraryapp.EntityObjects.Rental;

import java.util.List;

public class User {

    private String userId;
    private String fName;
    private String lName;
    private String email;
    private String phone;
    private String userName;
    private boolean superUser;

    private List<Favorite> favorite;
    private List<Rental> rentals;
    private List<Payment> payments;


    public User(String userId, String fName, String lName, String userName, String email, String phone, boolean superUser){

        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.superUser = superUser;

    }

    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSuperUser() {
        return superUser;
    }

    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  List<Favorite> getFavoriteBookList(){

        return favorite;
    }

    public List<Rental> getRentalList(){

        return rentals;
    }

    public List<Payment> getPaymentList(){

        return payments;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", superUser=" + superUser +
                ", password='" + password + '\'' +
                '}';
    }
}
