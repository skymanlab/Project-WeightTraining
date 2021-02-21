package com.skymanlab.weighttraining.management.FitnessCenter.data;

public class Member {

    // constant
    public static final int ACTIVE_STATE_ENTER = 1;
    public static final int ACTIVE_STATE_EXERCISE = 2;
    public static final int ACTIVE_STATE_EXIT = 3;

    // constant
    public static final String MEMBER_NUMBER = "memberNumber";
    public static final String USER_NAME = "userName";
    public static final String ACTIVE_STATE = "activeState";
    public static final String IS_DISCLOSED = "isDisclosed";

    // instance variable
    private long memberNumber;
    private String userName;
    private int activeState;
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

    public int getActiveState() {
        return activeState;
    }

    public void setActiveState(int activeState) {
        this.activeState = activeState;
    }

    public boolean getIsDisclosed() {
        return isDisclosed;
    }

    public void setIsDisclosed(boolean isDisclosed) {
        this.isDisclosed = isDisclosed;
    }
}
