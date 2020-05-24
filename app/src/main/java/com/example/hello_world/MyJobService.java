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

    @Override
    public boolean onStartJob(JobParameters params) {//Call when execute our Job
        notif = new Notification(this);
        db = new DBHandler(this);

        doBackgroundWork(params);

        return true;//tells the system that it should keep the device awake so our service can execute its work
    }

    private void doBackgroundWork(final JobParameters params){//do background taks when our JobService starts
        new Thread(new Runnable() {//Runnable : what work we want to do
            @Override
            public void run() {//define what we want to do in background
                Information_Product infoProduct = new Information_Product();
                /*HERE, I'M JUST CHECK IF THE PRICE CHANGE FOR THE PRODUCTS AT THE SAME TIME*/
                if (products != null){
                    products.clear();
                }
                products=db.getAllProducts();

                for(Product current: products) {
                    //Checking every 15 minutes if the price changed
                    double last_price = current.getActual_price();
                    infoProduct.updatePrice(current, getApplicationContext());//Update product info
                    infoProduct.SendNotification(db.getProduct(current.getName()), notif, db.getProduct(current.getName()).getActual_price(),last_price);//Notify if the product price drops
                }
            }
        }).start();//start: Thread starts immediately
    }

    @Override
    public boolean onStopJob(JobParameters params) {//Call by the system when our Job has been cancelled
        jobCancelled = true;
        return false;//true: indicates that we want to reschedule our job
    }
}
