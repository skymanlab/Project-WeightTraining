package com.skymanlab.weighttraining;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.project.activity.TopBarManager;
import com.skymanlab.weighttraining.management.project.activity.event.program.EPSectionManager;
import com.skymanlab.weighttraining.management.project.activity.event.program.EPSectionOneAllRandomManager;
import com.skymanlab.weighttraining.management.project.activity.event.program.EPSectionOneDirectManager;
import com.skymanlab.weighttraining.management.project.activity.event.program.EPSectionOneEachRandomManager;
import com.skymanlab.weighttraining.management.project.activity.event.program.EPSectionOneManager;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.RightDataChecker;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;

import java.util.ArrayList;

public class EventProgramActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] EventProgramActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private FirebaseUser firebaseUser = null;
    private MuscleArea muscleArea = null;

    // instance variable
    private ArrayList<Event> eventArrayList = null;

    // instance variable
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_program);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EventProgramListActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");


        // [iv/C]FirebaseUser : Firebase 를 통해 user 정보 가져오기
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [iv/C]String : 전 Activity 에서 전달된 intent 를 가져온다. 그리고 intent 로 전달된 데이터 중 muscleArea 를 가져온다.
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());

        // [check 1] : FirebaseAuth 에서 FirebaseUser 를 가져왔다.
        if (this.firebaseUser != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");

            // [check 2] : 옳은 muscleArea 데이터이다.
            if (RightDataChecker.checkWhetherRightMuscleArea(this.muscleArea)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 옳은 muscleArea 데이터입니다. <=");

                // [iv/C]Progressbar :
                this.progressBar = (ProgressBar) findViewById(R.id.event_program_section_1_progressbar);

                // [lv/C]TopBarManager : Top bar 초기 설정
                TopBarManager topBarManager = new TopBarManager(this, this.firebaseUser, true, false);
                topBarManager.connectWidget();
                topBarManager.setTitleContent(DataFormatter.setTopTitleFormat(MuscleAreaNextActivityType.EVENT_PROGRAM, this.muscleArea));
                topBarManager.initWidget();

                // [method] : eventArrayList 를 가져오면서 다음 과정 진행
                loadEventArrayList(this);

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 정확하지 않은 muscle 데이터입니다. <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : FirebaseUser 객체가 존재하지 않습니다. <=");
        } // [check 1]

    } // End of method [onCreate]

    @Override
    protected void onDestroy() {
        super.onDestroy();

        final String METHOD_NAME = "[onDestroy] ";

    }


    /**
     * [method]
     *
     */
    private void loadEventArrayList(Activity activity){

        final String METHOD_NAME = "[loadEventArrayList] ";

        // [lv/C]DatabaseReference :
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

        // [lv/C]Query :
        Query query = db.child(this.firebaseUser.getUid()).child(this.muscleArea.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // [lv/C]ArrayList<Event> : 객체 생성
                eventArrayList = new ArrayList<>();

                // [cycle 1] : snapshot 의 하위 목록 가져오기
                for (DataSnapshot search : snapshot.getChildren()) {

                    // [lv/C]Event :
                    Event data = search.getValue(Event.class);
                    data.setKey(search.getKey());

                    eventArrayList.add(data);

                } // [cycle 1]


                // [lv/C]GroupingEventData : eventArrayList 를 그룹화한 데이터를 저장하기 위해
                GroupingEventData groupingEventData = null;

                // [lv/i]sectionType : sectionType 을 저장하기 위해
                int sectionType = 0;

                // [check 4] : eventArrayList 에 추가된 event 데이터가 있다.
                if (eventArrayList.size() != 0) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_4/true : event table 에서 가져온 데이터가 있습니다. <=");

                    // [lv/C]GroupingEventUtil : eventArrayList 를 토대로 a~e 그룹으로 나눠서 각 그룹 별로 저장
                    GroupingEventUtil groupingEventUtil = new GroupingEventUtil(eventArrayList);
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
                EPSectionOneManager sectionOne = new EPSectionOneManager(activity);
                sectionOne.connectWidget();
                sectionOne.setSectionType(sectionType);
                sectionOne.initWidget();

                // [lv/C]EPSectionOneEachRandomManager : section_1_each_random 을 관리하는 메니저
                EPSectionOneEachRandomManager eachRandom = new EPSectionOneEachRandomManager(activity, firebaseUser, muscleArea, groupingEventData);
                eachRandom.connectWidget();
                eachRandom.setSectionType(sectionType);
                eachRandom.initWidget();

                // [lv/C]EPSectionOneAllRandomManager : section_1_all_random 을 관리하는 메니저
                EPSectionOneAllRandomManager allRandom = new EPSectionOneAllRandomManager(activity, firebaseUser, muscleArea, eventArrayList, groupingEventData);
                allRandom.connectWidget();
                allRandom.setSectionType(sectionType);
                allRandom.initWidget();

                // [lv/C]EPSectionOneDirectManager : section_1_direct 을 관리하는 메니저
                EPSectionOneDirectManager direct = new EPSectionOneDirectManager(activity, firebaseUser, muscleArea, groupingEventData);
                direct.connectWidget();
                direct.setSectionType(sectionType);
                direct.initWidget();

                // [iv/C]Progressbar : progressbar 지우기
                progressBar.setVisibility(ProgressBar.GONE);

                // [lv/C]EPSectionOneManager : section_1_each_random 을 보여주기
                sectionOne.getRandomWrapper().setVisibility(LinearLayout.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    } // End of method [loadEventArrayList]

}