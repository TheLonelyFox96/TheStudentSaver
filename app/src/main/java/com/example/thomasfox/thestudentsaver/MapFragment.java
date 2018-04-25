package com.example.thomasfox.thestudentsaver;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.app.Fragment;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static android.app.Service.START_REDELIVER_INTENT;


/**
 * Created by thomasfox on 12/11/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>, LocationListener{


    private static final String TAG = "";
    MapView mMapView;
    View mView;
    GoogleMap mGoogleMap;
    protected ArrayList<Geofence> mGeofenceList;
    private PendingIntent pendingIntent;
    private GeofencingRequest geofencingRequest;
    private GeofencingClient mGeofencingClient;
    protected GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;




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

        mGeofenceList = new ArrayList<>();

        pendingIntent = null;
        mGeofencingClient = LocationServices.getGeofencingClient(getContext());

        initaliseMap();
        buildAPIClient();
        createStoreGeofences();
    }

    public void initaliseMap(){
        mMapView = mView.findViewById(R.id.mapView);
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }


    /** CREATION OF GOOGLE API CLIENT **/

    private void buildAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /* Sourced from : io2015codelabs.appspot.com/codelabs/geofences#4 */
    @Override
    public void onStart(){
        super.onStart();



      if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();

        }

    }

    @Override
    public void onStop(){
        super.onStop();
        mGoogleApiClient.connect();
    }

    /** CREATION END OF GOOGLE API CLIENT **/


    /** START OF BUILDING GEOFENCES : Sourced from - https://developer.android.com/training/location/geofencing.html **/

    private void createStoreGeofences() {
        //create geofence for each Store in database.
        for (HashMap.Entry<String, LatLng> entry : Stores.STORES.entrySet()) {
            Geofence storeGeofences = new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Stores.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
            mGeofenceList.add(storeGeofences);
            Log.v(TAG, "GEOFENCES CREATED");
        }
    }

        private void addStoreGeofences(){

        GeofencingRequest geofencingRequest = getGeofencingRequest();


            Intent intent = new Intent(getContext(), Receiver.class);
            Log.v(TAG, "PENDING INTENT Activated");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                if (!mGoogleApiClient.isConnected()) {
                Log.v(TAG, "GOOGLE API NOT CONNECTED");
                } else {
              try{
                if (checkLocationPermission()) {
                    LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, geofencingRequest, pendingIntent).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Toast.makeText(getContext(), "Geofences Added", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Geofences Not Added", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }

        }catch (SecurityException SeE){Log.v(TAG,"Error");
              }
        }

    }



    private GeofencingRequest getGeofencingRequest() {
        return new GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofences(mGeofenceList)
                .build();
    }

    /** END OF BUILDING GEOFENCES**/





   /** MAP SETUP AND GETTING LOCATION OF USER **/


    //readies the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //Mcdonalds Fratton marker
        LatLng latMcdonalds = Stores.STORES.get(Stores.GEOFENCE_McDonalds_Fratton);
        googleMap.addMarker(new MarkerOptions().position(latMcdonalds).title("Mcdonalds"));
        Circle circle1 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latMcdonalds.latitude, latMcdonalds.longitude))
                .radius(Stores.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

        //Mcdonalds Commercial Road marker
        LatLng latMcdonalds_commercial = Stores.STORES.get(Stores.GEOFENCE_McDonalds_Commercial);
        googleMap.addMarker(new MarkerOptions().position(latMcdonalds_commercial).title("Mcdonalds"));
        Circle circle11 = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latMcdonalds_commercial.latitude, latMcdonalds_commercial.longitude))
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

        if(checkLocationPermission()) {
            mGoogleMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLatestLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onResult(@NonNull Status status) {
    }

    private void getLatestLocation(){
        if(checkLocationPermission()){
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lastLocation != null){

                startLocationUpdates();
            }
            else{
                startLocationUpdates();
            }
        }
        else{
            askPermission();
        }
    }

    private LocationRequest requestUserLocation;
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;
    private final int REQ_PERMISSION = 20;

    //Start Update Location - help from: android location updates.
    private void startLocationUpdates(){
        requestUserLocation = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        if (checkLocationPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, requestUserLocation, this);
            addStoreGeofences();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

    }

    private boolean checkLocationPermission(){
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED);
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQ_PERMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLatestLocation();

                }
                else{
                    Log.v(TAG, "LOCATION DENIED");
                }
            }
        }
    }






}









