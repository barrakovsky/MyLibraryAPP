package com.example.android.mylibraryapp.ControlObjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mylibraryapp.EntityObjects.Request;
import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BaseActivity extends AppCompatActivity {

    public androidx.appcompat.widget.Toolbar toolbar;


    ActionBarDrawerToggle mDrawerToggle;
    Context context;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;

    protected boolean isAdmin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    protected boolean useToolbar() {
        return true;
    }


    @Override
    public void setContentView(int layoutResID) {
        context = this;

        if(isAdmin){
            DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer_main_admin, null);
            FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.frame);
            getLayoutInflater().inflate(layoutResID, activityContainer, true);

            super.setContentView(fullView);
            toolbar = (Toolbar) fullView.findViewById(R.id.tool_bar);
            navigationView = (NavigationView) findViewById(R.id.navigation_view_admin);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    if (menuItem.isChecked()) menuItem.setChecked(false);
                    else menuItem.setChecked(true);

                    drawerLayout.closeDrawers();

                    switch (menuItem.getItemId()) {

                        case R.id.home:
                            Intent home = new Intent(BaseActivity.this, MainActivity.class);
                            startActivity(home);
                            break;

                        case R.id.editProfile:
                            Intent editProfile = new Intent(BaseActivity.this, EditProfileActivity.class);
                            editProfile.putExtra("isAdmin", true);
                            startActivity(editProfile);
                            break;

                        case R.id.myRentals:
                            Intent rental = new Intent(BaseActivity.this, RentalActivity.class);
                            rental.putExtra("isAdmin", true);
                            startActivity(rental);
                            break;

                        case R.id.bookSearch:
                            Intent searchBook = new Intent(BaseActivity.this, SearchResultsActivity.class);
                            searchBook.putExtra("isAdmin", true);
                            startActivity(searchBook);
                            finish();
                            break;

                        case R.id.bookRequest:
                            Intent bookRequest = new Intent(BaseActivity.this, RequestBookActivity.class);
                            bookRequest.putExtra("isAdmin", true);
                            startActivity(bookRequest);
                            finish();
                            break;

                        case R.id.myFavorites:
                            Intent favorites = new Intent(BaseActivity.this, ViewFavoritesActivity.class);
                            favorites.putExtra("isAdmin", true);
                            startActivity(favorites);
                            finish();
                            break;

                        case R.id.adminAcess:
                            Intent adminView = new Intent(BaseActivity.this, AdminView.class);
                            adminView.putExtra("isAdmin", true);
                            startActivity(adminView);
                            finish();
                            break;

                        case R.id.logout:
                            firebaseAuth.getInstance().signOut();
                            Intent logout = new Intent(BaseActivity.this, LoginActivity.class);
                            startActivity(logout);
                            finish();
                            break;


                        default:

                            return true;
                    }
                    return true;
                }
            });

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutAdmin);




        }else {

            DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer_main, null);
            FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.frame);
            getLayoutInflater().inflate(layoutResID, activityContainer, true);

            super.setContentView(fullView);
            toolbar = (Toolbar) fullView.findViewById(R.id.tool_bar);

            navigationView = (NavigationView) findViewById(R.id.navigation_view);


            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    if (menuItem.isChecked()) menuItem.setChecked(false);
                    else menuItem.setChecked(true);

                    drawerLayout.closeDrawers();

                    switch (menuItem.getItemId()) {

                        case R.id.home:
                            Intent home = new Intent(BaseActivity.this, MainActivity.class);
                            startActivity(home);
                            break;

                        case R.id.editProfile:
                            Intent editProfile = new Intent(BaseActivity.this, EditProfileActivity.class);
                            startActivity(editProfile);
                            break;

                        case R.id.myRentals:
                            Intent rental = new Intent(BaseActivity.this, RentalActivity.class);
                            startActivity(rental);
                            break;

                        case R.id.bookSearch:
                            Intent searchBook = new Intent(BaseActivity.this, SearchResultsActivity.class);
                            startActivity(searchBook);
                            finish();
                            break;

                        case R.id.bookRequest:
                            Intent bookRequest = new Intent(BaseActivity.this, RequestBookActivity.class);
                            startActivity(bookRequest);
                            finish();
                            break;

                        case R.id.myFavorites:
                            Intent favorites = new Intent(BaseActivity.this, ViewFavoritesActivity.class);
                            startActivity(favorites);
                            finish();
                            break;

                        case R.id.logout:
                            firebaseAuth.getInstance().signOut();
                            Intent logout = new Intent(BaseActivity.this, LoginActivity.class);
                            startActivity(logout);
                            finish();
                            break;


                        default:

                            return true;
                    }
                    return true;
                }
            });

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutUser);


        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return mDrawerToggle.onOptionsItemSelected(item);
    }


}

