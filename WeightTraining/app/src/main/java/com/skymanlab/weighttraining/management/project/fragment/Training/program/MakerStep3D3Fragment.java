package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep3D3SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep3D3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep3D3Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep3D3Fragment";
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

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStepManager makerStepManager;
    private MakerStep3D3SectionManager sectionManager;

    // constructor
    public MakerStep3D3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Step3D3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep3D3Fragment newInstance(GroupingEventData chest, GroupingEventData shoulder, GroupingEventData lat, GroupingEventData upperBody, GroupingEventData arm, GroupingEventData etc) {

        MakerStep3D3Fragment fragment = new MakerStep3D3Fragment();

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

        }

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> savedInstanceState = " + savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreateView] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> savedInstanceState = " + savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maker_step3_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> savedInstanceState = " + savedInstanceState);
        if (savedInstanceState != null)
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> savedInstanceState back = " + savedInstanceState.getInt("back"));

        // [FragmentTopBarManager] [topBarManager] this is 'maker step 3-3' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 3-3 단계 설정
        this.makerStepManager = new MakerStepManager(this, view, MakerStepManager.STEP_THREE);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep3D3SectionManager] [sectionManager] this is 'maker step 3-3' fragment's section manager.
        this.sectionManager = new MakerStep3D3SectionManager(this, view);
        this.sectionManager.setChestGroupingEventData(this.chestGroupingEventData);
        this.sectionManager.setShoulderGroupingEventData(this.shoulderGroupingEventData);
        this.sectionManager.setLatGroupingEventData(this.latGroupingEventData);
        this.sectionManager.setUpperBodyGroupingEventData(this.upperBodyGroupingEventData);
        this.sectionManager.setArmGroupingEventData(this.armGroupingEventData);
        this.sectionManager.setEtcGroupingEventData(this.etcGroupingEventData);
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

}