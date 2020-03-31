package com.example.android.mylibraryapp.ControlObjects;

import android.os.Bundle;
import android.widget.TextView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.R;

public class ViewBookInfoActivity extends BaseActivity {
    TextView title;
    TextView author;
    TextView isbn;
    TextView genre;
    TextView summary;
    TextView publishing;
    TextView pages;
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_information);

        title = findViewById(R.id.bookInfoTitle);
        author = findViewById(R.id.bookInfoAuthor);
        isbn = findViewById(R.id.bookInfoIsbn);
        genre = findViewById(R.id.bookInfoGenre);
        summary = findViewById(R.id.bookInfoSummary);
        publishing = findViewById(R.id.bookInfoPublishing);
        pages = findViewById(R.id.bookInfoPages);
        quantity = findViewById(R.id.bookInfoQty);

        Book book = (Book)getIntent().getSerializableExtra("Book");

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(Long.toString(book.getIsbn()));
        genre.setText("Genre: " + book.getGenre());
        summary.setText("\"" + book.getSummary() + "\"");
        publishing.setText("Published by " + book.getPublisher() + ", " + book.getPublishingYear());
        pages.setText(book.getNumberOfPages() + " pages");
        quantity.setText(book.getAvailableQty() + "/" + book.getTotQty() + " copies available");
    }
}
