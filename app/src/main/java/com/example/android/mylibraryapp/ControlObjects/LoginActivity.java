package com.example.android.mylibraryapp.ControlObjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Instantiating views
    EditText loginEmail_editText;
    EditText loginPassword_editText;
    Button loginButton;

    //Declaring an instance of FirebaseAuth
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
