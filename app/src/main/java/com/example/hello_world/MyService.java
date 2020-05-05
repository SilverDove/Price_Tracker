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
        //Log.d(TAG, "SERVICE CREATED");
        db = new DBHandler(this);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "onStartCommand");


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
        //Toast.makeText(this, "SERVICE DESTROYED", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "SERVICE DESTROYED");
        cancelJob();
        super.onDestroy();
    }

    public void scheduleJob(int frequency_compare) {
        if(TesterConnectionHTTP.isNetworkAvailable() == true){
            //Toast.makeText(this, "SCHEDULE JOB", Toast.LENGTH_SHORT).show();
            //Log.d(TAG, "SCHEDULE JOB");
            ComponentName componentName = new ComponentName(this, MyJobService.class);
            JobInfo info = new JobInfo.Builder(123, componentName)//settings to start our JobService
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)//I WANT TO THAT THE JOB IS EXECUTED WHEN THERE IS ANY INTERNET CONNEXION (MUST STOP WHEN DON4T HAVE EVENT DURING THE PROCESS BUT DOESNT WORK)
                    .setPersisted(true)//Keep our job alive even if we reboot the device
                    .setPeriodic(frequency_compare)//How often we want to execute the job (only a period because save battery and memory)[minimum every 15minutes]
                    .build();

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int resultCode = scheduler.schedule(info);
            if (resultCode == JobScheduler.RESULT_SUCCESS) {//Check if everything went successful
                //Log.d(TAG, "Job scheduled");
            }else{
                //Log.d(TAG, "Job scheduling failed");
            }
        }
    }

    public void cancelJob(){
        //Toast.makeText(this, "CANCEL JOB", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "CANCEL JOB");
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        //Log.d(TAG, "Job cancelled");

    }
}



