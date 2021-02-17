package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.ApiManager.AttendanceCalendarManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.util.ArrayList;


public class FitnessCenterRegisterInfoSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterRegisterInfoSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private String contractDate;
    private String expiryDate;
    private ArrayList<String> attendanceDateList;

    // instance variable
    private MaterialCalendarView materialCalendarView;

    // constructor
    public FitnessCenterRegisterInfoSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setAttendanceDateList(ArrayList<String> attendanceDateList) {
        this.attendanceDateList = attendanceDateList;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public void connectWidget() {

        // [ MaterialCalendarView | materialCalendarView ]
        this.materialCalendarView = (MaterialCalendarView) getView().findViewById(R.id.f_fitness_center_register_info_calendar_view);

    }

    @Override
    public void initWidget() {

        // init material calendar view
        AttendanceCalendarManager attendanceCalendarManager = new AttendanceCalendarManager(
                getFragment().getContext(),
                materialCalendarView,
                contractDate,
                expiryDate,
                attendanceDateList
        );
        attendanceCalendarManager.init();

    }
}
