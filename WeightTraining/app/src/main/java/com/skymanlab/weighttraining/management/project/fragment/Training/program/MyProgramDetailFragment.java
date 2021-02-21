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
import com.skymanlab.weighttraining.management.program.data.Program;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MyProgramDetailSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProgramDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProgramDetailFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MyProgramDetailFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // constant
    private static final String PROGRAM = "program";

    // instance variable
    private Program program;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MyProgramDetailSectionManager sectionManager;


    // constructor
    public MyProgramDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyProgramDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProgramDetailFragment newInstance(Program program) {

        MyProgramDetailFragment fragment = new MyProgramDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(PROGRAM, program);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.program = (Program) getArguments().getSerializable(PROGRAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_program_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< program > key = " + program.getKey());

        // top bar
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_my_program_detail_title));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // section
        this.sectionManager = new MyProgramDetailSectionManager(this, view);
        this.sectionManager.setProgram(program);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // top bar : start button
        this.topBarManager.initWidgetOfStartButton(
                null,
                new FragmentTopBarManager.StartButtonListener() {
                    @Override
                    public AlertDialog setStartButtonClickListener() {

                        getActivity().getSupportFragmentManager().popBackStack();
                        return null;
                    }
                }
        );

    }

}