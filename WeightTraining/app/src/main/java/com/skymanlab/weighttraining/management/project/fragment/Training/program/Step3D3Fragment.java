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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.Step3D3SectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step3D3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3D3Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step3D3Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String SELECTED_MUSCLE_AREA_LIST = "selectedMuscleAreaList";

    // instance variable
    private boolean[] isSelectedMuscleAreaList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private Step3D3SectionManager sectionManager;

    // constructor
    public Step3D3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isSelectedMuscleAreaList STEP 2-1 에서 선택한 muscle area list
     * @return A new instance of fragment Step3D3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step3D3Fragment newInstance(boolean[] isSelectedMuscleAreaList) {

        Step3D3Fragment fragment = new Step3D3Fragment();

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
        return inflater.inflate(R.layout.fragment_step3_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager :
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_program_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step3D3SectionManager :
        this.sectionManager = new Step3D3SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager(), this.isSelectedMuscleAreaList);
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();


    }
}