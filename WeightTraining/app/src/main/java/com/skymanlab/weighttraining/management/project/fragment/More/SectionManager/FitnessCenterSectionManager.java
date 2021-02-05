package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.GoogleMapManager;
import com.skymanlab.weighttraining.management.project.ApiManager.LocationUpdateManager;
import com.skymanlab.weighttraining.management.project.ApiManager.SearchUtil;

public class FitnessCenterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFM] FitnessCenterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private SearchView addressSearchView;

    // instance variable : google map api
    private GoogleMap gMap;
    private GoogleMapManager googleMapManager;

    // constructor
    public FitnessCenterSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // getter
    public GoogleMapManager getGoogleMapManager() {
        return googleMapManager;
    }

    @Override
    public void connectWidget() {

        // [SearchView] [addressSearchView]
        this.addressSearchView = (SearchView) getView().findViewById(R.id.f_fitness_center_address_search);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // google map api : 구글 맵 가져오기
        SupportMapFragment mapFragment = (SupportMapFragment) getFragment().getChildFragmentManager().findFragmentById(R.id.f_fitness_center_map);
        mapFragment.getMapAsync(createOnMapReadyCallback());

        // [SearchView] [addressSearchView]
        this.addressSearchView.setFocusable(true);
        this.addressSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< query text submit > query = " + query);
                if (gMap != null)
                    SearchUtil.searchAddress(getFragment().getActivity(), gMap, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private OnMapReadyCallback createOnMapReadyCallback() {
        final String METHOD_NAME = "[getOnMapReadyCallback] ";
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

}
