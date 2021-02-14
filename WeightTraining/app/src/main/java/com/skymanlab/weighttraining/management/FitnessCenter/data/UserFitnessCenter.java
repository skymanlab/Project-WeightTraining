package com.skymanlab.weighttraining.management.FitnessCenter.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class UserFitnessCenter implements Serializable {

    // constant
    public static final String MEMBER_NUMBER = "memberNumber";
    public static final String FITNESS_CENTER_NAME = "fitnessCenterName";
    public static final String FIRST_ADDRESS = "firstAddress";
    public static final String SECOND_ADDRESS = "secondAddress";
    public static final String THIRD_ADDRESS = "thirdAddress";
    public static final String CONTRACT_DATE = "contractDate";
    public static final String EXPIRY_DATE = "expiryDate";
    public static final String ATTENDANCE_DATE_LIST = "attendanceDateList";
    public static final String IS_DISCLOSED = "isDisclosed";
    public static final String IS_ALLOWED_ACCESS_NOTIFICATION = "isAllowedAccessNotification";

    // instance variable
    private long memberNumber;                      // 회원 번호
    private String fitnessCenterName;               // 피트니스 센터 이름
    private String firstAddress;                    // 주소 1
    private String secondAddress;                   // 주소 2
    private String thirdAddress;                    // 주소 3
    private String contractDate;                    // 계약일
    private String expiryDate;                      // 만기일
    private ArrayList<String> attendanceDateList;   // 출석날짜 리스트
    private boolean isDisclosed;                    // 공개할거냐? : true=공개, false=비공개
    private boolean isAllowedAccessNotification;    // 접근 알림을 허용할거냐? : true=알려줌, false=안알려줌


    // getter, setter
    public long getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(long memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getFitnessCenterName() {
        return fitnessCenterName;
    }

    public void setFitnessCenterName(String fitnessCenterName) {
        this.fitnessCenterName = fitnessCenterName;
    }

    public String getFirstAddress() {
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

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ArrayList<String> getAttendanceDateList() {
        return attendanceDateList;
    }

    public void setAttendanceDateList(ArrayList<String> attendanceDateList) {
        this.attendanceDateList = attendanceDateList;
    }

    public boolean getIsDisclosed() {
        return isDisclosed;
    }

    public void setIsDisclosed(boolean disclosed) {
        isDisclosed = disclosed;
    }

    public boolean getIsAllowedAccessNotification() {
        return isAllowedAccessNotification;
    }

    public void setIsAllowedAccessNotification(boolean allowedAccessNotification) {
        isAllowedAccessNotification = allowedAccessNotification;
    }
}
