package com.example.hello_world;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PopUpClass {

    private DBHandler db;

    //PopupWindow display method
    public void showPopupWindow(final View view, Context context) {
        db = new DBHandler(context);

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.pop_up_window, null);
        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;
        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler
        TextView test2 = popupView.findViewById(R.id.descriptionText);
        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        EditText editText = popupView.findViewById(R.id.frequency);
        TextView textView = popupView.findViewById(R.id.minutes);
        TextView currentfrequency = popupView.findViewById(R.id.CurrentNotification);

        List<Product> mlistProduct = db.getAllProducts();

        if(mlistProduct.size() == 0){
            currentfrequency.setText("Fréquence actuelle: 15 minutes");
            test2.setText("Tu ne peux pas changer la fréquence de notification tant que tu n'as pas de produit dans ta liste");
            textView.setVisibility(View.INVISIBLE);
            buttonEdit.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
        }else{
            Product mproduct = mlistProduct.get(0);
            test2.setText("A quelle fréquence veux-tu que l'application te notifie en cas de baisse de prix?\nCe nombre doit être supérieur ou égale à 15 minutes.");
            currentfrequency.setText("Fréquence actuelle: "+String.valueOf(mproduct.getTime_Update())+" minutes");
            textView.setText("minutes");
            textView.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = popupView.findViewById(R.id.frequency);
                int frequencyNotification = Integer.parseInt(editText.getText().toString());

                if(frequencyNotification<15) {//If the number if invalid
                    Toast.makeText(view.getContext(), "Nombre invalide", Toast.LENGTH_LONG).show();
                }else{
                    //Change the parameters
                    List<Product> productList = db.getAllProducts();
                    db.getAllProducts();
                    for (int i=0; i<productList.size(); i++){
                        db.updateNotificationFrequency(productList.get(i), frequencyNotification);
                    }
                    popupWindow.dismiss();//close the popUp window
                }
            }
        });

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
