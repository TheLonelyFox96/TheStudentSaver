package com.example.thomasfox.thestudentsaver;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * Created by thomasfox on 12/11/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    View mView;
    GoogleMap mGoogleMap;
    protected ArrayList<Geofence> mGeofenceList;
    private PendingIntent pendingIntent;
    private GeofencingRequest geofencingRequest;
    private GeofencingClient mGeofencingClient;


    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;


    // create view based on the map XML.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_map, container, false);
        return mView;

    }

    //Once view is created do this:
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Map");
        super.onViewCreated(view, savedInstanceState);

        mGeofenceList = new ArrayList<Geofence>();
        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);

        pendingIntent = null;
        populateGeofenceList();
        mGeofencingClient = LocationServices.getGeofencingClient(getContext());

    }


    //Get Store and create geofence
    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : Stores.STORES.entrySet()) {
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Stores.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
    }

    //Set geofence to monitor and event trigger

    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent(){
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(getContext(), GeofenceNotification.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


    //readies the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        googleMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //mcdonalds marker
        LatLng latMcDonalds = Stores.STORES.get(Stores.GEOFENCE_McDonalds);
        googleMap.addMarker(new MarkerOptions().position(latMcDonalds).title("Mcdonalds"));
        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latMcDonalds.latitude, latMcDonalds.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));
        //topshop marker
        LatLng latTopshop = Stores.STORES.get(Stores.GEOFENCE_TopShop);
        googleMap.addMarker(new MarkerOptions().position(latTopshop).title("Topshop"));
        Circle circle2 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latTopshop.latitude, latTopshop.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // accessorize
        LatLng latAccessorize = Stores.STORES.get(Stores.GEOFENCE_Accessorize);
        googleMap.addMarker(new MarkerOptions().position(latAccessorize).title("Accessorize"));
        Circle circle3 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latAccessorize.latitude, latAccessorize.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // ann summers
        LatLng latAnnSummers = Stores.STORES.get(Stores.GEOFENCE_AnnSummers);
        googleMap.addMarker(new MarkerOptions().position(latAnnSummers).title("Ann Summers"));
        Circle circle4 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latAnnSummers.latitude, latAnnSummers.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // burton
        LatLng latBurton = Stores.STORES.get(Stores.GEOFENCE_Burton);
        googleMap.addMarker(new MarkerOptions().position(latBurton).title("Burtons"));
        Circle circle5 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latBurton.latitude, latBurton.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // oasis
        LatLng latOasis = Stores.STORES.get(Stores.GEOFENCE_Oasis);
        googleMap.addMarker(new MarkerOptions().position(latOasis).title("Oasis"));
        Circle circle6 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latOasis.latitude, latOasis.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // usc
        LatLng latUSC = Stores.STORES.get(Stores.GEOFENCE_USC);
        googleMap.addMarker(new MarkerOptions().position(latUSC).title("USC"));
        Circle circle7 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latUSC.latitude, latUSC.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // footasylum
        LatLng latFootasylum = Stores.STORES.get(Stores.GEOFENCE_FootAsylum);
        googleMap.addMarker(new MarkerOptions().position(latFootasylum).title("Accessorize"));
        Circle circle8 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latFootasylum.latitude, latFootasylum.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // schuh
        LatLng latSchuh = Stores.STORES.get(Stores.GEOFENCE_Schuh);
        googleMap.addMarker(new MarkerOptions().position(latSchuh).title("Schuh"));
        Circle circle9 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latSchuh.latitude, latSchuh.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        // select
        LatLng latSelect = Stores.STORES.get(Stores.GEOFENCE_Select);
        googleMap.addMarker(new MarkerOptions().position(latSelect).title("Select"));
        Circle circle10 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latSelect.latitude, latSelect.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));



        //checks permission has been granted.
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

}









