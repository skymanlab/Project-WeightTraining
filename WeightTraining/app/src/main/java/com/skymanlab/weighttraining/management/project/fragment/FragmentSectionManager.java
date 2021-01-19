package com.skymanlab.weighttraining.management.project.fragment;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentSectionManager {

    // instance variable
    private Activity activity;
    private View view;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    // constructor
    public FragmentSectionManager(View view, FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
    }

    public FragmentSectionManager(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public FragmentSectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        this.activity = activity;
        this.view = view;
        this.fragmentManager = fragmentManager;
    }

    public FragmentSectionManager(View view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    public FragmentSectionManager(Activity activity, View view, Fragment fragment) {
        this.activity = activity;
        this.view = view;
        this.fragment = fragment;
    }

    public FragmentSectionManager(View view) {
        this.view = view;
    }

    // getter
    public Activity getActivity() {
        return activity;
    }

    public View getView() {
        return view;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
