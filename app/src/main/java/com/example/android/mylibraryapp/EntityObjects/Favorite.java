package com.example.android.mylibraryapp.EntityObjects;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class Favorite {
    private DocumentReference bookRef;
    private String title;
    private String author;

    public Favorite() {
        //empty constructor
    }

    public Favorite(DocumentReference bookRef, String title, String author) {
        this.bookRef = bookRef;
        this.title = title;
        this.author = author;
    }

    public DocumentReference getBookRef() {
        return bookRef;
    }

    public void setBookRef(DocumentReference bookRef) {
        this.bookRef = bookRef;
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
}
