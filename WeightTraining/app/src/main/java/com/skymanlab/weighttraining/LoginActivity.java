package com.skymanlab.weighttraining;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.AuthenticationManager;
import com.skymanlab.weighttraining.management.project.activity.LSectionManager;

public class LoginActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] LoginActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // instance variable
    private LSectionManager sectionManager;

    // instance variable : 인증관련
    private AuthenticationManager authenticationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // [AuthenticationManager] firebase 의 auth api 를 이용하여 google 인증하기
        this.authenticationManager = new AuthenticationManager(this);
        this.authenticationManager.initAuthProcedure();

        // [iv/C]LSectionManager : Login section manager / widget mapping & init
        this.sectionManager = new LSectionManager(this, this.authenticationManager);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // [check 1] : Activity 결과로 넘어온 값 확인
        if (requestCode == AuthenticationManager.RC_SIGN_IN) {

            this.authenticationManager.setOnActivityResultOfLifeCycle(data);

        } // [check 1]
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String METHOD_NAME = "[onStart] ";

        this.authenticationManager.setOnStartOfLifeCycle();

    }
}