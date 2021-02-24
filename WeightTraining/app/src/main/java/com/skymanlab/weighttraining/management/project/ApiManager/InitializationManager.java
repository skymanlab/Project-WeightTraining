package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.BaseEventDataManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.HashMap;

public class InitializationManager {

    // constant
    public static final String CLASS_NAME = InitializationManager.class.getSimpleName();
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Context context;

    // constructor
    public InitializationManager(Context context) {
        this.context = context;
    }

    public void init() {
        final String METHOD_NAME = "[init] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "========================================================?");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            // 인증받지 않았으므로 종료
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, " ============= > 인증받지 않았으므로 종료");
            return;
        }

        if (SettingsManager.checkIsInitialInstallation(context)) {
            // 최초 설치 아님 -> 설정 userNumber 와 isSavedBaseEvent 의 값에 따른 설정값 재등록
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 최초 설치 > 최초 설치가 아닙니다.");

            // userNumber
            checkSettingOfUserNumber(context, firebaseUser.getUid());

            // isSavedBaseEvent
            checkSettingOfIsSavedBaseEvent(context, firebaseUser.getUid());

        } else {
            // 최초 설치 완료 후 데이터 등록
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 최초 설치 > 최초 설치후 기본 데이터를 저장합니다.");

            // 경로(user/uid) 에 유저 정보 저장 -> userNumber 등록 -> 경로(event/uid) 에 기존 종목 데이터 저장 -> isSavedBaseEvent 를 true 로 변경 -> isInitialInstallation 을 true 로 변경
            saveInitialData(
                    context,
                    firebaseUser.getUid(),
                    firebaseUser.getDisplayName(),
                    firebaseUser.getEmail()
            );

        }


    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [1] Firebase Database =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void saveInitialData(Context context, String uid, String userName, String userEmail) {
        final String METHOD_NAME = "[saveInitialData] ";

        DatabaseReference reference = FirebaseDatabase
                .getInstance()
                .getReference(FirebaseConstants.DATABASE_FIRST_NODE_USER);

        reference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-----------------------------------------------------------------------------------------------");
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< MutableData > currentData = " + currentData);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< MutableData > currentData.getValue() = " + currentData.getValue());

                // user 데이터
                User user = currentData
                        .child(uid)
                        .getValue(User.class);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< User > user = " + user);

                // userCounter
                Integer userCounter = currentData
                        .child(FirebaseConstants.DATABASE_NODE_USER_COUNTER_OF_USER)
                        .getValue(Integer.class);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Integer > userCounter = " + userCounter);

                if (user == null) {

                    if (userCounter == null) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< userCounter > null");
                        // userCount
                        userCounter = 1;

                    } else {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< userCounter > null 아님");
                        // userCount
                        userCounter += 1;
                    }

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 유저 카운트 > 넘버 = " + userCounter);
                    // 2. user 데이터 생성
                    HashMap<String, Object> userValue = new HashMap<>();
                    userValue.put(User.NUMBER, userCounter);
                    userValue.put(User.NAME, userName);
                    userValue.put(User.EMAIL, userEmail);
                    userValue.put(User.IS_SAVED_BASE_EVENT, false);

                    // 저장
                    currentData
                            .child(FirebaseConstants.DATABASE_NODE_USER_COUNTER_OF_USER).setValue(userCounter);      // 경로 : user/userCounter
                    currentData
                            .child(uid).setValue(userValue);                                                     // 경로 : user/uid

                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-----------------------------------------------------------------------------------------------");
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DatabaseError > error = " + error);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > committed = " + committed);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > currentData = " + currentData);

                if (error != null) {
                    // 에러 발생
                    return;
                }

                if (committed) {

                    Integer number = currentData.child(uid).child(User.NUMBER).getValue(Integer.class);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Integer > userNumber = " + number);

                    if (number != null) {
                        if (0 < number) {
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Integer > userNumber : 0 보다 큽니다.");
                            // 설정에서 key:userNumber 확인
                            if (!SettingsManager.checkUserNumber(context)) {

                                // 설정의 userNumber 항목을 number 로 수정
                                SettingsManager.setUserNumber(
                                        context,
                                        number
                                );
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Integer > userNumber : Setting - userNumber 에 번호를 등록함 ");
                            }
                        }
                    }

                    Boolean isSavedBaseEvent = currentData.child(uid).child(User.IS_SAVED_BASE_EVENT).getValue(Boolean.class);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Boolean > isSavedBaseEvent = " + isSavedBaseEvent);

                    if (isSavedBaseEvent != null) {
                        if (!isSavedBaseEvent) {

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<Boolean > isSavedBaseEvent : false 라서 baseEvent 를 저장하러 갑니다.");

                            // [lv/C]BaseEventDataManager : 기본 종목 데이터를 생성하고
                            BaseEventDataManager baseEventDataManager = new BaseEventDataManager(uid);
                            baseEventDataManager.init();
                            baseEventDataManager.saveContent(
                                    new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====================================== Base Event manager onComplete ===========================================");
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DatabaseError > error = " + error);
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DatabaseReference > ref = " + ref);

                                            // 에러 발생시 중지
                                            if (error != null) {
                                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< onComplete > 입력 성공");
                                                return;
                                            }

                                            // 경로(user/uid/isSavedBaseEvent) 을 false 를 true 로 변경
                                            currentData.getRef()
                                                    .child(uid)
                                                    .child(User.IS_SAVED_BASE_EVENT)
                                                    .setValue(true);

                                            // 설정의 isSavedBaseEvent 항목을 false 에서 true 로 변경
                                            SettingsManager.setIsSavedBaseEvent(context, true);
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< isSavedBaseEvent > 기본 종목 데이터가 저장되었고 그에 따라 설정에 isSavedBaseEvent 도 true 로 변경하였습니다.");

                                            // 설정의 isInitialInstallation 항목을 false 에서 true 로 변경
                                            SettingsManager.setIsInitialInstallation(context, true);
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< isInitialInstallation > 최초 설치에 따른 기본 데이터를 모두 저장하였습니다. 그에 따라 설정 isInitialInstallation 도 true 로 변경하였습니다.");
                                        }
                                    });
                        } else {

                            if (!SettingsManager.checkIsSavedBaseEvent(context)) {
                                // isSavedBaseEvent = true
                                SettingsManager.setIsSavedBaseEvent(context, true);
                                // isInitialInstallation = true
                                SettingsManager.setIsInitialInstallation(context, true);
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Boolean > Setting 에 isSavedBaseEvent, isInitialInstallation 을 true 로 변경");
                            }
                        }
                    }
                }
            }
        });

    } // End of method [saveUser]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= [2] SharedPreference   =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void checkSettingOfUserNumber(Context context, String uid) {
        final String METHOD_NAME = "[checkSettingOfUserNumber] ";

        if (SettingsManager.checkUserNumber(context)) {
            // 이미 userNumber 가 등록 되었으므로 확인 불 필요!
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Setting > userNumber 가 등록되어 있습니다.");

        } else {
            // 최초 설치가 아닌데 등록되어 있는 userNumber 가 없으므로, 데이터베이스를 다시 확인한다.
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Setting > userNumber 가 등록되어 있지 않습니다. 데이터베이스를 확인합니다.");
            DatabaseReference reference = FirebaseDatabase
                    .getInstance()
                    .getReference(FirebaseConstants.DATABASE_FIRST_NODE_USER);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Integer userNumber = snapshot
                            .child(uid)
                            .child(User.NUMBER)
                            .getValue(Integer.class);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Integer > userNumber = " + userNumber);

                    if (userNumber != null) {
                        if (0 < userNumber) {

                            // 설정의 userNumber 를 해당 userNumber 로 등록한다.
                            SettingsManager.setUserNumber(context, userNumber);
                        }
                    } else {

                        // 데이터베이스에 저장되어 있는 데이터가 없으므로 saveInitialData 다시 수행

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    public static void checkSettingOfIsSavedBaseEvent(Context context, String uid) {
        final String METHOD_NAME = "[checkSettingOfUserNumber] ";

        if (SettingsManager.checkIsSavedBaseEvent(context)) {
            // 이미 isSavedBaseEvent 가 등록 되었으므로 확인 불 필요!
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Setting > isSavedBaseEvent 가 등록되어 있습니다.");

        } else {
            // 최초 설치가 아닌데 등록되어 있는 isSavedBaseEvent 가 없으므로, 데이터베이스를 다시 확인한다.
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Setting > isSavedBaseEvent 가 등록되어 있지 않습니다. 데이터베이스를 확인합니다.");

            DatabaseReference reference = FirebaseDatabase
                    .getInstance()
                    .getReference(FirebaseConstants.DATABASE_FIRST_NODE_USER);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Boolean isSavedBaseEvent = snapshot
                            .child(uid)
                            .child(User.IS_SAVED_BASE_EVENT)
                            .getValue(Boolean.class);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Boolean > isSavedBaseEvent = " + isSavedBaseEvent);

                    if (isSavedBaseEvent != null) {
                        if (isSavedBaseEvent) {

                            // Setting : isSavedBaseEvent = true
                            SettingsManager.setIsSavedBaseEvent(context, true);
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Boolean > isSavedBaseEvent 을 Setting 에 등록");
                        }

                    } else {

                        // 데이터베이스에 저장되어 있는 데이터가 없으므로 saveInitialData 다시 수행

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

}
