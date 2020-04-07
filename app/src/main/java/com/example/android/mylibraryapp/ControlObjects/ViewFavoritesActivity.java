package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.Favorite;
import com.example.android.mylibraryapp.Misc.BookAdapter;
import com.example.android.mylibraryapp.Misc.FavoriteAdapter;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewFavoritesActivity extends BaseActivity{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference faveRef = db.collection("Users").document(user.getUid()).collection("Favorites");

    private FavoriteAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorites);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = faveRef.orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Favorite> option = new FirestoreRecyclerOptions.Builder<Favorite>()
                .setQuery(query, Favorite.class)
                .build();

        adapter = new FavoriteAdapter(option);

        recyclerView = findViewById(R.id.favoriteBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Favorite fave = documentSnapshot.toObject(Favorite.class);
                db.collection("Book").document(fave.getBookRef().getId())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Book book = documentSnapshot.toObject(Book.class);
                                Intent intent = new Intent(getApplicationContext(), ViewBookInfoActivity.class);
                                intent.putExtra("Book", book);
                                intent.putExtra("bookID", documentSnapshot.getId());

                                startActivity(intent);
                            }
                        });
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
