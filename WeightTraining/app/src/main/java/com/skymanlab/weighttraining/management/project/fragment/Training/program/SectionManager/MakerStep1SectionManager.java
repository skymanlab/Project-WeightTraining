package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep2Fragment;

public class MakerStep1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    public static final int STEP_1_DIRECT_TYPE = 0;
    public static final int STEP_1_EACH_RANDOM_TYPE = 1;
    public static final int STEP_1_ALL_RANDOM_TYPE = 2;

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // instance variable : step process manager
    private StepProcessManager stepProcessManager;

    // instance variable : widget
    private MaterialButton directSelection;         // [0] type
    private MaterialButton eachRandomSelection;     // [1] type
    private MaterialButton allRandomSelection;      // [2] type

    // constructor
    public MakerStep1SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    @Override
    public void mappingWidget() {

        // [iv/C]MaterialButton : [0] directSelection mapping
        this.directSelection = (MaterialButton) getView().findViewById(R.id.f_maker_step1_direct_selection);

        // [iv/C]MaterialButton : [1] eachRandomSelection mapping
        this.eachRandomSelection = (MaterialButton) getView().findViewById(R.id.f_maker_step1_each_random_selection);

        // [iv/C]MaterialButton : [2] allRandomSelection mapping
        this.allRandomSelection = (MaterialButton) getView().findViewById(R.id.f_maker_step1_all_random_selection);


    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]StepProgressManager : step 1 단계 설정
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_ONE);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.initWidget();

        // [iv/C]MaterialButton : directSelection click listener
        this.directSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "direct selection click ");

                // [lv/C]Step2D1Fragment : 객체를 생성하면서 STEP_1_DIRECT_TYPE(direct 버튼을 클릭했다는) 값을 넘겨준다.
                MakerStep2Fragment fragment = MakerStep2Fragment.newInstance(STEP_1_DIRECT_TYPE);

                // [method] : 위에서 생성한 fragment 로 이동하는 과정 진행
                moveStep2(fragment);

            }
        });

        // [iv/C]MaterialButton : eachRandomSelection click listener
        this.eachRandomSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "random selection click ");

                // [lv/C]Step2D1Fragment : 객체를 생성하면서 STEP_1_EACH_RANDOM_TYPE(each random 버튼을 클릭했다는) 값을 넘겨준다.
                MakerStep2Fragment fragment = MakerStep2Fragment.newInstance(STEP_1_EACH_RANDOM_TYPE);

                // [method] : 위에서 생성한 fragment 로 이동하는 과정 진행
                moveStep2(fragment);

            }
        });

        // [iv/C]MaterialButton : allRandomSelection click listener
        this.allRandomSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "random selection click ");

                // [lv/C]Step2D1Fragment : 객체를 생성하면서 STEP_1_ALL_RANDOM_TYPE(all random 버튼을 클릭했다는) 값을 넘겨준다.
                MakerStep2Fragment fragment = MakerStep2Fragment.newInstance(STEP_1_ALL_RANDOM_TYPE);

                // [method] : 위에서 생성한 fragment 로 이동하는 과정 진행
                moveStep2(fragment);

            }
        });

    }


    /**
     * [method] Step 2 로 이동
     */
    private void moveStep2(Fragment fragment) {

        // [lv/C]FragmentTransaction : fragmentManger 를 통해서 FragmentTransaction 객체 가져오기 -> 이동할 fragment 설정, back stack 에 넣기 -> transaction 마무리(이동)
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_home_content_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    } // End of method [moveStep2]
}
