package com.skymanlab.weighttraining.management.project.ApiManager;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.user.data.User;
import com.skymanlab.weighttraining.management.user.data.UserTraining;

import java.util.ArrayList;

public class MyUserDataManager {

    // constant
    private static final String CLASS_NAME = MyUserDataManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private String uid;

    // constructor
    public MyUserDataManager(String uid) {
        this.uid = uid;
    }

    public void loadContent(OnFitnessCenterEventListener listener) {
        final String METHOD_NAME = "[loadContent] ";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                .child(uid)
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.getValue() == null) {
                                    // 피트니스 센터가 등록되지 않았을 때
                                    // 리스너를 통해 widget 을 초기화 한다.
                                    listener.onNotRegister();
                                    return;
                                }

                                // ======================================================== UserTraining ========================================================
                                UserTraining myTraining = snapshot.child(User.TRAINING).getValue(UserTraining.class);

                                // ======================================================== UserFitnessCenter ========================================================
                                // UserFitnessCenter
                                UserFitnessCenter myFitnessCenter = snapshot.child(User.FITNESS_CENTER).getValue(UserFitnessCenter.class);

                                // ======================================================== Attendance List ========================================================
                                // Attendance List
                                ArrayList<Attendance> myAttendanceDateList = new ArrayList<>();

                                // 등록된 피트니스 센터가 있을 때
                                // 출석한 날짜를 가져와서 추가한다.
                                if (myFitnessCenter != null) {

                                    for (DataSnapshot search : snapshot.child(User.FITNESS_CENTER).child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren()) {
                                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 출석 날짜 항목 > search = " + search);
                                        myAttendanceDateList.add(search.getValue(Attendance.class));
                                    }

                                }

                                // 리스너를 통해 가져온 데이터 넘기기
                                listener.onRegisterMyFitnessCenter(
                                        myTraining, 
                                        myFitnessCenter, 
                                        myAttendanceDateList
                                );


                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------------------------------------------------------");
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > snapshot = " + snapshot);

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------------------------------- child : training ------------------------------------------");
                                if (myTraining != null) {
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getSquat = " + myTraining.getSquat());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getDeadlift = " + myTraining.getDeadlift());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getBenchPress = " + myTraining.getBenchPress());
                                }

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------------------------------- child : fitnessCenter ------------------------------------------");
                                if (myFitnessCenter != null) {
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getFitnessCenterName = " + myFitnessCenter.getFitnessCenterName());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getMemberNumber = " + myFitnessCenter.getMemberNumber());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getFirstAddress = " + myFitnessCenter.getFirstAddress());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getSecondAddress = " + myFitnessCenter.getSecondAddress());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getThirdAddress = " + myFitnessCenter.getThirdAddress());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getContractDate = " + myFitnessCenter.getContractDate());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getExpiryDate = " + myFitnessCenter.getExpiryDate());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getLatitude = " + myFitnessCenter.getLatitude());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getLongitude = " + myFitnessCenter.getLongitude());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getIsAllowedAccessNotification = " + myFitnessCenter.getIsAllowedAccessNotification());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > getIsDisclosed = " + myFitnessCenter.getIsDisclosed());
                                }

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------------------------------- child : attendanceDateList ------------------------------------------");
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 출석날짜리스트 > snapshot = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 출석날짜리스트 > snapshot / getChildren = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 출석날짜리스트 > snapshot / getChildrenCount = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildrenCount());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 에러 > 코드번호 = " + error.getCode());
                            }
                        }
                );

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnFitnessCenterEventListener {
        void onRegisterMyFitnessCenter(UserTraining userTraining, UserFitnessCenter userFitnessCenter, ArrayList<Attendance> attendanceDateList);

        void onNotRegister();
    }

}
