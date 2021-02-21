package com.skymanlab.weighttraining.management.user.data;

import java.io.Serializable;

public class UserTraining implements Serializable {

    // constant
    public static final String SQUAT ="squat";
    public static final String DEADLIFT = "deadlift";
    public static final String BENCH_PRESS = "benchPress";

    // instance variable
    private int squat;
    private int deadlift;
    private int benchPress;

    // getter, setter
    public int getSquat() {
        return squat;
    }

    public void setSquat(int squat) {
        this.squat = squat;
    }

    public int getDeadlift() {
        return deadlift;
    }

    public void setDeadlift(int deadlift) {
        this.deadlift = deadlift;
    }

    public int getBenchPress() {
        return benchPress;
    }

    public void setBenchPress(int benchPress) {
        this.benchPress = benchPress;
    }
}
