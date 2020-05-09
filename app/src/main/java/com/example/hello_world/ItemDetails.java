package com.example.hello_world;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemDetails extends AppCompatActivity {
    private DBHandler db;
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_item);
        /*getSupportActionBar().setTitle("Details du produit");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        Intent intent = getIntent();
        int position = intent.getIntExtra(ListesFragment.EXTRA_POSITION,0);

        db = new DBHandler(this);
        List<Product> listproduct = db.getAllProducts();
        p = listproduct.get(position);
        Toast.makeText(this, "INSIDE ItemDetails, for "+p.getName()+" the price is "+p.getActual_price(), Toast.LENGTH_SHORT).show();
        displayInfo();

    }

    //Determines if Action bar items are selected. If true then do corresponding actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle presses on the action bar items
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        System.out.println("HELLO");
        boolean checked = ((CheckBox) view).isChecked();
        EditText priceUnder = (EditText) findViewById(R.id.editText);
        System.out.println("Here again");
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                if (checked && !(String.valueOf(priceUnder.getText()).equals(""))) {
                    System.out.println("YES!");
                    p.setNotif_Under(true);
                    p.setPrice_Notif(Double.valueOf(String.valueOf(priceUnder.getText())));
                    TextView display = (TextView) findViewById(R.id.test);
                    display.setText("L'option est activée pour "+String.valueOf(p.getPrice_Notif())+ "€");
                }else{
                    System.out.println("So bad ...");
                    p.setNotif_Under(false);
                    p.setPrice_Notif(0);
                    TextView display = (TextView) findViewById(R.id.test);
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
        TextView display = (TextView) findViewById(R.id.test);
        CheckBox check = (CheckBox) findViewById(R.id.checkBox);
        if(p.getNotif_Under()==true){
            display.setText("L'option est activée pour "+String.valueOf(p.getPrice_Notif())+ "€");
            check.setChecked(true);
        }else{
            display.setText("L'option n'est pas activée");
            check.setChecked(false);
        }
    }



}
