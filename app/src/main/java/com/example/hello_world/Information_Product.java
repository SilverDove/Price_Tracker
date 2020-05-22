package com.example.hello_world;


import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader; //https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;


import static android.content.Context.MODE_PRIVATE;

public class Information_Product {

    public static void Get_HTML(String URL_String, String Product_Name, Context context) throws Exception {
        //retrieve the web page
        System.out.println("IN THE GET8HTML METHOD");
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;
        FileOutputStream fos=null;
        try{

            conn = (HttpURLConnection) new URL(
                    URL_String).openConnection();//returns  connection to the remote object referred to by the URL
            conn.connect();//opens a communications link to the resource referenced by the URL
            System.out.println("connection opens");

            bis = new BufferedInputStream(conn.getInputStream()/*an input stream that reads from this open connection*/);//Read the response. If it has no body, that method returns an empty stream

            byte[] bytes = new byte[2040];//create an aray of bytes
            int tmp;
            fos = context.openFileOutput(Product_Name, MODE_PRIVATE);

            while( (tmp = bis.read(bytes) ) != -1 ) {//Until we are not at the end of the HTML page
                String chaine = new String(bytes,0,tmp);//convert the string into character
                fos.write(chaine.getBytes());//Write in a file
                //System.out.print(chaine); //Print the string
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            fos.close();
            bis.close();
            conn.disconnect();
        }
    }

    public static double getPricefromWebpage(String pathToFile, String before, String after, Context context){
        double real_price = 0.0; //price at the end

        try {
            FileInputStream fis =context.openFileInput(pathToFile);
            System.out.println("open file");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
             //opens a file from a path
            String ligne;
            //Initialization of variables
            int price_length = 0;
            int decimal_length = 0;
            double price = 0; //integer part of the price
            double decimal = 0; //decimal part of the price
            int point = 0; //flag
            int counter = 1;
            int i;
            while((ligne = reader.readLine()) != null){ //goes through the file
                if(ligne.contains(before)){ //if we detect the line with the price
                    //int beginning = ligne.indexOf("<meta itemprop=\"price\" content=\"");
                    int beginning = ligne.indexOf(before);
                    //int end = ligne.indexOf("\" /> <!-- Offre de remboursement");
                    //int end = ligne.indexOf("\" >");
                    int end = ligne.indexOf(after);
                    beginning = beginning + before.length();
                    //end = end +1;
                    //System.out.println(ligne + "\n" + beginning + "\n" + end);
                    for (i = beginning ; i < end ;i++) { //check if the price has a decimal part
                        if(ligne.charAt(i)=='.') point = 1; //it means that it has a decimal part
                    }
                    for (i = beginning ; i < end ;i++) { //loop to calculate the length of the integer part
                        if(ligne.charAt(i)!='.') {
                            //System.out.println(ligne.charAt(i));
                            price_length++;
                        }else { break;}
                    }
                    if(point == 1) { //if there is a decimal part
                        decimal_length =end- beginning - price_length - 1; //computes the length of the decimal part
                        //System.out.println(decimal_length);
                        for (i = beginning ; i < end - decimal_length - 1;i++) { //go again through the line and get the integer part of the price
                            int a=Character.getNumericValue(ligne.charAt(i)); //casts a char to an int
                            price = price + (a*(Math.pow(10, price_length-1)));//a becomes a double because of the product with a double
                            //System.out.println(price + "\ni = " + i);
                            price_length--;
                        }
                        for (int k = i+1; k < end ;k++) { //computes the decimal part with the same method
                            //System.out.println(ligne.charAt(k));
                            int b=Character.getNumericValue(ligne.charAt(k));
                            decimal = decimal + (b*(Math.pow(10, -(counter))));
                            //System.out.println(decimal);
                            counter++;
                        }
                    }else {//if it has no decimal part
                        for (i = beginning ; i < end ;i++) {
                            int a=Character.getNumericValue(ligne.charAt(i));
                            price = price + (a*(Math.pow(10, price_length-1)));
                            //System.out.println(price);
                            price_length--;
                        }
                    }
                    //System.out.println(ligne.charAt(i));

                }
            }
            real_price = price + decimal;//sums the two parts
            System.out.println("Price is " + real_price);//displays the price

        } catch (Exception ex){
            System.err.println("Error. "+ex.getMessage());
        }

        return real_price;

    }

    public double  updatePrice(Product produit, Context context){
        double last_price = -1.0;

        if (TesterConnectionHTTP.isNetworkAvailable() == false) {
            //Display a message to say that there is no internet connection
            Toast.makeText(context, "NO INTERNET CONNECTION JOBSERVICE", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("INTERNET CONNECTION");
            double actual_price = 0.0;

            try {
                //Information about the product
                String URL = produit.getLink();
                last_price = produit.getActual_price();

                //Retrieve price directly depending on the website
                Get_HTML(URL, produit.getName(), context);

                if (URL.indexOf("electrodepot",10)!=-1){ //If shop is electrodepot
                        actual_price= getPricefromWebpage(produit.getName(),"<meta itemprop=\"price\" content=\"", "\" /> <!-- Offre de remboursement -->", context);
                }

                if (URL.indexOf("assointeresiea",7)!=-1) { //If shop is assointer
                    System.out.println("It's assointer!");
                    actual_price = Information_Product.getPricefromWebpage(produit.getName(), "<meta property=\"og:description\" content=\"Price : ", "€\" />", context);
                }

                if (URL.indexOf("darty",10)!=-1) { //If shop is darty
                    System.out.println("It's darty!");
                    actual_price = Information_Product.getPricefromWebpage(produit.getName(), "<meta itemprop=\"price\" content=\"", "\" >", context);
                }

                if (URL.indexOf("grosbill",10)!=-1) { //If shop is grobill
                    System.out.println("It's grobill!");
                    actual_price = Information_Product.getPricefromWebpage(produit.getName(), "var product_price_tag = '", "';", context);
                }

                if (URL.indexOf("ikea",10)!=-1) { //If shop is ikea
                    System.out.println("It's ikea!");
                    actual_price = Information_Product.getPricefromWebpage(produit.getName(), "data-product-price=\"", "\" data-currency=\"EUR\"", context);
                }

                if (URL.indexOf("footlocker",10)!=-1) { //If shop is footlocker
                    System.out.println("It's footlocker!");
                    actual_price = Information_Product.getPricefromWebpage(produit.getName(), "<meta itemprop=\"price\" content=\"", "\"/><span>&euro;", context);
                }

                if (URL.indexOf("castorama",10)!=-1) { //If shop is castorama
                    System.out.println("It's castorama!");
                    actual_price = Information_Product.getPricefromWebpage(produit.getName(), "<span class=\"pricing\" data-currency=\"EUR\" data-locale=\"fr_FR\" data-value=\"", "\">", context);
                }

                if (URL.indexOf("decathlon",10)!=-1) { //If shop is decathlon
                    System.out.println("It's decathlon!");
                    actual_price = Information_Product.getPricefromWebpage(produit.getName(), "\"price\": \"", "\",", context);
                }

                //Get the date and time
                Locale localeFR = new Locale("FR", "fr");
                Calendar calendrier = Calendar.getInstance(localeFR);
                DateFormat format = DateFormat.getDateTimeInstance();
                format.setCalendar(calendrier);
                System.out.println(format.format(calendrier.getTime()));

                //Update product
                System.out.println("previous price was: "+produit.getActual_price()+" and now it's "+actual_price);
                produit.setActual_price(actual_price);
                produit.setDate_suivie(format.format(calendrier.getTime()));

                DBHandler db = new DBHandler(context);
                db.deleteProduct(produit);
                db.addProduct(produit);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return last_price;
    }

    public void SendNotification(Product produit, Notification notif, double actual_price, double last_price){
        //Send a notification if the price drops
        if (actual_price < last_price) {
            if(produit.getNotif_Under() == false){
                notif.sendNotification(produit);//Notification
            }else{
                if (actual_price < produit.getPrice_Notif() ){
                    notif.sendNotification(produit);//Notification
                }
            }
        }
    }
}
