package com.skymanlab.weighttraining;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.BaseEventDataManager;

public class SettingsActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] SettingsActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        // constant
        private static final String CLASS_NAME = "[AcInner]_SettingFragment";
        private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

        // instance variable
        private Preference basicEventData;
        private Preference user;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final String METHOD_NAME = "[onCreatePreferences] ";

            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= basic_event_data =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // [iv/C]Preference : key 가 basic_event_data 인 설정 목록 가져오기 / 설정되어 있는 값 가져오기
            this.basicEventData = findPreference("base_event_data");
            String basicEventDataContent = this.basicEventData.getSharedPreferences().getString("base_event_data", "");

            // [check 1] : basicEventDataContent 의 값이 뭐냐? / 저장 안 함 = false, 저장 함 = true
            if (basicEventDataContent.equals("true")) {

                // [iv/C]Preference : summary 를 R.string.preference_category_app_basic_event_data_summary_two 으로 변경하기
                this.basicEventData.setSummary(R.string.preference_category_app_basic_event_data_summary_two);
                this.basicEventData.setSelectable(false);
                this.basicEventData.setEnabled(false);

            } // [check 1]



            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= user_info =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            this.user = findPreference("user_info");
            String userInfo = this.user.getSharedPreferences().getString("user_info", "");

            // log 로 확인
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>> basic event dat >>>>>>>>>>>>>>> = " + basicEventDataContent);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>> user info value  = " + userInfo);

        }

    }
}