package com.example.android.mylibraryapp.ControlObjects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.mylibraryapp.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Prevents the back button from being used after the user registers am account
    @Override
    public void onBackPressed()
    {}
}

