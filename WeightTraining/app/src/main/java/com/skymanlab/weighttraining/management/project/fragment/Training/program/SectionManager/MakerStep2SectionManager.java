package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
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
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep3D1Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep3D2Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep3D3Fragment;

import java.util.ArrayList;
import java.util.List;

public class MakerStep2SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, MakerStepManager.OnPreviousClickListener, MakerStepManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep2SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private int step1SelectedType;

    // instance variable
    private MaterialButtonToggleGroup muscleAreaToggleGroup;
        private MaterialButton chest;
    private MaterialButton shoulder;
    private MaterialButton lat;
    private MaterialButton upperBody;
    private MaterialButton arm;
    private MaterialButton etc;
    private ContentLoadingProgressBar progressBar;

    // instance variable
    private MakerStepManager makerStepManager;

    // instance variable
    private boolean[] isSelectedMuscleAreaList; // toggle button 에서 클릭 한 값을 알아내는

    // constructor
    public MakerStep2SectionManager(Activity activity, View view, FragmentManager fragmentManager, int step1SelectedType) {
        super(activity, view, fragmentManager);
        this.step1SelectedType = step1SelectedType;
    }

    @Override
    public void connectWidget() {

        // [iv/C]MaterialButtonToggleGroup :  connect
        this.muscleAreaToggleGroup = (MaterialButtonToggleGroup) getView().findViewById(R.id.f_maker_step2_muscle_area_toggle_group);

        // [iv/C]MaterialButton : chest connect
        this.chest = (MaterialButton) getView().findViewById(R.id.f_maker_step2_chest);

        // [iv/C]MaterialButton : shoulder connect
        this.shoulder = (MaterialButton) getView().findViewById(R.id.f_maker_step2_shoulder);

        // [iv/C]MaterialButton : lat connect
        this.lat = (MaterialButton) getView().findViewById(R.id.f_maker_step2_lat);

        // [iv/C]MaterialButton : upperBody connect
        this.upperBody = (MaterialButton) getView().findViewById(R.id.f_maker_step2_upper_body);

        // [iv/C]MaterialButton : arm connect
        this.arm = (MaterialButton) getView().findViewById(R.id.f_maker_step2_arm);

        // [iv/C]MaterialButton : etc connect
        this.etc = (MaterialButton) getView().findViewById(R.id.f_maker_step2_etc);

        // [iv/C]ContentLoadingProgressBar : progressBar connect
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_maker_step2_progress_bar);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]MakerStepManager : step 2 단계 설정 / OnNextClickListener 는 이 클래스에 implements 하여 override 된 함수에 구현한다.
        this.makerStepManager = new MakerStepManager(getView(), getFragmentManager(), MakerStepManager.STEP_TWO);
        this.makerStepManager.setPreviousClickListener(this);
        this.makerStepManager.setNextClickListener(this);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [iv/b]isSelectedMuscleAreaList : ToggleButton 과 1:1 매핑한 값을 초기화한다.(초기값은 false 이다.)
        this.isSelectedMuscleAreaList = new boolean[6];
        for (boolean isSelected : isSelectedMuscleAreaList) {
            isSelected = false;
        }

        // [iv/C]MaterialButtonToggleGroup : toggle button checked listener
        muscleAreaToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                switch (checkedId) {
                    case R.id.f_maker_step2_chest:

                        // [iv/b]isSelectedMuscleAreaList : chest 의 체크 여부 저장
                        isSelectedMuscleAreaList[0] = isChecked;

                        break;

                    case R.id.f_maker_step2_shoulder:

                        // [iv/b]isSelectedMuscleAreaList : shoulder 의 체크 여부 저장
                        isSelectedMuscleAreaList[1] = isChecked;

                        break;

                    case R.id.f_maker_step2_lat:

                        // [iv/b]isSelectedMuscleAreaList : lat 의 체크 여부 저장
                        isSelectedMuscleAreaList[2] = isChecked;

                        break;

                    case R.id.f_maker_step2_upper_body:

                        // [iv/b]isSelectedMuscleAreaList : upper body 의 체크 여부 저장
                        isSelectedMuscleAreaList[3] = isChecked;

                        break;

                    case R.id.f_maker_step2_arm:

                        // [iv/b]isSelectedMuscleAreaList : arm 의 체크 여부 저장
                        isSelectedMuscleAreaList[4] = isChecked;

                        break;

                    case R.id.f_maker_step2_etc:

                        // [iv/b]isSelectedMuscleAreaList : etc 의 체크 여부 저장
                        isSelectedMuscleAreaList[5] = isChecked;

                        break;
                }
            }
        });

    }

    @Override
    public AlertDialog setClickListenerOfPrevious() {
        return null;
    }

    @Override
    public void setClickListenerOfNext() {

        final String METHOD_NAME = "[setClickListenerOfNext] ";

        // [check 1] : 6가지의 MuscleArea 중 하나라도 선택한 것이 있을 때만 다음 단계 진행
        if (0 < this.muscleAreaToggleGroup.getCheckedButtonIds().size() ) {

            // [iv/C]ContentLoadingProgressBar : VISIBLE
            this.progressBar.setVisibility(View.VISIBLE);

            // [method] : firebase database 에서 선택한 MuscleArea 의 목록을 가져와서 그룹화하고 step 1 에서 선택한 타입의 Fragment 로 이동하는 과정 진행
            loadContent();

        } else {

            // "근육 부위를 하나라도 선택해주세요." Snack Bar 메시지 출력
            Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step2_snack_next, Snackbar.LENGTH_SHORT).show();

        } // [check 1]

    }


    /**
     * [method] Firebase Database 에서 event/$uid/$MuscleArea 에 해당하는 내용을 muscleArea 으로 가져오기
     */
    private void loadContent() {
        final String METHOD_NAME = "[loadContent] ";

        // [lv/C]DatabaseReference : Firebase Database 의 event 항목을 참조하기위한 객체 생성
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

        // [lv/C]Query : uid 로 query 만들기
        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "snapshot 내용 = " + snapshot);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "::::::::::::::::::::::::::::::::::::: chest ::::::::::::::::::::::::::::::::::::: ");
                // [method] : [0] CHEST 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                GroupingEventData chestGroupingEventData = loadContentByMuscleArea(snapshot, MuscleArea.CHEST, isSelectedMuscleAreaList[0]);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "::::::::::::::::::::::::::::::::::::: shoulder ::::::::::::::::::::::::::::::::::::: ");
                // [method] : [1] SHOULDER 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                GroupingEventData shoulderGroupingEventData = loadContentByMuscleArea(snapshot, MuscleArea.SHOULDER, isSelectedMuscleAreaList[1]);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "::::::::::::::::::::::::::::::::::::: lat ::::::::::::::::::::::::::::::::::::: ");
                // [method] : [2] LAT 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                GroupingEventData latGroupingEventData = loadContentByMuscleArea(snapshot, MuscleArea.LAT, isSelectedMuscleAreaList[2]);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "::::::::::::::::::::::::::::::::::::: upper body ::::::::::::::::::::::::::::::::::::: ");
                // [method] : [3] UPPER_BODY(or LEG) 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                GroupingEventData upperBodyGroupingEventData = loadContentByMuscleArea(snapshot, MuscleArea.UPPER_BODY, isSelectedMuscleAreaList[3]);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "::::::::::::::::::::::::::::::::::::: arm ::::::::::::::::::::::::::::::::::::: ");
                // [method] : [4] ARM 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                GroupingEventData armGroupingEventData = loadContentByMuscleArea(snapshot, MuscleArea.ARM, isSelectedMuscleAreaList[4]);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "::::::::::::::::::::::::::::::::::::: etc ::::::::::::::::::::::::::::::::::::: ");
                // [method] : [5] ETC 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                GroupingEventData etcGroupingEventData = loadContentByMuscleArea(snapshot, MuscleArea.ETC, isSelectedMuscleAreaList[5]);

                // [method] : chest, shoulder, lat, upperBody, arm, etc groupingEventData 를 담아서 step 1-0 에서 선택한 type 에 맞게 Fragment 이동
                moveNextStep(chestGroupingEventData, shoulderGroupingEventData, latGroupingEventData, upperBodyGroupingEventData, armGroupingEventData, etcGroupingEventData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // [check 1] : error 가 발생하면 -> muscleArea 에 해당하는 Fragment 를 만들지만, eventArrayList 는 null 인 fragment 객체를 생성한다.
                if (error != null) {

                    // "데이터를 가져오는데 오류가 발생하였습니다." snack bar 메시지 표시
                    Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step2_snack_load_content_db_error, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


    private GroupingEventData loadContentByMuscleArea(DataSnapshot snapshot, MuscleArea muscleArea, boolean isSelectedMuscleArea) {
        final String METHOD_NAME = "[loadContentByMuscleArea] ";

        // [lv/C]GroupingEventData :
        GroupingEventData groupingEventData = null;

        // [check 1] : 선택한 MuscleArea 이다.
        if (isSelectedMuscleArea) {

            // [lv/C]ArrayList<Event> : 각 목록을 담을
            ArrayList<Event> eventArrayList = new ArrayList<>();

            for (DataSnapshot search : snapshot.child(muscleArea.toString()).getChildren()) {

                // [lv/C]Event : 각 목록을 가져옴
                Event data = search.getValue(Event.class);
                data.setKey(search.getKey());

                // [lv/C]ArrayList<Event> : 위의 각 목록을 추가하기
                eventArrayList.add(data);

            }

            // [lv/C]GroupingEventData : 모든 목록이 담긴 eventArrayList 를 그룹화하기
            groupingEventData = GroupingEventUtil.classifyEventArrayListToGroupType(eventArrayList);

        } // [check 1]

        return groupingEventData;
    }// End of method [loadContentByChest]


    private void moveNextStep(GroupingEventData chest, GroupingEventData shoulder, GroupingEventData lat, GroupingEventData upperBody, GroupingEventData arm, GroupingEventData etc) {
        final String METHOD_NAME = "[moveNextStep] ";

        // [lv/C]FragmentTransaction : fragmentManager 를 통해 객체 가져오기
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // [check 1] : step1D0Type 이 뭐냐?
        switch (this.step1SelectedType) {
            case MakerStepManager.MAKER_TYPE_DIRECT_SELECTION:
                // direct
                // [lv/C]Step3D1Fragment : step 3-1 fragment 객체 생성
                MakerStep3D1Fragment step3_1Fragment = MakerStep3D1Fragment.newInstance(chest, shoulder, lat, upperBody, arm, etc);

                // [lv/C]FragmentTransaction : step 3-1 fragment 화면 전환
                transaction.replace(R.id.nav_home_content_wrapper, step3_1Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case MakerStepManager.MAKER_TYPE_EACH_GROUP_RANDOM:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> step1 에서 each random 을 선택하였습니다.");
                // each random
                // [lv/C]Step3D2Fragment : step 3-2 fragment 객체 생성
                MakerStep3D2Fragment step3_2Fragment = MakerStep3D2Fragment.newInstance(chest, shoulder, lat, upperBody, arm, etc);

                // [lv/C]FragmentTransaction : step 3-2 fragment 화면 전환
                transaction.replace(R.id.nav_home_content_wrapper, step3_2Fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;

            case MakerStepManager.MAKER_TYPE_ALL_GROUP_RANDOM:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> step1 에서 all random 을 선택하였습니다.");
                // all random
                // [lv/C]Step3D2Fragment : step 3-3 fragment 객체 생성
                MakerStep3D3Fragment step3_3Fragment = MakerStep3D3Fragment.newInstance(chest, shoulder, lat, upperBody, arm, etc);

                // [lv/C]FragmentTransaction : step 3-3 fragment 화면 전환
                transaction.replace(R.id.nav_home_content_wrapper, step3_3Fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;

        } // [check 1]

    } // End of method [moveNextStep]

}
