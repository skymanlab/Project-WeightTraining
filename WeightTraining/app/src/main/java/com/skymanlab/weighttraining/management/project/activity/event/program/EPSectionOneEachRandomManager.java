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
import com.skymanlab.weighttraining.management.project.activity.SectionManager;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.ProgramType;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;

public class EPSectionOneEachRandomManager  extends EPSectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEP] EPSectionOneEachRandomManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private GroupingEventData groupingEventData;

    // instance variable
    private TextView randomTitle;                           // title
    private LinearLayout randomAGroupWrapper;
    private LinearLayout randomBGroupWrapper;
    private LinearLayout randomCGroupWrapper;
    private LinearLayout randomDGroupWrapper;
    private LinearLayout randomEGroupWrapper;
    private Spinner randomAGroupCount;
    private Spinner randomBGroupCount;
    private Spinner randomCGroupCount;
    private Spinner randomDGroupCount;
    private Spinner randomEGroupCount;
    private View randomDivider;
    private LinearLayout randomProgramWrapper;              // program button wrapper
    private Button randomProgram;                           // program button

    // constructor
    public EPSectionOneEachRandomManager(Activity activity, FirebaseUser firebaseUser, MuscleArea muscleArea, GroupingEventData groupingEventData) {
        super(activity, firebaseUser, muscleArea);
        this.groupingEventData = groupingEventData;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]TextView : randomTitle mapping
        this.randomTitle = (TextView) super.getActivity().findViewById(R.id.event_program_section_1_random_title);                                      // title

        // [iv/C]LinearLayout : randomAGroupWrapper mapping / wrapper
        this.randomAGroupWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_random_a_group_wrapper);

        // [iv/C]LinearLayout : randomBGroupWrapper mapping / wrapper
        this.randomBGroupWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_random_b_group_wrapper);

        // [iv/C]LinearLayout : randomCGroupWrapper mapping / wrapper
        this.randomCGroupWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_random_c_group_wrapper);

        // [iv/C]LinearLayout : randomDGroupWrapper mapping / wrapper
        this.randomDGroupWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_random_d_group_wrapper);

        // [iv/C]LinearLayout : randomEGroupWrapper mapping / wrapper
        this.randomEGroupWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_random_e_group_wrapper);

        // [iv/C]Spinner : randomAGroupCount mapping / count
        this.randomAGroupCount = (Spinner) super.getActivity().findViewById(R.id.event_program_section_1_random_a_group_count);

        // [iv/C]Spinner : randomBGroupCount mapping / count
        this.randomBGroupCount = (Spinner) super.getActivity().findViewById(R.id.event_program_section_1_random_b_group_count);

        // [iv/C]Spinner : randomCGroupCount mapping / count
        this.randomCGroupCount = (Spinner) super.getActivity().findViewById(R.id.event_program_section_1_random_c_group_count);

        // [iv/C]Spinner : randomDGroupCount mapping / count
        this.randomDGroupCount = (Spinner) super.getActivity().findViewById(R.id.event_program_section_1_random_d_group_count);

        // [iv/C]Spinner : randomEGroupCount mapping /count
        this.randomEGroupCount = (Spinner) super.getActivity().findViewById(R.id.event_program_section_1_random_e_group_count);

        // [iv/C]View : randomDivider mapping
        this.randomDivider = (View) super.getActivity().findViewById(R.id.event_program_section_1_random_divider);

        // [iv/C]LinearLayout : randomProgramWrapper mapping
        this.randomProgramWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_random_bt_program_wrapper);            // program button wrapper

        // [iv/C]Button : randomProgram mapping
        this.randomProgram = (Button) super.getActivity().findViewById(R.id.event_program_section_1_random_bt_program);                                 // program button
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

        final String METHOD_NAME = "[initWidgetOfSectionOneRandom] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1_random 초기화 <<=======================");

        // [method] : aGroupEventArrayList 로 해당 widget 의 초기 설정을 한다.
        setWidgetOfGroupCount(groupingEventData.getAGroupEventArrayList(), this.randomAGroupWrapper, this.randomAGroupCount);

        // [method] : bGroupEventArrayList 로 해당 widget 의 초기 설정을 한다.
        setWidgetOfGroupCount(groupingEventData.getBGroupEventArrayList(), this.randomBGroupWrapper, this.randomBGroupCount);

        // [method] : cGroupEventArrayList 로 해당 widget 의 초기 설정을 한다.
        setWidgetOfGroupCount(groupingEventData.getCGroupEventArrayList(), this.randomCGroupWrapper, this.randomCGroupCount);

        // [method] : dGroupEventArrayList 로 해당 widget 의 초기 설정을 한다.
        setWidgetOfGroupCount(groupingEventData.getDGroupEventArrayList(), this.randomDGroupWrapper, this.randomDGroupCount);

        // [method] : eGroupEventArrayList 로 해당 widget 의 초기 설정을 한다.
        setWidgetOfGroupCount(groupingEventData.getEGroupEventArrayList(), this.randomEGroupWrapper, this.randomEGroupCount);


        // [iv/C]LinearLayout : muscleArea 에 따른 randomProgramWrapper 의 색 변경하기
        this.randomProgramWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [iv/C]Button : randomProgram click listener
        this.randomProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 각가의 그룹에서 랜덤으로 선택하고 합치기
                setClickListenerOfEachRandomProgram();

            }
        });
    }

    @Override
    void initWidgetWhenSizeIsZero() {

        final String METHOD_NAME = "[initWidgetOfSectionOneRandomWhenEventArrayListIsEmpty] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1_random / empty 초기화 <<=======================");

        // [iv/C]TextView : randomTitle 문자열 다시 설정
        this.randomTitle.setText(R.string.event_program_section_1_random_title_empty);

        // [ic/C]LinearLayout : randomAGroupWrapper 숨기기
        this.randomAGroupWrapper.setVisibility(LinearLayout.GONE);

        // [ic/C]LinearLayout : randomBGroupWrapper 숨기기
        this.randomBGroupWrapper.setVisibility(LinearLayout.GONE);

        // [ic/C]LinearLayout : randomCGroupWrapper 숨기기
        this.randomCGroupWrapper.setVisibility(LinearLayout.GONE);

        // [ic/C]LinearLayout : randomDGroupWrapper 숨기기
        this.randomDGroupWrapper.setVisibility(LinearLayout.GONE);

        // [ic/C]LinearLayout : randomEGroupWrapper 숨기기
        this.randomEGroupWrapper.setVisibility(LinearLayout.GONE);

        // [iv/C]View : randomDivider 숨기기
        this.randomDivider.setVisibility(View.GONE);

        // [iv/C]LinearLayout : muscleArea 에 따른 randomProgramWrapper 색 변경하기
        this.randomProgramWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [iv/C]Button : randomProgram 의 문자열 다시 설정 / click listener 설정
        this.randomProgram.setText(R.string.event_program_section_1_random_bt_program_empty);
        this.randomProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : EventAddActivity 로 이동
                moveEventAddActivity();
            }
        });

    }


    /**
     * [method] eventArrayList 를 유형별로 구분한 typeEvent 로 해당 widget 을 초기 설정한다.
     * typeEvent 의 size 가 0 이상이면, 개수 만큼 integer 를 추가하여 spinner 에 표시한다.
     * 만약 0 이면 하나의 type 을 감싸고 있는 LinearLayout 을 숨긴다.
     */
    private void setWidgetOfGroupCount(ArrayList<Event> typeEventArrayList, LinearLayout typeWrapper, Spinner typeCount) {

        final String METHOD_NAME = "[setWidgetOfGroupCount] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "********>> start <<********");

        // [check 1] : typeEvent 의 size 가 뭐냐?
        if (typeEventArrayList != null) {

            if (0 < typeEventArrayList.size()) {

                // [lv/C]ArrayAdapter : Integer 값을 추가하기 위한 adapter 생성
                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);

                // [cycle 1] : typeEvent 의 size 만큼
                for (int index = 0; index < typeEventArrayList.size(); index++) {

                    // [lv/C]ArrayAdapter : index 값 추가
                    adapter.add(index);

                } // [cycle 1]

                // [lv/C]ArrayAdapter : 마지막 총 개수 추가
                adapter.add(typeEventArrayList.size());

                // [lv/C]Spinner : 위의 adapter 를 연결하기
                typeCount.setAdapter(adapter);

            } else {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 배열의 size 가 0 입니다.<=");
                // [lv/C]LinearLayout : 해당 layout 을 사라지게 하기
                typeWrapper.setVisibility(LinearLayout.GONE);

            } // [check 1]

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 이 유형은 event 배열 객체가 없어요. <=");
            // [lv/C]LinearLayout : 해당 layout 을 사라지게 하기
            typeWrapper.setVisibility(LinearLayout.GONE);
        }

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "********>> end <<********");
    } // End of method [setWidgetOfGroupCount]


    /**
     * [method] randomProgram click listener
     */
    private void setClickListenerOfEachRandomProgram() {

        final String METHOD_NAME = "[setClickListenerOfEachRandomProgram] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> random program click listener / start <<++++++++++");

        // [lv/C]RandomEventSelectionUtil : 그룹 별 각자 랜덤으로 선택하기 위한
        RandomEventSelectionUtil eventSelectionUtil = new RandomEventSelectionUtil(ProgramType.EACH_RANDOM,
                getItemOfSpinner(this.randomAGroupCount),
                getItemOfSpinner(this.randomBGroupCount),
                getItemOfSpinner(this.randomCGroupCount),
                getItemOfSpinner(this.randomDGroupCount),
                getItemOfSpinner(this.randomEGroupCount));

        // [lv/C]RandomEventSelectionUtil : 구분된(그룹화된) eventArrayList 설정하기
        eventSelectionUtil.setGroupingEventData(this.groupingEventData);

        // [lv/C]RandomEventSelectionUtil : 그룹 별로 랜덤으로 선택하기
        eventSelectionUtil.selectRandomEvent();

        // [method] : randomSelectedEventArrayList 를 포함하여 EventSelectionProgramActivity 로 이동한다.
        moveEventSelectionProgramActivity(eventSelectionUtil.getRandomSelectedEventArrayList(), eventSelectionUtil.getNoSelectedEventArrayList());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> random program click listener / end <<++++++++++");

    } // End of method [setEachRandomProgram]

}
