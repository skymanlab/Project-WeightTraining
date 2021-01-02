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
    private static final String CLASS_NAME = "[PFTS] Step3D3SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private boolean[] isSelectedMuscleAreaList;

    // instance varaible
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
    public Step3D3SectionManager(Activity activity, View view, FragmentManager fragmentManager, boolean[] isSelectedMuscleAreaList) {
        super(activity, view, fragmentManager);
        this.isSelectedMuscleAreaList = isSelectedMuscleAreaList;
    }

    @Override
    public void mappingWidget() {

        // [lv/C]LayoutInflater : 객체 생성
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [iv/C]LinearLayout : mapping
        this.stepContentWrapper = (LinearLayout) getView().findViewById(R.id.f_program_step3_3_step_content_wrapper);

        // [method] : [0] chest widget mapping
        mappingWidgetOfChest(this.isSelectedMuscleAreaList[0], inflater);

        // [method] : [1] shoulder widget mapping
        mappingWidgetOfShoulder(this.isSelectedMuscleAreaList[1], inflater);

        // [method] : [2] lat widget mapping
        mappingWidgetOfLat(this.isSelectedMuscleAreaList[2], inflater);

        // [method] : [3] upper_body widget mapping
        mappingWidgetOfUpperBody(this.isSelectedMuscleAreaList[3], inflater);

        // [method] : [4] arm widget mapping
        mappingWidgetOfArm(this.isSelectedMuscleAreaList[4], inflater);

        // [method] : [5] etc widget mapping
        mappingWidgetOfEtc(this.isSelectedMuscleAreaList[5], inflater);

    }

    @Override
    public void initWidget() {

        // [iv/C] : step 3-3
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_THREE);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.initWidget();

        // [method] : [0] chest widget mapping
        initWidgetOfChest(this.isSelectedMuscleAreaList[0]);

        // [method] : [1] shoulder widget mapping
        initWidgetOfShoulder(this.isSelectedMuscleAreaList[1]);

        // [method] : [2] lat widget mapping
        initWidgetOfLat(this.isSelectedMuscleAreaList[2]);

        // [method] : [3] upper_body widget mapping
        initWidgetOfUpperBody(this.isSelectedMuscleAreaList[3]);

        // [method] : [4] arm widget mapping
        initWidgetOfArm(this.isSelectedMuscleAreaList[4]);

        // [method] : [5] etc widget mapping
        initWidgetOfEtc(this.isSelectedMuscleAreaList[5]);

        // [lv/C]DatabaseReference :
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

        // [lv/C]Query :
        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= chest =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : chest 목록을 가져와서 그룹화하기
                chestGroupingEventData = loadContentByMuscleArea(isSelectedMuscleAreaList[0], snapshot, MuscleArea.CHEST);

                // [method] : chestSpinner adapter setting
                setAdapterOfChest(isSelectedMuscleAreaList[0], chestGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= shoulder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : shoulder 목록을 가져와서
                shoulderGroupingEventData = loadContentByMuscleArea(isSelectedMuscleAreaList[1], snapshot, MuscleArea.SHOULDER);

                // [method] : chestSpinner adapter setting
                setAdapterOfShoulder(isSelectedMuscleAreaList[1], shoulderGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= lat =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : lat 목록을 가져와서 그룹화하기
                latGroupingEventData = loadContentByMuscleArea(isSelectedMuscleAreaList[2], snapshot, MuscleArea.LAT);

                // [method] : chestSpinner adapter setting
                setAdapterOfLat(isSelectedMuscleAreaList[2], latGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= upper_body =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : upper_body 목록을 가져와서 그룹화하기
                upperBodyGroupingEventData = loadContentByMuscleArea(isSelectedMuscleAreaList[3], snapshot, MuscleArea.LEG);

                // [method] : chestSpinner adapter setting
                setAdapterOfUpperBody(isSelectedMuscleAreaList[3], upperBodyGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= arm =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : arm 목록을 가져와서 그룹화하기
                armGroupingEventData = loadContentByMuscleArea(isSelectedMuscleAreaList[4], snapshot, MuscleArea.ARM);

                // [method] : chestSpinner adapter setting
                setAdapterOfArm(isSelectedMuscleAreaList[4], armGroupingEventData);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]GroupingEventData : etc 목록을 가져와서 그룹화하기
                etcGroupingEventData = loadContentByMuscleArea(isSelectedMuscleAreaList[5], snapshot, MuscleArea.ETC);

                // [method] : chestSpinner adapter setting
                setAdapterOfEtc(isSelectedMuscleAreaList[5], etcGroupingEventData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // "데이터를 가져오는데 오류가 발생하였습니다." Toast 메시지 표시
                Toast.makeText(getActivity(), getActivity().getString(R.string.f_program_step3_2_firebase_database_error_message), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void setClickListenerOfNext() {

        // [check 1] : chest 가 선택한 항목인가요?
        if (this.isSelectedMuscleAreaList[0]) {

        } // [check 1]

        // [check 2] : shoulder 가 선택한 항목인가요?
        if (this.isSelectedMuscleAreaList[1]) {

        } // [check 2]

        // [check 3] : lat 가 선택한 항목인가요?
        if (this.isSelectedMuscleAreaList[2]) {

        } // [check 3]

        // [check 4] : upper_body 가 선택한 항목인가요?
        if (this.isSelectedMuscleAreaList[3]) {

        } // [check 4]

        // [check 5] : arm 가 선택한 항목인가요?
        if (this.isSelectedMuscleAreaList[4]) {

        } // [check 5]

        // [check 6] : etc 가 선택한 항목인가요?
        if (this.isSelectedMuscleAreaList[5]) {

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
    private void mappingWidgetOfChest(boolean isSelectedMuscleArea, LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

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


    private void initWidgetOfChest(boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [iv/C]MaterialTextView : title setting
            this.chestTitle.setText(getActivity().getString(R.string.type_muscle_area_chest));

        } // [check 1]

    } // End of method [initWidgetOfChest]


    private void setAdapterOfChest(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 chestSpinner setting
            this.chestSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfChest]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Shoulder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfShoulder(boolean isSelectedMuscleArea, LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

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


    private void initWidgetOfShoulder(boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [iv/C]MaterialTextView : title setting
            this.shoulderTitle.setText(getActivity().getString(R.string.type_muscle_area_shoulder));

        } // [check 1]

    } // End of method [initWidgetOfShoulder]


    private void setAdapterOfShoulder(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 shoulderSpinner setting
            this.shoulderSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfShoulder]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Lat =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfLat(boolean isSelectedMuscleArea, LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

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


    private void initWidgetOfLat(boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [iv/C]MaterialTextView : title setting
            this.latTitle.setText(getActivity().getString(R.string.type_muscle_area_lat));

        } // [check 1]

    } // End of method [initWidgetOfLat]


    private void setAdapterOfLat(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 latSpinner setting
            this.latSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfLat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Upper Body =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfUpperBody(boolean isSelectedMuscleArea, LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

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


    private void initWidgetOfUpperBody(boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [iv/C]MaterialTextView : title setting
            this.upperBodyTitle.setText(getActivity().getString(R.string.type_muscle_area_upper_body));

        } // [check 1]

    } // End of method [initWidgetOfUpperBody]


    private void setAdapterOfUpperBody(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 upperBodySpinner setting
            this.upperBodySpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfUpperBody]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ARM =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfArm(boolean isSelectedMuscleArea, LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

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


    private void initWidgetOfArm(boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [iv/C]MaterialTextView : title setting
            this.armTitle.setText(getActivity().getString(R.string.type_muscle_area_arm));

        } // [check 1]

    } // End of method [initWidgetOfArm]


    private void setAdapterOfArm(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 armSpinner setting
            this.armSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfArm]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void mappingWidgetOfEtc(boolean isSelectedMuscleArea, LayoutInflater inflater) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

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


    private void initWidgetOfEtc(boolean isSelectedMuscleArea) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [iv/C]MaterialTextView : title setting
            this.etcTitle.setText(getActivity().getString(R.string.type_muscle_area_etc));

        } // [check 1]

    } // End of method [initWidgetOfEtc]


    private void setAdapterOfEtc(boolean isSelectedMuscleArea, GroupingEventData groupingEventData) {

        // [check 1] : 선택된 부위이다.
        if (isSelectedMuscleArea) {

            // [lv/C]AppCompatSpinner : adapter 를 생성하고 etcSpinner setting
            this.etcSpinner.setAdapter(setAdapterOfAllSpinner(groupingEventData.getAllSize()));

        } // [check 1]

    } // End of method [setAdapterOfEtc]

}
