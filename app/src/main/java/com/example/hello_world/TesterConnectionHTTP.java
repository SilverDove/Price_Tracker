package com.example.hello_world;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TesterConnectionHTTP extends Activity {

    public static int urlValidator(String URL_String, Context context){//Verify if the URL link is valid
        HttpURLConnection conn = null;
        try{
            conn = (HttpURLConnection) new URL(
                    URL_String).openConnection();//returns  connection to the remote object referred to by the URL
            conn.connect();//opens a communications link to the resource referenced by the URL

            System.out.println("Connection Successful");

            if(Information_Product.getNameCompatibleWebsite(context, URL_String) != "") {
                return 1;//true
            }else{
                return -1;//displayError
            }

        }
        catch (IOException e){
            System.out.println("No Internet connection");
            return -1;
        }finally{
            conn.disconnect();
        }
    }

    public static boolean isNetworkAvailable() {
        // Vérifie si la connexion internet est active...
        URL url = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL("https://www.google.fr").openConnection();//returns  connection to the remote object referred to by the URL
            conn.connect();//opens a communications link to the resource referenced by the URL

            System.out.println("Connection Successful");
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }finally{
            conn.disconnect();
        }

        /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo().isConnectedOrConnecting();*/
    }

}
