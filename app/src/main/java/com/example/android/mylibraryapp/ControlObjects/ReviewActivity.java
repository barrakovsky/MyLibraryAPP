package com.example.android.mylibraryapp.ControlObjects;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.Review;
import com.example.android.mylibraryapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class ReviewActivity extends BaseActivity {

    private DocumentReference bookRef;
    private String userID;

    private EditText reviewEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Book book = (Book) getIntent().getSerializableExtra("Book");
        String bookID = getIntent().getStringExtra("bookID");

        TextView bookTitleText = findViewById(R.id.review_title_tx);
        TextView bookAuthorText = findViewById(R.id.review_author_tx);

        reviewEditText = findViewById(R.id.review_text_et);

        bookRef = db.collection("Book").document(bookID);

        bookTitleText.setText(book.getTitle());
        bookAuthorText.setText(book.getAuthor());

    }

    public void submitReview(View v){

        DocumentReference revRef = bookRef.collection("Reviews").document();
        // TODO: find different way to create unique ids?
        String revID = UUID.randomUUID().toString();
        String summary = reviewEditText.getText().toString();
        Review newRev = new Review(revID, userID, summary);
        revRef.set(newRev);
        finish();

    }

}
