package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterRegisteredInfoFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSettingInfoFragment;

import java.util.ArrayList;

public class FitnessCenterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private UserFitnessCenter myFitnessCenter;
    private ArrayList<Attendance> myAttendanceDateList;

    // instance variable
    private FrameLayout fitnessCenterWrapper;
    private ImageView fitnessCenterIndicatorImage;
    private TextView fitnessCenterName;
    private TextView fitnessCenterAddress;

    // instance variable
    private LinearLayout registeredInfoWrapper;
    private TextView registeredInfoTitle;
    private TextView memberNumber;
    private TextView contractDate;
    private TextView expiryDate;
    private TextView attendanceDateCounter;

    // instance variable
    private LinearLayout settingInfoWrapper;
    private TextView settingInfoTitle;
    private TextView isAllowedAccessNotification;
    private TextView isDisclosed;

    // instance variable
    private TextView registerCancel;

    // constructor
    public FitnessCenterSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setMyFitnessCenter(UserFitnessCenter myFitnessCenter) {
        this.myFitnessCenter = myFitnessCenter;
    }

    public void setMyAttendanceDateList(ArrayList<Attendance> myAttendanceDateList) {
        this.myAttendanceDateList = myAttendanceDateList;
    }


    @Override
    public void connectWidget() {

        // ================================= section 1 =================================
        // [ FrameLayout | fitnessCenterWrapper ]
        this.fitnessCenterWrapper = (FrameLayout) getView().findViewById(R.id.f_fitnessCenter_wrapper);

        // [ ImageView | fitnessCenterIndicatorImage ]
        this.fitnessCenterIndicatorImage = (ImageView) getView().findViewById(R.id.f_fitnessCenter_indicatorImage);

        // [TextView] [fitnessCenterName] widget connect
        this.fitnessCenterName = (TextView) getView().findViewById(R.id.f_fitnessCenter_name);

        // [TextView] [fitnessCenterAddress] widget connect
        this.fitnessCenterAddress = (TextView) getView().findViewById(R.id.f_fitnessCenter_address);


        // ================================= section 2 =================================
        // [ LinearLayout | registeredInfoWrapper ]
        this.registeredInfoWrapper = (LinearLayout) getView().findViewById(R.id.f_fitnessCenter_registeredInfo_wrapper);

        // [ TextView | registeredInfoTitle ]
        this.registeredInfoTitle = (TextView) getView().findViewById(R.id.f_fitnessCenter_registeredInfo_title);

        // [ TextView | memberNumber ]
        this.memberNumber = (TextView) getView().findViewById(R.id.f_fitnessCenter_registeredInfo_memberNumber);

        // [ TextView | contractDate ]
        this.contractDate = (TextView) getView().findViewById(R.id.f_fitnessCenter_registeredInfo_contractDate);

        // [ TextView | expiryDate ]
        this.expiryDate = (TextView) getView().findViewById(R.id.f_fitnessCenter_registeredInfo_expiryDate);

        // [ TextView | attendanceDateCounter ]
        this.attendanceDateCounter = (TextView) getView().findViewById(R.id.f_fitnessCenter_registeredInfo_attendanceDateCount);


        // ================================= section 3 =================================
        // [ LinearLayout | settingInfoWrapper ]
        this.settingInfoWrapper = (LinearLayout) getView().findViewById(R.id.f_fitnessCenter_settingInfo_wrapper);

        // [ TextView | settingInfoTitle ]
        this.settingInfoTitle = (TextView) getView().findViewById(R.id.f_fitnessCenter_settingInfo_title);

        // [ TextView | isAllowedAccessNotification ]
        this.isAllowedAccessNotification = (TextView) getView().findViewById(R.id.f_fitnessCenter_settingInfo_isAllowedAccessNotification);

        // [ TextView | isDisclosed ]
        this.isDisclosed = (TextView) getView().findViewById(R.id.f_fitnessCenter_settingInfo_isDisclosed);


        // [ TextView | registerCancel ]
//        this.registerCancel = (TextView) getView().findViewById(R.id.f_fitnessCenter_registerCancel);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // myFitnessCenter 정보가 있으면
        if (this.myFitnessCenter != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > null 이 아닙니다.");

            // fitness center
            initWidgetOfFitnessCenterSection(
                    myFitnessCenter.getFitnessCenterName(),
                    myFitnessCenter.getThirdAddress()
            );

            // registered info
            initWidgetOfRegisteredInfoSection(
                    myFitnessCenter.getMemberNumber(),
                    myFitnessCenter.getContractDate(),
                    myFitnessCenter.getExpiryDate(),
                    myAttendanceDateList.size(),
                    myAttendanceDateList
            );

            // setting info
            initWidgetOfSettingInfoSection(
                    myFitnessCenter,
                    myAttendanceDateList
            );

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > null 이예요.");

            // fitnessCenterWrapper : FitnessCenterRegisterFragment 로 이동
            fitnessCenterWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 피트니스 센터 등록을 위해 '검색' 화면으로 이동
                    getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_home_content_wrapper, new FitnessCenterSearchFragment())
                            .addToBackStack(FitnessCenterFragment.class.getSimpleName())
                            .commit();
                }
            });
        }
    }


    /**
     * section : fitness center 의 widget init
     *
     * @param fitnessCenterName
     * @param fitnessCenterAddress
     */
    public void initWidgetOfFitnessCenterSection(String fitnessCenterName,
                                                 String fitnessCenterAddress) {

        // 등록한 피트니스 센터 이름과 주소를 표시
        this.fitnessCenterName.setText(fitnessCenterName);
        this.fitnessCenterAddress.setText(fitnessCenterAddress);

        // 다시 등록하지 못하도록 클릭 금지 및 지시 이미지도 감추기기
        this.fitnessCenterWrapper.setClickable(false);
        this.fitnessCenterIndicatorImage.setVisibility(View.GONE);

    }


    /**
     * section : register info 의 widget init
     *
     * @param memberNumber
     * @param contractDate
     * @param expiryDate
     * @param attendanceDateCount
     * @param attendanceDateList
     */
    public void initWidgetOfRegisteredInfoSection(long memberNumber,
                                                  String contractDate,
                                                  String expiryDate,
                                                  int attendanceDateCount,
                                                  ArrayList<Attendance> attendanceDateList) {

        final String METHOD_NAME = "[initWidgetOfRegisteredInfoSection] ";

        // click listener
        this.registeredInfoWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 등록 정보를 수정하기 위해 'FitnessCenterRegisteredInfoFragment' 로 이동
                        getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(
                                        R.id.nav_home_content_wrapper,
                                        FitnessCenterRegisteredInfoFragment.newInstance(myFitnessCenter, myAttendanceDateList)
                                )
                                .addToBackStack(FitnessCenterFragment.class.getSimpleName())
                                .commit();
                    }
                }
        );

        // title 의 drawableEnd 이미지
        this.registeredInfoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.right, 0);

        // text
        this.memberNumber.setText(memberNumber + "");
        this.contractDate.setText(contractDate);
        this.expiryDate.setText(expiryDate);
        this.attendanceDateCounter.setText("+ " + attendanceDateCount + " 일");

    }


    /**
     * section : SettingInfo 의 widget init
     *
     * @param myFitnessCenter
     * @param myAttendanceDateList
     */
    public void initWidgetOfSettingInfoSection(UserFitnessCenter myFitnessCenter, ArrayList<Attendance> myAttendanceDateList) {
        final String METHOD_NAME = "[initWidgetOfSettingInfoSection] ";

        // click listener
        this.settingInfoWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 설정 정보를 수정하기 위해 'FitnessCenterSettingInfoFragment' 로 이동
                        getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(
                                        R.id.nav_home_content_wrapper,
                                        FitnessCenterSettingInfoFragment.newInstance(myFitnessCenter)
                                )
                                .addToBackStack(FitnessCenterFragment.class.getSimpleName())
                                .commit();
                    }
                }
        );

        // title 의 drawableEnd 이미지
        this.settingInfoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.right, 0);

        // ========================================================== is allowed access notification ==========================================================
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<=====================================> isAllowedAccessNotification widget 초기화 ");
        if (myFitnessCenter.getIsAllowedAccessNotification()) {

            isAllowedAccessNotification.setTextColor(ContextCompat.getColor(getFragment().getContext(), R.color.colorTextBlack));
            isAllowedAccessNotification.setText(
                    getFragment().getString(R.string.f_fitnessCenter_settingInfo_isAllowedAccessNotification_true)
            );

        } else {

            isAllowedAccessNotification.setTextColor(ContextCompat.getColor(getFragment().getContext(), R.color.colorTextDefault));
            isAllowedAccessNotification.setText(
                    getFragment().getString(R.string.f_fitnessCenter_settingInfo_isAllowedAccessNotification_false)
            );

        }

        // ========================================================== is disclosed ==========================================================
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<=====================================> isDisclosed widget 초기화 ");
        if (myFitnessCenter.getIsDisclosed()) {

            isDisclosed.setText(
                    getFragment().getString(R.string.f_fitnessCenter_settingInfo_isDisclosed_true)
            );

        } else {

            isDisclosed.setText(
                    getFragment().getString(R.string.f_fitnessCenter_settingInfo_isDisclosed_false)
            );

        }

    }


}
