package com.skymanlab.weighttraining.management.user.data;

import java.io.Serializable;

public class Attendance implements Serializable {

    // constant
    public static final String DATE = "date";

    // instance variable
    private String date;

    // getter, setter
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
