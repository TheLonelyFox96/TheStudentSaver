package com.example.thomasfox.thestudentsaver;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.IBinder;
import android.preference.SwitchPreference;
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

public class GeofenceNotification extends IntentService {


    public static final String TAG = "GeofenceService";

    String CHANNEL_ID;

    public GeofenceNotification() {
        super("GeofenceNotification");
    }



    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition, triggeringGeofences);

            sendNotification();
            Log.v(TAG, "Notification Sent");

        }



        }


    private String getGeofenceTransitionDetails(
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

        private void sendNotification(){
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent = new Intent(this, DiscountFragment.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.accessorize_logo)

                .setContentTitle("New Discount Nearby!")
                .setContentText("10% Off at Accessorize")
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        notification.setAutoCancel(true);
        notificationManager.notify(0, notification.build());


    }



    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return ("Entered");
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return ("Exit");
            default:
                return ("None");
        }
    }
}


/* This was used to help - https://stackoverflow.com/questions/42141226/i-want-to-push-a-notification-when-exiting-a-geofence-in-android-studio*/



