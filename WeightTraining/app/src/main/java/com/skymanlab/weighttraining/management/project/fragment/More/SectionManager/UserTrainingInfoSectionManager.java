package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.dialog.ThreeMajorMeasurementsDialog;
import com.skymanlab.weighttraining.management.project.fragment.More.dialog.TrainingLevelDialog;
import com.skymanlab.weighttraining.management.user.data.User;
import com.skymanlab.weighttraining.management.user.data.UserTraining;


public class UserTrainingInfoSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] MyTrainingInfoSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private UserTraining myTraining;

    // instance variable
    private LinearLayout trainingLevelWrapper;
    private TextView trainingLevel;

    // instance variable
    private LinearLayout threeMajorMeasurementsWrapper;
    private TextView squat;
    private TextView deadLift;
    private TextView benchPress;

    // constructor
    public UserTrainingInfoSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setMyTraining(UserTraining myTraining) {
        this.myTraining = myTraining;
    }

    @Override
    public void connectWidget() {


        // [ LinearLayout | trainingLevelWrapper ]
        this.trainingLevelWrapper = (LinearLayout) getView().findViewById(R.id.f_userTrainingInfo_trainingLevel_wrapper);

        // [ TextView | trainingLevel ]
        this.trainingLevel = (TextView) getView().findViewById(R.id.f_userTrainingInfo_trainingLevel);


        // [ LinearLayout | threeMajorMeasurementsWrapper ]
        this.threeMajorMeasurementsWrapper = (LinearLayout) getView().findViewById(R.id.f_userTrainingInfo_threeMajorMeasurements_wrapper);

        // [ TextView | squat ]
        this.squat = (TextView) getView().findViewById(R.id.f_userTrainingInfo_threeMajorMeasurements_squat);

        // [ TextView | deadLift ]
        this.deadLift = (TextView) getView().findViewById(R.id.f_userTrainingInfo_threeMajorMeasurements_deadlift);

        // [ TextView | benchPress ]
        this.benchPress = (TextView) getView().findViewById(R.id.f_userTrainingInfo_threeMajorMeasurements_bench_press);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // wrapper click listener
        trainingLevelWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TrainingLevelDialog dialog = new TrainingLevelDialog();

                        dialog.setStyle(
                                DialogFragment.STYLE_NO_TITLE,
                                android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
                        );

                        dialog.show(
                                getFragment().getActivity().getSupportFragmentManager(),
                                TrainingLevelDialog.class.getSimpleName()
                        );
                    }
                }
        );

        // wrapper click listener
        threeMajorMeasurementsWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ThreeMajorMeasurementsDialog dialog = new ThreeMajorMeasurementsDialog();

                        dialog.setStyle(
                                DialogFragment.STYLE_NO_TITLE,
                                android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
                        );
                        dialog.show(
                                getFragment().getActivity().getSupportFragmentManager(),
                                ThreeMajorMeasurementsDialog.class.getSimpleName()
                        );
                    }
                }
        );

        if (myTraining != null) {

            // 트레이닝 레벨
            trainingLevel.setText(myTraining.getLevel());

            // 스쿼트 측정 무게
            squat.setText(myTraining.getSquat() + " kg");

            // 데드리프트 측정 무게
            deadLift.setText(myTraining.getDeadlift() + " kg");

            // 벤치 프레스 측정 무게
            benchPress.setText(myTraining.getBenchPress() + " kg");

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의 트레이닝 정보 > 아직 저장된 값이 없어요.");
        }


        // TrainingLevelDialog 에서 보낸 데이터 확인
        getFragment().getActivity().getSupportFragmentManager().setFragmentResultListener(
                UserTraining.LEVEL,
                getFragment(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                        trainingLevel.setText(result.getString(UserTraining.LEVEL));
                    }
                }
        );


        // ThreeMajorMeasurementsDialog 에서 보낸 데이터 확인
        getFragment().getActivity().getSupportFragmentManager().setFragmentResultListener(
                UserTraining.THREE_MAJOR_MEASUREMENTS,
                getFragment(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                        squat.setText(result.getInt(UserTraining.SQUAT) + " kg");

                        deadLift.setText(result.getInt(UserTraining.DEADLIFT) + " kg");

                        benchPress.setText(result.getInt(UserTraining.BENCH_PRESS) + " kg");

                    }
                }
        );
    }

}
