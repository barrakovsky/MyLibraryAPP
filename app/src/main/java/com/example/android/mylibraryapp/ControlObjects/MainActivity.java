package com.example.android.mylibraryapp.ControlObjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends BaseActivity {


    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();

    TextView userFirstName;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                arrayList.clear();
                listView = findViewById(R.id.homePageListView);
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    //arrayList.add(ds.getString(""));

                    userFirstName = findViewById(R.id.userFirstName);

                    String firstname;
                    firstname = ds.getString("firstName");
                    userFirstName.setText("Hello " + firstname);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
                arrayAdapter.notifyDataSetChanged();
                listView.setAdapter(arrayAdapter);


            }
        });
    }

}

