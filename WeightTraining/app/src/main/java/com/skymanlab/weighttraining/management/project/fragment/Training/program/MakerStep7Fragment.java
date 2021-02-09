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
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep6SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep7SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep7Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep7Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep7Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String FINAL_ORDER_LIST = "finalOrderList";
    private static final String MUSCLE_AREA_ARRAY_LIST = "muscleAreaArrayList";
    private static final String PROGRAM_SETTING_SET_NUMBER = "programSettingSetNumber";
    private static final String PROGRAM_SETTING_REST_TIME_MINUTE = "programSettingRestTimeMinute";
    private static final String PROGRAM_SETTING_REST_TIME_SECOND = "programSettingRestTimeSecond";

    // instance variable
    private ArrayList<Event> finalOrderList;
    private ArrayList<MuscleArea> muscleAreaArrayList;
    private int programSettingSetNumber;
    private int programSettingRestTimeMinute;
    private int programSettingRestTimeSecond;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStepManager makerStepManager;
    private MakerStep7SectionManager sectionManager;

    // constructor
    public MakerStep7Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MakerStep7Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep7Fragment newInstance(ArrayList<Event> finalOrderList,
                                                 ArrayList<MuscleArea> muscleAreaArrayList,
                                                 int programSettingSetNumber,
                                                 int programSettingRestTimeMinute,
                                                 int programSettingRestTimeSecond) {

        MakerStep7Fragment fragment = new MakerStep7Fragment();

        Bundle args = new Bundle();
        args.putSerializable(FINAL_ORDER_LIST, finalOrderList);
        args.putSerializable(MUSCLE_AREA_ARRAY_LIST, muscleAreaArrayList);
        args.putInt(PROGRAM_SETTING_SET_NUMBER, programSettingSetNumber);
        args.putInt(PROGRAM_SETTING_REST_TIME_MINUTE, programSettingRestTimeMinute);
        args.putInt(PROGRAM_SETTING_REST_TIME_SECOND, programSettingRestTimeSecond);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.finalOrderList = (ArrayList<Event>) getArguments().getSerializable(FINAL_ORDER_LIST);
            this.muscleAreaArrayList = (ArrayList<MuscleArea>) getArguments().getSerializable(MUSCLE_AREA_ARRAY_LIST);
            this.programSettingSetNumber = getArguments().getInt(PROGRAM_SETTING_SET_NUMBER);
            this.programSettingRestTimeMinute = getArguments().getInt(PROGRAM_SETTING_REST_TIME_MINUTE);
            this.programSettingRestTimeSecond = getArguments().getInt(PROGRAM_SETTING_REST_TIME_SECOND);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maker_step7, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[newEndButtonListenerInstance] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< program setting > setNumber = " + programSettingSetNumber);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< program setting > restTimeMinute = " + programSettingRestTimeMinute);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< program setting > restTimeSecond = " + programSettingRestTimeSecond);


        // [FragmentTopBarManager] [topBarManager] this is 'maker step 7' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 7 단계 설정
        this.makerStepManager = new MakerStepManager(this, view, MakerStepManager.STEP_SEVEN);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep7SectionManager] [sectionManager] this is 'maker step 7' fragment's section manager.
        this.sectionManager = new MakerStep7SectionManager(this, view);
        this.sectionManager.setFinalOrderList(this.finalOrderList);
        this.sectionManager.setMuscleAreaArrayList(this.muscleAreaArrayList);
        this.sectionManager.setProgramSettingSetNumber(this.programSettingSetNumber);
        this.sectionManager.setProgramSettingRestTimeMinute(this.programSettingRestTimeMinute);
        this.sectionManager.setProgramSettingRestTimeSecond(this.programSettingRestTimeSecond);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 와 EndButtonListener 설정
        this.topBarManager.setStartButtonListener(new FragmentTopBarManager.StartButtonListener() {
            @Override
            public AlertDialog setStartButtonClickListener() {

                // [method] fragment manager 를 통해 back stack 에서 pop!
                getActivity().getSupportFragmentManager().popBackStack();

                return null;
            }

        });
        this.topBarManager.initWidgetOfStartButton(null);
        this.topBarManager.setEndButtonListener(this.sectionManager.newEndButtonListenerInstance());
        this.topBarManager.initWidgetOfEndButton(getString(R.string.f_maker_step_end_button_next));

    }
}