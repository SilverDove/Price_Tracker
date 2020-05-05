package com.example.hello_world;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Aide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);
        getSupportActionBar().setTitle("Aide");
    }
}
