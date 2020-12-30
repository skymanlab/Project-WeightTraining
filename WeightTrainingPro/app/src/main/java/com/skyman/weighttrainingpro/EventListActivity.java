package com.skyman.weighttrainingpro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.project.activity.TopBarManager;
import com.skyman.weighttrainingpro.management.project.activity.event.list.ELSectionManager;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleAreaNextActivityType;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbConnector;
import com.skyman.weighttrainingpro.management.user.data.User;

public class EventListActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_EventListActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private User user = null;
    private MuscleArea muscleArea = null;

    // instance variable : db
    private ProjectDbConnector projectDbConnector = null;
    private EventDbManager eventDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EventListActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]User : 전 Activity 에서 전달된 intent 를 가져온다. 그리고 intent 로 전달된 데이터 중 user 를 가져온다.
        this.user = SessionManager.getUserInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** intent 에서 user 를 확인하겠습니다. ***");
        LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.user);

        // [iv/C]String : 전 Activity 에서 전달된 intent 를 가져온다. 그리고 intent 로 전달된 데이터 중 muscleArea 를 가져온다.
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** intent 에서 muscleArea 를 확인하겠습니다.");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. muscleArea = " + this.muscleArea);

        // [check 1] : 옳은 user 데이터이다.
        if (RightDataChecker.checkWhetherRightUser(this.user)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");

            // [check 2] : 옳은 muscleArea 데이터이다.
            if (RightDataChecker.checkWhetherRightMuscleArea(this.muscleArea)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 옳은 muscleArea 데이터입니다. <=");
                // [lv/C]TopBarManager : Top bar 초기 설정
                TopBarManager topBarManager = new TopBarManager(this, this.user, true, false);
                topBarManager.mappingWidget();
                topBarManager.setTitleContent(DataFormatter.setTopTitleFormat(MuscleAreaNextActivityType.EVENT_LIST, this.muscleArea));
                topBarManager.initWidget();

                // [method] : database 연결 및 table 을 사용하기위한 메니저를 생성하는 초기작업 실행
                connectAndInitOfDatabase();

                // [check 3] : projectDbConnector 가 생성되었다.
                if (this.projectDbConnector != null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/true : 데이터베이스에 연결되었습니다. <=");
                    // [lv/C]ELSectionManager : section 메니저
                    ELSectionManager sectionManager = new ELSectionManager(this, this.user, this.muscleArea);
                    sectionManager.mappingWidget();
                    sectionManager.setEventDbManager(this.eventDbManager);
                    sectionManager.initWidget();

                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/false : 데이터베이스에 연결하기 위한 ProjectDbConnector 가 생성되지 않았습니다. <=");
                } // [check 3]

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 전 Activity 에서 Intent 에 포함하여 보낸 muscleArea 가 없어요! <=");
            } // [check 2]

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


    /**
     * [method] [set] project database 에 접근하기위한 초기 설정 및 table 을 사용하기 위한 메니저를 생성하는 초기작업 실행
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

}