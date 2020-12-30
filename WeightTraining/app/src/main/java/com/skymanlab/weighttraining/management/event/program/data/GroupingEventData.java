package com.skymanlab.weighttraining.management.event.program.data;


import com.skymanlab.weighttraining.management.event.data.Event;

import java.util.ArrayList;

public class GroupingEventData {

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
}
