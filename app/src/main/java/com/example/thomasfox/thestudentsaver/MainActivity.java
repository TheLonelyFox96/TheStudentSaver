package com.example.thomasfox.thestudentsaver;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {



    private static final String TAG = "";
    MapView mMapView;
    GoogleMap mGoogleMap;
    protected ArrayList<Geofence> mGeofenceList;
    private PendingIntent pendingIntent;
    private GeofencingRequest geofencingRequest;
    private GeofencingClient mGeofencingClient;
    protected GoogleApiClient mGoogleApiClient;



    //Set Dynamic Title Bar
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    //Create of the page on load.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FragmentManager fragmentManager = getFragmentManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Sets Home Fragment to open once app is loaded rather than the blank main activity */
        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_HomeFragment, 0);

        }




    }








    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Creation of the option menu button appearing on the top right of the screen.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();

        //Control for when one of the menu items is clicked.
        if (id == R.id.nav_SettingsFragment) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.nav_HelpFragment){
            fragmentManager.beginTransaction()
                    .replace(R.id.contentframe, new HelpFragment())
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    //Options and control for when one of the navigation menu items are clicked.

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /* Handles the navigation view item clicks here.*/
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

         if (id == R.id.nav_HomeFragment) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentframe, new HomeFragment())
                    .commit();
        }

        else if (id == R.id.nav_MapFragment) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentframe, new MapFragment())
                    .commit();

        } else if (id == R.id.nav_DiscountFragment) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentframe, new DiscountFragment())
                    .commit();
        }

        else if (id == R.id.nav_SettingsFragment) {
             Intent intent = new Intent(this, SettingsActivity.class);
             startActivity(intent);
             return true;
        }

         else if (id == R.id.nav_HelpFragment) {
             fragmentManager.beginTransaction()
                     .replace(R.id.contentframe, new HelpFragment())
                     .commit();
         }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true; }



    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



}




