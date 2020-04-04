package com.example.android.mylibraryapp.EntityObjects;

import java.util.Date;

public class Review {

    private String userID;
    private String userName;
    private Date date;
    private String review;

    public Review() {
        // no arg constructor
    }
    public Review(String userID, String userName, Date date, String review) {
        this.userID = userID;
        this.userName = userName;
        this.date = date;
        this.review = review;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}