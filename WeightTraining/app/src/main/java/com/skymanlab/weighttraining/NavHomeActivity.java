package com.skymanlab.weighttraining;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.Home.HomeFragment;
import com.skymanlab.weighttraining.management.project.fragment.Intervene.InterveneFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.MoreFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.TrainingFragment;
import com.skymanlab.weighttraining.management.user.data.User;

public class NavHomeActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_NavHomeFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FirebaseUser firebaseUser;
    private User user;

    // instance variable
    private BottomNavigationView navBottomBar;

    // instance variable
    private HomeFragment home;
    private TrainingFragment training;
    private InterveneFragment intervene;
    private MoreFragment more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);

        final String METHOD_NAME = "[onCreate] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++++++++++++++++++++++ NavHomeActivity +++++++++++++++++++++++++++++++++++++");

        // [iv/C]FirebaseUser : user 정보 가져오기
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [iv/C]User : FirebaseUser 의 정보 중 displayName, email, photoUrl 을 담는다.
        this.user = new User();
        this.user.setName(this.firebaseUser.getDisplayName());
        this.user.setEmail(this.firebaseUser.getEmail());
        this.user.setPhotoUrl(this.firebaseUser.getPhotoUrl().toString());
        this.user.setUid(this.firebaseUser.getUid());

        // [iv/C]Fragment : 각 Fragment 생성
        this.home = HomeFragment.newInstance(this.user);
        this.training = new TrainingFragment();
        this.intervene = new InterveneFragment();
        this.more = MoreFragment.newInstance(this.user);

        // [iv/C]FragmentTransaction : HomeFragment 화면을 보여주고 stack 에 담기
        FragmentTransaction startFragment = getSupportFragmentManager().beginTransaction();
        startFragment.replace(R.id.nav_home_content_container, home).commit();

        // [iv/C]Bo :
        navBottomBar = (BottomNavigationView) findViewById(R.id.nav_home_bottom_bar);
        navBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // [lv/C]FragmentTransaction : fragment 변경하기위한
                FragmentTransaction moveFragment = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.nav_bottom_bar_home:
                        // [method] : FragmentManager 에 있는 stack 의 모든 내용을 지운다.
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        // [lv/C]FragmentTransaction : home 이동
                        moveFragment.replace(R.id.nav_home_content_container, home).commitAllowingStateLoss();
                        // [iv/C]Stack<Fragment> : stack 에 push
                        break;
                    case R.id.nav_bottom_bar_training:
                        // [lv/C]FragmentTransaction : training 이동
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        moveFragment.replace(R.id.nav_home_content_container, training).commitAllowingStateLoss();
                        break;
                    case R.id.nav_bottom_bar_intervene:
                        // [lv/C]FragmentTransaction : intervene 이동
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        moveFragment.replace(R.id.nav_home_content_container, intervene).commitAllowingStateLoss();
                        break;
                    case R.id.nav_bottom_bar_more:
                        // [lv/C]FragmentTransaction : more 이동
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        moveFragment.replace(R.id.nav_home_content_container, more).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        final String METHOD_NAME = "[onBackPressed] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++ FragmentTransaction  = " + getSupportFragmentManager().getBackStackEntryCount());

        if (0 < getSupportFragmentManager().getBackStackEntryCount()) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> 0 보다 큼");
            super.onBackPressed();
        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 0 임");
            // [lv/C]AlertDialog : builder 객체 생성 및 초기 설정
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.home_alert_back_title)
                    .setMessage(R.string.home_alert_back_message)
                    .setPositiveButton(R.string.home_alert_back_bt_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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

}