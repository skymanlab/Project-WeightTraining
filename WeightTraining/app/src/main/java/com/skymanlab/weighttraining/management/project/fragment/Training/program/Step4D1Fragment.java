package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.Step4D1SectionManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step4D1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step4D1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step4D1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String SELECTED_EVENT_ARRAY_LIST = "selectedEventArrayList";

    // instance variable
    private ArrayList<Event> selectedEventArrayList;


    // instance variable
    private FragmentTopBarManager topBarManager;
    private Step4D1SectionManager sectionManager;

    // constructor
    public Step4D1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Step4D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step4D1Fragment newInstance(ArrayList<Event> eventArrayList) {

        Step4D1Fragment fragment = new Step4D1Fragment();

        Bundle args = new Bundle();
        args.putSerializable(SELECTED_EVENT_ARRAY_LIST, eventArrayList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedEventArrayList = (ArrayList<Event>) getArguments().getSerializable(SELECTED_EVENT_ARRAY_LIST);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step4_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        // [iv/C]FragmentTopBarManager : step 4-1 fragment top bar
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_program_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step4D1SectionManager : step 4-1 fragment section
        this.sectionManager = new Step4D1SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager());
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------------------------------------------------ selected event array list 확인 ");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.selectedEventArrayList);

    }
}