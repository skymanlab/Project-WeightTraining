package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.program.util.RandomEventSelectionUtil;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.ProgramType;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep4Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.EachGroupRandomItem;

import java.util.HashMap;

public class MakerStep3D2SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep3D2SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private LinearLayout eachGroupRandomListWrapper;

    // instance variable
    private GroupingEventData chestGroupingEventData;
    private GroupingEventData shoulderGroupingEventData;
    private GroupingEventData latGroupingEventData;
    private GroupingEventData lowerBodyGroupingEventData;
    private GroupingEventData armGroupingEventData;
    private GroupingEventData etcGroupingEventData;

    // instance variable
    private HashMap<MuscleArea, EachGroupRandomItem> eachGroupRandomItemList;

    // constructor
    public MakerStep3D2SectionManager(Fragment fragment, View view) {
        super(fragment, view);
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

    public void setLowerBodyGroupingEventData(GroupingEventData lowerBodyGroupingEventData) {
        this.lowerBodyGroupingEventData = lowerBodyGroupingEventData;
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


        // [iv/C]HashMap<MuscleArea, EachGroupRandomItem> :
        this.eachGroupRandomItemList = new HashMap<MuscleArea, EachGroupRandomItem>();

        // [lv/C]LayoutInflater :
        LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [1] chest
        initEachGroupRandomListWrapper(inflater, MuscleArea.CHEST, chestGroupingEventData);

        // [2] shoulder
        initEachGroupRandomListWrapper(inflater, MuscleArea.SHOULDER, shoulderGroupingEventData);

        // [3] lat
        initEachGroupRandomListWrapper(inflater, MuscleArea.LAT, latGroupingEventData);

        // [4] upper body
        initEachGroupRandomListWrapper(inflater, MuscleArea.LOWER_BODY, lowerBodyGroupingEventData);

        // [5] arm
        initEachGroupRandomListWrapper(inflater, MuscleArea.ARM, armGroupingEventData);

        // [6] etc
        initEachGroupRandomListWrapper(inflater, MuscleArea.ETC, etcGroupingEventData);

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

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 각 MuscleArea 의 EventResultSet =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // 각 MuscleArea 에서 랜덤으로 선택된 항목과 선택되지 않은 항목을 가져와서, EventResultSet 의 객체의 selectedEventArrayList 와 noSelectedEventArrayList 에 각각 입력한다.
                EventResultSet chestEventResultSet = createEventResultSet(MuscleArea.CHEST, chestGroupingEventData);
                EventResultSet shoulderEventResultSet = createEventResultSet(MuscleArea.SHOULDER, shoulderGroupingEventData);
                EventResultSet latEventResultSet = createEventResultSet(MuscleArea.LAT, latGroupingEventData);
                EventResultSet upperBodyEventResultSet = createEventResultSet(MuscleArea.LOWER_BODY, lowerBodyGroupingEventData);
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
                    Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step3_2_snack_next_check_true, Snackbar.LENGTH_SHORT).show();

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
                    FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_home_content_wrapper, step4Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } // [check 7]

                return null;
            }
        };
    }


    /**
     * eachGroupRandomListWrapper(LinearLayout) 에 해당 muscleArea 표시유무를 확인하여, 해당 groupEventData 를 이용하여 EachGroupRandomItem 객체를 생성 후 그 View 객체를 화면에 표시한다.
     *
     * @param inflater
     * @param muscleArea
     * @param groupingEventData
     */
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


    /**
     * 해당 MuscleArea 의 groupingEventData 로 EachGroupRandomItem 객체를 생성하여 반환한다.
     *
     * @param inflater
     * @param muscleArea
     * @param groupingEventData
     * @return
     */
    private EachGroupRandomItem createEachGroupRandomItem(LayoutInflater inflater, MuscleArea muscleArea, GroupingEventData groupingEventData) {

        // [lv/C]EachGroupRandomItem : eachGroupRandom 의 item 생성
        EachGroupRandomItem eachGroupRandomItem = new EachGroupRandomItem.Builder(getFragment().getActivity())
                .setInflater(inflater)
                .setGroupingEventData(groupingEventData)
                .setMuscleArea(muscleArea)
                .create();

        eachGroupRandomItem.createItem();

        return eachGroupRandomItem;
    }


    /**
     * eachGroupRandomItemList 에 eachGroupRandomItem 객체를 muscleArea 를 key 로 하여 추가한다.
     *
     * @param key
     * @param eachGroupRandomItem
     */
    private void addItemToEachGroupRandomItemList(MuscleArea key, EachGroupRandomItem eachGroupRandomItem) {

        this.eachGroupRandomItemList.put(key, eachGroupRandomItem);

    }


    /**
     * eachGroupRandomItem 을 화면에 표시하기 위해 eachGroupRandomListWrapper 에 view 를 추가하여 화면에 표시한다.
     *
     * @param eachGroupRandomItem
     */
    private void addViewToEachGroupRandomListWrapper(EachGroupRandomItem eachGroupRandomItem) {
        this.eachGroupRandomListWrapper.addView(eachGroupRandomItem.getItem());
    }


    /**
     * 해당 muscleArea 에서 각 그룹에서 선택한 개수를 이용하여 랜덤으로 선택한 event 의 EventResultSet 객체를 반환한다.
     *
     * @param key
     * @param groupingEventData
     * @return
     */
    private EventResultSet createEventResultSet(MuscleArea key, GroupingEventData groupingEventData) {

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
