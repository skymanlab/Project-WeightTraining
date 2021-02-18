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

        if (address == null){
            return;
        }

        String firstAddress = SearchUtil.getFirstAddress(address);
        String secondAddress = SearchUtil.getSecondAddress(address);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< String > firstAddress = " + firstAddress);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< String > secondAddress = " + secondAddress);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                .child(firstAddress)
                .child(secondAddress)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > snapshot = " + snapshot);

                        for (DataSnapshot search : snapshot.getChildren()) {

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > key = " + search.getKey());
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > search = " + search.getValue());

                            FitnessCenter fitnessCenter = search.getValue(FitnessCenter.class);
                            fitnessCenter.setFirstAddress(firstAddress);
                            fitnessCenter.setSecondAddress(secondAddress);
                            fitnessCenter.setThirdAddress(search.getKey());

                            if (fitnessCenter != null) {
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > fitnessCenter = " + fitnessCenter);

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getName = " + fitnessCenter.getName());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getThirdAddress = " + fitnessCenter.getThirdAddress());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLatitude = " + fitnessCenter.getLatitude());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLongitude = " + fitnessCenter.getLongitude());
                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getMemberCounter = " + fitnessCenter.getMemberCounter());

                                // googleMap 에 마커 추가
                                googleMap.addMarker(
                                        LocationUpdateUtil.createMarkerOptions(
                                                fitnessCenter.getLatitude(),
                                                fitnessCenter.getLongitude(),
                                                fitnessCenter.getName(),
                                                fitnessCenter.getThirdAddress()
                                        )
                                ).showInfoWindow();

                                fitnessCenterArrayList.add(fitnessCenter);

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

    @Override
    protected void onCancelled() {
        final String METHOD_NAME= "[onCancelled] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================ AsyncTask 가 종료 되었습니다.");
        super.onCancelled();
    }

    @Override
    protected void onCancelled(Address address) {
        final String METHOD_NAME= "[onCancelled] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================ AsyncTask 가 종료 되었습니다.========================");
        super.onCancelled(address);
    }
}
