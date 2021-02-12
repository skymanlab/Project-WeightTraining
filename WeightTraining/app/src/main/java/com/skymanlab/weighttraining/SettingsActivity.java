package com.skymanlab.weighttraining;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionUtil;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionResultManager;

public class SettingsActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] SettingsActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private TextView title;
    private ImageView startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // widget connect
        connectWidget();

        // widget init
        initWidget();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_preference_container, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final String METHOD_NAME = "[onRequestPermissionsResult] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< onActivityResult > requestCode = " + requestCode);


        PermissionResultManager permissionResultManager = new PermissionResultManager();

        switch (requestCode) {
            case PermissionResultManager.LOCATION_PERMISSION_REQUEST_CODE:
                // Location Permission 요청에 대한 결과 처리
                permissionResultManager.setNextProcedureForLocationPermissionRequestResult(
                        getSupportFragmentManager().findFragmentById(R.id.settings_preference_container),
                        permissions,
                        grantResults
                );
                break;

            case PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE:
                // Background Location Permission 요청에 대한 결과 치리
                permissionResultManager.setNextProcedureForBackgroundLocationPermissionRequestResult(
                        getSupportFragmentManager().findFragmentById(R.id.settings_preference_container)
                );

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String METHOD_NAME = "[initSettingOfLocationPermission] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< onActivityResult > requestCode = " + requestCode);

        switch (requestCode) {

            case PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Background Location Permission > 해당 요청에 대한 결과를 처리합니다.");
                // Background Location Permission 을 Application Detail Setting 에서 사용자가 설정한 결과에 대한 처리
                PermissionResultManager permissionResultManager = new PermissionResultManager();
                permissionResultManager.setNextProcedureForBackgroundLocationPermissionRequestResult(
                        getSupportFragmentManager().findFragmentById(R.id.settings_preference_container)
                );
                break;
        }

    }

    private void connectWidget() {

        this.title = (TextView) findViewById(R.id.include_top_bar_title);
        this.startButton = (ImageView) findViewById(R.id.include_top_bar_start_image);
    }

    private void initWidget() {

        // [TextView] [title] text
        this.title.setText(R.string.f_setting_title);

        // [ImageView] [startButton] VISIBLE / click listener
        this.startButton.setVisibility(View.VISIBLE);
        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= preference fragment =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class SettingsFragment extends PreferenceFragmentCompat {

        // constant
        private static final String CLASS_NAME = "[AcInner]SettingsFragment";
        private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

        // instance variable : app
        private Preference versionInfo;
        private Preference isSavedBaseEvent;
        private SwitchPreference isKeptLoggedIn;

        // instance variable : location
        private Preference isGrantedLocationPermission;
        private Preference isGrantedBackgroundLocationPermission;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final String METHOD_NAME = "[onCreatePreferences] ";

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


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 

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

                // [check 3]  Background Location Permission : 사용자가 거부한 적이 있는지 확인
                if (PermissionUtil.shouldShowRequestPermissionListRationale(getActivity(), PermissionManager.BACKGROUND_PERMISSION)) {

                    // Background Location Permission : 비활성화 상태 / 요약 : '사용자가 거부하였다'는 문구로 / Click listener : 어플리케이션 설정 화면으로 이동
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Background Location Permission > 사용자가 거부하였음.");
                    this.isGrantedBackgroundLocationPermission.setSummary(R.string.preference_category_location_background_permission_user_denied);
                    this.isGrantedBackgroundLocationPermission.setEnabled(true);
                    this.isGrantedBackgroundLocationPermission.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {

                            // requestCode : BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE / 직접 어플 설정화면으로 이동 -> 위치 -> 권한 -> 항상 허용으로 변경
                            PermissionUtil.requestApplicationSetting(getActivity(), PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE);

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

                            // requestCode : BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE
                            PermissionUtil.requestPermission(
                                    getActivity(),
                                    PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE,
                                    PermissionManager.BACKGROUND_PERMISSION
                            );

                            return true;
                        }
                    });

                } // [check 3]
            } // [check 2]

        } // End of Method [setBackgroundLocationPermissionState]

    } // End of SettingsFragment 

}