package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.Review;
import com.example.android.mylibraryapp.Misc.BookAdapter;
import com.example.android.mylibraryapp.Misc.ReviewAdapter;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewBookInfoActivity extends BaseActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reviewRef;

    private ReviewAdapter adapter;
    private RecyclerView recyclerView;

    private Book book;
    private String bookID;

    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView genre;
    private TextView summary;
    private TextView publishing;
    private TextView pages;
    private TextView quantity;

    private TextView reviewHead;

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

        reviewRef = db.collection("Book").document(bookID).collection("Reviews");

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

        reviewHead = findViewById(R.id.book_info_rev_head_tx);
        reviewHead.setText(String.format("Read reviews about %s:", book.getTitle()));

        setUpRecyclerView();
    }

    public void AddReview(View v) {
        Intent addRev = new Intent(ViewBookInfoActivity.this, ReviewActivity.class);
        addRev.putExtra("bookID", bookID);
        addRev.putExtra("Book", book);
        startActivity(addRev);
    }

    // TODO: Fix up for reviews
    private void setUpRecyclerView() {
        Query query = reviewRef.orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Review> option = new FirestoreRecyclerOptions.Builder<Review>()
                .setQuery(query, Review.class)
                .build();

        adapter = new ReviewAdapter(option);

        recyclerView = findViewById(R.id.reviews_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
