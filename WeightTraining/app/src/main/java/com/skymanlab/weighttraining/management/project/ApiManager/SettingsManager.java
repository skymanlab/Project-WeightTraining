package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

public class SettingsManager {

    // constant
    private static final String CLASS_NAME = SettingsManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= initial setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void setInitialSetting(Context context, boolean isInitialSetting) {
        final String METHOD_NAME = "[setInitialSetting] ";

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(context.getString(R.string.preference_key_initial_setting), isInitialSetting)
                .commit();
    }

    public static boolean checkInitialSetting(Context context) {
        final String METHOD_NAME = "[checkInitialSetting] ";

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_initial_setting), false);

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= keep logged in =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void setKeepLoggedIn(Activity activity, boolean isCheckable) {

        PreferenceManager.getDefaultSharedPreferences(activity)
                .edit()
                .putBoolean(activity.getString(R.string.preference_key_keep_logged_in), isCheckable)
                .commit();
    }


    /**
     * '로그인 유지' 기능이 true 인지 false 인지 구분하여
     *
     * @param activity
     * @return
     */
    public static boolean checkKeepLoggedIn(Activity activity) {
        final String METHOD_NAME = "[initKeepLoggedIn] ";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        boolean isCheckableOfKeepLoggedIn = PreferenceManager.getDefaultSharedPreferences(activity)
                .getBoolean(activity.getString(R.string.preference_key_keep_logged_in), false);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< keepLoggedIn > 첫 로그인 유지 설정 확인 중 = " + isCheckableOfKeepLoggedIn);

        // [check 1] '설정' 에서 로그인 유지를 true 로 했다면...
        if (isCheckableOfKeepLoggedIn) {

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                return true;
            }
        }
        return false;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= is disclosed fitness center =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void setIsDisclosedFitnessCenter(Context context, boolean isDisclosed) {
        final String METHOD_NAME = "[setInitialSetting] ";

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(context.getString(R.string.preference_key_is_disclosed_fitness_center), isDisclosed)
                .commit();
    }

    public static boolean getIsDisclosedFitnessCenter(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.preference_key_is_disclosed_fitness_center), false);
    }
}
