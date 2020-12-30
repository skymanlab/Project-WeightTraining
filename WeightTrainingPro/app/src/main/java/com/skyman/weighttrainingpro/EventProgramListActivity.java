package com.skyman.weighttrainingpro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.dialog.RestTimePickerDialog;
import com.skyman.weighttrainingpro.management.event.dialog.SetNumberPickerDialog;
import com.skyman.weighttrainingpro.management.project.activity.TopBarManager;
import com.skyman.weighttrainingpro.management.project.activity.event.program.list.EPLSectionOneManager;
import com.skyman.weighttrainingpro.management.project.activity.event.program.list.EPLSectionTwoManager;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleAreaNextActivityType;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.ArrayList;

public class EventProgramListActivity extends AppCompatActivity implements SetNumberPickerDialog.NoticeDialogListener, RestTimePickerDialog.NoticeDialogListener {

    // constant
    private static final String CLASS_NAME = "[Ac]_EventProgramListActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : session
    private User user;
    private MuscleArea muscleArea;
    private ArrayList<Event> selectedEventArrayList;
    private ArrayList<Event> noSelectedEventArrayList;

    // instance variable
    private EPLSectionOneManager sectionOne;
    private EPLSectionTwoManager sectionTwo;

    // instance variable
    private DialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_program_list);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EventProgramListActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]User : SessionManager 를 통해 user 데이터 가져오기
        this.user = SessionManager.getUserInIntent(getIntent());

        // [iv/C]MuscleArea : SessionManager 를 통해 muscleArea 데이터 가져오기
        this.muscleArea = SessionManager.getMuscleAreaInIntent(getIntent());

        // [iv/C]ArrayList<Event> : SessionManager 를 통해 selectedEventArrayList 데이터 가져오기
        this.selectedEventArrayList = SessionManager.getSelectedEventArrayListInIntent(getIntent());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** SessionManager - selectedEventArrayList ****");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.selectedEventArrayList);

        // [iv/C]ArrayList<Event> : SessionManager 를 통해 noSelectedEventArrayList 데이터 가져오기
        this.noSelectedEventArrayList = SessionManager.getNoSelectedEventArrayListInIntent(getIntent());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** SessionManager - noSelectedEventArrayList ****");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.noSelectedEventArrayList);

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

                // [iv/C]SectionOneManager : sectionOne 과 관련된 widget 메니저
                this.sectionOne = new EPLSectionOneManager(this, getSupportFragmentManager());
                this.sectionOne.mappingWidget();
                this.sectionOne.initWidget();

                // [iv/C]EPLSectionTwoManager : sectionTwo 와 관련된 widget 메니저
                this.sectionTwo = new EPLSectionTwoManager(this, this.user, this.muscleArea, this.selectedEventArrayList, this.noSelectedEventArrayList);
                this.sectionTwo.mappingWidget();
                this.sectionTwo.setSetNumber(this.sectionOne.getSetNumber());
                this.sectionTwo.setRestTimeMinute(this.sectionOne.getRestTimeMinute());
                this.sectionTwo.setRestTimeSecond(this.sectionOne.getRestTimeSecond());
                this.sectionTwo.initWidget();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 정확하지 않은 muscle 데이터입니다. <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 정확하지 않은 user 데이터입니다. <=");
        } // [check 1]

    } // End of method [onCreate]

    @Override
    public void onBackPressed() {

        final String METHOD_NAME = "[onBackPressed] ";

        // [lv/C]AlertDialog.Builder : builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.event_program_list_alert_back_press_title)
                .setMessage(R.string.event_program_list_alert_back_press_message)
                .setPositiveButton(R.string.event_program_list_alert_back_press_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method] : 해당 Activity 종료
                        finish();
                    }
                })
                .setNegativeButton(R.string.event_program_list_alert_back_press_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .show();

    } // End of method [onBackPressed]


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        final String METHOD_NAME = "[onActivityResult] ";

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 50000 && resultCode == RESULT_OK) {

            String result = data.getStringExtra("result");

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> result =  " + result);
            Toast.makeText(this, result + " 를 성공하였습니다.", Toast.LENGTH_SHORT).show();

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> no");
        }
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= SetNumberPickerDialog.NoticeDialogListener =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Override
    public void onDialogPositiveClick(SetNumberPickerDialog dialog, int setNumber) {

        final String METHOD_NAME = "[onDialogPositiveClick] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> set number picker dialog's positive button click >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. number picker 선택 값  = " + setNumber);

        // [iv/C]EPLSectionOneManager : section_1 의 setNumber widget5(TextView) 에 setNumber 설정하기
        this.sectionOne.getSetNumber().setText(String.format("%02d", setNumber));

        // [iv/C]EPLSectionOneManager : section_1 의 setNumber widget(TextView) 에 변경된 수가 적용되어 section_1 의 setNumber 의 UP, DOWN 버튼을 클릭했을 때, 변경된 수가 적용되도록 하는 것이다.
        this.sectionOne.setSetNumberCounter(setNumber);

        // [iv/C]EPLSectionOneManager : SetNumberPickerDialog 가 종료 되었기 때문에 다시 누르면 SetNumberPickerDialog 가 나올 수 있도록
        this.sectionOne.setClickedSetNumberPickerDialog(false);

    } // End of method [onDialogPositiveClick]

    @Override
    public void onDialogNegativeClick(SetNumberPickerDialog dialog) {

        final String METHOD_NAME = "[onDialogNegativeClick] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> set number picker dialog's negative button click >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        dialog.dismiss();

        // [iv/C]EPLSectionOneManager : SetNumberPickerDialog 가 종료 되었기 때문에 다시 누르면 SetNumberPickerDialog 가 나올 수 있도록
        this.sectionOne.setClickedSetNumberPickerDialog(false);

    } // End of method [onDialogNegativeClick]
//
//    @Override
//    public void onDialogCancel(DialogFragment dialogFragment) {
//
//        final String METHOD_NAME = "[onDialogNegativeClick] ";
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> set number picker dialog's onDialogCancel >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//        // [iv/C]EPLSectionOneManager : SetNumberPickerDialog 가 종료 되었기 때문에 다시 누르면 SetNumberPickerDialog 가 나올 수 있도록
//        this.sectionOne.setClickedSetNumberPickerDialog(false);
//
//        dialogFragment.dismiss();
//
//    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= RestTimePickerDialog.NoticeDialogListener =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Override
    public void onDialogPositiveClick(RestTimePickerDialog dialog, int restTimeMinuteNumber, int restTimeSecondNumber) {

        final String METHOD_NAME = "[onDialogPositiveClick] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> rest time picker dialog's positive button click >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // [iv/C]EPLSectionOneManager : section_1 의 restTimeMinute widget(TextView) 에 변경된 수로 설정한다.
        this.sectionOne.getRestTimeMinute().setText(String.format("%02d", restTimeMinuteNumber));

        // [iv/C]EPLSectionOneManager : section_1 의 restTimeMinute widget(TextView) 의 변경된 수가 적용되어 section_1 의 restTimeMinute 의 UP, DOWN 버튼을 클릭했을 때, 변경된 수가 적용되도록 하는 것이다.
        this.sectionOne.setRestTimeMinuteCounter(restTimeMinuteNumber);

        // [iv/C]EPLSectionOneManager : section_1 의 restTimeSecond widget(TextView) 에 변경된 수로 설정한다.
        this.sectionOne.getRestTimeSecond().setText(String.format("%02d", restTimeSecondNumber));

        // [iv/C]EPLSectionOneManager : section_1 의 restTimeSecond widget(TextView) 의 변경된 수가 적용되어 section_1 의 restTimeSecond 의 UP, DOWN 버튼을 클릭했을 때, 변경된 수가 적용되도록 하는 것이다.
        this.sectionOne.setRestTimeSecondCounter(restTimeSecondNumber);

        // [iv/C]EPLSectionOneManager : RestTimePickerDialog 가 종료 되었기 때문에 다시 누르면 RestTimePickerDialog 가 나올 수 있도록
        this.sectionOne.setClickedRestTimePickerDialog(false);

    } // End of method [onDialogPositiveClick]

    @Override
    public void onDialogNegativeClick(RestTimePickerDialog dialog) {

        final String METHOD_NAME = "[onDialogNegativeClick] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> rest time picker dialog's negative button click >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // [iv/C]EPLSectionOneManager : RestTimePickerDialog 가 종료 되었기 때문에 다시 누르면 RestTimePickerDialog 가 나올 수 있도록
        this.sectionOne.setClickedRestTimePickerDialog(false);

    } // End of method [onDialogNegativeClick]
}