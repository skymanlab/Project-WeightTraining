package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;

import java.io.IOException;
import java.util.List;

public class SearchUtil {

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 주소검색 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void searchAddress(Activity activity, GoogleMap googleMap, String addressText) {
        final String METHOD_NAME = "[searchAddress] ";

        // [Geocoder] [geocoder] 주소를 가져오기 위한 api
        Geocoder geocoder = new Geocoder(activity);

        // [List<Address>] [addressList] geocoder 로 가져온 주소 리스트를 받기위한 객체
        List<Address> addressList = null;

        try {

            // [List<Address>] [addressList] geocoder 를 이용하여 해당 주소로 위치가져오기
            addressList = geocoder.getFromLocationName(addressText, 10);

            // [check 1] 위치로 가져온 리스트가 있을 경우에
            if (!addressList.isEmpty()) {

                LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

                LocationUpdateUtil.showMarkerToMap(activity, googleMap, latLng);

            } else {
                Snackbar.make(activity.findViewById(R.id.nav_home_content_wrapper), R.string.etc_search_util_snack_not_find_address, Snackbar.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LatLng convertAddressToLatLng(Address address) {
        return new LatLng(address.getLatitude(), address.getLongitude());
    }


}
