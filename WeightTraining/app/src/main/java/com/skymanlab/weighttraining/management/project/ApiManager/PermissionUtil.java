package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
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


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 하나의 권한에 대한 검사  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 권한 승인 여부 확인
    public static boolean hasPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    // 권환을 거부한 적이 있는지 확인
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {

        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 여러개의 권한에 대한 검사 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 여러개의 권한 승인 여부 확인
    public static boolean hasPermissionList(Context context, String... permissionList) {

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
    public static boolean shouldShowRequestPermissionListRationale(Activity activity, String... permissionList) {
        for (String permission : permissionList) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return false;
            }
        }
        return true;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 권한 요청 : ActivityResultLauncher =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 필요한 이유 설명하고 권한 요청
    public static void showSnackbarForPermissionReRequestAndRequestLauncher(Activity activity,
                                                                            int snackbarTitleResId,
                                                                            int snackbarMessageResId,
                                                                            String permission,
                                                                            ActivityResultLauncher<String> activityResultLauncher) {

        final String METHOD_NAME = "[showForPermissionReRequest] ";

        if (activityResultLauncher != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Snackbar > 해당 권한 재 요청을 snackbar 로 요구합니다.");
            // 해당 권한이 필요한 이유를 사용자에게 알려줍니다.
            Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), snackbarTitleResId, Snackbar.LENGTH_INDEFINITE)
                    .setAction(snackbarMessageResId, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // 사용자가 동의하여 권한을 요청하는 화면을 표시합니다.
                            activityResultLauncher.launch(permission);

                        }
                    });
        } else {
            throw new RuntimeException("권한을 요청하기 위해서는 ActivityResultLauncher 객체가 존재해야 합니다. 해당 결과를 요청하고 결과가 전해지는지 감시하며, 결과를 ActivityResultCallback 을 사용하여 다음 과정을 처리하는데 사용됩니다. ");
        }
    }


    // 권한 요청하기
    public static void requestPermissionFromLauncher(String permission, ActivityResultLauncher<String> activityResultLauncher) {
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
    // 필요한 이유 설명하고 권한 요청
    public static void showSnackbarForPermissionReRequest(Activity activity,
                                                          int requestCode,
                                                          int snackbarTitleResId,
                                                          int snackbarMessageResId,
                                                          String... permissionList) {
        final String METHOD_NAME = "[showForPermissionReRequest] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Snackbar > 해당 권한 재 요청을 snackbar 로 요구합니다.");

        // [Snackbar] [] 해당 permission 이 있어야지만 나의 위치를 찾을 수 있습니다.
        Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), snackbarTitleResId, Snackbar.LENGTH_INDEFINITE)
                .setAction(snackbarMessageResId, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 권한을 요청합니다.
                        requestPermission(activity, requestCode, permissionList);
                    }
                })
                .show();

    }

    // 필요한 이유 설명하고 권한 요청 and 권한 요청하는 방법을 OnClickListener 를 통해 직접 선택
    public static void showSnackbarForPermissionReRequest(Activity activity,
                                                          int requestCode,
                                                          int snackbarTitleResId,
                                                          int snackbarMessageResId,
                                                          View.OnClickListener onClickListener) {

        final String METHOD_NAME = "[showForPermissionReRequest] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Snackbar > 해당 권한 재 요청을 snackbar 로 요구합니다.");

        // [Snackbar] [] 해당 permission 이 있어야지만 나의 위치를 찾을 수 있습니다.
        Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), snackbarTitleResId, Snackbar.LENGTH_INDEFINITE)
                .setAction(snackbarMessageResId, onClickListener)
                .show();

    }

    // 권한 요청하기
    public static void requestPermission(Activity activity, int requestCode, String... permissionList) {

        ActivityCompat.requestPermissions(activity, permissionList, requestCode);
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Service  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static boolean isEnabledLocationServices(Activity activity, String service) {

        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(service);
    }


    public static void showSnackbarForLocationServiceRequest(Activity activity,
                                                             int requestCode,
                                                             int snackbarTitleResId,
                                                             int snackbarMessageResId,
                                                             String service) {

        Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), snackbarTitleResId, Snackbar.LENGTH_INDEFINITE)
                .setAction(snackbarMessageResId, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent serviceSettingIntent = new Intent(service);
                        activity.startActivityForResult(serviceSettingIntent, requestCode);
                    }
                })
                .show();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Application setting  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void requestApplicationSetting(Activity activity, int requestCode) {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getApplicationContext().getPackageName()));
        activity.startActivityForResult(intent, requestCode);
    }

}
