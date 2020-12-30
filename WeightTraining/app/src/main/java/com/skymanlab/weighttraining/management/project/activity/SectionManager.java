package com.skymanlab.weighttraining.management.project.activity;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.user.data.User;

public class SectionManager {

    // instance variable
    private Activity activity;
    private FirebaseUser firebaseUser;
    private MuscleArea muscleArea;

    // constructor
    public SectionManager(Activity activity, FirebaseUser firebaseUser) {
        this.activity = activity;
        this.firebaseUser = firebaseUser;
    }

    // constructor
    public SectionManager(Activity activity) {
        this.activity = activity;
    }

    // constructor
    public SectionManager(Activity activity, FirebaseUser firebaseUser, MuscleArea muscleArea) {
        this.activity = activity;
        this.firebaseUser = firebaseUser;
        this.muscleArea = muscleArea;
    }

    // method : getter
    public Activity getActivity() {
        return activity;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public MuscleArea getMuscleArea() {
        return muscleArea;
    }

}
