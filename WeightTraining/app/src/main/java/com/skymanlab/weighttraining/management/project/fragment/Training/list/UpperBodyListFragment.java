package com.skymanlab.weighttraining.management.project.fragment.Training.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.SectionManager.EachEventListSectionManager;
import com.skymanlab.weighttraining.management.user.data.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpperBodyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpperBodyListFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTL] UpperBodyListFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private EachEventListSectionManager sectionManager;

    public UpperBodyListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UpperBodyListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpperBodyListFragment newInstance() {
        UpperBodyListFragment fragment = new UpperBodyListFragment();
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
        return inflater.inflate(R.layout.fragment_upper_body_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]EachEventListSectionManager : shoulder fragment section manager
        this.sectionManager = new EachEventListSectionManager(getActivity(), view, getActivity().getSupportFragmentManager(), MuscleArea.LEG);
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();
    }
}