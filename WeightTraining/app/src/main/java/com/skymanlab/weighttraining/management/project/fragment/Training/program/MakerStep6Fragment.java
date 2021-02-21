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
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep6Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep6Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep6Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String FINAL_ORDER_LIST = "finalOrderList";
    private static final String MUSCLE_AREA_ARRAY_LIST = "muscleAreaArrayList";

    // instance variable
    private ArrayList<Event> finalOrderList;
    private ArrayList<MuscleArea> muscleAreaArrayList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStepManager makerStepManager;
    private MakerStep6SectionManager sectionManager;

    // constructor
    public MakerStep6Fragment() {
        // Required empty public constructor
    }

    // getter
    public MakerStep6SectionManager getSectionManager() {
        return sectionManager;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MakerStep5Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep6Fragment newInstance(ArrayList<Event> finalOrderList, ArrayList<MuscleArea> muscleAreaArrayList) {

        MakerStep6Fragment fragment = new MakerStep6Fragment();

        Bundle args = new Bundle();
        args.putSerializable(FINAL_ORDER_LIST, finalOrderList);
        args.putSerializable(MUSCLE_AREA_ARRAY_LIST, muscleAreaArrayList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.finalOrderList = (ArrayList<Event>) getArguments().getSerializable(FINAL_ORDER_LIST);
            this.muscleAreaArrayList = (ArrayList<MuscleArea>) getArguments().getSerializable(MUSCLE_AREA_ARRAY_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maker_step6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        display();

        // [FragmentTopBarManager] [topBarManager] this is 'maker step 6' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 6 단계 설정
        this.makerStepManager = new MakerStepManager(this, view, MakerStepManager.STEP_SIX);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep6SectionManager] [sectionManager] this is 'maker step 6' fragment's section manager.
        this.sectionManager = new MakerStep6SectionManager(this, view);
        this.sectionManager.setFinalOrderList(this.finalOrderList);
        this.sectionManager.setMuscleAreaArrayList(this.muscleAreaArrayList);
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

        for (int index = 0; index < this.muscleAreaArrayList.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Muscle Area List > << " + index + " >> 근육 부위는 ? = " + this.muscleAreaArrayList.get(index));
        }
    }

}