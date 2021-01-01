package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.card.MaterialCardView;
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
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.util.ArrayList;

public class Step3D2SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTS] Step3D2SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private boolean[] isSelectedMuscleAreaList;
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
    public Step3D2SectionManager(Activity activity, View view, FragmentManager fragmentManager, boolean[] isSelectedMuscleAreaList) {
        super(activity, view, fragmentManager);
        this.isSelectedMuscleAreaList = isSelectedMuscleAreaList;
    }

    @Override
    public void mappingWidget() {

        // [lv/C]LayoutInflater :
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [iv/C]LinearLayout : stepContentWrapper mapping
        this.stepContentWrapper = (LinearLayout) getView().findViewById(R.id.f_program_step3_2_step_content_wrapper);

        // [method] : chest 용 layout inflater 와 widget mapping
        mappingWidgetOfChest(inflater, this.isSelectedMuscleAreaList[0]);

        // [method] : shoulder 용 layout inflater 와 widget mapping
        mappingWidgetOfShoulder(inflater, this.isSelectedMuscleAreaList[1]);

        // [method] : lat 용 layout inflater 와 widget mapping
        mappingWidgetOfLat(inflater, this.isSelectedMuscleAreaList[2]);

        // [method] : upper_body 용 layout inflater 와 widget mapping
        mappingWidgetOfUpperBody(inflater, this.isSelectedMuscleAreaList[3]);

        // [method] : arm 용 layout inflater 와 widget mapping
        mappingWidgetOfArm(inflater, this.isSelectedMuscleAreaList[4]);

        // [method] : etc 용 layout inflater 와 widget mapping
        mappingWidgetOfEtc(inflater, this.isSelectedMuscleAreaList[5]);

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
        initWidgetOfChest(isSelectedMuscleAreaList[0]);

        // [method] : shoulder widget init
        initWidgetOfShoulder(isSelectedMuscleAreaList[1]);

        // [method] : lat widget init
        initWidgetOfLat(isSelectedMuscleAreaList[2]);

        // [method] : upper_body widget init
        initWidgetOfUpperBody(isSelectedMuscleAreaList[3]);

        // [method] : arm widget init
        initWidgetOfArm(isSelectedMuscleAreaList[4]);

        // [method] : etc widget init
        initWidgetOfEtc(isSelectedMuscleAreaList[5]);

        // [lv/C]DatabaseReference :
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= chest =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : chest 목록을 가져와서 그룹화하기
                chestGroupingEventData = loadContentByMuscleArea(snapshot, isSelectedMuscleAreaList[0], MuscleArea.CHEST);

                // [method] : chest spinner widget init
                setAdapterOfChest(isSelectedMuscleAreaList[0], chestGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= shoulder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : shoulder 목록을 가져와서
                shoulderGroupingEventData = loadContentByMuscleArea(snapshot, isSelectedMuscleAreaList[1], MuscleArea.SHOULDER);

                // [method] : shoulder spinner widget init
                setAdapterOfShoulder(isSelectedMuscleAreaList[1], shoulderGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= lat =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : lat 목록을 가져와서 그룹화하기
                latGroupingEventData = loadContentByMuscleArea(snapshot, isSelectedMuscleAreaList[2], MuscleArea.LAT);

                // [method] : lat spinner widget init
                setAdapterOfLat(isSelectedMuscleAreaList[2], latGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= upper_body =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : upper_body 목록을 가져와서 그룹화하기
                upperBodyGroupingEventData = loadContentByMuscleArea(snapshot, isSelectedMuscleAreaList[3], MuscleArea.LEG);

                // [method] : upper_body spinner widget init
                setAdapterOfUpperBody(isSelectedMuscleAreaList[3], upperBodyGroupingEventData);


                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= arm =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : arm 목록을 가져와서 그룹화하기
                armGroupingEventData = loadContentByMuscleArea(snapshot, isSelectedMuscleAreaList[4], MuscleArea.ARM);

                // [method] : arm spinner widget init
                setAdapterOfArm(isSelectedMuscleAreaList[4], armGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : etc 목록을 가져와서 그룹화하기
                etcGroupingEventData = loadContentByMuscleArea(snapshot, isSelectedMuscleAreaList[5], MuscleArea.ETC);

                // [method] : etc spinner widget init
                setAdapterOfEtc(isSelectedMuscleAreaList[5], etcGroupingEventData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void setClickListenerOfNext() {

    }

    private GroupingEventData loadContentByMuscleArea(DataSnapshot snapshot, boolean isSelectedMuscleArea, MuscleArea muscleArea) {
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


//            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A GROUP ----> O");
//
//            } else {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A GROUP ----> X");
//
//            }
//
//            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "B GROUP ----> O");
//
//            } else {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "B GROUP ----> X");
//
//            }
//
//            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "C GROUP ----> O");
//
//            } else {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "C GROUP ----> X");
//
//            }
//
//            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "D GROUP ----> O");
//
//            } else {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "D GROUP ----> X");
//
//            }
//
//            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "E GROUP ----> O");
//
//            } else {
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "E GROUP ----> X");
//
//            }

//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------------------------------------------------------");
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "groupingEventData 를 확인하겠습니다.");
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-.-._>__>_>_>_>_>_>_>_>_>_>_>_>_ A");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, groupingEventData.getAGroupEventArrayList());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-.-._>__>_>_>_>_>_>_>_>_>_>_>_>_ B");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, groupingEventData.getBGroupEventArrayList());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-.-._>__>_>_>_>_>_>_>_>_>_>_>_>_ C");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, groupingEventData.getCGroupEventArrayList());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-.-._>__>_>_>_>_>_>_>_>_>_>_>_>_ D");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, groupingEventData.getDGroupEventArrayList());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-.-._>__>_>_>_>_>_>_>_>_>_>_>_>_ E");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, groupingEventData.getEGroupEventArrayList());

        } // [check 1]

        return groupingEventData;
    } // End of method [loadContentByMuscleArea]


    private ArrayAdapter<Integer> getAdapterOfGroupSpinner(ArrayList<Event> eventArrayList) {

        // [lv/C]ArrayAdapter<Integer> :
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), R.layout.support_simple_spinner_dropdown_item);

        // [cycle 1] : a group event array list 의 size 만큼
        for (int index = 0; index < eventArrayList.size(); index++) {

            // [lv/C]ArrayAdapter<Integer> : index 추가
            adapter.add(index);

        } // [cycle 1]

        // [lv/C]ArrayAdapter<Integer> : size 추가
        adapter.add(eventArrayList.size());

        return adapter;

    } // End of method [getAdapterOfGroupSpinner]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= chest =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] chest 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfChest(LayoutInflater inflater, boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 muscleArea 일때
        if (isSelectedMuscleArea) {

            // [iv/C]View : chest wrapper 로 layout 생성
            this.chestWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.chestTitle = (TextView) this.chestWrapper.findViewById(R.id.include_each_random_selection_muscle_area_title);

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
    private void initWidgetOfChest(boolean isSelectedMuscleArea) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [iv/C]TextView : chest title setting
            this.chestTitle.setText(getActivity().getString(R.string.type_muscle_area_chest));

        } // [check 1]

    } // End of method [initWidgetOfChest]


    /**
     * [method] chest 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfChest(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.chestAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.chestAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.chestBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.chestBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.chestCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.chestCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.chestDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.chestDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.chestEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.chestEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfChest]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= shoulder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] shoulder 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfShoulder(LayoutInflater inflater, boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 muscleArea 일때
        if (isSelectedMuscleArea) {

            // [iv/C]View : shoulder wrapper 의 layout 생성
            this.shoulderWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.shoulderTitle = (TextView) this.shoulderWrapper.findViewById(R.id.include_each_random_selection_muscle_area_title);

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
    private void initWidgetOfShoulder(boolean isSelectedMuscleArea) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [iv/C]TextView : shoulder title setting
            this.shoulderTitle.setText(getActivity().getString(R.string.type_muscle_area_shoulder));

        } // [check 1]

    } // End of method [initWidgetOfShoulder]


    /**
     * [method] shoulder 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfShoulder(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.shoulderAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.shoulderAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.shoulderBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.shoulderBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.shoulderCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.shoulderCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.shoulderDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.shoulderDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.shoulderEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.shoulderEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfShoulder]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= lat =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] lat 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfLat(LayoutInflater inflater, boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 muscleArea 일때
        if (isSelectedMuscleArea) {

            // [iv/C]View : lat wrapper 의 layout 생성
            this.latWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.latTitle = (TextView) this.latWrapper.findViewById(R.id.include_each_random_selection_muscle_area_title);

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
    private void initWidgetOfLat(boolean isSelectedMuscleArea) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [iv/C]TextView : lat title setting
            this.latTitle.setText(getActivity().getString(R.string.type_muscle_area_lat));

        } // [check 1]

    } // End of method [initWidgetOfLat]


    /**
     * [method] lat 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfLat(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.latAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.latAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.latBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.latBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.latCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.latCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.latDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.latDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.latEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.latEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfLat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= upper body =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] lat 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfUpperBody(LayoutInflater inflater, boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 muscleArea 일때
        if (isSelectedMuscleArea) {

            // [iv/C]View : upperBody wrapper 의 layout 생성
            this.upperBodyWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.upperBodyTitle = (TextView) this.upperBodyWrapper.findViewById(R.id.include_each_random_selection_muscle_area_title);

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
    private void initWidgetOfUpperBody(boolean isSelectedMuscleArea) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [iv/C]TextView : upper_body title setting
            this.upperBodyTitle.setText(getActivity().getString(R.string.type_muscle_area_upper_body));

        } // [check 1]

    } // End of method [initWidgetOfUpperBody]


    /**
     * [method] upper_body 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfUpperBody(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.upperBodyAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.upperBodyAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.upperBodyBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.upperBodyBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.upperBodyCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.upperBodyCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.upperBodyDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.upperBodyDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.upperBodyEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.upperBodyEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList()));

            } // [check 5]

        } // [check 1]
    } // End of method [setAdapterOfUpperBody]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= arm =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] arm 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfArm(LayoutInflater inflater, boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 muscleArea 일때
        if (isSelectedMuscleArea) {

            // [iv/C]View : arm wrapper 의 layout 생성
            this.armWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.armTitle = (TextView) this.armWrapper.findViewById(R.id.include_each_random_selection_muscle_area_title);

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
    private void initWidgetOfArm(boolean isSelectedMuscleArea) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [iv/C]TextView : arm title setting
            this.armTitle.setText(getActivity().getString(R.string.type_muscle_area_arm));

        } // [check 1]

    } // End of method [initWidgetOfArm]


    /**
     * [method] arm 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfArm(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.armAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.armAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.armBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.armBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.armCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.armCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList()));

            } // [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.armDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.armDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.armEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.armEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList()));

            } // [check 5]

        } // [check 1]

    } // End of method [setAdapterOfArm]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] etc 와 관련된 widget mapping
     *
     * @param inflater LayoutInflater
     */
    private void mappingWidgetOfEtc(LayoutInflater inflater, boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 muscleArea 일때
        if (isSelectedMuscleArea) {

            // [iv/C]View : etc wrapper 의 layout 생성
            this.etcWrapper = inflater.inflate(R.layout.include_each_random_selection, null);

            // [iv/C]TextView : title mapping
            this.etcTitle = (TextView) this.etcWrapper.findViewById(R.id.include_each_random_selection_muscle_area_title);

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
    private void initWidgetOfEtc(boolean isSelectedMuscleArea) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [iv/C]TextView : etc title setting
            this.etcTitle.setText(getActivity().getString(R.string.type_muscle_area_etc));

        } // [check 1]

    } // End of method [initWidgetOfEtc]


    /**
     * [method] etc 의 그룹화된 groupingEventData 를 각 그룹의 spinner 를 설정한다.
     * @param groupingEventData 그룹화된 event 데이터
     */
    private void setAdapterOfEtc(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] :
        if (isSelectedMuscleArea) {

            // [check 1] : a group
            if (!groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : a group wrapper GONE
                this.etcAGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : a group spinner 을 adapter 와 연겨하기
                this.etcAGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getAGroupEventArrayList()));

            } // [check 1]

            // [check 2] : b group
            if (!groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : b group wrapper GONE
                this.etcBGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : b group spinner 을 adapter 와 연겨하기
                this.etcBGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getBGroupEventArrayList()));

            } // [check 2]

            // [check 3] : c group
            if (!groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : c group wrapper GONE
                this.etcCGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : c group spinner 을 adapter 와 연겨하기
                this.etcCGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getCGroupEventArrayList()));

            }// [check 3]

            // [check 4] : d group
            if (!groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : d group wrapper GONE
                this.etcDGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : d group spinner 을 adapter 와 연겨하기
                this.etcDGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getDGroupEventArrayList()));

            } // [check 4]

            // [check 5] : e group
            if (!groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : e group wrapper GONE
                this.etcEGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]Spinner : e group spinner 을 adapter 와 연겨하기
                this.etcEGroupSpinner.setAdapter(getAdapterOfGroupSpinner(groupingEventData.getEGroupEventArrayList()));

            } // [check 5]

        } // [check 1]
    } // End of method [setAdapterOfEtc]

}
