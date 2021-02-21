package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.dialog.ThreeMajorMeasurementsDialog;
import com.skymanlab.weighttraining.management.user.data.User;
import com.skymanlab.weighttraining.management.user.data.UserTraining;


public class MyTrainingInfoSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] MyTrainingInfoSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private UserTraining myTraining;

    // instance variable
    private LinearLayout threeMajorMeasurementsWrapper;
    private TextView squat;
    private TextView deadLift;
    private TextView benchPress;

    // constructor
    public MyTrainingInfoSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setMyTraining(UserTraining myTraining) {
        this.myTraining = myTraining;
    }

    @Override
    public void connectWidget() {

        // [ LinearLayout | threeMajorMeasurementsWrapper ]
        this.threeMajorMeasurementsWrapper = (LinearLayout) getView().findViewById(R.id.f_my_training_info_three_major_measurements_wrapper);

        // [ TextView | squat ]
        this.squat = (TextView) getView().findViewById(R.id.f_my_training_info_three_major_measurements_squat);

        // [ TextView | deadLift ]
        this.deadLift = (TextView) getView().findViewById(R.id.f_my_training_info_three_major_measurements_deadlift);

        // [ TextView | benchPress ]
        this.benchPress = (TextView) getView().findViewById(R.id.f_my_training_info_three_major_measurements_bench_press);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // wrapper click listener
        threeMajorMeasurementsWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .add(
                                        R.id.nav_home_content_wrapper,
                                        new ThreeMajorMeasurementsDialog()
                                )
                                .addToBackStack(null)
                                .commit();

                    }
                }
        );

        if (myTraining != null) {

            // 스쿼트 측정 무게
            squat.setText(myTraining.getSquat() + "");

            // 데드리프트 측정 무게
            deadLift.setText(myTraining.getDeadlift() + "");

            // 벤치 프레스 측정 무게
            benchPress.setText(myTraining.getBenchPress() + "");

            getFragment().getActivity().getSupportFragmentManager().setFragmentResultListener(
                    User.TRAINING,
                    getFragment(),
                    new FragmentResultListener() {
                        @Override
                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                            squat.setText(result.getInt(UserTraining.SQUAT) + "");

                            deadLift.setText(result.getInt(UserTraining.DEADLIFT) + "");

                            benchPress.setText(result.getInt(UserTraining.BENCH_PRESS) + "");

                        }
                    }
            );

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의 트레이닝 정보 > 아직 저장된 값이 없어요.");
        }

    }

}
