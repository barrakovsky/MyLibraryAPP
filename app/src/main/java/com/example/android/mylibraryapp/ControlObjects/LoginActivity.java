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

        //Casting Views
        loginEmail_editText = findViewById(R.id.loginEmail);
        loginPassword_editText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        //Initializing the FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();


        //Button Click Event Handler
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = loginEmail_editText.getText().toString();
                String password = loginPassword_editText.getText().toString();

                //Checking if email field is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking if password field is empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

               /*Creates a signIn method which takes in an email address and password, validates
               them , and signs in a user with the signInWithEmailandPassword method
               */

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //If login is successful, toast displays
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            //If login fails successful, toast displays
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
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
