package com.example.android.mylibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RequestBookActivity extends AppCompatActivity {

    private EditText requestTitle, requestAuthor, requestISBN, requestGenre, requestPublisher, requestYear;
    private Button requestSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        int ISBN = 0;
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
        //Request add = new Request(ISBN, title, author, year, genre, publisher);

        String message = "Request submitted";

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        //menuReturn();
    }

    /*public void menuReturn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

}
