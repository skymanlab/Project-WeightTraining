package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.Step3D1Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.Step3D2Fragment;

public class Step2D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTS] Step2D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ToggleButton chest;
    private ToggleButton shoulder;
    private ToggleButton lat;
    private ToggleButton upperBody;
    private ToggleButton arm;
    private ToggleButton etc;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable
    private int step1D0Type;

    // instance variable
    private boolean[] isSelectedMuscleAreaList; // toggle button 에서 클릭 한 값을 알아내는

    // constructor
    public Step2D1SectionManager(Activity activity, View view, FragmentManager fragmentManager, int step1D0Type) {
        super(activity, view, fragmentManager);
        this.step1D0Type = step1D0Type;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]ToggleButton : [0] chest mapping
        this.chest = (ToggleButton) getView().findViewById(R.id.f_program_step2_1_chest);

        // [iv/C]ToggleButton : [1] shoulder mapping
        this.shoulder = (ToggleButton) getView().findViewById(R.id.f_program_step2_1_shoulder);

        // [iv/C]ToggleButton : [2] lat mapping
        this.lat = (ToggleButton) getView().findViewById(R.id.f_program_step2_1_lat);

        // [iv/C]ToggleButton : [3] upperBody mapping
        this.upperBody = (ToggleButton) getView().findViewById(R.id.f_program_step2_1_upper_body);

        // [iv/C]ToggleButton : [4] arm mapping
        this.arm = (ToggleButton) getView().findViewById(R.id.f_program_step2_1_arm);

        // [iv/C]ToggleButton : [5] etc mapping
        this.etc = (ToggleButton) getView().findViewById(R.id.f_program_step2_1_etc);

    }

    @Override
    public void initWidget() {

        // [iv/C]StepProcessManager : step 2-1 단계 설정 / OnNextClickListener 는 이 클래스에 implements 하여 override 된 함수에 구현한다.
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_TWO);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.initWidget();

        // [iv/b]isSelectedMuscleAreaList : ToggleButton 과 1:1 매핑한 값을 초기화한다.(초기값은 false 이다.)
        this.isSelectedMuscleAreaList = new boolean[6];
        for (boolean isSelected : isSelectedMuscleAreaList) {
            isSelected = false;
        }

        // [iv/C]ToggleButton : [0] chest change listener
        this.chest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // [iv/b]isSelectedMuscleAreaList : isChecked 값을 isSelectedMuscleAreaList[0] 에 저장
                isSelectedMuscleAreaList[0] = isChecked;
            }
        });


        // [iv/C]ToggleButton : [1] shoulder change listener
        this.shoulder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // [iv/b]isSelectedMuscleAreaList : isChecked 값을 isSelectedMuscleAreaList[1] 에 저장
                isSelectedMuscleAreaList[1] = isChecked;
            }
        });


        // [iv/C]ToggleButton : [2] lat change listener
        this.lat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // [iv/b]isSelectedMuscleAreaList : isChecked 값을 isSelectedMuscleAreaList[2] 에 저장
                isSelectedMuscleAreaList[2] = isChecked;
            }
        });


        // [iv/C]ToggleButton : [3] upperBody change listener
        this.upperBody.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // [iv/b]isSelectedMuscleAreaList : isChecked 값을 isSelectedMuscleAreaList[3] 에 저장
                isSelectedMuscleAreaList[3] = isChecked;
            }
        });


        // [iv/C]ToggleButton : [4] arm change listener
        this.arm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // [iv/b]isSelectedMuscleAreaList : isChecked 값을 isSelectedMuscleAreaList[4] 에 저장
                isSelectedMuscleAreaList[4] = isChecked;
            }
        });


        // [iv/C]ToggleButton : [5] etc change listener
        this.etc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // [iv/b]isSelectedMuscleAreaList : isChecked 값을 isSelectedMuscleAreaList[0] 에 저장
                isSelectedMuscleAreaList[5] = isChecked;
            }
        });

    }

    @Override
    public void setClickListenerOfNext() {

        final String METHOD_NAME = "[setClickListenerOfNext] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>==++++ 다음 버튼 클릭 !");

        for (int index = 0; index < this.isSelectedMuscleAreaList.length; index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + index + ">>> 번째 값은 ? = " + this.isSelectedMuscleAreaList[index]);
        }

        // [lv/C]FragmentTransaction : fragmentManager 를 통해 객체 가져오기
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // [check 1] : step1D0Type 이 뭐냐?
        switch (this.step1D0Type) {
            case Step1D0SectionManager.STEP_1_0_DIRECT_TYPE:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> step1 에서 direct 를 선택하였습니다.");
                // direct
                // [lv/C]Step3D1Fragment : step 3-1 fragment 객체 생성
                Step3D1Fragment step3_1Fragment = Step3D1Fragment.newInstance(this.isSelectedMuscleAreaList);

                // [lv/C]FragmentTransaction : step 3-1 fragment 화면 전환
                transaction.replace(R.id.nav_home_content_container, step3_1Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case Step1D0SectionManager.STEP_1_0_RANDOM_TYPE:

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> step1 에서 random 을 선택하였습니다.");
                // random
                // [lv/C]Step3D2Fragment : step 3-2 fragment 객체 생성
                Step3D2Fragment step3_2Fragment = Step3D2Fragment.newInstance(this.isSelectedMuscleAreaList);

                // [lv/C]FragmentTransaction : step 3-1 fragment 화면 전환
                transaction.replace(R.id.nav_home_content_container, step3_2Fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;
        }

    }
}
