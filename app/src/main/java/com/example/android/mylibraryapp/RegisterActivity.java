package com.example.android.mylibraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    //Instantiating views
    Button registerButton;
    EditText fName;
    EditText lName;
    EditText userName;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Casting Views
        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
    }


    //Function that when clicked reroutes to the login form
    public void Login(View view)
    {
        //Intent to Login form
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
    }
}

