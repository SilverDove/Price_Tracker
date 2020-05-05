package com.example.hello_world;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Notification extends Application {
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    public static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;
    private Context context;

    public Notification(Context context){
        this.context=context;
        createNotificationChannel();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Price Tracker Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(Product test){//WE NEED TO BE ABLE TO PUT THESE TWO FUNCTIONS IN NOTIFICATION CLASS
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle("UPDATE!")
                .setContentText("Le prix de " + test.getName()+ " est maintenant de "+ Double.toString(test.getActual_price()) + "€.")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        return notifyBuilder;
    }

    public void sendNotification(Product test) {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(test);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

}
