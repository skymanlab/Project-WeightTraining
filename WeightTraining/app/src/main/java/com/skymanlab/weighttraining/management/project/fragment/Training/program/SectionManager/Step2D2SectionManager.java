package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

public class Step2D2SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener{

    // constant
    private static final String CLASS_NAME = "[PFTS] Step2D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable
    private RecyclerView list;

    // constructor
    public Step2D2SectionManager(View view, FragmentManager fragmentManager) {
        super(view, fragmentManager);
    }

    @Override
    public void mappingWidget() {

        // [iv/C]RecyclerView : list mapping
        this.list = (RecyclerView)getView().findViewById(R.id.f_program_step2_2_recycler_view);

    }

    @Override
    public void initWidget() {

        // [iv/C]StepProcessManager : step 2-1 단계 설정 / OnNextClickListener 는 이 클래스에 implements 하여 override 된 함수에 구현한다.
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_TWO);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.initWidget();
    }

    @Override
    public void setClickListenerOfNext() {

        final String METHOD_NAME = "[setClickListenerOfNext] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++ next button click");
    }
}
