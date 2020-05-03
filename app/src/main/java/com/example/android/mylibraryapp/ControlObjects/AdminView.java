package com.example.android.mylibraryapp.ControlObjects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import android.widget.GridLayout;
import android.widget.Toast;


import androidx.cardview.widget.CardView;


import com.example.android.mylibraryapp.R;

public class AdminView extends BaseActivity {

    GridLayout mainGrid;

    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        super.isAdmin = i.getBooleanExtra("isAdmin", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_view);

        mainGrid = findViewById(R.id.adminGridMenu);

        //Set event
        setSingleEvent(mainGrid);

    }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i=0; i < 6; i++){
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    switch (view.getId()) {
                        case R.id.adminAddBook:
                            Intent addBook = new Intent(AdminView.this, AddBookToDB.class);
                            startActivity(addBook);
                            break;
                        case R.id.adminRemoveBook:
                            Intent removeBook = new Intent(AdminView.this, DeleteBook.class);
                            startActivity(removeBook);
                            break;
                        case R.id.adminEditBook:
                            Intent editBook = new Intent(AdminView.this, EditBookDetailsActivity.class);
                            startActivity(editBook);
                            break;
                        case R.id.adminViewRenatls:
                            Toast.makeText(AdminView.this, "clicked on view rentals", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.adminViewRequests:
                           Intent editInvQty = new Intent(AdminView.this, EditBookQty.class);
                            startActivity(editInvQty);
                            break;
                       case R.id.adminViewUsers:
                            Toast.makeText(AdminView.this, "clicked on view users", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }
}
