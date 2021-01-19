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
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep2SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep2Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep2Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String STEP_1_SELECTED_TYPE ="step1D0Type";

    // instance variable
    private int step1SelectedType;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStepManager2 makerStepManager;
    private MakerStep2SectionManager sectionManager;

    // constructor
    public MakerStep2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step1SelectedType MakerStep1Fragment 에서 선택된 type(direct, random 중 하나)
     * @return A new instance of fragment Step2D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep2Fragment newInstance(int step1SelectedType) {

        // step 2 fragment
        MakerStep2Fragment fragment = new MakerStep2Fragment();

        // step 2 fragment bundle setting
        Bundle args = new Bundle();
        args.putInt(STEP_1_SELECTED_TYPE, step1SelectedType);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.step1SelectedType = getArguments().getInt(STEP_1_SELECTED_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maker_step2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [FragmentTopBarManager] [topBarManager] maker step2 fragment 의 top bar manager 설정
        this.topBarManager = new FragmentTopBarManager(getActivity(), view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 2 단계 설정
        this.makerStepManager = new MakerStepManager2(getView(), getParentFragmentManager(), MakerStepManager.STEP_TWO);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep2SectionManager] [sectionManager] maker step 2 fragment section manager 설정
        this.sectionManager = new MakerStep2SectionManager(getActivity(),view, getActivity().getSupportFragmentManager(), this.step1SelectedType);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 와 EndButtonListener 설정
        this.topBarManager.setStartButtonListener(new FragmentTopBarManager.StartButtonListener() {
            @Override
            public void setStartButtonClickListener() {
                getParentFragmentManager().popBackStack();
            }
        });
        this.topBarManager.initWidgetOfStartButton(null);
        this.topBarManager.setEndButtonListener(this.sectionManager.newEndButtonListenerInstance());
        this.topBarManager.initWidgetOfEndButton(getString(R.string.f_maker_step_end_button_next));

    }
}