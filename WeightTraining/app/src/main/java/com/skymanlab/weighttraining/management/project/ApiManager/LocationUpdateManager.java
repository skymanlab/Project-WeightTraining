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
    private LocationSettingsRequest.Builder settingsRequester;

    // instance variable : init
    private boolean isInitialized = false;

    // constructor
    private LocationUpdateManager(Builder builder) {
        this.activity = builder.activity;
        this.interval = builder.interval;
        this.fastestInterval = builder.fastestInterval;
        this.priority = builder.priority;
        this.locationCallback = builder.locationCallback;
    }

    // getter
    public boolean isInitialized() {
        return isInitialized;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 위치 설정 변경 (사용자의 설정값으로) =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // init
    public void init() {

        // 위치를 가져오기 위한 '통합 위치 제공자' 가져오기
        createFusedLocationProviderClient(this.activity);

        // 위치에 대한 사용자의 설정을 적용하기 위한 LocationRequest 객체 생성
        createLocationRequest(this.interval, this.fastestInterval, this.priority);

        // 위의 LocationResult 객체를 '통합 위치 제공자' 에게 설정값 적용하기 요청
        setLocationRequest(this.locationRequest);

        // 적용하기 요청한 결과를 감시하고 결과를 통해 다음 과정진행하기 / success or failure
        checkSetting(this.activity, this.settingsRequester);

        if (this.fusedLocationClient != null) {
            this.isInitialized = true;
        }
    }


    // fusedLocationProviderClient
    public void createFusedLocationProviderClient(Activity activity) {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }


    // LocationRequest : location update 에 대한 사용자 설정값을 객체로 생성
    public void createLocationRequest(int interval, int fastestInterval, int priority) {

        this.locationRequest = LocationRequest
                .create()
                .setInterval(interval)
                .setFastestInterval(fastestInterval)
                .setPriority(priority);

    }

    public void createLocationRequest(int interval, int fastestInterval, int priority, int numUpdates) {

        this.locationRequest = LocationRequest
                .create()
                .setInterval(interval)
                .setFastestInterval(fastestInterval)
                .setPriority(priority)
                .setNumUpdates(numUpdates);
    }

    // 사용자 설정값이 있는 LocationRequest 객체를 LocationSettingRequest 를 사용하여'통합 위치 제공자' 에 추가하기
    public void setLocationRequest(LocationRequest locationRequest) {
        this.settingsRequester = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
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

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= fusedLocationUpdateClient 사용 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 마지막 위치 가져오기
    public void getLastLocation(Activity activity, OnSuccessListener<Location> onSuccessListener) {

        // [check 1] 권한 승인 확인
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // [check 2] fusedLocationClient 객세 생성 확인
            if (fusedLocationClient != null) {

                // 마지막 위치 가져오기 / 성공했을 시 OnSuccessListener 를 통해 다음 과정 수행하기
                this.fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(activity, onSuccessListener);

            } // [check 2]
            
        } // [check 1]
    }

    // 위치 업데이트 start
    public void startLocationUpdate(Activity activity) {

        // [check 1] 권한 승인 확인
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // [check 2] fusedLocationClient 객세 생성 확인
            if (fusedLocationClient != null) {

                // [check 3] locationCallback 객체 생성 확인
                if (locationCallback != null) {

                    this.fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
               
                } else {
                    throw new RuntimeException("MyLocationUpdateUtil 을 사용하기 위해서는 LocationCallback 객체를 Builder 를 통해 등록해주세요!");
                } // [check 3]

            } // [check 2]

        } // [check 1]
    }

    // 위치 업데이트 stop
    public void stopLocationUpdates(Activity activity) {
        // [check 1] 권한 승인 확인
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // [check 2] fusedLocationClient 객세 생성 확인
            if (fusedLocationClient != null) {

                // [check 3] locationCallback 객체 생성 확인
                if (locationCallback != null) {

                    // update 된 위치에 대한 다음 과정을 진행하는 LocationCallback 객체가 있을 때만
                    this.fusedLocationClient.removeLocationUpdates(locationCallback);

                } else {
                    throw new RuntimeException("MyLocationUpdateUtil 을 사용하기 위해서는 LocationCallback 객체를 Builder 를 통해 등록해주세요!");
                } // [check 3]

            } // [check 2]

        } // [check 1]
    }


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
