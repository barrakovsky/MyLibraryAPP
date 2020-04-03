package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.R;

public class ViewBookInfoActivity extends BaseActivity {

    Book book;
    String bookID;

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

        book = (Book)getIntent().getSerializableExtra("Book");
        bookID = getIntent().getStringExtra("bookID");

        //set book information
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        if (book.getIsbn() == 0)
            isbn.setText("ISBN: N/A");
        else
            isbn.setText("ISBN: " + book.getIsbn());
        if (book.getGenre() == "")
            genre.setText("Genre: N/A");
        else
            genre.setText("Genre: " + book.getGenre());
        if (book.getSummary() == "")
            summary.setText("Summary: N/A");
        else
            summary.setText("Summary: \"" + book.getSummary() + "\"");
        if (book.getPublishingYear() == 0 && book.getPublisher() == "")
            publishing.setText("Publisher: N/A  Year: N/A");
        else if (book.getPublisher() == "")
            publishing.setText("Publisher: N/A  Year: " + book.getPublishingYear());
        else if (book.getPublishingYear() == 0)
            publishing.setText("Publisher: " + book.getPublisher() + "  Year: N/A");
        else
            publishing.setText("Publisher: " + book.getPublisher() + "  Year: " + book.getPublishingYear());
        if (book.getNumberOfPages() == 0)
            pages.setText("Number of pages: N/A");
        else
            pages.setText(book.getNumberOfPages() + " pages");
        quantity.setText(book.getAvailableQty() + "/" + book.getTotQty() + " copies available");



    }

    public void AddReview(View v) {
        Intent addRev = new Intent(ViewBookInfoActivity.this, ReviewActivity.class);
        addRev.putExtra("bookID", bookID);
        addRev.putExtra("Book", book);
        startActivity(addRev);
    }

}
