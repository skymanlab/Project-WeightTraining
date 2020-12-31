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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step3D2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3D2Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step3D2Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String SELECTED_MUSCLE_AREA_LIST = "selectedMuscleAreaList";

    // instance variable
    private boolean[] isSelectedMuscleAreaList;

    // constructor
    public Step3D2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isSelectedMuscleAreaList STEP 2-1 에서 선택한 muscle area list
     * @return A new instance of fragment Step3D2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step3D2Fragment newInstance(boolean[] isSelectedMuscleAreaList) {

        Step3D2Fragment fragment = new Step3D2Fragment();

        Bundle args = new Bundle();
        args.putBooleanArray(SELECTED_MUSCLE_AREA_LIST, isSelectedMuscleAreaList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.isSelectedMuscleAreaList = getArguments().getBooleanArray(SELECTED_MUSCLE_AREA_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step3_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}