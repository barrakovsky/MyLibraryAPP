package com.example.android.mylibraryapp.ControlObjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.Payment;
import com.example.android.mylibraryapp.EntityObjects.Rental;
import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.Misc.BookAdapter;
import com.example.android.mylibraryapp.Misc.RentalAdapter;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class RentalActivity extends BaseActivity {
    FirebaseAuth firebaseAuth;
    private static final String TAG = "RentalActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference rentalRef = db.collection("Users").document(Objects.requireNonNull(user).getUid()).collection("Rentals");


    private RentalAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", false);

        //Set the main view
        setContentView(R.layout.activity_rental);

        setUpRecycleView();

    }

    private void setUpRecycleView() {
        Query rentals = rentalRef.whereEqualTo("active", true);

        FirestoreRecyclerOptions<Rental> options = new FirestoreRecyclerOptions.Builder<Rental>()
                .setQuery(rentals, Rental.class).build();

        adapter = new RentalAdapter(options);
        RecyclerView rentalRecyclerView = findViewById(R.id.rentalList);
        rentalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rentalRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RentalAdapter.OnItemClickListener() {
            @Override
            public void onButtonClick(int position) {
                renewRental(position);
            }
        });


    }

    public void renewRental(int position){
        //get the rental document for the position
        Rental rental = adapter.getItem(position);

        Date startDate = rental.getRentalStartDate();
        Date dueDate = rental.getRentalDueDate();

        int diffInDays = Math.round(( (dueDate.getTime() - startDate.getTime())
                / (1000 * 60 * 60 * 24) ));

        if(diffInDays >= 29){
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(rental.getRentalDueDate());
        cal.add(Calendar.DATE, 5);

        //Setting the new due Date in the client app
        rental.setRentalDueDate(cal.getTime());

        //after everything changes notify the adapter that
        adapter.notifyItemChanged(position);

        final int fPosition = position;
        final Date fdueDate = rental.getRentalDueDate();
        //pushing the update into the db
        rentalRef.whereEqualTo("rentalID", rental.getRentalID()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                rentalRef.document(document.getId()).update("rentalDueDate", fdueDate

                                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //after everything changes notify the adapter that
                                            adapter.notifyItemChanged(fPosition);


                                        }
                                    }
                                });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }


}
