package com.skymanlab.weighttraining;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.dialog.RestTimePickerDialog;
import com.skymanlab.weighttraining.management.project.activity.event.program.process.EPSPSectionManager;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.RightDataChecker;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.user.data.User;

public class EventProgramProcessActivity extends AppCompatActivity implements RestTimePickerDialog.NoticeDialogListener {

    // constant
    private static final String CLASS_NAME = "[Ac] EventProgramProcessActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private FirebaseUser firebaseUser;
    private MuscleArea muscleArea;
    private int setNumber;
    private int restTime;
    private Event event;
    private int eventPosition;

    // instance variable
    private EPSPSectionManager section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_program_process);

        final String METHOD_NAME = "[onCreate] ";

        // [method] : 화면 꺼짐 방지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EventProgramListActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]FirebaseUser : Firebase 를 통해 user 정보 가져오기
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [iv/C]MuscleArea : SessionManager 를 이용하여 intent 의 muscleArea 를 가져오기
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());

        // [iv/i]setNumber : SessionManager 를 이용하여 intent 의 setNumber 를 가져오기
        this.setNumber = SessionManager.getSetNumberInIntent(getIntent());

        // [iv/i]restTime : SessionManager 를 이용하여 intent 의 restTime 을 가져오기
        this.restTime = SessionManager.getRestTimeInIntent(getIntent());

        // [iv/C]Event : SessionManager 를 이용하여 intent 의 event 를 가져오기
        this.event = SessionManager.getEventInIntent(getIntent());

        // [iv/i]eventPosition : SessionManager 를 이용하여 intent 의 eventPosition 을 가져오기
        this.eventPosition = SessionManager.getEventPositionInIntent(getIntent());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++ eventPosition = " + this.eventPosition);


        // [check 1] : FirebaseAuth 에서 FirebaseUser 를 가져왔다.
        if (this.firebaseUser != null) {

            // [check 2] : 옳은 event 데이터이다.
            if (RightDataChecker.checkWhetherRightEvent(this.event)) {

                // [iv/C]EPSPSectionManager : section 의 widget 을 초기설정
                this.section = new EPSPSectionManager(this, this.firebaseUser, this.event, this.eventPosition, this.setNumber, this.restTime, getSupportFragmentManager());
                this.section.connectWidget();
                this.section.initWidget();

            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : FirebaseUser 객체가 존재하지 않습니다. <=");
        } // [check 1]

    } // End of method [onCreate]


    @Override
    public void onBackPressed() {

        final String METHOD_NAME = "[onBackPressed] ";

        // [lv/C]AlertDialog.Builder : builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.event_program_process_alert_back_press_title)
                .setMessage(R.string.event_program_process_alert_back_press_massage)
                .setPositiveButton(R.string.event_program_process_alert_back_press_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [check 1] : section 의 timer 가 생성 되었을 때만, 시간 카운트가 되었을 때만
                        if (section.getTimer() != null) {

                            // [iv/C]EPSPSectionManager : 해당 section manager 의 timer 종료
                            section.getTimer().cancel();

                        } else {
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : timer 가 시작된 적이 없습니다. <=");
                        } // [check 1]

                        // [method] : 해당 Activity 종료
                        finish();

                    }
                })
                .setNegativeButton(R.string.event_program_process_alert_back_press_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .show();

    } // End of method [onBackPressed]


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // [method] : 화면 꺼지 방지 해제
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    public void onDialogPositiveClick(RestTimePickerDialog dialog, int restTimeMinuteNumber, int restTimeSecondNumber) {

        final String METHOD_NAME = "[onDialogPositiveClick] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onDialogPositiveClick ");

        // [iv/C]EPSPSectionManager : section 의 restTimeCounter(TextView) widget 을 선택된 값으로 설정하기
        this.section.getRestTimeCounter().setText(DataFormatter.setTimeFormat(restTimeMinuteNumber, restTimeSecondNumber));
        this.section.getRestTimeCounter().setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));

        // [iv/C]EPSPSectionManager : section 의 restTime 을 변경된 값으로 설정하기
        this.section.setRestTime(DataManager.convertMillisecondOfRestTime(restTimeMinuteNumber, restTimeSecondNumber));


        // [iv/C]restTime : resTime 도 변경된 값으로 설정하기
        this.restTime = DataManager.convertMillisecondOfRestTime(restTimeMinuteNumber, restTimeSecondNumber);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 0. rest time = " + DataManager.convertMillisecondOfRestTime(restTimeMinuteNumber, restTimeSecondNumber));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 1. minute number = " + restTimeMinuteNumber);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 2. second number = " + restTimeSecondNumber);
    }


    @Override
    public void onDialogNegativeClick(RestTimePickerDialog dialog) {
        final String METHOD_NAME = "[onDialogNegativeClick] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onDialogNegativeClick ");

        dialog.dismiss();
    }
}