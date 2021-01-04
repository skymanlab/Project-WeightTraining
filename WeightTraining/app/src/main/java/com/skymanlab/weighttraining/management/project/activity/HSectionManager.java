package com.skymanlab.weighttraining.management.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.AdMobActivity;
import com.skymanlab.weighttraining.MuscleAreaActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;

import org.w3c.dom.Text;

public class HSectionManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PA] HSectionManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private TextView eventAdd;
    private TextView eventList;
    private TextView eventProgram;
    private MaterialCardView container;
    private ImageView adMopClicker;

    // constructor
    public HSectionManager(Activity activity, FirebaseUser firebaseUser) {
        super(activity, firebaseUser);
    }

    // getter
    public MaterialCardView getContainer() {
        return container;
    }

    public ImageView getAdMopClicker() {
        return adMopClicker;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]TextView : eventAdd mapping
        this.eventAdd = (TextView) getActivity().findViewById(R.id.home_event_add);

        // [iv/C]TextView : eventList mapping
        this.eventList = (TextView) getActivity().findViewById(R.id.home_event_list);

        // [iv/C]TextView : eventProgram mapping
        this.eventProgram = (TextView) getActivity().findViewById(R.id.home_event_program);

        // [iv/C]MaterialCardView : container mapping
        this.container = (MaterialCardView) getActivity().findViewById(R.id.home_container);

        // [iv/C]ImageView : adMobClicker mapping
        this.adMopClicker = (ImageView) getActivity().findViewById(R.id.home_ad_mob_clicker);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionOne] ";

        // [ic/C]TextView : eventAdd click listener
        this.eventAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. type = " + MuscleAreaNextActivityType.EVENT_ADD);
                // [method] : EventActivity 로 이동
                moveMuscleAreaActivity(MuscleAreaNextActivityType.EVENT_ADD);

            }
        });

        // [ic/C]TextView : eventList click listener
        this.eventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. type = " + MuscleAreaNextActivityType.EVENT_LIST);
                // [method] : EventListActivity 로 이동
                moveMuscleAreaActivity(MuscleAreaNextActivityType.EVENT_LIST);

            }
        });

        // [ic/C]TextView : eventProgram click listener
        this.eventProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. type = " + MuscleAreaNextActivityType.EVENT_PROGRAM);
                // [method] :  로 이동
                moveMuscleAreaActivity(MuscleAreaNextActivityType.EVENT_PROGRAM);

            }
        });

        // [iv/C]ImageView : adMobClicker click listener
        this.adMopClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // [lv/C]Intent : AdMobActivity 로 이동
                Intent intent = new Intent(getActivity(), AdMobActivity.class);
                getActivity().startActivity(intent);

            }
        });

    }


    /**
     * [method] 해당 Activity 를 muscleAreaNextActivityType 을 포함하여 이동한다.
     */
    private void moveMuscleAreaActivity(MuscleAreaNextActivityType muscleAreaNextActivityType) {

        // [lv/C]Intent : MuscleAreaActivity 로 이동하기 위한 객체 생성
        Intent intent = new Intent(getActivity(), MuscleAreaActivity.class);

        // [lv/C]SessionManager : intent 에 muscleAreaNextActivityType 추가하기
        SessionManager.setMuscleAreaNextActivityTypeInIntent(intent, muscleAreaNextActivityType);

        // [lv/C]Intent : 해당 화면으로 이동
        getActivity().startActivity(intent);

    } // End of method [moveMuscleAreaActivity]

}
