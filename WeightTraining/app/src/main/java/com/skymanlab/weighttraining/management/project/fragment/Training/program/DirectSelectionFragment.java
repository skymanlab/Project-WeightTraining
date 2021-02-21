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
import com.skymanlab.weighttraining.management.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.DirectSelectionSectionManager;

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
    private MuscleArea muscleArea;
    private GroupingEventData groupingEventData;

    // instance variable
    private DirectSelectionSectionManager sectionManager;

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

        DirectSelectionFragment fragment = new DirectSelectionFragment();

        Bundle args = new Bundle();
        args.putSerializable(MUSCLE_AREA, muscleArea);
        args.putSerializable(GROUPING_EVENT_DATA, groupingEventData);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.muscleArea = (MuscleArea) getArguments().get(MUSCLE_AREA);
            this.groupingEventData = (GroupingEventData) getArguments().getSerializable(GROUPING_EVENT_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direct_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [DirectSelectionSectionManager] [sectionManager] this is 'direct selection' fragment's section manager.
        this.sectionManager = new DirectSelectionSectionManager(this, view);
        this.sectionManager.setGroupingEventData(this.groupingEventData);
        this.sectionManager.setMuscleAreaStandardId(this.muscleArea);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

    }

}
