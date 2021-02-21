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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.ProgramSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramFragment extends Fragment {

    // instance variable
    private FragmentTopBarManager topBarManager;
    private ProgramSectionManager sectionManager;

    // constructor
    public ProgramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProgramFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgramFragment newInstance() {
        ProgramFragment fragment = new ProgramFragment();
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
        return inflater.inflate(R.layout.fragment_program, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // top bar
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_title));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // section
        this.sectionManager = new ProgramSectionManager(this, view);
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