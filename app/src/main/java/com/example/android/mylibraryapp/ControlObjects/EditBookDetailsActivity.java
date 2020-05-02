package com.example.android.mylibraryapp.ControlObjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditBookDetailsActivity extends BaseActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference booksRef;
    private static final String TAG = "";
    Query query;
    String docID;

    TextInputLayout isbn_textView;
    TextInputLayout bookTitle_textView;
    TextInputLayout author_textView;
    TextInputLayout publisher_textView;
    TextInputLayout Year_textView;
    TextInputLayout genre_textView;
    TextInputLayout bookSummary_textView;

    Button submit;
    Button getBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        super.isAdmin = true;
        setContentView(R.layout.activity_admin_editbookdetails);

         isbn_textView = findViewById(R.id.isbnInputLayout);
         bookTitle_textView = findViewById(R.id.bookTitleLayout);
         author_textView = findViewById(R.id.authorLayout);
         publisher_textView = findViewById(R.id.publisherLayout);
         Year_textView = findViewById(R.id.yearLayout);
         genre_textView = findViewById(R.id.genreLayout);
         bookSummary_textView = findViewById(R.id.bookSummaryLayout);
         submit = findViewById(R.id.editBookSaveChanges);
         getBook = findViewById(R.id.bgetBook);



    }

    private boolean validateISBN(){
        String isbn = isbn_textView.getEditText().getText().toString();
        if(isbn.isEmpty()){
            isbn_textView.setError("Field can't be empty");
            isbn_textView.requestFocus();
            return false;
        }else{
            isbn_textView.setError(null);
            return true;
        }
    }

    public void getBook(View v){

        if(!validateISBN()){
            return;
        }

        long isbn = Long.parseLong(isbn_textView.getEditText().getText().toString());
        booksRef = db.collection("Book");
        query = booksRef.whereEqualTo("isbn", isbn);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()){

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        docID = document.getId();
                        bookTitle_textView.getEditText().setText((String)document.getData().get("title"));
                        author_textView.getEditText().setText(((String)document.getData().get("author")));
                        publisher_textView.getEditText().setText((String)document.getData().get("publisher"));

                        String publishedYear_temp = Long.toString((long)document.getData().get("publishingYear"));

                        Year_textView.getEditText().setText(publishedYear_temp);
                        genre_textView.getEditText().setText((String)document.getData().get("genre"));
                        bookSummary_textView.getEditText().setText((String)document.getData().get("summary"));

                        isbn_textView.setEnabled(false);
                    }

                    //need to make the isbn field not editable


                }else{

                    Toast.makeText(EditBookDetailsActivity.this, "The query didn't have any results, try a different ISBN", Toast.LENGTH_SHORT).show();


                }
            }
        });






    }


    public void saveChangesToDB(View v){
        //check values for title and author
        String title = bookTitle_textView.getEditText().getText().toString();
        String author = author_textView.getEditText().getText().toString();

        if(title.isEmpty()){
            bookTitle_textView.setError("Title should not be empty");
            return;
        }

        if(author.isEmpty()){
            author_textView.setError("Author should not be empty");
        }

        String publisher = publisher_textView.getEditText().getText().toString();
        long year = Long.parseLong(Year_textView.getEditText().getText().toString());
        String genre = genre_textView.getEditText().getText().toString();
        String summary = bookSummary_textView.getEditText().getText().toString();


        db.collection("Book").document(docID).update("title", title, "author", author, "publisher", publisher,
                "publishingYear", year, "genre", genre, "summary", summary).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");

                isbn_textView.setEnabled(true);
                isbn_textView.getEditText().getText().clear();

                bookTitle_textView.getEditText().getText().clear();
                author_textView.getEditText().getText().clear();
                publisher_textView.getEditText().getText().clear();
                Year_textView.getEditText().getText().clear();
                genre_textView.getEditText().getText().clear();
                bookSummary_textView.getEditText().getText().clear();

                Toast.makeText(EditBookDetailsActivity.this, "Book details were update to the DB", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });



    }

}
