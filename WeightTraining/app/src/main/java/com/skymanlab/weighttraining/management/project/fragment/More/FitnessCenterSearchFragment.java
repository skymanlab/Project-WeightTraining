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
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

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

        // [FragmentTopBarManager] [topBarManager] this is 'more' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_fitness_center_search_title));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [FitnessCenterSectionManager] [sectionManager] this is 'more' fragment's section manager.
        this.sectionManager = new FitnessCenterSearchSectionManager(this, view);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 를 생성하여 start button click listener 설정하기
        this.topBarManager.setStartButtonListener(
                new FragmentTopBarManager.StartButtonListener() {
                    @Override
                    public AlertDialog setStartButtonClickListener() {

                        // [method] fragment manager 를 통해 back stack 에서 pop!
                        getActivity().getSupportFragmentManager().popBackStack();
                        return null;
                    }
                }
        );
        this.topBarManager.initWidgetOfStartButton(null);
        this.topBarManager.setEndButtonListener(this.sectionManager.newEndButtonListenerInstance());
        this.topBarManager.initWidgetOfEndButton(getString(R.string.f_fitness_center_search_top_bar_end_button));
    }

    @Override
    public void onPause() {
        super.onPause();
        final String METHOD_NAME = "[onPause] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Update util > 이 존재하나요? " + this.sectionManager.getGoogleMapManager());

        this.sectionManager.getGoogleMapManager().stopLocationUpdate();
        this.sectionManager.getGoogleMapManager().stopFitnessCenterMarkerManager();
    }

}