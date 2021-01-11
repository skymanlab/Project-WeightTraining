package com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skymanlab.weighttraining.management.project.fragment.Training.program.DirectSelectionFragment;

import java.util.ArrayList;

public class DirectSelectionFragmentPagerAdapter extends FragmentStateAdapter {

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;

    // constructor
    public DirectSelectionFragmentPagerAdapter(@NonNull Fragment fragment, ArrayList<DirectSelectionFragment> fragmentArrayList) {
        super(fragment);
        this.fragmentArrayList = fragmentArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return fragmentArrayList.get(position);

    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
