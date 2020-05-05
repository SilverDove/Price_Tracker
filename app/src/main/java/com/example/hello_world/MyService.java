package com.example.hello_world;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MyService extends Service {
    private List<Product> products;
    private DBHandler db;
    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "SERVICE CREATED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SERVICE CREATED");
        db = new DBHandler(this);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStartCommand");


        scheduleJob(900000);//A MODIFIER EN FONCTION DU PRODUIT

        /*test = db.getProduct("ASSO");
        notif.sendNotification(test);*/

        /*products=db.getAllProducts();

        for(Product current: products){
            //Checking every X seconds if the price changed
            try{
                Thread.sleep(60000);//Wait current.getTime_Update() seconds

                notif.sendNotification(current);
                //Compare_Price(current, this);
            }catch(InterruptedException ex){
                android.util.Log.d("Price Tracker", ex.toString());
            }
        }*/

        return START_STICKY;//TO CHECK
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "SERVICE DESTROYED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SERVICE DESTROYED");
        cancelJob();
        super.onDestroy();
    }

    public void scheduleJob(int frequency_compare) {
        if(TesterConnectionHTTP.isNetworkAvailable() == true){
            Toast.makeText(this, "SCHEDULE JOB", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "SCHEDULE JOB");
            ComponentName componentName = new ComponentName(this, MyJobService.class);
            JobInfo info = new JobInfo.Builder(123, componentName)//settings to start our JobService
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)//I WANT TO THAT THE JOB IS EXECUTED WHEN THERE IS ANY INTERNET CONNEXION (MUST STOP WHEN DON4T HAVE EVENT DURING THE PROCESS BUT DOESNT WORK)
                    .setPersisted(true)//Keep our job alive even if we reboot the device
                    .setPeriodic(frequency_compare)//How often we want to execute the job (only a period because save battery and memory)[minimum every 15minutes]
                    .build();

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int resultCode = scheduler.schedule(info);
            if (resultCode == JobScheduler.RESULT_SUCCESS) {//Check if everything went successful
                Log.d(TAG, "Job scheduled");
            }else{
                Log.d(TAG, "Job scheduling failed");
            }
        }
    }

    public void cancelJob(){
        Toast.makeText(this, "CANCEL JOB", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "CANCEL JOB");
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");

    }

    /*public void Compare_Price(Product produit, Context context) {//NOT SURE ABOUT THIS CONTEXT

        if (TesterConnectionHTTP.isNetworkAvailable() == false) {
            //Display a message to say that there is no internet connection
            System.out.println("NO INTERNET CONNECTION");
        } else {
            System.out.println("INTERNET CONNECTION");
            double actual_price, last_price;

            try {
                String URL = produit.getLink();
                last_price = produit.getActual_price();
                Information_Product.Get_HTML(URL, produit.getName(), context);
                actual_price = Information_Product.Find_price_ElectroDepot(produit.getName(), context);//A MODIFIER EN FONCTION DU SITE

                //Get the date and time
                Locale localeFR = new Locale("FR", "fr");
                Calendar calendrier = Calendar.getInstance(localeFR);
                DateFormat format = DateFormat.getDateTimeInstance();
                format.setCalendar(calendrier);
                System.out.println(format.format(calendrier.getTime()));

                produit.setActual_price(actual_price);
                produit.setDate_suivie(format.format(calendrier.getTime()));

                if (actual_price < last_price) {
                    //Notification
                    notif.sendNotification(produit);
                    System.out.println("SMALLER ! NEED TO NOTIFY THE USER");
                } else {
                    System.out.println("NOT SMALLER");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

private NotificationCompat.Builder getNotificationBuilder(){//WE NEED TO BE ABLE TO PUT THESE TWO FUNCTIONS IN NOTIFICATION CLASS
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext(), PRIMARY_CHANNEL_ID)
                .setContentTitle("UPDATE!")
                .setContentText("IT'S WORKING")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        return notifyBuilder;
    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }*/


}



