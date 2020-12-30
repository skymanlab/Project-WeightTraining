package com.skyman.weighttrainingpro.management.project.activity.event.program;

import android.app.Activity;
import android.content.Intent;
import android.widget.Spinner;

import com.skyman.weighttrainingpro.EventAddActivity;
import com.skyman.weighttrainingpro.EventProgramListActivity;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.project.activity.SectionManager;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.ArrayList;

public abstract class EPSectionManager extends SectionManager {

    // constructor
    public static final int NOT_ZERO_TYPE_SECTION = 1;
    public static final int ZERO_TYPE_SECTION = 2;
    public static final Class NOT_ZERO_TYPE_CLASS_MOVE = EventProgramListActivity.class;
    public static final Class ZERO_TYPE_CLASS_MOVE = EventAddActivity.class;
    // constant
    private static final String CLASS_NAME = "[PAEP]_EPSectionOneEachRandomManager";       // EventProgramActivity Section One Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // instance variable
    private int sectionType;

    // constructor
    public EPSectionManager(Activity activity) {
        super(activity);
    }

    // constructor
    public EPSectionManager(Activity activity, User user, MuscleArea muscleArea) {
        super(activity, user, muscleArea);
    }

    // method : getter, setter
    public int getSectionType() {
        return sectionType;
    }

    public void setSectionType(int sectionType) {
        this.sectionType = sectionType;
    }

    // abstract method
    abstract void initWidgetWhenSizeIsNotZero();

    abstract void initWidgetWhenSizeIsZero();


    /**
     * [method] Spinner 의 객체가 있을 때, 그 선택 값을 가져오기
     */
    public int getItemOfSpinner(Spinner typeCount) {

        final String METHOD_NAME = "[getItemOfSpinner] ";

        // [lv/i]selectedItem : 선택한 값을 int type 으로 변환
        int selectedItem = 0;

        // [check 2] : position 이 0 보다 크거나 같다.
        if (0 <= typeCount.getSelectedItemPosition()) {

            // [lv/i]selectedItem : 해당 선택 값을 int type 으로 변환
            selectedItem = Integer.parseInt(typeCount.getSelectedItem().toString());

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. 선택한 위치의 값 = " + selectedItem + " 입니다.");

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 0 보다 작어! <=");
        } // [check 2]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "********>> end <<********");
        return selectedItem;
    } // End of method [getItemOfSpinner]


    /**
     * [method] move EventProgramListActivity.class
     */
    public void moveEventSelectionProgramActivity(ArrayList<Event> selectedEventArrayList, ArrayList<Event> noSelectedEventArrayList) {

        // [lv/C]Intent : EventSelectedProgramActivity 로 이동하기 위한
        Intent intent = new Intent(getActivity(), NOT_ZERO_TYPE_CLASS_MOVE);

        // [lv/C]SessionManager : intent 에 user, muscleArea, 무작위로 선택된 eventArrayList , 나머지 선택되지 않은 eventArrayList 를 추가한다.
        SessionManager.setUserInIntent(intent, getUser());
        SessionManager.setMuscleAreaInIntent(intent, getMuscleArea());
        SessionManager.setSelectedEventArrayListInIntent(intent, selectedEventArrayList);
        SessionManager.setNoSelectedEventArrayListInIntent(intent, noSelectedEventArrayList);

        // [method] : 이 Activity 를 stack 에서 제거 하며 이동
        getActivity().finish();
        getActivity().startActivity(intent);

    } // End of method [moveEventSelectionProgramActivity]


    /**
     * [method] move EventAddActivity when section Type is SECTION_ZERO_TYPE
     */
    public void moveEventAddActivity() {

        // [lv/C]Intent : EventAddActivity 로 이동하기 위한 객체 생성
        Intent intent = new Intent(getActivity(), ZERO_TYPE_CLASS_MOVE);

        // [lv/C]SessionManager : "user" 와 "muscleArea" 을 intent 에 추가하기
        SessionManager.setUserInIntent(intent, getUser());
        SessionManager.setMuscleAreaInIntent(intent, getMuscleArea());

        // [method] : EventAddActivity 이동
        getActivity().startActivity(intent);


    } // End of method [moveEventAddActivity]

}
