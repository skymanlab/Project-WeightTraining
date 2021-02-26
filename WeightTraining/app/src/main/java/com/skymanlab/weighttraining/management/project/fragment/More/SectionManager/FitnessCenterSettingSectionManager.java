package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.SettingsActivity;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.FitnessCenter.data.Member;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.FitnessCenterGeofencingManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionUtil;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.HashMap;

public class FitnessCenterSettingSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterSettingSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private UserFitnessCenter myFitnessCenter;

    // instance variable
    private Switch isAllowedAccessNotification;
    private MaterialCardView backgroundLocationPermissionGrantedGo;
    private Switch isDisclosed;

    // constructor
    public FitnessCenterSettingSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setMyFitnessCenter(UserFitnessCenter myFitnessCenter) {
        this.myFitnessCenter = myFitnessCenter;
    }

    @Override
    public void connectWidget() {

        // [ Switch | isAllowedAccessNotification ]
        this.isAllowedAccessNotification = (Switch) getView().findViewById(R.id.f_fitnessCenterSetting_isAllowedAccessNotification);

        // [ MaterialCardView | backgroundLocationPermissionGrantedGo ]
        this.backgroundLocationPermissionGrantedGo = (MaterialCardView) getView().findViewById(R.id.f_fitnessCenterSetting_isAllowedAccessNotification_grantedGo);

        // [ Switch | isDisclosed ]
        this.isDisclosed = (Switch) getView().findViewById(R.id.f_fitnessCenterSetting_isDisclosed);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // 아직 피트니스 센터 등록이 되지 안았을 때는 비활성화 상태로 전환한다.
        if (myFitnessCenter == null) {
            isDisclosed.setEnabled(false);
            isAllowedAccessNotification.setEnabled(false);
            return;
        }

        // init isAllowedAccessNotification
        initWidgetOfIsAllowedAccessNotification();

        // init isDisclosed
        initWidgetOfIsDisclosed();

    }


    /**
     * isAllowedAccessNotification
     */
    private void initWidgetOfIsAllowedAccessNotification() {
        final String METHOD_NAME = "[initWidgetOfIsAllowedAccessNotification] ";

        // 내가 아직 피트니스 센터 등록을 하지 않았을 때는
        if (myFitnessCenter == null) {

            // FitnessCenterFragment 에서 등록 되지 않으면 이 fragment 로 들어오지 못하지만
            return;
        }

        // 백그라운드 권한이 승인되지 않았을 때는
        if (!PermissionUtil.hasPermissionList(getFragment().getContext(), PermissionManager.BACKGROUND_PERMISSION)) {

            // isAllowedAccessNotification 을 사용하지 못 하도록
            isAllowedAccessNotification.setEnabled(false);

            // 사용자가 백그라운드 위치 권한에 대해서 인식하고
            // 백그라운드 위치 권한을 승인하도록 유도하기
            backgroundLocationPermissionGrantedGo.setVisibility(View.VISIBLE);
            backgroundLocationPermissionGrantedGo.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            getFragment().getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            // 백그라운드 위치 권한을 승인하기 위한 화면(SettingsActivity)으로 이동
                            Intent intent = new Intent(getFragment().getContext(), SettingsActivity.class);

                            getFragment().getActivity().startActivity(intent);


                        }
                    }
            );


            return;
        } else {

            isAllowedAccessNotification.setEnabled(true);

            backgroundLocationPermissionGrantedGo.setVisibility(View.GONE);

        }

        // FitnessCenter Geofencing Manager 를 통해 초기화 작업하기
        FitnessCenterGeofencingManager geofencingManager = new FitnessCenterGeofencingManager(
                getFragment().getActivity(),
                new LatLng(myFitnessCenter.getLatitude(), myFitnessCenter.getLongitude())
//                new LatLng(36.138983, 128.333594)
        );
        geofencingManager.init();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< myFitnessCenter > latitude = " + myFitnessCenter.getLatitude());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< myFitnessCenter > latitude = " + myFitnessCenter.getLongitude());

        // is allowed access notification
        isAllowedAccessNotification.setChecked(myFitnessCenter.getIsAllowedAccessNotification());
        isAllowedAccessNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // 경로 : user/나의Uid/fitnessCenter/isAllowedAccessNotification
                StringBuilder path = new StringBuilder()
                        .append(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                        .append("/")
                        .append(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .append("/")
                        .append(User.FITNESS_CENTER)
                        .append("/")
                        .append(UserFitnessCenter.IS_ALLOWED_ACCESS_NOTIFICATION);

                // 데이터 입력
                HashMap<String, Object> updateContent = new HashMap<>();
                updateContent.put(path.toString(), isChecked);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.updateChildren(
                        updateContent,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                // 먼저 데이터베이스에 isAllowedAccessNotification 을 업데이트하고
                                // 업데이트 내용이 데이터베이스에 반영이 완료되면
                                // 어플에 피트니스 센터 지오팬스를 등록하거나 삭제한다.

                                // 그러면 네트워크가 연결 안되면 반영이 안되네????

                                // isChecked 여부에 따라 피트니스 센터 지오펜싱 모니터닝 실행 또는 중지 메소드 실행
                                if (isChecked) {
                                    // 활성화 상태
                                    // Geofencing 을 켠다.
                                    // 데이터베이스의 isAllowedAccessNotification 을 업데이트 한다. / true
                                    geofencingManager.addGeofence();

                                } else {
                                    // 비활성화 상태
                                    // Gofencing 을 종료한다.
                                    // 데이터베이스의 isAllowedAccessNotification 을 업데이트 한다. / false
                                    geofencingManager.removeGeofence();
                                }

                                // FitnessCenterFragment 에서 보낸 myFitnessCenter 의 isAllowedAccessNotification 을 변경하여 '백 버튼'을 눌렀을 때 화면에 변겨된 값이 반영되도록 한다.
                                myFitnessCenter.setIsAllowedAccessNotification(isChecked);
                            }
                        }
                );

            }
        });

    }


    /**
     * Widget : isDisclosed
     */
    private void initWidgetOfIsDisclosed() {
        final String METHOD_NAME = "[initWidgetOfIsDisclosed] ";

        // is disclosed
        isDisclosed.setChecked(myFitnessCenter.getIsDisclosed());
        isDisclosed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (myFitnessCenter != null) {

                    // 1. 경로 : fitnessCenter/주소1/주소2/주소3/memberList/나의회원번호/isDisclosed
                    StringBuilder pathOfFitnessCenter = new StringBuilder()
                            .append(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                            .append("/")
                            .append(myFitnessCenter.getFirstAddress())
                            .append("/")
                            .append(myFitnessCenter.getSecondAddress())
                            .append("/")
                            .append(myFitnessCenter.getThirdAddress())
                            .append("/")
                            .append(FitnessCenter.MEMBER_LIST)
                            .append("/")
                            .append(myFitnessCenter.getMemberNumber())
                            .append("/")
                            .append(Member.IS_DISCLOSED);

                    // 2. 경로 : user/나의uid/fitnessCenter/isDisclosed
                    StringBuilder pathOfMyFitnessCenter = new StringBuilder()
                            .append(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                            .append("/")
                            .append(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .append("/")
                            .append(User.FITNESS_CENTER)
                            .append("/")
                            .append(UserFitnessCenter.IS_DISCLOSED);

                    // 각 경로에 isDisclosed 값(파라미터:isChecked) 를 동시에 업데이트 하기 위한
                    HashMap<String, Object> updateContentList = new HashMap<>();
                    updateContentList.put(pathOfFitnessCenter.toString(), isChecked);
                    updateContentList.put(pathOfMyFitnessCenter.toString(), isChecked);

                    // 데이터베이스 업데이트
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.updateChildren(
                            updateContentList,
                            new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                    if (error != null) {
                                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 에러 발생 > 내용 = " + error.getMessage());
                                        return;
                                    }

                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 업데이트 완료 > isDisclosed 에 대한 업데이트를 완료하였습니다. = " + isChecked);

                                    // FitnessCenterFragment 에서 보낸 myFitnessCenter 의 isDisclosed 을 변경하여 '백 버튼'을 눌렀을 때 화면에 변겨된 값이 반영되도록 한다.
                                    myFitnessCenter.setIsDisclosed(isChecked);

                                }
                            }
                    );


                }
            }
        });
    }

}
