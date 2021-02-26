package com.skymanlab.weighttraining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SettingsManager;

public class MainActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] MainActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ImageView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> MainActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]ImageView : log mapping / visible, fadein, fadeout
        this.log = (ImageView) findViewById(R.id.main_log);
        this.log.setVisibility(ImageView.VISIBLE);
        this.log.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein));

        SettingsManager.displayAllSettingsValue(this);

        // [lv/C]MoveEnrollActivity : 6초 후 EnrollActivity 이동
        MoveActivity move = new MoveActivity(getApplicationContext());
        move.start();


    } // End of method [onCreate]


    /**
     * [innerClass] user table 에서
     */
    class MoveActivity extends Thread {

        // instance variable
        private Context context;

        // constructor
        public MoveActivity(Context context) {
            this.context = context;
        }

        @Override
        public void run() {

            final String METHOD_NAME = "[run] ";

            Class nextActivity = null;

            if (SettingsManager.checkIsFinishedInitialization(context)) {
                // 초기화 완료
                nextActivity = LoginActivity.class;
            } else {
                // 초기화 아직 안함
                nextActivity = InitialInstallActivity.class;
            }

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 선택된 클래스 > 이름 = " + nextActivity.getSimpleName());
            try {

                // [method] : 3초 지연
                sleep(2000);

                // [lv/C]Intent : targetActivity 이동하는 intent 생성 및 이동
                Intent intent = new Intent(this.context, nextActivity);
                finish();
                startActivity(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } // End of method [run]

    } // End of innerClass
}