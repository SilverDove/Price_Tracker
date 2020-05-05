package com.example.hello_world;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectivityReceiver extends BroadcastReceiver {

    public ConnectivityReceiver() {
        super();
    }

    public void onReceive(final Context context, final Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        /*boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();*/

        if (null != activeNetwork) {
            Toast.makeText(context, "CONNECTED TO INTERNET", Toast.LENGTH_LONG).show();
            System.out.println("CONNECTED TO INTERNET");
        }else{
            Toast.makeText(context, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
            System.out.println("NO INTERNET CONNECTION");
        }

        /*if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }*/

    }
}
