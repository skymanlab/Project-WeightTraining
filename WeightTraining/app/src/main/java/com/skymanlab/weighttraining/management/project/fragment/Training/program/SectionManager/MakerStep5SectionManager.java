package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

public class MakerStep5SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, MakerStepManager.OnPreviousClickListener, MakerStepManager.OnNextClickListener {


    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep5SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private MakerStepManager makerStepManager;

    // instance variable
    private EventResultSet chestEventResultSet;
    private EventResultSet shoulderEventResultSet;
    private EventResultSet latEventResultSet;
    private EventResultSet upperBodyEventResultSet;
    private EventResultSet armEventResultSet;
    private EventResultSet etcEventResultSet;

    // constructor
    public MakerStep5SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    // setter
    public void setChestEventResultSet(EventResultSet chestEventResultSet) {
        this.chestEventResultSet = chestEventResultSet;
    }

    public void setShoulderEventResultSet(EventResultSet shoulderEventResultSet) {
        this.shoulderEventResultSet = shoulderEventResultSet;
    }

    public void setLatEventResultSet(EventResultSet latEventResultSet) {
        this.latEventResultSet = latEventResultSet;
    }

    public void setUpperBodyEventResultSet(EventResultSet upperBodyEventResultSet) {
        this.upperBodyEventResultSet = upperBodyEventResultSet;
    }

    public void setArmEventResultSet(EventResultSet armEventResultSet) {
        this.armEventResultSet = armEventResultSet;
    }

    public void setEtcEventResultSet(EventResultSet etcEventResultSet) {
        this.etcEventResultSet = etcEventResultSet;
    }

    @Override
    public void connectWidget() {

    }

    @Override
    public void initWidget() {

        // [iv/C]MakerStepManager : step 5
        this.makerStepManager = new MakerStepManager(getView(), getFragmentManager(), MakerStepManager.STEP_FIVE);
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
