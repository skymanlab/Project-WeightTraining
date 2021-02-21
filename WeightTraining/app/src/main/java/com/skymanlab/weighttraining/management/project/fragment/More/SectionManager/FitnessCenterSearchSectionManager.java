package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.GoogleMapManager;
import com.skymanlab.weighttraining.management.project.ApiManager.LocationUpdateManager;
import com.skymanlab.weighttraining.management.project.ApiManager.LocationUpdateUtil;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionUtil;
import com.skymanlab.weighttraining.management.project.ApiManager.SearchUtil;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterRegisterFragment;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterSearchFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FitnessCenterSearchSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterSearchSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : google map api
    private GoogleMap gMap;
    private GoogleMapManager googleMapManager;

    // instance variable
    private SearchView addressSearchView;
    private TextView searchedAddress;

    // instance variable
    private FitnessCenter fitnessCenter = null;
    private ArrayList<FitnessCenter> fitnessCenterArrayList = null;

    // constructor
    public FitnessCenterSearchSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // getter
    public GoogleMapManager getGoogleMapManager() {
        return googleMapManager;
    }

    @Override
    public void connectWidget() {

        // [SearchView] [addressSearchView]
        this.addressSearchView = (SearchView) getView().findViewById(R.id.f_fitness_center_search_search_view);

        // [TextView] [searchedAddress] widget connect
        this.searchedAddress = (TextView) getView().findViewById(R.id.f_fitness_center_search_address);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // google map api : 구글 맵 가져오기
        SupportMapFragment mapFragment = (SupportMapFragment) getFragment().getChildFragmentManager().findFragmentById(R.id.f_fitness_center_search_map);
        mapFragment.getMapAsync(createOnMapReadyCallback());

        // [SearchView] [addressSearchView]
        this.addressSearchView.setFocusable(true);
        this.addressSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< query text submit > query = " + query);

                if (gMap != null) {

                    if (PermissionUtil.hasPermissionList(getFragment().getActivity(), PermissionManager.LOCATION_PERMISSION_LIST)) {

                        SearchUtil.searchAddress(
                                getFragment().getActivity(),
                                query,
                                new SearchUtil.OnSuccessListener() {
                                    @Override
                                    public void showAddress(Address address) {
                                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<><><.m.,m,m");

                                        // [ TextView | searchedAddress ] text
                                        searchedAddress.setText(address.getAddressLine(0));

                                        if (checkFitnessCenter(address.getLatitude(), address.getLongitude())) {
                                            // 기존에 등록되어 있는 피트니스 센터가 있으면
                                            // 그 위치로 이동만
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< check fitness center > 기존의 피트니스 센터가 존재한다.");
                                            LocationUpdateUtil.moveLocation(gMap, address);

                                            // FitnessCenterArrayList 에서 기존의 FitnessCenter 객체 가져오기
                                            fitnessCenter = getFitnessCenter(address.getLatitude(), address.getLongitude());

                                            // 이미 존재한다고 사용자에게 알려주기
                                            Snackbar.make(
                                                    getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar),
                                                    R.string.f_fitness_center_search_snack_already_exist,
                                                    Snackbar.LENGTH_LONG
                                            )
                                                    .show();

                                        } else {
                                            // 기존에 등록되지 않은 피트니스 센터이면
                                            // 구글 맵에 마커표시하고 그 위치로 이동
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< check fitness center > 처음 검색된 피트니스 센터이다.");
                                            LocationUpdateUtil.showMarkerToMap(getFragment().getActivity(), gMap, address);

                                            // 새로운 FitnessCenter 객체 생성하기
                                            fitnessCenter = newInstanceOfFitnessCenter(address);

                                        }

                                    }
                                });

                    } else {

                        Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_fitness_center_search_snack_no_location_permission, Snackbar.LENGTH_SHORT).show();
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    /**
     * @return
     */
    private OnMapReadyCallback createOnMapReadyCallback() {
        final String METHOD_NAME = "[createOnMapReadyCallback] ";
        return new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                fitnessCenterArrayList = new ArrayList<>();

                // [GoogleMap] [gMap] 전연 변수로 사용하기 위해서
                gMap = googleMap;

                googleMapManager = new GoogleMapManager.Builder()
                        .setFragment(getFragment())
                        .setGoogleMap(gMap)
                        .setInterval(LocationUpdateManager.INTERNAL)
                        .setFastestInterval(LocationUpdateManager.FASTEST_INTERNAL)
                        .setPriority(LocationUpdateManager.PRIORITY)
                        .setFitnessCenterArrayList(fitnessCenterArrayList)
                        .create();

                googleMapManager.init();

            }
        };
    }


    /**
     * EndButton
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        final String METHOD_NAME = "[newEndButtonListenerInstance] ";
        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++++++++++++++++++++ fitnessCenterArrayList +++++++++++++++++++++++++++++++++++");
                for (int index = 0; index < fitnessCenterArrayList.size(); index++) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getName = " + fitnessCenterArrayList.get(index).getName());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getThirdAddress = " + fitnessCenterArrayList.get(index).getThirdAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getLatitude = " + fitnessCenterArrayList.get(index).getLatitude());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getLongitude = " + fitnessCenterArrayList.get(index).getLongitude());
                }

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++++++++++++++++++++ fitnessCenter +++++++++++++++++++++++++++++++++++");
                if (fitnessCenter != null) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getName = " + fitnessCenter.getName());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getFirstAddress = " + fitnessCenter.getFirstAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getSecondAddress = " + fitnessCenter.getSecondAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getThirdAddress = " + fitnessCenter.getThirdAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLatitude = " + fitnessCenter.getLatitude());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLongitude = " + fitnessCenter.getLongitude());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getMemberCounter = " + fitnessCenter.getMemberCounter());

                    // fitness center register fragment  로 이동
                    getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_home_content_wrapper, FitnessCenterRegisterFragment.newInstance(fitnessCenter))
                            .addToBackStack(null)
                            .commit();

                } else {
                    Snackbar.make(
                            getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar),
                            R.string.f_fitness_center_search_snack_check_data_false,
                            Snackbar.LENGTH_SHORT
                    )
                            .show();
                }
                return null;
            }
        };
    }


    private boolean checkData() {
        final String METHOD_NAME = "[checkData] ";

        String defaultData = getFragment().getString(R.string.f_fitness_center_search_address_default);
        String inputData = searchedAddress.getText().toString();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< getString > getString = " + defaultData);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< searchedAddress > searchedAddress = " + inputData);

        boolean first = defaultData.equals(inputData);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< boolean > first = " + first);

        if (!defaultData.equals(inputData)) {
            return true;
        }

        return false;
    }


    private boolean checkFitnessCenter(double latitude, double longitude) {
        final String METHOD_NAME = "[checkFitnessCenter] ";

        for (int index = 0; index < fitnessCenterArrayList.size(); index++) {
            if (fitnessCenterArrayList.get(index).getLatitude() == latitude && fitnessCenterArrayList.get(index).getLongitude() == longitude) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===============>> 기존의 피트니스 센터가 존재합니다.");
                return true;
            }
        }
        return false;
    }

    private FitnessCenter getFitnessCenter(double latitude, double longitude) {
        final String METHOD_NAME = "[getFitnessCenter] ";

        for (int index = 0; index < fitnessCenterArrayList.size(); index++) {
            if (fitnessCenterArrayList.get(index).getLatitude() == latitude && fitnessCenterArrayList.get(index).getLongitude() == longitude) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===============>> 기존의 피트니스 센터가 존재합니다.");
                return fitnessCenterArrayList.get(index);
            }
        }

        return null;
    }

    private FitnessCenter newInstanceOfFitnessCenter(Address address) {

        FitnessCenter fitnessCenter = new FitnessCenter();

        fitnessCenter.setName(addressSearchView.getQuery().toString());
        fitnessCenter.setFirstAddress(SearchUtil.getFirstAddress(address));
        fitnessCenter.setSecondAddress(SearchUtil.getSecondAddress(address));
        fitnessCenter.setThirdAddress(address.getAddressLine(0));
        fitnessCenter.setLatitude(address.getLatitude());
        fitnessCenter.setLongitude(address.getLongitude());

        return fitnessCenter;
    }


}
