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

public class LSectionManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PA] LSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private GoogleSignInClient googleSignInClient;

    // instance variable
    private SignInButton googleAuth;

    // constructor
    public LSectionManager(Activity activity, GoogleSignInClient googleSignInClient) {
        super(activity);
        this.googleSignInClient = googleSignInClient;
    }

    @Override
    public void connectWidget() {

        // [iv/C]Button : googleAuth connect
        this.googleAuth = (SignInButton) getActivity().findViewById(R.id.login_google_auth);

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
                singIn();
            }
        });

    }


    /**
     * [method] GoogleSignInClient 를 통해 인증화면으로 이동  -> 결과를 Intent 로 받아오기
     */
    private void singIn() {

        // [lv/C]Intent : Google 인증을 위한 화면으로 이동
        Intent signInIntent = googleSignInClient.getSignInIntent();
        getActivity().startActivityForResult(signInIntent, LoginActivity.RC_SIGN_IN);

    } // End of method [singIn]

}
