package com.skyman.weighttrainingpro;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.project.activity.HSectionManager;
import com.skyman.weighttrainingpro.management.project.activity.TopBarManager;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbConnector;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_HomeActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable : session
    private User user = null;
    private boolean initialSettingChecker = false;

    // instance variable : db
    private ProjectDbConnector projectDbConnector = null;
    private EventDbManager eventDbManager = null;

    // instance vairable
    private HSectionManager sectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> HomeActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]User : 전 Activity 에서 보낸 Intent 에 포한된 "user" 을 가져온다.
        this.user = SessionManager.getUserInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** user 확인 ***");
        LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.user);


        // [check 1] : 옳은 user 데이터이다.
        if (RightDataChecker.checkWhetherRightUser(this.user)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");
            // [lv/C]TopBarManager : Top bar 초기 설정
            TopBarManager topBarManager = new TopBarManager(this, this.user, false, false);
            topBarManager.mappingWidget();
            topBarManager.initWidget();

            // [lv/C]HSectionManager : section 메니저
            sectionManager = new HSectionManager(this, this.user);
            sectionManager.mappingWidget();
            sectionManager.initWidget();

            // [method] : database 연결 및 table 을 사용하기위한 메니저를 생성하는 초기작업 실행
            connectAndInitOfDatabase();

            // [check 2] : projectDbConnector 가 생성되었다.
            if (this.projectDbConnector != null) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 데이터베이스에 연결되었습니다. <=");
                // [lv/C]TopBarManager : HomeActivity 에서만 BaseEvent 를 등록하기 위한 event table 메니저 등록한다.
                topBarManager.setEventDbManager(this.eventDbManager);

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 데이터베이스에 연결하기 위한 ProjectDbConnector 가 생성되지 않았습니다. <=");
            } // [check 2]

//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("basic_event_data", "TWO");
//            editor.commit();
//            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> basic event data = " + sharedPreferences.getString("basic_event_data", "true") );

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 정확하지 않는 user 데이터입니다. <=");
        } // [check 1]

    } // End of method [onCreate]


    @Override
    protected void onDestroy() {
        super.onDestroy();

        final String METHOD_NAME = "[onDestroy] ";

        // [check 1] : projectDbConnector 가 생성되었다.
        if (this.projectDbConnector != null) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 데이터베이스에 연결되었습니다. <=");
            // [iv/C]ProjectDbConnector : Database close
            this.projectDbConnector.closeProjectDB();

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : projectDbConnector 가 생성되지 않았습니다. <=");
        } // [check 1]

    } // End of method [onDestroy]


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


    /**
     * [method] project database 에 접근하기위한 초기 설정 및 table 을 사용하기 위한 메니저를 생성하는 초기작업 실행
     */
    private void connectAndInitOfDatabase() {

        final String METHOD_NAME = "[connectAndInitOfDatabase] ";

        // [iv/C]ProjectDbConnector : database 에 연결하기 위한 connector 생성
        this.projectDbConnector = new ProjectDbConnector(getApplicationContext());

        // [iv/C]ProjectDbConnector : ProjectDbHelper 생성 및 연결완료 설정하기
        this.projectDbConnector.connectProjectDB();

        // [iv/C]EventDbManager : 위에서 연결한 database 에서 event table 을 사용하기 위한 manager 생성
        this.eventDbManager = new EventDbManager(this.projectDbConnector);

    } // End of method [connectAndInitOfDatabase]

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        final  String METHOD_NAME = "[onWindowFocusChanged] ";
        super.onWindowFocusChanged(hasFocus);

        int mainContainerWidth = this.sectionManager.getMainContainer().getWidth();
        int mainContainerHeight = this.sectionManager.getMainContainer().getHeight();

        int adMobClickWidth = this.sectionManager.getAdMobClick().getWidth();
        int adMobClickHeight = this.sectionManager.getAdMobClick().getHeight();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>  focus 에서 width = " + mainContainerWidth);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>  focus 에서 height = " + mainContainerHeight);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> focus 에서 adMobClick width = " + adMobClickWidth );
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> focus 에서 adMobClick height = " + adMobClickHeight );

        Random random = new Random();

        int randomPositionWidth = random.nextInt(mainContainerWidth - adMobClickWidth);
        int randomPositionHeight = random.nextInt(mainContainerHeight - adMobClickHeight) + 100;

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++ random width = " + randomPositionWidth);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++ random height = " + randomPositionHeight);

        this.sectionManager.getAdMobClick().setPadding(randomPositionWidth, randomPositionHeight, 0, 0);
    }
}