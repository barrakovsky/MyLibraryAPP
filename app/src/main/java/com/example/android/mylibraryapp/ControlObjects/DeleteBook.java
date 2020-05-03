package com.example.android.mylibraryapp.ControlObjects;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteBook extends BaseActivity {

    TextInputLayout isbn_textView;
    Button deleteBook;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference booksRef;

    private static final String TAG = "";

    Query query;
    String docID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.isAdmin = true;
        setContentView(R.layout.admin_remove_book);


        isbn_textView = findViewById(R.id.removeBookIsbnInputLayout);
        deleteBook = findViewById(R.id.deleteBook);



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


    public void deleteBook(View v){

        if(!validateISBN()){
            return;
        }

        final long isbn = Long.parseLong(isbn_textView.getEditText().getText().toString());

        //find if there is a book with this ISBN.
        getBook(v);


        // [START fs_collection_group_query]
        db.collectionGroup("Rentals").whereEqualTo("isbn", isbn).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()){
                            //No open rentals--delete the book
                            db.collection("Book").document(docID).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            Toast.makeText(DeleteBook.this, "Book was deleted from the library system!", Toast.LENGTH_SHORT).show();
                                            isbn_textView.setEnabled(true);
                                            isbn_textView.getEditText().getText().clear();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error deleting document", e);
                                        }
                                    });


                        }else{
                            //Open rentals-- can't delete the book
                            Toast.makeText(DeleteBook.this, "The book has open rentals, can't delete", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DeleteBook.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });


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
                        isbn_textView.setEnabled(false);
                    }

                    //need to make the isbn field not editable


                }else{

                    Toast.makeText(DeleteBook.this, "The query didn't have any results, try a different ISBN", Toast.LENGTH_SHORT).show();


                }
            }
        });






    }
}
