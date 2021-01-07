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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MyProgramSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProgramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProgramFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MyProgramFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    // instance variable
    private FragmentTopBarManager topBarManager;
    private MyProgramSectionManager sectionManager;

    // constructor
    public MyProgramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Step2D2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProgramFragment newInstance() {

        MyProgramFragment fragment = new MyProgramFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_program, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String METHOD_NAME = "[onViewCreated] ";

        // [iv/C]FragmentTopBarManager :
        this.topBarManager = new FragmentTopBarManager(getActivity(), view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step2D2SectionManager :
        this.sectionManager = new MyProgramSectionManager(view, getActivity().getSupportFragmentManager());
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();
    }
}