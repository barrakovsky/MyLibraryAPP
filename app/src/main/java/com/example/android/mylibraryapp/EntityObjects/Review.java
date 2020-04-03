package com.example.android.mylibraryapp.EntityObjects;

public class Review {

    private String reviewID;
    private String review;
    private String userID;

    public Review(String reviewID, String userID, String review) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.review = review;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}