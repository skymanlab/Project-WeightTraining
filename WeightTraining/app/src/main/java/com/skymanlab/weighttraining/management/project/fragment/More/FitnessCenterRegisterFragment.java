package com.skymanlab.weighttraining.management.project.fragment.More;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitnessCenterRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitnessCenterRegisterFragment extends Fragment {


    public FitnessCenterRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FitnessCenterRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitnessCenterRegisterFragment newInstance() {
        FitnessCenterRegisterFragment fragment = new FitnessCenterRegisterFragment();
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
        return inflater.inflate(R.layout.fragment_fitness_center_register, container, false);
    }
}