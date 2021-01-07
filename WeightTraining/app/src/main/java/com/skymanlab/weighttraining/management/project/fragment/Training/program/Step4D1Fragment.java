package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.util.EventResultSet;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.Step4D1SectionManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step4D1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step4D1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step4D1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String CHEST_EVENT_RESULT_SET = "chestEventResultSet";
    private static final String SHOULDER_EVENT_RESULT_SET = "shoulderEventResultSet";
    private static final String LAT_EVENT_RESULT_SET = "latEventResultSet";
    private static final String UPPER_BODY_EVENT_RESULT_SET = "upperBodyEventResultSet";
    private static final String ARM_EVENT_RESULT_SET = "armEventResultSet";
    private static final String ETC_EVENT_RESULT_SET = "etcEventResultSet";

    // instance variable
    private EventResultSet chestEventResultSet;
    private EventResultSet shoulderEventResultSet;
    private EventResultSet latEventResultSet;
    private EventResultSet upperBodyEventResultSet;
    private EventResultSet armEventResultSet;
    private EventResultSet etcEventResultSet;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private Step4D1SectionManager sectionManager;

    // constructor
    public Step4D1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Step4D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step4D1Fragment newInstance(EventResultSet chest,
                                              EventResultSet shoulder,
                                              EventResultSet lat,
                                              EventResultSet upperBody,
                                              EventResultSet arm,
                                              EventResultSet etc) {

        Step4D1Fragment fragment = new Step4D1Fragment();

        Bundle args = new Bundle();
        args.putSerializable(CHEST_EVENT_RESULT_SET, chest);             // [0] chest
        args.putSerializable(SHOULDER_EVENT_RESULT_SET, shoulder);       // [1] shoulder
        args.putSerializable(LAT_EVENT_RESULT_SET, lat);                 // [2] lat
        args.putSerializable(UPPER_BODY_EVENT_RESULT_SET, upperBody);    // [3] upper body
        args.putSerializable(ARM_EVENT_RESULT_SET, arm);                 // [4] arm
        args.putSerializable(ETC_EVENT_RESULT_SET, etc);                 // [5] etc
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            this.chestEventResultSet = (EventResultSet) getArguments().getSerializable(CHEST_EVENT_RESULT_SET);          // [0] chest
            this.shoulderEventResultSet = (EventResultSet) getArguments().getSerializable(SHOULDER_EVENT_RESULT_SET);    // [1] shoulder
            this.latEventResultSet = (EventResultSet) getArguments().getSerializable(LAT_EVENT_RESULT_SET);              // [2] lat
            this.upperBodyEventResultSet = (EventResultSet) getArguments().getSerializable(UPPER_BODY_EVENT_RESULT_SET); // [3] upper body
            this.armEventResultSet = (EventResultSet) getArguments().getSerializable(ARM_EVENT_RESULT_SET);              // [4] arm
            this.etcEventResultSet = (EventResultSet) getArguments().getSerializable(ETC_EVENT_RESULT_SET);              // [5] etc
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step4_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        // [iv/C]FragmentTopBarManager : step 4-1 fragment top bar
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_program_menu_program_maker));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step4D1SectionManager : step 4-1 fragment section
        this.sectionManager = new Step4D1SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager());
        this.sectionManager.setSelectedChestEventArrayList(this.chestEventResultSet.getSelectedEventArrayList());
        this.sectionManager.setSelectedShoulderEventArrayList(this.shoulderEventResultSet.getSelectedEventArrayList());
        this.sectionManager.setSelectedLatEventArrayList(this.latEventResultSet.getSelectedEventArrayList());
        this.sectionManager.setSelectedUpperBodyEventArrayList(this.upperBodyEventResultSet.getSelectedEventArrayList());
        this.sectionManager.setSelectedArmEventArrayList(this.armEventResultSet.getSelectedEventArrayList());
        this.sectionManager.setSelectedEtcEventArrayList(this.etcEventResultSet.getSelectedEventArrayList());
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();

    }


}