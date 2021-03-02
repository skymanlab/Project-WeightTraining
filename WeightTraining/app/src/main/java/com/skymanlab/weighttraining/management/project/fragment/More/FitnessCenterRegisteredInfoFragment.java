package com.skymanlab.weighttraining.management.project.fragment.More;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.SectionManager.FitnessCenterRegisteredInfoSectionManager;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.User;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitnessCenterRegisteredInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitnessCenterRegisteredInfoFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFM] FitnessCenterRegisterInfoFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String MY_FITNESS_CENTER = "myFitnessCenter";
    private static final String MY_ATTENDANCE_DATE_LIST = "myAttendanceDateList";

    // instance variable
    private UserFitnessCenter myFitnessCenter;
    private ArrayList<Attendance> myAttendanceDateList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private FitnessCenterRegisteredInfoSectionManager sectionManager;

    // constructor
    public FitnessCenterRegisteredInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FitnessCenterRegisterInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitnessCenterRegisteredInfoFragment newInstance(UserFitnessCenter myFitnessCenter,
                                                                  ArrayList<Attendance> myAttendanceDateList) {

        FitnessCenterRegisteredInfoFragment fragment = new FitnessCenterRegisteredInfoFragment();

        Bundle args = new Bundle();
        args.putSerializable(MY_FITNESS_CENTER, myFitnessCenter);
        args.putSerializable(MY_ATTENDANCE_DATE_LIST, myAttendanceDateList);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.myFitnessCenter = (UserFitnessCenter) getArguments().getSerializable(MY_FITNESS_CENTER);
            this.myAttendanceDateList = (ArrayList<Attendance>) getArguments().getSerializable(MY_ATTENDANCE_DATE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_center_registered_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // top bar
        topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_fitnessCenterRegisteredInfo_title));
        topBarManager.connectWidget();
        topBarManager.initWidget();

        // section
        sectionManager = new FitnessCenterRegisteredInfoSectionManager(this, view);
        sectionManager.setMyFitnessCenter(myFitnessCenter);
        sectionManager.setMyAttendanceDateList(myAttendanceDateList);
        sectionManager.connectWidget();
        sectionManager.initWidget();

        // top bar : start button
        topBarManager.initWidgetOfStartButton(
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