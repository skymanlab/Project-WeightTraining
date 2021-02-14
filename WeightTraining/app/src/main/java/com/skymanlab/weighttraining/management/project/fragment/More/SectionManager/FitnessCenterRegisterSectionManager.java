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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.FitnessCenter.data.Member;
import com.skymanlab.weighttraining.management.FitnessCenter.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterFragment;
import com.skymanlab.weighttraining.management.user.data.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class FitnessCenterRegisterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterRegisterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private String fitnessCenterName;
    private String fitnessCenterFirstAddress;
    private String fitnessCenterSecondAddress;
    private String fitnessCenterThirdAddress;
    private double fitnessCenterLatitude;
    private double fitnessCenterLongitude;

    // instance variable
    private TextView name;
    private TextView address;
    private LinearLayout contractDateWrapper;
    private TextView contractDate;
    private LinearLayout expiryDateWrapper;
    private TextView expiryDate;

    // constructor
    public FitnessCenterRegisterSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setFitnessCenterName(String fitnessCenterName) {
        this.fitnessCenterName = fitnessCenterName;
    }

    public void setFitnessCenterFirstAddress(String fitnessCenterFirstAddress) {
        this.fitnessCenterFirstAddress = fitnessCenterFirstAddress;
    }

    public void setFitnessCenterSecondAddress(String fitnessCenterSecondAddress) {
        this.fitnessCenterSecondAddress = fitnessCenterSecondAddress;
    }

    public void setFitnessCenterThirdAddress(String fitnessCenterThirdAddress) {
        this.fitnessCenterThirdAddress = fitnessCenterThirdAddress;
    }

    public void setFitnessCenterLatitude(double fitnessCenterLatitude) {
        this.fitnessCenterLatitude = fitnessCenterLatitude;
    }

    public void setFitnessCenterLongitude(double fitnessCenterLongitude) {
        this.fitnessCenterLongitude = fitnessCenterLongitude;
    }

    @Override
    public void connectWidget() {

        // [ TextView | name ]
        this.name = (TextView) getView().findViewById(R.id.f_fitness_center_register_name);

        // [ TextView | address ]
        this.address = (TextView) getView().findViewById(R.id.f_fitness_center_register_address);

        // [LinearLayout] [contractDateWrapper] widget connect
        this.contractDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitness_center_register_contract_date_wrapper);

        // [TextView] [contractDate] widget connect
        this.contractDate = (TextView) getView().findViewById(R.id.f_fitness_center_register_contract_date);

        // [LinearLayout] [expiryDateWrapper] widget connect
        this.expiryDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitness_center_register_expiry_date_wrapper);

        // [TextView] [expiryDate] widget connect
        this.expiryDate = (TextView) getView().findViewById(R.id.f_fitness_center_register_expiry_date);

    }

    @Override
    public void initWidget() {

        // [ TextView | name ] text
        this.name.setText(fitnessCenterName);

        // [ TextView | address ] text
        this.address.setText(fitnessCenterThirdAddress);

        // [LinearLayout] [contractDateWrapper] click listener
        this.contractDateWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 날짜 가져오기
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // [DatePickerDialog] [dialog]
                DatePickerDialog dialog = new DatePickerDialog(getFragment().getContext(), createOnDateSetListener(contractDate), year, month, dayOfMonth);
                dialog.show();
            }
        });


        // [LinearLayout] [expiryDateWrapper] click listener
        this.expiryDateWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 날짜 가져오기
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // [DatePickerDialog] [dialog]
                DatePickerDialog dialog = new DatePickerDialog(getFragment().getContext(), createOnDateSetListener(expiryDate), year, month, dayOfMonth);
                dialog.show();
            }
        });

    }


    public FragmentTopBarManager.EndButtonListener newInstanceOfEndButtonListener() {
        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {

                if (!checkData()) {
                    Snackbar.make(
                            getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar),
                            R.string.f_fitness_center_register_snack_no_selected_date,
                            Snackbar.LENGTH_SHORT
                    ).show();
                    return null;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getFragment().getContext());
                builder.setTitle(R.string.f_fitness_center_register_alert_register_title)
                        .setMessage(R.string.f_fitness_center_register_alert_register_message)
                        .setPositiveButton(R.string.f_fitness_center_register_alert_register_bt_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                saveContent(
                                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                        fitnessCenterName,
                                        fitnessCenterFirstAddress,
                                        fitnessCenterSecondAddress,
                                        fitnessCenterThirdAddress,
                                        fitnessCenterLatitude,
                                        fitnessCenterLongitude,
                                        contractDate.getText().toString(),
                                        expiryDate.getText().toString()
                                );
                            }
                        })
                        .setNegativeButton(R.string.f_fitness_center_register_alert_register_bt_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                return builder.create();
            }
        };
    }


    private DatePickerDialog.OnDateSetListener createOnDateSetListener(TextView date) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // Date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY년 MM월 dd일");

                // Date format setting
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                // TextView 의 setting / text / background
                date.setText(simpleDateFormat.format(calendar.getTime()));
                date.setBackgroundResource(R.color.colorBackgroundWhite);
            }
        };
    }


    private boolean checkData() {

        String contractDateDefault = getFragment().getString(R.string.f_fitness_center_register_contract_date_default);
        String expiryDateDefault = getFragment().getString(R.string.f_fitness_center_register_expiry_date_default);

        String selectedContractDate = contractDate.getText().toString();
        String selectedExpiryDate = expiryDate.getText().toString();

        if (!selectedContractDate.equals(contractDateDefault) && !selectedExpiryDate.equals(expiryDateDefault)) {
            return true;
        } else {
            return false;
        }

    }


    private void saveContent(String userUid,
                             String userName,
                             String fitnessCenterName,
                             String fitnessCenterFirstAddress,
                             String fitnessCenterSecondAddress,
                             String fitnessCenterThirdAddress,
                             double latitude,
                             double longitude,
                             String contractDate,
                             String expiryDate) {
        final String METHOD_NAME = "[saveContent] ";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        // 경로 : fitnessCenter/주소1/주소2/주소3
        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                .child(fitnessCenterFirstAddress)
                .child(fitnessCenterSecondAddress)
                .child(fitnessCenterThirdAddress)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                        // 경로 : fitnessCenter/주소1/주소2/주소3/memberCounter
                        Integer memberCounter = currentData.child(FitnessCenter.MEMBER_COUNTER).getValue(Integer.class);

                        if (memberCounter == null) {
                            memberCounter = 1;
                        } else {
                            memberCounter += 1;
                        }

                        // 경로 fitnessCenter/주소1/주소2/주소3/ 에 저장할 데이터
                        HashMap<String, Object> fitnessCenterData = new HashMap<>();
                        fitnessCenterData.put(FitnessCenter.NAME, fitnessCenterName);                     // 이름
                        fitnessCenterData.put(FitnessCenter.LATITUDE, latitude);             // 위도
                        fitnessCenterData.put(FitnessCenter.LONGITUDE, longitude);           // 경도
                        fitnessCenterData.put(FitnessCenter.MEMBER_COUNTER, memberCounter);  // 회원 카운터

                        // 경로 fitnessCenter/주소1/주소2/주소3/memberList/isDisclosedList 에 저장할 데이터
                        // '회원번호:true' or '회원번호:false'
                        HashMap<String, Object> isDisclosedData = new HashMap<>();
                        isDisclosedData.put(memberCounter + "", true);

                        // 경로 fitnessCenter/주소1/주소2/주소3/memberList/infoList
                        HashMap<String, Object> infoData = new HashMap<>();
                        infoData.put(Member.USER_UID, userUid);
                        infoData.put(Member.USER_NAME, userName);

                        // save
                        currentData.setValue(fitnessCenterData);
                        currentData.child(FitnessCenter.MEMBER_LIST)
                                .child(Member.IS_DISCLOSED_LIST)
                                .setValue(isDisclosedData);
                        currentData.child(FitnessCenter.MEMBER_LIST)
                                .child(Member.INFO_LIST)
                                .child(memberCounter + "")
                                .setValue(infoData);

                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DatabaseError > error = " + error);
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > committed = " + committed);
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > currentData = " + currentData);


                        if (error != null)
                            return;

                        if (committed) {

                            Integer userCounter = currentData.child(FitnessCenter.MEMBER_COUNTER).getValue(Integer.class);
                            String uid = currentData.child(FitnessCenter.MEMBER_LIST)
                                    .child(Member.INFO_LIST)
                                    .child(userCounter.toString())
                                    .child(Member.USER_UID)
                                    .getValue(String.class);

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Integer > userCounter = " + userCounter);
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< String > uid = " + uid);

                            if (userCounter != null) {
                                if (uid.equals(userUid)) {


                                    saveContentOfMyFitnessCenter(reference,
                                            userUid,
                                            fitnessCenterFirstAddress,
                                            fitnessCenterSecondAddress,
                                            fitnessCenterThirdAddress,
                                            fitnessCenterName,
                                            userCounter,
                                            contractDate,
                                            expiryDate);
                                }
                            }

                        }
                    }
                });
    }


    private void saveContentOfMyFitnessCenter(DatabaseReference reference,
                                              String uid,
                                              String firstAddress,
                                              String secondAddress,
                                              String thirdAddress,
                                              String fitnessCenterName,
                                              long memberCounter,
                                              String contractDate,
                                              String expiryDate) {

        // 경로 : user/uid/fitnessCenter
        HashMap<String, Object> myFitnessCenterData = new HashMap<>();
        myFitnessCenterData.put(UserFitnessCenter.FIRST_ADDRESS, firstAddress);
        myFitnessCenterData.put(UserFitnessCenter.SECOND_ADDRESS, secondAddress);
        myFitnessCenterData.put(UserFitnessCenter.THIRD_ADDRESS, thirdAddress);
        myFitnessCenterData.put(UserFitnessCenter.FITNESS_CENTER_NAME, fitnessCenterName);
        myFitnessCenterData.put(UserFitnessCenter.MEMBER_NUMBER, memberCounter);
        myFitnessCenterData.put(UserFitnessCenter.CONTRACT_DATE, contractDate);
        myFitnessCenterData.put(UserFitnessCenter.EXPIRY_DATE, expiryDate);
        myFitnessCenterData.put(UserFitnessCenter.IS_DISCLOSED, true);
        myFitnessCenterData.put(UserFitnessCenter.IS_ALLOWED_ACCESS_NOTIFICATION, true);

        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                .child(uid)
                .child(User.FITNESS_CENTER)
                .setValue(
                        myFitnessCenterData,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                if (error != null)
                                    return;

                                Snackbar.make(
                                        getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar),
                                        R.string.f_fitness_center_register_snack_save_complete,
                                        Snackbar.LENGTH_SHORT
                                ).show();

                                getFragment().getActivity()
                                        .getSupportFragmentManager()
                                        .popBackStack(
                                                FitnessCenterFragment.class.getSimpleName(),
                                                FragmentManager.POP_BACK_STACK_INCLUSIVE
                                        );

                            }
                        }
                );

    }


}
