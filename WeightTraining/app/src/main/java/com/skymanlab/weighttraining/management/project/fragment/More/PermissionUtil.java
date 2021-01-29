package com.skymanlab.weighttraining.management.project.fragment.More;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;


public class PermissionUtil {

    // constant
    private static final String CLASS_NAME = PermissionUtil.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constructor
    public PermissionUtil() {
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ActivityResultLauncher =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 권한 승인 여부 확인
    public boolean hasPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    // 권환을 거부한 적이 있는지 확인
    public boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {

        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    // 거부한 요청자에게 필요한 이유 설명하고 요청하는 snackbar 화면에 표시하기
    public void showDialogForPermissionReRequest(Activity activity, String permission, ActivityResultLauncher<String> activityResultLauncher) {
        final String METHOD_NAME = "[showForPermissionReRequest] ";

        if (activityResultLauncher != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Snackbar > 해당 권한 재 요청을 snackbar 로 요구합니다.");

            // 해당 권한이 필요한 이유를 사용자에게 알려줍니다.
            Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), R.string.project_permission_re_request_reason, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.project_permission_re_request, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // 사용자가 동의하여 권한을 요청하는 화면을 표시합니다.
                            activityResultLauncher.launch(permission);

                        }
                    })
                    .show();

        } else {
            throw new RuntimeException("권한을 요청하기 위해서는 ActivityResultLauncher 객체가 존재해야 합니다. 해당 결과를 요청하고 결과가 전해지는지 감시하며, 결과를 ActivityResultCallback 을 사용하여 다음 과정을 처리하는데 사용됩니다. ");
        }

    }

    // 권한 요청하기
    public void requestPermissionFromLauncher(String permission, ActivityResultLauncher<String> activityResultLauncher) {
        final String METHOD_NAME = "[requestPermission] ";

        if (activityResultLauncher != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< launch > 결과를 감시하는 launcher 에게 해당 권한을 요청합니다.");
            // 사용자가 동의하여 권한을 요청하는 화면을 표시합니다.
            activityResultLauncher.launch(permission);

        } else {
            throw new RuntimeException("권한을 요청하기 위해서는 ActivityResultLauncher 객체가 존재해야 합니다. 해당 결과를 요청하고 결과가 전해지는지 감시하며, 결과를 ActivityResultCallback 을 사용하여 다음 과정을 처리하는데 사용됩니다. ");
        }
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ActivityCompat.requestPermission() =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 여러개의 권한 승인 여부 확인
    public boolean hasPermissionList(Context context, String... permissionList) {

        if (context != null && 0 < permissionList.length) {

            if (permissionList.length == 0)
                return false;

            for (String permission : permissionList) {
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                    // 하나라도 거부되어 있으면 해당 권한들에 대해서 승인 요청을 해야 하므로 false 를 리턴한다.
                    return false;
                }
            }
        }
        return true;
    }

    // 권환을 거부한 적이 있는지 확인
    public boolean shouldShowRequestPermissionRationale(Activity activity, String... permissionList) {
        for (String permission : permissionList) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    // 거부한 요청자에게 필요한 이유 설명하고 요청하는 snackbar 화면에 표시하기
    public void showDialogForPermissionReRequest(Activity activity, int requestCode, String... permissionList) {
        final String METHOD_NAME = "[showForPermissionReRequest] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Snackbar > 해당 권한 재 요청을 snackbar 로 요구합니다.");

        // [Snackbar] [] 해당 permission 이 있어야지만 나의 위치를 찾을 수 있습니다.
        Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), R.string.etc_permission_util_snack_location_permission_request_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.etc_permission_util_snack_location_permission_request_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 권한을 요청합니다.
                        requestPermissionList(activity, requestCode, permissionList);
                    }
                })
                .show();

    }

    // 권한 요청하기
    public void requestPermissionList(Activity activity, int requestCode, String... permissionList) {

        ActivityCompat.requestPermissions(activity, permissionList, requestCode);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 시스템 설정에서 '위치사용'을 활성화 하였는지 확인 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * 해당 위치 추적을 하기 위해 필요한 시스템의 '위치' 사용을 허락했는지 검사한다.
     *
     * @param activity
     * @return
     */
    public boolean checkLocationServicesStatus(Activity activity) {

        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * 시스템의 '위치' 를 사용으로 변환하도록 하기위해 설정창으로 이동
     *
     * @param activity
     * @param requestCode
     */
    public void showDialogForLocationServiceRequest(Activity activity, int requestCode) {

        Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), R.string.etc_permission_util_snack_location_service_request_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.etc_permission_util_snack_location_service_request_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 시스템 설정 : 위치 설정의 사용여부를 결정하기 위한 화면 표시
                        Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivityForResult(callGPSSettingIntent, requestCode);
                    }
                })
                .show();
    }
}
