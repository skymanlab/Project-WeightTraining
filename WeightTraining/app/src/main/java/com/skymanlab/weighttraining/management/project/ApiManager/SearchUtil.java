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
import java.util.List;
import java.util.Locale;

public class SearchUtil {

    // constant
    private static final String CLASS_NAME = SearchUtil.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 주소검색 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static String searchAddress(Activity activity, GoogleMap googleMap, String addressText) {
        final String METHOD_NAME = "[searchAddress] ";

        // [Geocoder] [geocoder] 주소를 가져오기 위한 api
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Geocoder > 객체는 ? = " + geocoder);

        // [List<Address>] [addressList] geocoder 로 가져온 주소 리스트를 받기위한 객체
        List<Address> addressList = null;

        try {

            // [List<Address>] [addressList] geocoder 를 이용하여 해당 주소로 위치가져오기
            addressList = geocoder.getFromLocationName(addressText, 1);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > 리스트 객체 = " + addressList);

            // [check 1] 위치로 가져온 리스트가 있을 경우에
            if (!addressList.isEmpty()) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< AddressList> 사이즈는 ? = " + addressList.size());

                LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

                LocationUpdateUtil.showMarkerToMap(activity, googleMap, latLng);

                // admin area : 도
                // locality : 시
                // feature name : 자세한 주소
                //
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< AddressList> 사이즈는 ? = " + addressList.size());

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getCountryName = " + addressList.get(0).getCountryName());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getAdminArea = " + addressList.get(0).getAdminArea());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubAdminArea = " + addressList.get(0).getSubAdminArea());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getLocality = " + addressList.get(0).getLocality());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubLocality = " + addressList.get(0).getSubLocality());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getFeatureName = " + addressList.get(0).getFeatureName());

                return addressList.get(0).getAddressLine(0);

            } else {
                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_not_find_address, Snackbar.LENGTH_SHORT).show();
                return activity.getString(R.string.etc_search_util_snack_not_find_address);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return activity.getString(R.string.etc_search_util_snack_find_address_error);
        }
    }


    public static void searchAddress(Activity activity, String addressText, OnSuccessListener onSuccessListener) {
        final String METHOD_NAME = "[searchAddress] ";

        // [Geocoder] [geocoder] 주소를 가져오기 위한 api
        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Geocoder > 객체는 ? = " + geocoder);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Locale > getDefault ? = " + Locale.getDefault());

        // [List<Address>] [addressList] geocoder 로 가져온 주소 리스트를 받기위한 객체
        List<Address> addressList = null;

        try {

            // [List<Address>] [addressList] geocoder 를 이용하여 해당 주소로 위치가져오기
            addressList = geocoder.getFromLocationName(addressText, 2);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > 리스트 객체 = " + addressList);

            // [check 1] 위치로 가져온 리스트가 있을 경우에
            if (!addressList.isEmpty()) {

                // admin area : 도
                // locality : 시
                // sub locality : 구
                // feature name : 자세한 주소
                //
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< AddressList> 사이즈는 ? = " + addressList.size());

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getCountryName = " + addressList.get(0).getCountryName());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getAdminArea = " + addressList.get(0).getAdminArea());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubAdminArea = " + addressList.get(0).getSubAdminArea());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getLocality = " + addressList.get(0).getLocality());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubLocality = " + addressList.get(0).getSubLocality());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getFeatureName = " + addressList.get(0).getFeatureName());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getMaxAddressLineIndex = " + addressList.get(0).getMaxAddressLineIndex());

                // 검색된 주소를 리스너에게 넘겨주기
                onSuccessListener.showAddress(addressList.get(0));

//                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_find_address, Snackbar.LENGTH_SHORT).show();

            } else {
                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_not_find_address, Snackbar.LENGTH_SHORT).show();
            }

        } catch (IllegalArgumentException iae) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< IllegalArgumentException >>> IllegalArgument 예외사황 발생");
            iae.printStackTrace();
        } catch (IOException e) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< IOException >>> IO 예외사황 발생");
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_find_address_error, Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    public static void searchLAddress(Activity activity, LatLng position, OnSuccessListener onSuccessListener) {
        final String METHOD_NAME = "[searchAddress] ";

        // [Geocoder] [geocoder] 주소를 가져오기 위한 api
        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Geocoder > 객체는 ? = " + geocoder);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Locale > getDefault ? = " + Locale.getDefault());

        // [List<Address>] [addressList] geocoder 로 가져온 주소 리스트를 받기위한 객체
        List<Address> addressList = null;

        try {

            // latitude 와 longitude 로 Address 구하기
            addressList = geocoder.getFromLocation(position.latitude, position.longitude, 1);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > 리스트 객체 = " + addressList);

            // [check 1] 위치로 가져온 리스트가 있을 경우에
            if (!addressList.isEmpty()) {

                // admin area : 도
                // locality : 시
                // sub locality : 구
                // feature name : 자세한 주소
                //
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< AddressList> 사이즈는 ? = " + addressList.size());
//
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getCountryName = " + addressList.get(0).getCountryName());
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getAdminArea = " + addressList.get(0).getAdminArea());
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubAdminArea = " + addressList.get(0).getSubAdminArea());
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getLocality = " + addressList.get(0).getLocality());
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getSubLocality = " + addressList.get(0).getSubLocality());
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getFeatureName = " + addressList.get(0).getFeatureName());
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > getMaxAddressLineIndex = " + addressList.get(0).getMaxAddressLineIndex());

                // 검색된 주소를 리스너에게 넘겨주기
                onSuccessListener.showAddress(addressList.get(0));

//                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_find_address, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_not_find_address, Snackbar.LENGTH_SHORT).show();
            }

        } catch (IllegalArgumentException iae) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< IllegalArgumentException >>> IllegalArgument 예외사황 발생");
            iae.printStackTrace();
        } catch (IOException e) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< IOException >>> IO 예외사황 발생");
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_find_address_error, Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    public static Address searchLAddress(Activity activity, LatLng position) {
        final String METHOD_NAME = "[searchAddress] ";

        // [Geocoder] [geocoder] 주소를 가져오기 위한 api
        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Geocoder > 객체는 ? = " + geocoder);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Locale > getDefault ? = " + Locale.getDefault());

        // [List<Address>] [addressList] geocoder 로 가져온 주소 리스트를 받기위한 객체
        List<Address> addressList = null;

        try {

            // latitude 와 longitude 로 Address 구하기
            addressList = geocoder.getFromLocation(position.latitude, position.longitude, 1);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Address > 리스트 객체 = " + addressList);

            // [check 1] 위치로 가져온 리스트가 있을 경우에
            if (!addressList.isEmpty()) {
                return addressList.get(0);
            } else {
                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_not_find_address, Snackbar.LENGTH_SHORT).show();
            }

        } catch (IllegalArgumentException iae) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< IllegalArgumentException >>> IllegalArgument 예외사황 발생");
            iae.printStackTrace();
            return null;
        } catch (IOException e) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< IOException >>> IO 예외사황 발생");
            Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_find_address_error, Snackbar.LENGTH_SHORT).show();
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

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= listener =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnSuccessListener {
        void showAddress(Address address);
    }

}
