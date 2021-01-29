package com.skymanlab.weighttraining.management.project.fragment.More;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

public class PermissionManager {

    // constant
    private static final String CLASS_NAME = PermissionManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final String[] LOCATION_PERMISSION_LIST = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    // instance variable
    private Activity activity;

    // instance variable
    private PermissionUtil util;
    private ActivityResultLauncher<String> launcher;

    // constructor
    public PermissionManager(Activity activity) {
        this.activity = activity;
    }

    public void init(GrantedPermissionListener listener) {

        if (listener == null)
            return;

        // [PermissionUtil] [util] Permission 관련 method 를 사용하기 위해 객체 생성
        util = new PermissionUtil();

        // [check 1] 권한 승인 여부 확인
        if (util.hasPermissionList(activity, LOCATION_PERMISSION_LIST)) {       // 승인된 경우

            // [check 2] 시스템 위치 서비스를 활성화 했는지 확인
            if (util.checkLocationServicesStatus(activity)) {

                // 해당 권한, 서비스가 활성화 되었으므로 진행하면 됩니다.
                listener.setNextProcedure();

            } else {

                // 사용자에게 시스템 위치 서비스가 왜 필요한지 설명하고 요청하기
                util.showDialogForLocationServiceRequest(activity, ActivityResultManager.LOCATION_SERVICE_REQUEST_CODE);

            } // [check 2]

        } else {

            // [check 3] 이미 거부한 권한인지 확인
            if (util.shouldShowRequestPermissionRationale(activity, LOCATION_PERMISSION_LIST)) {

                // 사용자에게 필요한 이유 설명과 권한 요청 하기 / request coed = LOCATION_PERMISSION_REQUEST_CODE / permissions = LOCATION_PERMISSION_LIST
                util.showDialogForPermissionReRequest(activity, ActivityResultManager.LOCATION_PERMISSION_REQUEST_CODE, LOCATION_PERMISSION_LIST);

            } else {

                // permission 요청하기
                util.requestPermissionList(activity, ActivityResultManager.LOCATION_PERMISSION_REQUEST_CODE, LOCATION_PERMISSION_LIST);

            } // [check 3]

        } // [check 1]

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface GrantedPermissionListener {
        void setNextProcedure();
    }

}
