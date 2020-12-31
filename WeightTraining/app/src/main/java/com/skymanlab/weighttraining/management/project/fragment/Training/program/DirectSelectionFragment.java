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
    private static final String MUSCLE_AREA = "muscleArea";
    private static final String EVENT_ARRAY_LIST = "eventArrayList";
    // constant
    private static final String CLASS_NAME = "[PFTP] DirectSelectionFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private MuscleArea muscleArea = null;
    private ArrayList<Event> eventArrayList = null;

    // instance variable
    private DirectSelectionSectionManager sectionManager = null;

    // constructor
    public DirectSelectionFragment() {
        // Required empty public constructor
    }

    // setter
    public void setEventArrayList(ArrayList<Event> eventArrayList) {
        this.eventArrayList = eventArrayList;
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
    public static DirectSelectionFragment newInstance(MuscleArea muscleArea, ArrayList<Event> eventArrayList) {

        DirectSelectionFragment fragment = new DirectSelectionFragment();

        Bundle args = new Bundle();
        args.putSerializable(MUSCLE_AREA, muscleArea);
        args.putSerializable(EVENT_ARRAY_LIST, eventArrayList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String METHOD_NAME = "[onCreate] ";
        if (getArguments() != null) {
            this.muscleArea = (MuscleArea) getArguments().get(MUSCLE_AREA);
            this.eventArrayList = (ArrayList<Event>) getArguments().get(EVENT_ARRAY_LIST);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++ muscleArea 는 ? = " + muscleArea.toString());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++ eventArrayList 의 size 는 ? = " + eventArrayList.size());

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
        final String METHOD_NAME = "[onViewCreated] ";

        // [iv/C]DirectSelectionSectionManager :
        this.sectionManager = new DirectSelectionSectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager());
        this.sectionManager.mappingWidget();
        this.sectionManager.setGroupingEventData(this.eventArrayList);
        this.sectionManager.setMuscleAreaStandardId(this.muscleArea);
        this.sectionManager.initWidget();
    }
}