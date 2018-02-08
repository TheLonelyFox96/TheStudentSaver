package com.example.thomasfox.thestudentsaver;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by thomasfox on 21/01/2018.
 */

public class Stores {

    public static final String GEOFENCE_McDonalds = "Mcdonalds";
    public static final String GEOFENCE_TopShop = "TopShop";
    public static final String GEOFENCE_Accessorize = "Accessorize";
    public static final String GEOFENCE_AnnSummers = "Ann Summers";
    public static final String GEOFENCE_Burton = "Burton";
    public static final String GEOFENCE_Oasis = "Oasis";
    public static final String GEOFENCE_USC = "USC";
    public static final String GEOFENCE_FootAsylum = "FootAsylum";
    public static final String GEOFENCE_Schuh = "Schuh";
    public static final String GEOFENCE_Select = "Select";
//    public static final String GEOFENCE_MissSelfridge = "Miss Selfridge";

    public static final float GEOFENCE_RADIUS_IN_METERS = 100;


    public static final HashMap<String, LatLng> STORES = new HashMap<String, LatLng>();

    static {

        STORES.put(GEOFENCE_McDonalds, new LatLng(50.7965, -1.06741));
        STORES.put(GEOFENCE_TopShop, new LatLng(50.800734, -1.089648));
        STORES.put(GEOFENCE_Accessorize, new LatLng(50.801300, -1.089829));
        STORES.put(GEOFENCE_AnnSummers, new LatLng(50.806632, -1.089828));
        STORES.put(GEOFENCE_Burton, new LatLng(50.800532, -1.090034));
        STORES.put(GEOFENCE_Oasis, new LatLng(50.800033, -1.089947));
        STORES.put(GEOFENCE_USC, new LatLng(50.801973, -1.090557));
        STORES.put(GEOFENCE_FootAsylum, new LatLng(50.800735, -1.090329));
        STORES.put(GEOFENCE_Schuh, new LatLng(50.800783, -1.090367));
        STORES.put(GEOFENCE_Select, new LatLng(50.800795, -1.089376));
//        STORES.put(GEOFENCE_MissSelfridge, new LatLng(50.800734, -1.089648));






    }


}