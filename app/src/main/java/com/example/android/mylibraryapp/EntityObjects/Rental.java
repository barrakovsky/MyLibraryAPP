package com.example.android.mylibraryapp.EntityObjects;

import java.util.Date;

public class Rental {



    private String rentalID;
    private Date rentalDueDate;
    private Date rentalStartDate;
    private Date rentalReturnedDate;
    private String userID;
    private long isbn;
    private int amount;
    private boolean active;
    private String bookTitle;

    public Rental(){

    }

    public Rental(String rentalID, String bookTitle, Date rentalDueDate, long isbn, int amount, boolean active, String userID,  Date rentalStartDate) {
        this.rentalID = rentalID;
        this.userID = userID;
        this.rentalDueDate = rentalDueDate;
        this.rentalStartDate = rentalStartDate;
        this.active = active;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.amount = amount;

    }

    public String getRentalID() {
        return rentalID;
    }

    public void setRentalID(String rentalId) {
        this.rentalID = rentalID;
    }

    public Date getRentalDueDate() {
        return rentalDueDate;
    }

    public void setRentalDueDate(Date rentalDueDate) {
        this.rentalDueDate = rentalDueDate;
    }

    public Date getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public Date getRentalReturnedDate() {
        return rentalReturnedDate;
    }

    public void setRentalReturnedDate(Date rentalReturnedDate) {
        this.rentalReturnedDate = rentalReturnedDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }






}
