package com.example.mylibraryapp;

import java.util.Date;

public class Rental {


    public Rental(String rentalId, Date rentalDueDate, User user, Book book, int amount, boolean active) {
        this.rentalId = rentalId;
        this.rentalDueDate = rentalDueDate;
        this.user = user;
        this.book = book;
        this.amount = amount;
        this.active = active;
    }

    private String rentalId;
    private Date rentalDueDate;
    private User user;
    private Book book;
    private int amount;
    private boolean active;



    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }



    public Date getRentalDueDate() {
        return rentalDueDate;
    }

    public void setRentalDueDate(Date rentalDueDate) {
        this.rentalDueDate = rentalDueDate;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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













}
