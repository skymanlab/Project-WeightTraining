package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.GoogleMapManager;
import com.skymanlab.weighttraining.management.project.ApiManager.LocationUpdateManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionUtil;
import com.skymanlab.weighttraining.management.project.ApiManager.SearchUtil;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FitnessCenterRegisterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] FitnessCenterRegisterSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable : google map api
    private GoogleMap gMap;
    private GoogleMapManager googleMapManager;

    // instance variable
    private SearchView addressSearchView;
    private TextView address;
    private LinearLayout contractDateWrapper;
    private TextView contractDate;
    private LinearLayout expiryDateWrapper;
    private TextView expiryDate;

    // constructor
    public FitnessCenterRegisterSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // getter
    public GoogleMapManager getGoogleMapManager() {
        return googleMapManager;
    }

    @Override
    public void connectWidget() {

        // [SearchView] [addressSearchView]
        this.addressSearchView = (SearchView) getView().findViewById(R.id.f_fitness_center_register_search);

        // [TextView] [address] widget connect
        this.address = (TextView) getView().findViewById(R.id.f_fitness_center_register_address);

        // [LinearLayout] [contractDateWrapper] widget connect
        this.contractDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitness_center_register_contract_date_wrapper);

        // [TextView] [contractDate] widget connect
        this.contractDate = (TextView) getView().findViewById(R.id.f_fitness_center_register_contract_date);

        // [LinearLayout] [expiryDateWrapper] widget connect
        this.expiryDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitness_center_register_expiry_date_wrapper);

        // [TextView] [expiryDate] widget connect
        this.expiryDate = (TextView) getView().findViewById(R.id.f_fitness_center_register_expiry_date);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // google map api : 구글 맵 가져오기
        SupportMapFragment mapFragment = (SupportMapFragment) getFragment().getChildFragmentManager().findFragmentById(R.id.f_fitness_center_register_map);
        mapFragment.getMapAsync(createOnMapReadyCallback());

        // [SearchView] [addressSearchView]
        this.addressSearchView.setFocusable(true);
        this.addressSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< query text submit > query = " + query);

                if (gMap != null) {

                    if (PermissionUtil.hasPermissionList(getFragment().getActivity(), PermissionManager.LOCATION_PERMISSION_LIST)) {

                        address.post(new Runnable() {
                            @Override
                            public void run() {

                                address.setText(SearchUtil.searchAddress(getFragment().getActivity(), gMap, query));
                            }
                        });

//                        address.setText(SearchUtil.searchAddress(getFragment().getActivity(), gMap, query));

                    } else {

                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // [LinearLayout] [contractDateWrapper] click listener
        this.contractDateWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 날짜 가져오기
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // [DatePickerDialog] [dialog]
                DatePickerDialog dialog = new DatePickerDialog(getFragment().getContext(), createOnDateSetListener(contractDate), year, month, dayOfMonth);
                dialog.show();
            }
        });


        // [LinearLayout] [expiryDateWrapper] click listener
        this.expiryDateWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 날짜 가져오기
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // [DatePickerDialog] [dialog]
                DatePickerDialog dialog = new DatePickerDialog(getFragment().getContext(), createOnDateSetListener(expiryDate), year, month, dayOfMonth);
                dialog.show();
            }
        });

    }


    /**
     * @return
     */
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


    private DatePickerDialog.OnDateSetListener createOnDateSetListener(TextView date) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // Date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY년 MM월 dd일");

                // Date format setting
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                // TextView 의 setting / text / background
                date.setText(simpleDateFormat.format(calendar.getTime()));
                date.setBackgroundResource(R.color.colorBackgroundWhite);
            }
        };
    }


    private FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        return  new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {
                return null;
            }
        };
    }
}
