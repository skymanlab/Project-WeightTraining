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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.Step6EventRvAdapter;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.dialog.Step6DetailProgramSettingDialog;

import java.util.ArrayList;

public class MakerStep6SectionManager extends FragmentSectionManager implements FragmentSectionInitializable,
        MakerStepManager.OnPreviousClickListener,
        MakerStepManager.OnNextClickListener {


    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep6SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Fragment fragment;

    // instance variable
    private MakerStepManager makerStepManager;

    // instance variable
    private ArrayList<Event> finalOrderList;

    // instance variable
    private NumberPicker programSettingSetNumber;
    private NumberPicker programSettingRestTimeMinute;
    private NumberPicker programSettingRestTimeSecond;
    private RecyclerView eventListWrapper;

    // instance variable
    private ArrayList<DetailProgram> detailProgramArrayList;

    // constructor
    public MakerStep6SectionManager(Activity activity, View view, FragmentManager fragmentManager, Fragment fragment) {
        super(activity, view, fragmentManager);
        this.fragment = fragment;
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

        // [iv/C]MakerStepManager : step 6
        this.makerStepManager = new MakerStepManager(getView(), getFragmentManager(), MakerStepManager.STEP_SIX);
        this.makerStepManager.setPreviousClickListener(this);
        this.makerStepManager.setNextClickListener(this);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

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
        initWidgetOfRecyclerView(this.finalOrderList);

        // [iv/C]ArrayList<DetailProgram> : 프로그램의 특정 event 의 프로그램만 설정값을 다르게 할 때, 해당 부분의 세부 프로그램으로 저장
        this.detailProgramArrayList = new ArrayList<>();

        // [iv/C]Fragment : MakerStep6Fragment 의 FragmentManger 를 통해 Step6DetailProgramSettingDialog 에서 보낸 데이터 가져오기
        fragment.getParentFragmentManager().setFragmentResultListener(Step6DetailProgramSettingDialog.REQUEST_KEY, fragment, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                DetailProgram detailProgram = new DetailProgram();
                detailProgram.setKey(result.getString(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_EVENT_KEY));
                detailProgram.setSetNumber(result.getInt(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_SET_NUMBER));
                detailProgram.setRestTimeMinute(result.getInt(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_REST_TIME_MINUTE));
                detailProgram.setRestTimeSecond(result.getInt(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_REST_TIME_SECOND));

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "0. event key  = " + result.getString(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_EVENT_KEY));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. setNumber = " + result.getInt(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_SET_NUMBER));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. restTimeMinute = " + result.getInt(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_REST_TIME_MINUTE));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. restTimeSecond = " + result.getInt(Step6DetailProgramSettingDialog.DETAIL_PROGRAM_REST_TIME_SECOND));
            }
        });

    }

    @Override
    public void setClickListenerOfNext() {

        // [check 1] : program setting 값이 모두 0 이면 -> 아직 설정을 하지 않은 경우다.
        if ((0 == this.programSettingSetNumber.getValue())
                && (0 == this.programSettingRestTimeMinute.getValue())
                && (0 == this.programSettingRestTimeSecond.getValue())) {

        } else {
            // [lv/C]Program : 해당 프로그램의 설정값으로 객체를 생성한다.
            Program program = new Program();
            program.setSetNumber(this.programSettingSetNumber.getValue());
            program.setRestTimeMinute(this.programSettingRestTimeMinute.getValue());
            program.setRestTimeSecond(this.programSettingRestTimeSecond.getValue());

        } // [check 1]

    }

    @Override
    public AlertDialog setClickListenerOfPrevious() {
        return null;
    }


    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initWidgetOfRecyclerView(ArrayList<Event> finalOrderList) {

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.eventListWrapper.setLayoutManager(layoutManager);

        // [iv/C]SelectedEventItemRvAdapter : recyclerView 의 adapter 생성
        Step6EventRvAdapter adapter = new Step6EventRvAdapter.Builder()
                .setFragmentManager(getFragmentManager())
                .setFinalOrderList(this.finalOrderList)
                .create();

        // [iv/C] : recyclerView 의 adapter setting
        this.eventListWrapper.setAdapter(adapter);

    } // End of method [initWidgetOfRecyclerView]


    public int getDataOfProgramSettingSetNumber() {

        return programSettingSetNumber.getValue();
    }

    public int getDataOfProgramSettingRestTimeMinute() {
        return programSettingRestTimeMinute.getValue();
    }


    public int getDataOfProgramSettingRestTimeSecond() {
        return programSettingRestTimeSecond.getValue();
    }
}
