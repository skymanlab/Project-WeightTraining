package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.SettingsActivity;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.ApiManager.FitnessCenterGeofencingManager;
import com.skymanlab.weighttraining.management.project.ApiManager.InitializationManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SettingsManager;
import com.skymanlab.weighttraining.management.project.data.BaseEventDataManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterRegisterFragment;

public class MoreSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFM] MoreSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private LinearLayout registerDayCountWrapper;
    private TextView registerDayCount;
    private LinearLayout trainingCountWrapper;
    private TextView trainingCount;
    private MaterialCardView fitnessCenter;
    private MaterialCardView target;
    private MaterialCardView setting;
    private MaterialButton notice;
    private MaterialButton serviceCenter;


    // instance variable :
    FitnessCenterGeofencingManager geofenceManager;

    // constructor
    public MoreSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [iv/C]LinearLayout : connect
        this.registerDayCountWrapper = (LinearLayout) getView().findViewById(R.id.f_more_register_day_count_wrapper);

        // [iv/C]TextView : connect
        this.registerDayCount = (TextView) getView().findViewById(R.id.f_more_register_day_count);

        // [iv/C]LinearLayout : connect
        this.trainingCountWrapper = (LinearLayout) getView().findViewById(R.id.f_more_training_count_wrapper);

        // [iv/C]TextView : connect
        this.trainingCount = (TextView) getView().findViewById(R.id.f_more_training_count);

        // [iv/C]LinearLayout : fitnessCenter connect
        this.fitnessCenter = (MaterialCardView) getView().findViewById(R.id.f_more_fitness_center);

        // [iv/C]LinearLayout : target connect
        this.target = (MaterialCardView) getView().findViewById(R.id.f_more_target);

        // [iv/C]LinearLayout : setting connect
        this.setting = (MaterialCardView) getView().findViewById(R.id.f_more_setting);

        // [iv/C]MaterialButton : connect
        this.notice = (MaterialButton) getView().findViewById(R.id.f_more_notice);

        // [iv/C]MaterialButton : connect
        this.serviceCenter = (MaterialButton) getView().findViewById(R.id.f_more_service_center);
    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // register day count 관련 widget 등의 초기 내용을 설정한다.
        initWidgetOfRegisterDayCount();

        // training count 관련 widget 들의 초기 내용을 설정한다.
        initWidgetOfTrainingCount();

        // [iv/C]LinearLayout : fitnessCenter click listener
        this.fitnessCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // fitnessCenterFragment 로 이동
                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, FitnessCenterFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        });

        // [iv/C]LinearLayout : target click listener
        this.target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // [iv/C]LinearLayout : setting click listener
        this.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/C]Intent : SettingsActivity 로 이동하는
                Intent intent = new Intent(getFragment().getActivity(), SettingsActivity.class);
                getFragment().getActivity().startActivity(intent);

            }
        });

        // [iv/C]MaterialButton : notice click listener
        this.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // [iv/C]MaterialButton : serviceCenter click listener
        this.serviceCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingsManager.displayAllSettingsValue(getFragment().getContext());

            }
        });

    }


    /**
     * register day count 와 관련된 widget 들의 초기 내용을 설정한다.
     */
    private void initWidgetOfRegisterDayCount() {

        // [iv/C]LinearLayout : registerCountWrapper click listener
        this.registerDayCountWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // [iv/C]TextView : registerCount - 피트니스 센터 등록 후 몇 일이 지났는지 표시한다.


    } // End of method [initWidgetOfRegisterDayCount]


    /**
     * training count 와 관련된 widwget 들의 초기 내용을 설정한다.
     */
    private void initWidgetOfTrainingCount() {

        // [iv/C]LinearLayout : trainingCountWrapper click listener
        this.trainingCountWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // [iv/C]TextView : trainingCount - 피트니스 센터에 운동하러 간 일수를 표시한다.


    } // End of method [initWidgetOfTrainingCount]


}
