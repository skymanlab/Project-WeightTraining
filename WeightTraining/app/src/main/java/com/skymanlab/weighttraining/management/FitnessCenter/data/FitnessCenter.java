package com.skymanlab.weighttraining.management.FitnessCenter.data;

public class FitnessCenter {

    // constant
    public static final String NAME = "name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MEMBER_COUNTER = "memberCounter";
    public static final String MEMBER_LIST = "memberList";

    // instance variable
    private String name;
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
