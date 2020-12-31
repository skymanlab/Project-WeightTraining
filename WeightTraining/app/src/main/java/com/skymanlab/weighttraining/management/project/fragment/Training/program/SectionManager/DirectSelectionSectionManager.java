package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DirectSelectionSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTS] Step3D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final int STANDARD_ID_CHEST = 100;
    private static final int STANDARD_ID_SHOULDER = 200;
    private static final int STANDARD_ID_LAT = 300;
    private static final int STANDARD_ID_UPPER_BODY = 400;
    private static final int STANDARD_ID_ARM = 500;
    private static final int STANDARD_ID_ETC = 600;

    // constant
    private static final int STANDARD_ID_A_GROUP = 10;
    private static final int STANDARD_ID_B_GROUP = 20;
    private static final int STANDARD_ID_C_GROUP = 30;
    private static final int STANDARD_ID_D_GROUP = 40;
    private static final int STANDARD_ID_E_GROUP = 50;

    // instance variable
    private MaterialCardView aGroupWrapper;
    private LinearLayout aGroupItemWrapper;
    private int[] aGroupCheckBoxIdList;
    private MaterialCardView bGroupWrapper;
    private LinearLayout bGroupItemWrapper;
    private int[] bGroupCheckBoxIdList;
    private MaterialCardView cGroupWrapper;
    private LinearLayout cGroupItemWrapper;
    private int[] cGroupCheckBoxIdList;
    private MaterialCardView dGroupWrapper;
    private LinearLayout dGroupItemWrapper;
    private int[] dGroupCheckBoxIdList;
    private MaterialCardView eGroupWrapper;
    private LinearLayout eGroupItemWrapper;
    private int[] eGroupCheckBoxIdList;

    // instance variable
    private GroupingEventData groupingEventData = null;
    private int muscleAreaStandardId = 0;
    private HashMap<Integer, Event> checkedEventList = new HashMap<>();

    // constructor
    public DirectSelectionSectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }


    @Override
    public void mappingWidget() {


        // [iv/C]MaterialCardView : mapping
        this.aGroupWrapper = (MaterialCardView) getView().findViewById(R.id.f_direct_selection_a_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.aGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_a_group_item_wrapper);

        // [iv/C]MaterialCardView : mapping
        this.bGroupWrapper = (MaterialCardView) getView().findViewById(R.id.f_direct_selection_b_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.bGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_b_group_item_wrapper);

        // [iv/C]MaterialCardView : mapping
        this.cGroupWrapper = (MaterialCardView) getView().findViewById(R.id.f_direct_selection_c_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.cGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_c_group_item_wrapper);

        // [iv/C]MaterialCardView : mapping
        this.dGroupWrapper = (MaterialCardView) getView().findViewById(R.id.f_direct_selection_d_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.dGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_d_group_item_wrapper);

        // [iv/C]MaterialCardView : mapping
        this.eGroupWrapper = (MaterialCardView) getView().findViewById(R.id.f_direct_selection_e_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.eGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_e_group_item_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        if (this.groupingEventData != null && (0 < this.muscleAreaStandardId)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++>> muscleAreaStandardId = " + this.muscleAreaStandardId);

            // [method] : A group 의 데이터를 표시하는 과정 진행
            makeGroupCheckBox(this.aGroupWrapper, this.aGroupItemWrapper, this.groupingEventData.getAGroupEventArrayList(), STANDARD_ID_A_GROUP);

            // [method] : B group 의 데이터를 표시하는 과정 진행
            makeGroupCheckBox(this.bGroupWrapper, this.bGroupItemWrapper, this.groupingEventData.getBGroupEventArrayList(), STANDARD_ID_B_GROUP);

            // [method] : C group 의 데이터를 표시하는 과정 진행
            makeGroupCheckBox(this.cGroupWrapper, this.cGroupItemWrapper, this.groupingEventData.getCGroupEventArrayList(), STANDARD_ID_C_GROUP);

            // [method] : D group 의 데이터를 표시하는 과정 진행
            makeGroupCheckBox(this.dGroupWrapper, this.dGroupItemWrapper, this.groupingEventData.getDGroupEventArrayList(), STANDARD_ID_D_GROUP);

            // [method] : E group 의 데이터를 표시하는 과정 진행
            makeGroupCheckBox(this.eGroupWrapper, this.eGroupItemWrapper, this.groupingEventData.getEGroupEventArrayList(), STANDARD_ID_E_GROUP);

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
     * [method] 해당 muscleArea 의 eventArrayList 를 토대로 그룹별로 나눈 GroupingEventData 객체를 만든다.
     *
     * @param eventArrayList muscleArea 의 목록
     */
    public void setGroupingEventData(ArrayList<Event> eventArrayList) {

        final String METHOD_NAME = "[setGroupingEventData] ";

        // [lv/C]GroupingEventUtil : eventArrayList 를 그룹 별로 나누어 저장한다.
        GroupingEventUtil util = new GroupingEventUtil(eventArrayList);
        util.classifyEventArrayListToGroupType();

        // [iv/C]GroupingEventData : util 을 이용하여 그룹화한 값 가져오기
        this.groupingEventData = util.getGroupingEvent();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----- A GROUP ----- ");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.groupingEventData.getAGroupEventArrayList());


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----- B GROUP ----- ");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.groupingEventData.getBGroupEventArrayList());


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----- C GROUP ----- ");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.groupingEventData.getCGroupEventArrayList());


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----- D GROUP ----- ");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.groupingEventData.getDGroupEventArrayList());


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----- E GROUP ----- ");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.groupingEventData.getEGroupEventArrayList());

    } // End of method [setGroupingEventData]


    /**
     * [method] 하나의 그룹의 eventDataArrayList 로 checkBox 를 만들고 관리하기 위한 id 를 따로 보관한다.
     */
    private int[] makeGroupCheckBox(MaterialCardView groupWrapper, LinearLayout groupItemWrapper, ArrayList<Event> groupEventArrayList, int groupStandardId) {
        final String METHOD_NAME = "[makeGroupCheckBox] ";

        // [lv/i]groupCheckBoxIdList : id 를 담을 배열
        int[] groupCheckBoxIdList = new int[groupEventArrayList.size()];

        // [check 1] : true(groupEventArrayList 의 size 가 0) -> groupWrapper 를 GONE / false -> groupEventArrayList 의 내용을 groupItemWrapper 에 CheckBox 를 만들어 추가한다.
        if (groupEventArrayList.isEmpty()) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++>> 데이터가 비어있습니다.");

            // [lv/C]MaterialCardView : 이 그룹의 wrapper 를 GONE
            groupWrapper.setVisibility(View.GONE);

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++>> 데이터가 있습니다.");
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. groupStandardId = " + groupStandardId);

            // [cycle 1] : groupEventArrayList 의 모든 데이터를 CheckBox 로 표시하기
            for (int index = 0; index < groupEventArrayList.size(); index++) {

                int finalIndex = index;

                // [lv/i]id : id 만들기
                int id = muscleAreaStandardId + groupStandardId + index;

                // [lv/i]groupCheckBoxIdList : id 를 리스트에 추가하기
                groupCheckBoxIdList[index] = id;

                // [lv/C]CheckBox : id 를 부여받은 CheckBox 생성하기
                MaterialCheckBox eventCheckBox = new MaterialCheckBox(getActivity());
                eventCheckBox.setId(id);
                eventCheckBox.setText(groupEventArrayList.get(index).getEventName());
                eventCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + compoundButton.getText() + ">>> 은 ? " + b);

                        // [check 1] : true -> checkedEventList 에 event 키 값으로 추가 / false -> checkedEventList 에 추가 되었는지 확인
                        if (b) {

                            // [iv/C]HashMap<Integer, Event> : id 를 key 로 하여 추가한다.
                            checkedEventList.put(id, groupEventArrayList.get(finalIndex));
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> key = " + id + " 의 값을 추가한다.");

                            // [lv/C]CheckBox : 색 변경
                            eventCheckBox.setBackgroundResource(R.color.colorBackgroundGray);

                        } else {

                            // [check 2] : id 를 key 로 하여 추가된 데이터가 있는지 검사한다.
                            if (checkedEventList.get(id) != null) {

                                // [iv/C]HashMap<Integer, Event> : id 를 key 하여 추가된 데이터가 있으면 삭제한다.
                                checkedEventList.remove(id);
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> key = " + id + " 을 삭제한다.");

                                // [lv/C]CheckBox : 기존의 색으로 변경
                                eventCheckBox.setBackgroundResource(R.color.colorBackgroundWhite);

                            } else {
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> key = " + id + " 은 추가되지 않았으므로 삭제하지 않는다.");
                            } // [check 2]
                        } // [check 1]
                    }
                });

                // [lv/C]LinearLayout : 해당 group 의 itemWrapper 에 위의 CheckBox 를 추가한다.
                groupItemWrapper.addView(eventCheckBox);

            } // [cycle 1]

        } // [check 1]

        return groupCheckBoxIdList;
    } // End of method [makeGroupCheckBox]


    /**
     * 체크된 종목이 저장되어 있는 checkedEventList 를 key(id) 로 오름차순 정렬한다. 그리고 정렬된 순서로 ArrayList<Event> 객체를 만들어서 반환한다.
     *
     * @return 정렬된 순서로 만들어진 ArrayList 객체
     */
    public ArrayList<Event> getCheckedEventArrayList() {

        // [lv/C]ArrayList<Event> : 체크된 리스트들을 정렬하여 eventArrayList 에 추가한다.
        ArrayList<Event> eventArrayList = new ArrayList<>();

        // [lv/C]TreeMap<Integer, Event> : 체크된 항목이 저장되어 있는 checkedEventList 를 key 정렬하기 위해 TreeMap 으로 만든다.
        TreeMap<Integer, Event> treeMap = new TreeMap<Integer, Event>(this.checkedEventList);

        // [lv/C]Iterator<Integer> : key 를 오름차순으로 정렬
        Iterator<Integer> sortedKey = treeMap.keySet().iterator();

        // [cycle 1] : sortedKey 로 정렬된 순서대로 모든 데이터확인하기
        while (sortedKey.hasNext()) {

            // [lv/C]Integer : 정렬된 key 를 sortedKey 에서 가져오기
            Integer key = sortedKey.next();

            // [lv/C]ArrayList<Event> : treeMap 에서 sortedKey 순서대로 Event 객체를 추가하기
            eventArrayList.add(treeMap.get(key));

        }

        return eventArrayList;
    } // End of method [getCheckedEventArrayList]

}
