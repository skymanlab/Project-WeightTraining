package com.skymanlab.weighttraining.management.project.ApiManager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

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
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;

public class MyFitnessCenterManager {

    // constant
    private static final String CLASS_NAME = MyFitnessCenterManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private String uid;
    private ContentLoadingProgressBar progressBar;

    // constructor
    public MyFitnessCenterManager(String uid, ContentLoadingProgressBar progressBar) {
        this.uid = uid;
        this.progressBar = progressBar;
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void loadContent(String uid) {
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
                                    ArrayList<String> attendanceDateList = new ArrayList<>();
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

                                    progressBar.setVisibility(View.INVISIBLE);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnMyFitnessCenterManipulationListener {
        void manipulateAttendanceDateList(ArrayList<String> attendanceDateList);
    }

}
