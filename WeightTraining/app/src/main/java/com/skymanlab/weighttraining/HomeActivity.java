package com.skymanlab.weighttraining;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.preference.PreferenceManager;

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
import com.skymanlab.weighttraining.management.project.activity.HSectionManager;
import com.skymanlab.weighttraining.management.project.activity.TopBarManager;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.HashMap;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] HomeActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private FirebaseUser firebaseUser = null;

    // instance variable
    private HSectionManager sectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> HomeActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]FirebaseUser : Firebase 를 통해 user 정보 가져오기
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [check 1] : FirebaseAuth 에서 FirebaseUser 를 가져왔다.
        if (this.firebaseUser != null) {

            // [method] : 인증받은 나의 데이터가 FirebaseDatabase 에 저장되어 있는지 유무를 판단하여 해당 과정을 진행한다.
            registerFirstUserSetting(this.firebaseUser);

            // [method] :
            registerBaseEventDataSetting();

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");
            // [lv/C]TopBarManager : Top bar 초기 설정
            TopBarManager topBarManager = new TopBarManager(this, this.firebaseUser, true, false);
            topBarManager.connectWidget();
            topBarManager.setTitleContent(getString(R.string.home_title));
            topBarManager.initWidget();

            // [iv/C]HSectionManager : section 메니저
            this.sectionManager = new HSectionManager(this, this.firebaseUser);
            this.sectionManager.connectWidget();
            this.sectionManager.initWidget();

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : FirebaseUser 객체가 존재하지 않습니다. <=");
        } // [check 1]



    } // End of method [onCreate]


    @Override
    public void onBackPressed() {

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

    } // End of method [onBackPressed]

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        final String METHOD_NAME = "[onWindowFocusChanged] ";

        // [lv/C]Random : 랜덤으로 X, Y 좌표 구하기위한
        Random random = new Random();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++ A_1. width = " + this.sectionManager.getContainer().getWidth());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++ A_2. height = " + this.sectionManager.getContainer().getHeight());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**************** B_1. width = " +  this.sectionManager.getAdMopClicker().getWidth());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**************** B_2. height = " + this.sectionManager.getAdMopClicker().getHeight());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++ C_1. width = " + (this.sectionManager.getContainer().getWidth() - this.sectionManager.getAdMopClicker().getWidth()));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++ C_2. height = " + (this.sectionManager.getContainer().getHeight() - this.sectionManager.getAdMopClicker().getHeight()));

        // [lv/i]randomPositionX : View(container) 의 W, H 를 구하고 해당 ImageView(adMobClicker) 의 W, H 를 뺀 값에서 랜덤으로 X, Y 값 구하기
        int randomPositionX = random.nextInt(this.sectionManager.getContainer().getWidth() - this.sectionManager.getAdMopClicker().getWidth());
        int randomPositionY = random.nextInt(this.sectionManager.getContainer().getHeight() - this.sectionManager.getAdMopClicker().getHeight());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "> X = " + randomPositionX  + ", Y = " + randomPositionY);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(randomPositionX, randomPositionY, 0, 0);
        // [iv/C]HSectionManager : section 의 adMobClicker 의 margin 으로 위치 변경


        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(randomPositionX, randomPositionY+200,0,0);

        this.sectionManager.getAdMopClicker().setLayoutParams(layoutParams);

//        this.sectionManager.getAdMopClicker().setLeft(randomPositionX);
//        this.sectionManager.getAdMopClicker().setTop(randomPositionY);
//        this.sectionManager.getAdMopClicker().setPadding(randomPositionX, randomPositionY, 0,0);
    }

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

        // [lv/C]String : 'base_event_data' 에 저장되어 있는 값을 가져온다.
        String baseEventData = preferences.getString("base_event_data", "false");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ 기존 base_event_data 확인 = " + baseEventData);

        // [check 1] : 'base_event_data' 가 'true' 가 아닐 때만 검사한다.
        if (!baseEventData.equals("true")) {

            // [lv/C]DatabaseReference :
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

            // [lv/C]Query :
            Query query = db.child(firebaseUser.getUid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> snapshot = " + snapshot);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> snapshot children = " + snapshot.getChildren());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> snapshot value = " + snapshot.getValue());

                    // [check 1] : 해당 key=uid 에서 가져온 value 가 있으면, TWO 가 아닌데 저장되어 있는 것이므로 'base_event_data' 를 TWO 로 변경해야 한다.
                    if (snapshot.getValue() != null) {

                        // 'base_event_data' 즉, 기본 종목 데이터가 입력되었습니다. 위에서는 TWO 가 아닌데 이미 데이터베이스에는 저장되어 있다는 말이다. 그래서 Preference 에 'base_event_data' 항목의 defaultValue 를 ONE -> TWO 로 변경한다.
                        // [lv/C]SharedPreference : 설정값을 저장하기 위한 객체 생성 / 'base_event_data' 의 값을 true 로 변경
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("base_event_data", "true");
                        editor.commit();
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ 바뀐 base_event_data 확인 = " + preferences.getString("base_event_data", ""));

                    } else {
                        // 'base_event_data' 즉, 기본 종목 데이터가 입력되지 않았습니다.
                        // [lv/C]SharedPreference : 설정값을 저장하기 위한 객체 생성 / 'base_event_data' 의 값을 false 로 변경
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("base_event_data", "false");
                        editor.commit();
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ 바뀐 base_event_data 확인 = " + preferences.getString("base_event_data", ""));

                    } // [check 1]

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 기본 종목 데이터가 저장되었습니다. Firebase 의 database 를 검사할 필요가 없습니다.");
        } // [check 1]

    } // End of method [registerBaseEventDataSetting]

}