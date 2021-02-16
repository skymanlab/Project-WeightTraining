package com.skymanlab.weighttraining.management.FitnessCenter.data;

public class Member {

    // constant
    public static final String MEMBER_NUMBER = "memberNumber";
    public static final String USER_NAME = "userName";
    public static final String IS_ACTIVE = "isActive";
    public static final String IS_DISCLOSED = "isDisclosed";

    // instance variable
    private long memberNumber;
    private String userName;
    private boolean isActive;
    private boolean isDisclosed;

    // getter, setter
    public long getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(long memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDisclosed() {
        return isDisclosed;
    }

    public void setIsDisclosed(boolean isDisclosed) {
        this.isDisclosed = isDisclosed;
    }
}
