package com.skyman.weighttrainingpro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.project.activity.TopBarManager;
import com.skyman.weighttrainingpro.management.project.activity.event.add.EASectionOneManager;
import com.skyman.weighttrainingpro.management.project.activity.event.add.EASectionTwoManager;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleAreaNextActivityType;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbConnector;
import com.skyman.weighttrainingpro.management.user.data.User;

public class EventAddActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_EventAddActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable : session
    private User user = null;
    private MuscleArea muscleArea = null;

    // instance variable : db
    private ProjectDbConnector projectDbConnector = null;
    private EventDbManager eventDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EventAddActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]User : SessionManager 를 통해 intent 에서 user 데이터 가져오기
        this.user = SessionManager.getUserInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** intent - user 데이터 확인 ****");
        LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.user);

        // [iv/C]String : SessionManager 를 통해 intent 에서 muscleAreaType 데이터 가져오기
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** intent - muscleAreaType 데이터 확인 ****");
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
                topBarManager.setTitleContent(DataFormatter.setTopTitleFormat(MuscleAreaNextActivityType.EVENT_ADD, this.muscleArea));
                topBarManager.initWidget();

                // [method] : database 연결 및 table 을 사용하기위한 메니저를 생성하는 초기작업 실행
                connectAndInitOfDatabase();

                // [check 3] : projectDbConnector 가 생성되었다.
                if (this.projectDbConnector != null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/true : 데이터베이스에 연결되었습니다. <=");
                    // [lv/C]EASectionOneManager : section_1 메니저
                    EASectionOneManager sectionOneManager = new EASectionOneManager(this, this.user, this.muscleArea);
                    sectionOneManager.mappingWidget();
                    sectionOneManager.setEventDbManager(this.eventDbManager);
                    sectionOneManager.initWidget();

                    // [lv/C]EASectionTwoManager : section_2 메니저
                    EASectionTwoManager sectionTwoManager = new EASectionTwoManager(this, this.user, this.muscleArea);
                    sectionTwoManager.mappingWidget();
                    sectionTwoManager.setEventDbManager(this.eventDbManager);
                    sectionTwoManager.initWidget();

                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/false : 데이터베이스에 연결하기 위한 ProjectDbConnector 가 생성되지 않았습니다. <=");
                } // [check 3]

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 정확하지 않은 muscle 데이터입니다. <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 정확하지 않은 user 데이터입니다. <=");
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

}