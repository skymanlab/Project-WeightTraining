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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.SectionManager.UserTrainingInfoSectionManager;
import com.skymanlab.weighttraining.management.user.data.UserTraining;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserTrainingInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserTrainingInfoFragment extends Fragment {

    // constant
    private static final String MY_TRAINING = "myTraining";

    // instance variable
    private UserTraining myTraining;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private UserTrainingInfoSectionManager sectionManager;

    // constructor
    public UserTrainingInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyTrainingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTrainingInfoFragment newInstance(UserTraining myTraining) {
        UserTrainingInfoFragment fragment = new UserTrainingInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(MY_TRAINING, myTraining);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.myTraining = (UserTraining) getArguments().getSerializable(MY_TRAINING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_training_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // top bar
        topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_userTrainingInfo_title));
        topBarManager.connectWidget();
        topBarManager.initWidget();

        // section
        sectionManager = new UserTrainingInfoSectionManager(this, view);
        sectionManager.setMyTraining(myTraining);
        sectionManager.connectWidget();
        sectionManager.initWidget();

        // top bar : start button
        topBarManager.initWidgetOfStartButton(
                null,
                new FragmentTopBarManager.StartButtonListener() {
                    @Override
                    public AlertDialog setStartButtonClickListener() {
                        getActivity().getSupportFragmentManager().popBackStack();
                        return null;
                    }
                }
        );

    }
}