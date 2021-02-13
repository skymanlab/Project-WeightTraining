package com.skymanlab.weighttraining.management.FitnessCenter.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class UserFitnessCenter implements Serializable {

    // constant
    public static final String FITNESS_CENTER_KEY = "fitnessCenterKey";
    public static final String CONTRACT_DATE = "contractDate";
    public static final String EXPIRY_DATE = "expiryDate";
    public static final String ATTENDANCE_DATE_LIST = "attendanceDateList";
    public static final String IS_DISCLOSED = "isDisclosed";

    // instance variable
    private String fitnessCenterKey;                // fitnessCenter node 의 key
    private Date contractDate;                      // 계약일
    private Date expiryDate;                        // 만기일
    private ArrayList<String> attendanceDateList;   // 출석날짜
    private boolean isDisclosed;                    // 공개할거냐?


}
