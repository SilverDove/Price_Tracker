package com.example.hello_world;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader; //https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import static android.content.Context.MODE_PRIVATE;

public class Information_Product {

    private static final String LISTE_ENSEIGNES = "ListEnseignes.txt";

    public static void deleteFileInterernalStorage (Context context, String fileToPath){
            File folder = context.getFilesDir();
            File file = new File(folder, fileToPath);
            file.delete();
    }

    public static boolean checkUniqueFile (Context context, String fileToPath){
        DBHandler db  = new DBHandler(context);
        File folder = context.getFilesDir();
        List<Product> listProduct = db.getAllProducts();
        boolean flag = true;

        for (Product product : listProduct){
            if(fileToPath.equals(product.getName()) == true){
                return false;
            }
        }
        return flag;
    }

    public static void Get_HTML(String URL_String, String Product_Name, Context context) throws Exception {
        //retrieve the web page
        System.out.println("IN THE GET_HTML METHOD");
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

    public static void updateCompatibleWebsite(Context context){
            String listeEnseignes = "electrodepot\n"+"assointeresiea\n"+"darty\n"+"grosbill\n"+"ikea\n"+"footlocker\n"+"castorama\n"+"decathlon";

            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(LISTE_ENSEIGNES, Context.MODE_PRIVATE));
                outputStreamWriter.write(listeEnseignes);
                outputStreamWriter.close();

                System.out.println("List of all website in the file is "+getListCompatibleWebsite(context));

            }catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
    }

    public static String getListCompatibleWebsite(Context context){

        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(LISTE_ENSEIGNES);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    public static String getNameCompatibleWebsite(Context context, String pathToWebsite){

        updateCompatibleWebsite(context);//create file with all the shops inside

        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(LISTE_ENSEIGNES);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null) {
                    if(pathToWebsite.indexOf(receiveString,8)!=-1){//If the website is compatible
                        stringBuilder.append(receiveString);
                        break;
                    }
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        System.out.println("The website is from "+ret);

        return ret;
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

    public static double ChooseWebsite(String URL, String Name, Context context){
        Double actual_price = 0.0;

        String nameWebsite = getNameCompatibleWebsite(context, URL);

        System.out.println("In the function ChooseWebsite, the name is "+ nameWebsite);

        switch (nameWebsite){
            case "electrodepot":
                actual_price= getPricefromWebpage(Name,"<meta itemprop=\"price\" content=\"", "\" /> <!-- Offre de remboursement -->", context);
                break;

            case "assointeresiea":
                actual_price = Information_Product.getPricefromWebpage(Name, "<meta property=\"og:description\" content=\"Price : ", "€\" />", context);
                break;

            case "darty":
                actual_price = Information_Product.getPricefromWebpage(Name, "<meta itemprop=\"price\" content=\"", "\" >\n" +
                        "<meta itemprop=\"priceCurrency\" content=\"EUR\" >", context);
                break;

            case "grosbill":
                actual_price = Information_Product.getPricefromWebpage(Name, "var product_price_tag = '", "';", context);
                break;

            case "ikea":
                System.out.println("Hello");
                actual_price = Information_Product.getPricefromWebpage(Name, "data-product-price=\"", "\" data-currency=\"EUR\"", context);
                break;

            case "footlocker":
                actual_price = Information_Product.getPricefromWebpage(Name, "<meta itemprop=\"price\" content=\"", "\"/><span>&euro;", context);
                break;

            case "castorama":
                actual_price = Information_Product.getPricefromWebpage(Name, "<meta itemprop=\"price\"\n" +
                        "            content=\"", "\" />", context);
                break;

            case "decathlon":
                actual_price = Information_Product.getPricefromWebpage(Name, "<meta property=\"product:original_price:amount\" content=\"", "\"/>", context);
                break;
        }

        return actual_price;

    }

    public void updatePrice(Product produit, Context context){

        if (TesterConnectionHTTP.isNetworkAvailable()==false) {
            //Display a message to say that there is no internet connection
            Toast.makeText(context, "NO INTERNET CONNECTION JOBSERVICE", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("INTERNET CONNECTION");
            double actual_price = 0.0;

            try {
                //Information about the product
                String URL = produit.getLink();
                //Retrieve price directly depending on the website
                Get_HTML(URL, produit.getName(), context);//Get the HTML code from the webpage
                actual_price = ChooseWebsite(URL, produit.getName(),context);//Return the price of the corresponding website

                //Get the date and time
                Locale localeFR = new Locale("FR", "fr");
                Calendar calendrier = Calendar.getInstance(localeFR);
                DateFormat format = DateFormat.getDateTimeInstance();
                format.setCalendar(calendrier);
                System.out.println(format.format(calendrier.getTime()));

                //Update product
                DBHandler db = new DBHandler(context);
                System.out.println("previous price was: "+produit.getActual_price()+" and now it's "+actual_price);
                db.updateActualPrice(produit, actual_price);
                db.updateDateSuivie(produit, format.format(calendrier.getTime()));
                System.out.println("Date suivie is :"+db.getProduct(produit.getName()).getDate_suivie());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SendNotification(Product produit, Notification notif, double actual_price, double last_price){
        //Send a notification if the price drops
        System.out.println("In the fonction SendNotification !! We have actual price = "+actual_price+" and last_price = "+last_price);
        if (actual_price < last_price) {
            System.out.println("actualPrice < lastPrice");
            if(produit.getNotif_Under() == false){
                System.out.println("No notification under a specific price");
                notif.sendNotification(produit);//Notification
            }else{
                System.out.println("Notification under a specific price");
                if (actual_price < produit.getPrice_Notif() ){
                    System.out.println("actualPrice < priceNotif");
                    notif.sendNotification(produit);//Notification
                }
            }
        }
    }
}
