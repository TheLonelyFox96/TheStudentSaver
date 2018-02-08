package com.example.thomasfox.thestudentsaver;

import android.app.IntentService;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasfox on 21/01/2018.
 */

public class GeofenceNotification extends BroadcastReceiver{


    public static final String TAG = "GeofenceService";



    public void onReceive(Context context, Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

//        if (geofencingEvent.hasError()) {
//
//            Log.v(TAG, "GeofencingEvent Error" + geofencingEvent.getErrorCode());
//
//        }


        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();



        // Get the geofences that were triggered. A single event can trigger
        // multiple geofences.

        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        Geofence geofence = triggeringGeofences.get(0);

//        // Test that the reported transition was of interest.
//        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
//
//            // Get the transition details as a String.
//            String geofenceTransitionDetails = getGeofenceTransitionDetails(
//                    this,
//                    geofenceTransition,
//                    triggeringGeofences
//            );

            // Send notification and log the transition details.
            sendNotification(context, geofenceTransition);

            //Log.i(TAG, geofenceTransitionDetails);


        }


    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    private void sendNotification(Context context, int notificationDetails) {


        if (notificationDetails == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // Get a notification builder that's compatible with platform versions >= 4
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
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
            // Define the notification settings.
            builder
                    .setSmallIcon(R.drawable.ic_action_location)
                    .setContentTitle("You have entered")
                    .setContentText("Click notification to return to app")
                    .setContentIntent(notificationPendingIntent)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
            // Dismiss notification once the user touches it.
            builder.setAutoCancel(true);


            // Get an instance of the Notification manager
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Issue the notification
            mNotificationManager.notify(0, builder.build());

        } else if (notificationDetails == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Get a notification builder that's compatible with platform versions >= 4
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

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

            // Define the notification settings.
            builder
                    .setSmallIcon(R.drawable.ic_action_location)
                    .setContentTitle("You have left")
                    .setContentText("Click notification to return to app")
                    .setContentIntent(notificationPendingIntent)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
            // Dismiss notification once the user touches it.
            builder.setAutoCancel(true);

            // Get an instance of the Notification manager
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Issue the notification
            mNotificationManager.notify(0, builder.build());
        }
    }


    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "transition - enter";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "transition - exit";
            default:
                return "unknown transition";
        }
    }


}





