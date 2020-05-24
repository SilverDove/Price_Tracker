package com.example.hello_world;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class ItemDetails extends AppCompatActivity {
    private DBHandler db;
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_item);
        getSupportActionBar().setTitle("Details du produit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int position = intent.getIntExtra(ListesFragment.EXTRA_POSITION,0);

        db = new DBHandler(this);
        List<Product> listproduct = db.getAllProducts();
        p = listproduct.get(position);
        System.out.println("Option: "+p.getNotif_Under()+" under "+p.getPrice_Notif());
        displayInfo();

        Button buttonNotificationUnder = findViewById(R.id.AppliquerButton);
        onButtonClickNotificationUnder(buttonNotificationUnder);

    }

    public void onButtonClickNotificationUnder(Button button) {//Interaction with the button and what the user wrote
        //button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appliquer prix de Notification
                EditText priceUnder = (EditText) findViewById(R.id.PrixNotification);
                db.updateNotificationUnder(p, true);
                db.updatePriceUnder(p, Double.valueOf(String.valueOf(priceUnder.getText())));
                TextView display = (TextView) findViewById(R.id.detailsNotification);
                display.setText("L'option est activée pour "+String.valueOf(db.getProduct(p.getName()).getPrice_Notif())+ "€");
            }
        });
    }

    public void onSwitchClicked(View view) {
        // Is the view now checked?
        System.out.println("HELLO");

        Switch simpleSwitch = (Switch) findViewById(R.id.Switch);
        boolean switchState = simpleSwitch.isChecked();

        EditText priceUnder = (EditText) findViewById(R.id.PrixNotification);
        EditText EUR = findViewById(R.id.EUR);
        Button buttonNotificationUnder = findViewById(R.id.AppliquerButton);
        System.out.println("Here again");

        // Check which switch was clicked
        switch(view.getId()) {
            case R.id.Switch:
                if (switchState) {
                    System.out.println("YES!");
                    priceUnder.setVisibility(View.VISIBLE);
                    EUR.setVisibility(View.VISIBLE);
                    buttonNotificationUnder.setVisibility(View.VISIBLE);
                    TextView display = (TextView) findViewById(R.id.detailsNotification);
                    display.setText("L'option est activée pour "+String.valueOf(p.getPrice_Notif())+ "€");
                }else{
                    System.out.println("So bad ...");
                    priceUnder.setVisibility(View.INVISIBLE);
                    EUR.setVisibility(View.INVISIBLE);
                    buttonNotificationUnder.setVisibility(View.INVISIBLE);
                    db.updateNotificationUnder(p, false);
                    db.updatePriceUnder(p, 0.0);
                    TextView display = (TextView) findViewById(R.id.detailsNotification);
                    display.setText("L'option n'est pas activée");
                }
                break;
        }
    }

    public void displayInfo(){
        TextView nameOfProduct = (TextView) findViewById(R.id.ProductName);
        nameOfProduct.setText(p.getName());

        TextView dateOfProduct = (TextView) findViewById(R.id.date);
        dateOfProduct.setText(p.getDate_suivie());

        TextView firstPriceOfProduct = (TextView) findViewById(R.id.initialprice);
        firstPriceOfProduct.setText(String.valueOf(p.getInitial_price()));

        TextView actualPriceOfProduct = (TextView) findViewById(R.id.actualprice);
        actualPriceOfProduct.setText(String.valueOf(p.getActual_price()));

        TextView linkOfProduct = (TextView) findViewById(R.id.link);
        linkOfProduct.setText(p.getLink());

        TextView display = (TextView) findViewById(R.id.detailsNotification);

        Switch simpleSwitch = (Switch) findViewById(R.id.Switch);

        TextView firstDateOfProduct = (TextView) findViewById(R.id.firstdate);
        firstDateOfProduct.setText(p.getFirst_Date());

        EditText prixNotif = findViewById(R.id.PrixNotification);
        EditText EUR = findViewById(R.id.EUR);
        Button buttonNotificationUnder = findViewById(R.id.AppliquerButton);


        if(p.getNotif_Under()==true){
            display.setText("L'option est activée pour "+String.valueOf(p.getPrice_Notif())+ "€");
            prixNotif.setVisibility(View.VISIBLE);
            EUR.setVisibility(View.VISIBLE);
            buttonNotificationUnder.setVisibility(View.VISIBLE);
            simpleSwitch.setChecked(true);
        }else{
            display.setText("L'option est désactivée");
            prixNotif.setVisibility(View.INVISIBLE);
            EUR.setVisibility(View.INVISIBLE);
            buttonNotificationUnder.setVisibility(View.INVISIBLE);
            simpleSwitch.setChecked(false);
        }
    }



}
