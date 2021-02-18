package com.skymanlab.weighttraining.management.project.ApiManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import java.security.acl.Permission;

public class PermissionManager {

    // constant
    private static final String CLASS_NAME = PermissionManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final String[] LOCATION_PERMISSION_LIST = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final String[] BACKGROUND_PERMISSION = {
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    public static final String SYSTEM_LOCATION_SERVICE_SETTING = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
    public static final String APPLICATION_SETTINGS = Settings.ACTION_APPLICATION_SETTINGS;

    // instance variable
    private Activity activity;

    // instance variable
    private ActivityResultLauncher<String> launcher;

    // constructor
    public PermissionManager(Activity activity) {
        this.activity = activity;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= fine_location / coarse_location / location service =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void initLocationPermission(GrantedPermissionListener listener) {
        final String METHOD_NAME = "[initLocationPermission] ";

        if (listener == null)
            return;

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==========================================================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==========================================================================");
        // [check 1] Location Permission : 권한이 승인되었는지 확인
        if (PermissionUtil.hasPermissionList(activity, LOCATION_PERMISSION_LIST)) {       // 승인된 경우

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==============>>>>>>>>> 위치 권한 승인됨");
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==============>>>>>>>>> GPS 권한은 = " + PermissionUtil.isEnabledLocationServices(activity, LocationManager.GPS_PROVIDER));
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==============>>>>>>>>> 네트워크 권한은 = " + PermissionUtil.isEnabledLocationServices(activity, LocationManager.NETWORK_PROVIDER));


            // [check 2] Location Permission / gps provider 와 network provider :  GPS, Network 가 활성화 되어 있는지 확인
            if (PermissionUtil.isEnabledLocationServices(activity, LocationManager.GPS_PROVIDER)
                    && PermissionUtil.isEnabledLocationServices(activity, LocationManager.NETWORK_PROVIDER)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===========> 이제 googleMap , location 서비스를 활성화합니다.");
                // 통과!!! -> 해당 서비스 진행
                listener.setNextProcedure();

            } else {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< GPS 와 네티워크 > 네트워트나 위치 서비스가 활성화되지 않았습니다.");

                // Location Permission / gps provider 와 network provider : 사용자에게 '시스템 위치 설정의 활성화'가 필요한 이유 설명 -> 권한 요청 화면 보여주기
                PermissionUtil.showSnackbarForLocationServiceRequest(
                        activity,
                        PermissionResultManager.LOCATION_SERVICE_REQUEST_CODE,
                        R.string.etc_permission_manager_snack_location_service_request_title,
                        R.string.etc_permission_manager_snack_location_service_request_message,
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                );

            } // [check 2]

        } else {

            // [check 3] Location Permission : 사용자가 이미 권한을 거부하였는지 확인한다.
            if (PermissionUtil.shouldShowRequestPermissionListRationale(activity, LOCATION_PERMISSION_LIST)) {

                // Location Permission : 사용자에게 '위치 권한 승인'이 필요한 이유 설명 -> 권한 요청 화면 보여주기
                PermissionUtil.showSnackbarForPermissionReRequest(
                        activity,
                        PermissionResultManager.LOCATION_PERMISSION_REQUEST_CODE,
                        R.string.etc_permission_manager_snack_location_permission_request_title,
                        R.string.etc_permission_manager_snack_location_permission_request_message,
                        LOCATION_PERMISSION_LIST
                );

            } else {

                // Location Permission : 권한 요청 화면 보여주기
                PermissionUtil.requestPermission(activity, PermissionResultManager.LOCATION_PERMISSION_REQUEST_CODE, LOCATION_PERMISSION_LIST);

            } // [check 3]

        } // [check 1]

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= background_location =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void initBackgroundLocationPermission(GrantedPermissionListener listener) {

        // [check 1] Background Location Permission : '백그라운드 위치 권한' 을 승인하였는지 확인
        if (PermissionUtil.hasPermissionList(activity, BACKGROUND_PERMISSION)) {

            // 통과!!! -> 해당 서비스 진행
            listener.setNextProcedure();

        } else {

            // [check 1] Background Location Permission : 권한을 이미 거부한적이 있는 확인
            if (PermissionUtil.shouldShowRequestPermissionListRationale(activity, BACKGROUND_PERMISSION)) {

                // Background Location Permission : 사용자에게 백그라운드 위치 권한이 필요한 이유를 설명 -> 권한 요청 화면 보여주기
                PermissionUtil.showSnackbarForPermissionReRequest(activity,
                        PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE,
                        R.string.etc_permission_manager_snack_background_location_permission_request_title,
                        R.string.etc_permission_manager_snack_background_location_permission_request_message,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PermissionUtil.requestApplicationSetting(activity, PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE);

                            }
                        }
                );

            } else {

                // Background Location Permission : 권한 요청 화면 보여주기
                PermissionUtil.requestPermission(activity,
                        PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE,
                        BACKGROUND_PERMISSION);

            } // [check 2]

        } // [check 1]

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface GrantedPermissionListener {
        void setNextProcedure();
    }

}
