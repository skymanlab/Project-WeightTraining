package com.skyman.weighttrainingpro.management.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.skyman.weighttrainingpro.MuscleAreaActivity;
import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleAreaNextActivityType;
import com.skyman.weighttrainingpro.management.user.data.User;

public class HSectionManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PA]_HSectionManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private TextView eventAdd;
    private TextView eventList;
    private TextView eventProgram;

    // instance variable
    private MaterialCardView mainContainer;
    private ImageView adMobClick;

    // constructor
    public HSectionManager(Activity activity, User user) {
        super(activity, user);
    }

    // getter


    public MaterialCardView getMainContainer() {
        return mainContainer;
    }

    public ImageView getAdMobClick() {
        return adMobClick;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]TextView : eventAdd mapping
        this.eventAdd = (TextView) getActivity().findViewById(R.id.home_event_add);

        // [iv/C]TextView : eventList mapping
        this.eventList = (TextView) getActivity().findViewById(R.id.home_event_list);

        // [iv/C]TextView : eventProgram mapping
        this.eventProgram = (TextView) getActivity().findViewById(R.id.home_event_program);

        // [iv/C]MaterialCardView : mainContainer mapping
        this.mainContainer = (MaterialCardView) getActivity().findViewById(R.id.home_main_container);

        // [iv/C]ImageView : adMobClick mapping
        this.adMobClick = (ImageView) getActivity().findViewById(R.id.home_ad_mob_click);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionOne] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> main container 의 size ");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. width = " + this.mainContainer.getWidth());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. height = " + this.mainContainer.getHeight());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>> ");

        android.view.Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> window size width = " + size.x);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> window size height = " + size.y);


        // [ic/C]TextView :  click listener
        this.eventAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. type = " + MuscleAreaNextActivityType.EVENT_ADD);
                // [method] : EventActivity 로 이동
                moveMuscleAreaActivity(MuscleAreaNextActivityType.EVENT_ADD);

            }
        });

        // [ic/C]TextView :  click listener
        this.eventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. type = " + MuscleAreaNextActivityType.EVENT_LIST);
                // [method] : EventListActivity 로 이동
                moveMuscleAreaActivity(MuscleAreaNextActivityType.EVENT_LIST);

            }
        });

        // [ic/C]TextView :  click listener
        this.eventProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. type = " + MuscleAreaNextActivityType.EVENT_PROGRAM);
                // [method] :  로 이동
                moveMuscleAreaActivity(MuscleAreaNextActivityType.EVENT_PROGRAM);

            }
        });

    }


    /**
     * [method] 해당 Activity 를 muscleAreaNextActivityType 을 포함하여 이동한다.
     */
    private void moveMuscleAreaActivity(MuscleAreaNextActivityType muscleAreaNextActivityType) {

        // [lv/C]Intent : MuscleAreaActivity 로 이동하기 위한 객체 생성
        Intent intent = new Intent(getActivity(), MuscleAreaActivity.class);

        // [lv/C]SessionManager : intent 에 user 추가하기
        SessionManager.setUserInIntent(intent, getUser());

        // [lv/C]SessionManager : intent 에 muscleAreaNextActivityType 추가하기
        SessionManager.setMuscleAreaNextActivityTypeInIntent(intent, muscleAreaNextActivityType);

        // [lv/C]Intent : 해당 화면으로 이동
        getActivity().startActivity(intent);

    } // End of method [moveMuscleAreaActivity]

}
