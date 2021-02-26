package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

public class SettingsManager {

    // constant
    private static final String CLASS_NAME = SettingsManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= is initial installation =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static boolean checkIsFinishedInitialization(Context context) {
        final String METHOD_NAME = "[checkIsFinishedInitialization]";

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(
                        context.getString(R.string.preference_key_isFinishedInitialization),
                        false
                );

    }

    public static void setIsFinishedInitialization(Context context, boolean isFinishedInitialization) {
        final String METHOD_NAME = "[setIsFinishedInitialization] ";

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(
                        context.getString(R.string.preference_key_isFinishedInitialization),
                        isFinishedInitialization
                )
                .commit();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= user number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static boolean checkUserNumber(Context context) {

        int userNumber = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(context.getString(R.string.preference_key_user_number), 0);

        if (0 < userNumber) {
            return true;
        } else {
            return false;
        }
    }

    public static int getUserNumber(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(
                        context.getString(R.string.preference_key_user_number),
                        0
                );
    }

    public static void setUserNumber(Context context, int userNumber) {
        final String METHOD_NAME = "[setInitialSetting] ";

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(
                        context.getString(R.string.preference_key_user_number),
                        userNumber
                )
                .commit();

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= is kept logged in =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static boolean checkIsKeptLoggedIn(Context context) {
        final String METHOD_NAME = "[initKeepLoggedIn] ";


        boolean isCheckableOfKeepLoggedIn = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(
                        context.getString(R.string.preference_key_is_kept_logged_in),
                        false
                );

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< keepLoggedIn > 첫 로그인 유지 설정 확인 중 = " + isCheckableOfKeepLoggedIn);

        // [check 1] '설정' 에서 로그인 유지를 true 로 했다면...
        if (isCheckableOfKeepLoggedIn) {

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                return true;
            }
        }
        return false;

    }

    public static void setIsKeptLoggedIn(Context context, boolean isKeptLoggedIn) {

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(
                        context.getString(R.string.preference_key_is_kept_logged_in),
                        isKeptLoggedIn
                )
                .commit();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= is saved base event =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static boolean checkIsSavedBaseEvent(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(
                        context.getString(R.string.preference_key_is_saved_base_event),
                        false
                );

    }

    public static void setIsSavedBaseEvent(Context context, boolean isSaved) {

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(
                        context.getString(R.string.preference_key_is_saved_base_event),
                        isSaved
                )
                .commit();

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= is disclosed fitness center =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static boolean getIsDisclosedFitnessCenter(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(
                        context.getString(R.string.preference_key_is_disclosed),
                        false
                );

    }

    public static void setIsDisclosedFitnessCenter(Context context, boolean isDisclosed) {
        final String METHOD_NAME = "[setInitialSetting] ";

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(
                        context.getString(R.string.preference_key_is_disclosed),
                        isDisclosed
                )
                .commit();
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 모든 설정값 확인
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    public static void displayAllSettingsValue(Context context) {
        final String METHOD_NAME = "[displayAllSettingsValue] ";

        // isFinishedInitialization : 초기화가 완료 되었냐? / true or false
        // userNumber : 유저 번호 / number
        // versionInfo : 버전 정보 /
        // isSavedBaseEvent : 기본 종목이 저장 되었냐? / true or false
        // isKeptLoggedIn : 로그인을 유지할 거냐? / true or false
        // isGrantedLocationPermission : 위치 권한을 승인 했냐? / true or false
        // isGrantedBackgroundLocationPermission : 백그라운드 위치 권한을 승인 했냐? / true or false
        // isAllowedAccessNotification : 피트니스 센터 지오펜싱을 활성화 했냐? / true or fasle
        // isDisclosed : 피트니스 센터를 공개할 거냐? / true or false

        boolean isFinishedInitialization = PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(context.getString(R.string.preference_key_isFinishedInitialization), false);
        int userNumber = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(context.getString(R.string.preference_key_user_number), 0);
        boolean isSavedBaseEvent = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_saved_base_event), false);
        boolean isKeptLoggedIn = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_kept_logged_in), false);
        boolean isGrantedLocationPermission = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_granted_location_permission), false);
        boolean isGrantedBackgroundLocationPermission = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_granted_background_location_permission), false);
        boolean isAllowedAccessNotification = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_allowed_access_notification), false);
        boolean isDisclosed = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_disclosed), false);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isFinishedInitialization = " + isFinishedInitialization);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< int > userNumber = " + userNumber);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isSavedBaseEvent = " + isSavedBaseEvent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isKeptLoggedIn = " + isKeptLoggedIn);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isGrantedLocationPermission = " + isGrantedLocationPermission);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isGrantedBackgroundLocationPermission = " + isGrantedBackgroundLocationPermission);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isAllowedAccessNotification = " + isAllowedAccessNotification);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isDisclosed = " + isDisclosed);

    }

    public static void initSetting(Context context) {
        final String METHOD_NAME = "[initSetting] ";

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();

        // is finished initialization
        editor.putBoolean(
                context.getString(R.string.preference_key_isFinishedInitialization),
                false
        );

        // user number
        editor.putInt(
                context.getString(R.string.preference_key_user_number),
                0
        );

        // is saved base event
        editor.putBoolean(
                context.getString(R.string.preference_key_is_saved_base_event),
                false
        );

        // is kept logged in
        editor.putBoolean(
                context.getString(R.string.preference_key_is_kept_logged_in),
                false
        );

        // is granted location permission
        editor.putBoolean(
                context.getString(R.string.preference_key_is_granted_location_permission),
                false
        );

        // is granted background location permission
        editor.putBoolean(
                context.getString(R.string.preference_key_is_granted_background_location_permission),
                false
        );

        // is allowed access notification
        editor.putBoolean(
                context.getString(R.string.preference_key_is_allowed_access_notification),
                false
        );

        // is disclosed
        editor.putBoolean(
                context.getString(R.string.preference_key_is_disclosed),
                false
        );

        editor.commit();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< SharedPreference.Editor > commit");
    }
}
