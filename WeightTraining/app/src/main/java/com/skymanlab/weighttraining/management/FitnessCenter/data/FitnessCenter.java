package com.skymanlab.weighttraining.management.FitnessCenter.data;

import android.location.Address;

public class FitnessCenter {

    // constant
    public static final String NAME = "name";
    public static final String FIRST_ADDRESS = "firstAddress";
    public static final String SECOND_ADDRESS = "secondAddress";
    public static final String THIRD_ADDRESS = "thirdAddress";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MEMBER_COUNTER = "memberCounter";
    public static final String MEMBER_LIST = "memberList";

    // instance variable
    private String name;
    private String firstAddress;
    private String secondAddress;
    private String thirdAddress;
    private double latitude;
    private double longitude;
    private long memberCounter;

    // getter, setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

        return firstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    public String getThirdAddress() {
        return thirdAddress;
    }

    public void setThirdAddress(String thirdAddress) {
        this.thirdAddress = thirdAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getMemberCounter() {
        return memberCounter;
    }

    public void setMemberCounter(long memberCounter) {
        this.memberCounter = memberCounter;
    }
}
