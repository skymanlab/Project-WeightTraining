package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep7Fragment;

import java.util.ArrayList;

public class MakerStep6SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep6SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> finalOrderList;
    private ArrayList<MuscleArea> muscleAreaArrayList;

    // instance variable
    private NumberPicker programSettingSetNumber;
    private NumberPicker programSettingRestTimeMinute;
    private NumberPicker programSettingRestTimeSecond;

    // constructor
    public MakerStep6SectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setFinalOrderList(ArrayList<Event> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }

    public void setMuscleAreaArrayList(ArrayList<MuscleArea> muscleAreaArrayList) {
        this.muscleAreaArrayList = muscleAreaArrayList;
    }

    @Override
    public void connectWidget() {

        // [iv/C]NumberPicker : connect
        this.programSettingSetNumber = (NumberPicker) getView().findViewById(R.id.f_maker_step6_program_setting_set_number);

        // [iv/C]NumberPicker : programSettingRestTimeMinute connect
        this.programSettingRestTimeMinute = (NumberPicker) getView().findViewById(R.id.f_maker_step6_program_setting_rest_time_minute);

        // [iv/C]NumberPicker : programSettingRestTimeSecond connect
        this.programSettingRestTimeSecond = (NumberPicker) getView().findViewById(R.id.f_maker_step6_program_setting_rest_time_second);
    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // program setting set number
        initWidgetOfProgramSettingSetNumber();

        // program setting rest time minute
        initWidgetOfProgramSettingRestTimeMinute();

        // program setting rest time second
        initWidgetOfProgramSettingRestTimeSecond();

    }


    /**
     * 다음 단계를 진행하기 위한 과정을 EndButtonLister 객체를 생성하여 반환한다.
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        final String METHOD_NAME = "[newEndButtonListenerInstance] ";

        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                if (checkData()) {

                    getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_home_content_wrapper, MakerStep7Fragment.newInstance(
                                    finalOrderList,
                                    muscleAreaArrayList,
                                    getValueOfProgramSettingSetNumber(),
                                    getValueOfProgramSettingRestTimeMinute(),
                                    getValueOfProgramSettingRestTimeSecond()
                            ))
                            .addToBackStack(null)
                            .commit();
                } else {
                    Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step6_snack_setting_data_error, Snackbar.LENGTH_SHORT).show();
                }

                return null;
            }
        };
    }


    /**
     * ProgramSetting 의 setNumber widget 에 대한 초기 내용 설정
     */
    public void initWidgetOfProgramSettingSetNumber() {

        // [iv/C]NumberPicker : set number
        this.programSettingSetNumber.setMaxValue(9);
        this.programSettingSetNumber.setMinValue(0);

    }


    /**
     * ProgramSetting 의 restTimeMinute widget 에 대한 초기 내용 설정
     */
    private void initWidgetOfProgramSettingRestTimeMinute() {

        // [iv/C]NumberPicker : rest time minute
        this.programSettingRestTimeMinute.setMaxValue(59);
        this.programSettingRestTimeMinute.setMinValue(0);

    }


    /**
     * ProgramSetting 의 restTimeSecond widget 에 대한 초기 내용 설정
     */
    private void initWidgetOfProgramSettingRestTimeSecond() {

        // [iv/C]NumberPicker : rest time second
        this.programSettingRestTimeSecond.setMaxValue(59);
        this.programSettingRestTimeSecond.setMinValue(0);

    }

    /**
     * 다른 fragment 에서 programSettingSetNumber widget 의 값이 필요할 때, 해당 메소드의 반환값으로 넘겨준다.
     *
     * @return
     */
    public int getValueOfProgramSettingSetNumber() {

        return programSettingSetNumber.getValue();
    }


    /**
     * 다른 fragment 에서 programSettingRestTimeMinute widget 의 값이 필요할 때, 해당 메소드의 반환값으로 넘겨준다.
     *
     * @return
     */
    public int getValueOfProgramSettingRestTimeMinute() {
        return programSettingRestTimeMinute.getValue();
    }


    /**
     * 다른 fragment 에서 programSettingRestTimeSecond widget 의 값이 필요할 때, 해당 메소드의 반환값으로 넘겨준다.
     *
     * @return
     */
    public int getValueOfProgramSettingRestTimeSecond() {
        return programSettingRestTimeSecond.getValue();
    }

    /**
     * program setting 부분의 setNumber, restTimeMinute, restTimeSecond 의 적절한 데이터 변경이 있는지 검사하여 그 결과를 리턴한다.
     *
     * @return
     */
    private boolean checkData() {
        final String METHOD_NAME = "[checkData] ";

        // [check 1] : setNumber 는 0 이상일때만
        if (0 < this.programSettingSetNumber.getValue()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. set number 조건 통과");

            // '분' 이 0 이더라도 '초' 가 0 이상이면 조건 만족한다.
            boolean restTimeChecker1 = (0 == this.programSettingRestTimeMinute.getValue()) && (0 < this.programSettingRestTimeSecond.getValue()) ? true : false;
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. rest time checker 1 = " + restTimeChecker1);

            // '분' 이 0 이상이면 '초' 는 0 이거나 그 이상이면 조건에 만족한다.
            boolean restTimeChecker2 = (0 < this.programSettingRestTimeMinute.getValue()) && (0 <= this.programSettingRestTimeSecond.getValue()) ? true : false;
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. rest time checker 2 = " + restTimeChecker2);

            // [check 2] : restTimeMinute, restTimeSecond 는 restTime 으로 조건을 검사한다.
            if (restTimeChecker1 || restTimeChecker2) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. rest time 조건 통과");
                return true;

            } else {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. rest time 조건 불충분");
                return false;
            }

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. set number 조건 불충분");
            return false;

        } // [check 1]

    } // End of method [checkData]

}
