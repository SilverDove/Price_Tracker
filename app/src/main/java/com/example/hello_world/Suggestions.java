package com.example.hello_world;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Suggestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        getSupportActionBar().setTitle("Suggestions");

        Button button_send =findViewById(R.id.suggestion_button);

        onButtonClickListener(button_send);

    }

    public void onButtonClickListener(Button b){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSuggestion(v);
            }
        });
    }

    public void sendSuggestion(View v){
        String TO = "tricot@et.esiea.fr";//Price Tracker email address
        EditText editTExt = findViewById(R.id.suggestion_text);
        String message = editTExt.getText().toString();
        Intent email = new Intent(Intent.ACTION_SEND);

        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(android.content.Intent.EXTRA_EMAIL,TO);
        email.putExtra(Intent.EXTRA_SUBJECT, "Nouvelle Suggestion pour Price Tracker");
        email.putExtra(Intent.EXTRA_TEXT, message);

        try{
            startActivity(Intent.createChooser(email, "Envoie à Price Tracker Company :)"));
        }catch (android.content.ActivityNotFoundException ex) {
            ex.getMessage();
        }
    }


}
