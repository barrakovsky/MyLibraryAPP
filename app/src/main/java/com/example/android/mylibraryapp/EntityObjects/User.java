package com.example.android.mylibraryapp.EntityObjects;


import java.util.List;

public class User {

    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String userName;
    private boolean superUser;

    private List<Favorite> favorite;
    private List<Rental> rentals;
    private List<Payment> payments;

    public User() {
        // empty constructor
    }

    public User(String userID, String firstName, String lastName, String userName, String email, String phone, boolean superUser){

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.superUser = superUser;

    }

    private String password;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
                "userId='" + userID + '\'' +
                ", fName='" + firstName + '\'' +
                ", lName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", superUser=" + superUser +
                ", password='" + password + '\'' +
                '}';
    }
}
