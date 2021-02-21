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
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;

import java.util.ArrayList;


public class FitnessCenterRegisterInfoSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterRegisterInfoSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private UserFitnessCenter myFitnessCenter;
    private ArrayList<Attendance> myAttendanceDateList;

    // instance variable
    private MaterialCalendarView attendanceCalendar;

    // constructor
    public FitnessCenterRegisterInfoSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    public void setMyFitnessCenter(UserFitnessCenter myFitnessCenter) {
        this.myFitnessCenter = myFitnessCenter;
    }

    public void setMyAttendanceDateList(ArrayList<Attendance> myAttendanceDateList) {
        this.myAttendanceDateList = myAttendanceDateList;
    }

    // setter
    @Override
    public void connectWidget() {

        // [ MaterialCalendarView | attendanceCalendar ]
        this.attendanceCalendar = (MaterialCalendarView) getView().findViewById(R.id.f_fitness_center_register_info_attendance_calendar);

    }

    @Override
    public void initWidget() {

        // init material calendar view
        AttendanceCalendarManager attendanceCalendarManager = new AttendanceCalendarManager(
                getFragment().getContext(),
                attendanceCalendar,
                myFitnessCenter.getContractDate(),
                myFitnessCenter.getExpiryDate(),
                myAttendanceDateList
        );
        attendanceCalendarManager.init();

    }
}
