package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.location.Address;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.GoogleMapSettingManager;
import com.skymanlab.weighttraining.management.project.ApiManager.LocationUpdateManager;
import com.skymanlab.weighttraining.management.project.ApiManager.LocationUpdateUtil;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionUtil;
import com.skymanlab.weighttraining.management.project.ApiManager.SearchUtil;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterRegisterFragment;

import java.util.ArrayList;

public class FitnessCenterSearchSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterSearchSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : google map api
    private GoogleMap gMap;
    private GoogleMapSettingManager googleMapSettingManager;

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
    public GoogleMapSettingManager getGoogleMapSettingManager() {
        return googleMapSettingManager;
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

                // googleMap 을 가져왔을 때
                if (gMap != null) {

                    if (PermissionUtil.hasPermissionList(getFragment().getActivity(), PermissionManager.LOCATION_PERMISSION_LIST)) {

                        // SearchUtil 을 이용하여 주소 검색하기
                        SearchUtil.searchAddress(
                                getFragment().getActivity(),
                                query,
                                new SearchUtil.OnSearchedAddressListener() {
                                    @Override
                                    public void showSearchedAddress(Address address) {
                                        // 검색 성공시에만 FitnessCenter 객체가 만들어지고
                                        // 해당 객체가 존재할 때 다음 단계로 넘어 갈 수 있다.

                                        // 여기서는 GoogleMapSettingManger 를 통해서 해당 지역을 검색하여
                                        // 등록된 피트니스 센터가 있으면 fitnessCenterList 에 추가되어 담겨진다.

                                        // 만약 해당 검색어로 검색된 Address 가
                                        // 이 fitnessCenterList 에 있는 주소이면 여기의 FitnessCenter 객체를 반환하고
                                        // 아니면 Address 를 토대로 새로운 FitnessCenter 객체를 반환한다.


                                        // 검색된 피트니스 센터의 주소를 화면에 표시하기
                                        searchedAddress.setText(address.getAddressLine(0));

                                        if (checkExistFitnessCenter(address.getLatitude(), address.getLongitude())) {
                                            
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< check fitness center > 기존의 피트니스 센터가 존재한다.");

                                            // 기존에 등록되어 있는 피트니스 센터가 있으면
                                            // 그 위치로 이동만
                                            LocationUpdateUtil.moveLocation(gMap, address);

                                            // FitnessCenterArrayList 에서 이미 등록된 피트니스 센터의 정보가 담긴 FitnessCenter 객체 가져오기
                                            fitnessCenter = getExistFitnessCenter(address.getLatitude(), address.getLongitude());

                                            // 이미 존재한다고 사용자에게 알려주기
                                            Snackbar.make(
                                                    getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar),
                                                    R.string.f_fitness_center_search_snack_already_exist,
                                                    Snackbar.LENGTH_LONG
                                            )
                                                    .show();

                                        } else {
                                            
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< check fitness center > 처음 검색된 피트니스 센터이다.");
                                            // 기존에 등록되지 않은 피트니스 센터이면
                                            // 구글 맵에 마커표시하고 그 위치로 이동
                                            LocationUpdateUtil.showMarkerToMap(getFragment().getActivity(), gMap, address);

                                            //  이미 등록된 피트니스 센터가 아니므로 새로운 FitnessCenter 객체 생성하기
                                            fitnessCenter = newInstanceOfFitnessCenter(address);

                                        }
                                    }
                                }
                        );
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

                // 현재 지역에 등록되어 있는 피트니스 센터를 가져오기 위한 ArrayList 입니다.
                // FitnessCenterMarkerManager 를 통해 피트니스 센터 리스트를 가져온다.
                fitnessCenterArrayList = new ArrayList<>();

                // [GoogleMap] [gMap] 전연 변수로 사용하기 위해서
                gMap = googleMap;

                // 구글 맵에 대해서 설정을 하기 위한 메니저 이다.
                googleMapSettingManager = new GoogleMapSettingManager.Builder()
                        .setFragment(getFragment())
                        .setGoogleMap(gMap)
                        .setInterval(LocationUpdateManager.INTERNAL)
                        .setFastestInterval(LocationUpdateManager.FASTEST_INTERNAL)
                        .setPriority(LocationUpdateManager.PRIORITY)
                        .setFitnessCenterArrayList(fitnessCenterArrayList)
                        .create();

                googleMapSettingManager.init();

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

                // addressSearchView 의 OnQueryTextListener 에서
                // SearchUtil 을 통해 구해진 FitnessCenter 객체가 존재할 때만 다음 단계로 넘어간다.

                if (fitnessCenter != null) {

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


    /**
     * 검색된 주소(Address) 가 fitnessCenterList 에 이미 존재 하는 주소인지 확인한다.
     *
     * @param latitude
     * @param longitude
     * @return
     */
    private boolean checkExistFitnessCenter(double latitude, double longitude) {
        final String METHOD_NAME = "[checkFitnessCenter] ";

        for (int index = 0; index < fitnessCenterArrayList.size(); index++) {
            if (fitnessCenterArrayList.get(index).getLatitude() == latitude && fitnessCenterArrayList.get(index).getLongitude() == longitude) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===============>> 기존의 피트니스 센터가 존재합니다.");
                return true;
            }
        }
        return false;
    }


    /**
     * 검색된 주소(Address) 가 fitnessCenterList 에 이미 존해하는 주소이면 해당 주소의 FitnessCenter 객체를 반환한다.
     *
     * @param latitude
     * @param longitude
     * @return
     */
    private FitnessCenter getExistFitnessCenter(double latitude, double longitude) {
        final String METHOD_NAME = "[getFitnessCenter] ";

        for (int index = 0; index < fitnessCenterArrayList.size(); index++) {
            if (fitnessCenterArrayList.get(index).getLatitude() == latitude && fitnessCenterArrayList.get(index).getLongitude() == longitude) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===============>> 기존의 피트니스 센터가 존재합니다.");
                return fitnessCenterArrayList.get(index);
            }
        }

        return null;
    }


    /**
     * 검색된 주소(Address) 가 fitnessCenterList 에 포함되지 않은 주소이면,
     * 해당 주소(Address) 로 FitnessCenter 객체를 생성하여 반환다.
     * ( 주의. 이 FitnessCenter 객체는 피트니스 센터를 등록하기 위해 꼭 필요한 데이터를 담고 있는 객체이다.)
     *
     * @param address
     * @return
     */
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


    private void display() {

        final String METHOD_NAME = "[display] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================================= 이미 등록된 fitnessCenterArrayList =================================");
        for (int index = 0; index < fitnessCenterArrayList.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getName = " + fitnessCenterArrayList.get(index).getName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getThirdAddress = " + fitnessCenterArrayList.get(index).getThirdAddress());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getLatitude = " + fitnessCenterArrayList.get(index).getLatitude());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getLongitude = " + fitnessCenterArrayList.get(index).getLongitude());
        }

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================================= 검색 확정된 피트니스 센터 정보가 담긴 fitnessCenter =================================");
        if (fitnessCenter != null) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getName = " + fitnessCenter.getName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getFirstAddress = " + fitnessCenter.getFirstAddress());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getSecondAddress = " + fitnessCenter.getSecondAddress());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getThirdAddress = " + fitnessCenter.getThirdAddress());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLatitude = " + fitnessCenter.getLatitude());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLongitude = " + fitnessCenter.getLongitude());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getMemberCounter = " + fitnessCenter.getMemberCounter());
        }

    }
}
