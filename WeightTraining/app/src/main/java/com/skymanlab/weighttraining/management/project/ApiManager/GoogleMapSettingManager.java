package com.skymanlab.weighttraining.management.project.ApiManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.More.FitnessCenterRegisterFragment;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapSettingManager {

    // constant
    private static final String CLASS_NAME = GoogleMapSettingManager.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Fragment fragment;
    private GoogleMap googleMap;
    private int interval;
    private int fastestInterval;
    private int priority;
    private ArrayList<FitnessCenter> fitnessCenterArrayList;

    // instance variable
    private PermissionManager permissionManager;
    private LocationUpdateManager updateManager;
    private FitnessCenterMarkerManager markerManager;

    // constructor
    private GoogleMapSettingManager(Builder builder) {
        this.fragment = builder.fragment;
        this.googleMap = builder.googleMap;
        this.interval = builder.interval;
        this.fastestInterval = builder.fastestInterval;
        this.priority = builder.priority;
        this.fitnessCenterArrayList = builder.fitnessCenterArrayList;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= initialize setting  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void init() {
        final String METHOD_NAME = "[init] ";

        // Location API 를 사용하기 위해서 권한 확인하기 : 현재 위치를 표시하기 위해
        permissionManager = new PermissionManager(fragment.getActivity());
        permissionManager.initLocationPermission(
                new PermissionManager.GrantedPermissionListener() {
                    @Override
                    public void setNextProcedure() {

                        if (!NetworkStateChecker.checkActiveNetwork(fragment.getContext())) {
                            Snackbar.make(
                                    fragment.getActivity().findViewById(R.id.nav_home_content_wrapper),
                                    R.string.etc_google_map_manager_snack_notAccessNetwork,
                                    Snackbar.LENGTH_SHORT
                            ).show();
                            return;
                        }

                        // init UI
                        initWidgetOfDefaultUiSetting();

                        // my location 사용가능 한지 파악 후
                        initWidgetOfMyLocation();

                        // 마지막 위치
                        showFitnessCenterListToLastLocation();

                    }
                }
        );

    }


    private void initWidgetOfDefaultUiSetting() {
        final String METHOD_NAME = "[initDefaultUI] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< UI > googleMap 의 기본 UI 를 설정하겠습니다.");

        // map tool bar 사용 가능 / zoom 컨트롤 사용 가능
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // map click listener
        googleMap.setOnMapClickListener(createOnMapClickListener());

    }


    private void initWidgetOfMyLocation() {
        final String METHOD_NAME = "[initMyLocation] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< my location > googleMap 에 현재 자신의 위치를 가져올 수 있도록 설정중입니다.");

        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {

            // 권한이 거부 되었을 때
            return;

        }

        // Enable : true 로 변경
        googleMap.setMyLocationEnabled(true);

        // my location click listener 등록
        googleMap.setOnMyLocationClickListener(createOnMyLocationClickListener());

        // my location button click listener 등록
        googleMap.setOnMyLocationButtonClickListener(createOnMyLocationButtonClickListener());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< My location >  location 를 가져오기 위한 설정을 진행합니다.");

        // 위치 업데이트 사용하기 : 초기 설정 작업하기
        initLocationUpdate();

    }

    private void initLocationUpdate() {
        final String METHOD_NAME = "[initLocationUpdate] ";

        // [LocationUpdateManager] [updateManager] location update 를 사용하기 위한 초기 데이터를 설정한다.
        updateManager = new LocationUpdateManager.Builder()
                .setActivity(fragment.getActivity())
                .setInterval(interval)
                .setFastestInterval(fastestInterval)
                .setPriority(priority)
                .setLocationCallback(createLocationCallback())
                .create();

        // [LocationUpdateManager] [updateManager] 초기 데이터로 초기 설정을 한다.
        updateManager.initSetup();

    }


    public void showFitnessCenterListToLastLocation() {
        final String METHOD_NAME = "[showFitnessCenterListToLastLocation] ";

        // marker click listener 등록
        googleMap.setOnMarkerClickListener(createOnMarkerClickListener());

        LocationUtil.getLastLocation(
                fragment.getActivity(),
                new LocationUtil.OnLastLocationListener() {
                    @Override
                    public void showLastLocation(Location location) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< last location > 마지막 위치가 있는지 확인합니다. = " + location);
                        if (location != null) {
                            // 마지막 위치 표시 ->
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< lat location > 마지막 위치가 있습니다. 마지막 위치를 표시합니다. / 위도:" + location.getLatitude() + ", 경도: " + location.getLongitude());

                            // [LatLng] [lastLatLng] location 으로 LatLng 객체로 생성
                            LatLng lastLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            // latLatLng 으로 위치 이동만
                            LocationUpdateUtil.moveLocation(googleMap, lastLatLng);

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===========================================================================");
                            // lastLatLng 에서 firstAddress, secondAddress 를 구한다. 그리고 이것으로 firebase database 에서 저장된 데이터를 가져온다.
                            markerManager = new FitnessCenterMarkerManager(fragment.getActivity(), googleMap, fitnessCenterArrayList);
                            markerManager.execute(lastLatLng);


                        } else {

                            // 기본 위치 표시 ->
                            // [LatLng] [lastLatLng] 서울을 기본 위치로 하여 LatLng 객체로 생성
                            LatLng defaultLatLng = LocationUpdateUtil.SEOUL;
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< last location > 위치 없음 기존 위치로 표시합니다. / 위도:" + defaultLatLng.latitude + ", 경도:" + defaultLatLng.longitude);

                            // googleMap 에 lastLatLng 의 위치로 이동만
                            LocationUpdateUtil.moveLocation(googleMap, defaultLatLng);

                            // defaultLatLng 에서 firstAddress, secondAddress 를 구한다. 그리고 이것으로 firebase database 에서 저장된 데이터를 가져온다.
                            markerManager = new FitnessCenterMarkerManager(fragment.getActivity(), googleMap, fitnessCenterArrayList);
                            markerManager.execute(defaultLatLng);


                        }
                    }
                }
        );

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Listener =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private GoogleMap.OnMapClickListener createOnMapClickListener() {
        final String METHOD_NAME = "[createOnMapClickListener] ";
        return new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< map click > 해당 맵을 클릭하였습니다. / 위도:" + latLng.latitude + ", 경도:" + latLng.longitude);
            }
        };
    }

    private GoogleMap.OnMyLocationClickListener createOnMyLocationClickListener() {
        final String METHOD_NAME = "[createOnMyLocationClickListener] ";
        return new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< my location click listener > 나의 위치를 클릭하였습니다. / 위도:" + location.getLatitude() + ", 경도:" + location.getLongitude());

            }
        };
    }

    private GoogleMap.OnMyLocationButtonClickListener createOnMyLocationButtonClickListener() {
        final String METHOD_NAME = "[createOnMyLocationButtonClickListener] ";
        return new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< my location button click listener > 나의 위치 버튼을 클릭하여서 현재 위치로 이동 합니다.");
                return false;
            }
        };
    }

    private GoogleMap.OnMarkerClickListener createOnMarkerClickListener() {
        final String METHOD_NAME = "[createOnMyLocationButtonClickListener] ";
        return new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                // 이미 등록된 피트니스 센터이면
                // 피트니스 센터 리스트가 있는 fitnessCenterList 에서
                // 해당 위치의 피트니스 센터의 정보가 담긴 FitnessCenter 객체를 가져온다.
                FitnessCenter fitnessCenter = getFitnessCenter(marker);

                if (fitnessCenter != null) {

                    int numberOfMember = getNumberOfRegisteredMembers(marker.getTitle());

                    // alertDialog 의 message
                    StringBuilder message = new StringBuilder()
                            .append("센터명 : ")
                            .append(fitnessCenter.getName())
                            .append("\n")
                            .append("회원 수 : ")
                            .append(numberOfMember)
                            .append(" 명")
                            .append("\n")
                            .append("주소 : ")
                            .append(fitnessCenter.getThirdAddress());

                    // alertDialog 로 클릭한 위치의 피트니스 센터 정보를 간단하게 보여준다.
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
                    builder.setTitle(R.string.etc_google_map_manager_alert_marker_title)
                            .setMessage(message.toString())
                            .setPositiveButton(R.string.etc_google_map_manager_alert_marker_bt_positive, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // fitness center register fragment 로 이동
                                    fragment.getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_home_content_wrapper, FitnessCenterRegisterFragment.newInstance(fitnessCenter))
                                            .addToBackStack(null)
                                            .commit();

                                }
                            })
                            .setNegativeButton(R.string.etc_google_map_manager_alert_marker_bt_negative, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------------------------------- marker -----------------------------");
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Marker > getTitle = " + marker.getTitle());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Marker > getSnippet = " + marker.getSnippet());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Marker > latitude = " + marker.getPosition().latitude);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Marker > longitude = " + marker.getPosition().longitude);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------------------------------- fitnessCenterList -----------------------------");
                for (int index = 0; index < fitnessCenterArrayList.size(); index++) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getName = " + fitnessCenterArrayList.get(index).getName());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getMemberCounter = " + fitnessCenterArrayList.get(index).getMemberCounter());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getFirstAddress = " + fitnessCenterArrayList.get(index).getFirstAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getSecondAddress = " + fitnessCenterArrayList.get(index).getSecondAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getThirdAddress = " + fitnessCenterArrayList.get(index).getThirdAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getLatitude = " + fitnessCenterArrayList.get(index).getLatitude());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getLongitude = " + fitnessCenterArrayList.get(index).getLongitude());
                }

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------------------------------- 클릭 한 위치의 fitnessCenter -----------------------------");
                if (fitnessCenter != null) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getName = " + fitnessCenter.getName());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getFirstAddress = " + fitnessCenter.getFirstAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getSecondAddress = " + fitnessCenter.getSecondAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getThirdAddress = " + fitnessCenter.getThirdAddress());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLatitude = " + fitnessCenter.getLatitude());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getLongitude = " + fitnessCenter.getLongitude());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< FitnessCenter > getMemberCounter = " + fitnessCenter.getMemberCounter());
                }

                return true;
            }
        };
    }

    private LocationCallback createLocationCallback() {
        final String METHOD_NAME = "[createLocationCallback] ";
        return new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // locationResult 를 통해 update 된 위치의 모든 데이터 가져오기
                List<Location> locationList = locationResult.getLocations();

                // [check 1] 위의 데이터가 존재할 경우 만
                if (locationResult.getLastLocation() != null) {


                    // [Location] [currentLocation] 마지막 index 의 location 를 현재 위치로 설정
                    Location currentLocation = locationResult.getLastLocation();
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< location call back > 현재 위치를 업데이트 받았습니다. / 위도:" + currentLocation.getLatitude() + ", 경도:" + currentLocation.getLongitude());

                    // [LatLng] [currentLatLng] Location 의 위도, 경도로 LatLng 객체 생성
                    LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                    LocationUpdateUtil.showMarkerToMap(fragment.getActivity(), googleMap, currentLatLng);

                } // [check 1]
            }
        };
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * 마커를 클릭했을 때 해당 위치의 피트니스 센터 정보가 담긴 객체를 반환한다.
     *
     * @param marker
     * @return
     */
    private FitnessCenter getFitnessCenter(Marker marker) {
        final String METHOD_NAME = "[getFitnessCenter] ";

        for (int index = 0; index < fitnessCenterArrayList.size(); index++) {

            // latitude 와 longitude 로 피트니스 센터 객체를 찾는다.
            if (marker.getPosition().latitude == fitnessCenterArrayList.get(index).getLatitude()
                    && marker.getPosition().longitude == fitnessCenterArrayList.get(index).getLongitude()) {
                return fitnessCenterArrayList.get(index);
            }

        }

        return null;
    }

    private int getNumberOfRegisteredMembers(String markerTitle) {
        final String METHOD_NAME = "[getNumberOfRegisteredMembers] ";

        int dashIndex = markerTitle.indexOf("/");
        int tagIndex = markerTitle.indexOf("명");

        String number = markerTitle.substring(dashIndex + 1, tagIndex);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< / 위치 > index = " + dashIndex);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 명 위치 > index = " + tagIndex);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 구한 숫자 > string = " + number);

        return Integer.parseInt(number);

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 외부에서 사용하기 위한 method =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // location update start
    public void startLocationUpdate() {
        final String METHOD_NAME = "[startLocationUpdate] ";

        if (updateManager != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< update manager > 해당 location update 를 실행합니다.");

            updateManager.startLocationUpdate(fragment.getActivity());

        }

    }

    // location update stop
    public void stopLocationUpdate() {
        final String METHOD_NAME = "[stopLocationUpdate] ";

        if (this.updateManager != null) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< update manager stop > 해당 location update 를 중지합니다.");
            this.updateManager.stopLocationUpdates(fragment.getActivity());
        }

    }

    // fitness center marker manager -> AsyncTask 종료
    public void stopFitnessCenterMarkerManager() {
        final String METHOD_NAME = "[stopFitnessCenterMarkerManager] ";

        if (markerManager != null) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 마커 메니저 > 상태는 ? = " + markerManager.getStatus());
            if (markerManager.getStatus() == AsyncTask.Status.RUNNING) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 마커 메니저 > 아직 실행 중입니다.");
                markerManager.cancel(true);
            }
        }

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        private Fragment fragment;
        private GoogleMap googleMap;
        private int interval;
        private int fastestInterval;
        private int priority;
        private ArrayList<FitnessCenter> fitnessCenterArrayList;

        // constructor
        public Builder() {
        }

        // setter
        public Builder setFragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder setGoogleMap(GoogleMap googleMap) {
            this.googleMap = googleMap;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setFastestInterval(int fastestInterval) {
            this.fastestInterval = fastestInterval;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setFitnessCenterArrayList(ArrayList<FitnessCenter> fitnessCenterArrayList) {
            this.fitnessCenterArrayList = fitnessCenterArrayList;
            return this;
        }

        // create
        public GoogleMapSettingManager create() {
            return new GoogleMapSettingManager(this);
        }
    }
}
