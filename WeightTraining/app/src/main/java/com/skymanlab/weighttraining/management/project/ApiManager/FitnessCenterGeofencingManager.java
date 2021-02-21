package com.skymanlab.weighttraining.management.project.ApiManager;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

public class FitnessCenterGeofencingManager {

    // constant
    private static final String CLASS_NAME = FitnessCenterGeofencingManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // instance
    private Activity activity;
    private LatLng location;

    // instance
    private GeofencingClient geofencingClient;
    private GeofencingRequest geofencingRequest;
    private PendingIntent pendingIntent;

    // constructor
    public FitnessCenterGeofencingManager(Activity activity, LatLng location) {
        this.activity = activity;
        this.location = location;
    }

    // getter
    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void init() {

        // geofencingClient 가져오기
        this.geofencingClient = FitnessCenterGeofencingUtil.createGeofencingClient(activity);

        // Geofence 생성
        Geofence geofence = FitnessCenterGeofencingUtil.createGeofence(
                location,
                FitnessCenterGeofencingUtil.GEOFENCE_RADIUS_IN_METERS,
                FitnessCenterGeofencingUtil.GEOFENCE_EXPIRATION_IN_MILLISECONDS,
                FitnessCenterGeofencingUtil.GEOFENCE_TRANSITION_TYPE,
                FitnessCenterGeofencingUtil.GEOFENCE_LOITERING_DELAY

        );
        // PendingIntent : application context 에
        this.pendingIntent = FitnessCenterGeofencingUtil.createPendingIntent(activity);

        //
        this.geofencingRequest = FitnessCenterGeofencingUtil.createGeofencingRequest(
                FitnessCenterGeofencingUtil.GEOFENCING_REQUEST_INITIAL_TRIGGER,
                geofence
        );

    }

    public void addGeofence() {
        final String METHOD_NAME = "[addGeofence] ";

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            this.geofencingClient.addGeofences(this.geofencingRequest, this.pendingIntent)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(
                                    activity.findViewById(R.id.nav_home_bottom_bar),
                                    "모니터링 시작 성공!",
                                    Snackbar.LENGTH_SHORT)
                                    .show();

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< ㄴSuccess > = " + aVoid);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(
                                    activity.findViewById(R.id.nav_home_bottom_bar),
                                    "모니터링 시작 실패!",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });


        }
    }

    public void removeGeofence() {

        this.geofencingClient.removeGeofences(this.pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(
                                activity.findViewById(R.id.nav_home_bottom_bar),
                                "모니터링 중지 성공!",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(
                                activity.findViewById(R.id.nav_home_bottom_bar),
                                "모니터링 중지 실패!",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
