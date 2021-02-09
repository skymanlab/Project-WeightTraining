package com.skymanlab.weighttraining.management.event.program.data;

import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.io.Serializable;
import java.util.ArrayList;

public class Program implements Serializable {

    // instance variable
    private String key;
    private String nickName;
    private ArrayList<MuscleArea> muscleAreaList;
    private ArrayList<DetailProgram> detailProgramList;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public ArrayList<MuscleArea> getMuscleAreaList() {
        return muscleAreaList;
    }

    public void setMuscleAreaList(ArrayList<MuscleArea> muscleAreaList) {
        this.muscleAreaList = muscleAreaList;
    }

    public ArrayList<DetailProgram> getDetailProgramList() {
        return detailProgramList;
    }

    public void setDetailProgramList(ArrayList<DetailProgram> detailProgramList) {
        this.detailProgramList = detailProgramList;
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
