package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.Favorite;
import com.example.android.mylibraryapp.EntityObjects.Review;
import com.example.android.mylibraryapp.Misc.ReviewAdapter;
import com.example.android.mylibraryapp.R;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewBookInfoActivity extends BaseActivity {

    private Button addReviewBut;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reviewRef;
    private FirebaseUser user;

    private ReviewAdapter adapter;

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
    private CheckBox favorite;
    Boolean active = true;

    private ArrayList<DocumentReference> favorites = new ArrayList<>();

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
        favorite = findViewById(R.id.bookInfoFavorite);

        book = (Book) getIntent().getSerializableExtra("Book");
        bookID = getIntent().getStringExtra("bookID");
        user = FirebaseAuth.getInstance().getCurrentUser();

        final CollectionReference faveRef = db.collection("Users").document(user.getUid()).collection("Favorites");
        final DocumentReference bookRef = db.collection("Book").document(bookID);

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

        //change status of favorite button based on favorites list
        faveRef.whereEqualTo("bookRef", bookRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.get("bookRef").equals(bookRef))
                                    favorite.setChecked(true);
                                else favorite.setChecked(false);
                            }
                        }
                    }
                });

        //add and remove from favorites
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite.isChecked()) {
                    Favorite fave = new Favorite(bookRef, book.getTitle(), book.getAuthor());

                    faveRef.document()
                            .set(fave);
                    String message = "Added to favorites";
                    Toast.makeText(ViewBookInfoActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Query query = faveRef.whereEqualTo("bookRef", bookRef);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                                    faveRef.document(documentSnapshot.getId()).delete();
                                Toast.makeText(ViewBookInfoActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Review Button is disabled if the user has already written a review for this book.
        setUpReviewButton();

        // Setting up reviews at bottom of page
        TextView reviewHead = findViewById(R.id.book_info_rev_head_tx);
        reviewHead.setText(String.format("Read reviews about %s:", book.getTitle()));
        setUpRecyclerView();
    }

    private void setUpReviewButton() {
        addReviewBut = findViewById(R.id.add_review_but);
        reviewRef = db.collection("Book").document(bookID).collection("Reviews");

        reviewRef.whereEqualTo("userID", FirebaseAuth.getInstance().getUid() )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!Objects.requireNonNull(querySnapshot).isEmpty()) { // no documents found
                                addReviewBut.setAlpha(.08f);
                                addReviewBut.setClickable(false);
                            }
                        }
                    }
                });
    }

    // AddReviewActivity starts when Add Review button is pressed
    public void AddReview(View view) {
        Intent addRev = new Intent(ViewBookInfoActivity.this, AddReviewActivity.class);
        addRev.putExtra("bookID", bookID);
        addRev.putExtra("Book", book);
        startActivity(addRev);
    }

    // EditReviewActivity starts when user presses on one of their own reviews
    public void EditReview(Review review, String revID) {
        Intent editRev = new Intent(ViewBookInfoActivity.this, EditReviewActivity.class);
        editRev.putExtra("bookID", bookID);
        editRev.putExtra("Book", book);
        editRev.putExtra("Review", review);
        editRev.putExtra("ReviewID", revID);
        startActivity(editRev);
    }

    // Book rental button functionality
    public void rentBook(View v) {

        String rentalID = Long.toString(System.currentTimeMillis());

        CollectionReference collectionReference = db.collection("Users").document(user.getUid()).collection("Rentals");

        String rentalStartDate = java.text.DateFormat.getDateTimeInstance().format(new Date());


        Map<String, Object> userRental = new HashMap<>();

        userRental.put("rentalID", rentalID);
        userRental.put("active", active);
        userRental.put("bookTitle", book.getTitle());
        userRental.put("rentalDueDate", "");
        userRental.put("isbn", book.getIsbn());
        userRental.put("amount", "");
        userRental.put("returnedDate", "");
        userRental.put("rentalStartDate", rentalStartDate);

        


        collectionReference.add(userRental);
    }

    // Recyclerview for reviews shown at bottom of screen
    private void setUpRecyclerView() {

        // Reviews displayed in order of last edited date first
        Query query = reviewRef.orderBy("lastDate", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Review> option = new FirestoreRecyclerOptions.Builder<Review>()
                .setQuery(query, Review.class)
                .build();

        adapter = new ReviewAdapter(option);

        adapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Review review = documentSnapshot.toObject(Review.class);
                // Any review that was mad by the logged in user is clickable and editable by that user
                if (Objects.requireNonNull(review).getUserID().equals(FirebaseAuth.getInstance().getUid())) {
                    String revID = documentSnapshot.getId();
                    EditReview(review, revID);
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.reviews_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        setUpReviewButton();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
