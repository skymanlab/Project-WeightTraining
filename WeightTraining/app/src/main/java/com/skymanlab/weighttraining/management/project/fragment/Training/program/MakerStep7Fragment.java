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
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep7SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    private static final String PROGRAM = "program";
    private static final String DETAIL_PROGRAM_LIST = "detailProgramList";

    // instance variable
    private ArrayList<Event> finalOrderList;
    private Program program;
    private HashMap<String, DetailProgram> detailProgramList;

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
    public static MakerStep7Fragment newInstance(ArrayList<Event> finalOrderList, Program program, HashMap<String, DetailProgram> detailProgramList) {
        MakerStep7Fragment fragment = new MakerStep7Fragment();
        Bundle args = new Bundle();
        args.putSerializable(FINAL_ORDER_LIST, finalOrderList);
        args.putSerializable(PROGRAM, program);
        args.putSerializable(DETAIL_PROGRAM_LIST, detailProgramList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.finalOrderList = (ArrayList<Event>) getArguments().getSerializable(FINAL_ORDER_LIST);
            this.program = (Program) getArguments().getSerializable(PROGRAM);
            this.detailProgramList = (HashMap<String, DetailProgram>) getArguments().getSerializable(DETAIL_PROGRAM_LIST);
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

        final String METHOD_NAME = "[onViewCreated] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============= program set number  = " + program.getSetNumber());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============= program rest time minute  = " + program.getRestTimeMinute());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============= program rest time second = " + program.getRestTimeSecond());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============= detail program array list = " + detailProgramList.size());

        if (0 < detailProgramList.size()) {
            Iterator iterator = detailProgramList.keySet().iterator();

            while (iterator.hasNext()) {

                String key = (String) iterator.next();

                for (int index = 0; index < finalOrderList.size(); index++) {

                    if (finalOrderList.get(index).getKey().equals(key)) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "detail program list 이 있는 even 의 name = " + finalOrderList.get(index).getEventName());
                    }

                } // [cycle ]
            }
        }

        // [FragmentTopBarManager] [topBarManager] this is 'maker step 7' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 7 단계 설정
        this.makerStepManager = new MakerStepManager(this, view, MakerStepManager.STEP_SEVEN);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep5SectionManager] [sectionManager] maker step 7 fragment section manager 설정
        this.sectionManager = new MakerStep7SectionManager(this, view);
        this.sectionManager.setFinalOrderList(this.finalOrderList);
        this.sectionManager.setProgram(this.program);
        this.sectionManager.setDetailProgramList(this.detailProgramList);
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
        this.topBarManager.initWidgetOfEndButton(getString(R.string.f_maker_step_end_button_complete));

    }
}