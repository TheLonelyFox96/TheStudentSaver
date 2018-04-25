package com.example.thomasfox.thestudentsaver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.thomasfox.thestudentsaver.GeofenceNotification;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by thomasfox on 12/02/2018.
 */

public class Receiver extends BroadcastReceiver {
    private static final String TAG = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        int notificationGeofenceID = (int) System.currentTimeMillis();


        String notificationChannel = "NotificationChannel";




        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            String geofenceTransitionDetails = getGeofenceTransitionDetails(triggeringGeofences);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Android O requires a Notification Channel.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Channel_Name";
                // Create the channel for the notification
                NotificationChannel newChannel =
                        new NotificationChannel(notificationChannel, name, NotificationManager.IMPORTANCE_DEFAULT);

                // Set the Notification Channel for the Notification Manager.
                notificationManager.createNotificationChannel(newChannel);
            }

            // Create an explicit content Intent that starts the main Activity.
            Intent notificationIntent = new Intent(context, MainActivity.class);

            // Construct a task stack.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

            // Add the main Activity to the task stack as the parent.
            stackBuilder.addParentStack(MainActivity.class);

            // Push the content Intent onto the stack.
            stackBuilder.addNextIntent(notificationIntent);

            // Get a PendingIntent containing the entire back stack.
            PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notification = new Notification.Builder(context)
                            .setSmallIcon(R.drawable.studentsaver_statusicon)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.studentsaver))
                            .setContentTitle("Student Discount Nearby!")
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setContentText(geofenceTransitionDetails)
                            .setContentIntent(notificationPendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification.setChannelId(notificationChannel); // Channel ID
            }

            Notification bigNotification = new Notification.BigTextStyle(notification)
                    .bigText(geofenceTransitionDetails).build();

            notification.setAutoCancel(true);
            notificationManager.notify(notificationGeofenceID, bigNotification);
            Log.v(TAG, "NOTIFICATION SENT");

        }else {
                Log.v(TAG, "NOTIFICATION NOT SENT");
            }


    }

    /** Retrieval of the Geofence details from the Transition, and creation of a string for notification text **/
    private String getGeofenceTransitionDetails(List<Geofence> triggeringGeofences) {


        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());

        }

        return triggeringGeofencesIdsList.toString().replace("[", "").replace("]","");
    }
}