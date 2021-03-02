package com.skymanlab.weighttraining.management.project.fragment.More.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.CalendarManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;

import java.util.ArrayList;

public class AttendanceDialog extends DialogFragment implements FragmentSectionInitializable {
    // constant
    private static final String CLASS_NAME = AttendanceDialog.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String USER_FITNESS_CENTER = "userFitnessCenter";
    private static final String ATTENDANCE_DATE_LIST = "attendanceDateList";

    // instance variable
    private UserFitnessCenter userFitnessCenter;
    private ArrayList<Attendance> attendanceDateList;

    // instance variable
    private ImageView cancel;
    private MaterialCalendarView calendarView;
    private CalendarManager calendarManager;

    // constructor
    public AttendanceDialog() {

    }

    public static final AttendanceDialog newInstance(UserFitnessCenter userFitnessCenter, ArrayList<Attendance> attendanceDateList) {

        AttendanceDialog dialog = new AttendanceDialog();

        Bundle args = new Bundle();
        args.putSerializable(USER_FITNESS_CENTER, userFitnessCenter);
        args.putSerializable(ATTENDANCE_DATE_LIST, attendanceDateList);

        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.userFitnessCenter = (UserFitnessCenter) getArguments().getSerializable(USER_FITNESS_CENTER);
            this.attendanceDateList = (ArrayList<Attendance>) getArguments().getSerializable(ATTENDANCE_DATE_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        display();

        connectWidget();

        initWidget();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void connectWidget() {

        // [ ImageView | cancel ]
        this.cancel = (ImageView) getView().findViewById(R.id.custom_dialog_attendance_cancel);

        // [ MaterialCalendarView | calendarView ]
        this.calendarView = (MaterialCalendarView) getView().findViewById(R.id.custom_dialog_attendance_calendarView);

    }

    @Override
    public void initWidget() {

        // click listener
        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        // calendar
        calendarManager = new CalendarManager(
                getContext(),
                calendarView,
                userFitnessCenter.getContractDate(),
                userFitnessCenter.getExpiryDate(),
                attendanceDateList
        );

        calendarManager.init();

    }

    private void display() {
        final String METHOD_NAME = "[display] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > 등록일 = " + userFitnessCenter.getContractDate());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > 만료일 = " + userFitnessCenter.getExpiryDate());

        for (int index = 0; index < attendanceDateList.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > 출석일 = " + attendanceDateList.get(index).getDate());
        }
    }
}
