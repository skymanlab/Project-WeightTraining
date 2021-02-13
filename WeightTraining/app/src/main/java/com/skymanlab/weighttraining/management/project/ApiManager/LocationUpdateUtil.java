package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUpdateUtil {

    // constant
    public static final LatLng SEOUL = new LatLng(37.56, 126.97);

    // constant
    private static final int CAMERA_ZOOM = 15;

    // constructor
    private LocationUpdateUtil() {

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= marker =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static MarkerOptions createMarkerOptions(Activity activity, LatLng currentLatLng) {
        final String METHOD_NAME = "[createCurrentMarkerOptions] ";

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(getMarkerAddress(activity, currentLatLng));
        markerOptions.snippet(getMarkerSnippet(currentLatLng));
//        markerOptions.draggable(true);
        return markerOptions;
    }

    public static MarkerOptions createMarkerOptions(Activity activity, Address address) {
        final String METHOD_NAME = "[createCurrentMarkerOptions] ";

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(address.getLatitude(), address.getLongitude()));
        markerOptions.title(address.getAddressLine(0));
        markerOptions.snippet(getMarkerSnippet(new LatLng(address.getLatitude(), address.getLongitude())));
        markerOptions.draggable(true);

        return markerOptions;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= marker content =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static String getMarkerAddress(Activity activity, LatLng currentLatLng) {
        final String METHOD_NAME = "[getMarkerTitle] ";

        // 현재
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    currentLatLng.latitude,
                    currentLatLng.longitude,
                    1
            );
        } catch (IOException e) {
            e.printStackTrace();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException ie) {
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            return "주소 미발견";
        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public static String getMarkerSnippet(LatLng currentLatLng) {
        return new StringBuilder().append("위도 : ").append(currentLatLng.latitude).append(", 경도 : ").append(currentLatLng.longitude).toString();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= show marker =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void showMarkerToMap(Activity activity, GoogleMap googleMap, LatLng latLng) {

        MarkerOptions markerOptions = createMarkerOptions(activity, latLng);

        Log.d(LocationUpdateUtil.class.getSimpleName(), "onLocationResult: markerOptions = " + markerOptions);
        googleMap.clear();
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));

    }

    public static void showMarkerToMap(Activity activity, GoogleMap googleMap, Address address) {

        MarkerOptions markerOptions = createMarkerOptions(activity, address);

        Log.d(LocationUpdateUtil.class.getSimpleName(), "onLocationResult: markerOptions = " + markerOptions);
        googleMap.clear();
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), CAMERA_ZOOM));

    }

    public static void showMarkerListToMap() {

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= move =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static void moveLocation(GoogleMap googleMap, Address address) {

        googleMap.clear();
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                        new LatLng(address.getLatitude(), address.getLongitude()),
                        CAMERA_ZOOM
                )
        );
    }

    public static void moveLocation(GoogleMap googleMap, LatLng latLng) {

        googleMap.clear();
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM)
        );
    }
}
