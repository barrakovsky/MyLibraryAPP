package com.example.android.mylibraryapp.EntityObjects;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {

    private String userID;      // User's id in Users doc
    private String userName;    // User's name at time of last review writing
    private Date origDate;      // Date review was originally written
    private Date lastDate;      // Date of last revision (may be same as above)
    private String review;      // Review, stored as a string

    public Review() {
        // no arg constructor (needed for firebase instantiation)
    }

    public Review(String userID, String userName, Date origDate, Date lastDate, String review) {
        this.userID = userID;
        this.userName = userName;
        this.origDate = origDate;
        this.lastDate = lastDate;
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

    public Date getOrigDate() { return origDate; }

    public void setOrigDate(Date origDate) { this.origDate = origDate; }

    public Date getLastDate() { return lastDate; }

    public void setLastDate(Date lastDate) { this.lastDate = lastDate; }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}