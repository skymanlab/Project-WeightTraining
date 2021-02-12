package com.skymanlab.weighttraining.management.program.util;

import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.program.data.GroupingEventData;

import java.util.ArrayList;

public class GroupingEventUtil  {

    // instance variable
    private ArrayList<Event> eventArrayList;
    private GroupingEventData groupingEvent;

    // constructor
    public GroupingEventUtil(ArrayList<Event> eventArrayList) {
        this.eventArrayList = eventArrayList;
    }

    // getter
    public ArrayList<Event> getEventArrayList() {
        return eventArrayList;
    }

    public GroupingEventData getGroupingEvent() {
        return groupingEvent;
    }

    /**
     * [method] eventArrayList 를 targetMuscleType 의 유형별로 나눠서 각각 저장한다.
     */
    public void classifyEventArrayListToGroupType() {

        // [iv/C]GroupingEvent :
        groupingEvent = new GroupingEventData();

        // [iv/C]ArrayList<Event> : 객체 생성
        ArrayList<Event> aGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> bGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> cGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> dGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> eGroupEventArrayList = new ArrayList<>();

        // [cycle 1] : eventArrayList 의 size 만큼
        for (int index = 0; index < this.eventArrayList.size(); index++) {

            // [check 1] : targetMuscleType 이 뭐냐?
            switch (this.eventArrayList.get(index).getGroupType()) {

                case A_GROUP:
                    // [iv/C]ArrayList<Event> : aGroupEventArrayList 에 추가하기
                    aGroupEventArrayList.add(this.eventArrayList.get(index));
                    break;
                case B_GROUP:
                    // [iv/C]ArrayList<Event> : bGroupEventArrayList 에 추가하기
                    bGroupEventArrayList.add(this.eventArrayList.get(index));
                    break;
                case C_GROUP:
                    // [iv/C]ArrayList<Event> : cGroupEventArrayList 에 추가하기
                    cGroupEventArrayList.add(this.eventArrayList.get(index));
                    break;
                case D_GROUP:
                    // [iv/C]ArrayList<Event> : dGroupEventArrayList 에 추가하기
                    dGroupEventArrayList.add(this.eventArrayList.get(index));
                    break;
                case E_GROUP:
                    // [iv/C]ArrayList<Event> : eGroupEventArrayList 에 추가하기
                    eGroupEventArrayList.add(this.eventArrayList.get(index));
                    break;
                default:
                    break;

            } // [check 1]

        } // [cycle 1]

        // [iv/C]GroupingEvent : 각 그룹 별로 설정하기
        this.groupingEvent.setAGroupEventArrayList(aGroupEventArrayList);
        this.groupingEvent.setBGroupEventArrayList(bGroupEventArrayList);
        this.groupingEvent.setCGroupEventArrayList(cGroupEventArrayList);
        this.groupingEvent.setDGroupEventArrayList(dGroupEventArrayList);
        this.groupingEvent.setEGroupEventArrayList(eGroupEventArrayList);

    } // End of method [classifyEventArrayListToGroupType]



    /**
     * [method] eventArrayList 를 targetMuscleType 의 유형별로 나눠서 각각 저장한다.
     */
    public static GroupingEventData classifyEventArrayListToGroupType(ArrayList<Event> eventArrayList) {

        // [iv/C]GroupingEvent :
        GroupingEventData groupingEventData = new GroupingEventData();

        // [iv/C]ArrayList<Event> : 객체 생성
        ArrayList<Event> aGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> bGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> cGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> dGroupEventArrayList = new ArrayList<>();
        ArrayList<Event> eGroupEventArrayList = new ArrayList<>();

        // [cycle 1] : eventArrayList 의 size 만큼
        for (int index = 0; index < eventArrayList.size(); index++) {

            // [check 1] : targetMuscleType 이 뭐냐?
            switch (eventArrayList.get(index).getGroupType()) {

                case A_GROUP:
                    // [iv/C]ArrayList<Event> : aGroupEventArrayList 에 추가하기
                    aGroupEventArrayList.add(eventArrayList.get(index));
                    break;
                case B_GROUP:
                    // [iv/C]ArrayList<Event> : bGroupEventArrayList 에 추가하기
                    bGroupEventArrayList.add(eventArrayList.get(index));
                    break;
                case C_GROUP:
                    // [iv/C]ArrayList<Event> : cGroupEventArrayList 에 추가하기
                    cGroupEventArrayList.add(eventArrayList.get(index));
                    break;
                case D_GROUP:
                    // [iv/C]ArrayList<Event> : dGroupEventArrayList 에 추가하기
                    dGroupEventArrayList.add(eventArrayList.get(index));
                    break;
                case E_GROUP:
                    // [iv/C]ArrayList<Event> : eGroupEventArrayList 에 추가하기
                    eGroupEventArrayList.add(eventArrayList.get(index));
                    break;
                default:
                    break;

            } // [check 1]

        } // [cycle 1]

        // [iv/C]GroupingEventData : 각 그룹 별로 설정하기
        groupingEventData.setAGroupEventArrayList(aGroupEventArrayList);
        groupingEventData.setBGroupEventArrayList(bGroupEventArrayList);
        groupingEventData.setCGroupEventArrayList(cGroupEventArrayList);
        groupingEventData.setDGroupEventArrayList(dGroupEventArrayList);
        groupingEventData.setEGroupEventArrayList(eGroupEventArrayList);

        return groupingEventData;

    } // End of method [classifyEventArrayListToGroupType]


}
