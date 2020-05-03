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

import java.util.Date;
import java.util.Objects;

public class AddReviewActivity extends BaseActivity {

    private DocumentReference bookRef;
    private String userID;
    private User user;
    private EditText reviewEditText;
    private Button submitBut;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", false);

        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_review);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
            }
        });

        Book book = (Book) getIntent().getSerializableExtra("Book");
        String bookID = getIntent().getStringExtra("bookID");
        bookRef = db.collection("Book").document(Objects.requireNonNull(bookID));

        TextView bookTitleText = findViewById(R.id.review_title_tx);
        TextView bookAuthorText = findViewById(R.id.review_author_tx);
        submitBut = findViewById(R.id.review_submit_but);

        reviewEditText = findViewById(R.id.review_text_et);

        bookTitleText.setText(Objects.requireNonNull(book).getTitle());
        bookAuthorText.setText(book.getAuthor());
        submitBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    submitReview(v);
            }
        });

    }

    public void submitReview(View view){
        String summary = reviewEditText.getText().toString();

        if(TextUtils.isEmpty(summary)) {
            Toast.makeText(AddReviewActivity.this, "You must enter a review!", Toast.LENGTH_SHORT).show();
        } else {
            DocumentReference revRef = bookRef.collection("Reviews").document();
            Date date = new Date(); // We put this as original & last date; indicates no edits
            Review newRev = new Review(userID, user.getUserName(), date, date, summary);
            revRef.set(newRev);
            finish();
        }
    }
}
