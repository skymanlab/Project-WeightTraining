package com.skymanlab.weighttraining;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import com.skymanlab.weighttraining.management.project.data.BaseEventDataManager;
import com.skymanlab.weighttraining.management.project.fragment.Home.HomeFragment;
import com.skymanlab.weighttraining.management.project.fragment.Intervene.InterveneFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.MoreFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.TrainingFragment;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.HashMap;

public class NavHomeActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] NavHomeActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

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

        // [method] : firebase database 에 나의 정보가 저장되어 있지 않으면, 나의 정보를 database 에 저장한다.
        registerFirstUserSetting(this.firebaseUser);

        // [method] : firebase database 의 event/ uid 로 데이터가 저장되어 있
        registerBaseEventDataSetting();

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


    /**
     * [method] FirebaseDatabase 에서 user 의 하위 목록에 나의 uid 가 있는지 검사하여 없으면, 인증받은 FirebaseUser 객체의 정보를 'user/uid' 에 데이터를 저장한다.
     */
    private void registerFirstUserSetting(FirebaseUser firebaseUser) {

        final String METHOD_NAME = "[registerFirstAuth] ";

        // [lv/C]DatabaseReference : user/$uid 을 가져오기 위한
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // [lv/C]User : uid 로 user 가져오기
                User userData = snapshot.child(firebaseUser.getUid()).getValue(User.class);

                // [check 1] : FirebaseDatabase 에 저장된 데이터가 없습니다.
                if (userData == null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++++++ firebase database 의 user 없음");

                    // [lv/C]DatabaseReference : user/$uid 위치에 해당 uid 로 저장하기
                    reference.runTransaction(new Transaction.Handler() {

                        // [lv/C]HashMap<String, Object> : user 데이터를 가져온다.
                        HashMap<String, Object> data = null;

                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                            // [lv/C]Integer : user/userCount 가져오기
                            Integer userCount = currentData.child("userCount").getValue(Integer.class);

                            // [check 1] : 객체가 있을 때만
                            if (userCount == null) {

                                // [lv/C]HashMap<String, Object> : user 데이터 입력
                                data = new HashMap<>();
                                data.put("count", 1);
                                data.put("name", firebaseUser.getDisplayName());
                                data.put("email", firebaseUser.getEmail());

                                // [lv/C]MutableData : user/userCount 와 user/$uid 로 각각 저장한다.
                                currentData.child("userCount").setValue(1);
                                currentData.child(firebaseUser.getUid()).setValue(data);

                            } else {

                                // [lv/i]nextUserCount : userCount 의 다음 값
                                int nextUserCount = userCount + 1;

                                // [lv/C]HashMap<String, Object> : user 데이터 입력
                                data = new HashMap<>();
                                data.put("count", nextUserCount);
                                data.put("name", firebaseUser.getDisplayName());
                                data.put("email", firebaseUser.getEmail());

                                // [lv/C]MutableData : user/userCount 와 user/$uid 로 각각 저장한다.
                                currentData.child("userCount").setValue(nextUserCount);
                                currentData.child(firebaseUser.getUid()).setValue(data);
                            }

                            return Transaction.success(currentData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                        }
                    });

                } // [check 1]
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++ firebase database 의 error 발생!! ");
            }
        });

    } // End of method [registerFirstAuth]


    /**
     * [method] 기존 'base_event_data' 의 값을 가져와서 TWO 가 아닐 때는 진짜 기본 종목 데이터가 저장되지 않았는지를
     * FirebaseDatabase 에서 event 의 하위 목록에 나의 uid 가 있는지 검사하여 있으면 설정항목의 'base_event_data' 를 ONE->TWO 로 변경한다.
     */
    private void registerBaseEventDataSetting() {

        final String METHOD_NAME = "[registerBaseEventDataSetting] ";

        // [lv/C]SharedPreferences :
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // [lv/C]String : 'base_event_data' 에 저장되어 있는 값을 가져온다.
        String baseEventData = preferences.getString("base_event_data", "false");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ 기존 base_event_data 확인 = " + baseEventData);

        // [lv/C]DatabaseReference : Firebase 의 database 에 저장 유무를 확인하기 위한 
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
        
        // [lv/C]Query : firebase 의 database 에서 'event/$uid' 에 데이터 유무를 판별하기 위한 query 생성
        Query query = db.child(firebaseUser.getUid());

        // [check 1] : 'base_event_data' 의 값이 뭐었이냐?
        switch (baseEventData) {
            case "true":

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.getValue() != null) {
                            // true - true : 설정값이 true 이고 데이터베이스에 저장되었으면(true) -> 아무것도 안 해도 됩니다.
                        } else {
                            // true - false : 설정값이 true 인데 데이터베이스에는 저장되어 있지 않으면(false) -> '기본 종목 데이터'를 데이터베이스에 다시 저장해야 한다.
                            // [method] : 기본 종목 데이터 입력하는 과정 진행
                            saveBaseEventData(preferences);
                        } 
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                break;
            case "false":

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.getValue() != null) {
                            // false - true : 설정값은 false 인데 데이터베이스에는 저장되어 있으면(true) -> 설정값을 true 로 변경한다.
                            
                            // [lv/C]SharedPreference : 설정값을 저장하기 위한 객체 생성 / 'base_event_data' 의 값을 true 로 변경
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("base_event_data", "true");
                            editor.commit();
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ 바뀐 base_event_data 확인 = " + preferences.getString("base_event_data", ""));

                        } else {
                            // false - false : 설정값이 false 인데 데이터베이스에도 저장되어 있지 않으면(false) -> 데이터베이스에 저장하는 과정을 진행한다.

                            // [lv/C]AlertDialog : builder 를 통해 AlertDialog 객체를 생성하고, 초기값을 설정한다.
                            AlertDialog.Builder builder = new AlertDialog.Builder(NavHomeActivity.this);
                            builder.setTitle(R.string.preference_alert_base_event_data_save_title)
                                    .setMessage(R.string.preference_alert_base_event_data_save_message)
                                    .setPositiveButton(R.string.preference_alert_base_event_data_save_bt_positive, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            // [method] : '기본 종목 데이터'를 저장하는 과정 진행
                                            saveBaseEventData(preferences);
                                        }
                                    })
                                    .setNegativeButton(R.string.preference_alert_base_event_data_save_bt_negative, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .show();
                        } 
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

        } // [check 1]

    } // End of method [registerBaseEventDataSetting]


    /**
     * [method] BaseEventDataManager 를 이용하여 기본 종목 데이터를 firebase database 에 저장한다.
     */
    private void saveBaseEventData(SharedPreferences preferences) {
        final String METHOD_NAME = "[saveBaseEventData] ";

        // [lv/C]BaseEventDataManager : 기본 종목 데이터를 생성하고
        BaseEventDataManager baseEventDataManager = new BaseEventDataManager(FirebaseAuth.getInstance().getCurrentUser().getUid());
        baseEventDataManager.makeAllBaseEvent();
        baseEventDataManager.saveAllBaseEvent();

        // [lv/C]SharedPreference : "base_event_data" 의 값을 true 로 변경한다.
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("base_event_data", "true");
        editor.commit();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++> firebase 의 database 에 저장되었으며, preference 의 설정 값도 true 로 변경하였습니다.");

    } // End of method [saveBaseEventData]

}