package com.example.android.mylibraryapp.EntityObjects;

import java.io.Serializable;

public class Book implements Serializable {
    private String author;
    private int availableQty;
    private String genre;
    private long isbn;
    private int numberOfPages;
    private String publisher;
    private int publishingYear;
    private String summary;
    private String title;
    private int totQty;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotQty(int totQty) {
        this.totQty = totQty;
    }


    public long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSummary() {
        return summary;
    }

    public int getTotQty() {
        return totQty;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public Book() {
        //empty constructor
    }

    public Book(long isbn, String title, String author, int publishingYear, String genre, int numberOfPages, String publisher, String summary, int totQty, int availableQty) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.publisher = publisher;
        this.summary = summary;
        this.totQty = totQty;
        this.availableQty = availableQty;
    }


}
