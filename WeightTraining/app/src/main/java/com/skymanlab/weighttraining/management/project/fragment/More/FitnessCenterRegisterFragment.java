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
    private static final String FITNESS_CENTER_ADDRESS = "fitnessCenterAddress";

    // instance variable
    private String fitnessCenterName;
    private String fitnessCenterAddress;

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
    public static FitnessCenterRegisterFragment newInstance(String fitnessCenterName,
                                                            String fitnessCenterAddress) {
        FitnessCenterRegisterFragment fragment = new FitnessCenterRegisterFragment();
        Bundle args = new Bundle();
        args.putString(FITNESS_CENTER_NAME, fitnessCenterName);
        args.putString(FITNESS_CENTER_ADDRESS, fitnessCenterAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.fitnessCenterName = getArguments().getString(FITNESS_CENTER_NAME);
            this.fitnessCenterAddress = getArguments().getString(FITNESS_CENTER_ADDRESS);
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
        sectionManager.setFitnessCenterAddress(fitnessCenterAddress);
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