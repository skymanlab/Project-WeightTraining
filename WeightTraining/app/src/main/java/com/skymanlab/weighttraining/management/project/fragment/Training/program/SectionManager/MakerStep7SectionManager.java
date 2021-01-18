package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MakerStep7SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, MakerStepManager.OnNextClickListener, MakerStepManager.OnPreviousClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep7SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private MakerStepManager makerStepManager;

    // instance variable
    private ArrayList<Event> finalOrderList;
    private Program program;
    private HashMap<String, DetailProgram> detailProgramList;

    // constructor
    public MakerStep7SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    // setter
    public void setFinalOrderList(ArrayList<Event> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setDetailProgramList(HashMap<String, DetailProgram> detailProgramList) {
        this.detailProgramList = detailProgramList;
    }

    @Override
    public void connectWidget() {

    }

    @Override
    public void initWidget() {

        // [iv/C]MakerStepManager : step 7
        this.makerStepManager = new MakerStepManager(getView(), getFragmentManager(), MakerStepManager.STEP_SEVEN);
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
