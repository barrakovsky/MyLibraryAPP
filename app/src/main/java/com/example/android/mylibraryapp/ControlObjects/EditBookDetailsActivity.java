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
import com.google.android.gms.tasks.Task;
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

    TextView isbn_textView;
    TextView bookTitle_textView;
    TextView author_textView;
    TextView publisher_textView;
    TextView Year_textView;
    TextView genre_textView;
    TextView bookSummary_textView;

    Button submit;
    Button getBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", false);
        setContentView(R.layout.activity_edit_book_details);

         isbn_textView = findViewById(R.id.edit_book_isbn_et);
         bookTitle_textView = findViewById(R.id.edit_book_title_et);
         author_textView = findViewById(R.id.edit_book_author_et);
         publisher_textView = findViewById(R.id.edit_book_publisher_et);
         Year_textView = findViewById(R.id.edit_book_year_et);
         genre_textView = findViewById(R.id.edit_book_genre_et);
         bookSummary_textView = findViewById(R.id.edit_book_summary_et);
         submit = findViewById(R.id.edit_book_but);
         getBook = findViewById(R.id.bGetBook);



        getBook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String isbn = isbn_textView.getText().toString();

                if (TextUtils.isEmpty(isbn)) {
                    Toast.makeText(EditBookDetailsActivity.this, "Please Enter book isbn to get the book from the database", Toast.LENGTH_SHORT).show();
                    isbn_textView.requestFocus();
                    return;
                } else {

                    booksRef = db.collection("Book");

                    Query query = booksRef.whereEqualTo("isbn", isbn);
                    query.get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && task.getResult() != null){
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            bookTitle_textView.setText((String)document.getData().get("bookTitle"));
                                            author_textView.setText((String)document.getData().get("author"));
                                            publisher_textView.setText((String)document.getData().get("publisher"));
                                            Year_textView.setText((int)document.getData().get("publishingYear"));
                                            genre_textView.setText((String)document.getData().get("genre"));
                                            bookSummary_textView.setText((String)document.getData().get("summary"));

                                        }






                                    }else{
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }

                                }

                            });
                }
            }
        });




    }

}
