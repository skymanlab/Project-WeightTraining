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
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep8SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep8Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep8Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep8Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String PROGRAM = "program";

    // instance variable
    private Program program;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStepManager makerStepManager;
    private MakerStep8SectionManager sectionManager;


    // constructor
    public MakerStep8Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MakerStep7Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep8Fragment newInstance(Program program) {
        MakerStep8Fragment fragment = new MakerStep8Fragment();
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
        return inflater.inflate(R.layout.fragment_maker_step8, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        display();

        // [FragmentTopBarManager] [topBarManager] this is 'maker step 8' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 8 단계 설정
        this.makerStepManager = new MakerStepManager(this, view, MakerStepManager.STEP_EIGHT);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep5SectionManager] [sectionManager] maker step 8 fragment section manager 설정
        this.sectionManager = new MakerStep8SectionManager(this, view);
        this.sectionManager.setProgram(this.program);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 와 EndButtonListener 설정
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
        this.topBarManager.initWidgetOfEndButton(
                getString(R.string.f_maker_step_end_button_complete),
                this.sectionManager.newEndButtonListenerInstance()
        );

    }


    private void display() {
        final String METHOD_NAME = "[display] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> getKey = " + program.getKey());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> getNickName = " + program.getNickName());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> getTotalEventNumber = " + program.getTotalEventNumber());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> getTotalSetNumber = " + program.getTotalSetNumber());

        for (int index = 0; index < program.getMuscleAreaList().size(); index++) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<muscleArea>>> = " + program.getMuscleAreaList().get(index));
        }

        for (int index = 0; index < program.getDetailProgramList().size(); index++) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<DetailProgramList>>> getOrder = " + program.getDetailProgramList().get(index).getOrder());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<DetailProgramList>>> getMuscleArea = " + program.getDetailProgramList().get(index).getMuscleArea());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<DetailProgramList>>> getEventKey = " + program.getDetailProgramList().get(index).getEventKey());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<DetailProgramList>>> getEventName = " + program.getDetailProgramList().get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<DetailProgramList>>> getSetNumber = " + program.getDetailProgramList().get(index).getSetNumber());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<DetailProgramList>>> getRestTimeMinute = " + program.getDetailProgramList().get(index).getRestTimeMinute());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Program> << " + index + " >> <<<DetailProgramList>>> getRestTimeSecond = " + program.getDetailProgramList().get(index).getRestTimeSecond());
        }

    }
}