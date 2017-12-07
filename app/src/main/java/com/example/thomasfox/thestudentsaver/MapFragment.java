package com.example.thomasfox.thestudentsaver;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.Fragment;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * Created by thomasfox on 12/11/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    View mView;
    GoogleMap mGoogleMap;

    protected ArrayList<Geofence> mGeofenceList;


    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_map, container, false);
        return mView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Map");
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);

        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();

        // Get the geofences used. Geofence data is hard coded in this sample.
        populateGeofenceList();
    }


    //readies the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        googleMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (canAccessLocation()) {
            googleMap.setMyLocationEnabled(true);
        } else {
            requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        }

    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == PermissionChecker.checkSelfPermission(getContext(), perm));
    }

    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : Constants.LANDMARKS.entrySet()) {
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
    }

//    private PendingIntent getGeofencePendingIntent() {
//        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
//        // We use FLAG_UPDATE_CURRENT so that we get the
//        //same pending intent back when calling addgeoFences()
//        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }



}









