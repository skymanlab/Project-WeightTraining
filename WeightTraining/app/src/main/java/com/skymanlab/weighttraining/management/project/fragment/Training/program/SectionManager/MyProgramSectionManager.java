package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

public class MyProgramSectionManager extends FragmentSectionManager implements FragmentSectionInitializable{

    // constant
    private static final String CLASS_NAME = "[PFTPS] MyProgramSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private RecyclerView list;

    // constructor
    public MyProgramSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [iv/C]RecyclerView : list connect
        this.list = (RecyclerView)getView().findViewById(R.id.f_my_program_recycler_view);

    }

    @Override
    public void initWidget() {

    }

}
