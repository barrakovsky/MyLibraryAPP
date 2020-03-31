package com.example.android.mylibraryapp.EntityObjects;

import java.time.Year;

public class Request {

    private long ISBN;
    private String title;
    private String author;
    private int publishingYear;
    private String genre;
    private String publisher;

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public Request(long ISBN, String title, String author, int publishingYear, String genre, String publisher) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.genre = genre;
        this.publisher = publisher;
    }

}
