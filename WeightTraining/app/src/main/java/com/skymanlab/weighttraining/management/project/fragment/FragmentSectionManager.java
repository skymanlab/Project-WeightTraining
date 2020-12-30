package com.skymanlab.weighttraining.management.project.fragment;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.FragmentManager;

public class FragmentSectionManager {

    // instance variable
    private Activity activity;
    private View view;
    //    private User user;
    private FragmentManager fragmentManager;

    // constructor
//    public FragmentSectionManager(Activity activity, View view, User user) {
//        this.activity = activity;
//        this.view = view;
//        this.user = user;
//    }

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

    public FragmentSectionManager(View view) {
        this.view = view;
//        this.user = user;
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

    //
//    public User getUser() {
//        return user;
//    }


}
