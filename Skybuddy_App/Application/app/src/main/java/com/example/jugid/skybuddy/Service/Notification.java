package com.example.jugid.skybuddy.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Notification extends Service {
    private static Timer timer;
    private String CHANNEL_ID = "1";

    private NotificationManager notificationManager;
    private NotificationChannel channel;

    private NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("SkyBuddy")
            .setContentText("Des nouveaux messages attendent !")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        timer.scheduleAtFixedRate(new Notification.taskActualizer(),0,600000);
        Toast.makeText(getApplicationContext(), "Demarrage du service", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private class taskActualizer extends TimerTask {
        @Override
        public void run() {
            VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    if(response.equals("1")){
                        notificationManager.notify(1, mBuilder.build());
                    }
                }
            };
            try{
                Date dateTime = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                Thibaut.lastActualisationDateMessage = dateFormat.format(dateTime);
                Chloe.getMessageUpdate(getApplicationContext(),Thibaut.lastActualisationDateMessage,callback);
                //actualiser la demande de message
            }
            catch(Exception e){
                Log.e("Service notification",e.getMessage());
            }
        }
    }
}
