package com.skymanlab.weighttraining.management.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;
import com.skymanlab.weighttraining.management.user.data.User;

public class MASectionManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PA]_MASectionManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private MuscleAreaNextActivityType nextActivityType;

    // instance variable
    private TextView chest;
    private TextView shoulder;
    private TextView lat;
    private TextView leg;
    private TextView arm;

    // constructor
    public MASectionManager(Activity activity, FirebaseUser firebaseUser, MuscleAreaNextActivityType nextActivityType) {
        super(activity, firebaseUser);
        this.nextActivityType = nextActivityType;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]TextView : chest mapping
        this.chest = (TextView) getActivity().findViewById(R.id.muscle_area_chest);

        // [iv/C]TextView : shoulder mapping
        this.shoulder = (TextView) getActivity().findViewById(R.id.muscle_area_shoulder);

        // [iv/C]TextView : lat mapping
        this.lat = (TextView) getActivity().findViewById(R.id.muscle_area_lat);

        // [iv/C]TextView : leg mapping
        this.leg = (TextView) getActivity().findViewById(R.id.muscle_area_leg);

        // [iv/C]TextView : arm mapping
        this.arm = (TextView) getActivity().findViewById(R.id.muscle_area_arm);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionOne] ";

        // [iv/C]TextView : chest click listener
        this.chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : click listener 설정하기
                setClickListener(MuscleArea.CHEST);
            }
        });

        // [iv/C]TextView : shoulder click listener
        this.shoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : click listener 설정하기
                setClickListener(MuscleArea.SHOULDER);

            }
        });

        // [iv/C]TextView : lat click listener
        this.lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : click listener 설정하기
                setClickListener(MuscleArea.LAT);

            }
        });

        // [iv/C]TextView : leg click listener
        this.leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : click listener 설정하기
                setClickListener(MuscleArea.LEG);

            }
        });

        // [iv/C]TextView : arm click listener
        this.arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : click listener 설정하기
                setClickListener(MuscleArea.ARM);
            }
        });

    }


    /**
     * [method] click listener 설정
     */
    private void setClickListener(MuscleArea muscleArea) {

        final String METHOD_NAME = "[setClickListener] ";

        // [method] : muscleAreaNextActivityType 로 이동할 Activity 를 알아낸 다음, 해당 muscleArea 를 intent 에 추가하여 이동한다.
        moveActivity(DataManager.getNextActivity(this.nextActivityType), muscleArea);

    } // End of method [setClickListener]


    /**
     * [method] Intent 에 "muscleArea" 로 데이터를 담아 EventAddActivity 로 이동한다.
     */
    private void moveActivity(Class nextActivity, MuscleArea muscleArea) {

        // [lv/C]Intent : 해당 activity 로 이동하기 위한 객체 생성
        Intent intent = new Intent(getActivity(), nextActivity);

        // [lv/C]SessionManager : intent 에 muscleArea 추가하기
        SessionManager.setMuscleAreaInIntent(intent, muscleArea);

        // [method] : 설정이 끝난 intent 를 다음 Activity 로 전송하며 이동
        getActivity().startActivity(intent);

    } // End of method [moveActivity]

}
