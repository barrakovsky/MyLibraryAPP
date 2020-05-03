package com.example.android.mylibraryapp.ControlObjects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.mylibraryapp.EntityObjects.Payment;
import com.example.android.mylibraryapp.Misc.FavoriteAdapter;
import com.example.android.mylibraryapp.Misc.PaymentAdapter;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class PaymentActivity extends BaseActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference payRef = db.collection("Users")
            .document(Objects.requireNonNull(user).getUid())
            .collection("Payments");

    PaymentAdapter outAdapter;
    PaymentAdapter histAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", false);

        setContentView(R.layout.activity_payment);
        setUpRecyclerViews();
    }

    private void setUpRecyclerViews() {

        // set up outstanding payments recyclerview
        Query outQuery = payRef.whereEqualTo("paidFlag", false)
                .orderBy("invDate", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Payment> outOptions = new FirestoreRecyclerOptions.Builder<Payment>()
                .setQuery(outQuery, Payment.class)
                .build();

        outAdapter = new PaymentAdapter(outOptions);

        RecyclerView outRecyclerView = findViewById(R.id.pay_out_recycler);
        outRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        outRecyclerView.setAdapter(outAdapter);

        // set up historical payments recyclerview
        Query histQuery = payRef.whereEqualTo("paidFlag", true)
                .orderBy("invDate", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Payment> histOptions = new FirestoreRecyclerOptions.Builder<Payment>()
                .setQuery(histQuery, Payment.class)
                .build();

        histAdapter = new PaymentAdapter(histOptions);

        RecyclerView histRecyclerView = findViewById(R.id.pay_hist_recycler);
        histRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        histRecyclerView.setAdapter(histAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        outAdapter.startListening();
        histAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        outAdapter.stopListening();
        histAdapter.stopListening();
    }
}
