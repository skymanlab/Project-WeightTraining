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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep2SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;

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
    private static final String MAKER_TYPE = "makerType";

    // instance variable
    private int makerType;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStepManager makerStepManager;
    private MakerStep2SectionManager sectionManager;

    // constructor
    public MakerStep2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param makerType MakerStep1Fragment 에서 선택된 type(direct, random 중 하나)
     * @return A new instance of fragment Step2D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep2Fragment newInstance(int makerType) {

        // step 2 fragment
        MakerStep2Fragment fragment = new MakerStep2Fragment();

        // step 2 fragment bundle setting
        Bundle args = new Bundle();
        args.putInt(MAKER_TYPE, makerType);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.makerType = getArguments().getInt(MAKER_TYPE);
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

        display();

        // [FragmentTopBarManager] [topBarManager] this is 'maker step 2' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 2 단계 설정
        this.makerStepManager = new MakerStepManager(this, view, MakerStepManager.STEP_TWO);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep2SectionManager] [sectionManager] this is 'maker step 2' fragment's section manager.
        this.sectionManager = new MakerStep2SectionManager(this, view, this.makerType);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 와 EndButtonListener 설정
        this.topBarManager.initWidgetOfStartButton(
                null,
                new FragmentTopBarManager.StartButtonListener() {
                    @Override
                    public AlertDialog setStartButtonClickListener() {

                        // [method] fragment manager 를 통해 back stack 에서 pop!
                        getActivity().getSupportFragmentManager().popBackStack();

                        return null;
                    }
                }
        );
        this.topBarManager.initWidgetOfEndButton(
                getString(R.string.f_maker_step_end_button_next),
                this.sectionManager.newEndButtonListenerInstance()
        );

    }


    private void display() {
        final String METHOD_NAME = "[display] ";

    }
}