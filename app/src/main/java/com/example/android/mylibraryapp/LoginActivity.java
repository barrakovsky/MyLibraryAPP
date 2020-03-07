package com.example.android.mylibraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    //Instantiating views
    EditText loginEmail;
    EditText loginPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Casting Views
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
    }

    //Function that when clicked reroutes to the registration form
    public void Register(View view) {
        //Intent to register form
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    //Prevents the back button from being used on the login page
    @Override
    public void onBackPressed()
    {}
}
