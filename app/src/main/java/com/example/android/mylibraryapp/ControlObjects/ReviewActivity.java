package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class ReviewActivity extends BaseActivity {

    private DocumentReference bookRef;
    private String userID;
    private User user;
    private EditText reviewEditText;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_review);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
            }
        });

        Book book = (Book) getIntent().getSerializableExtra("Book");
        String bookID = getIntent().getStringExtra("bookID");
        bookRef = db.collection("Book").document(bookID);

        TextView bookTitleText = findViewById(R.id.review_title_tx);
        TextView bookAuthorText = findViewById(R.id.review_author_tx);

        reviewEditText = findViewById(R.id.review_text_et);

        bookTitleText.setText(book.getTitle());
        bookAuthorText.setText(book.getAuthor());

    }

    public void submitReview(View v){
        String summary = reviewEditText.getText().toString();

        if(TextUtils.isEmpty(summary)) {
            Toast.makeText(ReviewActivity.this, "You must enter a review!", Toast.LENGTH_SHORT).show();
        } else {
            DocumentReference revRef = bookRef.collection("Reviews").document();
            Date date = new Date();
            Review newRev = new Review(userID, user.getUserName(), date, summary);
            revRef.set(newRev);
            finish();
        }
    }
}
