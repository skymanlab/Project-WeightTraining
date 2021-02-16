package com.skymanlab.weighttraining.management.project.ApiManager;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.FitnessCenter.data.Member;
import com.skymanlab.weighttraining.management.FitnessCenter.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;

public class FitnessCenterMemberManager {

    // constant
    private static final String CLASS_NAME = FitnessCenterMemberManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private String uid;

    // instance variable
//    private ArrayList<Member> memberArrayList;

    // constructor
    public FitnessCenterMemberManager(String uid) {
        this.uid = uid;
    }

    public void init(OnMemberManipulateListener listener) {
        final String METHOD_NAME = "[init] ";

//        memberArrayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                .child(uid)
                .child(User.FITNESS_CENTER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< DataSnapshot > snapshot = " + snapshot);

                        UserFitnessCenter userFitnessCenter = snapshot.getValue(UserFitnessCenter.class);

                        if (userFitnessCenter != null) {

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getMemberNumber = " + userFitnessCenter.getMemberNumber());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getFitnessCenterName = " + userFitnessCenter.getFitnessCenterName());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getFirstAddress = " + userFitnessCenter.getFirstAddress());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getSecondAddress = " + userFitnessCenter.getSecondAddress());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getThirdAddress = " + userFitnessCenter.getThirdAddress());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getContractDate = " + userFitnessCenter.getContractDate());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getExpiryDate = " + userFitnessCenter.getExpiryDate());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getIsDisclosed = " + userFitnessCenter.getIsDisclosed());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getIsAllowedAccessNotification = " + userFitnessCenter.getIsAllowedAccessNotification());


                            // 유저가 공개 하였을 때만
                            if (userFitnessCenter.getIsDisclosed()) {

                                reference.child(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                                        .child(userFitnessCenter.getFirstAddress())
                                        .child(userFitnessCenter.getSecondAddress())
                                        .child(userFitnessCenter.getThirdAddress())
                                        .child(FitnessCenter.MEMBER_LIST)
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=========< DataSnapshot > snapshot = " + snapshot);

                                                // 데이터가 변경
                                                ArrayList<Member> memberArrayList = new ArrayList<>();

                                                for (DataSnapshot search : snapshot.getChildren()) {
                                                    Member member = search.getValue(Member.class);
                                                    member.setMemberNumber(Integer.parseInt(search.getKey()));

                                                    memberArrayList.add(member);
                                                }
                                                listener.manipulateMemberList(memberArrayList);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnMemberManipulateListener {
        void manipulateMemberList(ArrayList<Member> memberArrayList);
    }

}
