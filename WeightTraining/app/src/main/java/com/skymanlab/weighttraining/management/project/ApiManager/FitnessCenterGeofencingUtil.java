package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class FitnessCenterGeofencingUtil {

    // constant
    public static final float GEOFENCE_RADIUS_IN_METERS = 100f;             // Geofence 반경 : 100[m]
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = Geofence.NEVER_EXPIRE;       // Geofence 유지 시간 : 계속 유지
    public static final int GEOFENCE_TRANSITION_TYPE = Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT;      // Geofence 감지 유형 : 들어올 때랑 나갈때
    public static final int GEOFENCE_LOITERING_DELAY = 60 * 1000;           // Geofence 의 Transition 이 DWELL 일 때, 이를 감지하기 위해 머물러야 하는 시간 : 60 * 1[s] = 1[min]
    public static final int GEOFENCING_REQUEST_INITIAL_TRIGGER = GeofencingRequest.INITIAL_TRIGGER_ENTER;       // 해당 Geonfence 를

    // constant
    private static final String REQUEST_ID_FITNESS_CENTER_GEOFENCING = "fitnessCenterGeofencing";
    private static final int PENDING_INTENT_REQUEST_CODE_FITNESS_CENTER_GEOFENCING = 51000;


    public static GeofencingClient createGeofencingClient(Context context) {
        return LocationServices.getGeofencingClient(context);
    }


    public static Geofence createGeofence(LatLng location, float radiusInMeters, long expirationInMilliseconds, int transitionType) {
        return new Geofence.Builder()
                .setRequestId(REQUEST_ID_FITNESS_CENTER_GEOFENCING)
                .setCircularRegion(
                        location.latitude,
                        location.longitude,
                        radiusInMeters
                )
                .setExpirationDuration(expirationInMilliseconds)
                .setTransitionTypes(transitionType)
//                .setLoiteringDelay(GEOFENCE_LOITERING_DELAY)
                .build();
    }


    public static PendingIntent createPendingIntent(Context context) {

        // 이 braodcast 를 activity 가 아니라 application context 에
        Intent intent = new Intent(context, FitnessCenterGeofencingBroadcastReceiver.class);
        intent.setAction(FitnessCenterGeofencingBroadcastReceiver.ACTION_FITNESS_CENTER_GEOFENCING);
        return PendingIntent.getBroadcast(
                context,
                0,
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
