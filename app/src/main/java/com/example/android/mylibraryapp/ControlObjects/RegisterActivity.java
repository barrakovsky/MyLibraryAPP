package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //Instantiating views
    Button registerButton;
    EditText firstName_editText;
    EditText lastName_editText;
    EditText userName_editText;
    EditText phoneNumber_editText;
    EditText email_editText;
    EditText password_editText;


    private static final String TAG = "MainActivity";


    //The entry point of the Firebase Authentication SDK.
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String uid;
    String userID = Long.toString(System.currentTimeMillis());

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
        firebaseFirestore = FirebaseFirestore.getInstance();



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
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Your password is too week please enter a password more than 6 characters", Toast.LENGTH_SHORT).show();
                }


                //Firebase method that creates User account and stores user data in database
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            User information = new User(userID, firstName, lastName, userName, email, phone, false);

                            Toast.makeText(RegisterActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                            uid = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("Users").document(uid);
                            Map<String, Object> user = new HashMap<>();
                            user.put("userID", information.getUserID());
                            user.put("firstName", information.getFirstName());
                            user.put("lastName", information.getLastName());
                            user.put("userName", information.getUserName());
                            user.put("email", information.getEmail());
                            user.put("phone", information.getPhone());



                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(RegisterActivity.this, "Login Successful ! ", Toast.LENGTH_SHORT).show();

                                }
                            });

                           startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException(), Toast.LENGTH_SHORT).show();
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

