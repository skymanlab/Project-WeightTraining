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
import com.skymanlab.weighttraining.management.FitnessCenter.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.FitnessCenterGeofencingManager;
import com.skymanlab.weighttraining.management.project.ApiManager.MyFitnessCenterManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SettingsManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MoreSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFM] MoreSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private LinearLayout registerDayCountWrapper;
    private TextView registerDayCount;
    private LinearLayout trainingCountWrapper;
    private TextView trainingCount;
    private MaterialCardView fitnessCenter;
    private MaterialCardView target;
    private MaterialCardView setting;
    private MaterialButton notice;
    private MaterialButton serviceCenter;


    // instance variable :
    FitnessCenterGeofencingManager geofenceManager;
    private UserFitnessCenter myFitnessCenter = null;

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

        // [iv/C]LinearLayout : fitnessCenter connect
        this.fitnessCenter = (MaterialCardView) getView().findViewById(R.id.f_more_fitness_center);

        // [iv/C]LinearLayout : target connect
        this.target = (MaterialCardView) getView().findViewById(R.id.f_more_target);

        // [iv/C]LinearLayout : setting connect
        this.setting = (MaterialCardView) getView().findViewById(R.id.f_more_setting);

        // [iv/C]MaterialButton : connect
        this.notice = (MaterialButton) getView().findViewById(R.id.f_more_notice);

        // [iv/C]MaterialButton : connect
        this.serviceCenter = (MaterialButton) getView().findViewById(R.id.f_more_service_center);
    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        MyFitnessCenterManager myFitnessCenterManager = new MyFitnessCenterManager(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myFitnessCenterManager.loadContent(new MyFitnessCenterManager.OnFitnessCenterEventListener() {
            @Override
            public void onRegisterMyFitnessCenter(UserFitnessCenter userFitnessCenter) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< MyFitnessCenterManager > userFitnessCenter 을 가져왔습니다.");

                // userFitnessCenter
                myFitnessCenter = userFitnessCenter;

                // register day count 관련 widget 등의 초기 내용을 설정한다.
                initWidgetOfRegisterDayCount(
                        calculateRegisterDayCount(userFitnessCenter.getContractDate())
                );

                // training count 관련 widget 들의 초기 내용을 설정한다.
                initWidgetOfTrainingCount(userFitnessCenter.getAttendanceDateList().size());

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
                        .replace(R.id.nav_home_content_wrapper, FitnessCenterFragment.newInstance(myFitnessCenter))
                        .addToBackStack(null)
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

        // [iv/C]MaterialButton : notice click listener
        this.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, FitnessCenterSearchFragment.newInstance())
                        .addToBackStack(FitnessCenterFragment.class.getSimpleName())
                        .commit();

//                FitnessCenterMemberManager memberManager = new FitnessCenterMemberManager(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                memberManager.init(new FitnessCenterMemberManager.OnMemberManipulateListener() {
//                    @Override
//                    public void manipulateMemberList(ArrayList<Member> memberArrayList) {
//
//                        for (int index = 0; index < memberArrayList.size(); index++) {
//                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=========< " + index + " > getMemberNumber = " + memberArrayList.get(index).getMemberNumber());
//                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=========< " + index + " > getUserName = " + memberArrayList.get(index).getUserName());
//                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=========< " + index + " > getIsActive = " + memberArrayList.get(index).getIsActive());
//                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=========< " + index + " > getIsDisclosed = " + memberArrayList.get(index).getIsDisclosed());
//                        }
//
//                    }
//                });

//                ConnectivityManager manager = (ConnectivityManager) getFragment().getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                Network currentNetwork = manager.getActiveNetwork();
//                for (Network network : manager.getAllNetworks()) {
//                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> 모든 네트워크 확인 중 = " + network);
//                }
//
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 네트워크 상태 > 현재 네트워크 상태는 NET_CAPABILITY_NOT_METERED = " +currentNetwork);

            }
        });

        // [iv/C]MaterialButton : serviceCenter click listener
        this.serviceCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingsManager.displayAllSettingsValue(getFragment().getContext());

            }
        });

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
