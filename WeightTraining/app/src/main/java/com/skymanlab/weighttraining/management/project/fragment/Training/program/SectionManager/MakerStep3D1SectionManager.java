package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.DirectSelectionFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep6Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.DirectSelectionFragmentPagerAdapter;

import java.util.ArrayList;

public class MakerStep3D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, MakerStepManager.OnPreviousClickListener, MakerStepManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep3D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Fragment fragment;

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;
    private ArrayList<MuscleArea> fragmentMuscleAreaList;

    // instance variable
    private MakerStepManager makerStepManager;

    // instance variable
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    // instance variable
    private DirectSelectionFragmentPagerAdapter adapter;

    // constructor
    public MakerStep3D1SectionManager(Activity activity, View view, FragmentManager fragmentManager, Fragment fragment) {
        super(activity, view, fragmentManager);
        this.fragment = fragment;
    }

    // setter
    public void setFragmentArrayList(ArrayList<DirectSelectionFragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
    }

    public void setFragmentMuscleAreaList(ArrayList<MuscleArea> fragmentMuscleAreaList) {
        this.fragmentMuscleAreaList = fragmentMuscleAreaList;
    }

    @Override
    public void connectWidget() {

        // [iv/C]TabLayout : connect
        this.tabLayout = (TabLayout) getView().findViewById(R.id.f_maker_step3_1_tab);

        // [iv/C]ViewPager2 : connect
        this.viewPager = (ViewPager2) getView().findViewById(R.id.f_maker_step3_1_pager);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]MakerStepManager : step 3-1
        this.makerStepManager = new MakerStepManager(getView(), getFragmentManager(), MakerStepManager.STEP_THREE);
        this.makerStepManager.setPreviousClickListener(this);
        this.makerStepManager.setNextClickListener(this);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [method] : 위에서 추가한 fragmentArrayList 와 fragmentTitleList 로 viewPager 를 만들기
        initViewPager();

    }

    @Override
    public AlertDialog setClickListenerOfPrevious() {
        return null;
    }

    @Override
    public void setClickListenerOfNext() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 각 MuscleArea 의 EventResultSet =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // 각 MuscleArea 에서 체크된 항목과 체크되지 않은 항목을 가져와서, EventResultSet 의 객체의 selectedEventArrayList 와 noSelectedEventArrayList 에 각각 입력한다.
        EventResultSet chestEventResultSet = new EventResultSet();
        EventResultSet shoulderEventResultSet = new EventResultSet();
        EventResultSet latEventResultSet = new EventResultSet();
        EventResultSet upperBodyEventResultSet = new EventResultSet();
        EventResultSet armEventResultSet = new EventResultSet();
        EventResultSet etcEventResultSet = new EventResultSet();

        // [cycle 1] :
        for (int index = 0; index < fragmentArrayList.size(); index++) {

            switch (fragmentMuscleAreaList.get(index)) {
                case CHEST:

                    // [lv/C]EventResultSet : [0] chest 의 EventResultSet 의 selectedEventArrayList 와 noSelectedEventArrayList 를 입력하기
                    chestEventResultSet.setSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList());
                    chestEventResultSet.setNoSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getNoCheckedEventList());

                    break;

                case SHOULDER:

                    // [lv/C]EventResultSet : [1] shoulder 의 EventResultSet 의 selectedEventArrayList 와 noSelectedEventArrayList 를 입력하기
                    shoulderEventResultSet.setSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList());
                    shoulderEventResultSet.setNoSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getNoCheckedEventList());

                    break;

                case LAT:

                    // [lv/C]EventResultSet : [2] lat 의 EventResultSet 의 selectedEventArrayList 와 noSelectedEventArrayList 를 입력하기
                    latEventResultSet.setSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList());
                    latEventResultSet.setNoSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getNoCheckedEventList());

                    break;

                case LEG:

                    // [lv/C]EventResultSet : [3] upper_body 의 EventResultSet 의 selectedEventArrayList 와 noSelectedEventArrayList 를 입력하기
                    upperBodyEventResultSet.setSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList());
                    upperBodyEventResultSet.setNoSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getNoCheckedEventList());

                    break;

                case ARM:

                    // [lv/C]EventResultSet : [4] arm 의 EventResultSet 의 selectedEventArrayList 와 noSelectedEventArrayList 를 입력하기
                    armEventResultSet.setSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList());
                    armEventResultSet.setNoSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getNoCheckedEventList());

                    break;

                case ETC:

                    // [lv/C]EventResultSet : [5] etc 의 EventResultSet 의 selectedEventArrayList 와 noSelectedEventArrayList 를 입력하기
                    etcEventResultSet.setSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList());
                    etcEventResultSet.setNoSelectedEventArrayList(fragmentArrayList.get(index).getSectionManager().getNoCheckedEventList());

                    break;
            }

        } // [cycle 1]


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= step 4-1 로 넘어가는 과정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [check 7] : 각 MuscleArea 의 selectedEventArrayList 가 모두 0 이면 다음 단계로 넘어가지 못함
        if (chestEventResultSet.getSelectedEventArrayList().isEmpty()
                && shoulderEventResultSet.getSelectedEventArrayList().isEmpty()
                && latEventResultSet.getSelectedEventArrayList().isEmpty()
                && upperBodyEventResultSet.getSelectedEventArrayList().isEmpty()
                && armEventResultSet.getSelectedEventArrayList().isEmpty()
                && etcEventResultSet.getSelectedEventArrayList().isEmpty()) {

            // "선택되지 않았습니다." snack bar 메시지 출력
            Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step3_1_snack_next_check_true, Snackbar.LENGTH_SHORT).show();

        } else {

            // [lv/C]Step4D1Fragment  : step 6 fragment 생성 및 각 MuscleArea 의 EventResultSet 객체를 넘기기
            MakerStep6Fragment step6Fragment = MakerStep6Fragment.newInstance(
                    chestEventResultSet,
                    shoulderEventResultSet,
                    latEventResultSet,
                    upperBodyEventResultSet,
                    armEventResultSet,
                    etcEventResultSet
            );

            // [lv/C]FragmentTransaction :
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_home_content_wrapper, step6Fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } // [check 1]


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------- CHEST ----------------------------------");
        for (int index=0; index < chestEventResultSet.getNoSelectedEventArrayList().size() ; index++ ) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<" +index +">>>>> event Name = " + chestEventResultSet.getNoSelectedEventArrayList().get(index).getEventName());
        } // [cycle ]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------- SHOULDER ----------------------------------");
        for (int index=0; index < shoulderEventResultSet.getNoSelectedEventArrayList().size() ; index++ ) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<" +index +">>>>> event Name = " + shoulderEventResultSet.getNoSelectedEventArrayList().get(index).getEventName());
        } // [cycle ]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------- LAT ----------------------------------");
        for (int index=0; index < latEventResultSet.getNoSelectedEventArrayList().size() ; index++ ) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<" +index +">>>>> event Name = " + latEventResultSet.getNoSelectedEventArrayList().get(index).getEventName());
        } // [cycle ]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------- UPPER_BODY ----------------------------------");
        for (int index=0; index < upperBodyEventResultSet.getNoSelectedEventArrayList().size() ; index++ ) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<" +index +">>>>> event Name = " + upperBodyEventResultSet.getNoSelectedEventArrayList().get(index).getEventName());
        } // [cycle ]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------- ARM ----------------------------------");
        for (int index=0; index < armEventResultSet.getNoSelectedEventArrayList().size() ; index++ ) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<" +index +">>>>> event Name = " + armEventResultSet.getNoSelectedEventArrayList().get(index).getEventName());
        } // [cycle ]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------- ETC ----------------------------------");
        for (int index=0; index < etcEventResultSet.getNoSelectedEventArrayList().size() ; index++ ) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<" +index +">>>>> event Name = " + etcEventResultSet.getNoSelectedEventArrayList().get(index).getEventName());
        } // [cycle ]

    }


    /**
     * [method] fragmentArrayList 의 fragment 로 tabLayout, viewPager 초기화 하기
     */
    public void initViewPager() {

        final String METHOD_NAME = "[initViewPager]";

        // [iv/C]DirectPagerAdapter : viewPager 의 adapter 생성
        this.adapter = new DirectSelectionFragmentPagerAdapter(fragment, fragmentArrayList);

        // [iv/C]ViewPager2 : 위의 adapter 를 연결
        this.viewPager.setAdapter(adapter);
        this.viewPager.setOffscreenPageLimit(fragmentArrayList.size());

        // [lv/C]TabLayoutMediator : viewPager 와 tabLayout 을 1:1 매핑하고, title 붙이기
        TabLayoutMediator mediator = new TabLayoutMediator(this.tabLayout, this.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(DataManager.convertHanguleOfMuscleArea(fragmentMuscleAreaList.get(position)));

            }
        });
        mediator.attach();

    } // End of method [initViewPager]
}
