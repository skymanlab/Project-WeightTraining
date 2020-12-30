package com.skymanlab.weighttraining;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.activity.TopBarManager;
import com.skymanlab.weighttraining.management.project.activity.event.add.EASectionOneManager;
import com.skymanlab.weighttraining.management.project.activity.event.add.EASectionTwoManager;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.RightDataChecker;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;

public class EventAddActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_EventAddActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private FirebaseUser firebaseUser = null;
    private MuscleArea muscleArea = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EventAddActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]FirebaseUser : Firebase 를 통해 user 정보 가져오기
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [iv/C]String : SessionManager 를 통해 intent 에서 muscleAreaType 데이터 가져오기
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** intent - muscleAreaType 데이터 확인 ****");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. muscleArea = " + this.muscleArea);

        // [check 1] : FirebaseAuth 에서 FirebaseUser 를 가져왔다.
        if (this.firebaseUser != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : firebaseUser 데이터가 있습니다. <=");

            // [check 2] : 옳은 muscleArea 데이터이다.
            if (RightDataChecker.checkWhetherRightMuscleArea(this.muscleArea)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 옳은 muscleArea 데이터입니다. <=");
                // [lv/C]TopBarManager : Top bar 초기 설정
                TopBarManager topBarManager = new TopBarManager(this, this.firebaseUser, true, false);
                topBarManager.mappingWidget();
                topBarManager.setTitleContent(DataFormatter.setTopTitleFormat(MuscleAreaNextActivityType.EVENT_ADD, this.muscleArea));
                topBarManager.initWidget();

                // [lv/C]EASectionOneManager : section_1 메니저
                EASectionOneManager sectionOneManager = new EASectionOneManager(this, this.firebaseUser, this.muscleArea);
                sectionOneManager.mappingWidget();
                sectionOneManager.initWidget();

                // [lv/C]EASectionTwoManager : section_2 메니저
                EASectionTwoManager sectionTwoManager = new EASectionTwoManager(this, this.firebaseUser, this.muscleArea, getSupportFragmentManager());
                sectionTwoManager.mappingWidget();
                sectionTwoManager.initWidget();

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

    } // End of method [onDestroy]


}