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
    public static boolean checkIsInitialInstallation(Context context) {
        final String METHOD_NAME = "[checkIsInitialInstallation]";

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(
                        context.getString(R.string.preference_key_is_initial_installation),
                        false
                );

    }

    public static void setIsInitialInstallation(Context context, boolean isInitialInstallation) {
        final String METHOD_NAME = "[setIsInitialInstallation] ";

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(
                        context.getString(R.string.preference_key_is_initial_installation),
                        isInitialInstallation
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
                        context.getString(R.string.preference_key_is_disclosed_fitness_center),
                        false
                );

    }

    public static void setIsDisclosedFitnessCenter(Context context, boolean isDisclosed) {
        final String METHOD_NAME = "[setInitialSetting] ";

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(
                        context.getString(R.string.preference_key_is_disclosed_fitness_center),
                        isDisclosed
                )
                .commit();
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // 모든 설정값 확인
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    public static void displayAllSettingsValue(Context context) {
        final String METHOD_NAME = "[displayAllSettingsValue] ";

        // isInitialAuthentication : 최초 인증이냐? / true or false
        // userNumber : 유저 번호 / number
        // versionInfo : 버전 정보 /
        // isSavedBaseEvent : 기본 종목이 저장 되었냐? / true or false
        // isKeptLoggedIn : 로그인을 유지할 거냐? / true or false
        // isGrantedLocationPermission : 위치 권한을 승인 했냐? / true or false
        // isGrantedBackgroundLocationPermission : 백그라운드 위치 권한을 승인 했냐? / true or false
        // isEnabledFitnessCenterGeofencing : 피트니스 센터 지오펜싱을 활성화 했냐? / true or fasle
        // isDisclosedFitnessCenter : 피트니스 센터를 공개할 거냐? / true or false

        boolean isInitialInstallation = PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(context.getString(R.string.preference_key_is_initial_installation), false);
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
        boolean isEnabledFitnessCenterGeofencing = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_enabled_fitness_center_geofencing), false);
        boolean isDisclosedFitnessCenter = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_disclosed_fitness_center), false);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isInitialInstallation = " + isInitialInstallation);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< int > userNumber = " + userNumber);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isSavedBaseEvent = " + isSavedBaseEvent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isKeptLoggedIn = " + isKeptLoggedIn);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isGrantedLocationPermission = " + isGrantedLocationPermission);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isGrantedBackgroundLocationPermission = " + isGrantedBackgroundLocationPermission);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isEnabledFitnessCenterGeofencing = " + isEnabledFitnessCenterGeofencing);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > isDisclosedFitnessCenter = " + isDisclosedFitnessCenter);

    }

    public static void initSetting(Context context) {
        final String METHOD_NAME = "[initSetting] ";

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();

        // is initial installation
        editor.putBoolean(
                context.getString(R.string.preference_key_is_initial_installation),
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

        // is enabled fitness center geofencing
        editor.putBoolean(
                context.getString(R.string.preference_key_is_enabled_fitness_center_geofencing),
                false
        );

        // is disclosed fitness center
        editor.putBoolean(
                context.getString(R.string.preference_key_is_disclosed_fitness_center),
                false
        );

        editor.commit();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< SharedPreference.Editor > commit");
    }
}
