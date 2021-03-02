package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.ApiManager.CalendarManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.MoreFragment;
import com.skymanlab.weighttraining.management.user.data.Attendance;
import com.skymanlab.weighttraining.management.user.data.User;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class FitnessCenterRegisteredInfoSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterRegisterInfoSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private UserFitnessCenter myFitnessCenter;
    private ArrayList<Attendance> myAttendanceDateList;

    // instance variable
    private LinearLayout contractDateWrapper;
    private TextView contractDate;
    private LinearLayout expiryDateWrapper;
    private TextView expiryDate;
    private TextView registerCancel;

    // constructor
    public FitnessCenterRegisteredInfoSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setMyFitnessCenter(UserFitnessCenter myFitnessCenter) {
        this.myFitnessCenter = myFitnessCenter;
    }

    public void setMyAttendanceDateList(ArrayList<Attendance> myAttendanceDateList) {
        this.myAttendanceDateList = myAttendanceDateList;
    }

    // setter
    @Override
    public void connectWidget() {

        // [ LinearLayout | contractDateWrapper ]
        this.contractDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitnessCenterRegisteredInfo_contractDate_wrapper);

        // [ TextView | contractDate ]
        this.contractDate = (TextView) getView().findViewById(R.id.f_fitnessCenterRegisteredInfo_contractDate);

        // [ LinearLayout | expiryDateWrapper ]
        this.expiryDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitnessCenterRegisteredInfo_expiryDate_wrapper);

        // [ TextView | expiryDate ]
        this.expiryDate = (TextView) getView().findViewById(R.id.f_fitnessCenterRegisteredInfo_expiryDate);

        // [ TextView | registerCancel ]
        this.registerCancel = (TextView) getView().findViewById(R.id.f_fitnessCenterRegisteredInfo_registerCancel);

    }

    @Override
    public void initWidget() {

        initWidgetOfContractDate();

        initWidgetOfExpiryDate();

        initWidgetOfRegisterCancel();

    }


    /**
     * widget : contractDate
     */
    private void initWidgetOfContractDate() {

        // text
        this.contractDate.setText(myFitnessCenter.getContractDate());

        // click listener
        this.contractDateWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // myFitnessCenter 의 contractDate 는 특정 문자열 형태이므로
                        // LocalDate 를 이용하여 특정 문자열을 날짜 데이터로 바꾼다.
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
                        LocalDate localDate = LocalDate.parse(myFitnessCenter.getContractDate(), dateTimeFormatter);

                        // (주의) LocalDate 와 Calendar 객체는 날짜 중 Month 를 나타내는 형태가 다므로
                        // LocalDate 의 month : 1-12
                        // Calendar 의 month : 0-11
                        DatePickerDialog dialog = new DatePickerDialog(
                                getFragment().getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                        // 파라미터로 받은 날짜 데이터로 '날짜 형식'으로 변환
                                        String date = transformDateFormat(year, month, dayOfMonth);

                                        // 기존의 contractDate 와 DatePicker 에서 변경한 날짜가 같으면 종료
                                        if (myFitnessCenter.getContractDate().equals(date)) {
                                            Snackbar.make(getView(), R.string.f_fitnessCenterRegisteredInfo_snack_noChangedData, Snackbar.LENGTH_SHORT)
                                                    .show();
                                            return;
                                        }

                                        // 데이터 베이스에 적용
                                        HashMap<String, Object> updateContent = new HashMap<>();
                                        updateContent.put(UserFitnessCenter.CONTRACT_DATE, date);

                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child(User.FITNESS_CENTER)
                                                .updateChildren(
                                                        updateContent,
                                                        new DatabaseReference.CompletionListener() {
                                                            @Override
                                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                                                // 변경된 내용을 myFitnessCenter 에 반영하기
                                                                myFitnessCenter.setContractDate(date);

                                                                // 변경된 내용을 화면에 표시
                                                                contractDate.setText(date);

                                                                // 수정 완료 메시지
                                                                Snackbar.make(getView(), R.string.f_fitnessCenterRegisteredInfo_snack_modificationComplete, Snackbar.LENGTH_SHORT)
                                                                        .show();
                                                            }
                                                        }
                                                );
                                    }
                                },
                                localDate.getYear(),
                                localDate.getMonthValue() - 1,
                                localDate.getDayOfMonth()
                        );
                        dialog.show();
                    }
                }
        );
    }


    /**
     * widget : expiryDate
     */
    private void initWidgetOfExpiryDate() {

        // text
        this.expiryDate.setText(myFitnessCenter.getExpiryDate());

        // click listener
        this.expiryDateWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // myFitnessCenter 의 contractDate 는 특정 문자열 형태이므로
                        // LocalDate 를 이용하여 특정 문자열을 날짜 데이터로 바꾼다.
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
                        LocalDate localDate = LocalDate.parse(myFitnessCenter.getExpiryDate(), dateTimeFormatter);

                        // (주의) LocalDate 와 Calendar 객체는 날짜 중 Month 를 나타내는 형태가 다므로
                        // LocalDate 의 month : 1-12
                        // Calendar 의 month : 0-11
                        DatePickerDialog dialog = new DatePickerDialog(
                                getFragment().getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                        // 파라미터로 받은 날짜 데이터로 '날짜 형식'으로 변환
                                        String date = transformDateFormat(year, month, dayOfMonth);

                                        // 기존의 expiryDate 와 DatePicker 에서 변경한 날짜가 같으면 종료
                                        if (myFitnessCenter.getExpiryDate().equals(date)) {
                                            Snackbar.make(getView(), R.string.f_fitnessCenterRegisteredInfo_snack_noChangedData, Snackbar.LENGTH_SHORT)
                                                    .show();
                                            return;
                                        }

                                        // 데이터 베이스에 적용
                                        HashMap<String, Object> updateContent = new HashMap<>();
                                        updateContent.put(UserFitnessCenter.EXPIRY_DATE, date);

                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child(User.FITNESS_CENTER)
                                                .updateChildren(
                                                        updateContent,
                                                        new DatabaseReference.CompletionListener() {
                                                            @Override
                                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                                                // 변경된 내용을 myFitnessCenter 에 반영하기
                                                                myFitnessCenter.setExpiryDate(date);

                                                                // 변경된 내용을 화면에 표시
                                                                expiryDate.setText(date);

                                                                // 수정 완료 메시지
                                                                Snackbar.make(getView(), R.string.f_fitnessCenterRegisteredInfo_snack_modificationComplete, Snackbar.LENGTH_SHORT)
                                                                        .show();
                                                            }
                                                        }
                                                );
                                    }
                                },
                                localDate.getYear(),
                                localDate.getMonthValue() - 1,
                                localDate.getDayOfMonth()
                        );
                        dialog.show();
                    }
                }
        );
    }


    private void initWidgetOfRegisterCancel() {

        // click listener
        this.registerCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getFragment().getContext());
                        builder.setTitle(R.string.f_fitnessCenterRegisteredInfo_alert_registerCancel_title)
                                .setMessage(R.string.f_fitnessCenterRegisteredInfo_alert_registerCancel_message)
                                .setPositiveButton(
                                        R.string.f_fitnessCenterRegisteredInfo_alert_registerCancel_button_positive,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                // 피트니스 센터 등록 정보 삭제
                                                deleteContent(
                                                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                                        myFitnessCenter.getFirstAddress(),
                                                        myFitnessCenter.getSecondAddress(),
                                                        myFitnessCenter.getThirdAddress(),
                                                        myFitnessCenter.getMemberNumber()
                                                );
                                            }
                                        }
                                )
                                .setNegativeButton(
                                        R.string.f_fitnessCenterRegisteredInfo_alert_registerCancel_button_negative,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }
                                )
                                .show();

                    }
                }
        );

    }


    /**
     * 파라미터로 받은 날짜 데이터를 '날짜 형식'으로 변환하여 반환하기
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    private String transformDateFormat(int year, int month, int dayOfMonth) {

        // 파라미터로 받은 날짜를 calendar 객체로 변환
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        // Date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

        return dateFormat.format(calendar.getTime());

    }


    private void deleteContent(String uid,
                               String firstAddress,
                               String secondAddress,
                               String thirdAddress,
                               long memberNumber) {

        String pathOfUserFitnessCenter = new StringBuilder()
                .append(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                .append("/")
                .append(uid)
                .append("/")
                .append(User.FITNESS_CENTER)
                .toString();

        String pathOfFitnessCenter = new StringBuilder()
                .append(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                .append("/")
                .append(firstAddress)
                .append("/")
                .append(secondAddress)
                .append("/")
                .append(thirdAddress)
                .append("/")
                .append(FitnessCenter.MEMBER_LIST)
                .append("/")
                .append(memberNumber)
                .toString();

        HashMap<String, Object> deleteContent = new HashMap<>();
        deleteContent.put(pathOfUserFitnessCenter, null);
        deleteContent.put(pathOfFitnessCenter, null);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.updateChildren(
                deleteContent,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        if (error != null) {
                            // 사용자에게 알리기 : 데이터베이스 에러로 등록 취소를 실패
                            Snackbar.make(getView(), R.string.f_fitnessCenterRegisteredInfo_snack_registerCancelComplete_databaseError, Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        // 사용자에게 열려주기
                        Snackbar.make(getView(), R.string.f_fitnessCenterRegisteredInfo_snack_registerCancelComplete, Snackbar.LENGTH_SHORT)
                                .show();

                        // 백스택에 있는 moreFragment 로 다시 이동
                        getFragment().getActivity().getSupportFragmentManager()
                                .popBackStack(
                                        MoreFragment.class.getSimpleName(),
                                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                                );

                    }
                }
        );

    }

}
