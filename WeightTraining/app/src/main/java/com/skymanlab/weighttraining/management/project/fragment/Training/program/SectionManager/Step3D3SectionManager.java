package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

public class Step3D3SectionManager extends FragmentSectionManager implements FragmentSectionInitializable , StepProcessManager.OnNextClickListener{

    // constant
    private static final String CLASS_NAME = "[PFTS] Step3D2SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constructor
    public Step3D3SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    @Override
    public void mappingWidget() {

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void setClickListenerOfNext() {

    }
}
