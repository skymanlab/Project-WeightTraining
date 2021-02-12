package com.skymanlab.weighttraining.management.program.data;


import android.util.Log;

import com.skymanlab.weighttraining.management.event.data.Event;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupingEventData implements Serializable {

    // instance variable
    private ArrayList<Event> aGroupEventArrayList;
    private ArrayList<Event> bGroupEventArrayList;
    private ArrayList<Event> cGroupEventArrayList;
    private ArrayList<Event> dGroupEventArrayList;
    private ArrayList<Event> eGroupEventArrayList;

    // getter,setter
    public ArrayList<Event> getAGroupEventArrayList() {
        return aGroupEventArrayList;
    }

    public void setAGroupEventArrayList(ArrayList<Event> aGroupEventArrayList) {
        this.aGroupEventArrayList = aGroupEventArrayList;
    }

    public ArrayList<Event> getBGroupEventArrayList() {
        return bGroupEventArrayList;
    }

    public void setBGroupEventArrayList(ArrayList<Event> bGroupEventArrayList) {
        this.bGroupEventArrayList = bGroupEventArrayList;
    }

    public ArrayList<Event> getCGroupEventArrayList() {
        return cGroupEventArrayList;
    }

    public void setCGroupEventArrayList(ArrayList<Event> cGroupEventArrayList) {
        this.cGroupEventArrayList = cGroupEventArrayList;
    }

    public ArrayList<Event> getDGroupEventArrayList() {
        return dGroupEventArrayList;
    }

    public void setDGroupEventArrayList(ArrayList<Event> dGroupEventArrayList) {
        this.dGroupEventArrayList = dGroupEventArrayList;
    }

    public ArrayList<Event> getEGroupEventArrayList() {
        return eGroupEventArrayList;
    }

    public void setEGroupEventArrayList(ArrayList<Event> eGroupEventArrayList) {
        this.eGroupEventArrayList = eGroupEventArrayList;
    }

    public int getAllSize() {
        String TAG = "GroupingEventData";

        Log.d(TAG, "getAllSize: a group = "  + aGroupEventArrayList.size());
        Log.d(TAG, "getAllSize: b group = "  + bGroupEventArrayList.size());
        Log.d(TAG, "getAllSize: c group = " + cGroupEventArrayList.size());
        Log.d(TAG, "getAllSize: d group = " + dGroupEventArrayList.size());
        Log.d(TAG, "getAllSize: e group = " + eGroupEventArrayList.size());

        return aGroupEventArrayList.size() + bGroupEventArrayList.size() + cGroupEventArrayList.size() + dGroupEventArrayList.size() + eGroupEventArrayList.size();
    }
}
