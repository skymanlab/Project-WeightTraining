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
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > snapshot = " + snapshot);

                                if (snapshot.getValue() == null) {
                                    listener.onNotRegister();
                                    return;
                                }


                                // ======================================================== training ========================================================
                                UserTraining myTraining = snapshot.child(User.TRAINING).getValue(UserTraining.class);

                                if (myTraining != null) {
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "============================================");
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getSquat = " + myTraining.getSquat());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getDeadlift = " + myTraining.getDeadlift());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의트레이닝 > getBenchPress = " + myTraining.getBenchPress());
                                }

                                // ======================================================== fitnessCenter ========================================================
                                UserFitnessCenter myFitnessCenter = snapshot.child(User.FITNESS_CENTER).getValue(UserFitnessCenter.class);

                                ArrayList<Attendance> myAttendanceDateList = new ArrayList<>();

                                if (myFitnessCenter != null) {
                                    // 데이터베이스에 저장된 데이터가 있을 때 ->

                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "---------------------------------------------------------------------------------");
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > attendanceDateList = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > attendanceDateList / getChildren = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren());
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UserFitnessCenter > attendanceDateList / getChildrenCount = " + snapshot.child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildrenCount());

                                    // 출석일 날짜 리스트를 가져온다.
                                    for (DataSnapshot search : snapshot.child(User.FITNESS_CENTER).child(UserFitnessCenter.ATTENDANCE_DATE_LIST).getChildren()) {
                                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 출석 날짜 항목 > search = " + search);
                                        myAttendanceDateList.add(search.getValue(Attendance.class));
                                    }
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

                                listener.onRegisterMyFitnessCenter(myTraining, myFitnessCenter, myAttendanceDateList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnFitnessCenterEventListener {
        void onRegisterMyFitnessCenter(UserTraining userTraining, UserFitnessCenter userFitnessCenter, ArrayList<Attendance> attendanceDateList);

        void onNotRegister();
    }

}
