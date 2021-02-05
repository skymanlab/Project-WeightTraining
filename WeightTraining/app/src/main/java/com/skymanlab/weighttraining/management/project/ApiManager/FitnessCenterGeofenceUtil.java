package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class FitnessCenterGeofenceUtil {

    // constant
    public static final float GEOFENCE_RADIUS_IN_METERS = 100f;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = Geofence.NEVER_EXPIRE;
    public static final int GEOFENCE_TRANSITION_TYPE = Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT;
    public static final int GEOFENCE_LOITERING_DELAY = 2000;
    public static final int GEOFENCING_REQUEST_INITIAL_TRIGGER = GeofencingRequest.INITIAL_TRIGGER_DWELL;

    // constant
    private static final String GEOFENCE_REQUEST_ID = "fitnessCenterGeofence";
    private static final int PENDING_INTENT_REQUEST_CODE = 51000;


    public static GeofencingClient createGeofencingClient(Context context) {
        return LocationServices.getGeofencingClient(context);
    }


    public static Geofence createGeofence(LatLng location, float radiusInMeters, long expirationInMilliseconds, int transitionType) {
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQUEST_ID)
                .setCircularRegion(
                        location.latitude,
                        location.longitude,
                        radiusInMeters
                )
                .setExpirationDuration(expirationInMilliseconds)
                .setTransitionTypes(transitionType)
                .setLoiteringDelay(GEOFENCE_LOITERING_DELAY)
                .build();
    }


    public static PendingIntent createPendingIntent(Context context) {

        // 이 braodcast 를 activity 가 아니라 application context 에
        Intent intent = new Intent(context, FitnessCenterGeofenceBroadcastReceiver.class);

        return PendingIntent.getBroadcast(
                context,
                PENDING_INTENT_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    public static GeofencingRequest createGeofencingRequest(int initialTrigger, Geofence geofence) {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(initialTrigger)
                .addGeofence(geofence)
                .build();
    }


}
