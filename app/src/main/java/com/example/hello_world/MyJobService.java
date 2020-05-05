package com.example.hello_world;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    private List<Product> products;
    private Notification notif ;
    private DBHandler db;
    private Product test;
    private Context context = this;

    @Override
    public boolean onStartJob(JobParameters params) {//Call when execute our Job
        //Toast.makeText(this, "START JOB", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "START JOB");
        notif = new Notification(this);
        db = new DBHandler(this);
        doBackgroundWork(params);
        return true;//tells the system that it should keep the device awake so our service can execute its work
    }

    private void doBackgroundWork(final JobParameters params){//do background taks when our JobService starts
        new Thread(new Runnable() {//Runnable : what work we want to do
            @Override
            public void run() {//define what we want to do in background

                /*test = db.getProduct("ASSO");
                notif.sendNotification(test);*/

                /*HERE, I'M JUST CHECK IF THE PPRICE CHANGE FOR THE PRODUCTS AT THE SAME TIME*/
                products=db.getAllProducts();

                for(Product current: products) {
                    //Checking every 15 minutes if the price changed
                        Compare_Price(current);
                }


                /*for (int i=0; i<10 ; i++){
                    Log.d(TAG, "run: "+i);

                    if(jobCancelled){//if true
                        return;//don't execute our work
                    }

                    try {
                        Thread.sleep(1000);//sleep our Thread
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    jobFinished(params, false);//false: no reschedule our work when errors/failure
                }*/
            }
        }).start();//start: Thread starts immediately
    }

    public void Compare_Price(Product produit) {

        if (TesterConnectionHTTP.isNetworkAvailable() == false) {
            //Display a message to say that there is no internet connection
            //Toast.makeText(this, "NO INTERNET CONNECTION JOBSERVICE", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("INTERNET CONNECTION");
            double actual_price, last_price;

            try {
                String URL = produit.getLink();
                last_price = produit.getActual_price();
                Information_Product.Get_HTML(URL, produit.getName(), context);
                if(produit.getLink().indexOf("electrodepot",10)!=-1){
                    actual_price = Information_Product.Find_price_ElectroDepot(produit.getName(), context);
                }else{
                    actual_price = Information_Product.Find_price_AssoInter(produit.getName(), context);
                }

                //Get the date and time
                Locale localeFR = new Locale("FR", "fr");
                Calendar calendrier = Calendar.getInstance(localeFR);
                DateFormat format = DateFormat.getDateTimeInstance();
                format.setCalendar(calendrier);
                System.out.println(format.format(calendrier.getTime()));

                produit.setActual_price(actual_price);
                produit.setDate_suivie(format.format(calendrier.getTime()));

                db = new DBHandler(this);
                db.deleteProduct(produit);
                db.addProduct(produit);

                if (actual_price < last_price) {
                    //Notification
                    notif.sendNotification(produit);
                    System.out.println("INSIDE ItemDetails, for "+produit.getName()+" the price is "+produit.getActual_price());
                    System.out.println("SMALLER ! NEED TO NOTIFY THE USER");
                } else {
                    System.out.println("NOT SMALLER");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {//Call by the system when our Job has been cancelled
        //Toast.makeText(this, "JOB CANCELLED BEFORE COMPLETION", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "JOB CANCELLED BEFORE COMPLETION");
        jobCancelled = true;
        return false;//true: indicates that we want to reschedule our job
    }
}
