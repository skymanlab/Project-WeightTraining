package com.skymanlab.weighttraining.management.project.fragment.Training.SectionManager;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.ListFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.ProgramFragment;

public class TrainingSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // instance variable
    private MaterialCardView eventList;
    private MaterialCardView eventProgram;

    // constructor
    public TrainingSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [iv/C]MaterialCardView : eventList connect
        this.eventList = (MaterialCardView) getView().findViewById(R.id.f_training_event_list);

        // [iv/C]MaterialCardView : eventProgram connect
        this.eventProgram = (MaterialCardView) getView().findViewById(R.id.f_training_event_program);

    }

    @Override
    public void initWidget() {

        // [iv/C]MaterialCardView : eventList click listener
        this.eventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [FragmentTransaction] [transaction] move 'list' fragment
                FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_home_content_wrapper, ListFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        // [iv/C]MaterialCardView : eventProgram click listener
        this.eventProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [FragmentTransaction] [transaction] move 'program' fragment
                FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_home_content_wrapper, ProgramFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
