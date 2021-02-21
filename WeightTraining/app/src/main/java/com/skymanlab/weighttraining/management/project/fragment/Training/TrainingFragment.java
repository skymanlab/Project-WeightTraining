package com.skymanlab.weighttraining.management.project.fragment.Training;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.SectionManager.TrainingSectionManager;
import com.skymanlab.weighttraining.management.user.data.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFT] TrainingFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private TrainingSectionManager sectionManager;

    // constructor
    public TrainingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrainingFragment newInstance() {
        TrainingFragment fragment = new TrainingFragment();
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
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final String METHOD_NAME = "[onViewCreated] ";
        super.onViewCreated(view, savedInstanceState);

        // top bar
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_training_title));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // section
        this.sectionManager = new TrainingSectionManager(this, view);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

    }
}