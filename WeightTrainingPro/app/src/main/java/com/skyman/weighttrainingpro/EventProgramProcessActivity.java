package com.skyman.weighttrainingpro;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.dialog.RestTimePickerDialog;
import com.skyman.weighttrainingpro.management.project.activity.event.program.process.EPSPSectionManager;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

public class EventProgramProcessActivity extends AppCompatActivity implements RestTimePickerDialog.NoticeDialogListener {

    // constant
    private static final String CLASS_NAME = "[Ac]_EventProgramSetProcessingActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private User user;
    private MuscleArea muscleArea;
    private int setNumber;
    private int restTime;
    private Event event;

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

        // [iv/C]User : SessionManager 를 이용하여 intent 의 user 를 가져오기
        this.user = SessionManager.getUserInIntent(getIntent());

        // [iv/C]MuscleArea : SessionManager 를 이용하여 intent 의 muscleArea 를 가져오기
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());

        // [iv/i]setNumber : SessionManager 를 이용하여 intent 의 setNumber 를 가져오기
        this.setNumber = SessionManager.getSetNumberInIntent(getIntent());

        // [iv/i]restTime : SessionManager 를 이용하여 intent 의 restTime 을 가져오기
        this.restTime = SessionManager.getRestTimeInIntent(getIntent());

        // [iv/C]Event : SessionManager 를 이용하여 intent 의 event 를 가져오기
        this.event = SessionManager.getEventInIntent(getIntent());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. user");
        LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.user);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. event ");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.event);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. muscleArea = " + this.muscleArea);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "4. setNumber = " + this.setNumber);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "5. restTime = " + this.restTime);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "6. restTime 분,초 = " + DataFormatter.setTimeFormat(this.restTime));


        // [check 1] : 옳은 user 데이터이다.
        if (RightDataChecker.checkWhetherRightUser(this.user)) {

            // [check 2] : 옳은 event 데이터이다.
            if (RightDataChecker.checkWhetherRightEvent(this.event)) {

                // [iv/C]EPSPSectionManager : section 의 widget 을 초기설정
                this.section = new EPSPSectionManager(this, this.user, this.event, this.setNumber, this.restTime, getSupportFragmentManager());
                this.section.mappingWidget();
                this.section.initWidget();

            }
        }

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

                        // [method] :
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
        this.section.getRestTimeCounter().setTextColor(ContextCompat.getColor(this, R.color.colorTextSecond));

        // [iv/C]EPSPSectionManager : section 의 restTime 을 변경된 값으로 설정하기
        this.section.setRestTime(DataManager.convertMillisecondOfRestTime(restTimeMinuteNumber, restTimeSecondNumber));


        // [iv/C]restTime : resTime 도 변경된 값으로 설정하기
        this.restTime = DataManager.convertMillisecondOfRestTime(restTimeMinuteNumber, restTimeSecondNumber);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 0. rest time = " + DataManager.convertMillisecondOfRestTime(restTimeMinuteNumber, restTimeSecondNumber));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 1. minute number = " + restTimeMinuteNumber);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 2. second number = " + restTimeSecondNumber);
    } // End of method [onDialogPositiveClick]


    @Override
    public void onDialogNegativeClick(RestTimePickerDialog dialog) {
        final String METHOD_NAME = "[onDialogNegativeClick] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onDialogNegativeClick ");

        dialog.dismiss();
    } // End of method [onDialogNegativeClick]


}