package com.example.android.mylibraryapp.ControlObjects;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddBookToDB extends BaseActivity{


    private static final String TAG = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference booksRef;

    EditText title_editText;
    EditText author_editText;
    EditText isbn_editText;
    EditText publisher_editText;
    EditText genre_editText;
    EditText totQty_editText;
    EditText numOfPages_editText;
    EditText publishedYear_edit_Text;
    EditText summary_edit_Text;
    Button saveChangesToDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.isAdmin = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);



        //Casting views
        title_editText = findViewById(R.id.bookTitleEditText);
        author_editText = findViewById(R.id.bookAuthorEditText);
        isbn_editText = findViewById(R.id.bookISBNEditText);
        publisher_editText = findViewById(R.id.bookPublisherEditText);
        genre_editText = findViewById(R.id.bookGenreEditText);
        totQty_editText = findViewById(R.id.totQtyEditText);
        numOfPages_editText = findViewById(R.id.numPagesEditText);
        publishedYear_edit_Text = findViewById(R.id.publishedYearEditText);
        summary_edit_Text = findViewById(R.id.bookSummaryEditText);
        saveChangesToDB = findViewById(R.id.bSaveToDB);


        saveChangesToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String title = title_editText.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(AddBookToDB.this, "Please Enter book title", Toast.LENGTH_SHORT).show();
                    title_editText.requestFocus();
                    return;
                }

                final String author = author_editText.getText().toString();
                if (TextUtils.isEmpty(author)) {
                    Toast.makeText(AddBookToDB.this, "Please Enter book author", Toast.LENGTH_SHORT).show();
                    author_editText.requestFocus();
                    return;
                }

                String isbn_temp = isbn_editText.getText().toString();
                if(TextUtils.isEmpty(isbn_temp) ||isbn_temp.length() != 13){
                    Toast.makeText(AddBookToDB.this, "ISBN can't be empty or length should be 13 char", Toast.LENGTH_SHORT).show();
                    isbn_editText.requestFocus();
                    return;
                }
                final long isbn = Long.parseLong(isbn_temp);;

                final String publisher = publisher_editText.getText().toString();
                final String bookGenre = genre_editText.getText().toString();


                String totQty_temp = totQty_editText.getText().toString();
                if(TextUtils.isEmpty(totQty_temp)){
                    Toast.makeText(AddBookToDB.this, "Total qty should be only numbers", Toast.LENGTH_SHORT).show();
                    totQty_editText.requestFocus();
                    return;
                }
                final int totQty = Integer.parseInt(totQty_temp);


                String numberOfPages_temp = numOfPages_editText.getText().toString();
                int value = 0;
                if(!TextUtils.isEmpty((numberOfPages_temp))){
                    value = Integer.parseInt(numberOfPages_temp);
                }

                final int numberOfPages = value;


                String publishedYear_temp = publishedYear_edit_Text.getText().toString();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                value = 0;
                String yearString = String.valueOf(year);
                if(!TextUtils.isEmpty(publishedYear_temp)){
                    if (!TextUtils.isDigitsOnly(publishedYear_temp) || publishedYear_temp.length() != 4 || publishedYear_temp.compareTo(yearString) <= 0){
                        Toast.makeText(AddBookToDB.this, "Published year should be 4 digits and published year should be smaller or equal to the current year", Toast.LENGTH_SHORT).show();
                        publishedYear_edit_Text.requestFocus();
                        return;
                    }else{
                        value = Integer.parseInt(publishedYear_temp);
                    }
                }

                final int publishedYear = value;

                final String summary = summary_edit_Text.getText().toString();




                booksRef = db.collection("Book");

                Query query = booksRef.whereEqualTo("isbn", isbn);
                query.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    boolean b = task.getResult().isEmpty();
                                    if(b){
                                        boolean canAdd = true;
                                        final Book book = new Book(isbn, title, author, publishedYear, bookGenre, numberOfPages, publisher, summary, totQty, totQty);

                                        //Add to DB
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("isbn", book.getIsbn());
                                        data.put("title", book.getTitle());
                                        data.put("author", book.getAuthor());
                                        data.put("publishingYear", book.getPublishingYear());
                                        data.put("genre", book.getGenre());
                                        data.put("numOfPages", book.getNumberOfPages());
                                        data.put("publisher", book.getPublisher());
                                        data.put("summary", book.getSummary());
                                        data.put("availableQty", book.getAvailableQty());
                                        data.put("totQty", book.getTotQty());


                                        db.collection("Book")
                                                .add(data)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(AddBookToDB.this, "Added book was Successful ! ", Toast.LENGTH_SHORT).show();
                                                        title_editText.setText(null);
                                                        author_editText.setText(null);
                                                        isbn_editText.setText(null);
                                                        publisher_editText.setText(null);
                                                        genre_editText.setText(null);
                                                        totQty_editText.setText(null);
                                                        numOfPages_editText.setText(null);
                                                        publishedYear_edit_Text.setText(null);
                                                        summary_edit_Text.setText(null);
                                                        title_editText.requestFocus();
                                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error adding document", e);
                                                    }
                                                });

                                    }else{
                                        Toast.makeText(AddBookToDB.this, "This ISBN already exist in the system, please use edit book details window", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }





                            }


            });
        }

    });


}}
