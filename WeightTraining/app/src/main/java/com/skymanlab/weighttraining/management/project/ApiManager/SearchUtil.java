package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchUtil {

    // (참고)
    // admin area : 도
    // locality : 시
    // sub locality : 구
    // feature name : 자세한 주소

    // constant
    private static final String CLASS_NAME = SearchUtil.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 주소검색 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static String searchAddress(Activity activity,
                                       GoogleMap googleMap,
                                       String addressText) {

        final String METHOD_NAME = "[searchAddress] ";

        // 네트워크에 연결되지 않으면 사용자에게 알려주기
        if (!NetworkStateChecker.checkActiveNetwork(activity)) {

            Snackbar.make(
                    activity.findViewById(R.id.nav_home_content_wrapper),
                    R.string.etc_google_map_manager_snack_notAccessNetwork,
                    Snackbar.LENGTH_SHORT
            ).show();

            return activity.getString(R.string.etc_google_map_manager_snack_notAccessNetwork);
        }

        // Geocoder : Address 객체를 가져오기 위한
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());

        // 검색된 Address 객체를 담기 위한 ArrayList 선언
        List<Address> addressList = null;

        try {

            // Geocoder 를 통해 해당 한글 주소를 통해 Address 리스트 가져오기
            addressList = geocoder.getFromLocationName(
                    addressText,
                    1
            );

            // 가져온 Address 리스트에 항목이 있을 때만
            if (!addressList.isEmpty()) {

                LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

                LocationUpdateUtil.showMarkerToMap(activity, googleMap, latLng);

                return addressList.get(0).getAddressLine(0);

            } else {
                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_notFindAddress, Snackbar.LENGTH_SHORT).show();
                return activity.getString(R.string.etc_searchUtil_snack_notFindAddress);
            }

        } catch (IllegalArgumentException iae) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 예외상황 > IllegalArgumentException 발생");
            iae.printStackTrace();
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_error, Snackbar.LENGTH_SHORT).show();
            return activity.getString(R.string.etc_searchUtil_snack_error);
        } catch (IOException e) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 예외상황 > IOException 발생");
            e.printStackTrace();
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_error, Snackbar.LENGTH_SHORT).show();
            return activity.getString(R.string.etc_searchUtil_snack_error);
        }
    }


    public static void searchAddress(Activity activity,
                                     String addressText,
                                     OnSearchedAddressListener onSearchedAddressListener) {

        final String METHOD_NAME = "[searchAddress] ";

        // 네트워크에 연결되지 않으면 사용자에게 알려주기
        if (!NetworkStateChecker.checkActiveNetwork(activity)) {
            Snackbar.make(
                    activity.findViewById(R.id.nav_home_content_wrapper),
                    R.string.etc_google_map_manager_snack_notAccessNetwork,
                    Snackbar.LENGTH_SHORT
            ).show();
            return;
        }

        // Geocoder : Address 객체를 가져오기 위한 
        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());

        // 검색된 Address 객체를 담기 위한 ArrayList 선언
        List<Address> addressList = null;

        try {

            // Geocoder 를 통해 해당 한글 주소를 통해 Address 리스트 가져오기
            addressList = geocoder.getFromLocationName(
                    addressText,
                    2
            );

            // 가져온 Address 리스트에 항목이 있을 때만
            if (!addressList.isEmpty()) {

                // 검색된 주소를 리스너에게 넘겨주기
                onSearchedAddressListener.showSearchedAddress(
                        addressList.get(0)
                );

                display(addressList);


            } else {
                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_notFindAddress, Snackbar.LENGTH_SHORT).show();
            }

        } catch (IllegalArgumentException iae) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 예외상황 > IllegalArgumentException 발생");
            iae.printStackTrace();
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_error, Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 예외상황 > IOException 발생");
            e.printStackTrace();
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_error, Snackbar.LENGTH_SHORT).show();
        }

    }


    public static Address searchLAddress(Activity activity, LatLng position) {
        final String METHOD_NAME = "[searchAddress] ";

        // 네트워크에 연결되지 않으면 사용자에게 알려주기
        if (!NetworkStateChecker.checkActiveNetwork(activity)) {
            Snackbar.make(
                    activity.findViewById(R.id.nav_home_content_wrapper),
                    R.string.etc_google_map_manager_snack_notAccessNetwork,
                    Snackbar.LENGTH_SHORT
            ).show();
            return null;
        }

        // Geocoder : Address 객체를 가져오기 위한
        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());

        // 검색된 Address 객체를 담기 위한 ArrayList 선언
        List<Address> addressList = null;

        try {

            // Geocoder 를 통해 해당 한글 주소를 통해 Address 리스트 가져오기
            addressList = geocoder.getFromLocation(
                    position.latitude,
                    position.longitude,
                    1);

            // 가져온 Address 리스트에 항목이 있을 때만
            if (!addressList.isEmpty()) {

                return addressList.get(0);

            } else {

                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_notFindAddress, Snackbar.LENGTH_SHORT).show();

            }

        } catch (IllegalArgumentException iae) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 예외상황 > IllegalArgumentException 발생");
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_error, Snackbar.LENGTH_SHORT).show();
            iae.printStackTrace();
            return null;
        } catch (IOException e) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 예외상황 > IOException 발생");
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_searchUtil_snack_error, Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }

        return null;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static String getFirstAddress(Address address) {

        if (address.getAdminArea() != null) {
            return address.getAdminArea();
        } else {
            return address.getSubAdminArea();
        }

    }

    public static String getSecondAddress(Address address) {
        if (address.getLocality() != null) {
            return address.getLocality();
        } else {
            return address.getSubLocality();
        }
    }


    private static void display(List<Address> addressList) {
        final String METHOD_NAME = "[display] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==========================================================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< AddressList> 가져온 Address 리스트의 항목 개수는 ? = " + addressList.size());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===================================== Address 리스트의 첫 번재 =====================================");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getCountryName = " + addressList.get(0).getCountryName());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getAdminArea = " + addressList.get(0).getAdminArea());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubAdminArea = " + addressList.get(0).getSubAdminArea());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getLocality = " + addressList.get(0).getLocality());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubLocality = " + addressList.get(0).getSubLocality());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getFeatureName = " + addressList.get(0).getFeatureName());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getMaxAddressLineIndex = " + addressList.get(0).getMaxAddressLineIndex());

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= listener =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnSearchedAddressListener {
        void showSearchedAddress(Address address);
    }

}
