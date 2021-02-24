package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.skymanlab.weighttraining.HomeActivity;
import com.skymanlab.weighttraining.LoginActivity;
import com.skymanlab.weighttraining.NavHomeActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import javax.net.ssl.SNIHostName;

public class AuthenticationManager {

    // constant
    private static final String CLASS_NAME = AuthenticationManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // constant
    public static final int RC_SIGN_IN = 9000;

    // instance variable
    private Activity activity;

    // instance variable
    private FirebaseAuth mAuth;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    // constructor
    public AuthenticationManager(Activity activity) {
        this.activity = activity;
    }

    public void initAuthProcedure() {

        // [iv/C]GoogleSignInOptions : google sign-in 하기 위한 설정
        this.googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // [iv/C]GoogleSignInClient : google sign-in 설정한 값으로 클라이언트 만든다.
        this.googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);

        // [iv/C]FirebaseAuth : FirebaseAuth 초기화
        this.mAuth = FirebaseAuth.getInstance();

    }

    public void setOnActivityResultOfLifeCycle(Intent data) {
        final String METHOD_NAME = "[setOnActivityResultOfLifeCycle] ";


        // [lv/C]Task : 최초 인증을 통해 Intent 로 넘어온 결과를 이용하여 idToken 가져오기
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            // [lv/C]GoogleSignInAccount : 위 결과를 가져오기
            GoogleSignInAccount account = task.getResult(ApiException.class);

            //[method] : GoogleSignInAccount 객체를 통해 idToken 을 가져와서 다음 과정을 진행한다.
            firebaseAuthWithGoogle(account.getIdToken());

        } catch (ApiException e) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "Google sign in failed ");
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< ApiException >  = " + e.getLocalizedMessage());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< ApiException >  = " + e.getMessage());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< ApiException >  = " + e.getStatus());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< ApiException >  = " + e.getStatusCode());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< ApiException >  = " + e.getCause());
            e.printStackTrace();

            if (e.getStatusCode() == GoogleSignInStatusCodes.NETWORK_ERROR) {
                Snackbar.make(activity.findViewById(R.id.login_google_auth), R.string.etc_authentication_snack_ApiException_network_error, Snackbar.LENGTH_SHORT).show();
            }

            // [method] : 인증 실패
            updateUI(null);
        }

    }

    public void setOnStartOfLifeCycle() {

        // [iv/C]FirebaseUser : FirebaseAuth 객체를 통해 연결된 user 를 가져온다. / 최초 인증이 완료 되면 FirebaseAuth 를 통해 user 정보를 가져올 수 있다.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // [method] : 위에서 user 객체를 가져온 값으로 다음 화면 정하기
        updateUI(currentUser);
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
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------- onComplete ---------------------------->> ");

                        // [check 1] : 로그인을 진행하는 Thread 의 결과를 확인
                        if (task.isSuccessful()) {
                            // 로그인 성공 / 인증 성공 ( sign in with credential : success )
                            // [lv/C]FirebaseUser : FirebaseAuth 를 통해 user 정보 가져오기
                            FirebaseUser user = mAuth.getCurrentUser();

                            // [method] : user 가 있으면 HomeActivity 로 이동하는
                            updateUI(user);

                        } else {
                            // 로그인 실패 / 인증 실패 ( If sign in fails, display a message to the user. )

                            // [method] : 인증 실패
                            Snackbar.make(activity.getCurrentFocus(), R.string.etc_authentication_snack_authentication_failed, Snackbar.LENGTH_SHORT).show();

                        } // [check 1]
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----------------------------- onFailure --------------------------->> ");
                        e.printStackTrace();

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
            Intent intent = new Intent(activity, NavHomeActivity.class);
            activity.finish();
            activity.startActivity(intent);

        } else {
            // user 객체가 없다. / 인증 실패 / 로그인 실패
        } // [check 1]

    } // End of method [updateUI]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 로그인 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] GoogleSignInClient 를 통해 인증화면으로 이동  -> 결과를 Intent 로 받아오기
     */
    public void singIn(boolean isKeepLoggedIn) {

        // [lv/C]Intent : Google 인증을 위한 화면으로 이동
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

        // SharedPreference 에 해당 설정 저장하기
        SettingsManager.setIsKeptLoggedIn(activity, isKeepLoggedIn );

    } // End of method [singIn]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 로그아웃 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] App logout
     */
    public static void signOut() {

        // [lv/C]FirebaseAuth : signOut 을 호출하여 사용자를 로그아웃한다.
        FirebaseAuth.getInstance().signOut();

    } // End of method [logoutUser]


//    public static void signOutFromOnFinish(Activity activity) {
//        final String METHOD_NAME = "[signOutFromOnFinish] ";
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 인증 관리자 > 인증 관리자의 signOut 기능을 실행중압니다.");
//
//        // '로그인 유지' 설정이 false 일 때는 로그아웃 하기
//        if (!SettingsManager.checkIsKeptLoggedIn(activity)) {
//            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< keepLoggedIn > setting 에서 로그인 유지 설정이 false 입니다.");
//            signOut();
//        }
//    }

    /**
     * [method] App logout
     */
    public static void showDialogOfSignOut(Activity activity) {

        // [lv/C]AlertDialog : Builder 객체 생성 / 초기 설정
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.etc_authentication_alert_logout_user_title)
                .setMessage(R.string.etc_authentication_alert_logout_user_message)
                .setPositiveButton(R.string.etc_authentication_alert_logout_user_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]FirebaseAuth : signOut 을 호출하여 사용자를 로그아웃한다.
                        FirebaseAuth.getInstance().signOut();

                        // [lv/C]Snackbar : 로그아웃 완료 메시지
                        Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), "로그아웃이 완료되었습니다.", Snackbar.LENGTH_SHORT).show();

                        // [lv/C]Intent : LoginActivity 로 이동하는 객체 생성
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.finish();
                        activity.startActivity(intent);

                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.etc_authentication_alert_logout_user_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


    } // End of method [logoutUser]

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 회원탈퇴 하기 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] App withdraw : Firebase 에 등록된 사용자를 삭제한다.
     */
    public static void showDialogOfWithdraw(Activity activity) {

        // [lv/C]AlertDialog : Builder 객체 생성 / 초기 설정
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.etc_authentication_alert_withdraw_user_title)
                .setMessage(R.string.etc_authentication_alert_withdraw_user_message)
                .setPositiveButton(R.string.etc_authentication_alert_withdraw_user_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]FirebaseUser :
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                // [check 1] : task 의 성공여부를 받는다.
                                if (task.isSuccessful()) {

                                    // [lv/C]Snackbar : 탈퇴 완료 메시지
                                    Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), "탈퇴가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();

                                    // [lv/C]Intent : LoginActivity 로 이동하는 객체 생성
                                    Intent intent = new Intent(activity, LoginActivity.class);
                                    activity.finish();
                                    activity.startActivity(intent);

                                    dialog.dismiss();
                                } // [check 1]
                            }
                        });

                    }
                })
                .setNegativeButton(R.string.etc_authentication_alert_withdraw_user_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    } // End of method [withdrawUser]


    public String getGoogleSingInStatusCode(int statusCode) {

        String errorStatusCode = null;
        switch (statusCode) {
            case GoogleSignInStatusCodes.SUCCESS_CACHE:
                // -1
                errorStatusCode = "SUCCESS_CACHE";
                break;

            case GoogleSignInStatusCodes.SUCCESS:
                // 0
                errorStatusCode = "SUCCESS";
                break;

            case GoogleSignInStatusCodes.SIGN_IN_REQUIRED:
                // 4
                errorStatusCode = "SIGN_IN_REQUIRED";
                break;

            case GoogleSignInStatusCodes.INVALID_ACCOUNT:
                // 5
                errorStatusCode = "INVALID_ACCOUNT";
                break;

            case GoogleSignInStatusCodes.RESOLUTION_REQUIRED:
                // 6
                errorStatusCode = "RESOLUTION_REQUIRED";
                break;

            case GoogleSignInStatusCodes.NETWORK_ERROR:
                // 7
                errorStatusCode = "NETWORK_ERROR";
                break;

            case GoogleSignInStatusCodes.INTERNAL_ERROR:
                // 8
                errorStatusCode = "INTERNAL_ERROR";
                break;

            case GoogleSignInStatusCodes.DEVELOPER_ERROR:
                // 10
                errorStatusCode = "DEVELOPER_ERROR";
                break;

            case GoogleSignInStatusCodes.ERROR:
                // 13
                errorStatusCode = "ERROR";
                break;

            case GoogleSignInStatusCodes.INTERRUPTED:
                // 14
                errorStatusCode = "INTERRUPTED";
                break;

            case GoogleSignInStatusCodes.TIMEOUT:
                // 15
                errorStatusCode = "TIMEOUT";
                break;

            case GoogleSignInStatusCodes.CANCELED:
                // 16
                errorStatusCode = "CANCELED";
                break;

            case GoogleSignInStatusCodes.API_NOT_CONNECTED:
                // 17
                errorStatusCode = "API_NOT_CONNECTED";
                break;

            case GoogleSignInStatusCodes.REMOTE_EXCEPTION:
                // 19
                errorStatusCode = "REMOTE_EXCEPTION";
                break;

            case GoogleSignInStatusCodes.CONNECTION_SUSPENDED_DURING_CALL:
                // 20
                errorStatusCode = "CONNECTION_SUSPENDED_DURING_CALL";
                break;

            case GoogleSignInStatusCodes.RECONNECTION_TIMED_OUT_DURING_UPDATE:
                // 21
                errorStatusCode = "RECONNECTION_TIMED_OUT_DURING_UPDATE";
                break;

            case GoogleSignInStatusCodes.RECONNECTION_TIMED_OUT:
                // 22
                errorStatusCode = "RECONNECTION_TIMED_OUT";
                break;

            case GoogleSignInStatusCodes.SIGN_IN_CANCELLED:
                // 12501
                errorStatusCode = "SIGN_IN_CANCELLED";
                break;

            case GoogleSignInStatusCodes.SIGN_IN_CURRENTLY_IN_PROGRESS:
                // 12502
                errorStatusCode = "SIGN_IN_CURRENTLY_IN_PROGRESS";
                break;

            case GoogleSignInStatusCodes.SIGN_IN_FAILED:
                // 12500
                errorStatusCode = "SIGN_IN_FAILED";
                break;

        }

        return errorStatusCode;
    }
}
