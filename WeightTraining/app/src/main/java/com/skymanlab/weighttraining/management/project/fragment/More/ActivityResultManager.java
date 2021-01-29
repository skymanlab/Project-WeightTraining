package com.skymanlab.weighttraining.management.project.fragment.More;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import java.security.Permission;

public class ActivityResultManager {

    // constant
    private static final String CLASS_NAME = PermissionManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 50000;   // PermissionManager : location permission 요청 결과를 받기 위한
    public static final int LOCATION_SERVICE_REQUEST_CODE = 50001;      // PermissionManager : location service enable 요청 결과를 받기 위한
    public static final int RESOLUTION_REQUEST_CODE = 50002;            // FusedLocationProviderClient : location request 적용 실패 시 -> 요청 결과를 받기 위한


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= permission result for ActivityResultLauncher =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public ActivityResultLauncher<String> createActivityResultLauncher(Fragment fragment, PermissionResultListener permissionResultListener) {
        final String METHOD_NAME = "[createActivityResultLauncher] ";
        return fragment.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                setNextProcedureForPermissionResult(result, permissionResultListener);
            }
        });
    }

    public ActivityResultLauncher<String> createActivityResultLauncher(AppCompatActivity appCompatActivity, PermissionResultListener permissionResultListener) {
        final String METHOD_NAME = "[createActivityResultLauncher] ";
        return appCompatActivity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                setNextProcedureForPermissionResult(result, permissionResultListener);
            }
        });
    }


    public void setNextProcedureForPermissionResult(boolean result, PermissionResultListener permissionResultListener) {
        final String METHOD_NAME = "[createActivityResultLauncher] ";

        if (result) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< next procedure > permission result : GRANTED / 다음 과정을 수행합니다.");
            permissionResultListener.setGrantedPermissionResult();

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< next procedure > permission result : DENIED / 기능이 제한됩니다. 사용자에게 알려줍니다.");
            permissionResultListener.setDeniedPermissionResult();

        }
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Request Code 에 따라 다음 절차를 설정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [1] LOCATION_PERMISSION_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForLocationPermissionRequestResult(Fragment targetFragment, String[] permissions, int[] grantResults) {
        final String METHOD_NAME = "[setNextProcedureForLocationPermissionRequestResult] ";

        // [check 1] 모든 권한이 승인 되었는지 확인
        if (isItApprovedAllPermissions(permissions, grantResults)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location permission > == 모든 권한을 승인하였습니다.");

            // [check 2] targetFragment 가 어떤 Fragment 인지 구분
            if (targetFragment instanceof FitnessCenterFragment) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< targetFragment > FitnessCenterFragment : fragment - sectionManager - googleMapManager 의 init() 수행합니다.");
                setNextProcedureForGrantedLocationPermissionOfFitnessCenterFragment((FitnessCenterFragment) targetFragment);
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location permission > == 거부한 권한이 있습니다. ");
        } // [check 1]

    }

    private boolean isItApprovedAllPermissions(String[] permissions, int[] grantResults) {
        final String METHOD_NAME = "[isItApprovedAllPermissions] ";

        if (permissions.length == 0)
            return false;

        for (int index = 0; index < permissions.length; index++) {

            if (0 < permissions.length && !permissions[index].equals("")) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< permission > " + permissions[index]);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< permission grant result > " + grantResults[index]);

                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setNextProcedureForGrantedLocationPermissionOfFitnessCenterFragment(FitnessCenterFragment fragment) {
        fragment.getSectionManager().getGoogleMapManager().init();
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [2] GPS_ENABLE_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForLocationServiceRequestResult(Fragment targetFragment) {
        final String METHOD_NAME = "[setNextProcedureForLocationServiceRequestResult] ";

        // [PermissionUtil] [util] util 을 이용하여 location service 활성화되었는지 다시 확인하기 위해서
        PermissionUtil util = new PermissionUtil();

        // [check 1] : 다시 location service 가 활성화 되었는지 확인
        if (util.checkLocationServicesStatus(targetFragment.getActivity())) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location service > 해당 location service 는 활성화 되었습니다.");

            // [check 2] targetFragment 가 어떤 Fragment 인지 구분
            if (targetFragment instanceof FitnessCenterFragment) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< targetFragment > FitnessCenterFragment 에 대한 다음 과정을 진행합니다.");
                setNextProcedureForGrantedLocationServiceOfFitnessCenterFragment((FitnessCenterFragment) targetFragment);

            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location service > 해당 location service 는 비활성화 된 상태입니다.");
        } // [check 1]


    }

    private void setNextProcedureForGrantedLocationServiceOfFitnessCenterFragment(FitnessCenterFragment fragment) {
        fragment.getSectionManager().getGoogleMapManager().init();
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [3] RESOLUTION_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForResolutionRequestResult() {

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Listener =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface PermissionResultListener {
        void setGrantedPermissionResult();

        void setDeniedPermissionResult();
    }


}
