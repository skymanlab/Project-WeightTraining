package com.skymanlab.weighttraining.management.program.data;

import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.io.Serializable;

public class DetailProgram implements Serializable {

    // constructor
    public static final String ORDER = "order";
    public static final String MUSCLE_AREA = "muscleArea";
    public static final String EVENT_KEY = "eventKey";
    public static final String EVENT_NAME = "eventName";
    public static final String SET_NUMBER = "setNumber";
    public static final String REST_TIME_MINUTE = "restTimeMinute";
    public static final String REST_TIME_SECOND = "restTimeSecond";

    // instance variable
    private int order;
    private MuscleArea muscleArea;
    private String eventKey;
    private String eventName;
    private int setNumber;
    private int restTimeMinute;
    private int restTimeSecond;

    // getter, setter
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public MuscleArea getMuscleArea() {
        return muscleArea;
    }

    public void setMuscleArea(MuscleArea muscleArea) {
        this.muscleArea = muscleArea;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public int getRestTimeMinute() {
        return restTimeMinute;
    }

    public void setRestTimeMinute(int restTimeMinute) {
        this.restTimeMinute = restTimeMinute;
    }

    public int getRestTimeSecond() {
        return restTimeSecond;
    }

    public void setRestTimeSecond(int restTimeSecond) {
        this.restTimeSecond = restTimeSecond;
    }
}
