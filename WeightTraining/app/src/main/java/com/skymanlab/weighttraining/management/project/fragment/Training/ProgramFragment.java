package com.skymanlab.weighttraining.management.project.fragment.Training;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramFragment extends Fragment {

    // instance variable
    private FragmentTopBarManager topBarManager;

    // constructor
    public ProgramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventProcessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgramFragment newInstance(String param1, String param2) {
        ProgramFragment fragment = new ProgramFragment();
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
        return inflater.inflate(R.layout.fragment_program, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager :
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), "프로그램");
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

    }
}