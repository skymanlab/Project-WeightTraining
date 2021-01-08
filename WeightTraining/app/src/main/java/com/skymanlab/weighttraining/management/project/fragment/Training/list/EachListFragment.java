package com.skymanlab.weighttraining.management.project.fragment.Training.list;

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
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.SectionManager.EachListSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EachListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EachListFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTL] EachListFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String MUSCLE_AREA = "muscleArea";

    // instance variable
    private MuscleArea muscleArea;

    // instance variable
    private EachListSectionManager sectionManager;

    // constructor
    public EachListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EachListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EachListFragment newInstance(MuscleArea muscleArea) {
        EachListFragment fragment = new EachListFragment();
        Bundle args = new Bundle();
        args.putSerializable(MUSCLE_AREA, muscleArea);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.muscleArea = (MuscleArea) getArguments().getSerializable(MUSCLE_AREA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_each_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> muscleArea " + muscleArea);

        // [iv/C]EachListSectionManager : each list fragment section manager
        this.sectionManager = new EachListSectionManager(getActivity(), view, getActivity().getSupportFragmentManager(), this, this.muscleArea);
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();
    }
}