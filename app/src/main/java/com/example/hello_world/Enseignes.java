package com.example.hello_world;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

public class Enseignes extends AppCompatActivity {

    Button button;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseignes);

        /*Change the display of the Toolbar*/
        getSupportActionBar().setTitle("Enseignes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
