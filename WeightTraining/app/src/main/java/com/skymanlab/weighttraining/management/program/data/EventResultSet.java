package com.skymanlab.weighttraining.management.program.data;

import com.skymanlab.weighttraining.management.event.data.Event;

import java.io.Serializable;
import java.util.ArrayList;

public class EventResultSet implements Serializable {

    // instance variable
    private ArrayList<Event> selectedEventArrayList;
    private ArrayList<Event> noSelectedEventArrayList;

    // constructor
    public EventResultSet () {
        this.selectedEventArrayList = new ArrayList<>();
        this.noSelectedEventArrayList = new ArrayList<>();
    }

    public ArrayList<Event> getSelectedEventArrayList() {
        return selectedEventArrayList;
    }

    public void setSelectedEventArrayList(ArrayList<Event> selectedEventArrayList) {
        this.selectedEventArrayList = selectedEventArrayList;
    }

    public void addSelectedEventArrayList(Event event) {
        this.selectedEventArrayList.add(event);
    }

    public ArrayList<Event> getNoSelectedEventArrayList() {
        return noSelectedEventArrayList;
    }

    public void setNoSelectedEventArrayList(ArrayList<Event> noSelectedEventArrayList) {
        this.noSelectedEventArrayList = noSelectedEventArrayList;
    }

    public void addNoSelectedEventArrayList(Event event) {
        this.noSelectedEventArrayList.add(event);
    }

    public void initArrayList() {
        this.selectedEventArrayList = new ArrayList<>();
        this.noSelectedEventArrayList = new ArrayList<>();
    }
}
