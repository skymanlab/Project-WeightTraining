package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.project.ApiManager.NetworkStateChecker;
import com.skymanlab.weighttraining.management.project.fragment.More.NoticeFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.SettingsFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.dialog.AttendanceDialog;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.MyUserDataManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SettingsManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.MoreFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.UserTrainingInfoFragment;
import com.skymanlab.weighttraining.management.user.data.UserTraining;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MoreSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFM] MoreSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable :
    private UserTraining myTraining = null;
    private UserFitnessCenter myFitnessCenter = null;
    private ArrayList<Attendance> myAttendanceDateList = null;

    // instance variable
    private LinearLayout registerDayCountWrapper;
    private TextView registerDayCount;
    private LinearLayout trainingCountWrapper;
    private TextView trainingCount;
    private MaterialCardView fitnessCenter;
    private MaterialCardView setting;
    private MaterialCardView training;
    private MaterialButton notice;
    private MaterialButton serviceCenter;

    // instance variable :
    private AdView adMob;

    // constructor
    public MoreSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [iv/C]LinearLayout : connect
        this.registerDayCountWrapper = (LinearLayout) getView().findViewById(R.id.f_more_registerDayCount_wrapper);

        // [iv/C]TextView : connect
        this.registerDayCount = (TextView) getView().findViewById(R.id.f_more_registerDayCount);


        // [iv/C]LinearLayout : connect
        this.trainingCountWrapper = (LinearLayout) getView().findViewById(R.id.f_more_trainingCount_wrapper);

        // [iv/C]TextView : connect
        this.trainingCount = (TextView) getView().findViewById(R.id.f_more_trainingCount);


        // [ MaterialCardView | fitnessCenter ]
        this.fitnessCenter = (MaterialCardView) getView().findViewById(R.id.f_more_fitnessCenter);

        // [ MaterialCardView | setting ]
        this.setting = (MaterialCardView) getView().findViewById(R.id.f_more_setting);

        // [ MaterialCardView | training ]
        this.training = (MaterialCardView) getView().findViewById(R.id.f_more_training);


        // [iv/C]MaterialButton : connect
        this.notice = (MaterialButton) getView().findViewById(R.id.f_more_notice);

        // [iv/C]MaterialButton : connect
        this.serviceCenter = (MaterialButton) getView().findViewById(R.id.f_more_serviceCenter);

        // [ AdView | adMob ] widget connect
        this.adMob = (AdView) getView().findViewById(R.id.f_more_adMob);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // ad mob adMob init
        initWidgetOfAdMob();

        // ============================================================= 
        MyUserDataManager myDataManager = new MyUserDataManager(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myDataManager.loadContent(new MyUserDataManager.OnFitnessCenterEventListener() {

            @Override
            public void onRegisterMyFitnessCenter(UserTraining userTraining, UserFitnessCenter userFitnessCenter, ArrayList<Attendance> attendanceDateList) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< MyFitnessCenterManager > userFitnessCenter 을 가져왔습니다.");

                // userFitnessCenter
                myFitnessCenter = userFitnessCenter;

                // userTraining
                myTraining = userTraining;

                // attendanceDateList
                myAttendanceDateList = attendanceDateList;

                // 피트니스 센터가 등록되었을 때만
                // - 등록일
                // - 운동 횟수
                if (userFitnessCenter != null) {

                    // =========================================== register day count ===========================================
                    // 등록일 -> userFitnessCenter 객체 : register day count 관련 widget 등의 초기 내용을 설정한다.
                    initWidgetOfRegisterDayCount(
                            calculateRegisterDayCount(userFitnessCenter.getContractDate())
                    );

                    // =========================================== training count ===========================================
                    // 운동 횟수 -> attendanceDateList 객체 : training count 관련 widget 들의 초기 내용을 설정한다.
                    initWidgetOfTrainingCount(userFitnessCenter, attendanceDateList);

                }

            }

            @Override
            public void onNotRegister() {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< MyFitnessCenterManager > userFitnessCenter 이 null 이예요.");

            }
        });


        // ================================================= fitness center =================================================
        // click listener
        this.fitnessCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // FitnessCenterFragment 로 이동
                // 1. UserFitnessCenter  : 피트니스 센터를 등록해야지만 가져올 수 있음
                // 2. ArrayList<Attendance> : 피트니스 센터를 등록하기만 하면 size 가 0 이상은 됨( 이유 : 출석체크를 하지 않으면 size 가 0 입니다.)
                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, FitnessCenterFragment.newInstance(myFitnessCenter, myAttendanceDateList))
                        .addToBackStack(MoreFragment.class.getSimpleName())
                        .commit();

            }
        });


        // ================================================= training =================================================
        // click listener
        this.training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // UserTrainingInfoFragment 로 이동
                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.nav_home_content_wrapper,
                                UserTrainingInfoFragment.newInstance(myTraining)
                        )
                        .addToBackStack(null)
                        .commit();


                // 그냥 로그 찍기 위해서
                if (myTraining != null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================");
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getSquat = " + myTraining.getSquat());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getDeadlift = " + myTraining.getDeadlift());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getBenchPress = " + myTraining.getBenchPress());

                }

            }
        });


        // ================================================= setting =================================================
        // click listener
        this.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 설정화면(SettingsFragment) 로 이동
                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });


        // ------------------------------------------------- notice -------------------------------------------------
        // click listener
        this.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // NoticeFragment 로 이동
                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, NoticeFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        });


        // ------------------------------------------------- service center -------------------------------------------------
        // click listener
        this.serviceCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingsManager.displayAllSettingsValue(getFragment().getContext());

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("text/plain");
                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"skyman1325@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, getFragment().getString(R.string.f_more_serviceCenter_emailTitle));
                intent.setType("message/rfc822");
                getFragment().getActivity().startActivity(intent);

            }
        });

    } // End of method [initWidget]


    /**
     * Widget : adMob
     */
    private void initWidgetOfAdMob() {

        MobileAds.initialize(getFragment().getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adMob.loadAd(adRequest);
//        adMob.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//            }
//
//            @Override
//            public void onAdFailedToLoad(LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//
////            @Override
////            public void onAdLeftApplication() {
////                super.onAdLeftApplication();
////            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//
//            @Override
//            public void onAdClicked() {
//                super.onAdClicked();
//            }
//        });

    }

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
    private void initWidgetOfTrainingCount(UserFitnessCenter userFitnessCenter, ArrayList<Attendance> attendanceDateList) {

        // text :  피트니스 센터에 운동하러 간 일수를 표시한다.
        this.trainingCount.setText(attendanceDateList.size() + " 회");

        // click listener
        this.trainingCountWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AttendanceDialog dialog = AttendanceDialog.newInstance(userFitnessCenter, attendanceDateList);
                dialog.setStyle(
                        DialogFragment.STYLE_NO_TITLE,
                        android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
                );

                dialog.show(
                        getFragment().getActivity().getSupportFragmentManager(),
                        AttendanceDialog.class.getSimpleName()
                );

            }
        });

    } // End of method [initWidgetOfTrainingCount]


    /**
     * 등록된 날에서 몇일이 지났는지 계산하여 반환한다.
     *
     * @param contractDate
     * @return
     */
    private int calculateRegisterDayCount(String contractDate) {
        final String METHOD_NAME = "[calculateRegisterDayCount] ";

        // 현재 시간 가져오기
        LocalDate nowLocalDate = LocalDate.now();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.set(nowLocalDate.getYear(), nowLocalDate.getMonthValue() - 1, nowLocalDate.getDayOfMonth());

        // 파라미터로 받은 날짜를 Calendar 객체로 변환
        LocalDate contractLocalDate = LocalDate.parse(contractDate, DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        Calendar contractCalendar = Calendar.getInstance();
        contractCalendar.set(contractLocalDate.getYear(), contractLocalDate.getMonthValue() - 1, contractLocalDate.getDayOfMonth());

        // 시간을 하루(24*60*60*1000) 로 나눠서 일수로 변환  
        long now = nowCalendar.getTimeInMillis() / (24 * 60 * 60 * 1000);
        long contract = contractCalendar.getTimeInMillis() / (24 * 60 * 60 * 1000);

        // 현재 일에서 등록일을 빼서 경과된 일수를 구하기 + 등록일도 포함하여 화면에 표시
        long D_DAY = now - contract + 1;

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================================================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/LocalDate > YEAR = " + nowLocalDate.getYear());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/LocalDate > MONTH = " + nowLocalDate.getMonthValue());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/LocalDate > calendar = " + nowLocalDate.getDayOfMonth());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/Calendar > YEAR = " + nowCalendar.get(Calendar.YEAR));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/Calendar > MONTH = " + nowCalendar.get(Calendar.MONTH));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 현재/Calendar > calendar = " + nowCalendar.get(Calendar.DAY_OF_MONTH));

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================================================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/LocalDate > YEAR = " + contractLocalDate.getYear());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/LocalDate > MONTH = " + contractLocalDate.getMonthValue());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/LocalDate > calendar = " + contractLocalDate.getDayOfMonth());


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/Calendar > YEAR = " + contractCalendar.get(Calendar.YEAR));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/Calendar > MONTH = " + contractCalendar.get(Calendar.MONTH));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 등록일/Calendar > calendar = " + contractCalendar.get(Calendar.DAY_OF_MONTH));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================================================================");

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DATE > date = " + contractCalendar.getTime().toString());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 시간 > now = " + now);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 시간 > contract = " + contract);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "경과 일수 = " + D_DAY);

        return (int) D_DAY;
    }

}
