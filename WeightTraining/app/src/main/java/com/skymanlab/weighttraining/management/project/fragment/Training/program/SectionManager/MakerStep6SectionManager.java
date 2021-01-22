package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep7Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.Step6EventRvAdapter;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.dialog.Step6DetailProgramDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class MakerStep6SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep6SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> finalOrderList;

    // instance variable
    private NumberPicker programSettingSetNumber;
    private NumberPicker programSettingRestTimeMinute;
    private NumberPicker programSettingRestTimeSecond;
    private RecyclerView eventListWrapper;

    // instance variable
    private HashMap<String, DetailProgram> detailProgramList;

    // constructor
    public MakerStep6SectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setFinalOrderList(ArrayList<Event> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }


    @Override
    public void connectWidget() {

        // [iv/C]NumberPicker : connect
        this.programSettingSetNumber = (NumberPicker) getView().findViewById(R.id.f_maker_step6_program_setting_set_number);

        // [iv/C]NumberPicker : programSettingRestTimeMinute connect
        this.programSettingRestTimeMinute = (NumberPicker) getView().findViewById(R.id.f_maker_step6_program_setting_rest_time_minute);

        // [iv/C]NumberPicker : programSettingRestTimeSecond connect
        this.programSettingRestTimeSecond = (NumberPicker) getView().findViewById(R.id.f_maker_step6_program_setting_rest_time_second);

        // [iv/C]RecyclerView :
        this.eventListWrapper = (RecyclerView) getView().findViewById(R.id.f_maker_step6_detail_program_setting_event_list_wrapper);
    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]HashMap<String, DetailProgram> : 프로그램의 특정 event 의 프로그램만 설정값을 다르게 할 때, 해당 부분의 세부 프로그램으로 저장
        this.detailProgramList = new HashMap<>();

        // [iv/C]NumberPicker : set number
        this.programSettingSetNumber.setMaxValue(9);
        this.programSettingSetNumber.setMinValue(0);

        // [iv/C]NumberPicker : rest time minute
        this.programSettingRestTimeMinute.setMaxValue(59);
        this.programSettingRestTimeMinute.setMinValue(0);

        // [iv/C]NumberPicker : rest time second
        this.programSettingRestTimeSecond.setMaxValue(59);
        this.programSettingRestTimeSecond.setMinValue(0);

        // Step6EventRvAdapter 의 객체를 생성하고 recyclerView 의 초기 내용을 설정한다.
        initWidgetOfRecyclerView();

        // [iv/C]Fragment : MakerStep6Fragment 의 FragmentManger 를 통해 Step6DetailProgramSettingDialog 에서 보낸 데이터 가져오기
        getFragment().getParentFragmentManager().setFragmentResultListener(Step6DetailProgramDialog.REQUEST_KEY, getFragment(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                // dialog 에서 key, setNumber, restTimeMinute, restTimeSecond 가져오기
                String dialogKey = result.getString(Step6DetailProgramDialog.DETAIL_PROGRAM_EVENT_KEY);
                int dialogSetNumber = result.getInt(Step6DetailProgramDialog.DETAIL_PROGRAM_SET_NUMBER);
                int dialogRestTimeMinute = result.getInt(Step6DetailProgramDialog.DETAIL_PROGRAM_REST_TIME_MINUTE);
                int dialogRestTimeSecond = result.getInt(Step6DetailProgramDialog.DETAIL_PROGRAM_REST_TIME_SECOND);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "0. 해당 key 의 DetailProgram 객체는 = " + detailProgramList.get(result.getString(Step6DetailProgramDialog.DETAIL_PROGRAM_EVENT_KEY)));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-1. dialog 에서 가져온 key = " + dialogKey);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-2. dialog 에서 가져온 setNumber = " + dialogSetNumber);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-3. dialog 에서 가져온 restTimeMinute = " + dialogRestTimeMinute);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-4. dialog 에서 가져온 restTimeSecond = " + dialogRestTimeSecond);


                // [check 1] : detailProgram 의 key 에 해당하는 객체가 없을 때만
                if (detailProgramList.get(result.getString(Step6DetailProgramDialog.DETAIL_PROGRAM_EVENT_KEY)) == null) {

                    // detailProgram 는 null 이다.
                    // [check 2] : dialog 와 step 6 fragment 의 program 설정 값들의 비교하여 변경 유무를 판별한다.
                    if ((programSettingSetNumber.getValue() == dialogSetNumber)
                            && (programSettingRestTimeMinute.getValue() == dialogRestTimeMinute)
                            && (programSettingRestTimeSecond.getValue() == dialogRestTimeSecond)) {

                        // 변경된 값이 없는 경우
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. 변경된 값이 없음");

                    } else {

                        // 하나라도 변경이 되었을 경우 : 새로운 DetailProgram 객체를 만들어서 detailProgramList 에 추가한다.
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. 변경된 값이 있어서 DetailProgram 객체를 생성");

                        // [lv/C]DetailProgram : 개별 설정된 event 의 프로그램 내용으로 DetailProgram 객체 생성
                        DetailProgram detailProgram = new DetailProgram();
                        detailProgram.setKey(dialogKey);
                        detailProgram.setSetNumber(dialogSetNumber);
                        detailProgram.setRestTimeMinute(dialogRestTimeMinute);
                        detailProgram.setRestTimeSecond(dialogRestTimeSecond);

                        // [iv/C]ArrayList<DetailProgram> : 위의 detailProgram 을 추가한다.
                        detailProgramList.put(dialogKey, detailProgram);

                    } // [check 2]

                } else {

                    // detailProgram 가 존재한다. : 이 event 는 detail program 이 설정된 적이 있다.

                    // detailProgram 에서 기존의 설정값 가져오기
                    int setNumber = detailProgramList.get(dialogKey).getSetNumber();
                    int restTimeMinute = detailProgramList.get(dialogKey).getRestTimeMinute();
                    int restTimeSecond = detailProgramList.get(dialogKey).getRestTimeSecond();

                    // [check 3] : dialog 와 detailProgram(기존의 생성된) 의 program 설정 값들을 비교하여 변경 유무를 판별한다.
                    if ((setNumber == dialogSetNumber) && (restTimeMinute == dialogRestTimeMinute) && (restTimeSecond == dialogRestTimeSecond)) {

                        // 변경된 값이 없는 경우.
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-3. 변경된 값이 없어요");

                    } else {

                        // 하나라도 변경이 되었을 경우 : 기존의 detailProgram 에 dialog 의 program 설정 값을 수정한다.
                        detailProgramList.get(dialogKey).setSetNumber(dialogSetNumber);
                        detailProgramList.get(dialogKey).setRestTimeMinute(dialogRestTimeMinute);
                        detailProgramList.get(dialogKey).setRestTimeSecond(dialogRestTimeSecond);

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-3. 수정된 값을 적용해야 해요!");
                    } // [check 3]

                } // [check 1]
            }
        });

    }


    /**
     * 다음 단계를 진행하기 위한 과정을 EndButtonLister 객체를 생성하여 반환한다.
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {

                // [check 1] : set number 는 0 이상 이어야 하고, rest time 은 0 초 이상이어야 한다.
                if (checkData()) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. program 을 생성합니다.");

                    // [lv/C]Program : 해당 프로그램의 설정값으로 객체를 생성한다.
                    Program program = new Program();
                    program.setSetNumber(programSettingSetNumber.getValue());
                    program.setRestTimeMinute(programSettingRestTimeMinute.getValue());
                    program.setRestTimeSecond(programSettingRestTimeSecond.getValue());

                    // [lv/C]MakerStep7Fragment : step 7 fragment 생성
                    MakerStep7Fragment step7Fragment = MakerStep7Fragment.newInstance(finalOrderList, program, detailProgramList);

                    // [lv/C]FragmentTransaction : step 7 fragment 로 이동
                    FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_home_content_wrapper, step7Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {

                    Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step6_snack_setting_data_error, Snackbar.LENGTH_SHORT).show();

                } // [check 1]

                return null;
            }
        };
    }


    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initWidgetOfRecyclerView() {

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getFragment().getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.eventListWrapper.setLayoutManager(layoutManager);

        // [iv/C]SelectedEventItemRvAdapter : recyclerView 의 adapter 생성
        Step6EventRvAdapter adapter = new Step6EventRvAdapter.Builder()
                .setFragment(getFragment())
                .setFinalOrderList(this.finalOrderList)
                .setDetailProgramList(this.detailProgramList)
                .create();

        // [iv/C] : recyclerView 의 adapter setting
        this.eventListWrapper.setAdapter(adapter);

    } // End of method [initWidgetOfRecyclerView]


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
