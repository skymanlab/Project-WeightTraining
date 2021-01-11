package com.skymanlab.weighttraining.management.project.fragment.Training.list.SectionManager;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter.EachListFragmentPagerAdapter;

public class ListSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTLS] ListSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Fragment fragment;

    // instance variable
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    // instance variable
    private EachListFragmentPagerAdapter pagerAdapter;

    // constructor
    public ListSectionManager(Activity activity, View view, Fragment fragment) {
        super(activity, view);
        this.fragment = fragment;
    }

    @Override
    public void connectWidget() {

        // [iv/C]TabLayout : tabLayout connect
        this.tabLayout = (TabLayout) getView().findViewById(R.id.f_list_tab_layout);

        // [iv/C]ViewPager2 : viewPager connect
        this.viewPager = (ViewPager2) getView().findViewById(R.id.f_list_view_pager2);

    }

    @Override
    public void initWidget() {

        // [iv/C]EachListPagerAdapter : ViewPager2 의 adapter 생성
        this.pagerAdapter = new EachListFragmentPagerAdapter(fragment);

        // [iv/C]ViewPager2 : 위의 pagerAdapter 를 연결
        this.viewPager.setAdapter(this.pagerAdapter);
        this.viewPager.setPageTransformer(new EachListFragmentPagerAdapter.ZoomOutPageTransformer());

        // [lv/C]TabLayoutMediator : ViewPager2 의 각 페이지마다 TabLayout 의 위치에 맞게 매핑하고, 각 title 을 만들기
        TabLayoutMediator mediator = new TabLayoutMediator(this.tabLayout, this.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position) {
                    case 0:
                        tab.setText(getActivity().getString(R.string.type_muscle_area_chest));
                        break;
                    case 1:
                        tab.setText(getActivity().getString(R.string.type_muscle_area_shoulder));
                        break;
                    case 2:
                        tab.setText(getActivity().getString(R.string.type_muscle_area_lat));
                        break;
                    case 3:
                        tab.setText(getActivity().getString(R.string.type_muscle_area_upper_body));
                        break;
                    case 4:
                        tab.setText(getActivity().getString(R.string.type_muscle_area_arm));
                        break;
                    case 5:
                        tab.setText(getActivity().getString(R.string.type_muscle_area_etc));
                        break;
                }
            }
        });
        mediator.attach();
    }
}
