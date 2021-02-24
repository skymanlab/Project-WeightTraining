package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.location.Address;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;

import java.util.ArrayList;

public class FitnessCenterMarkerManager extends AsyncTask<LatLng, Void, Address> {

    // constant
    private static final String CLASS_NAME = FitnessCenterMarkerManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Activity activity;
    private GoogleMap googleMap;
    private ArrayList<FitnessCenter> fitnessCenterArrayList;

    // constructor
    public FitnessCenterMarkerManager(Activity activity, GoogleMap googleMap, ArrayList<FitnessCenter> fitnessCenterArrayList) {
        super();
        this.activity = activity;
        this.googleMap = googleMap;
        this.fitnessCenterArrayList = fitnessCenterArrayList;
    }

    @Override
    protected Address doInBackground(LatLng... latLngs) {
        final String METHOD_NAME = "[doInBackground] ";

        LatLng position = latLngs[0];

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< LatLng > position = " + position);

        Address address = SearchUtil.searchLAddress(activity, position);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > address = " + address);


        return address;
    }

    @Override
    protected void onPreExecute() {
        // 전 처리
    }

    @Override
    protected void onPostExecute(Address address) {
        final String METHOD_NAME = "[doInBackground] ";
        // 후 처리 : UI

        // SearchUtil 을 통해 모종의 이유로 Address 객체를 가져오지 못 하였을 때는 중지한다.
        if (address == null) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > SearchUtil 을 통해 객체를 가져오지 못 하였습니다. 그래서 데이터 베이스에서 가져오지 않습니다.");
            return;
        }

        // SearchUtil 을 사용하여 address 에서 firstAddress 와 secondAddress 가져오기
        String firstAddress = SearchUtil.getFirstAddress(address);
        String secondAddress = SearchUtil.getSecondAddress(address);

        // Address 에서 구한 firstAddress 와 secondAddress 로 내가 사는 지역에 등록된 피트니스 센터를 리스트로 만들어서 가져온다.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                .child(firstAddress)
                .child(secondAddress)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > snapshot = " + snapshot);

                        // 해당 지역('시'나 '구') 에 등록되어 있는 피트니스 센터를 모두 가져온다.
                        for (DataSnapshot search : snapshot.getChildren()) {

                            // 피트니스 센터 데이터를 가져온다.
                            FitnessCenter fitnessCenter = search.getValue(FitnessCenter.class);
                            fitnessCenter.setFirstAddress(firstAddress);                    // firstAddress
                            fitnessCenter.setSecondAddress(secondAddress);                  // secondAddress
                            fitnessCenter.setThirdAddress(search.getKey());                 // thirdAddress : key 가 주소3 이다.

                            if (fitnessCenter != null) {

                                // 피트니스 센터에 등록된 회원의 수를 표시하기 위해서
                                long counter = snapshot
                                        .child(search.getKey())
                                        .child(FitnessCenter.MEMBER_LIST)
                                        .getChildrenCount();

                                // googleMap 에 마커 추가
                                googleMap.addMarker(
                                        LocationUpdateUtil.createMarkerOptions(
                                                fitnessCenter.getLatitude(),
                                                fitnessCenter.getLongitude(),
                                                fitnessCenter.getName() + "/" + counter + "명",
                                                fitnessCenter.getThirdAddress()
                                        )
                                ).showInfoWindow();

                                fitnessCenterArrayList.add(fitnessCenter);

                            }

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "======================================== 데이터베이스 참조 결과 ========================================");
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > key = " + search.getKey());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > search = " + search.getValue());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "======================================== FitnessCenter ========================================");

                            if (fitnessCenter != null) {

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > fitnessCenter = " + fitnessCenter);
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getName = " + fitnessCenter.getName());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getThirdAddress = " + fitnessCenter.getThirdAddress());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLatitude = " + fitnessCenter.getLatitude());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLongitude = " + fitnessCenter.getLongitude());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getMemberCounter = " + fitnessCenter.getMemberCounter());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > member list 의 현재 등록 된 회원 수 = " + snapshot.child(search.getKey()).child(FitnessCenter.MEMBER_LIST).getChildrenCount());

                            }
                        }

                        if (0 < fitnessCenterArrayList.size()) {
                            Snackbar.make(activity.findViewById(R.id.nav_home_bottom_bar), R.string.etc_fitness_center_marker_manager_snack_fitnessCenterArrayList_not_zero_size, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


}
