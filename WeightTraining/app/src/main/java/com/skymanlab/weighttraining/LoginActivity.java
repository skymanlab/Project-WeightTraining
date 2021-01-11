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
import com.skymanlab.weighttraining.management.project.activity.LSectionManager;

public class LoginActivity extends AppCompatActivity {

    // constant
    public static final int RC_SIGN_IN = 9000;
    // constant
    private static final String CLASS_NAME = "[Ac] LoginActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // instance variable
    private LSectionManager sectionManager;

    // instance variable
    private FirebaseAuth mAuth;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // [iv/C]GoogleSignInOptions : google sign-in 하기 위한 설정
        this.googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // [iv/C]GoogleSignInClient : google sign-in 설정한 값으로 클라이언트 만든다.
        this.googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // [iv/C]FirebaseAuth : FirebaseAuth 초기화
        this.mAuth = FirebaseAuth.getInstance();

        // [iv/C]LSectionManager : Login section manager / widget mapping & init
        this.sectionManager = new LSectionManager(this, googleSignInClient);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // [check 1] : Activity 결과로 넘어온 값 확인
        if (requestCode == RC_SIGN_IN) {

            // [lv/C]Task : 최초 인증을 통해 Intent 로 넘어온 결과를 이용하여 idToken 가져오기
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // [lv/C]GoogleSignInAccount : 위 결과를 가져오기
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //[method] : GoogleSignInAccount 객체를 통해 idToken 을 가져와서 다음 과정을 진행한다.
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                e.printStackTrace();

                // [method] : 인증 실패
                updateUI(null);
            }

        } // [check 1]
    }

    @Override
    protected void onStart() {
        super.onStart();

        final String METHOD_NAME = "[onStart] ";

        // [iv/C]FirebaseUser : FirebaseAuth 객체를 통해 연결된 user 를 가져온다. / 최초 인증이 완료 되면 FirebaseAuth 를 통해 user 정보를 가져올 수 있다.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // [method] : 위에서 user 객체를 가져온 값으로 다음 화면 정하기
        updateUI(currentUser);

        if (currentUser != null)
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~ firebase user = " + currentUser.getUid());

    }


    /**
     * [method] 사용자가 정상적으로 로그인(최초 인증)하면 GoogleSignInAccount 객체에서 id_token 을 가져와서 Firebase 사용자 인증 정보로 교환하고 해당 정보를 사용해 Firebase 에 이증합니다.
     */
    private void firebaseAuthWithGoogle(String idToken) {

        final String METHOD_NAME = "[firebaseAuthWithGoogle] ";


        // [lv/C]AuthCredential : idToken 으로 google 의 인증 증명하기 위한 auth_credential 을 가져오기
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        // [iv/C]FirebaseAuth : GoogleAuthProvider 을 가지고 FirebaseAuth 으로 로그인하기 / 로그인이 완료되었을 때 리스너를 통해(OnCompleteListener<AuthResult>) 다음 과정 진행하기
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // [check 1] : 로그인을 진행하는 Thread 의 결과를 확인
                        if (task.isSuccessful()) {
                            // 로그인 성공 / 인증 성공
                            // [lv/C]FirebaseUser : FirebaseAuth 를 통해 user 정보 가져오기 
                            FirebaseUser user = mAuth.getCurrentUser();
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>> 처음 인증");
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------> firebase user = " + user.getUid());

                            // [method] : user 가 있으면 HomeActivity 로 이동하는
                            updateUI(user);

                        } else {
                            // 로그인 실패 / 인증 실패

                            // [method] : 인증 실패
                            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();

                        } // [check 1]
                    }
                });

    } // End of method [firebaseAuthWithGoogle]


    /**
     * [method] FirebaseAuth 를 통해 가져온 FirebaseUser 를 확인하여 다음 화면으로 넘어가기
     */
    private void updateUI(FirebaseUser user) {

        final String METHOD_NAME = "[updateUI] ";

        // [check 1] : FirebaseUser 객체가 있을 때
        if (user != null) {
            // user 객체가 있다. / 해당 Google 인증을 통해 Firebase 인증을 성공했다. / 로그인 성공

            // [lv/C]Intent : HomeActivity 로 이동하기 위한 Intent 객체 생성 / LoginActivity 종료 / HomeActivity 로 이동
            Intent intent = new Intent(this, NavHomeActivity.class);
            finish();
            startActivity(intent);

        } else {
            // user 객체가 없다. / 인증 실패 / 로그인 실패
        } // [check 1]

    } // End of method [updateUI]


}