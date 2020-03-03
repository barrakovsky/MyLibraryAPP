package com.example.libraryapp;

import java.time.Year;

public class Book {
    private String ISBN;
    private String title;
    private String author;
    private Year publishingYear;
    private String genre;
    private int numPages;
    private String publisher;
    private String summary;
    private int totQty;
    private int availableQty;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String bookISBN) {
        ISBN = bookISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String bookTitle) {
        title = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String bookAuthor) {
        author = bookAuthor;
    }

    public Year getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Year bookPublishingYear) {
        publishingYear = bookPublishingYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String bookGenre) {
        genre = bookGenre;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int bookNumPages) {
        numPages = bookNumPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String bookPublisher) {
        publisher = bookPublisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String bookSummary) {
        summary = bookSummary;
    }

    public int getTotQty() {
        return totQty;
    }

    public void setTotQty(int bookTotQty) {
        totQty = bookTotQty;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int bookAvailableQty) {
        availableQty = bookAvailableQty;
    }

    public Book(String ISBN, String title, String author, Year publishingYear, String genre, int numPages, String publisher, String summary) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.genre = genre;
        this.numPages = numPages;
        this.publisher = publisher;
        this.summary = summary;
    }

    public Book(String ISBN, String title, String author, Year publishingYear, int numPages, String publisher) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.genre = "N/A";
        this.numPages = numPages;
        this.publisher = publisher;
        this.summary = "N/A";
    }

}