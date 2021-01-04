package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.DirectSelectionFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.Step3D1Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.Step4D1Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.DirectPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Step3D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] Step3D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Fragment fragment;
    private GroupingEventData chestGroupingEventData;
    private GroupingEventData shoulderGroupingEventData;
    private GroupingEventData latGroupingEventData;
    private GroupingEventData upperBodyGroupingEventData;
    private GroupingEventData armGroupingEventData;
    private GroupingEventData etcGroupingEventData;

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;
    private ArrayList<String> fragmentTitleList;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ContentLoadingProgressBar progressBar;

    // instance variable
    private DirectPagerAdapter adapter;

    // constructor
    public Step3D1SectionManager(Activity activity, View view, FragmentManager fragmentManager, Fragment fragment) {
        super(activity, view, fragmentManager);
        this.fragment = fragment;
    }

    // setter
    public void setChestGroupingEventData(GroupingEventData chestGroupingEventData) {
        this.chestGroupingEventData = chestGroupingEventData;
    }

    public void setShoulderGroupingEventData(GroupingEventData shoulderGroupingEventData) {
        this.shoulderGroupingEventData = shoulderGroupingEventData;
    }

    public void setLatGroupingEventData(GroupingEventData latGroupingEventData) {
        this.latGroupingEventData = latGroupingEventData;
    }

    public void setUpperBodyGroupingEventData(GroupingEventData upperBodyGroupingEventData) {
        this.upperBodyGroupingEventData = upperBodyGroupingEventData;
    }

    public void setArmGroupingEventData(GroupingEventData armGroupingEventData) {
        this.armGroupingEventData = armGroupingEventData;
    }

    public void setEtcGroupingEventData(GroupingEventData etcGroupingEventData) {
        this.etcGroupingEventData = etcGroupingEventData;
    }


    public void setFragmentArrayList(ArrayList<DirectSelectionFragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
    }

    public void setFragmentTitleList(ArrayList<String> fragmentTitleList) {
        this.fragmentTitleList = fragmentTitleList;
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

        // [method] : 위에서 추가한 fragmentArrayList 와 fragmentTitleList 로 viewPager 를 만들기
        initViewPager();

    }

    @Override
    public void setClickListenerOfNext() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        ArrayList<Event> checkedAllEventArrayList = new ArrayList<>();

        // [cycle 1] :
        for (int index=0; index < fragmentArrayList.size() ; index++ ) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------- < " + index  + " >- sectionManger = " +fragmentArrayList.get(index).getSectionManager());
            checkedAllEventArrayList.addAll(fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList());
        } // [cycle 1]

        for (int index = 0; index < checkedAllEventArrayList.size(); index++) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + index + ">>> 선택된 eventName 은 ? " + checkedAllEventArrayList.get(index).getEventName());
        } // [cycle 1]


        // [check 1] : checkedAllEventArrayList 에 데이터가 있을 때만
        if (!checkedAllEventArrayList.isEmpty()) {

            // [lv/C]Step4D1Fragment  : step 4-1 fragment 생성 및 checkedAllEventArrayList 를 selectedEventArrayList 로 넘기기
            Step4D1Fragment step4_1 = Step4D1Fragment.newInstance(checkedAllEventArrayList);

            // [lv/C]FragmentTransaction :
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_home_content_container, step4_1);
            transaction.addToBackStack(null);
            transaction.commit();

        } else {

            // "선택되지 않았습니다." Toast 메시지 출력
            Toast.makeText(getActivity(), "선택되지 않았습니다.", Toast.LENGTH_SHORT).show();

        } // [check 1]

    }


    /**
     * [method] fragmentArrayList 의 fragment 로 tabLayout, viewPager 초기화 하기
     */
    public void initViewPager() {

        final String METHOD_NAME = "[initViewPager]" ;

        // [iv/C]DirectPagerAdapter : viewPager 의 adapter 생성
        this.adapter = new DirectPagerAdapter(fragment, fragmentArrayList);

        // [iv/C]ViewPager2 : 위의 adapter 를 연결
        this.viewPager.setAdapter(adapter);
        this.viewPager.setOffscreenPageLimit(fragmentArrayList.size());

        // [lv/C]TabLayoutMediator : viewPager 와 tabLayout 을 1:1 매핑하고, title 붙이기
        TabLayoutMediator mediator = new TabLayoutMediator(this.tabLayout, this.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(fragmentTitleList.get(position));

            }
        });
        mediator.attach();

        // [iv/C]ContentLoadingProgressBar : GONE
        this.progressBar.setVisibility(View.GONE);

    } // End of method [initViewPager]

}
