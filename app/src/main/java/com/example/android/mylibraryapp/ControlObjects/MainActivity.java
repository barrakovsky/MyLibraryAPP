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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
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
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends BaseActivity {


    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();

    TextView userFirstName;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        firebaseAuth.getInstance().getCurrentUser().getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult result) {

                Map<String, Object>  claimsMap = new HashMap<String, Object>();
                claimsMap = result.getClaims();

                if(result.getClaims().containsKey("admin")){
                    isAdmin  = (boolean) result.getClaims().get("admin");
                    if (isAdmin) {
                        setContentView(R.layout.activity_main_admin);
                    }else{
                        setContentView(R.layout.activity_main);
                    }

                }else{
                    setContentView(R.layout.activity_main);
                }

            }
        });




        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                arrayList.clear();
                listView = findViewById(R.id.homePageListView);
                if (task.isSuccessful() && task.getResult() != null) {
                    //arrayList.add(ds.getString(""));

                    userFirstName = findViewById(R.id.userFirstName);

                    String firstname;
                    firstname = task.getResult().getString("firstName");
                    userFirstName.setText("Hello " + firstname);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
                arrayAdapter.notifyDataSetChanged();
                listView.setAdapter(arrayAdapter);
            }
        });
    }

}

