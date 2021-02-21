package com.skymanlab.weighttraining.management.project.ApiManager;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.FitnessCenter.data.Member;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
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

    // constructor
    public FitnessCenterMemberManager(String uid) {
        this.uid = uid;
    }

    public void init(OnMemberManipulateListener listener) {
        final String METHOD_NAME = "[init] ";

        // Firebase Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                .child(uid)
                .child(User.FITNESS_CENTER)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====================>>>>>>>>>====================>>>>>>>>>====================>>>>>>>>>");
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< DataSnapshot > snapshot = " + snapshot);

                        UserFitnessCenter myFitnessCenter = snapshot.getValue(UserFitnessCenter.class);

                        if (myFitnessCenter == null) {
                            // 피트니스 센터 등록을 하지 않았을 때
                            listener.isNotRegisteredTheFitnessCenter();
                            return;
                        }

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getMemberNumber = " + myFitnessCenter.getMemberNumber());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getFitnessCenterName = " + myFitnessCenter.getFitnessCenterName());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getFirstAddress = " + myFitnessCenter.getFirstAddress());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getSecondAddress = " + myFitnessCenter.getSecondAddress());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getThirdAddress = " + myFitnessCenter.getThirdAddress());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getContractDate = " + myFitnessCenter.getContractDate());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getExpiryDate = " + myFitnessCenter.getExpiryDate());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getIsDisclosed = " + myFitnessCenter.getIsDisclosed());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------< UserFitnessCenter > getIsAllowedAccessNotification = " + myFitnessCenter.getIsAllowedAccessNotification());


                        // 내가 공개상태로 설정하였을 때만
                        if (myFitnessCenter.getIsDisclosed()) {

                            // 피트니스 센터를 등록하였고
                            // 다른 사람에게 나의 등록 여부를 공개하고 싶을 때
                            // 경로( fitnessCenter/주소1/주소2/주소3/memberList ) 에서 모든 회원의 데이터를 가져온다.
                            reference.child(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                                    .child(myFitnessCenter.getFirstAddress())
                                    .child(myFitnessCenter.getSecondAddress())
                                    .child(myFitnessCenter.getThirdAddress())
                                    .child(FitnessCenter.MEMBER_LIST)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----------------------------------");
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=========< DataSnapshot > snapshot = " + snapshot);

                                            // '공개'로 설정된 회원들을 담을 ArrayList
                                            ArrayList<Member> memberArrayList = new ArrayList<>();

                                            // 나의 회원정보가 담긴 Member 객체
                                            Member myMemberData = null;

                                            for (DataSnapshot search : snapshot.getChildren()) {

                                                // 회원들의 정보를 member 객체에 담는다. 그리고 회원 번호도 추가한다.
                                                Member member = search.getValue(Member.class);
                                                member.setMemberNumber(Integer.parseInt(search.getKey()));

                                                // 공개 상태인 멤버만 memberArrayList 에 추가하기 -> 삭제 (이유: HomeSectionFragment 에서 myFi
                                                if (member.getIsDisclosed()) {

                                                    if (myFitnessCenter.getMemberNumber() == member.getMemberNumber()) {
                                                        // 나의 회원 정보는 맨 앞으로
                                                        memberArrayList.add(0, member);
                                                    } else {
                                                        memberArrayList.add(member);
                                                    }
                                                }

                                                // 위에서 생성한 member 데이터에서 memberNumber 가 나의 회원번호가 같으면
                                                // 나의 회원 데이터 객체로....
                                                if (myFitnessCenter.getMemberNumber() == member.getMemberNumber()) {
                                                    myMemberData = member;
                                                }

                                            }

                                            // 내가 공개상태일 때
                                            // 나의 uid 와 myFitnessCenter 정보도 넘겨주고
                                            // 나와 같은 피트니스 센터에 등록한 회원들 중에서 '공개' 상태인 회원의 목록을 넘겨준다.
                                            listener.isDisclosedState(uid, myFitnessCenter, myMemberData, memberArrayList);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        } else {

                            // 피트니스 센터 등록을 하였지만
                            // 다른 사람에게 공개하고 싶지 않을 때
                            listener.isNotIsDisclosedState();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnMemberManipulateListener {
        void isDisclosedState(String myUid, UserFitnessCenter myFitnessCenter, Member myMemberData, ArrayList<Member> memberArrayList);

        void isNotIsDisclosedState();

        void isNotRegisteredTheFitnessCenter();
    }

}
