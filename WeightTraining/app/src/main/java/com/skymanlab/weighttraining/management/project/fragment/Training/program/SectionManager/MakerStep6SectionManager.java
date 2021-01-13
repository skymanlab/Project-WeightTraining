package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.util.ArrayList;

public class MakerStep6SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, MakerStepManager.OnPreviousClickListener, MakerStepManager.OnNextClickListener {


    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep5SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private MakerStepManager makerStepManager;

    // instance variable
    private ArrayList<Event> finalOrderEventArrayList;

    // constructor
    public MakerStep6SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    // setter
    public void setFinalOrderEventArrayList(ArrayList<Event> finalOrderEventArrayList) {
        this.finalOrderEventArrayList = finalOrderEventArrayList;
    }

    @Override
    public void connectWidget() {

    }

    @Override
    public void initWidget() {

        // [iv/C]MakerStepManager : step 6
        this.makerStepManager = new MakerStepManager(getView(), getFragmentManager(), MakerStepManager.STEP_SIX);
        this.makerStepManager.setPreviousClickListener(this);
        this.makerStepManager.setNextClickListener(this);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();
    }

    @Override
    public void setClickListenerOfNext() {

    }

    @Override
    public AlertDialog setClickListenerOfPrevious() {
        return null;
    }
}
