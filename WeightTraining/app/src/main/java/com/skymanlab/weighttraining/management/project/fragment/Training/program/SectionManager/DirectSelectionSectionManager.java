package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.event.program.util.GroupingEventUtil;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.util.ArrayList;

public class DirectSelectionSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTS] Step3D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private LinearLayout aGroupWrapper;
    private LinearLayout aGroupItemWrapper;
    private LinearLayout bGroupWrapper;
    private LinearLayout bGroupItemWrapper;
    private LinearLayout cGroupWrapper;
    private LinearLayout cGroupItemWrapper;
    private LinearLayout dGroupWrapper;
    private LinearLayout dGroupItemWrapper;
    private LinearLayout eGroupWrapper;
    private LinearLayout eGroupItemWrapper;

    // instance variable
    private GroupingEventData groupingEventData;

    // constructor
    public DirectSelectionSectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    @Override
    public void mappingWidget() {

        // [iv/C]LinearLayout : mapping
        this.aGroupWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_a_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.aGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_a_group_item_wrapper);

        // [iv/C]LinearLayout : mapping
        this.bGroupWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_b_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.bGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_b_group_item_wrapper);

        // [iv/C]LinearLayout : mapping
        this.cGroupWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_c_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.cGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_c_group_item_wrapper);

        // [iv/C]LinearLayout : mapping
        this.dGroupWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_d_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.dGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_d_group_item_wrapper);

        // [iv/C]LinearLayout : mapping
        this.eGroupWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_e_group_wrapper);

        // [iv/C]LinearLayout : mapping
        this.eGroupItemWrapper = (LinearLayout) getView().findViewById(R.id.f_direct_selection_e_group_item_wrapper);

    }

    @Override
    public void initWidget() {


    }


    public void setGroupingEventData(ArrayList<Event> eventArrayList) {

        // [lv/C]GroupingEventUtil : eventArrayList 를 그룹 별로 나누어 저장한다.
        GroupingEventUtil util = new GroupingEventUtil(eventArrayList);

    }
}
