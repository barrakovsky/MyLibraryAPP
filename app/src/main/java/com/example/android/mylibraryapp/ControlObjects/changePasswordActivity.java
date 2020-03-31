package com.example.android.mylibraryapp.ControlObjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.mylibraryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class changePasswordActivity extends AppCompatActivity {

    Button submitButton;
    EditText sendEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sendEmail = findViewById(R.id.changePasswordEemail);
        submitButton = findViewById(R.id.submit);
        firebaseAuth = FirebaseAuth.getInstance();

        //Method that allows user to change password
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sendEmail.getText().toString();

                if (email.equals(""))
                {
                    Toast.makeText(changePasswordActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                firebaseAuth.getInstance().signOut();
                                Toast.makeText(changePasswordActivity.this, "Please Check Your Email To Change Your Password", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(changePasswordActivity.this, LoginActivity.class));
                                finish();
                            }
                            else
                            {
                                String error = task.getException().getMessage();
                                Toast.makeText(changePasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });





    }


}