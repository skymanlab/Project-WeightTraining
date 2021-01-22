package com.skymanlab.weighttraining.management.project.fragment.Intervene;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InterventionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterventionFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFI] InterveneFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private FragmentTopBarManager topBarManager;

    public InterventionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InterveneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterventionFragment newInstance() {
        InterventionFragment fragment = new InterventionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intervene, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String METHOD_NAME = "[onViewCreated] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ intervene fragment ++++++++++++");

        // [FragmentTopBarManager] [topBarManager] this is 'Intervene' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_intervene_title));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

    }
}