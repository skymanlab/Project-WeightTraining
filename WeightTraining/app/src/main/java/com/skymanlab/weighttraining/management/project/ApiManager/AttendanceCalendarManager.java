package com.skymanlab.weighttraining.management.project.ApiManager;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class AttendanceCalendarManager {

    // constant
    private static final String CLASS_NAME = AttendanceCalendarManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Context context;
    private MaterialCalendarView materialCalendarView;
    private String contractDate;
    private String expiryDate;
    private ArrayList<String> attendanceDateList;

    // constructor
    public AttendanceCalendarManager(Context context,
                                     MaterialCalendarView materialCalendarView,
                                     String contractDate,
                                     String expiryDate,
                                     ArrayList<String> attendanceDateList) {
        this.context = context;
        this.materialCalendarView = materialCalendarView;
        this.contractDate = contractDate;
        this.expiryDate = expiryDate;
        this.attendanceDateList = attendanceDateList;
    }


    public void init() {
        final String METHOD_NAME = "[init] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< String > contractDate = " + contractDate);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< String > expiryDate = " + expiryDate);


        // today decorator
        TodayDecorator todayDecorator = new TodayDecorator();

        // sunday decorator
        SundayDecorator sundayDecorator = new SundayDecorator();

        // saturday decorator
        SaturdayDecorator saturdayDecorator = new SaturdayDecorator();

        // attendance date decorator
        AttendanceDateDecorator attendanceDateDecorator = new AttendanceDateDecorator(
                transformAttendanceDate(attendanceDateList)
        );

//        // register period decorator
//        RegisterPeriodDecorator registerPeriodDecorator = new RegisterPeriodDecorator(contractDate, expiryDate);

        // contract date decorator
        ContractDateDecorator contractDateDecorator = new ContractDateDecorator(contractDate);

        // expiry date decorator
        ExpiryDateDecorator expiryDateDecorator = new ExpiryDateDecorator(expiryDate);

        // add decorators
        materialCalendarView.addDecorators(sundayDecorator, saturdayDecorator, attendanceDateDecorator, contractDateDecorator, expiryDateDecorator);

        // select today
        materialCalendarView.setSelectedDate(CalendarDay.today());

//        LocalDate localDate = getLocalDate("2021년 11월 13일");
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LocalDate > getYear = " + localDate.getYear());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LocalDate > getMonth = " + localDate.getMonthValue());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LocalDate > getDayOfMonth = " + localDate.getDayOfMonth());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LocalDate > getDayOfWeek = " + localDate.getDayOfWeek());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LocalDate > getDayOfMonth = " + localDate.getDayOfMonth());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LocalDate > getDayOfYear = " + localDate.getDayOfYear());
//
//        CalendarDay calendarDay = CalendarDay.from(localDate);
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< CalendarDay > getYear = " + calendarDay.getYear());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< CalendarDay > getMonth = " + calendarDay.getMonth());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< CalendarDay > getDay = " + calendarDay.getDay());
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< CalendarDay > getDate = " + calendarDay.getDate());


    }

    // ================================================ etc ================================================
    private ArrayList<CalendarDay> transformAttendanceDate(ArrayList<String> attendanceDateList) {
        ArrayList<CalendarDay> transformedData = new ArrayList<>();

        for (String attendanceDate : attendanceDateList) {
            transformedData.add(getCalendarDay(attendanceDate));
        }

        return transformedData;
    }

    private CalendarDay getCalendarDay(String date) {
        org.threeten.bp.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return CalendarDay.from(localDate);
    }

    private LocalDate getLocalDate(String date) {

        org.threeten.bp.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return LocalDate.parse(date, formatter);

    }


    // ================================================ Decorator ================================================
    // Span 종류 : TextView 에 대한 span
    // 1. StyleSpan - 사용법 : new StyleSpan(Typeface.BOLD)
    // 2. RelativeSizeSpan - 사용법 : new RelativeSizeSpan(1.4f)
    // 3. ForegroundColorSpan - 사용법 : new ForegroundColorSpan(Color.RED)

    /**
     * Today
     */
    public class TodayDecorator implements DayViewDecorator {

        // instance variable
        private CalendarDay calendarDay;

        // constructor
        public TodayDecorator() {
            this.calendarDay = CalendarDay.today();
        }


        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(calendarDay);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(ContextCompat.getDrawable(context, R.drawable.button_icon_check));
        }
    }


    /**
     * Sunday
     */
    public class SundayDecorator implements DayViewDecorator {

        // instance variable

        // constructor
        public SundayDecorator() {

        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            LocalDate localDate = day.getDate();
            return DayOfWeek.SUNDAY == localDate.getDayOfWeek();
        }

        @Override
        public void decorate(DayViewFacade view) {
            // text color -> RED
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }


    /**
     * Saturday
     */
    public class SaturdayDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            LocalDate localDate = day.getDate();
            return DayOfWeek.SATURDAY == localDate.getDayOfWeek();
        }

        @Override
        public void decorate(DayViewFacade view) {
            // text color -> RED
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }


    /**
     * Attendance Date
     */
    public class AttendanceDateDecorator implements DayViewDecorator {

        // instance variable
        private HashSet<CalendarDay> attendanceDateList;

        // constructor
        public AttendanceDateDecorator(Collection<CalendarDay> attendanceDateList) {
            this.attendanceDateList = new HashSet<>(attendanceDateList);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return attendanceDateList.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.attendance_date_checker));
        }
    }


    /**
     *
     */
    public class RegisterPeriodDecorator implements DayViewDecorator {

        // instance variable
        private String contractDate;
        private String expiryDate;

        // constructor
        public RegisterPeriodDecorator(String contractDate, String expiryDate) {
            this.contractDate = contractDate;
            this.expiryDate = expiryDate;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {


            // contractDate 를 CalendarDay 로 변환
            CalendarDay contractCalendarDay = CalendarDay.from(
                    getLocalDate(this.contractDate)
            );

            // expiryDate 를 CalendarDay 로 변환
            CalendarDay expiryCalendarDay = CalendarDay.from(
                    getLocalDate(this.expiryDate)
            );

            return day.equals(contractCalendarDay) || day.equals(expiryCalendarDay);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.attendance_register_period));
        }
    }


    /**
     * Contract Date
     */
    public class ContractDateDecorator implements DayViewDecorator{

        // instance variable
        private String contractDate;

        // constructor
        public ContractDateDecorator(String contractDate) {
            this.contractDate = contractDate;
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {

            CalendarDay contractCalendarDay = getCalendarDay(contractDate);

            return day.equals(contractCalendarDay);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.attendance_contract_date));
        }
    }


    /**
     * Expiry Date
     */
    public class ExpiryDateDecorator implements DayViewDecorator{

        // instance variable
        private String expiryDate;

        // constructor
        public ExpiryDateDecorator(String expiryDate) {
            this.expiryDate = expiryDate;
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {

            CalendarDay contractCalendarDay = getCalendarDay(expiryDate);

            return day.equals(contractCalendarDay);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.attendance_expiry_date));
        }
    }
}
