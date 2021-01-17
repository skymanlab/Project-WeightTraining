package com.skymanlab.weighttraining.management.event.program.data;

public class DetailProgram {

    // instance variable
    private String key;
    private int setNumber;
    private int restTimeMinute;
    private int restTimeSecond;

    // getter, setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
