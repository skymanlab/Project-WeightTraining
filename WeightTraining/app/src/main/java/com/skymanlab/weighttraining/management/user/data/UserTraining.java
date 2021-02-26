package com.skymanlab.weighttraining.management.user.data;

import java.io.Serializable;

public class UserTraining implements Serializable {

    // constant
    public static final String LEVEL = "level";
    public static final String THREE_MAJOR_MEASUREMENTS = "threeMajorMeasurements";
    public static final String SQUAT = "squat";
    public static final String DEADLIFT = "deadlift";
    public static final String BENCH_PRESS = "benchPress";

    // instance variable
    private String level;
    private int squat;
    private int deadlift;
    private int benchPress;

    // getter, setter
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

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
