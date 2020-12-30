package com.skyman.weighttrainingpro.management.project.activity;

import android.app.Activity;

import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

public class SectionManager {

    // instance variable
    private Activity activity;
    private User user;
    private MuscleArea muscleArea;

    // constructor
    public SectionManager(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    // constructor
    public SectionManager(Activity activity) {
        this.activity = activity;
    }

    // constructor
    public SectionManager(Activity activity, User user, MuscleArea muscleArea) {
        this.activity = activity;
        this.user = user;
        this.muscleArea = muscleArea;
    }

    // method : getter
    public Activity getActivity() {
        return activity;
    }

    public User getUser() {
        return user;
    }

    public MuscleArea getMuscleArea() {
        return muscleArea;
    }

}
