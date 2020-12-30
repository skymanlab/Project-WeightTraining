package com.skyman.weighttrainingpro;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;

public class SettingsActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_HomeActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

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
        private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

        // instance variable
        private Preference basicEventData;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final String METHOD_NAME = "[onCreatePreferences] ";

            // [iv/C]Preference : key 가 basic_event_data 인 설정 목록 가져오기 / 설정되어 있는 값 가져오기
            this.basicEventData = findPreference("basic_event_data");
            String basicEventDataContent = this.basicEventData.getSharedPreferences().getString("basic_event_data", "true");

            // [check 1] : basicEventDataContent 의 값이 뭐냐? ONE 또는 TWO
            if (basicEventDataContent.equals("TWO")) {

                // [iv/C]Preference : summary 를 R.string.preference_category_app_basic_event_data_summary_two 으로 변경하기
                this.basicEventData.setSummary(R.string.preference_category_app_basic_event_data_summary_two);
                this.basicEventData.setSelectable(false);
                this.basicEventData.setEnabled(false);

            } // [check 1]

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>> basic event dat >>>>>>>>>>>>>>> = " + basicEventDataContent);

        }

    }
}