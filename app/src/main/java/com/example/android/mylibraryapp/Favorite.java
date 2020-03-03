package com.example.libraryapp;

import java.util.ArrayList;
import java.util.List;

public class Favorite {
    private List<Book> favoriteBooks;
    private User user;

    public List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavorites(List<Book> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Favorite(User user) {
        this.user = user;
        this.favoriteBooks = new ArrayList<Book>();
    }

    public void addFavorite(Book book) {
        favoriteBooks.add(book);
    }

    public void removeFavorite(Book book) {
        favoriteBooks.remove(book);
    }

}