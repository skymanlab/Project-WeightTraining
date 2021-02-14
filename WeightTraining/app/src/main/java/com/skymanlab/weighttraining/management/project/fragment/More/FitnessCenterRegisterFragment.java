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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.SectionManager.FitnessCenterRegisterSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitnessCenterRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitnessCenterRegisterFragment extends Fragment {

    // constant
    private static final String FITNESS_CENTER_NAME = "fitnessCenterName";
    private static final String FITNESS_CENTER_FIRST_ADDRESS = "fitnessCenterFirstAddress";
    private static final String FITNESS_CENTER_SECOND_ADDRESS = "fitnessCenterSecondAddress";
    private static final String FITNESS_CENTER_THIRD_ADDRESS = "fitnessCenterThirdAddress";
    private static final String FITNESS_CENTER_LATITUDE = "fitnessCenterLatitude";
    private static final String FITNESS_CENTER_LONGITUDE = "fitnessCenterLongitude";

    // instance variable
    private String fitnessCenterName;
    private String fitnessCenterFirstAddress;
    private String fitnessCenterSecondAddress;
    private String fitnessCenterThirdAddress;
    private double fitnessCenterLatitude;
    private double fitnessCenterLongitude;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private FitnessCenterRegisterSectionManager sectionManager;

    public FitnessCenterRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FitnessCenterRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitnessCenterRegisterFragment newInstance(String name,
                                                            String firstAddress,
                                                            String secondAddress,
                                                            String thirdAddress,
                                                            double latitude,
                                                            double longitude) {
        FitnessCenterRegisterFragment fragment = new FitnessCenterRegisterFragment();
        Bundle args = new Bundle();
        args.putString(FITNESS_CENTER_NAME, name);
        args.putString(FITNESS_CENTER_FIRST_ADDRESS, firstAddress);
        args.putString(FITNESS_CENTER_SECOND_ADDRESS, secondAddress);
        args.putString(FITNESS_CENTER_THIRD_ADDRESS, thirdAddress);
        args.putDouble(FITNESS_CENTER_LATITUDE, latitude);
        args.putDouble(FITNESS_CENTER_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.fitnessCenterName = getArguments().getString(FITNESS_CENTER_NAME);
            this.fitnessCenterFirstAddress = getArguments().getString(FITNESS_CENTER_FIRST_ADDRESS);
            this.fitnessCenterSecondAddress = getArguments().getString(FITNESS_CENTER_SECOND_ADDRESS);
            this.fitnessCenterThirdAddress = getArguments().getString(FITNESS_CENTER_THIRD_ADDRESS);
            this.fitnessCenterLatitude = getArguments().getDouble(FITNESS_CENTER_LATITUDE);
            this.fitnessCenterLongitude = getArguments().getDouble(FITNESS_CENTER_LONGITUDE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_center_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // top bar
        topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_fitness_center_register_title));
        topBarManager.connectWidget();
        topBarManager.initWidget();

        // section
        sectionManager = new FitnessCenterRegisterSectionManager(this, view);
        sectionManager.setFitnessCenterName(fitnessCenterName);
        sectionManager.setFitnessCenterFirstAddress(fitnessCenterFirstAddress);
        sectionManager.setFitnessCenterSecondAddress(fitnessCenterSecondAddress);
        sectionManager.setFitnessCenterThirdAddress(fitnessCenterThirdAddress);
        sectionManager.setFitnessCenterLatitude(fitnessCenterLatitude);
        sectionManager.setFitnessCenterLongitude(fitnessCenterLongitude);
        sectionManager.connectWidget();
        sectionManager.initWidget();

        // top bar : start button
        topBarManager.setStartButtonListener(
                new FragmentTopBarManager.StartButtonListener() {
                    @Override
                    public AlertDialog setStartButtonClickListener() {
                        getActivity().getSupportFragmentManager().popBackStack();
                        return null;
                    }
                }
        );
        topBarManager.initWidgetOfStartButton(null);

        // top bar : end button
        topBarManager.setEndButtonListener(
                this.sectionManager.newInstanceOfEndButtonListener()
        );
        topBarManager.initWidgetOfEndButton(
                getString(R.string.f_fitness_center_register_top_bar_end_button)
        );
    }
}