package com.skymanlab.weighttraining.management.project.fragment.More;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.SectionManager.FitnessCenterSearchSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitnessCenterSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitnessCenterSearchFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFM] FitnessCenterRegisterFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private FitnessCenterSearchSectionManager sectionManager;

    // constructor
    public FitnessCenterSearchFragment() {
        // Required empty public constructor
    }

    // getter
    public FitnessCenterSearchSectionManager getSectionManager() {
        return sectionManager;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FitnessCenterRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitnessCenterSearchFragment newInstance() {
        FitnessCenterSearchFragment fragment = new FitnessCenterSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_center_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        // top bar
        topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_fitness_center_search_title));
        topBarManager.connectWidget();
        topBarManager.initWidget();

        // section
        sectionManager = new FitnessCenterSearchSectionManager(this, view);
        sectionManager.connectWidget();
        sectionManager.initWidget();

        // top bar : start button
        topBarManager.initWidgetOfStartButton(
                null,
                new FragmentTopBarManager.StartButtonListener() {
                    @Override
                    public AlertDialog setStartButtonClickListener() {

                        // [method] fragment manager 를 통해 back stack 에서 pop!
                        getActivity().getSupportFragmentManager().popBackStack();
                        return null;
                    }
                }
        );

        // top bar : end button
        topBarManager.initWidgetOfEndButton(
                getString(R.string.f_fitness_center_search_top_bar_end_button),
                sectionManager.newEndButtonListenerInstance()
        );
    }

    @Override
    public void onPause() {
        final String METHOD_NAME = "[onPause] ";
        super.onPause();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "========================= 구글 맵에 대한 '위치 업데이트'와 '마커 매니저' 에 대한 중지를 요청합니다. =========================");
        sectionManager.getGoogleMapSettingManager().stopLocationUpdate();
        sectionManager.getGoogleMapSettingManager().stopFitnessCenterMarkerManager();
    }

}