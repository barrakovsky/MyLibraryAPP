package com.example.android.mylibraryapp.ControlObjects;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.Misc.BookAdapter;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchResultsActivity extends BaseActivity{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference bookRef = db.collection("Book");

    private BookAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    private ImageButton filter;
    private Spinner field;
    private Spinner genre;
    private Spinner order;
    private TextView fieldText;
    private TextView genreText;
    private TextView orderText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", false);
        setContentView(R.layout.activity_search_result);
        searchView = findViewById(R.id.search_view);
        field = findViewById(R.id.search_field);
        genre = findViewById(R.id.genre_filter);
        order = findViewById(R.id.search_order);
        fieldText = findViewById(R.id.search_by);
        genreText = findViewById(R.id.search_genre);
        orderText = findViewById(R.id.order_by);
        filter = findViewById(R.id.search_filter);

        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(this, R.array.search_fields, android.R.layout.simple_spinner_item);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        field.setAdapter(fieldAdapter);

        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(this, R.array.search_genres, android.R.layout.simple_spinner_item);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre.setAdapter(genreAdapter);

        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(this, R.array.search_orders, android.R.layout.simple_spinner_item);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order.setAdapter(orderAdapter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (field.getVisibility() == View.GONE) {
                    field.setVisibility(View.VISIBLE);
                    fieldText.setVisibility(View.VISIBLE);
                    genre.setVisibility(View.VISIBLE);
                    genreText.setVisibility(View.VISIBLE);
                    order.setVisibility(View.VISIBLE);
                    orderText.setVisibility(View.VISIBLE);
                }
                else if (field.getVisibility() == View.VISIBLE) {
                    field.setVisibility(View.GONE);
                    fieldText.setVisibility(View.GONE);
                    genre.setVisibility(View.GONE);
                    genreText.setVisibility(View.GONE);
                    order.setVisibility(View.GONE);
                    orderText.setVisibility(View.GONE);
                }
            }
        });

        setUpRecyclerView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = bookRef.orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Book> option = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        adapter = new BookAdapter(option);

        recyclerView = findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Book book = documentSnapshot.toObject(Book.class);
                Intent intent = new Intent(getApplicationContext(), ViewBookInfoActivity.class);
                intent.putExtra("Book", book);
                intent.putExtra("bookID", documentSnapshot.getId());

                startActivity(intent);
            }
        });
    }

    private void search(String search) {
        Query query = bookRef;

        if (field.getSelectedItem().toString().equals("Title")) {
            if (order.getSelectedItem().toString().equals("Ascending")) {
                query = query.orderBy("title", Query.Direction.ASCENDING);
            }
            else{
                query = query.orderBy("title", Query.Direction.DESCENDING);
            }
        }
        else if (field.getSelectedItem().toString().equals("Author")) {
            if (order.getSelectedItem().toString().equals("Ascending")) {
                query = query.orderBy("author", Query.Direction.ASCENDING);
            }
            if (order.getSelectedItem().toString().equals("Descending")) {
                query = query.orderBy("author", Query.Direction.DESCENDING);
            }
        }

        if (!genre.getSelectedItem().toString().equals("All")) {
            query = query.whereEqualTo("genre", genre.getSelectedItem().toString());
        }

        query = query.startAt(search).endAt(search + "\uf8ff");

        FirestoreRecyclerOptions<Book> option = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        adapter.updateOptions(option);
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
