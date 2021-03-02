
package com.skymanlab.weighttraining;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.AuthenticationManager;
import com.skymanlab.weighttraining.management.project.ApiManager.InitializationManager;
import com.skymanlab.weighttraining.management.project.ApiManager.NotificationManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SettingsManager;
import com.skymanlab.weighttraining.management.project.data.BaseEventDataManager;
import com.skymanlab.weighttraining.management.project.fragment.Home.HomeFragment;
import com.skymanlab.weighttraining.management.project.fragment.Intervene.InterventionFragment;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionResultManager;
import com.skymanlab.weighttraining.management.project.fragment.More.MoreFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.TrainingFragment;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.HashMap;

public class NavHomeActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    // constant
    private static final String CLASS_NAME = "[Ac] NavHomeActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private BottomNavigationView navBottomBar;

    // instance variable
    private HomeFragment homeFragment;
    private TrainingFragment trainingFragment;
    private InterventionFragment interveneFragment;
    private MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreate] ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++++++++++++++++++++++ NavHomeActivity +++++++++++++++++++++++++++++++++++++");

        // nav_home_content_wrapper 에 표시할 Fragment 
        this.homeFragment = HomeFragment.newInstance();
        this.trainingFragment = TrainingFragment.newInstance();
        this.interveneFragment = InterventionFragment.newInstance();
        this.moreFragment = MoreFragment.newInstance();

        // nav_home_content_wrapper 에 표시할 처음 Fragment 는 home 
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_home_content_wrapper, homeFragment)
                .commit();

        // [iv/C]Bo :
        navBottomBar = (BottomNavigationView) findViewById(R.id.nav_home_bottom_bar);
        navBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_bottom_bar_home:

                        // back stack 에서 제거
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        // homeFragment 로 변경
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_home_content_wrapper, homeFragment)
                                .commitAllowingStateLoss();
                        break;

                    case R.id.nav_bottom_bar_training:

                        // back stack 에서 제거
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        // trainingFragment 로 변경
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_home_content_wrapper, trainingFragment)
                                .commitAllowingStateLoss();
                        break;

//                    case R.id.nav_bottom_bar_intervene:
//
//                        // back stack 에서 제거
//                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//                        // interveneFragment 로 변경
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_home_content_wrapper, interveneFragment)
//                                .commitAllowingStateLoss();
//                        break;

                    case R.id.nav_bottom_bar_more:

                        // back stack 에서 제거
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        // interveneFragment 로 변경
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_home_content_wrapper, moreFragment)
                                .commitAllowingStateLoss();
                        break;

                }

                return true;
            }
        });

        // 알림 channel 등록
        NotificationManager notificationManager = new NotificationManager(this);
        notificationManager.createNotificationChannel();

        // 초기화 메니저로 초기화
        InitializationManager initializationManager = new InitializationManager(this);
        initializationManager.init();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------------------=====================================================================================");
    }


    @Override
    public void onBackPressed() {
        final String METHOD_NAME = "[onBackPressed] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++ FragmentTransaction  = " + getSupportFragmentManager().getBackStackEntryCount());

        if (0 < getSupportFragmentManager().getBackStackEntryCount()) {
            // back stack 가 비어 있지 않으므로

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> 0 보다 큼");
            super.onBackPressed();

        } else {
            // back stack 가 비어 있으므로 '종료' alert dialog 를 표시한다.

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 0 임");

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.home_alert_back_title)
                    .setMessage(R.string.home_alert_back_message)
                    .setPositiveButton(R.string.home_alert_back_bt_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // 종료
                            finish();

                        }
                    })
                    .setNegativeButton(R.string.home_alert_back_bt_negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

    } // End of method [onBackPressed]


    @Override
    protected void onDestroy() {
        final String METHOD_NAME = "[onDestroy] ";

        if (!SettingsManager.checkIsKeptLoggedIn(getApplicationContext())) {
            // '로그인 유지' 기능이 비활성화 되어 있으면
            // 로그아웃
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< isKeptLoggedIn > 로그아웃이 진행됩니다.");
            AuthenticationManager.signOut();
        }

        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        final String METHOD_NAME = "[onRequestPermissionsResult] ";
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> ---- request code = " + requestCode);

        PermissionResultManager permissionResultManager = new PermissionResultManager();

        switch (requestCode) {

            case PermissionResultManager.LOCATION_PERMISSION_REQUEST_CODE:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Location Permission Request > 해당 권한 요청 결과를 바탕으로 다음 과정을 진행하겠습니다.");
                permissionResultManager.setNextProcedureForLocationPermissionRequestResult(
                        getSupportFragmentManager().findFragmentById(R.id.nav_home_content_wrapper),
                        permissions,
                        grantResults
                );

                break;
            case PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===============================================================================> ");
                // Background Location Permission 요청에 대한 결과 치리
                permissionResultManager.setNextProcedureForBackgroundLocationPermissionRequestResult(
                        getSupportFragmentManager().findFragmentById(R.id.nav_home_content_wrapper)
                );

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final String METHOD_NAME = "[onActivityResult] ";
        super.onActivityResult(requestCode, resultCode, data);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> ---- onActivityResult request code = " + requestCode);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> ---- onActivityResult result code = " + resultCode);

        PermissionResultManager permissionResultManager = new PermissionResultManager();

        switch (requestCode) {

            case PermissionResultManager.LOCATION_SERVICE_REQUEST_CODE:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LOCATION_SERVICE_REQUEST_CODE > 위치 권한에 대한 결과를 반영합니다.");
                permissionResultManager.setNextProcedureForLocationServiceRequestResult(
                        getSupportFragmentManager().findFragmentById(R.id.nav_home_content_wrapper)
                );

                break;

            case PermissionResultManager.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE > 백그라운드 위치 권한에 대한 결과를 반영합니다.");
                // Background Location Permission 을 Application Detail Setting 에서 사용자가 설정한 결과에 대한 처리
                permissionResultManager.setNextProcedureForBackgroundLocationPermissionRequestResult(
                        getSupportFragmentManager().findFragmentById(R.id.nav_home_content_wrapper)
                );
                break;

        }
    }


}