package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.SettingsActivity;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.MyUserDataManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SettingsManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.MoreFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.MyTrainingInfoFragment;
import com.skymanlab.weighttraining.management.user.data.UserTraining;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MoreSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFM] MoreSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private LinearLayout registerDayCountWrapper;
    private TextView registerDayCount;
    private LinearLayout trainingCountWrapper;
    private TextView trainingCount;
    private MaterialCardView fitnessCenter;
    private MaterialCardView target;
    private MaterialCardView setting;
    private MaterialCardView myTrainingInfo;
    private MaterialButton notice;
    private MaterialButton serviceCenter;

    // instance variable :
    private UserTraining myTrainingData = null;
    private UserFitnessCenter myFitnessCenterData = null;
    private ArrayList<Attendance> myAttendanceDateList = null;

    // constructor
    public MoreSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [iv/C]LinearLayout : connect
        this.registerDayCountWrapper = (LinearLayout) getView().findViewById(R.id.f_more_register_day_count_wrapper);

        // [iv/C]TextView : connect
        this.registerDayCount = (TextView) getView().findViewById(R.id.f_more_register_day_count);

        // [iv/C]LinearLayout : connect
        this.trainingCountWrapper = (LinearLayout) getView().findViewById(R.id.f_more_training_count_wrapper);

        // [iv/C]TextView : connect
        this.trainingCount = (TextView) getView().findViewById(R.id.f_more_training_count);

        // [ MaterialCardView | fitnessCenter ]
        this.fitnessCenter = (MaterialCardView) getView().findViewById(R.id.f_more_fitness_center);

        // [ MaterialCardView | target ]
        this.target = (MaterialCardView) getView().findViewById(R.id.f_more_target);

        // [ MaterialCardView | setting ]
        this.setting = (MaterialCardView) getView().findViewById(R.id.f_more_setting);

        // [ MaterialCardView | myTrainingInfo ]
        this.myTrainingInfo = (MaterialCardView) getView().findViewById(R.id.f_more_my_training_info);

        // [iv/C]MaterialButton : connect
        this.notice = (MaterialButton) getView().findViewById(R.id.f_more_notice);

        // [iv/C]MaterialButton : connect
        this.serviceCenter = (MaterialButton) getView().findViewById(R.id.f_more_service_center);
    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        MyUserDataManager myDataManager = new MyUserDataManager(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myDataManager.loadContent(new MyUserDataManager.OnFitnessCenterEventListener() {

            @Override
            public void onRegisterMyFitnessCenter(UserTraining userTraining, UserFitnessCenter userFitnessCenter, ArrayList<Attendance> attendanceDateList) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< MyFitnessCenterManager > userFitnessCenter 을 가져왔습니다.");

                // =========================================== my fitness center ===========================================
                // userFitnessCenter
                myFitnessCenterData = userFitnessCenter;

                // register day count 관련 widget 등의 초기 내용을 설정한다.
                initWidgetOfRegisterDayCount(
                        calculateRegisterDayCount(userFitnessCenter.getContractDate())
                );

                // training count 관련 widget 들의 초기 내용을 설정한다.
                initWidgetOfTrainingCount(attendanceDateList.size());

                // =========================================== my training data ===========================================
                myTrainingData = userTraining;

                // =========================================== my attendance date list ===========================================
                myAttendanceDateList = attendanceDateList;

            }

            @Override
            public void onNotRegister() {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< MyFitnessCenterManager > userFitnessCenter 이 null 이예요.");

            }
        });


        // [iv/C]LinearLayout : fitnessCenter click listener
        this.fitnessCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // fitnessCenterFragment 로 이동
                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, FitnessCenterFragment.newInstance(myFitnessCenterData, myAttendanceDateList))
                        .addToBackStack(MoreFragment.class.getSimpleName())
                        .commit();

            }
        });

        // [iv/C]LinearLayout : target click listener
        this.target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // [iv/C]LinearLayout : setting click listener
        this.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/C]Intent : SettingsActivity 로 이동하는
                Intent intent = new Intent(getFragment().getActivity(), SettingsActivity.class);
                getFragment().getActivity().startActivity(intent);

            }
        });

        this.myTrainingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================");
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getSquat = " + myTrainingData.getSquat());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getDeadlift = " + myTrainingData.getDeadlift());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getBenchPress = " + myTrainingData.getBenchPress());

                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.nav_home_content_wrapper,
                                MyTrainingInfoFragment.newInstance(myTrainingData)
                        )
                        .addToBackStack(null)
                        .commit();
            }
        });


        // [iv/C]MaterialButton : notice click listener
        this.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, FitnessCenterSearchFragment.newInstance())
                        .addToBackStack(null)
                        .commit();


            }
        });

        // [iv/C]MaterialButton : serviceCenter click listener
        this.serviceCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingsManager.displayAllSettingsValue(getFragment().getContext());

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("text/plain");
                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"skyman1325@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, getFragment().getString(R.string.f_more_service_center_email_title));
                intent.setType("message/rfc822");
                getFragment().getActivity().startActivity(intent);

            }
        });

    } // End of method [initWidget]


    /**
     * register day count 와 관련된 widget 들의 초기 내용을 설정한다.
     */
    private void initWidgetOfRegisterDayCount(int count) {

        // text : 피트니스 센터 등록 후 몇 일이 지났는지 표시한다.
        this.registerDayCount.setText("+ " + count + " 일");

        // click listener
        this.registerDayCountWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    } // End of method [initWidgetOfRegisterDayCount]


    /**
     * training count 와 관련된 widwget 들의 초기 내용을 설정한다.
     */
    private void initWidgetOfTrainingCount(int count) {

        // text :  피트니스 센터에 운동하러 간 일수를 표시한다.
        this.trainingCount.setText(count + " 회");

        // click listener
        this.trainingCountWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    } // End of method [initWidgetOfTrainingCount]


    private int calculateRegisterDayCount(String contractDate) {
        final String METHOD_NAME = "[calculateRegisterDayCount] ";

        LocalDate nowLocalDate = LocalDate.now();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.set(nowLocalDate.getYear(), nowLocalDate.getMonthValue() - 1, nowLocalDate.getDayOfMonth());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================================================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/LocalDate > YEAR = " + nowLocalDate.getYear());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/LocalDate > MONTH = " + nowLocalDate.getMonthValue());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/LocalDate > calendar = " + nowLocalDate.getDayOfMonth());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/Calendar > YEAR = " + nowCalendar.get(Calendar.YEAR));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/Calendar > MONTH = " + nowCalendar.get(Calendar.MONTH));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/Calendar > calendar = " + nowCalendar.get(Calendar.DAY_OF_MONTH));

        LocalDate contractLocalDate = LocalDate.parse(contractDate, DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        Calendar contractCalendar = Calendar.getInstance();
        contractCalendar.set(contractLocalDate.getYear(), contractLocalDate.getMonthValue() - 1, contractLocalDate.getDayOfMonth());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================================================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/LocalDate > YEAR = " + contractLocalDate.getYear());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/LocalDate > MONTH = " + contractLocalDate.getMonthValue());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/LocalDate > calendar = " + contractLocalDate.getDayOfMonth());


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/Calendar > YEAR = " + contractCalendar.get(Calendar.YEAR));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/Calendar > MONTH = " + contractCalendar.get(Calendar.MONTH));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/Calendar > calendar = " + contractCalendar.get(Calendar.DAY_OF_MONTH));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================================================================");

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DATE > date = " + contractCalendar.getTime().toString());

        long now = nowCalendar.getTimeInMillis() / (24 * 60 * 60 * 1000);
        long contract = contractCalendar.getTimeInMillis() / (24 * 60 * 60 * 1000);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 시간 > now = " + now);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 시간 > contract = " + contract);


        long D_DAY = now - contract;

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "경과 일수 = " + D_DAY);

        return (int) D_DAY;
    }

}
