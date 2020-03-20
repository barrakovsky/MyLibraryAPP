package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //Instantiating views
    Button registerButton;
    EditText firstName_editText;
    EditText lastName_editText;
    EditText userName_editText;
    EditText phoneNumber_editText;
    EditText email_editText;
    EditText password_editText;

    //The entry point of the Firebase Authentication SDK.
    FirebaseAuth firebaseAuth;

    //A Firebase reference that represents a particular location in your Database and can be used for reading or writing data to that Database location
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Casting Views
        firstName_editText = findViewById(R.id.firstName);
        lastName_editText = findViewById(R.id.lastName);
        userName_editText = findViewById(R.id.userName);
        phoneNumber_editText = findViewById(R.id.phoneNumber);
        email_editText = findViewById(R.id.email);
        password_editText = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);

        //Writing to the database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        //Event handler when the register button is clicked
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Converting EditText to Strings
                final String firstName = firstName_editText.getText().toString();
                final String lastName = lastName_editText.getText().toString();
                final String userName = userName_editText.getText().toString();
                final String phone = phoneNumber_editText.getText().toString();
                final String email = email_editText.getText().toString();
                String password = password_editText.getText().toString();

                //Checking if First Name field is empty
                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Your First Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking if Last Name field is empty
                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking if user Name field is empty
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Your User Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking if Phone Number field is empty
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking if User Name field is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking if User Name field is empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Creates a new user account with the email and password provided by the user
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Method to test if user sign up is successful
                                if (task.isSuccessful()) {

                                    //Creating a user instance
                                    User information = new User(userName, firstName, lastName, email, phone, false);

                                    //Writes the user data to database
                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            //Toast which displays if registration is successful and redirects user to Quiz activity
                                            Toast.makeText(RegisterActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });

                                } else {

                                    //Toast displayed if registering user field
                                    Toast.makeText(RegisterActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }


    //Function that when clicked reroutes to the login activity
    public void Login(View view)
    {
        //Intent to Login activity
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
    }
}

