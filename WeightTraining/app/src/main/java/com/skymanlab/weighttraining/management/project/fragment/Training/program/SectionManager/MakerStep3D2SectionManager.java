package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.event.program.util.RandomEventSelectionUtil;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.ProgramType;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep4Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.EachGroupRandomItem;

import java.util.HashMap;

public class MakerStep3D2SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, MakerStepManager.OnPreviousClickListener, MakerStepManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep3D2SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private MakerStepManager makerStepManager;

    // instance variable
    private LinearLayout eachGroupRandomListWrapper;

    // instance variable
    private GroupingEventData chestGroupingEventData;
    private GroupingEventData shoulderGroupingEventData;
    private GroupingEventData latGroupingEventData;
    private GroupingEventData upperBodyGroupingEventData;
    private GroupingEventData armGroupingEventData;
    private GroupingEventData etcGroupingEventData;

    // instance variable
    private HashMap<MuscleArea, EachGroupRandomItem> eachGroupRandomItemList;

    // constructor
    public MakerStep3D2SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    // setter
    public void setChestGroupingEventData(GroupingEventData chestGroupingEventData) {
        this.chestGroupingEventData = chestGroupingEventData;
    }

    public void setShoulderGroupingEventData(GroupingEventData shoulderGroupingEventData) {
        this.shoulderGroupingEventData = shoulderGroupingEventData;
    }

    public void setLatGroupingEventData(GroupingEventData latGroupingEventData) {
        this.latGroupingEventData = latGroupingEventData;
    }

    public void setUpperBodyGroupingEventData(GroupingEventData upperBodyGroupingEventData) {
        this.upperBodyGroupingEventData = upperBodyGroupingEventData;
    }

    public void setArmGroupingEventData(GroupingEventData armGroupingEventData) {
        this.armGroupingEventData = armGroupingEventData;
    }

    public void setEtcGroupingEventData(GroupingEventData etcGroupingEventData) {
        this.etcGroupingEventData = etcGroupingEventData;
    }

    @Override
    public void connectWidget() {

        // [iv/C]LinearLayout : eachGroupRandomListWrapper connect
        this.eachGroupRandomListWrapper = (LinearLayout) getView().findViewById(R.id.f_maker_step3_2_each_group_random_list_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]MakerStepManager :
        this.makerStepManager = new MakerStepManager(getView(), getFragmentManager(), MakerStepManager.STEP_THREE);
        this.makerStepManager.setPreviousClickListener(this);
        this.makerStepManager.setNextClickListener(this);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [iv/C]HashMap<MuscleArea, EachGroupRandomItem> :
        this.eachGroupRandomItemList = new HashMap<MuscleArea, EachGroupRandomItem>();

        // [lv/C]LayoutInflater :
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [1] chest
        initEachGroupRandomListWrapper(inflater, MuscleArea.CHEST, chestGroupingEventData);

        // [2] shoulder
        initEachGroupRandomListWrapper(inflater, MuscleArea.SHOULDER, shoulderGroupingEventData);

        // [3] lat
        initEachGroupRandomListWrapper(inflater, MuscleArea.LAT, latGroupingEventData);

        // [4] upper body
        initEachGroupRandomListWrapper(inflater, MuscleArea.UPPER_BODY, upperBodyGroupingEventData);

        // [5] arm
        initEachGroupRandomListWrapper(inflater, MuscleArea.ARM, armGroupingEventData);

        // [6] etc
        initEachGroupRandomListWrapper(inflater, MuscleArea.ETC, etcGroupingEventData);

    }

    @Override
    public AlertDialog setClickListenerOfPrevious() {
        return null;
    }

    @Override
    public void setClickListenerOfNext() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 각 MuscleArea 의 EventResultSet =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // 각 MuscleArea 에서 랜덤으로 선택된 항목과 선택되지 않은 항목을 가져와서, EventResultSet 의 객체의 selectedEventArrayList 와 noSelectedEventArrayList 에 각각 입력한다.
        EventResultSet chestEventResultSet = createEventResultSet(MuscleArea.CHEST, chestGroupingEventData);
        EventResultSet shoulderEventResultSet = createEventResultSet(MuscleArea.SHOULDER, shoulderGroupingEventData);
        EventResultSet latEventResultSet = createEventResultSet(MuscleArea.LAT, latGroupingEventData);
        EventResultSet upperBodyEventResultSet = createEventResultSet(MuscleArea.UPPER_BODY, upperBodyGroupingEventData);
        EventResultSet armEventResultSet = createEventResultSet(MuscleArea.ARM, armGroupingEventData);
        EventResultSet etcEventResultSet = createEventResultSet(MuscleArea.ETC, etcGroupingEventData);

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= step 4-1 로 넘어가는 과정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [check 7] : 각 MuscleArea 의 selectedEventArrayList 가 모두 0 이면 다음 단계로 넘어가지 못함
        if (chestEventResultSet.getSelectedEventArrayList().isEmpty()
                && shoulderEventResultSet.getSelectedEventArrayList().isEmpty()
                && latEventResultSet.getSelectedEventArrayList().isEmpty()
                && upperBodyEventResultSet.getSelectedEventArrayList().isEmpty()
                && armEventResultSet.getSelectedEventArrayList().isEmpty()
                && etcEventResultSet.getSelectedEventArrayList().isEmpty()) {

            // "선택되지 않았습니다." snack bar 메시지 출력
            Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step3_2_snack_next_check_true, Snackbar.LENGTH_SHORT).show();

        } else {

            // [lv/C]MakerStep4Fragment : step 4 fragment 생성
            MakerStep4Fragment step4Fragment = MakerStep4Fragment.newInstance(
                    chestEventResultSet,
                    shoulderEventResultSet,
                    latEventResultSet,
                    upperBodyEventResultSet,
                    armEventResultSet,
                    etcEventResultSet);

            // [lv/C]FragmentTransaction : step 4 Fragment 로 이동
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_home_content_wrapper, step4Fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } // [check 7]

    }

    private void initEachGroupRandomListWrapper(LayoutInflater inflater, MuscleArea muscleArea, GroupingEventData groupingEventData) {

        if (groupingEventData != null) {

            // [lv/C]EachGroupRandomItem : muscleArea 의 eachGroupRandom 의 item 을 생성하는 과정 진행
            EachGroupRandomItem eachGroupRandomItem = createEachGroupRandomItem(inflater, muscleArea, groupingEventData);

            // eachGroupRandomItem 을 관리하기 위한 리스트에 추가한다. / key 는 muscleArea 이다.
            addItemToEachGroupRandomItemList(muscleArea, eachGroupRandomItem);

            // eachGroupRandomListWrapper 에
            addViewToEachGroupRandomListWrapper(eachGroupRandomItem);
        }

    }

    private EachGroupRandomItem createEachGroupRandomItem(LayoutInflater inflater, MuscleArea muscleArea, GroupingEventData groupingEventData) {

        // [lv/C]EachGroupRandomItem : eachGroupRandom 의 item 생성
        EachGroupRandomItem eachGroupRandomItem = new EachGroupRandomItem.Builder(getActivity())
                .setInflater(inflater)
                .setGroupingEventData(groupingEventData)
                .setMuscleArea(muscleArea)
                .create();

        eachGroupRandomItem.createItem();

        return eachGroupRandomItem;
    }

    private void addItemToEachGroupRandomItemList(MuscleArea key, EachGroupRandomItem eachGroupRandomItem) {

        this.eachGroupRandomItemList.put(key, eachGroupRandomItem);

    }

    private void addViewToEachGroupRandomListWrapper(EachGroupRandomItem eachGroupRandomItem) {
        this.eachGroupRandomListWrapper.addView(eachGroupRandomItem.getItem());
    }

    private EventResultSet createEventResultSet (MuscleArea key, GroupingEventData groupingEventData) {

        // [lv/C]EventResultSet :
        EventResultSet eventResultSet = new EventResultSet();

        if (groupingEventData != null) {

            // [lv/C]RandomEventSelectionUtil :
            RandomEventSelectionUtil util = new RandomEventSelectionUtil(
                    ProgramType.EACH_RANDOM,
                    eachGroupRandomItemList.get(key).getItemOfAGroupSpinner(),
                    eachGroupRandomItemList.get(key).getItemOfBGroupSpinner(),
                    eachGroupRandomItemList.get(key).getItemOfCGroupSpinner(),
                    eachGroupRandomItemList.get(key).getItemOfDGroupSpinner(),
                    eachGroupRandomItemList.get(key).getItemOfEGroupSpinner());
            util.setGroupingEventData(groupingEventData);
            util.selectRandomEvent();

            eventResultSet.setSelectedEventArrayList(util.getSelectedEventArrayList());
            eventResultSet.setNoSelectedEventArrayList(util.getNoSelectedEventArrayList());

        }
        return eventResultSet;
    } // End of method [makeEventResultSet]

}
