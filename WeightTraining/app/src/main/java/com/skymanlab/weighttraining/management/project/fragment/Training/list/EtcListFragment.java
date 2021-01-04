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
import com.skymanlab.weighttraining.management.project.fragment.Training.list.SectionManager.EachEventListSectionManager;
import com.skymanlab.weighttraining.management.user.data.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EtcListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EtcListFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTL] EtcListFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private EachEventListSectionManager sectionManager;

    public EtcListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EtcListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EtcListFragment newInstance() {
        EtcListFragment fragment = new EtcListFragment();
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
        return inflater.inflate(R.layout.fragment_etc_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]EachEventListSectionManager : etc fragment section manager
//        this.sectionManager = new EachEventListSectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager(), MuscleArea.SHOULDER);
//        this.sectionManager.mappingWidget();
//        this.sectionManager.initWidget();

    }
}