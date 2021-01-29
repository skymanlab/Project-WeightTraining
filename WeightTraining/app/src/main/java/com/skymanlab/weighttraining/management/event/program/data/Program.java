package com.skymanlab.weighttraining.management.event.program.data;

import java.io.Serializable;

public class Program implements Serializable {

    // instance variable
    private String key;
    private String programName;
    private int setNumber;
    private int restTimeMinute;
    private int restTimeSecond;
    private int totalSetNumber;
    private int totalEventNumber;

    // getter, setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
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

    public int getTotalSetNumber() {
        return totalSetNumber;
    }

    public void setTotalSetNumber(int totalSetNumber) {
        this.totalSetNumber = totalSetNumber;
    }

    public int getTotalEventNumber() {
        return totalEventNumber;
    }

    public void setTotalEventNumber(int totalEventNumber) {
        this.totalEventNumber = totalEventNumber;
    }
}
