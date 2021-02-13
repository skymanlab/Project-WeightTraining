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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FitnessCenterSearchSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterRegisterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable : google map api
    private GoogleMap gMap;
    private GoogleMapManager googleMapManager;

    // instance variable
    private SearchView addressSearchView;
    private TextView searchedAddress;

    // instance variable
    private Address fitnessCenterAddress = null;

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

                                        // 해당 address 를 fitnessCenterAddress 로 등록
                                        fitnessCenterAddress = address;

                                        // address 에 대한 LatLng 객체 생성
                                        LatLng location = new LatLng(address.getLatitude(), address.getLongitude());

                                        // [ TextView | searchedAddress ] text
                                        searchedAddress.setText(address.getAddressLine(0));

                                        // 구글 맵에 address 의 LatLng 위치에 마커로 표시
                                        LocationUpdateUtil.showMarkerToMap(getFragment().getActivity(), gMap, address);

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

                // [GoogleMap] [gMap] 전연 변수로 사용하기 위해서
                gMap = googleMap;

                googleMapManager = new GoogleMapManager.Builder()
                        .setActivity(getFragment().getActivity())
                        .setGoogleMap(gMap)
                        .setInterval(LocationUpdateManager.INTERNAL)
                        .setFastestInterval(LocationUpdateManager.FASTEST_INTERNAL)
                        .setPriority(LocationUpdateManager.PRIORITY)
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

                if (checkData()) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< checkData > true");
                    getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(
                                    R.id.nav_home_content_wrapper,
                                    FitnessCenterRegisterFragment.newInstance(addressSearchView.getQuery().toString(), searchedAddress.getText().toString())
                            )
                            .addToBackStack(null)
                            .commit();
                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< checkData > false");
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


}
