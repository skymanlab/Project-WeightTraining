package com.skymanlab.weighttraining.management.project.ApiManager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.telecom.StatusHints;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationUtil {

    // ============================================= Last location =============================================
    // 마지막 위치 가져오기
    public static void getLastLocation(Activity activity, OnLastLocationListener onLastLocationListener) {

        // 통합 Location
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        // [check 1] 권한 승인 확인
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (fusedLocationProviderClient != null) {

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                        activity,
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                onLastLocationListener.showLastLocation(location);
                            }
                        }
                );
            } // [check 2]

        } // [check 1]
    }


    // ============================================= interface =============================================
    public interface OnLastLocationListener {
        void showLastLocation(Location location);
    }

}
