package com.skyman.weighttrainingpro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.project.activity.MASectionManager;
import com.skyman.weighttrainingpro.management.project.activity.TopBarManager;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleAreaNextActivityType;
import com.skyman.weighttrainingpro.management.user.data.User;

public class MuscleAreaActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_MuscleAreaActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private User user = null;
    private MuscleAreaNextActivityType muscleAreaNextActivityType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_area);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> MuscleAreaActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]User : SessionManager 를 통해 intent 에서 user 데이터 가져오기
        this.user = SessionManager.getUserInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** intent - user 데이터 확인 ****");
        LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.user);

        // [iv/C]MuscleAreaNextActivityType : SessionManager 를 통해 intent 에서 muscleAreaNextActivityType 데이터 가져오기
        this.muscleAreaNextActivityType = SessionManager.getMuscleAreaNextActivityTypeInIntent(getIntent());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** intent - muscleAreaNextActivityType 데이터 확인 ****");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. muscleAreaNextActivity = " + this.muscleAreaNextActivityType);

        // [check 1] : 옳은 user 데이터이다.
        if (RightDataChecker.checkWhetherRightUser(this.user)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");

            // [check 2] : 옳은 muscleArea 데이터이다.
            if (RightDataChecker.checkWhetherRightMuscleAreaNextActivityType(this.muscleAreaNextActivityType)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 옳은 muscleAreaNextActivityType 데이터입니다. <=");

                // [lv/C]TopBarManager : Top bar 초기 설정 / subTitle 설정
                TopBarManager topBarManager = new TopBarManager(this, this.user, false, true);
                topBarManager.mappingWidget();
                topBarManager.setSubTitleContent(DataFormatter.setSubtitleFormat(this.muscleAreaNextActivityType));
                topBarManager.initWidget();

                // [lv/C]MASectionManager : section 메니저
                MASectionManager sectionManager = new MASectionManager(this, this.user, this.muscleAreaNextActivityType);
                sectionManager.mappingWidget();
                sectionManager.initWidget();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 정확하지 않은 muscle 데이터입니다. <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 정확하지 않은 user 데이터입니다. <=");
        } // [check 1]

    } // End of method [onCreate]

}