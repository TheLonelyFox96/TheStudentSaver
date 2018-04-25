package com.example.thomasfox.thestudentsaver;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by thomasfox on 21/01/2018.
 */

public class Stores {

    public static final String GEOFENCE_McDonalds_Fratton = "Free value item at Fratton Mcdonalds now!";
    public static final String GEOFENCE_McDonalds_Commercial = "Free value item at Mcdonalds now!";
    public static final String GEOFENCE_TopShop = "Enjoy 10% Student Discount with Topshop !";
    public static final String GEOFENCE_Accessorize = "Enjoy 30% + 10% extra at Accessorize now!";
    public static final String GEOFENCE_AnnSummers = "Enjoy 10% Student Discount with Ann Summers!";
    public static final String GEOFENCE_Burton = "Enjoy 10% Student Discount with Burton!";
    public static final String GEOFENCE_Oasis = "Enjoy 10% Student Discount with Oasis !";
    public static final String GEOFENCE_USC = "Enjoy 10% Student Discount with USC!";
    public static final String GEOFENCE_FootAsylum = "Enjoy 10% Student Discount with FootAsylum!";
    public static final String GEOFENCE_Schuh = "Enjoy 10% Student Discount with Schuh!";
    public static final String GEOFENCE_Select = "Enjoy 20% Student Discount with Select!";


    public static final float GEOFENCE_RADIUS_IN_METERS = 50;


    public static final HashMap<String, LatLng> STORES = new HashMap<String, LatLng>();

    static {

        STORES.put(GEOFENCE_McDonalds_Fratton, new LatLng(50.7965, -1.06741));
        STORES.put(GEOFENCE_McDonalds_Commercial, new LatLng(50.802727, -1.087971));
        STORES.put(GEOFENCE_TopShop, new LatLng(50.800734, -1.089721));
        STORES.put(GEOFENCE_Accessorize, new LatLng(50.801344, -1.089768));
        STORES.put(GEOFENCE_AnnSummers, new LatLng(50.801783, -1.088706));
        STORES.put(GEOFENCE_Burton, new LatLng(50.800328, -1.090045));
        STORES.put(GEOFENCE_Oasis, new LatLng(50.800037, -1.089970));
        STORES.put(GEOFENCE_USC, new LatLng(50.801538, -1.090504));
        STORES.put(GEOFENCE_FootAsylum, new LatLng(50.800515, -1.090336));
        STORES.put(GEOFENCE_Schuh, new LatLng(50.800565, -1.090292));
        STORES.put(GEOFENCE_Select, new LatLng(50.800839, -1.089575));

    }






}