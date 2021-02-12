package com.skymanlab.weighttraining.management.program.data;

import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.io.Serializable;
import java.util.ArrayList;

public class Program implements Serializable {

    // constructor
    public static final String KEY = "key";
    public static final String NICK_NAME = "nickName";
    public static final String MUSCLE_AREA_LIST = "muscleAreaList";
    public static final String FINAL_ORDER_LIST = "finalOrderList";
    public static final String DETAIL_PROGRAM_LIST = "detailProgramList";
    public static final String TOTAL_SET_NUMBER = "totalSetNumber";
    public static final String TOTAL_EVENT_NUMBER = "totalEventNumber";
    public static final String COUNT = "count";


    // instance variable
    private String key;
    private String nickName;
    private ArrayList<MuscleArea> muscleAreaList;
    private ArrayList<Event> finalOrderList;
    private ArrayList<DetailProgram> detailProgramList;
    private int totalSetNumber;
    private int totalEventNumber;
    private long count;

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

    public ArrayList<Event> getFinalOrderList() {
        return finalOrderList;
    }

    public void setFinalOrderList(ArrayList<Event> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }

    public ArrayList<DetailProgram> getDetailProgramList() {
        return detailProgramList;
    }

    public void setDetailProgramList(ArrayList<DetailProgram> detailProgramList) {
        this.detailProgramList = detailProgramList;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
