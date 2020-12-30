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
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.Step2D1SectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step2D1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step2D1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step2D1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String STEP_1_0_TYPE ="step1D0Type";

    // instance variable
    private int step1D0Type;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private Step2D1SectionManager sectionManager;

    // constructor
    public Step2D1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step1D0Type Step1D0Fragment 에서 선택된 type(direct, random 중 하나)
     * @return A new instance of fragment Step2D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step2D1Fragment newInstance(int step1D0Type) {

        // step 2-1 fragment
        Step2D1Fragment fragment = new Step2D1Fragment();

        // step 2-1 fragment bundle setting
        Bundle args = new Bundle();
        args.putInt(STEP_1_0_TYPE, step1D0Type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.step1D0Type = getArguments().getInt(STEP_1_0_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step2_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager :
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_program_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step2D1SectionManager :
        this.sectionManager = new Step2D1SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager(), this.step1D0Type);
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();
    }
}