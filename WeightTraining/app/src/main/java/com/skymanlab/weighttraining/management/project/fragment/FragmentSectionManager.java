package com.skymanlab.weighttraining.management.project.fragment;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentSectionManager {

    // instance variable
    private Fragment fragment;
    private View view;

    // constructor
    public FragmentSectionManager(Fragment fragment, View view) {
        this.fragment = fragment;
        this.view = view;
    }

    // getter
    public View getView() {
        return view;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
