package com.skymanlab.weighttraining.management.project.ApiManager;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationUpdateManager {

    // constant : location request
    public static final int INTERNAL = 10000;         // 선호하는 위치 업데이트 수신 간격
    public static final int FASTEST_INTERNAL = 1000;  // 업데이트를 처리할 수 있는 가장 빠른 간격
    public static final int PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;       // 도시 블록 내의 위치 정밀도를 요청 : 100 [m]

    // instance variable : builder
    private Activity activity;
    private int interval;
    private int fastestInterval;
    private int priority;
    private LocationCallback locationCallback;

    // instance variable :  use location api
    private FusedLocationProviderClient fusedLocationClient;

    // instance variable : location setting
    private LocationRequest locationRequest;
    private LocationSettingsRequest.Builder locationSettingsRequestBuilder;

    // constructor
    private LocationUpdateManager(Builder builder) {
        this.activity = builder.activity;
        this.interval = builder.interval;
        this.fastestInterval = builder.fastestInterval;
        this.priority = builder.priority;
        this.locationCallback = builder.locationCallback;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 위치 업데이트를 사용하기 위한 초기 설정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // init
    public void initSetup() {

        // 위치를 가져오기 위한 '통합 위치 제공자' 가져오기
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        // 위치 서비스를 요청하기 위한 설정
        locationRequest = createLocationRequest(this.interval, this.fastestInterval, this.priority);

        // 위에서 설정한 LocationResult(위치 요청하기 위한 설정값) 객체를 LocationSettingsRequest(설정값을 fusedLocationProviderClient 에 요청) 를 통해 요청을 추가한다.
        locationSettingsRequestBuilder = addLocationRequest(this.locationRequest);

        // LocationSettingsRequest 를 통해 추가한 LocationRequest 가 FusedLocationProviderClient 에 잘 적용되었는지 확인하기
        checkSetting(activity, locationSettingsRequestBuilder);

    }


    // ================================================= LocationRequest =================================================
    public LocationRequest createLocationRequest(int interval, int fastestInterval, int priority) {

        // interval : 위치 업데이트 간견
        // fastestInterval : 가장 빠른 위치 업데이트 간견
        // priority : 우선 순위

        return LocationRequest
                .create()
                .setInterval(interval)
                .setFastestInterval(fastestInterval)
                .setPriority(priority);

    }

    public LocationRequest createLocationRequest(int interval, int fastestInterval, int priority, int numUpdates) {

        // interval : 위치 업데이트 간견
        // fastestInterval : 가장 빠른 위치 업데이트 간견
        // priority : 우선 순위
        // numNumber : 반복 회수

        return LocationRequest
                .create()
                .setInterval(interval)
                .setFastestInterval(fastestInterval)
                .setPriority(priority)
                .setNumUpdates(numUpdates);
    }

    // ================================================= LocationSettingsRequest =================================================
    public LocationSettingsRequest.Builder addLocationRequest(LocationRequest locationRequest) {

        // LocationRequest 를 추가하기
        return new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

    }

    // LocationSettingsRequest 를 통해 적용한 설정이 충족되었는지 SettingsClient 를 통해 이 LocationSettingsResponse 를 감시한다. 그리고 이 객체의 상태 코드로 결과를 확인한다.
    public void checkSetting(Activity activity, LocationSettingsRequest.Builder settingsRequester) {

        SettingsClient client = LocationServices.getSettingsClient(activity);

        Task<LocationSettingsResponse> settingsResponseTask = client.checkLocationSettings(settingsRequester.build());

        // 성공 : OnSuccessListener 를 통해 성공 후 과정 진행
        settingsResponseTask.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });

        // 실패 : OnFailureListener 를 통해 실패 후 과정 진행 => 사용자에게 위치 설정을 변경하라는 메시지를 표시한다.
        settingsResponseTask.addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (e instanceof ResolvableApiException) {
                    // Exception 이 ResolvableApiException 이란 말은 사용자의 위치 설정을 적용하지 못 했다는 의미이다. -> 사용자에게 알려줄 필요가 있다.

                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult()
                        resolvable.startResolutionForResult(activity, PermissionResultManager.RESOLUTION_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }



    // ================================================= Location update start/stop =================================================
    // 위치 업데이트 start
    public void startLocationUpdate(Activity activity) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (fusedLocationClient != null) {

                if (locationRequest != null) {

                    if (locationCallback != null) {

                        this.fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

                    } else {
                        throw new RuntimeException("MyLocationUpdateUtil 을 사용하기 위해서는 LocationCallback 객체를 Builder 를 통해 등록해주세요!");
                    } // [check 3

                }

            } // [check 2]

        } // [check 1]
    }

    // 위치 업데이트 stop
    public void stopLocationUpdates(Activity activity) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (fusedLocationClient != null) {

                if (locationCallback != null) {

                    this.fusedLocationClient.removeLocationUpdates(locationCallback);

                } else {
                    throw new RuntimeException("MyLocationUpdateUtil 을 사용하기 위해서는 LocationCallback 객체를 Builder 를 통해 등록해주세요!");
                } // [check 3]

            } // [check 2]

        } // [check 1]
    }


    // =========================================================== Builder ===========================================================
    public static class Builder {

        // instance variable : fused location provider client 를 가져오기 위한
        private Activity activity;

        // instance variable : location request
        private int interval;
        private int fastestInterval;
        private int priority;
        private int numUpdates;

        // instance variable : 업데이된 location 에 대한 처리를 하는 location
        private LocationCallback locationCallback;

        // constructor
        public Builder() {

        }

        // setter
        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setFastestInterval(int fastestInterval) {
            this.fastestInterval = fastestInterval;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setNumUpdates(int numUpdates) {
            this.numUpdates = numUpdates;
            return this;
        }

        public Builder setLocationCallback(LocationCallback locationCallback) {
            this.locationCallback = locationCallback;
            return this;
        }

        // create
        public LocationUpdateManager create() {
            return new LocationUpdateManager(this);
        }
    }


}
