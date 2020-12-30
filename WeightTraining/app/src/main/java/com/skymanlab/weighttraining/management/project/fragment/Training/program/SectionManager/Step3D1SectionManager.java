package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.DirectSelectionFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.DirectPagerAdapter;

import java.util.ArrayList;

public class
Step3D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTS] Step3D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;
    private boolean isCompletedFragmentArrayList;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ContentLoadingProgressBar progressBar;

    // constructor
    public Step3D1SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
        this.isCompletedFragmentArrayList = false;
    }

    // setter
    public void setFragmentArrayList(ArrayList<DirectSelectionFragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
    }

    public void setCompletedFragmentArrayList(boolean completedFragmentArrayList) {
        isCompletedFragmentArrayList = completedFragmentArrayList;
    }

    // getter
    public ContentLoadingProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]TabLayout : mapping
        this.tabLayout = (TabLayout) getView().findViewById(R.id.f_program_step3_1_tab);

        // [iv/C]ViewPager2 : mapping
        this.viewPager = (ViewPager2) getView().findViewById(R.id.f_program_step3_1_pager);

        // [iv/C]ContentLoadingProgressBar : mapping
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_program_step3_1_progress_bar);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]StepProcessManager : step 3-1
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_THREE);
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.initWidget();

    }

    @Override
    public void setClickListenerOfNext() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        // [check 1] : isCompletedFragmentArrayList 가 true 일 때만
        if (this.isCompletedFragmentArrayList) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+> 클릭");
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>> sectionManager 에서  확인한 fragmentArrayList 의 size 는 ? = " + fragmentArrayList.size());


        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 아직 siCompletedFragmentArrayList 가 완료 되지 않았습니다. <=");
        } // [check 1]
    }


    /**
     * [method] fragmentArrayList 의 fragment 로 tabLayout, viewPager 초기화 하기
     *
     * @param fragment ViewPager2 가 포함되어 있는 layout 을 관리하는 Fragment
     * @param fragmentArrayList viewPager 에 표시하는 Fragment 객체가 들어있는 ArrayList
     * @param fragmentMuscleAreaList fragmentArrayList 와 1:1 매핑되어 있는 fragment 객체의 muscleArea 값이 있는 ArrayList
     */
    public void initViewPager(Fragment fragment, ArrayList<DirectSelectionFragment> fragmentArrayList, ArrayList<String> fragmentMuscleAreaList) {

        // [lv/C]DirectPagerAdapter : viewPager 의 adapter 생성
        DirectPagerAdapter adapter = new DirectPagerAdapter(fragment, fragmentArrayList);

        // [iv/C]ViewPager2 : 위의 adapter 를 연결
        this.viewPager.setAdapter(adapter);

        // [lv/C]TabLayoutMediator : viewPager 와 tabLayout 을 1:1 매핑하고, title 붙이기
        TabLayoutMediator mediator = new TabLayoutMediator(this.tabLayout, this.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(fragmentMuscleAreaList.get(position));

            }
        });
        mediator.attach();

    } // End of method [initViewPager]


}
