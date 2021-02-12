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
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.AllGroupRandomItem;

import java.util.HashMap;

public class MakerStep3D3SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep3D3SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private GroupingEventData chestGroupingEventData;
    private GroupingEventData shoulderGroupingEventData;
    private GroupingEventData latGroupingEventData;
    private GroupingEventData lowerBodyGroupingEventData;
    private GroupingEventData armGroupingEventData;
    private GroupingEventData etcGroupingEventData;

    // instance variable :
    private LinearLayout allGroupRandomListWrapper;

    // instance variable
    private HashMap<MuscleArea, AllGroupRandomItem> allGroupRandomItemList;

    // constructor
    public MakerStep3D3SectionManager(Fragment fragment, View view) {
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

        // [iv/C]LinearLayout : allGroupRandomListWrapper connect
        this.allGroupRandomListWrapper = (LinearLayout) getView().findViewById(R.id.f_program_step3_3_all_group_random_list_wrapper);

    }

    @Override
    public void initWidget() {

        // [iv/C]HashMap<String, AllGroupRandomItem> :
        this.allGroupRandomItemList = new HashMap<MuscleArea, AllGroupRandomItem>();

        // [lv/C]LayoutInflater :
        LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [1] chest
        initAllGroupRandomListWrapper(inflater, MuscleArea.CHEST, chestGroupingEventData);

        // [2] shoulder
        initAllGroupRandomListWrapper(inflater, MuscleArea.SHOULDER, shoulderGroupingEventData);

        // [3] lat
        initAllGroupRandomListWrapper(inflater, MuscleArea.LAT, latGroupingEventData);

        // [4] upper body
        initAllGroupRandomListWrapper(inflater, MuscleArea.LOWER_BODY, lowerBodyGroupingEventData);

        // [5] arm
        initAllGroupRandomListWrapper(inflater, MuscleArea.ARM, armGroupingEventData);

        // [6] etc
        initAllGroupRandomListWrapper(inflater, MuscleArea.ETC, etcGroupingEventData);

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

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 각 MuscleArea 의 random data 를 생성 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [1] chest
                EventResultSet chestEventResultSet = createRandomResultSet(MuscleArea.CHEST, chestGroupingEventData);

                // [2] shoulder
                EventResultSet shoulderEventResultSet = createRandomResultSet(MuscleArea.SHOULDER, shoulderGroupingEventData);

                // [3] lat
                EventResultSet latEventResultSet = createRandomResultSet(MuscleArea.LAT, latGroupingEventData);

                // [4] upper body
                EventResultSet upperBodyEventResultSet = createRandomResultSet(MuscleArea.LOWER_BODY, lowerBodyGroupingEventData);

                // [5] arm
                EventResultSet armEventResultSet = createRandomResultSet(MuscleArea.ARM, armGroupingEventData);

                // [6] etc
                EventResultSet etcEventResultSet = createRandomResultSet(MuscleArea.ETC, etcGroupingEventData);


                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= step 4-1 로 넘어가는 과정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [check 1] : 각 MuscleArea 의 selectedEventArrayList 가 모두 0 이면 다음 단계로 넘어가지 못함
                if (chestEventResultSet.getSelectedEventArrayList().isEmpty()
                        && shoulderEventResultSet.getSelectedEventArrayList().isEmpty()
                        && latEventResultSet.getSelectedEventArrayList().isEmpty()
                        && upperBodyEventResultSet.getSelectedEventArrayList().isEmpty()
                        && armEventResultSet.getSelectedEventArrayList().isEmpty()
                        && etcEventResultSet.getSelectedEventArrayList().isEmpty()) {

                    // "선택되지 않았습니다." snack bar 메시지 출력
                    Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step3_3_snack_next_check_true, Snackbar.LENGTH_SHORT).show();

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
                    transaction.addToBackStack("step3-3");
                    transaction.commit();

                } // [check 1]

                return null;
            }
        };
    }


    /**
     * allGroupRandomListWrapper 에 AllGroupRandomItem 을 생성하고 그 view 를 추가하여 화면에 표시한다.
     *
     * @param inflater
     * @param muscleArea
     * @param groupingEventData
     */
    private void initAllGroupRandomListWrapper(LayoutInflater inflater, MuscleArea muscleArea, GroupingEventData groupingEventData) {

        if (groupingEventData != null) {

            // muscleArea 의 allGroupRandom 의 item 을 생성하는 과정 진행
            AllGroupRandomItem allGroupRandomItem = createAllGroupRandomItem(inflater, muscleArea, groupingEventData);

            // allGroupRandomItem 을 관리하기 위한 리스트에 추가한다. / key 는 muscleArea 이다.
            addItemToAllGroupRandomItemList(muscleArea, allGroupRandomItem);

            // allGroupRandomListWrapper 에 allGroupRandomItem 의 view 를 추가한다.
            addViewToAllGroupRandomListWrapper(allGroupRandomItem);
        }

    } // End of method [initAllGroupRandomListWrapper]


    /**
     * AllGroupRandomItem 객체를 생성하여 반환한다.
     *
     * @param inflater
     * @param muscleArea
     * @param groupingEventData
     * @return
     */
    private AllGroupRandomItem createAllGroupRandomItem(LayoutInflater inflater, MuscleArea muscleArea, GroupingEventData groupingEventData) {

        // [lv/C]AllGroupRandomItem : item 생성
        AllGroupRandomItem allGroupRandomItem = new AllGroupRandomItem.Builder(getFragment().getActivity())
                .setInflater(inflater)
                .setMuscleArea(muscleArea)
                .setGroupingEventData(groupingEventData)
                .create();

        allGroupRandomItem.createItem();

        return allGroupRandomItem;

    } // End of method [AllGroupRandomItem]


    /**
     * allGroupRandomItemList 에 AllGroupRandomItem 을 추가한다.
     *
     * @param key
     * @param allGroupRandomItem
     */
    private void addItemToAllGroupRandomItemList(MuscleArea key, AllGroupRandomItem allGroupRandomItem) {
        this.allGroupRandomItemList.put(key, allGroupRandomItem);
    } // End of method [addItemToAllGroupRandomItemList]


    /**
     * allGroupRandomListWrapper 에 AllGroupRandomItem 의 item view 를 추가한다.
     *
     * @param allGroupRandomItem item view 가 담긴 객체
     */
    private void addViewToAllGroupRandomListWrapper(AllGroupRandomItem allGroupRandomItem) {

        this.allGroupRandomListWrapper.addView(allGroupRandomItem.getItem());
    } // End of method [addViewToAllGroupRandomListWrapper]


    /**
     * 각 MuscleArea 의 allGroupRandomItem 의 spinner 에서 선택한 값으로 랜덤 데이터를 생성한다.
     *
     * @param key
     * @param groupingEventData
     * @return
     */
    private EventResultSet createRandomResultSet(MuscleArea key, GroupingEventData groupingEventData) {

        // [lv/C]EventResultSet :
        EventResultSet eventResultSet = new EventResultSet();

        if (groupingEventData != null) {

            // [RandomEventSelectionUtil] [util] all random type 으로 랜덤 선택
            RandomEventSelectionUtil util = new RandomEventSelectionUtil(ProgramType.ALL_RANDOM, this.allGroupRandomItemList.get(key).getItemOfAllGroupSpinner());
            util.setGroupingEventData(groupingEventData);
            util.setIntegratedEventArrayList();
            util.selectRandomEvent();

            // [EventResultSet] [eventResultSet] util 에서 랜덤으로 선택된 목록과 그 나머지 목록으로 객체 생성
            eventResultSet.setSelectedEventArrayList(util.getSelectedEventArrayList());
            eventResultSet.setNoSelectedEventArrayList(util.getNoSelectedEventArrayList());
        }

        return eventResultSet;

    } // End of method [createRandomResultSet]

}
