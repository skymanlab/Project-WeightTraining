package com.skymanlab.weighttraining.management.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.skymanlab.weighttraining.LoginActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.AuthenticationManager;

public class LSectionManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PA] LSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private AuthenticationManager authenticationManager;

    // instance variable
    private SignInButton googleAuth;
    private CheckBox keepLoggedInChecker;

    // constructor
    public LSectionManager(Activity activity, AuthenticationManager authenticationManager) {
        super(activity);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void connectWidget() {

        // [iv/C]Button : googleAuth connect
        this.googleAuth = (SignInButton) getActivity().findViewById(R.id.login_google_auth);

        // [ CheckBox | keepLoggedInChecker ]
        this.keepLoggedInChecker = (CheckBox) getActivity().findViewById(R.id.login_keepLoggedInChecker);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> Login Section Manager 실행");

        // [iv/C]Button : googleAuth click listener
        this.googleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> Google Sign-In Button 클릭!");

                // [method] : 로그인 과정 진행
                authenticationManager.singIn(
                        keepLoggedInChecker.isChecked()     // checkBox 의 체크 상태를 가져오기
                );
            }
        });

    }

}
