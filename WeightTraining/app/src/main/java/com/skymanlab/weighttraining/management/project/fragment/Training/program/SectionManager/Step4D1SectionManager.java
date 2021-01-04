package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

public class Step4D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener{

    // instance variable
    private StepProcessManager stepProcessManager;

    // constructor
    public Step4D1SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    @Override
    public void mappingWidget() {

    }

    @Override
    public void initWidget() {

        // [iv/C]StepProcessManager : step 4-1
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_FOUR);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.initWidget();

    }

    @Override
    public void setClickListenerOfNext() {

    }
}
