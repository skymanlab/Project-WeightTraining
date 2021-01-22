package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep3D1SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep3D1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep3D1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep3D1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String CHEST_GROUPING_EVENT_DATA = "chestGroupingEventData";
    private static final String SHOULDER_GROUPING_EVENT_DATA = "shoulderGroupingEventData";
    private static final String LAT_GROUPING_EVENT_DATA = "latGroupingEventData";
    private static final String UPPER_BODY_GROUPING_EVENT_DATA = "upperBodyGroupingEventData";
    private static final String ARM_GROUPING_EVENT_DATA = "armGroupingEventData";
    private static final String ETC_GROUPING_EVENT_DATA = "etcGroupingEventData";

    // instance variable
    private GroupingEventData chestGroupingEventData;
    private GroupingEventData shoulderGroupingEventData;
    private GroupingEventData latGroupingEventData;
    private GroupingEventData upperBodyGroupingEventData;
    private GroupingEventData armGroupingEventData;
    private GroupingEventData etcGroupingEventData;

    // instance variable : step 3-1
    private ArrayList<DirectSelectionFragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<MuscleArea> fragmentMuscleAreaList = new ArrayList<>();

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStepManager makerStepManager;
    private MakerStep3D1SectionManager sectionManager;

    // constructor
    public MakerStep3D1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Step3D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep3D1Fragment newInstance(GroupingEventData chest, GroupingEventData shoulder, GroupingEventData lat, GroupingEventData upperBody, GroupingEventData arm, GroupingEventData etc) {

        MakerStep3D1Fragment fragment = new MakerStep3D1Fragment();

        Bundle args = new Bundle();
        args.putSerializable(CHEST_GROUPING_EVENT_DATA, chest);
        args.putSerializable(SHOULDER_GROUPING_EVENT_DATA, shoulder);
        args.putSerializable(LAT_GROUPING_EVENT_DATA, lat);
        args.putSerializable(UPPER_BODY_GROUPING_EVENT_DATA, upperBody);
        args.putSerializable(ARM_GROUPING_EVENT_DATA, arm);
        args.putSerializable(ETC_GROUPING_EVENT_DATA, etc);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String METHOD_NAME = "[onCreate] ";

        if (getArguments() != null) {

            this.chestGroupingEventData = (GroupingEventData) getArguments().getSerializable(CHEST_GROUPING_EVENT_DATA);
            this.shoulderGroupingEventData = (GroupingEventData) getArguments().getSerializable(SHOULDER_GROUPING_EVENT_DATA);
            this.latGroupingEventData = (GroupingEventData) getArguments().getSerializable(LAT_GROUPING_EVENT_DATA);
            this.upperBodyGroupingEventData = (GroupingEventData) getArguments().getSerializable(UPPER_BODY_GROUPING_EVENT_DATA);
            this.armGroupingEventData = (GroupingEventData) getArguments().getSerializable(ARM_GROUPING_EVENT_DATA);
            this.etcGroupingEventData = (GroupingEventData) getArguments().getSerializable(ETC_GROUPING_EVENT_DATA);

            /*
            fragmentArrayList 를 중복으로 만들게 되면 back 버튼을 눌러 다시 돌아왔을 시, sectionManager 가 참조 안되는 error 발생!
            그러므로 생명 주기에 맞춰 처음에만 생성되게 onCreate 에서 fragmentArrayList 를 생성하도록 한다.
             */
            // [method] : chest GroupingEventData 의 데이터 유무를 확인하여, fragment 를 생성하고 추가하는 과정 진행
            makeFragment(chestGroupingEventData, MuscleArea.CHEST);

            // [method] : shoulder GroupingEventData 의 데이터 유무를 확인하여, fragment 를 생성하고 추가하는 과정 진행
            makeFragment(shoulderGroupingEventData, MuscleArea.SHOULDER);

            // [method] : lat GroupingEventData 의 데이터 유무를 확인하여, fragment 를 생성하고 추가하는 과정 진행
            makeFragment(latGroupingEventData, MuscleArea.LAT);

            // [method] : upperBody GroupingEventData 의 데이터 유무를 확인하여, fragment 를 생성하고 추가하는 과정 진행
            makeFragment(upperBodyGroupingEventData, MuscleArea.UPPER_BODY);

            // [method] : arm GroupingEventData 의 데이터 유무를 확인하여, fragment 를 생성하고 추가하는 과정 진행
            makeFragment(armGroupingEventData, MuscleArea.ARM);

            // [method] : etc GroupingEventData 의 데이터 유무를 확인하여, fragment 를 생성하고 추가하는 과정 진행
            makeFragment(etcGroupingEventData, MuscleArea.ETC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maker_step3_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        // [FragmentTopBarManager] [topBarManager] this is 'maker step 3-1' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 3-1 단계 설정
        this.makerStepManager = new MakerStepManager(this, view, MakerStepManager.STEP_THREE);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep3D1SectionManager] [sectionManager] this is 'maker step 3-1' fragment's section manager.
        this.sectionManager = new MakerStep3D1SectionManager(this, view);
        this.sectionManager.setFragmentArrayList(this.fragmentArrayList);
        this.sectionManager.setFragmentMuscleAreaList(this.fragmentMuscleAreaList);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 와 EndButtonListener 설정
        this.topBarManager.setStartButtonListener(new FragmentTopBarManager.StartButtonListener() {
            @Override
            public AlertDialog setStartButtonClickListener() {

                // [method] fragment manager 를 통해 back stack 에서 pop!
                getActivity().getSupportFragmentManager().popBackStack();

                return null;
            }
        });
        this.topBarManager.initWidgetOfStartButton(null);
        this.topBarManager.setEndButtonListener(this.sectionManager.newEndButtonListenerInstance());
        this.topBarManager.initWidgetOfEndButton(getString(R.string.f_maker_step_end_button_next));

    }


    /**
     * 해당 muscleArea 의 groupingEventData 를 보여주기 위한 Fragment 객체 생성하여 fragmentArrayList 추가한다. 그리고 해당 fragment 의 muscleArea 를 차례대로 fragmentMuscleAreaList 에 추가한다.
     *
     * @param groupingEventData
     * @param muscleArea
     */
    private void makeFragment(GroupingEventData groupingEventData, MuscleArea muscleArea) {
        final String METHOD_NAME = "[makeFragment] ";

        // [check 1] : groupingEventData 에 데이터가 있다.
        if (groupingEventData != null) {

            // [lv/C]DirectSelectionFragment : muscleArea 와 groupingEventData 로 Fragment 생성
            DirectSelectionFragment muscleAreaFragment = DirectSelectionFragment.newInstance(muscleArea, groupingEventData);

            // [iv/C]ArrayList<DirectSelectionFragment> : 위에서 생성한 fragment 를 ArrayList 에 추가한다.
            fragmentArrayList.add(muscleAreaFragment);

            // [iv/C]ArrayList<String> : 위와 1:1 매핑되는 MuscleArea 를 추가한다.
            fragmentMuscleAreaList.add(muscleArea);

        } // [check 1]

    } // End of method [makeFragment]

}