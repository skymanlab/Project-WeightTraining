package com.skymanlab.weighttraining;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.activity.MASectionManager;
import com.skymanlab.weighttraining.management.project.activity.TopBarManager;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.RightDataChecker;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;

public class MuscleAreaActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] MuscleAreaActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private FirebaseUser firebaseUser = null;
    private MuscleAreaNextActivityType muscleAreaNextActivityType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_area);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> MuscleAreaActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]FirebaseUser : Firebase 를 통해 user 정보 가져오기
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [iv/C]MuscleAreaNextActivityType : SessionManager 를 통해 intent 에서 muscleAreaNextActivityType 데이터 가져오기
        this.muscleAreaNextActivityType = SessionManager.getMuscleAreaNextActivityTypeInIntent(getIntent());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** intent - muscleAreaNextActivityType 데이터 확인 ****");
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. muscleAreaNextActivity = " + this.muscleAreaNextActivityType);


        // [check 1] : FirebaseAuth 에서 FirebaseUser 를 가져왔다.
        if (this.firebaseUser != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");

            // [check 2] : 옳은 muscleArea 데이터이다.
            if (RightDataChecker.checkWhetherRightMuscleAreaNextActivityType(this.muscleAreaNextActivityType)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 옳은 muscleAreaNextActivityType 데이터입니다. <=");

                // [lv/C]TopBarManager : Top bar 초기 설정 / subTitle 설정
                TopBarManager topBarManager = new TopBarManager(this, this.firebaseUser, true, true);
                topBarManager.connectWidget();
                topBarManager.setTitleContent(getString(R.string.muscle_area_title));
                topBarManager.setSubTitleContent(DataFormatter.setSubtitleFormat(this.muscleAreaNextActivityType));
                topBarManager.initWidget();

                // [lv/C]MASectionManager : section 메니저
                MASectionManager sectionManager = new MASectionManager(this, this.firebaseUser, this.muscleAreaNextActivityType);
                sectionManager.connectWidget();
                sectionManager.initWidget();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 정확하지 않은 muscle 데이터입니다. <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : FirebaseUser 객체가 존재하지 않습니다. <=");
        } // [check 1]

    } // End of method [onCreate]

}