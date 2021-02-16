package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;

public class FitnessCenterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FrameLayout goRegister;
    private ImageView goRegisterImage;
    private TextView fitnessCenterName;
    private TextView fitnessCenterAddress;
    private ContentLoadingProgressBar progressBar;

    // instance variable
    private TextView registerInfoTitle;
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

        // [ ContentLoadingProgressBar | progressBar ]
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_fitness_center_progress_bar);


        // [ TextView | registerInfoTitle ]
        this.registerInfoTitle = (TextView) getView().findViewById(R.id.f_fitness_center_registerInfo_title);

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

        // 경로 user/uid/fitnessCenter 항목을 읽어오기
        loadContent(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

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


    public void initWidgetOfRegisterInfoSection(String contractDate,
                                                String expiryDate,
                                                int attendanceDateCount) {

        final String METHOD_NAME = "[initWidgetOfRegisterInfoSection] ";

        this.registerInfoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.right, 0);
        this.registerInfoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================ zmfflr");
            }
        });

        // text
        this.contractDate.setText(contractDate);
        this.expiryDate.setText(expiryDate);
        this.attendanceDateCounter.setText(attendanceDateCount + " 일");

    }

    public void initWidgetOfSettingInfoSection(boolean isAllowedAccessNotification,
                                               boolean isDisclosed) {

        final String METHOD_NAME = "[initWidgetOfSettingInfoSection] ";

        this.settingInfoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.right, 0);
        this.settingInfoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================ 12312313");
            }
        });

        // is allowed access notification
        if (isAllowedAccessNotification) {
            this.isAllowedAccessNotification.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_allowed_access_notification_true)
            );
        } else {
            this.isAllowedAccessNotification.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_allowed_access_notification_false)
            );
        }

        // is disclosed
        if (isDisclosed) {
            this.isDisclosed.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_disclosed_true)
            );
        } else {
            this.isDisclosed.setText(
                    getFragment().getString(R.string.f_fitness_center_setting_info_is_disclosed_false)
            );
        }

    }


    private void loadContent(String uid) {
        final String METHOD_NAME = "[loadContent] ";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                .child(uid)
                .child(User.FITNESS_CENTER)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > snapshot = " + snapshot);

                                if (snapshot.getValue() == null) {
                                    // 데이터베이스에 저장된 데이터가 없을 때 -> 

                                    // goRegister click listener : FitnessCenterRegisterFragment 로 이동
                                    goRegister.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.nav_home_content_wrapper, new FitnessCenterSearchFragment())
                                                    .addToBackStack(FitnessCenterFragment.class.getSimpleName())
                                                    .commit();
                                        }
                                    });
                                    
                                    // progress bar 숨기기
                                    progressBar.setVisibility(View.INVISIBLE);
                                    return;
                                }

                                UserFitnessCenter userFitnessCenter = snapshot.getValue(UserFitnessCenter.class);

                                if (userFitnessCenter != null) {
                                    // 데이터베이스에 저장된 데이터가 있을 때 ->

                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------------------------------------------------------");
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > attendanceDateList = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST));
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > attendanceDateList / getChildren = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > attendanceDateList / getChildrenCount = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildrenCount());

                                    // 출석일 날짜 리스트를 가져온다.
                                    attendanceDateList = new ArrayList<>();
                                    for (DataSnapshot search : snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren()) {
                                        attendanceDateList.add(search.getValue(String.class));
                                    }
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===============");

                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getFirstAddress = " + userFitnessCenter.getFirstAddress());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getSecondAddress = " + userFitnessCenter.getSecondAddress());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getThirdAddress = " + userFitnessCenter.getThirdAddress());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter >  = " + userFitnessCenter.getMemberNumber());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getContractDate = " + userFitnessCenter.getContractDate());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getExpiryDate = " + userFitnessCenter.getExpiryDate());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > isAllowedAccessNotification = " + userFitnessCenter.getIsAllowedAccessNotification());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter >  = " + userFitnessCenter.getIsDisclosed());

                                    for (int index = 0; index < attendanceDateList.size(); index++) {
                                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > date = " + attendanceDateList.get(index));
                                    }

                                    // fitness center
                                    initWidgetOfFitnessCenterSection(userFitnessCenter.getFitnessCenterName(), userFitnessCenter.getThirdAddress());

                                    //  register info
                                    initWidgetOfRegisterInfoSection(userFitnessCenter.getContractDate(), userFitnessCenter.getExpiryDate(), attendanceDateList.size());

                                    // setting info
                                    initWidgetOfSettingInfoSection(userFitnessCenter.getIsAllowedAccessNotification(), userFitnessCenter.getIsDisclosed());

                                    progressBar.setVisibility(View.INVISIBLE);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );
    }


}
