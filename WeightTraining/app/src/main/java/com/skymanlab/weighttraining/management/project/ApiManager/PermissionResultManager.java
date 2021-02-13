package com.skymanlab.weighttraining.management.project.ApiManager;

import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.SettingsActivity;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;

public class PermissionResultManager {

    // constant
    private static final String CLASS_NAME = PermissionManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 50000;   // PermissionManager : location permission 요청 결과를 받기 위한
    public static final int LOCATION_SERVICE_REQUEST_CODE = 50001;      // PermissionManager : location service enable 요청 결과를 받기 위한
    public static final int RESOLUTION_REQUEST_CODE = 50002;            // FusedLocationProviderClient : location request 적용 실패 시 -> 요청 결과를 받기 위한
    public static final int BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE = 50003;
    public static final int APPLICATION_SETTINGS_REQUEST_CODE = 50004;


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
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [50000] LOCATION_PERMISSION_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForLocationPermissionRequestResult(Fragment targetFragment, String[] permissions, int[] grantResults) {
        final String METHOD_NAME = "[setNextProcedureForLocationPermissionRequestResult] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Fragment > 객체는 ? = " + targetFragment);

        // [check 1] 모든 권한이 승인 되었는지 확인
        if (isItApprovedAllPermissions(permissions, grantResults)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location permission > == 모든 권한을 승인하였습니다.");

            // [check 2] targetFragment 가 어떤 Fragment 인지 구분
            if (targetFragment instanceof FitnessCenterSearchFragment) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< targetFragment > FitnessCenterRegisterFragment : fragment - sectionManager - googleMapManager 의 init() 수행합니다.");
                // FitnessCenterRegisterFragment : sectionManager 의 구글 맵을 초기화하는 method 를 호출한다.
                ((FitnessCenterSearchFragment) targetFragment).getSectionManager().getGoogleMapManager().init();

            } else if (targetFragment instanceof SettingsActivity.SettingsFragment) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< targetFragment > SettingsFragment : 해당 locationPermission 을 set 으로 만듭니다.");
                ((SettingsActivity.SettingsFragment) targetFragment).setLocationPermissionState();
                ((SettingsActivity.SettingsFragment) targetFragment).setBackgroundLocationPermissionState();

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


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [50001] GPS_ENABLE_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForLocationServiceRequestResult(Fragment targetFragment) {
        final String METHOD_NAME = "[setNextProcedureForLocationServiceRequestResult] ";

        // [check 1] Location Service : GSP, Network 가 활성화 되었는지 확인
        if (PermissionUtil.isEnabledLocationServices(targetFragment.getActivity(), LocationManager.GPS_PROVIDER)
                || PermissionUtil.isEnabledLocationServices(targetFragment.getActivity(), LocationManager.NETWORK_PROVIDER)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location service > 해당 location service 는 활성화 되었습니다.");

            // [check 2] Fragment : targetFragment 가 FitnessCenterRegisterFragment 인지 구분
            if (targetFragment instanceof FitnessCenterSearchFragment) {

                // FitnessCenterRegisterFragment : sectionManager 의 구글 맵을 초기화하는 method 를 호출한다.
                ((FitnessCenterSearchFragment) targetFragment).getSectionManager().getGoogleMapManager().init();
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< targetFragment > FitnessCenterFragment 에 대한 다음 과정을 진행합니다.");

            } // [check 2]

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location service > 해당 location service 는 비활성화 된 상태입니다.");

        } // [check 1]

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [50002] RESOLUTION_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForResolutionRequestResult() {

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [50003] BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForBackgroundLocationPermissionRequestResult(Fragment targetFragment) {
        final String METHOD_NAME = "[setNextProcedureForBackgroundLocationPermissionRequestResult] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< targetFragment > 어디 fragment 인가요? = " + targetFragment);

        if (targetFragment instanceof SettingsActivity.SettingsFragment) {

            // SettingsFragment : 'Location Permission' 과 'Background Location Permission' 의 상태에 따른 화면 설정 method 를 실행한다.
            ((SettingsActivity.SettingsFragment) targetFragment).setLocationPermissionState();
            ((SettingsActivity.SettingsFragment) targetFragment).setBackgroundLocationPermissionState();
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< SettingsFragment > 설정 화면을 다시 설정하고 있습니다. ");
        }

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [50004] APPLICATION_SETTINGS_REQUEST_CODE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setNextProcedureForApplicationSettingRequestResult(Fragment targetFragment) {
        final String METHOD_NAME = "[setNextProcedureForApplicationPermissionRequestResult] ";

        if (targetFragment instanceof SettingsActivity.SettingsFragment) {

            // SettingsFragment : 'Location Permission' 과 'Background Location Permission' 의 상태에 따른 화면 설정 method 를 실행한다.
            ((SettingsActivity.SettingsFragment) targetFragment).setLocationPermissionState();
            ((SettingsActivity.SettingsFragment) targetFragment).setBackgroundLocationPermissionState();
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< SettingsFragment > 설정 화면을 다시 설정하고 있습니다. ");
        }

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Listener =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface PermissionResultListener {
        void setGrantedPermissionResult();

        void setDeniedPermissionResult();
    }


}
