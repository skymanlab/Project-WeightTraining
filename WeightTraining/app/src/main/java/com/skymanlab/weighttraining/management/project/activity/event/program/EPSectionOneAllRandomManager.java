package com.skymanlab.weighttraining.management.project.activity.event.program;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.util.RandomEventSelectionUtil;
import com.skymanlab.weighttraining.management.project.activity.SectionInitialization;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.ProgramType;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;


public class EPSectionOneAllRandomManager extends EPSectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEP] EPSectionOneAllRandomManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private GroupingEventData groupingEventData;

    // instance variable
    private TextView allRandomTitle;                        // title
    private LinearLayout allRandomGroupTotalCountWrapper;
    private Spinner allRandomGroupTotalCount;
    private View allRandomDivider;
    private LinearLayout allRandomProgramWrapper;           // program button wrapper
    private Button allRandomProgram;                        // program button

    // constructor
    public EPSectionOneAllRandomManager(Activity activity, FirebaseUser firebaseUser, MuscleArea muscleArea, ArrayList<Event> eventArrayList, GroupingEventData groupingEventData) {
        super(activity, firebaseUser, muscleArea);
        this.eventArrayList = eventArrayList;
        this.groupingEventData = groupingEventData;
    }

    @Override
    public void connectWidget() {

        // [iv/C]TextView : allRandomTitle connect
        this.allRandomTitle = (TextView) super.getActivity().findViewById(R.id.event_program_section_1_all_random_title);                               // title

        // [iv/C]LinearLayout :  connect
        this.allRandomGroupTotalCountWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_all_random_group_total_count_wrapper);

        // [iv/C]Spinner : allRandomGroupTotalCount connect
        this.allRandomGroupTotalCount = (Spinner) super.getActivity().findViewById(R.id.event_program_section_1_all_random_group_total_count);

        // [iv/C]View : allRandomDivider connect
        this.allRandomDivider = (View) super.getActivity().findViewById(R.id.event_program_section_1_all_random_divider);

        // [iv/C]LinearLayout : allRandomProgramWrapper connect
        this.allRandomProgramWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_all_random_bt_program_wrapper);     // program button wrapper

        // [iv/C]Button : allRandomProgram connect
        this.allRandomProgram = (Button) super.getActivity().findViewById(R.id.event_program_section_1_all_random_bt_program);                          // program button

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> sectionType = " + getSectionType());

        // [check 1] : sectionType 이 뭐냐?
        if (super.getSectionType() == EPSectionManager.NOT_ZERO_TYPE_SECTION) {

            // [method] : eventArrayList 의 size 가 0 일때의 widget 초기화
            initWidgetWhenSizeIsNotZero();

        } else if (super.getSectionType() == EPSectionManager.ZERO_TYPE_SECTION) {

            // [method] : eventArrayList 의 size 가 0 일때의 widget 초기화
            initWidgetWhenSizeIsZero();

        } // [check 1]
    }

    @Override
    void initWidgetWhenSizeIsNotZero() {

        final String METHOD_NAME = "[initWidgetOfSectionOneAllRandom] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1_all_random 초기화 <<=======================");

        // [iv/C]LinearLayout : muscleArea 에 따른 allRandomProgramWrapper 배경색을 변경하기
        this.allRandomProgramWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [method] : allRandomGroupTotalCount 을 eventArrayList 을 이용하여 초기 설정하기
        setAdapterOfAllRandomGroupTotalCount(this.eventArrayList, this.allRandomGroupTotalCount);

        // [iv/C]Button : allRandomProgram click listener
        this.allRandomProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 모든 그룹에서 랜덤으로 선택하기
                setClickListenerOfAllRandomProgram();

            }
        });
    }

    @Override
    void initWidgetWhenSizeIsZero() {

        final String METHOD_NAME = "[initWidgetOfSectionOneAllRandomWhenEventArrayListIsEmpty] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1_all_random / empty 초기화 <<=======================");

        // [iv/C]TextView : allRandomTitle 문자열 다시 설정
        this.allRandomTitle.setText(R.string.event_program_section_1_all_random_title_empty);

        // [iv/C]LinearLayout : allRandomGroupTotalCountWrapper 없애기
        this.allRandomGroupTotalCountWrapper.setVisibility(LinearLayout.GONE);

        // [iv/C]View : allRandomDivider 없애기
        this.allRandomDivider.setVisibility(View.GONE);

        // [iv/C]LinearLayout : muscleArea 에 따른 allRandomProgramWrapper 색 변경하기
        this.allRandomProgramWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [iv/C]Button : allRandomProgram 의 문자열 다시 설정 / click listener 설정
        this.allRandomProgram.setText(R.string.event_program_section_1_all_random_bt_program_empty);
        this.allRandomProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> allRandomProgram program click listener / start <<++++++++++");
                // [method] : EventAddActivity 로 이동
                moveEventAddActivity();
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> allRandomProgram program click listener / end <<++++++++++");

            }
        });
    }


    /**
     * [method] allRandomTypeTotalCount spinner 를 adapter 와 연결하기
     */
    private void setAdapterOfAllRandomGroupTotalCount(ArrayList<Event> groupTypeEventArrayList, Spinner allRandomGroupTotalCount) {

        final String METHOD_NAME = "[setAdapterOfAllRandomGroupTotalCount] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "********>> start <<********");


        // [check 1] :
        if (groupTypeEventArrayList != null) {

            // [check 2] :
            if (0 < groupTypeEventArrayList.size()) {

                // [lv/C]ArrayAdapter : int 값을 추가하기 위한 adapter 객체 생성
                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);

                // [cycle 1] : eventArrayList 의 size 만큼
                for (int index = 0; index < this.eventArrayList.size(); index++) {

                    // [lv/C]ArrayAdapter : adapter 에 index 값 추가
                    adapter.add(index);

                } // [cycle 1]

                // [lv/C]ArrayAdapter : adapter 에 size 값 추가
                adapter.add(groupTypeEventArrayList.size());

                // [lv/C]Spinner : 위 adapter 와 연결하기
                allRandomGroupTotalCount.setAdapter(adapter);

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 배열의 size 가 0 입니다.<=");
            } // [check 1]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 이 유형은 event 배열 객체가 없어요. <=");
        } // [check 1]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "********>> end <<********");
    } // End of method [setAdapterOfAllRandomGroupTotalCount]


    /**
     * [method] allRandomProgram click listener
     */
    private void setClickListenerOfAllRandomProgram() {

        final String METHOD_NAME = "[setClickListenerOfAllRandomProgram] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> allRandomProgram program click listener / start <<++++++++++");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. 몇 개를 선택하시겠습니까? = " + getItemOfSpinner(allRandomGroupTotalCount));

        // [lv/C]RandomEventSelectionUtil : 전체 그룹에서 랜덤으로 선택하기 위한 객체 생성
        RandomEventSelectionUtil eventSelectionUtil = new RandomEventSelectionUtil(ProgramType.ALL_RANDOM, getItemOfSpinner(allRandomGroupTotalCount));

        // [lv/C]RandomEventSelectionUtil : 구분된(그룹화된) eventArrayList 를 입력하기
        eventSelectionUtil.setGroupingEventData(this.groupingEventData);

        // [lv/C]RandomEventSelectionUtil : 전체 그룹에서 랜덤으로 선택하기
        eventSelectionUtil.selectRandomEvent();

        // [method] : randomSelectedEventArrayList 를 포함하여 EventSelectionProgramActivity 로 이동한다.
        moveEventSelectionProgramActivity(eventSelectionUtil.getSelectedEventArrayList(), eventSelectionUtil.getNoSelectedEventArrayList());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> allRandomProgram program click listener / end <<++++++++++");

    } // End of method [setClickListenerOfAllRandomProgram]

}
