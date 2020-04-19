package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.mylibraryapp.EntityObjects.Request;
import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestBookActivity extends BaseActivity {

    private EditText requestTitle, requestAuthor, requestISBN, requestGenre, requestPublisher, requestYear;
    private Button requestSubmit;
    FirebaseAuth firebaseAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", false);
        firebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.request_form);

        requestTitle = findViewById(R.id.request_title);
        requestAuthor = findViewById(R.id.request_author);
        requestISBN = findViewById(R.id.request_ISBN);
        requestGenre = findViewById(R.id.request_genre);
        requestPublisher = findViewById(R.id.request_publisher);
        requestYear = findViewById(R.id.request_year);
        requestSubmit = findViewById(R.id.request_submit);
    }

    private boolean validateTitle() {
        String titleInput = requestTitle.getText().toString().trim();

        if (titleInput.isEmpty()) {
            requestTitle.setError("Field cannot be empty");
            return false;
        } else {
            requestTitle.setError(null);
            return true;
        }
    }

    private boolean validateAuthor() {
        String authorInput = requestAuthor.getText().toString().trim();
        
        if (authorInput.isEmpty()) {
            requestAuthor.setError("Field cannot be empty");
            return false;
        } else {
            requestAuthor.setError(null);
            return true;
        }
    }

    private boolean validateYear() {
        String yearInput = requestYear.getText().toString().trim();
        if (!"".equals(yearInput) && yearInput.length() != 4) {
            requestYear.setError("Invalid year");
            return false;
        } else {
            requestYear.setError(null);
            return true;
        }
    }

    public void submitRequest(View v) {
        if (!validateTitle() | !validateAuthor() | !validateYear()) {
            return;
        }

        //create a new request object
        long ISBN = 0;
        int year = 0;
        String title = requestTitle.getText().toString().trim();
        String author = requestAuthor.getText().toString().trim();
        String genre = requestGenre.getText().toString().trim();
        String publisher = requestPublisher.getText().toString().trim();
        String tempISBN = requestISBN.getText().toString();
        String tempYear = requestYear.getText().toString();
        if (!"".equals(tempISBN)) {//if field is left empty ISBN is 0
            ISBN = Integer.parseInt(tempISBN);
        }
        if (!"".equals(tempYear)) {//if field is left empty year is 0
            year = Integer.parseInt(tempYear);
        }
        Request add = new Request(ISBN, title, author, year, genre, publisher);

        String message = "Request submitted";

        //add to firestore
        db.collection("Requests").document()
                .set(add)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Request", "DocumentSnapshot successfully written");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Request", "Error writing document", e);
            }
        });

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        menuReturn();
    }

    public void menuReturn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
