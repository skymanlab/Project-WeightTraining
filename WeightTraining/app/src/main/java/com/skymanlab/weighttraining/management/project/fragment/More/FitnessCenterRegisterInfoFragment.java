package com.skymanlab.weighttraining.management.project.fragment.More;

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
import com.skymanlab.weighttraining.management.project.fragment.More.SectionManager.FitnessCenterRegisterInfoSectionManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitnessCenterRegisterInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitnessCenterRegisterInfoFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFM] FitnessCenterRegisterInfoFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String ATTENDANCE_DATE_LIST = "attendanceDateList";
    private static final String CONTRACT_DATE = "contractDate";
    private static final String EXPIRY_DATE = "expiryDate";

    // instance variable
    private String contractDate;
    private String expiryDate;
    private ArrayList<String> attendanceDateList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private FitnessCenterRegisterInfoSectionManager sectionManager;

    // constructor
    public FitnessCenterRegisterInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FitnessCenterRegisterInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitnessCenterRegisterInfoFragment newInstance(String contractDate,
                                                                String expiryDate,
                                                                ArrayList<String> attendanceDateList) {

        FitnessCenterRegisterInfoFragment fragment = new FitnessCenterRegisterInfoFragment();

        Bundle args = new Bundle();
        args.putString(CONTRACT_DATE, contractDate);
        args.putString(EXPIRY_DATE, expiryDate);
        args.putStringArrayList(ATTENDANCE_DATE_LIST, attendanceDateList);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.contractDate = getArguments().getString(CONTRACT_DATE);
            this.expiryDate = getArguments().getString(EXPIRY_DATE);
            this.attendanceDateList = getArguments().getStringArrayList(ATTENDANCE_DATE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_center_register_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // top bar
        topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_fitness_center_register_info_title));
        topBarManager.connectWidget();
        topBarManager.initWidget();

        // section manager
        sectionManager = new FitnessCenterRegisterInfoSectionManager(this, view);
        sectionManager.setContractDate(contractDate);
        sectionManager.setExpiryDate(expiryDate);
        sectionManager.setAttendanceDateList(attendanceDateList);
        sectionManager.connectWidget();
        sectionManager.initWidget();

        // top bar : start button
        topBarManager.setStartButtonListener(new FragmentTopBarManager.StartButtonListener() {
            @Override
            public AlertDialog setStartButtonClickListener() {

                getActivity().getSupportFragmentManager().popBackStack();

                return null;
            }
        });
        topBarManager.initWidgetOfStartButton(null);
    }
}