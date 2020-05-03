package com.example.android.mylibraryapp.ControlObjects;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class EditBookQty extends BaseActivity{


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference booksRef;

    TextInputLayout isbn_textView;
    Button getBook;

    TextInputLayout bookTitle_textView;
    TextInputLayout totQty_textView;
    TextInputLayout aveQty_textView;


    RadioGroup radioOperationGroup;


    TextInputLayout adjustQty_textView;
    Button adjustQty_button;


    String docID;
    Query query;

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.isAdmin = true;
        setContentView(R.layout.editbookqty);




        isbn_textView = findViewById(R.id.editQtyIsbnInput);
        getBook = findViewById(R.id.editQtyGetBook);
        bookTitle_textView = findViewById(R.id.editBookTitleInputLayout);
        totQty_textView = findViewById(R.id.editBookTotQtyInputLayout);
        aveQty_textView = findViewById(R.id.editBookAveQtyInputLayout);
        radioOperationGroup = findViewById(R.id.operationGroup);
        adjustQty_textView = findViewById(R.id.inputLayoutQtyChange);
        adjustQty_button = findViewById(R.id.changeQty);


        bookTitle_textView.getEditText().setEnabled(false);
        totQty_textView.getEditText().setEnabled(false);
        aveQty_textView.getEditText().setEnabled(false);

        radioOperationGroup.setEnabled(false);
        adjustQty_textView.setEnabled(false);
        adjustQty_button.setEnabled(false);



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
                        totQty_textView.getEditText().setText((String)document.getData().get("totQty").toString());
                        aveQty_textView.getEditText().setText((String)document.getData().get("availableQty").toString());
                        getBook.setEnabled(false);
                        isbn_textView.setEnabled(false);

                        radioOperationGroup.setEnabled(true);
                        adjustQty_textView.setEnabled(true);
                        adjustQty_button.setEnabled(true);

                    }



                }else{

                    Toast.makeText(EditBookQty.this, "The query didn't have any results, try a different ISBN", Toast.LENGTH_SHORT).show();


                }
            }
        });


    }

    public void adjustQty(View v){

        //Get the number from the layout input
        String adjustQty_temp = adjustQty_textView.getEditText().getText().toString();
        int totQty_temp = Integer.parseInt(totQty_textView.getEditText().getText().toString());
        int aveQty_temp = Integer.parseInt(aveQty_textView.getEditText().getText().toString());

        if(TextUtils.isEmpty(adjustQty_temp)){
            Toast.makeText(EditBookQty.this, "Adjust should be only numbers", Toast.LENGTH_SHORT).show();
            adjustQty_textView.requestFocus();
            return;
        }

        int adjustQty = Integer.parseInt(adjustQty_temp);

        //get the operation and change the number accordingly
        int selectedId = radioOperationGroup.getCheckedRadioButtonId();
        if( findViewById(R.id.radioButtonRemove) == findViewById(selectedId)){
            if(adjustQty > aveQty_temp){
                Toast.makeText(EditBookQty.this, "Can't adjust, since the qty to reduce is greater than the qty available", Toast.LENGTH_SHORT).show();
                return;
            }

            //Remove operation
            adjustQty = 0-adjustQty;

        }





        final int totQty = totQty_temp + adjustQty;
        final int availableQty = aveQty_temp + adjustQty;

        //update the number in the docuemnt

        db.collection("Book").document(docID).update("totQty", totQty, "availableQty", availableQty).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");

                isbn_textView.setEnabled(true);
                isbn_textView.getEditText().getText().clear();

                bookTitle_textView.getEditText().getText().clear();
                totQty_textView.getEditText().getText().clear();
                aveQty_textView.getEditText().getText().clear();
                adjustQty_textView.getEditText().getText().clear();

                radioOperationGroup.setEnabled(false);
                adjustQty_textView.setEnabled(false);
                adjustQty_button.setEnabled(false);

                getBook.setEnabled(true);
                isbn_textView.setEnabled(true);


                Toast.makeText(EditBookQty.this, "Book qty was update to the DB", Toast.LENGTH_SHORT).show();

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
