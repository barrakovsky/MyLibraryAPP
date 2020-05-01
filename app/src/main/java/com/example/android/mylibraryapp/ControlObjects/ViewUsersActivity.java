package com.example.android.mylibraryapp.ControlObjects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.Misc.UserAdapter;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class ViewUsersActivity extends BaseActivity {

    CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");

    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);


        setUpRecyclerView();
    }

    // Recyclerview for reviews shown at bottom of screen
    private void setUpRecyclerView() {

        // Reviews displayed in order of last edited date first
        Query query = usersRef.orderBy("userID", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<User> option = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new UserAdapter(option);

        adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //User user = documentSnapshot.toObject(User.class);
                // fix me
            }
        });

        RecyclerView recyclerView = findViewById(R.id.viewusr_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
