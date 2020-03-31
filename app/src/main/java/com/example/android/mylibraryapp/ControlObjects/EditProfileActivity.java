package com.example.android.mylibraryapp.ControlObjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends BaseActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String uid;
    String userID = Long.toString(System.currentTimeMillis());



    EditText firstName_editText;
    EditText lastName_editText;
    EditText userName_editText;
    EditText phoneNumber_editText;
    EditText email_editText;
    EditText password_editText;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        //Casting Views
        firstName_editText = findViewById(R.id.editTextFirstName);
        lastName_editText = findViewById(R.id.editTextLastName);
        userName_editText = findViewById(R.id.editTextUserName);
        phoneNumber_editText = findViewById(R.id.editTextPhone);
        email_editText = findViewById(R.id.editTextEmail);
        saveButton = findViewById(R.id.saveDataButton);


        //Writing to the database
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        uid = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = firebaseFirestore.collection("Users").document(uid);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {

                    String firstName;
                    String lastName;
                    String userName;
                    String email;
                    String phone;
                    firstName = task.getResult().getString("firstName");
                    lastName  = task.getResult().getString("lastName");
                    userName  = task.getResult().getString("userName");
                    email     = task.getResult().getString("email");
                    phone     = task.getResult().getString("phone");


                    firstName_editText.setText(firstName);
                    lastName_editText.setText(lastName);
                    userName_editText.setText(userName);
                    email_editText.setText(email);
                    phoneNumber_editText.setText(phone);


                }
            }

            ;
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = firstName_editText.getText().toString();
                final String lastName = lastName_editText.getText().toString();
                final String userName = userName_editText.getText().toString();
                final String phone = phoneNumber_editText.getText().toString();
                final String email = email_editText.getText().toString();

                final User information = new User(userID, firstName, lastName, userName, email, phone, false);


                documentReference.update(
                        "firstName", information.getfName(),
                        "lastName", information.getlName(),
                        "userName", information.getUserName(),
                        "phone", information.getPhone(),
                        "email", information.getEmail()
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "Information Updated", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }

    public void changePassword(View view) {
        //Intent to register form
        Intent password = new Intent(EditProfileActivity.this, changePasswordActivity.class);
        startActivity(password);
    }



}