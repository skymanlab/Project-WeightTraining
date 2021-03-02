package com.skymanlab.weighttraining.management.project.fragment.More;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionResultManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionUtil;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    // constant
    private static final String CLASS_NAME = "[AcInner]SettingsFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private FragmentTopBarManager topBarManager;

    // instance variable : app
    private Preference versionInfo;
    private Preference isSavedBaseEvent;
    private SwitchPreference isKeptLoggedIn;

    // instance variable : location
    private Preference isGrantedLocationPermission;
    private Preference isGrantedBackgroundLocationPermission;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreateView] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================================================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "inflater = " + inflater);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "container = " + container);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "savedInstanceState = " + savedInstanceState);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String METHOD_NAME = "[onViewCreated] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================================================================");

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "view = " + view);

        TextView title = view.findViewById(R.id.include_top_bar_title);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< title > = " +title);


    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        final String METHOD_NAME = "[onCreatePreferences] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================ 설정 플래그 먼트 =====================" +
                "");
        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-= Category : app =-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // version info
        initSettingOfVersionInfo();

        // base event data
        initSettingOfBaseEventData();

        // keep logged in
        initSettingOfKeepLoggedIn();

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-= Category : location =-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // location permission
        initSettingOfLocationPermission();

        // background location permission
        initSettingOfBackgroundLocationPermission();

    }


    /**
     * Version Info
     */
    private void initSettingOfVersionInfo() {

        // [Preference] [versionInfo] 버전 정보에 대한 설정 항목
        this.versionInfo = findPreference(getString(R.string.preference_key_version_info));

    } // End of Method [initSettingOfVersionInfo]

    /**
     * Base Event Data
     */
    private void initSettingOfBaseEventData() {

        // [Preference] [isSavedBaseEvent] 기본 종목 데이터에 대한 설정 항목
        this.isSavedBaseEvent = findPreference(getString(R.string.preference_key_is_saved_base_event));

        // [check 1] : basicEventDataContent 의 값이 뭐냐? / 저장 안 함 = false, 저장 함 = true
        if (this.isSavedBaseEvent.getSharedPreferences()
                .getBoolean(
                        getString(R.string.preference_key_is_saved_base_event),
                        false
                )) {

            // [iv/C]Preference : summary 를 R.string.preference_category_app_basic_event_data_summary_two 으로 변경하기
            this.isSavedBaseEvent.setSummary(R.string.preference_category_app_basic_event_data_summary_two);
            this.isSavedBaseEvent.setSelectable(false);
            this.isSavedBaseEvent.setEnabled(false);

        } // [check 1]

    } // End of Method [initSettingOfBaseEventData]


    /**
     * Keep Logged In
     */
    private void initSettingOfKeepLoggedIn() {

        // [SwitchPreference] [isKeptLoggedIn] 로그인 유지 기능에 대한 설정 항목
        this.isKeptLoggedIn = findPreference(getString(R.string.preference_key_is_kept_logged_in));

    } // End of Method [initSettingOfKeepLoggedIn]


    /**
     * Location Permission
     */
    private void initSettingOfLocationPermission() {

        // [Preference] [isGrantedLocationPermission] 위치 권한에 대한 설정 항목
        this.isGrantedLocationPermission = findPreference(getString(R.string.preference_key_is_granted_location_permission));

        setLocationPermissionState();

    } // End of Method [initSettingOfLocationPermission]


    /**
     * Background Location Permission
     */
    private void initSettingOfBackgroundLocationPermission() {
        final String METHOD_NAME = "[initSettingOfBackgroundLocationPermission] ";

        // [Preference] [backgroundLocationPermission] 백그라운드 위치 권한에 대한 설정 항목
        this.isGrantedBackgroundLocationPermission = findPreference(getString(R.string.preference_key_is_granted_background_location_permission));

        setBackgroundLocationPermissionState();

    } // End of Method [initSettingOfBackgroundLocationPermission]


    /**
     * 어플리케이션의 Location Permission  의 상태에 따른 화면 설정
     */
    public void setLocationPermissionState() {
        final String METHOD_NAME = "[setLocationPermissionState] ";

        // [check 1] Location Permission : 승인이 되었는지 확인하기
        if (PermissionUtil.hasPermissionList(getActivity(), PermissionManager.LOCATION_PERMISSION_LIST)) {

            // Location Permission : 비활성화 상태 / 요약 : '승인이 완료되었다'는 문구로
            this.isGrantedLocationPermission.setSummary(R.string.preference_category_location_permission_granted);
            this.isGrantedLocationPermission.setEnabled(false);

        } else {

            // Location Permission : 비활성화 상태 / 요약 : '거부 되었다'는 문구로 / click listener : 권한 요청하기
            this.isGrantedLocationPermission.setSummary(R.string.preference_category_location_permission_denied);
            this.isGrantedLocationPermission.setEnabled(true);
            this.isGrantedLocationPermission.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    // requestCode : LOCATION_PERMISSION_REQUEST_CODE
                    PermissionUtil.requestPermission(
                            getActivity(),
                            PermissionResultManager.LOCATION_PERMISSION_REQUEST_CODE,
                            PermissionManager.LOCATION_PERMISSION_LIST);


                    return true;
                }
            });

        } // [check 1]

    } // End of Method [setLocationPermissionState]


    /**
     * 어플리케이션의 Background location permission 의 상태에 따른 화면 설정
     */
    public void setBackgroundLocationPermissionState() {
        final String METHOD_NAME = "[setBackgroundLocationPermissionState] ";

        // [check 1] Location permission 이 아직 승인되지 않았습니다.
        if (!PermissionUtil.hasPermissionList(getContext(), PermissionManager.LOCATION_PERMISSION_LIST)) {

            // Background Location Permission : 비활성화 상태 / 요약 : '위치 권한이 먼저 승인되어야 한다.'는 문구로
            this.isGrantedBackgroundLocationPermission.setSummary(R.string.preference_category_location_background_permission_not_enable);
            this.isGrantedBackgroundLocationPermission.setEnabled(false);

            return;
        } // [check 1]


        // [check 2] Background Location Permission : 승인이 되었는지 확인
        if (PermissionUtil.hasPermissionList(getContext(), PermissionManager.BACKGROUND_PERMISSION)) {

            // Background Location Permission : 비활성화 상태 / 요약 : '승인이 완료되었다'는 문구로
            this.isGrantedBackgroundLocationPermission.setSummary(R.string.preference_category_location_background_permission_granted);
            this.isGrantedBackgroundLocationPermission.setEnabled(false);

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================> 거부 되었음");

            // [check 3]  Background Location Permission : 사용자가 거부한 적이 있는지 확인
            if (PermissionUtil.shouldShowRequestPermissionListRationale(getActivity(), PermissionManager.BACKGROUND_PERMISSION)) {

                // Background Location Permission : 비활성화 상태 / 요약 : '사용자가 거부하였다'는 문구로 / Click listener : 어플리케이션 설정 화면으로 이동
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Background Location Permission > 사용자가 거부하였음.");
                this.isGrantedBackgroundLocationPermission.setSummary(R.string.preference_category_location_background_permission_user_denied);
                this.isGrantedBackgroundLocationPermission.setEnabled(true);
                this.isGrantedBackgroundLocationPermission.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.preference_category_location_background_permission_alert_describe_to_user_title)
                                .setMessage(R.string.preference_category_location_background_permission_alert_describe_to_user_message)
                                .setPositiveButton(R.string.preference_category_location_background_permission_alert_describe_to_user_bt_positive, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // requestCode : BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE / 직접 어플 설정화면으로 이동 -> 위치 -> 권한 -> 항상 허용으로 변경
                                        PermissionUtil.requestApplicationSetting(getActivity(), PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE);

                                    }
                                })
                                .setNegativeButton(R.string.preference_category_location_background_permission_alert_describe_to_user_bt_negative, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                        return true;
                    }
                });


            } else {

                // Background Location Permission : 아직 거부한 적이 없음
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Background Location Permission > 아직 거부한 적 없음");

                // summary -> denied 요약문으로 변경 / 확성화 -> true / click -> Location Permission 이 '항상 허용'으로 변경해야합니다.
                this.isGrantedBackgroundLocationPermission.setSummary(R.string.preference_category_location_background_permission_denied);
                this.isGrantedBackgroundLocationPermission.setEnabled(true);
                this.isGrantedBackgroundLocationPermission.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Preference > 클릭한 preference 는 ? = " + preference);
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Preference > =================>");

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.preference_category_location_background_permission_alert_describe_to_user_title)
                                .setMessage(R.string.preference_category_location_background_permission_alert_describe_to_user_message)
                                .setPositiveButton(R.string.preference_category_location_background_permission_alert_describe_to_user_bt_positive, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // requestCode : BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE
                                        PermissionUtil.requestPermission(
                                                getActivity(),
                                                PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE,
                                                PermissionManager.BACKGROUND_PERMISSION
                                        );
                                    }
                                })
                                .setNegativeButton(R.string.preference_category_location_background_permission_alert_describe_to_user_bt_negative, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                        return true;
                    }
                });

            } // [check 3]
        } // [check 2]

    } // End of Method [setBackgroundLocationPermissionState]

}
