package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.util.EventResultSet;
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.event.program.util.RandomEventSelectionUtil;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.ProgramType;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.Step4D1Fragment;

import java.util.ArrayList;

public class Step3D2SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] Step3D2SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable
    private LinearLayout stepContentWrapper;

    // instance variable : chest
    private View chestWrapper;
    private TextView chestTitle;
    private MaterialCardView chestAGroupWrapper;
    private MaterialCardView chestBGroupWrapper;
    private MaterialCardView chestCGroupWrapper;
    private MaterialCardView chestDGroupWrapper;
    private MaterialCardView chestEGroupWrapper;
    private AppCompatSpinner chestAGroupSpinner;
    private AppCompatSpinner chestBGroupSpinner;
    private AppCompatSpinner chestCGroupSpinner;
    private AppCompatSpinner chestDGroupSpinner;
    private AppCompatSpinner chestEGroupSpinner;

    // instance variable : shoulder
    private View shoulderWrapper;
    private TextView shoulderTitle;
    private MaterialCardView shoulderAGroupWrapper;
    private MaterialCardView shoulderBGroupWrapper;
    private MaterialCardView shoulderCGroupWrapper;
    private MaterialCardView shoulderDGroupWrapper;
    private MaterialCardView shoulderEGroupWrapper;
    private AppCompatSpinner shoulderAGroupSpinner;
    private AppCompatSpinner shoulderBGroupSpinner;
    private AppCompatSpinner shoulderCGroupSpinner;
    private AppCompatSpinner shoulderDGroupSpinner;
    private AppCompatSpinner shoulderEGroupSpinner;

    // instance variable : lat
    private View latWrapper;
    private TextView latTitle;
    private MaterialCardView latAGroupWrapper;
    private MaterialCardView latBGroupWrapper;
    private MaterialCardView latCGroupWrapper;
    private MaterialCardView latDGroupWrapper;
    private MaterialCardView latEGroupWrapper;
    private AppCompatSpinner latAGroupSpinner;
    private AppCompatSpinner latBGroupSpinner;
    private AppCompatSpinner latCGroupSpinner;
    private AppCompatSpinner latDGroupSpinner;
    private AppCompatSpinner latEGroupSpinner;

    // instance variable : upper body
    private View upperBodyWrapper;
    private TextView upperBodyTitle;
    private MaterialCardView upperBodyAGroupWrapper;
    private MaterialCardView upperBodyBGroupWrapper;
    private MaterialCardView upperBodyCGroupWrapper;
    private MaterialCardView upperBodyDGroupWrapper;
    private MaterialCardView upperBodyEGroupWrapper;
    private AppCompatSpinner upperBodyAGroupSpinner;
    private AppCompatSpinner upperBodyBGroupSpinner;
    private AppCompatSpinner upperBodyCGroupSpinner;
    private AppCompatSpinner upperBodyDGroupSpinner;
    private AppCompatSpinner upperBodyEGroupSpinner;

    // instance variable : arm
    private View armWrapper;
    private TextView armTitle;
    private MaterialCardView armAGroupWrapper;
    private MaterialCardView armBGroupWrapper;
    private MaterialCardView armCGroupWrapper;
    private MaterialCardView armDGroupWrapper;
    private MaterialCardView armEGroupWrapper;
    private AppCompatSpinner armAGroupSpinner;
    private AppCompatSpinner armBGroupSpinner;
    private AppCompatSpinner armCGroupSpinner;
    private AppCompatSpinner armDGroupSpinner;
    private AppCompatSpinner armEGroupSpinner;

    // instance variable : etc
    private View etcWrapper;
    private TextView etcTitle;
    private MaterialCardView etcAGroupWrapper;
    private MaterialCardView etcBGroupWrapper;
    private MaterialCardView etcCGroupWrapper;
    private MaterialCardView etcDGroupWrapper;
    private MaterialCardView etcEGroupWrapper;
    private AppCompatSpinner etcAGroupSpinner;
    private AppCompatSpinner etcBGroupSpinner;
    private AppCompatSpinner etcCGroupSpinner;
    private AppCompatSpinner etcDGroupSpinner;
    private AppCompatSpinner etcEGroupSpinner;

    // instance variable
    private GroupingEventData chestGroupingEventData;
    private GroupingEventData shoulderGroupingEventData;
    private GroupingEventData latGroupingEventData;
    private GroupingEventData upperBodyGroupingEventData;
    private GroupingEventData armGroupingEventData;
    private GroupingEventData etcGroupingEventData;

    // constructor
    public Step3D2SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
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
    public void mappingWidget() {

        // [lv/C]LayoutInflater :
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [iv/C]LinearLayout : stepContentWrapper mapping
        this.stepContentWrapper = (LinearLayout) getView().findViewById(R.id.f_program_step3_2_step_content_wrapper);

        // [method] : chest 용 layout inflater 와 widget mapping
        mappingWidgetOfChest(inflater);

        // [method] : shoulder 용 layout inflater 와 widget mapping
        mappingWidgetOfShoulder(inflater);

        // [method] : lat 용 layout inflater 와 widget mapping
        mappingWidgetOfLat(inflater);

        // [method] : upper_body 용 layout inflater 와 widget mapping
        mappingWidgetOfUpperBody(inflater);

        // [method] : arm 용 layout inflater 와 widget mapping
        mappingWidgetOfArm(inflater);

        // [method] : etc 용 layout inflater 와 widget mapping
        mappingWidgetOfEtc(inflater);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]StepProcessManager :
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_THREE);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.initWidget();


        // [method] : chest widget init
        initWidgetOfChest();

        // [method] : shoulder widget init
        initWidgetOfShoulder();

        // [method] : lat widget init
        initWidgetOfLat();

        // [method] : upper_body widget init
        initWidgetOfUpperBody();

        // [method] : arm widget init
        initWidgetOfArm();

        // [method] : etc widget init
        initWidgetOfEtc();

    }

    @Override
    public void setClickListenerOfNext() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 각 MuscleArea 의 EventResultSet =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // 각 MuscleArea 에서 랜덤으로 선택된 항목과 선택되지 않은 항목을 가져와서, EventResultSet 의 객체의 selectedEventArrayList 와 noSelectedEventArrayList 에 각각 입력한다.
        EventResultSet chestEventResultSet = new EventResultSet();
        EventResultSet shoulderEventResultSet = new EventResultSet();
        EventResultSet latEventResultSet = new EventResultSet();
        EventResultSet upperBodyEventResultSet = new EventResultSet();
        EventResultSet armEventResultSet = new EventResultSet();
        EventResultSet etcEventResultSet = new EventResultSet();
        
        // [check 1] : chest 가 선택한 항목인가요?
        if (this.chestGroupingEventData != null) {

            // [lv/C]RandomEventSelectionUtil : each random 으로 chest 를 그룹별로 선택
            RandomEventSelectionUtil chestUtil = new RandomEventSelectionUtil(
                    ProgramType.EACH_RANDOM,
                    getSpinnerItem(chestAGroupSpinner),
                    getSpinnerItem(chestBGroupSpinner),
                    getSpinnerItem(chestCGroupSpinner),
                    getSpinnerItem(chestDGroupSpinner),
                    getSpinnerItem(chestEGroupSpinner));
            chestUtil.setGroupingEventData(chestGroupingEventData);
            chestUtil.selectRandomEvent();

            // [lv/C]EventResultSet : [0] chest 의 selectedEventArrayList 와 noSelectedEventArrayList 을 EventResultSet 에 입력하기
            chestEventResultSet.setSelectedEventArrayList(chestUtil.getSelectedEventArrayList());
            chestEventResultSet.setNoSelectedEventArrayList(chestUtil.getNoSelectedEventArrayList());

        } // [check 1]

        // [check 2] : [1] shoulder 가 선택한 항목인가요?
        if (this.shoulderGroupingEventData != null) {

            // [lv/C]RandomEventSelectionUtil : each random 으로 shoulder 를 그룹별로 선택
            RandomEventSelectionUtil shoulderUtil = new RandomEventSelectionUtil(
                    ProgramType.EACH_RANDOM,
                    getSpinnerItem(shoulderAGroupSpinner),
                    getSpinnerItem(shoulderBGroupSpinner),
                    getSpinnerItem(shoulderCGroupSpinner),
                    getSpinnerItem(shoulderDGroupSpinner),
                    getSpinnerItem(shoulderEGroupSpinner));
            shoulderUtil.setGroupingEventData(shoulderGroupingEventData);
            shoulderUtil.selectRandomEvent();

            // [lv/C]EventResultSet : [1] shoulder 의 selectedEventArrayList 와 noSelectedEventArrayList 을 EventResultSet 에 입력하기
            shoulderEventResultSet.setSelectedEventArrayList(shoulderUtil.getSelectedEventArrayList());
            shoulderEventResultSet.setNoSelectedEventArrayList(shoulderUtil.getNoSelectedEventArrayList());

        } // [check 2]

        // [check 3] : [2] lat 가 선택한 항목인가요?
        if (this.latGroupingEventData != null) {

            // [lv/C]RandomEventSelectionUtil : each random 으로 lat 를 그룹별로 선택
            RandomEventSelectionUtil latUtil = new RandomEventSelectionUtil(
                    ProgramType.EACH_RANDOM,
                    getSpinnerItem(latAGroupSpinner),
                    getSpinnerItem(latBGroupSpinner),
                    getSpinnerItem(latCGroupSpinner),
                    getSpinnerItem(latDGroupSpinner),
                    getSpinnerItem(latEGroupSpinner));
            latUtil.setGroupingEventData(latGroupingEventData);
            latUtil.selectRandomEvent();

            // [lv/C]EventResultSet : [2] lat 의 selectedEventArrayList 와 noSelectedEventArrayList 을 EventResultSet 에 입력하기
            latEventResultSet.setSelectedEventArrayList(latUtil.getSelectedEventArrayList());
            latEventResultSet.setNoSelectedEventArrayList(latUtil.getNoSelectedEventArrayList());

        } // [check 3]

        // [check 4] : [3] upper_body 가 선택한 항목인가요?
        if (this.upperBodyGroupingEventData != null) {

            // [lv/C]RandomEventSelectionUtil : each random 으로 upperBody 를 그룹별로 선택
            RandomEventSelectionUtil upperBodyUtil = new RandomEventSelectionUtil(
                    ProgramType.EACH_RANDOM,
                    getSpinnerItem(upperBodyAGroupSpinner),
                    getSpinnerItem(upperBodyBGroupSpinner),
                    getSpinnerItem(upperBodyCGroupSpinner),
                    getSpinnerItem(upperBodyDGroupSpinner),
                    getSpinnerItem(upperBodyEGroupSpinner));
            upperBodyUtil.setGroupingEventData(upperBodyGroupingEventData);
            upperBodyUtil.selectRandomEvent();

            // [lv/C]EventResultSet : [3] upperBody 의 selectedEventArrayList 와 noSelectedEventArrayList 을 EventResultSet 에 입력하기
            upperBodyEventResultSet.setSelectedEventArrayList(upperBodyUtil.getSelectedEventArrayList());
            upperBodyEventResultSet.setNoSelectedEventArrayList(upperBodyUtil.getNoSelectedEventArrayList());

        } // [check 4]

        // [check 5] : [4] arm 가 선택한 항목인가요?
        if (this.armGroupingEventData != null) {

            // [lv/C]RandomEventSelectionUtil : each random 으로 arm 를 그룹별로 선택
            RandomEventSelectionUtil armUtil = new RandomEventSelectionUtil(
                    ProgramType.EACH_RANDOM,
                    getSpinnerItem(armAGroupSpinner),
                    getSpinnerItem(armBGroupSpinner),
                    getSpinnerItem(armCGroupSpinner),
                    getSpinnerItem(armDGroupSpinner),
                    getSpinnerItem(armEGroupSpinner));
            armUtil.setGroupingEventData(armGroupingEventData);
            armUtil.selectRandomEvent();

            // [lv/C]EventResultSet : [4] arm 의 selectedEventArrayList 와 noSelectedEventArrayList 을 EventResultSet 에 입력하기
            armEventResultSet.setSelectedEventArrayList(armUtil.getSelectedEventArrayList());
            armEventResultSet.setNoSelectedEventArrayList(armUtil.getNoSelectedEventArrayList());


        } // [check 5]

        // [check 6] : [5] etc 가 선택한 항목인가요?
        if (this.etcGroupingEventData != null) {

            // [lv/C]RandomEventSelectionUtil : each random 으로 etc 를 그룹별로 선택
            RandomEventSelectionUtil etcUtil = new RandomEventSelectionUtil(
                    ProgramType.EACH_RANDOM,
                    getSpinnerItem(etcAGroupSpinner),
                    getSpinnerItem(etcBGroupSpinner),
                    getSpinnerItem(etcCGroupSpinner),
                    getSpinnerItem(etcDGroupSpinner),
                    getSpinnerItem(etcEGroupSpinner));
            etcUtil.setGroupingEventData(etcGroupingEventData);
            etcUtil.selectRandomEvent();

            // [lv/C]EventResultSet : [5] etc 의 selectedEventArrayList 와 noSelectedEventArrayList 을 EventResultSet 에 입력하기
            etcEventResultSet.setSelectedEventArrayList(etcUtil.getSelectedEventArrayList());
            etcEventResultSet.setNoSelectedEventArrayList(etcUtil.getNoSelectedEventArrayList());

        } // [check 6]


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= step 4-1 로 넘어가는 과정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [check 7] : 각 MuscleArea 의 selectedEventArrayList 가 모두 0 이면 다음 단계로 넘어가지 못함
        if (chestEventResultSet.getSelectedEventArrayList().isEmpty()
                && shoulderEventResultSet.getSelectedEventArrayList().isEmpty()
                && latEventResultSet.getSelectedEventArrayList().isEmpty()
                && upperBodyEventResultSet.getSelectedEventArrayList().isEmpty()
                && armEventResultSet.getSelectedEventArrayList().isEmpty()
                && etcEventResultSet.getSelectedEventArrayList().isEmpty()) {

            // "선택되지 않았습니다." snack bar 메시지 출력
            Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_program_step3_2_snack_next_check_true, Snackbar.LENGTH_SHORT).show();

        } else {

            // [lv/C]Step4D1Fragment  : step 4-1 fragment 생성 및 각 MuscleArea 의 EventResultSet 객체를 넘기기
            Step4D1Fragment step4_1 = Step4D1Fragment.newInstance(
                    chestEventResultSet,
                    shoulderEventResultSet,
                    latEventResultSet,
                    upperBodyEventResultSet,
                    armEventResultSet,
                    etcEventResultSet
            );

            // [lv/C]FragmentTransaction :
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_home_content_container, step4_1);
            transaction.addToBackStack(null);
            transaction.commit();

        } // [check 7]

    }


    /**
     * spinner 의 position 은 그 position 의 값과 같으므로 각 위치의 값을 가져온다. 하지만 만약 spinner 에서 -1 값이 넘어오면 0 으로 치환하여 반환한다.
     *
     * @param groupSpinner position 을 가져올 spinner
     * @return groupSpinner 의 position
     */
    private int getSpinnerItem(AppCompatSpinner groupSpinner) {

        if (0 < groupSpinner.getSelectedItemPosition()) {
            return groupSpinner.getSelectedItemPosition();
        } else {
            return 0;
        }
    } // End of method [getSpinnerItem]

    /**
     * @param size 추가할 항목의 size
     * @return 항목이 추가된 adpater
     */
    private ArrayAdapter<Integer> getAdapterOfGroupSpinner(int size) {

        // [lv/C]ArrayAdapter<Integer> :
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), R.layout.support_simple_spinner_dropdown_item);

        // [cycle 1] : a group event array list 의 size 만큼
        for (int index = 0; index <= size; index++) {

            // [lv/C]ArrayAdapter<Integer> : index 추가
            adapter.add(index);

        } // [cycle 1]

        return adapter;

    } // End of method [getAdapterOfGroupSpinner]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= chest =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] chest 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfChest(LayoutInflater inflater) {

        // [check 1] : 선택된 muscleArea 일때
        if (this.chestGroupingEventData != null) {

            // [iv/C]View : chest wrapper 로 layout 생성
            this.chestWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.chestTitle = (TextView) this.chestWrapper.findViewById(R.id.include_each_random_selection_muscle_area);

            // [iv/C]MaterialCardView : mapping
            this.chestAGroupWrapper = (MaterialCardView) this.chestWrapper.findViewById(R.id.include_each_random_selection_a_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.chestBGroupWrapper = (MaterialCardView) this.chestWrapper.findViewById(R.id.include_each_random_selection_b_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.chestCGroupWrapper = (MaterialCardView) this.chestWrapper.findViewById(R.id.include_each_random_selection_c_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.chestDGroupWrapper = (MaterialCardView) this.chestWrapper.findViewById(R.id.include_each_random_selection_d_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.chestEGroupWrapper = (MaterialCardView) this.chestWrapper.findViewById(R.id.include_each_random_selection_e_group_wrapper);

            // [iv/C]AppCompatSpinner : mapping
            this.chestAGroupSpinner = (AppCompatSpinner) this.chestWrapper.findViewById(R.id.include_each_random_selection_a_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.chestBGroupSpinner = (AppCompatSpinner) this.chestWrapper.findViewById(R.id.include_each_random_selection_b_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.chestCGroupSpinner = (AppCompatSpinner) this.chestWrapper.findViewById(R.id.include_each_random_selection_c_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.chestDGroupSpinner = (AppCompatSpinner) this.chestWrapper.findViewById(R.id.include_each_random_selection_d_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.chestEGroupSpinner = (AppCompatSpinner) this.chestWrapper.findViewById(R.id.include_each_random_selection_e_group_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 추가하기
            this.stepContentWrapper.addView(this.chestWrapper);

        } // [check 1]

    } // End of method [mappingWidgetOfChest]


    /**
     * [method] chest 와 관련 widget init
     */
    private void initWidgetOfChest() {

        // [check 1] :
        if (this.chestGroupingEventData != null) {

            // [iv/C]TextView : chest title setting
            this.chestTitle.setText(getActivity().getString(R.string.type_muscle_area_chest));

            // [method] : chest 그룹 별로 spinner 설정하기
            setAdapterOfChest(this.chestGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfChest]


    /**
     * [method] chest 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     *
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfChest(GroupingEventData groupingEventData) {

        // [check 1] :
        if (groupingEventData != null) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.chestAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.chestAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList().size()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.chestBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.chestBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList().size()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.chestCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.chestCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList().size()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.chestDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.chestDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList().size()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.chestEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.chestEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList().size()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfChest]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= shoulder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] shoulder 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfShoulder(LayoutInflater inflater) {

        // [check 1] : 선택된 muscleArea 일때
        if (this.shoulderGroupingEventData != null) {

            // [iv/C]View : shoulder wrapper 의 layout 생성
            this.shoulderWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.shoulderTitle = (TextView) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_muscle_area);

            // [iv/C]MaterialCardView : mapping
            this.shoulderAGroupWrapper = (MaterialCardView) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_a_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.shoulderBGroupWrapper = (MaterialCardView) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_b_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.shoulderCGroupWrapper = (MaterialCardView) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_c_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.shoulderDGroupWrapper = (MaterialCardView) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_d_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.shoulderEGroupWrapper = (MaterialCardView) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_e_group_wrapper);

            // [iv/C]AppCompatSpinner : mapping
            this.shoulderAGroupSpinner = (AppCompatSpinner) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_a_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.shoulderBGroupSpinner = (AppCompatSpinner) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_b_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.shoulderCGroupSpinner = (AppCompatSpinner) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_c_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.shoulderDGroupSpinner = (AppCompatSpinner) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_d_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.shoulderEGroupSpinner = (AppCompatSpinner) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_e_group_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 추가하기
            this.stepContentWrapper.addView(this.shoulderWrapper);

        } // [check 1]

    } // End of method [mappingWidgetOfShoulder]


    /**
     * [method] shoulder 와 관련 widget init
     */
    private void initWidgetOfShoulder() {

        // [check 1] :
        if (this.shoulderGroupingEventData != null) {

            // [iv/C]TextView : shoulder title setting
            this.shoulderTitle.setText(getActivity().getString(R.string.type_muscle_area_shoulder));

            // [method] : shoulder 그룹 별로 spinner 설정하기
            setAdapterOfShoulder(this.shoulderGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfShoulder]


    /**
     * [method] shoulder 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     *
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfShoulder(GroupingEventData groupingEventData) {

        // [check 1] :
        if (groupingEventData != null) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.shoulderAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.shoulderAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList().size()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.shoulderBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.shoulderBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList().size()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.shoulderCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.shoulderCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList().size()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.shoulderDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.shoulderDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList().size()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.shoulderEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.shoulderEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList().size()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfShoulder]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= lat =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] lat 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfLat(LayoutInflater inflater) {

        // [check 1] : 선택된 muscleArea 일때
        if (this.latGroupingEventData != null) {

            // [iv/C]View : lat wrapper 의 layout 생성
            this.latWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.latTitle = (TextView) this.latWrapper.findViewById(R.id.include_each_random_selection_muscle_area);

            // [iv/C]MaterialCardView : mapping
            this.latAGroupWrapper = (MaterialCardView) this.latWrapper.findViewById(R.id.include_each_random_selection_a_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.latBGroupWrapper = (MaterialCardView) this.latWrapper.findViewById(R.id.include_each_random_selection_b_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.latCGroupWrapper = (MaterialCardView) this.latWrapper.findViewById(R.id.include_each_random_selection_c_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.latDGroupWrapper = (MaterialCardView) this.latWrapper.findViewById(R.id.include_each_random_selection_d_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.latEGroupWrapper = (MaterialCardView) this.latWrapper.findViewById(R.id.include_each_random_selection_e_group_wrapper);

            // [iv/C]AppCompatSpinner : mapping
            this.latAGroupSpinner = (AppCompatSpinner) this.latWrapper.findViewById(R.id.include_each_random_selection_a_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.latBGroupSpinner = (AppCompatSpinner) this.latWrapper.findViewById(R.id.include_each_random_selection_b_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.latCGroupSpinner = (AppCompatSpinner) this.latWrapper.findViewById(R.id.include_each_random_selection_c_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.latDGroupSpinner = (AppCompatSpinner) this.latWrapper.findViewById(R.id.include_each_random_selection_d_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.latEGroupSpinner = (AppCompatSpinner) this.latWrapper.findViewById(R.id.include_each_random_selection_e_group_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 추가하기
            this.stepContentWrapper.addView(this.latWrapper);

        } // [check 1]

    } // End of method [mappingWidgetOfLat]


    /**
     * [method] lat 와 관련 widget init
     */
    private void initWidgetOfLat() {

        // [check 1] :
        if (this.latGroupingEventData != null) {

            // [iv/C]TextView : lat title setting
            this.latTitle.setText(getActivity().getString(R.string.type_muscle_area_lat));

            // [method] : lat 그룹 별로 spinner 설정하기
            setAdapterOfLat(this.latGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfLat]


    /**
     * [method] lat 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     *
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfLat(GroupingEventData groupingEventData) {

        // [check 1] :
        if (groupingEventData != null) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.latAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.latAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList().size()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.latBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.latBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList().size()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.latCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.latCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList().size()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.latDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.latDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList().size()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.latEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.latEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList().size()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfLat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= upper body =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] lat 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfUpperBody(LayoutInflater inflater) {

        // [check 1] : 선택된 muscleArea 일때
        if (this.upperBodyGroupingEventData != null) {

            // [iv/C]View : upperBody wrapper 의 layout 생성
            this.upperBodyWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.upperBodyTitle = (TextView) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_muscle_area);

            // [iv/C]MaterialCardView : mapping
            this.upperBodyAGroupWrapper = (MaterialCardView) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_a_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.upperBodyBGroupWrapper = (MaterialCardView) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_b_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.upperBodyCGroupWrapper = (MaterialCardView) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_c_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.upperBodyDGroupWrapper = (MaterialCardView) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_d_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.upperBodyEGroupWrapper = (MaterialCardView) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_e_group_wrapper);

            // [iv/C]AppCompatSpinner : mapping
            this.upperBodyAGroupSpinner = (AppCompatSpinner) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_a_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.upperBodyBGroupSpinner = (AppCompatSpinner) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_b_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.upperBodyCGroupSpinner = (AppCompatSpinner) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_c_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.upperBodyDGroupSpinner = (AppCompatSpinner) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_d_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.upperBodyEGroupSpinner = (AppCompatSpinner) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_e_group_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 추가하기
            this.stepContentWrapper.addView(this.upperBodyWrapper);

        } // [check 1]

    } // End of method [mappingWidgetOfUpperBody]


    /**
     * [method] upper_body 와 관련 widget init
     */
    private void initWidgetOfUpperBody() {

        // [check 1] :
        if (this.upperBodyGroupingEventData != null) {

            // [iv/C]TextView : upper_body title setting
            this.upperBodyTitle.setText(getActivity().getString(R.string.type_muscle_area_upper_body));

            // [method] : upperBody 그룹 별로 spinner 설정하기
            setAdapterOfUpperBody(this.upperBodyGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfUpperBody]


    /**
     * [method] upper_body 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     *
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfUpperBody(GroupingEventData groupingEventData) {

        // [check 1] :
        if (groupingEventData != null) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.upperBodyAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.upperBodyAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList().size()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.upperBodyBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.upperBodyBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList().size()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.upperBodyCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.upperBodyCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList().size()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.upperBodyDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.upperBodyDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList().size()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.upperBodyEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.upperBodyEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList().size()));

            } // [check 5]

        } // [check 1]
    } // End of method [setAdapterOfUpperBody]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= arm =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] arm 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfArm(LayoutInflater inflater) {

        // [check 1] : 선택된 muscleArea 일때
        if (this.armGroupingEventData != null) {

            // [iv/C]View : arm wrapper 의 layout 생성
            this.armWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.armTitle = (TextView) this.armWrapper.findViewById(R.id.include_each_random_selection_muscle_area);

            // [iv/C]MaterialCardView : mapping
            this.armAGroupWrapper = (MaterialCardView) this.armWrapper.findViewById(R.id.include_each_random_selection_a_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.armBGroupWrapper = (MaterialCardView) this.armWrapper.findViewById(R.id.include_each_random_selection_b_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.armCGroupWrapper = (MaterialCardView) this.armWrapper.findViewById(R.id.include_each_random_selection_c_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.armDGroupWrapper = (MaterialCardView) this.armWrapper.findViewById(R.id.include_each_random_selection_d_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.armEGroupWrapper = (MaterialCardView) this.armWrapper.findViewById(R.id.include_each_random_selection_e_group_wrapper);

            // [iv/C]AppCompatSpinner : mapping
            this.armAGroupSpinner = (AppCompatSpinner) this.armWrapper.findViewById(R.id.include_each_random_selection_a_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.armBGroupSpinner = (AppCompatSpinner) this.armWrapper.findViewById(R.id.include_each_random_selection_b_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.armCGroupSpinner = (AppCompatSpinner) this.armWrapper.findViewById(R.id.include_each_random_selection_c_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.armDGroupSpinner = (AppCompatSpinner) this.armWrapper.findViewById(R.id.include_each_random_selection_d_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.armEGroupSpinner = (AppCompatSpinner) this.armWrapper.findViewById(R.id.include_each_random_selection_e_group_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 추가하기
            this.stepContentWrapper.addView(this.armWrapper);

        } // [check 1]

    } // End of method [mappingWidgetOfArm]


    /**
     * [method] arm 와 관련 widget init
     */
    private void initWidgetOfArm() {

        // [check 1] :
        if (this.armGroupingEventData != null) {

            // [iv/C]TextView : arm title setting
            this.armTitle.setText(getActivity().getString(R.string.type_muscle_area_arm));

            // [method] : arm 그룹 별로 spinner 설정하기
            setAdapterOfArm(this.armGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfArm]


    /**
     * [method] arm 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     *
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfArm(GroupingEventData groupingEventData) {

        // [check 1] :
        if (groupingEventData != null) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.armAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.armAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList().size()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.armBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.armBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList().size()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.armCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.armCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList().size()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.armDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.armDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList().size()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.armEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.armEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList().size()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfArm]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] etc 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfEtc(LayoutInflater inflater) {

        // [check 1] : 선택된 muscleArea 일때
        if (this.etcGroupingEventData != null) {

            // [iv/C]View : etc wrapper 의 layout 생성
            this.etcWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.etcTitle = (TextView) this.etcWrapper.findViewById(R.id.include_each_random_selection_muscle_area);

            // [iv/C]MaterialCardView : mapping
            this.etcAGroupWrapper = (MaterialCardView) this.etcWrapper.findViewById(R.id.include_each_random_selection_a_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.etcBGroupWrapper = (MaterialCardView) this.etcWrapper.findViewById(R.id.include_each_random_selection_b_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.etcCGroupWrapper = (MaterialCardView) this.etcWrapper.findViewById(R.id.include_each_random_selection_c_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.etcDGroupWrapper = (MaterialCardView) this.etcWrapper.findViewById(R.id.include_each_random_selection_d_group_wrapper);

            // [iv/C]MaterialCardView : mapping
            this.etcEGroupWrapper = (MaterialCardView) this.etcWrapper.findViewById(R.id.include_each_random_selection_e_group_wrapper);

            // [iv/C]AppCompatSpinner : mapping
            this.etcAGroupSpinner = (AppCompatSpinner) this.etcWrapper.findViewById(R.id.include_each_random_selection_a_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.etcBGroupSpinner = (AppCompatSpinner) this.etcWrapper.findViewById(R.id.include_each_random_selection_b_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.etcCGroupSpinner = (AppCompatSpinner) this.etcWrapper.findViewById(R.id.include_each_random_selection_c_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.etcDGroupSpinner = (AppCompatSpinner) this.etcWrapper.findViewById(R.id.include_each_random_selection_d_group_spinner);

            // [iv/C]AppCompatSpinner : mapping
            this.etcEGroupSpinner = (AppCompatSpinner) this.etcWrapper.findViewById(R.id.include_each_random_selection_e_group_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 추가하기
            this.stepContentWrapper.addView(this.etcWrapper);

        } // [check 1]

    } // End of method [mappingWidgetOfEtc]

    /**
     * [method] etc 와 관련 widget init
     */
    private void initWidgetOfEtc() {

        // [check 1] :
        if (this.etcGroupingEventData != null) {

            // [iv/C]TextView : etc title setting
            this.etcTitle.setText(getActivity().getString(R.string.type_muscle_area_etc));

            // [method] : etc 그룹 별로 spinner 설정하기
            setAdapterOfEtc(this.etcGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfEtc]


    /**
     * [method] etc 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     *
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfEtc(GroupingEventData groupingEventData) {

        // [check 1] :
        if (groupingEventData != null) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.etcAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.etcAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList().size()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.etcBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.etcBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList().size()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.etcCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.etcCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList().size()));

            }// [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.etcDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.etcDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList().size()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.etcEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.etcEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList().size()));

            } // [check 5]

        } // [check 1]
    } // End of method [setAdapterOfEtc]

}
