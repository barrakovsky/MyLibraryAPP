package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.Review;
import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.Objects;

public class EditReviewActivity extends BaseActivity {

    private DocumentReference reviewRef;
    private User user;
    private EditText reviewEditText;
    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", true);

        // We'll just reuse the Add Review layout. No reason not to
        setContentView(R.layout.activity_review);
        TextView headerText = findViewById(R.id.review_header_tx);
        headerText.setText(getResources().getText(R.string.edit_review_header));

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // getting user -- will update user's username on the review
        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
            }
        });

        // Get Extras
        Book book = (Book) getIntent().getSerializableExtra("Book");
        String bookID = getIntent().getStringExtra("bookID");
        review = (Review) getIntent().getSerializableExtra("Review");
        String revID = getIntent().getStringExtra("ReviewID");

        // getting review to update
        reviewRef = db.collection("Book").document(Objects.requireNonNull(bookID)).collection("Reviews").document(Objects.requireNonNull(revID));

        TextView bookTitleText = findViewById(R.id.review_title_tx);
        TextView bookAuthorText = findViewById(R.id.review_author_tx);
        Button submitBut = findViewById(R.id.review_submit_but);

        // Get original review text and put into box so it can be edited
        reviewEditText = findViewById(R.id.review_text_et);
        reviewEditText.setText(review.getReview());

        bookTitleText.setText(Objects.requireNonNull(book).getTitle());
        bookAuthorText.setText(book.getAuthor());

        submitBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    submitNewReview(view);
            }
        });

    }

    // Replaces original review in database, changes last date, updates username
    public void submitNewReview(View view){
        String summary = reviewEditText.getText().toString();

        if(TextUtils.isEmpty(summary)) {
            Toast.makeText(EditReviewActivity.this, "You must enter a review!", Toast.LENGTH_SHORT).show();
        } else if (summary.contentEquals(review.getReview())) {
            // ...User didn't do anything, so why bother sending it to the server
            Toast.makeText(EditReviewActivity.this, "Review left unchanged.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Review newRev = review;
            Date date = new Date();
            newRev.setLastDate(date);
            newRev.setReview(summary);
            newRev.setUserName(user.getUserName());
            reviewRef.set(newRev);
            finish();
        }
    }
}
