package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.GoogleMapManager;
import com.skymanlab.weighttraining.management.project.ApiManager.LocationUpdateManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SearchUtil;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterRegisterFragment;

public class FitnessCenterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FrameLayout goRegister;
    private TextView fitnessCenterName;
    private TextView fitnessCenterAddress;

    // constructor
    public FitnessCenterSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }


    @Override
    public void connectWidget() {

        // [FrameLayout] [goRegister] widget connect
        this.goRegister = (FrameLayout) getView().findViewById(R.id.f_fitness_center_go_register);

        // [TextView] [fitnessCenterName] widget connect
        this.fitnessCenterName = (TextView) getView().findViewById(R.id.f_fitness_center_name);

        // [TextView] [fitnessCenterAddress] widget connect
        this.fitnessCenterAddress = (TextView) getView().findViewById(R.id.f_fitness_center_address);

    }

    @Override
    public void initWidget() {

        // goRegister click listener : FitnessCenterRegisterFragment 로 이동
        this.goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, new FitnessCenterRegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }




}
