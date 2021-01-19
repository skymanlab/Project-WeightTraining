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
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.DirectSelectionFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep4Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.DirectSelectionFragmentPagerAdapter;

import java.util.ArrayList;

public class MakerStep3D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep3D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Fragment fragment;

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;
    private ArrayList<MuscleArea> fragmentMuscleAreaList;

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


        // [method] : 위에서 추가한 fragmentArrayList 와 fragmentTitleList 로 viewPager 를 만들기
        initViewPager();

    }


    /**
     * 다음 단계를 진행하기 위한 과정을 EndButtonLister 객체를 생성하여 반환한다.
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public void setEndButtonClickListener() {

                final String METHOD_NAME = "[setClickListenerOfNext] ";

                // [lv/C]EventResultSet : 각 muscleARea 별로 EventResultSet 생성하기
                EventResultSet chestEventResultSet = new EventResultSet();
                EventResultSet shoulderEventResultSet = new EventResultSet();
                EventResultSet latEventResultSet = new EventResultSet();
                EventResultSet upperBodyEventResultSet = new EventResultSet();
                EventResultSet armEventResultSet = new EventResultSet();
                EventResultSet etcEventResultSet = new EventResultSet();

                // [cycle 1] : fragment 의 각 muscleArea 의 CheckBox 에서 표시된 항목을 가져온다.
                for (int index = 0; index < fragmentArrayList.size(); index++) {

                    switch (fragmentMuscleAreaList.get(index)) {
                        case CHEST:
                            chestEventResultSet = fragmentArrayList.get(index).getSectionManager().getEventResultSetOfAllGroup();
                            break;
                        case SHOULDER:
                            shoulderEventResultSet = fragmentArrayList.get(index).getSectionManager().getEventResultSetOfAllGroup();
                            break;
                        case LAT:
                            latEventResultSet = fragmentArrayList.get(index).getSectionManager().getEventResultSetOfAllGroup();
                            break;
                        case UPPER_BODY:
                            upperBodyEventResultSet = fragmentArrayList.get(index).getSectionManager().getEventResultSetOfAllGroup();
                            break;
                        case ARM:
                            armEventResultSet = fragmentArrayList.get(index).getSectionManager().getEventResultSetOfAllGroup();
                            break;
                        case ETC:
                            etcEventResultSet = fragmentArrayList.get(index).getSectionManager().getEventResultSetOfAllGroup();
                            break;
                    }
                } // [cycle 1]


                // [check 1] : 각 muscleARea 의 selectedEventArrayList 에 데이터가 있을 때만 다음 단계를 진행한다.
                if (chestEventResultSet.getSelectedEventArrayList().isEmpty()
                        && shoulderEventResultSet.getSelectedEventArrayList().isEmpty()
                        && latEventResultSet.getSelectedEventArrayList().isEmpty()
                        && upperBodyEventResultSet.getSelectedEventArrayList().isEmpty()
                        && armEventResultSet.getSelectedEventArrayList().isEmpty()
                        && etcEventResultSet.getSelectedEventArrayList().isEmpty()) {

                    // "선택되지 않았습니다." snack bar 메시지 출력
                    Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step3_1_snack_next_check_true, Snackbar.LENGTH_SHORT).show();

                } else {

                    // [lv/C]MakerStep4Fragment : step 4 fragment 생성
                    MakerStep4Fragment step4Fragment = MakerStep4Fragment.newInstance(
                            chestEventResultSet,
                            shoulderEventResultSet,
                            latEventResultSet,
                            upperBodyEventResultSet,
                            armEventResultSet,
                            etcEventResultSet);

                    // [lv/C]FragmentTransaction : step 4 Fragment 로 이동
                    FragmentTransaction transaction = fragment.getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_home_content_wrapper, step4Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } // [check 1]
            }
        };
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
