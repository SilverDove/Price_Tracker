package com.example.hello_world;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {
    private Button button;
    private Button button_Aide;
    private EditText mName;
    private EditText mURL_Link;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

        mURL_Link = v.findViewById(R.id.URL_Link);
        mName= v.findViewById(R.id.Name_Product);
        button = (Button) v.findViewById(R.id.Button);
        button_Aide =(Button)v.findViewById(R.id.Aide_Button);

        onButtonClickListener(button);
        onButton_AideClickListener(button_Aide);

        return v;
    }

    public void onButton_AideClickListener(Button button_Aide){//Interaction with the button to access to the Aide page
        button_Aide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Display_Aide(v);
            }
        });
    }

    public void onButtonClickListener(Button button) {//Interaction with the button and what the user wrote
        //button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSecondactivity(v);
            }
        });
    }

    public void launchSecondactivity(View view){//Deal with the adding of a URL product
        String URL = mURL_Link.getText().toString();//Take what the user wrote for the URL
        String Name = mName.getText().toString();//Take what the user wrote for the name of the product
        int answer=-1;
        double Price_product=-1.0;

        answer= TesterConnectionHTTP.urlValidator(URL);//A VERIFIER (CONNECTION INTERNET)
        DBHandler db = new DBHandler(getContext());
        //db.AllDelete();

        if (answer==-1){
            Display_Error();
        }else try {
            Information_Product.Get_HTML(URL, Name, getContext());
            if (URL.indexOf("electrodepot",10)!=-1) { //If shop is electrodepot
                System.out.println("It's electrodepot!");
                Price_product = Information_Product.getPricefromWebpage(Name, "<meta itemprop=\"price\" content=\"", "\" /> <!-- Offre de remboursement -->", getContext());
            }

            if (URL.indexOf("assointeresiea",7)!=-1) { //If shop is assointer
                System.out.println("It's assointer!");
                Price_product = Information_Product.getPricefromWebpage(Name, "<meta property=\"og:description\" content=\"Price : ", "€\" />", getContext());
            }

            if (URL.indexOf("grosbill",10)!=-1) { //If shop is grobill
                System.out.println("It's grobill!");
               Price_product = Information_Product.getPricefromWebpage(Name, "var product_price_tag = '", "';", getContext());
            }

            /*GET THE DATE AND TIME*/
            Locale localeFR = new Locale("FR","fr");
            Calendar calendrier = Calendar.getInstance(localeFR );
            DateFormat format = DateFormat.getDateTimeInstance();
            format.setCalendar(calendrier);
            System.out.println(format.format(calendrier.getTime()));

            Product URL_Product = new Product(Name, URL, Price_product, format.format(calendrier.getTime()));
            db.addProduct(URL_Product);

            // Reading all products
            Log.d("Reading: ", "Reading all shops..");
            List<Product> products = db.getAllProducts();

            for (Product product : products) {
                String log = "Name:" + product.getName() + " ,Link: " + product.getLink() + " ,Prix initial: " + product.getInitial_price() + " ,Date suivie: " + product.getDate_suivie() + " ,Actual price: " + product.getActual_price();
                // Writing products to log
                Log.d("Product: : ", log);
            }

            Fragment selected_Fragment=null;
            selected_Fragment = new ListesFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_Fragment).commit();

        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    private void Display_Error(){//Display the Error dialog
        Dialog dialog = new Dialog();
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    public void Display_Aide(View view) {
        Intent intent = new Intent(getActivity(), Aide.class);
        startActivity(intent);

    }

}
