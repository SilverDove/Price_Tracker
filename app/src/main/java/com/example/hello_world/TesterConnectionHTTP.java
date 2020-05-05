package com.example.hello_world;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TesterConnectionHTTP extends Activity {

    public static int urlValidator(String URL_String){//Verify if the URL link is valid

        try{
            URL url = new URL(URL_String);
            URLConnection connection = url.openConnection();
            connection.connect();

            System.out.println("Connection Successful");

            //int pos = URL_String.indexOf("electrodepot",10);//shop compatible with the app
            if (URL_String.indexOf("electrodepot",10)==-1 && URL_String.indexOf("assointeresiea",8)==-1){//If the URL is not compatible with the app
                return -1;
            }else {//Otherwise
                if(URL_String.indexOf("electrodepot",10)!=-1) {
                    return 1;//electrodepot
                }else{
                    return 0;//assoInter
                }
            }
        }
        catch (IOException e){
            System.out.println("Internet Not Connected");
            return -1;
        }
    }

    public static boolean isNetworkAvailable() {
        // Vérifie si la connexion internet est active...

        URL url = null;
        try {
            url = new URL("https://www.google.fr");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo().isConnectedOrConnecting();*/
    }

}
