package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterRegisterInfoFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSettingFragment;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;

public class FitnessCenterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private UserFitnessCenter myFitnessCenter;
    private ArrayList<Attendance> myAttendanceDateList;

    // instance variable
    private FrameLayout goRegister;
    private ImageView goRegisterImage;
    private TextView fitnessCenterName;
    private TextView fitnessCenterAddress;

    // instance variable
    private TextView registerInfoTitle;
    private TextView memberNumber;
    private TextView contractDate;
    private TextView expiryDate;
    private TextView attendanceDateCounter;

    // instance variable
    private TextView settingInfoTitle;
    private TextView isAllowedAccessNotification;
    private TextView isDisclosed;

    // instance variable
    private ArrayList<String> attendanceDateList;

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

        // [FrameLayout] [goRegister] widget connect
        this.goRegister = (FrameLayout) getView().findViewById(R.id.f_fitness_center_go_register);

        // [ ImageView | goRegisterImage ]
        this.goRegisterImage = (ImageView) getView().findViewById(R.id.f_fitness_center_go_register_image);

        // [TextView] [fitnessCenterName] widget connect
        this.fitnessCenterName = (TextView) getView().findViewById(R.id.f_fitness_center_name);

        // [TextView] [fitnessCenterAddress] widget connect
        this.fitnessCenterAddress = (TextView) getView().findViewById(R.id.f_fitness_center_address);


        // [ TextView | registerInfoTitle ]
        this.registerInfoTitle = (TextView) getView().findViewById(R.id.f_fitness_center_registerInfo_title);

        // [ TextView | memberNumber ]
        this.memberNumber = (TextView) getView().findViewById(R.id.f_fitness_center_member_number);

        // [ TextView | contractDate ]
        this.contractDate = (TextView) getView().findViewById(R.id.f_fitness_center_contract_date);

        // [ TextView | expiryDate ]
        this.expiryDate = (TextView) getView().findViewById(R.id.f_fitness_center_expiry_date);

        // [ TextView | attendanceDateCounter ]
        this.attendanceDateCounter = (TextView) getView().findViewById(R.id.f_fitness_center_attendance_date_counter);


        // [ TextView | settingInfoTitle ]
        this.settingInfoTitle = (TextView) getView().findViewById(R.id.f_fitness_center_settingInfo_title);

        // [ TextView | isAllowedAccessNotification ]
        this.isAllowedAccessNotification = (TextView) getView().findViewById(R.id.f_fitness_center_setting_info_is_allowed_access_notification);

        // [ TextView | isDisclosed ]
        this.isDisclosed = (TextView) getView().findViewById(R.id.f_fitness_center_setting_info_is_disclosed);

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

            // register info
            initWidgetOfRegisterInfoSection(
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

            // goRegister click listener : FitnessCenterRegisterFragment 로 이동
            goRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_home_content_wrapper, new FitnessCenterSearchFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }


    public void initWidgetOfFitnessCenterSection(String fitnessCenterName,
                                                 String fitnessCenterAddress) {

        // text
        this.fitnessCenterName.setText(fitnessCenterName);
        this.fitnessCenterAddress.setText(fitnessCenterAddress);

        //
        this.goRegister.setClickable(false);
        this.goRegisterImage.setVisibility(View.GONE);

    }


    public void initWidgetOfRegisterInfoSection(long memberNumber,
                                                String contractDate,
                                                String expiryDate,
                                                int attendanceDateCount,
                                                ArrayList<Attendance> attendanceDateList) {

        final String METHOD_NAME = "[initWidgetOfRegisterInfoSection] ";


        // click listener
        this.registerInfoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.right, 0);
        this.registerInfoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================ zmfflr");
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< String > contractDate = " + contractDate);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< String > expiryDate = " + expiryDate);

                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.nav_home_content_wrapper,
                                FitnessCenterRegisterInfoFragment.newInstance(myFitnessCenter, myAttendanceDateList)
                        )
                        .addToBackStack(null)
                        .commit();

            }
        });

        // text
        this.memberNumber.setText(memberNumber + "");
        this.contractDate.setText(contractDate);
        this.expiryDate.setText(expiryDate);
        this.attendanceDateCounter.setText("+ " + attendanceDateCount + " 일");

    }

    public void initWidgetOfSettingInfoSection(UserFitnessCenter myFitnessCenter, ArrayList<Attendance> myAttendanceDateList) {
        final String METHOD_NAME = "[initWidgetOfSettingInfoSection] ";

        // settingInfoTitle : click listener 및 drawableEnd
        this.settingInfoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.right, 0);
        this.settingInfoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================ 12312313");

                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.nav_home_content_wrapper,
                                FitnessCenterSettingFragment.newInstance(myFitnessCenter)
                        )
                        .addToBackStack(null)
                        .commit();
            }
        });

        // ========================================================== is allowed access notification ==========================================================
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<=====================================> isAllowedAccessNotification widget 초기화 ");
        if (myFitnessCenter.getIsAllowedAccessNotification()) {

            isAllowedAccessNotification.setTextColor(ContextCompat.getColor(getFragment().getContext(), R.color.colorTextBlack));
            isAllowedAccessNotification.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_allowed_access_notification_true)
            );

        } else {

            isAllowedAccessNotification.setTextColor(ContextCompat.getColor(getFragment().getContext(), R.color.colorTextDefault));
            isAllowedAccessNotification.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_allowed_access_notification_false)
            );

        }


        // ========================================================== is disclosed ==========================================================
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<=====================================> isDisclosed widget 초기화 ");
        if (myFitnessCenter.getIsDisclosed()) {

            isDisclosed.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_disclosed_true)
            );

        } else {

            isDisclosed.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_disclosed_false)
            );

        }

    }

}
