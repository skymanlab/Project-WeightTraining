package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.DirectSelectionGroupItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DirectSelectionSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] DirectSelectionSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final int STANDARD_ID_CHEST = 100;
    private static final int STANDARD_ID_SHOULDER = 200;
    private static final int STANDARD_ID_LAT = 300;
    private static final int STANDARD_ID_UPPER_BODY = 400;
    private static final int STANDARD_ID_ARM = 500;
    private static final int STANDARD_ID_ETC = 600;

    // instance variable
    private GroupingEventData groupingEventData = null;
    private int muscleAreaStandardId = 0;

    // instance variable
    private LinearLayout groupListWrapper;
    private HashMap<GroupType, DirectSelectionGroupItem> groupItemList;

    // constructor
    public DirectSelectionSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setGroupingEventData(GroupingEventData groupingEventData) {
        this.groupingEventData = groupingEventData;
    }

    @Override
    public void connectWidget() {

        // [iv/C]LinearLayout : groupListWrapper connection
        this.groupListWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_group_list_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>+_+_+_+_+_+_+_+_+_+_ DirectSelectionSectionManager 2. initWidget");

        if (this.groupingEventData != null && (0 < this.muscleAreaStandardId)) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>+_+_+_+_+_+_+_+_+_+_ DirectSelectionSectionManager 2. initWidget 조건 성립");

            // [iv/C]HashMap<GroupType, DirectSelectionGroupItem> :
            this.groupItemList = new HashMap<GroupType, DirectSelectionGroupItem>();

            // [lv/C]LayoutInflater :
            LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // [1] a group
            initGroupListWrapper(inflater, GroupType.A_GROUP, this.groupingEventData.getAGroupEventArrayList());

            // [2] b group
            initGroupListWrapper(inflater, GroupType.B_GROUP, this.groupingEventData.getBGroupEventArrayList());

            // [3] c group
            initGroupListWrapper(inflater, GroupType.C_GROUP, this.groupingEventData.getCGroupEventArrayList());

            // [4] d group
            initGroupListWrapper(inflater, GroupType.D_GROUP, this.groupingEventData.getDGroupEventArrayList());

            // [5] e group
            initGroupListWrapper(inflater, GroupType.E_GROUP, this.groupingEventData.getEGroupEventArrayList());

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++>> groupingEventData 를 먼저 설정해주세요.");
        }

    }


    /**
     * muscleArea 에 따른 muscleAreaStandardId 를 할당한다.
     *
     * @param muscleArea 근육 부위
     */
    public void setMuscleAreaStandardId(MuscleArea muscleArea) {

        // [switch 1] : muscleArea 를 구분하여 muscleAreaStandardId 를 할당한다.
        switch (muscleArea) {
            case CHEST:
                // [iv/i]muscleAreaStandardId : CHEST standardId 할당
                this.muscleAreaStandardId = STANDARD_ID_CHEST;
                break;
            case SHOULDER:
                // [iv/i]muscleAreaStandardId : SHOULDER standardId 할당
                this.muscleAreaStandardId = STANDARD_ID_SHOULDER;
                break;
            case LAT:
                // [iv/i]muscleAreaStandardId : LAT standardId 할당
                this.muscleAreaStandardId = STANDARD_ID_LAT;
                break;
            case UPPER_BODY:
                // [iv/i]muscleAreaStandardId : UPPER_BODY standardId 할당
                this.muscleAreaStandardId = STANDARD_ID_UPPER_BODY;
                break;
            case LEG:
                // [iv/i]muscleAreaStandardId : LEG standardId 할당
                this.muscleAreaStandardId = STANDARD_ID_UPPER_BODY;
            case ARM:
                // [iv/i]muscleAreaStandardId : ARM standardId 할당
                this.muscleAreaStandardId = STANDARD_ID_ARM;
                break;
            case ETC:
                // [iv/i]muscleAreaStandardId : ETC standardId 할당
                this.muscleAreaStandardId = STANDARD_ID_ETC;
                break;
        } // [switch 1]

    } // End of method [setMuscleAreaStandardId]


    /**
     * groupListWrapper 에 groupItem 을 화면에 표시하는 초기 내용을 설정한다.
     *
     * @param inflater
     * @param groupType
     * @param groupEventArrayList
     */
    private void initGroupListWrapper(LayoutInflater inflater, GroupType groupType, ArrayList<Event> groupEventArrayList) {
        final String METHOD_NAME = "[initGroupListWrapper] ";

        if (groupEventArrayList != null) {

            if (!groupEventArrayList.isEmpty()) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------------==>> group item 생성 ");
                // DirectSelectionGroupItem 으로 groupItem 을 생성하기
                DirectSelectionGroupItem groupItem = createDirectSelectionGroupItem(inflater, groupType, groupEventArrayList);

                // groupItemList 에 추가하기
                addItemToGroupItemList(groupType, groupItem);

                // groupListWrapper 에 추가하여 화면에 표시하기
                addViewToGroupListWrapper(groupItem);
            }
        }
    } // End of method [initGroupListWrapper]


    /**
     * 각 그룹별로 groupItem 을 DirectSelectionGroupItem 을 이용하여 객체를 생성한다.
     *
     * @param inflater
     * @param groupType
     * @param groupEventArrayList
     * @return
     */
    private DirectSelectionGroupItem createDirectSelectionGroupItem(LayoutInflater inflater, GroupType groupType, ArrayList<Event> groupEventArrayList) {

        // [lv/C]DirectSelectionGroupItem :
        DirectSelectionGroupItem groupItem = new DirectSelectionGroupItem.Builder(getFragment().getActivity())
                .setInflater(inflater)
                .setGroupType(groupType)
                .setGroupEventArrayList(groupEventArrayList)
                .setStandardId(muscleAreaStandardId)
                .create();

        groupItem.createItem();

        return groupItem;

    } // End of method [createDirectSelectionGroupItem]


    /**
     * DirectSelectionGroupItem 객체를 groupItemList 에 추가하기
     *
     * @param groupType
     * @param groupItem
     */
    private void addItemToGroupItemList(GroupType groupType, DirectSelectionGroupItem groupItem) {
        this.groupItemList.put(groupType, groupItem);
    } // End of method [addItemToGroupItemList]


    /**
     * DirectSelectionGroupItem 객체의 view(item) 을 groupListWrapper 에 추가하여 화면에 표시하기
     *
     * @param groupItem
     */
    private void addViewToGroupListWrapper(DirectSelectionGroupItem groupItem) {
        this.groupListWrapper.addView(groupItem.getItem());
    } // End of method [addViewToGroupListWrapper]


    /**
     * 각 그룹의 EventResultSet 을 모두 합하여 반환한다.
     *
     * @return
     */
    public EventResultSet getEventResultSetOfAllGroup() {

        // [lv/C]EventResultSet : 각 그룹의 EventResultSet 을 모두 합하기 위해서
        EventResultSet allGroupEventResultSet = new EventResultSet();

        // a group
        if (groupItemList.get(GroupType.A_GROUP) != null) {

            // [lv/C]EventResultSet : a group 의 eventResultSet 가져오기
            EventResultSet aGroup = groupItemList.get(GroupType.A_GROUP).getEventResultSet();

            // [lv/C]EventResultSet : 합치기
            allGroupEventResultSet.getSelectedEventArrayList().addAll(aGroup.getSelectedEventArrayList());
            allGroupEventResultSet.getNoSelectedEventArrayList().addAll(aGroup.getNoSelectedEventArrayList());

        }

        // b group
        if (groupItemList.get(GroupType.B_GROUP) != null) {

            // [lv/C]EventResultSet : b group 의 eventResultSet 가져오기
            EventResultSet bGroup = groupItemList.get(GroupType.B_GROUP).getEventResultSet();

            // [lv/C]EventResultSet : 합치기
            allGroupEventResultSet.getSelectedEventArrayList().addAll(bGroup.getSelectedEventArrayList());
            allGroupEventResultSet.getNoSelectedEventArrayList().addAll(bGroup.getNoSelectedEventArrayList());

        }

        // c group
        if (groupItemList.get(GroupType.C_GROUP) != null) {

            // [lv/C]EventResultSet : c group 의 eventResultSet 가져오기
            EventResultSet cGroup = groupItemList.get(GroupType.C_GROUP).getEventResultSet();

            // [lv/C]EventResultSet : 합치기
            allGroupEventResultSet.getSelectedEventArrayList().addAll(cGroup.getSelectedEventArrayList());
            allGroupEventResultSet.getNoSelectedEventArrayList().addAll(cGroup.getNoSelectedEventArrayList());

        }

        // d group
        if (groupItemList.get(GroupType.D_GROUP) != null) {

            // [lv/C]EventResultSet : d group 의 eventResultSet 가져오기
            EventResultSet dGroup = groupItemList.get(GroupType.D_GROUP).getEventResultSet();

            // [lv/C]EventResultSet : 합치기
            allGroupEventResultSet.getSelectedEventArrayList().addAll(dGroup.getSelectedEventArrayList());
            allGroupEventResultSet.getNoSelectedEventArrayList().addAll(dGroup.getNoSelectedEventArrayList());

        }

        // e group
        if (groupItemList.get(GroupType.E_GROUP) != null) {

            // [lv/C]EventResultSet : e group 의 eventResultSet 가져오기
            EventResultSet eGroup = groupItemList.get(GroupType.E_GROUP).getEventResultSet();

            // [lv/C]EventResultSet : 합치기
            allGroupEventResultSet.getSelectedEventArrayList().addAll(eGroup.getSelectedEventArrayList());
            allGroupEventResultSet.getNoSelectedEventArrayList().addAll(eGroup.getNoSelectedEventArrayList());

        }

        return allGroupEventResultSet;
    } // End of method [getEventResultSetOfAllGroup]


}
