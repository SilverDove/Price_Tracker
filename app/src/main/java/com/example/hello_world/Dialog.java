package com.example.hello_world;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {//Function which display the content of the Error dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ERROR")
                .setMessage("Le lien est incorrect ou incompatible avec notre application. Il est aussi possible que ce nom soit déjà utilisé.")
                .setPositiveButton("Réessayez", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //If we click the button, the dialog page will close
                    }
                });
        return builder.create();
    }
}

