package com.example.ruoxilu.criticalmass;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RuoxiLu on 4/17/15.
 */
public class MapsHandler {
    public static Context mContext;
    public static Map<String, Marker> mapMarkers = new HashMap<String, Marker>();
    public static Map<Marker, String> markerIDs = new HashMap<Marker, String>();
    public static GoogleMap mMap;

    public static LocationRequest mLocationRequest;
    public static GoogleApiClient mGoogleApiClient;

    private static Location mCurrentLocation = Settings.getDefaultLocation();
    private static Location mLastLocation = Settings.getDefaultLocation();

    public MapsHandler(Context context){
        this.mContext = context;
    }

    public static void initLocationRequest() {
        // Create a new global location parameters object
        mLocationRequest = LocationRequest.create();
        Log.i(Settings.APPTAG, "LOCATION REQUEST CREATED");
        // Set the update interval
        mLocationRequest.setInterval(Settings.UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setFastestInterval(Settings.FAST_INTERVAL_CEILING_IN_MILLLISECONDS);

    }

    public static Location initialMapLocation(){
        LocationManager mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = mLocationManager.getBestProvider(criteria, true);

        // Get Current Location
        if (mLocationManager.getLastKnownLocation(provider) == null) {
            return Settings.getDefaultLocation();

        } else {
            return mLocationManager.getLastKnownLocation(provider);
        }
    }

    /*
     * Create map markers based on location and size
     * Size Criterion:
     *      10-20:
     *      20-50:
     *      50-100:
     *      100-500:
     *      >500:
     */
    // SIGN_MARKER_OBJECT
    public static MarkerOptions createMarkerOpt(MassEvent mEvent) {

        int size = mEvent.getEventSize();
        MarkerOptions markerOpt;

        if (size < Settings.POPSIZE2) {
            markerOpt = setMarkerOpt(mEvent);
            markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2));
        } else if (size < Settings.POPSIZE3) {
            markerOpt = setMarkerOpt(mEvent);
            markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker3));
        } else if (size < Settings.POPSIZE4) {
            markerOpt = setMarkerOpt(mEvent);
            markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker4));
        } else if (size < Settings.POPSIZE5) {
            markerOpt = setMarkerOpt(mEvent);
            markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker5));
        } else {
            markerOpt = setMarkerOpt(mEvent);
            markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker6));

        }

        return markerOpt;
    }

    private static MarkerOptions setMarkerOpt(MassEvent mEvent) {

        MarkerOptions markerOpt = new MarkerOptions().position(
                new LatLng(mEvent.getLocation().getLatitude(), mEvent
                        .getLocation().getLongitude()))
                .title("Location: " + mEvent.getLocationName()).snippet("Size: " + mEvent.getEventSize());

        return markerOpt;
    }

}