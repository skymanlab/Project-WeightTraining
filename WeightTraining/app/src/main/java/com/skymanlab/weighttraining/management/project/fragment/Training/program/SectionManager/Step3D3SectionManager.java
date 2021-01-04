package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.util.ArrayList;

public class Step3D3SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] Step3D3SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable : step content
    private LinearLayout stepContentWrapper;

    // instance variable : chest
    private View chestView;
    private MaterialCardView chestWrapper;
    private MaterialTextView chestTitle;
    private AppCompatSpinner chestSpinner;

    // instance variable : shoulder
    private View shoulderView;
    private MaterialCardView shoulderWrapper;
    private MaterialTextView shoulderTitle;
    private AppCompatSpinner shoulderSpinner;

    // instance variable : lat
    private View latView;
    private MaterialCardView latWrapper;
    private MaterialTextView latTitle;
    private AppCompatSpinner latSpinner;

    // instance variable : upper_body
    private View upperBodyView;
    private MaterialCardView upperBodyWrapper;
    private MaterialTextView upperBodyTitle;
    private AppCompatSpinner upperBodySpinner;

    // instance variable : arm
    private View armView;
    private MaterialCardView armWrapper;
    private MaterialTextView armTitle;
    private AppCompatSpinner armSpinner;

    // instance variable : etc
    private View etcView;
    private MaterialCardView etcWrapper;
    private MaterialTextView etcTitle;
    private AppCompatSpinner etcSpinner;

    // instance variable
    private GroupingEventData chestGroupingEventData;
    private GroupingEventData shoulderGroupingEventData;
    private GroupingEventData latGroupingEventData;
    private GroupingEventData upperBodyGroupingEventData;
    private GroupingEventData armGroupingEventData;
    private GroupingEventData etcGroupingEventData;

    // constructor
    public Step3D3SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
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

        // [lv/C]LayoutInflater : 객체 생성
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [iv/C]LinearLayout : mapping
        this.stepContentWrapper = (LinearLayout) getView().findViewById(R.id.f_program_step3_3_step_content_wrapper);

        // [method] : [0] chest widget mapping
        mappingWidgetOfChest(inflater);

        // [method] : [1] shoulder widget mapping
        mappingWidgetOfShoulder(inflater);

        // [method] : [2] lat widget mapping
        mappingWidgetOfLat(inflater);

        // [method] : [3] upper_body widget mapping
        mappingWidgetOfUpperBody(inflater);

        // [method] : [4] arm widget mapping
        mappingWidgetOfArm( inflater);

        // [method] : [5] etc widget mapping
        mappingWidgetOfEtc(inflater);

    }

    @Override
    public void initWidget() {

        // [iv/C] : step 3-3
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_THREE);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.initWidget();

        // [method] : [0] chest widget mapping
        initWidgetOfChest();

        // [method] : [1] shoulder widget mapping
        initWidgetOfShoulder();

        // [method] : [2] lat widget mapping
        initWidgetOfLat();

        // [method] : [3] upper_body widget mapping
        initWidgetOfUpperBody();

        // [method] : [4] arm widget mapping
        initWidgetOfArm();

        // [method] : [5] etc widget mapping
        initWidgetOfEtc();

    }

    @Override
    public void setClickListenerOfNext() {

        // [check 1] : chest 가 선택한 항목인가요?
        if (this.chestGroupingEventData != null) {

        } // [check 1]

        // [check 2] : shoulder 가 선택한 항목인가요?
        if (this.shoulderGroupingEventData != null) {

        } // [check 2]

        // [check 3] : lat 가 선택한 항목인가요?
        if (this.latGroupingEventData != null) {

        } // [check 3]

        // [check 4] : upper_body 가 선택한 항목인가요?
        if (this.upperBodyGroupingEventData != null) {

        } // [check 4]

        // [check 5] : arm 가 선택한 항목인가요?
        if (this.armGroupingEventData != null) {

        } // [check 5]

        // [check 6] : etc 가 선택한 항목인가요?
        if (this.etcGroupingEventData != null) {

        } // [check 6]
    }


    /**
     * firebase database 에서 event/$uid$/ 에서 가져온 snapshot 을 통해 각 MuscleArea 의 목록을 가져와서 그룹화한 GroupingEventData 를 만들어서 반환한다.
     *
     * @param isSelectedMuscleArea 해당 MuscleArea 의 선택 여부
     * @param snapshot             firebase database 에서 가져온 데이터
     * @param muscleArea           firebase database 에서 목록 가져올 때, 어떤 부위의 목록을 가져오는 건가요?
     * @return
     */
    private GroupingEventData loadContentByMuscleArea(boolean isSelectedMuscleArea, DataSnapshot snapshot, MuscleArea muscleArea) {
        final String METHOD_NAME = "[loadContentByMuscleArea] ";

        // [lv/C]GroupingEventData : eventArrayList 를 그룹화한다.
        GroupingEventData groupingEventData = null;

        // [check 1] : true(선택된 muscleArea 이다.) ->
        if (isSelectedMuscleArea) {

            // [lv/C]ArrayList<Event> : 각 muscleArea 담을
            ArrayList<Event> eventArrayList = new ArrayList<>();

            for (DataSnapshot search : snapshot.child(muscleArea.toString()).getChildren()) {

                // [lv/C]Event :
                Event data = search.getValue(Event.class);
                data.setKey(search.getKey());

                // [lv/C]ArrayList<Event> : 위의 객체 추가하기
                eventArrayList.add(data);

            }

            // [lv/C]GroupingEventUtil : eventArrayList 를 그룹화하고, 그룹화한 데이터를 groupingEventData 에 저장하기
            GroupingEventUtil util = new GroupingEventUtil(eventArrayList);
            util.classifyEventArrayListToGroupType();
            groupingEventData = util.getGroupingEvent();

        } // [check 1]

        return groupingEventData;
    } // End of method [loadContentByMuscleArea]


    /**
     * @param size 추가할 항목의 size
     * @return 항목이 추가된 adpater
     */
    private ArrayAdapter<Integer> setAdapterOfAllSpinner(int size) {

        // [lv/C]ArrayList<Integer> :
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), R.layout.support_simple_spinner_dropdown_item);

        // [cycle 1] :
        for (int index = 0; index <= size; index++) {

            // [lv/C]ArrayAdapter<Integer> : size 만큼 index 를 추가한다.
            adapter.add(index);

        } // [cycle 1]

        return adapter;

    } // End of method [setAdapterOfAllSpinner]

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Chest =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfChest(LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (this.chestGroupingEventData != null) {

            // [iv/C]View : chestView mapping
            this.chestView = inflater.inflate(R.layout.include_all_random_selection, null);

            // [iv/C]MaterialCardView : chestWrapper mapping
            this.chestWrapper = (MaterialCardView) this.chestView.findViewById(R.id.include_all_random_selection_wrapper);

            // [iv/C]MaterialTextView : chestTitle mapping
            this.chestTitle = (MaterialTextView) this.chestView.findViewById(R.id.include_all_random_selection_title);

            // [iv/C]AppCompatSpinner : chestSpinner mapping
            this.chestSpinner = (AppCompatSpinner) this.chestView.findViewById(R.id.include_all_random_selection_all_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 위의 chestView 를 추가한다.
            this.stepContentWrapper.addView(this.chestView);

        } // [check 1]

    } // End of method [mappingWidgetOfChest]


    private void initWidgetOfChest() {

        // [check 1] : 선택된 부위이다.
        if (this.chestGroupingEventData != null) {

            // [iv/C]MaterialTextView : title setting
            this.chestTitle.setText(getActivity().getString(R.string.type_muscle_area_chest));

            // [method] : [0] chest adapter setting
            setAdapterOfChest(this.chestGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfChest]


    private void setAdapterOfChest(GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (groupingEventData != null) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 chestSpinner setting
            this.chestSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfChest]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Shoulder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfShoulder( LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (this.shoulderGroupingEventData != null) {

            // [iv/C]View : shoulderView mapping
            this.shoulderView = inflater.inflate(R.layout.include_all_random_selection, null);

            // [iv/C]MaterialCardView : shoulderWrapper mapping
            this.shoulderWrapper = (MaterialCardView) this.shoulderView.findViewById(R.id.include_all_random_selection_wrapper);

            // [iv/C]MaterialTextView : shoulderTitle mapping
            this.shoulderTitle = (MaterialTextView) this.shoulderView.findViewById(R.id.include_all_random_selection_title);

            // [iv/C]AppCompatSpinner : shoulderSpinner mapping
            this.shoulderSpinner = (AppCompatSpinner) this.shoulderView.findViewById(R.id.include_all_random_selection_all_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 위의 shoulderView 를 추가한다.
            this.stepContentWrapper.addView(this.shoulderView);

        } // [check 1]

    } // End of method [mappingWidgetOfShoulder]


    private void initWidgetOfShoulder() {

        // [check 1] : 선택된 부위이다.
        if (this.shoulderGroupingEventData != null) {

            // [iv/C]MaterialTextView : title setting
            this.shoulderTitle.setText(getActivity().getString(R.string.type_muscle_area_shoulder));

            // [method] : [1] shoulder adapter setting
            setAdapterOfShoulder(this.shoulderGroupingEventData);
        } // [check 1]

    } // End of method [initWidgetOfShoulder]


    private void setAdapterOfShoulder(GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (groupingEventData != null) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 shoulderSpinner setting
            this.shoulderSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfShoulder]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Lat =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfLat(LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (this.latGroupingEventData != null) {

            // [iv/C]View : latView mapping
            this.latView = inflater.inflate(R.layout.include_all_random_selection, null);

            // [iv/C]MaterialCardView : latWrapper mapping
            this.latWrapper = (MaterialCardView) this.latView.findViewById(R.id.include_all_random_selection_wrapper);

            // [iv/C]MaterialTextView : latTitle mapping
            this.latTitle = (MaterialTextView) this.latView.findViewById(R.id.include_all_random_selection_title);

            // [iv/C]AppCompatSpinner : latSpinner mapping
            this.latSpinner = (AppCompatSpinner) this.latView.findViewById(R.id.include_all_random_selection_all_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 위의 latView 를 추가한다.
            this.stepContentWrapper.addView(this.latView);

        } // [check 1]

    } // End of method [mappingWidgetOfLat]


    private void initWidgetOfLat() {

        // [check 1] : 선택된 부위이다.
        if (this.latGroupingEventData != null) {

            // [iv/C]MaterialTextView : title setting
            this.latTitle.setText(getActivity().getString(R.string.type_muscle_area_lat));

            // [method] : [2] lat adapter setting
            setAdapterOfLat(this.latGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfLat]


    private void setAdapterOfLat(GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (groupingEventData != null) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 latSpinner setting
            this.latSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfLat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Upper Body =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfUpperBody( LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (this.upperBodyGroupingEventData != null) {

            // [iv/C]View : upperBodyView mapping
            this.upperBodyView = inflater.inflate(R.layout.include_all_random_selection, null);

            // [iv/C]MaterialCardView : upperBodyWrapper mapping
            this.upperBodyWrapper = (MaterialCardView) this.upperBodyView.findViewById(R.id.include_all_random_selection_wrapper);

            // [iv/C]MaterialTextView : upperBodyTitle mapping
            this.upperBodyTitle = (MaterialTextView) this.upperBodyView.findViewById(R.id.include_all_random_selection_title);

            // [iv/C]AppCompatSpinner : upperBodySpinner mapping
            this.upperBodySpinner = (AppCompatSpinner) this.upperBodyView.findViewById(R.id.include_all_random_selection_all_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 위의 upperBodyView 를 추가한다.
            this.stepContentWrapper.addView(this.upperBodyView);

        } // [check 1]

    } // End of method [mappingWidgetOfUpperBody]


    private void initWidgetOfUpperBody() {

        // [check 1] : 선택된 부위이다.
        if (this.upperBodyGroupingEventData != null) {

            // [iv/C]MaterialTextView : title setting
            this.upperBodyTitle.setText(getActivity().getString(R.string.type_muscle_area_upper_body));

            // [method] : [3] upper_body adapter setting
            setAdapterOfUpperBody(this.upperBodyGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfUpperBody]


    private void setAdapterOfUpperBody( GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (groupingEventData != null) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 upperBodySpinner setting
            this.upperBodySpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfUpperBody]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ARM =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfArm( LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (this.armGroupingEventData != null) {

            // [iv/C]View : armView mapping
            this.armView = inflater.inflate(R.layout.include_all_random_selection, null);

            // [iv/C]MaterialCardView : armWrapper mapping
            this.armWrapper = (MaterialCardView) this.armView.findViewById(R.id.include_all_random_selection_wrapper);

            // [iv/C]MaterialTextView : armTitle mapping
            this.armTitle = (MaterialTextView) this.armView.findViewById(R.id.include_all_random_selection_title);

            // [iv/C]AppCompatSpinner : armSpinner mapping
            this.armSpinner = (AppCompatSpinner) this.armView.findViewById(R.id.include_all_random_selection_all_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 위의 armView 를 추가한다.
            this.stepContentWrapper.addView(this.armView);

        } // [check 1]

    } // End of method [mappingWidgetOfArm]


    private void initWidgetOfArm() {

        // [check 1] : 선택된 부위이다.
        if (this.armGroupingEventData != null) {

            // [iv/C]MaterialTextView : title setting
            this.armTitle.setText(getActivity().getString(R.string.type_muscle_area_arm));

            // [method] : [4] arm adapter setting
            setAdapterOfArm(this.armGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfArm]


    private void setAdapterOfArm(GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (groupingEventData != null) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 armSpinner setting
            this.armSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfArm]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfEtc(LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (this.etcGroupingEventData != null) {

            // [iv/C]View : etcView mapping
            this.etcView = inflater.inflate(R.layout.include_all_random_selection, null);

            // [iv/C]MaterialCardView : etcWrapper mapping
            this.etcWrapper = (MaterialCardView) this.etcView.findViewById(R.id.include_all_random_selection_wrapper);

            // [iv/C]MaterialTextView : etcTitle mapping
            this.etcTitle = (MaterialTextView) this.etcView.findViewById(R.id.include_all_random_selection_title);

            // [iv/C]AppCompatSpinner : etcSpinner mapping
            this.etcSpinner = (AppCompatSpinner) this.etcView.findViewById(R.id.include_all_random_selection_all_spinner);

            // [iv/C]LinearLayout : stepContentWrapper 에 위의 etcView 를 추가한다.
            this.stepContentWrapper.addView(this.etcView);

        } // [check 1]

    } // End of method [mappingWidgetOfEtc]


    private void initWidgetOfEtc() {

        // [check 1] : 선택된 부위이다.
        if (this.etcGroupingEventData != null) {

            // [iv/C]MaterialTextView : title setting
            this.etcTitle.setText(getActivity().getString(R.string.type_muscle_area_etc));

            // [method] : [5] etc adapter setting
            setAdapterOfEtc(this.etcGroupingEventData);

        } // [check 1]

    } // End of method [initWidgetOfEtc]


    private void setAdapterOfEtc( GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (groupingEventData != null) {


            // [lv/C]AppCompatSpinner : adapter 를 생성하고 etcSpinner setting
            this.etcSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfEtc]

}
