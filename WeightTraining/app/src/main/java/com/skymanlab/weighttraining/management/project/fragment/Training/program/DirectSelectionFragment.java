package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.DirectSelectionSectionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DirectSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DirectSelectionFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] DirectSelectionFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String MUSCLE_AREA = "muscleArea";
    private static final String GROUPING_EVENT_DATA = "groupingEventData";

    // instance variable
    private MuscleArea muscleArea = null;
    private GroupingEventData groupingEventData = null;

    // instance variable
    private DirectSelectionSectionManager sectionManager = null;

    // constructor
    public DirectSelectionFragment() {
        // Required empty public constructor
    }

    // setter
    public void setGroupingEventData(GroupingEventData groupingEventData) {
        this.groupingEventData = groupingEventData;
    }

    // getter
    public DirectSelectionSectionManager getSectionManager() {
        return sectionManager;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DirectSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DirectSelectionFragment newInstance(MuscleArea muscleArea, GroupingEventData groupingEventData) {

        final String METHOD_NAME = "[newInstance] ";

        DirectSelectionFragment fragment = new DirectSelectionFragment();

        Bundle args = new Bundle();
        args.putSerializable(MUSCLE_AREA, muscleArea);
        args.putSerializable(GROUPING_EVENT_DATA, groupingEventData);
        fragment.setArguments(args);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>----------->>>>> DirectSelectionFragment 1. newInstance ");

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String METHOD_NAME = "[onCreate] ";
        if (getArguments() != null) {
            this.muscleArea = (MuscleArea) getArguments().get(MUSCLE_AREA);
            this.groupingEventData = (GroupingEventData) getArguments().getSerializable(GROUPING_EVENT_DATA);
        }

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>----------->>>>> DirectSelectionFragment 2. onCreate ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String METHOD_NAME= "[onCreateView] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>----------->>>>> DirectSelectionFragment 3. onCreateView ");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direct_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>----------->>>>> DirectSelectionFragment 4. onViewCreated ");

        // [iv/C]DirectSelectionSectionManager :
        this.sectionManager = new DirectSelectionSectionManager(getActivity(), view, getActivity().getSupportFragmentManager());
        this.sectionManager.mappingWidget();
        this.sectionManager.setGroupingEventData(this.groupingEventData);
        this.sectionManager.setMuscleAreaStandardId(this.muscleArea);
        this.sectionManager.initWidget();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>----------->>>>> DirectSelectionFragment 5. onViewCreated  ---- 완료");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>----------->>>>> DirectSelectionFragment 5. onViewCreated  ----  sectionManager 는 ? " + sectionManager);


    }

}
