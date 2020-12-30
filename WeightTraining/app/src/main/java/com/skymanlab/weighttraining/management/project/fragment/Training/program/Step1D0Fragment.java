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
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.Step1D0SectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step1D0Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step1D0Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step1D0Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private Step1D0SectionManager sectionManager;

    public Step1D0Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Step1D0Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step1D0Fragment newInstance(String param1, String param2) {
        Step1D0Fragment fragment = new Step1D0Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step1_0, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager :
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_program_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step1D0SectionManager :
        this.sectionManager = new Step1D0SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager());
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();

    }
}