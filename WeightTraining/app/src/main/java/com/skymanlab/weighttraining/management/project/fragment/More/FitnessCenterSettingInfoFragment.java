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
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.SectionManager.FitnessCenterSettingInfoSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitnessCenterSettingInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitnessCenterSettingInfoFragment extends Fragment {

    // constant
    private static final String MY_FITNESS_CENTER = "myFitnessCenter";

    // instance variable
    private UserFitnessCenter myFitnessCenter;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private FitnessCenterSettingInfoSectionManager sectionManager;

    public FitnessCenterSettingInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FitnessCenterSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitnessCenterSettingInfoFragment newInstance(UserFitnessCenter myFitnessCenter) {

        FitnessCenterSettingInfoFragment fragment = new FitnessCenterSettingInfoFragment();

        Bundle args = new Bundle();
        args.putSerializable(MY_FITNESS_CENTER, myFitnessCenter);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.myFitnessCenter = (UserFitnessCenter) getArguments().getSerializable(MY_FITNESS_CENTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_center_setting_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // top bar
        topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_fitnessCenterSettingInfo_title));
        topBarManager.connectWidget();
        topBarManager.initWidget();

        // section
        sectionManager = new FitnessCenterSettingInfoSectionManager(this, view);
        sectionManager.setMyFitnessCenter(myFitnessCenter);
        sectionManager.connectWidget();
        sectionManager.initWidget();

        // top bar : Start button
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