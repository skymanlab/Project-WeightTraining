package com.skyman.weighttrainingpro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.event.program.data.GroupingEventData;
import com.skyman.weighttrainingpro.management.event.program.util.GroupingEventUtil;
import com.skyman.weighttrainingpro.management.project.activity.TopBarManager;
import com.skyman.weighttrainingpro.management.project.activity.event.program.EPSectionManager;
import com.skyman.weighttrainingpro.management.project.activity.event.program.EPSectionOneAllRandomManager;
import com.skyman.weighttrainingpro.management.project.activity.event.program.EPSectionOneDirectManager;
import com.skyman.weighttrainingpro.management.project.activity.event.program.EPSectionOneEachRandomManager;
import com.skyman.weighttrainingpro.management.project.activity.event.program.EPSectionOneManager;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleAreaNextActivityType;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbConnector;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.ArrayList;

public class EventProgramActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_EventProgramActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private User user = null;
    private MuscleArea muscleArea = null;

    // instance variable
    private ProjectDbConnector projectDbConnector = null;
    private EventDbManager eventDbManager = null;

    // instance variable
    private ArrayList<Event> eventArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_program);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EventProgramListActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]User : 전 Activity 에서 전달된 intent 를 가져온다. 그리고 intent 로 전달된 데이터 중 user 를 가져온다.
        this.user = SessionManager.getUserInIntent(getIntent());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** intent 에서 user 를 확인하겠습니다. ***");
//        LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.user);

        // [iv/C]String : 전 Activity 에서 전달된 intent 를 가져온다. 그리고 intent 로 전달된 데이터 중 muscleArea 를 가져온다.
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** intent 에서 muscleArea 를 확인하겠습니다.");
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. muscleArea = " + this.muscleArea);

        // [check 1] : 옳은 user 데이터이다.
        if (RightDataChecker.checkWhetherRightUser(this.user)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");

            // [check 2] : 옳은 muscleArea 데이터이다.
            if (RightDataChecker.checkWhetherRightMuscleArea(this.muscleArea)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 옳은 muscleArea 데이터입니다. <=");
                // [lv/C]TopBarManager : Top bar 초기 설정
                TopBarManager topBarManager = new TopBarManager(this, this.user, true, false);
                topBarManager.mappingWidget();
                topBarManager.setTitleContent(DataFormatter.setTopTitleFormat(MuscleAreaNextActivityType.EVENT_PROGRAM, this.muscleArea));
                topBarManager.initWidget();

                // [method] : database 연결 및 table 을 사용하기위한 메니저를 생성하는 초기작업 실행
                connectAndInitOfDatabase();

                // [check 3] : projectDbConnector 가 생성되었다.
                if (this.projectDbConnector != null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/true : 데이터베이스에 연결되었습니다. <=");
                    // [method] : event table 에서 모든 event 데이터 가졎오기
                    setEventArrayList();

                    // [lv/C]GroupingEventData : eventArrayList 를 그룹화한 데이터를 저장하기 위해
                    GroupingEventData groupingEventData = null;

                    // [lv/i]sectionType : sectionType 을 저장하기 위해
                    int sectionType = 0;

                    // [check 4] : eventArrayList 에 추가된 event 데이터가 있다.
                    if (this.eventArrayList.size() != 0) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_4/true : event table 에서 가져온 데이터가 있습니다. <=");

                        // [lv/C]GroupingEventUtil : eventArrayList 를 토대로 a~e 그룹으로 나눠서 각 그룹 별로 저장
                        GroupingEventUtil groupingEventUtil = new GroupingEventUtil(this.eventArrayList);
                        groupingEventUtil.classifyEventArrayListToGroupType();

                        // [lv/C]GroupingEventData : 위에서 eventArrayList 를 그룹화한 데이터를 가져오기
                        groupingEventData = groupingEventUtil.getGroupingEvent();

                        // [lv/i]sectionType : sectionType 을 NOT_ZERO_TYPE_SECTION 으로 저장
                        sectionType = EPSectionManager.NOT_ZERO_TYPE_SECTION;

                    } else {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_4/false : event table 에 저장된 데이터가 없습니다. <=");

                        // [lv/i]sectionType : sectionType 을 ZERO_TYPE_SECTION 으로 저장
                        sectionType = EPSectionManager.ZERO_TYPE_SECTION;

                    } // [check 4]

                    // [lv/C]EPSectionOneManager : section_1 을 관리하는 메니저
                    EPSectionOneManager sectionOne = new EPSectionOneManager(this);
                    sectionOne.mappingWidget();
                    sectionOne.setSectionType(sectionType);
                    sectionOne.initWidget();

                    // [lv/C]EPSectionOneEachRandomManager : section_1_each_random 을 관리하는 메니저
                    EPSectionOneEachRandomManager eachRandom = new EPSectionOneEachRandomManager(this, this.user, this.muscleArea, groupingEventData);
                    eachRandom.mappingWidget();
                    eachRandom.setSectionType(sectionType);
                    eachRandom.initWidget();

                    // [lv/C]EPSectionOneAllRandomManager : section_1_all_random 을 관리하는 메니저
                    EPSectionOneAllRandomManager allRandom = new EPSectionOneAllRandomManager(this, this.user, this.muscleArea, this.eventArrayList, groupingEventData);
                    allRandom.mappingWidget();
                    allRandom.setSectionType(sectionType);
                    allRandom.initWidget();

                    // [lv/C]EPSectionOneDirectManager : section_1_direct 을 관리하는 메니저
                    EPSectionOneDirectManager direct = new EPSectionOneDirectManager(this, this.user, this.muscleArea, groupingEventData);
                    direct.mappingWidget();
                    direct.setSectionType(sectionType);
                    direct.initWidget();

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
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= db 사용 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] project database 에 접근하기위한 초기 설정 및 table 을 사용하기 위한 메니저를 생성하는 초기작업 실행
     */
    private void connectAndInitOfDatabase() {

        // [iv/C]ProjectDbConnector : database 에 연결하기 위한 connector 생성
        this.projectDbConnector = new ProjectDbConnector(getApplicationContext());

        // [iv/C]ProjectDbConnector : ProjectDbHelper 생성 및 연결완료 설정하기
        this.projectDbConnector.connectProjectDB();

        // [iv/C]EventDbManager : 위에서 연결한 database 에서 event table 을 사용하기 위한 manager 생성
        this.eventDbManager = new EventDbManager(this.projectDbConnector);

    } // End of method [connectAndInitOfDatabase]


    /**
     * [method] 해당 userCount 와 muscleArea 에 해당하는 event 데이터를 가져와서 eventArrayList 를 만든다.
     */
    private void setEventArrayList() {

        this.eventArrayList = this.eventDbManager.loadContentBy(this.user.getCount(), this.muscleArea);

    } // End of method [setEventArrayList]

}