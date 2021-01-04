package com.skymanlab.weighttraining.management.event.program.util;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;

import java.util.ArrayList;

public class DirectEventSelectionUtil {

    // constant
    private static final String CLASS_NAME = "[EPU] DirectEventSelectionUtil";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private GroupingEventData groupingEventData;

    // instance variable
    private boolean[] isCheckedAGroup;
    private boolean[] isCheckedBGroup;
    private boolean[] isCheckedCGroup;
    private boolean[] isCheckedDGroup;
    private boolean[] isCheckedEGroup;

    // instance variable
    private ArrayList<Event> directSelectedEventArrayList;
    private ArrayList<Event> noSelectedEventArrayList;


    public boolean[] getIsCheckedAGroup() {

        final String METHOD_NAME = "[getIsCheckedAGroup] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>> A Group <<<<<<<<<<<");
        for (int index = 0; index < isCheckedAGroup.length; index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> isChecked = " + isCheckedAGroup[index]);

        }

        return isCheckedAGroup;
    }

    public void setIsCheckedAGroup(boolean[] isCheckedAGroup) {
        this.isCheckedAGroup = isCheckedAGroup;
    }

    public boolean[] getIsCheckedBGroup() {

        final String METHOD_NAME = "[getIsCheckedBGroup] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>> B Group <<<<<<<<<<<");
        for (int index = 0; index < isCheckedBGroup.length; index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> isChecked = " + isCheckedBGroup[index]);

        }

        return isCheckedBGroup;
    }

    public void setIsCheckedBGroup(boolean[] isCheckedBGroup) {
        this.isCheckedBGroup = isCheckedBGroup;
    }

    public boolean[] getIsCheckedCGroup() {

        final String METHOD_NAME = "[getIsCheckedCGroup] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>> C Group <<<<<<<<<<<");
        for (int index = 0; index < isCheckedCGroup.length; index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> isChecked = " + isCheckedCGroup[index]);

        }

        return isCheckedCGroup;
    }

    public void setIsCheckedCGroup(boolean[] isCheckedCGroup) {
        this.isCheckedCGroup = isCheckedCGroup;
    }

    public boolean[] getIsCheckedDGroup() {

        final String METHOD_NAME = "[getIsCheckedDGroup] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>> D Group <<<<<<<<<<<");
        for (int index = 0; index < isCheckedDGroup.length; index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> isChecked = " + isCheckedDGroup[index]);

        }

        return isCheckedDGroup;
    }

    public void setIsCheckedDGroup(boolean[] isCheckedDGroup) {
        this.isCheckedDGroup = isCheckedDGroup;
    }

    public boolean[] getIsCheckedEGroup() {

        final String METHOD_NAME = "[getIsCheckedEGroup] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>> E Group <<<<<<<<<<<");
        for (int index = 0; index < isCheckedEGroup.length; index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> isChecked = " + isCheckedEGroup[index]);

        }

        return isCheckedEGroup;
    }

    public void setIsCheckedEGroup(boolean[] isCheckedEGroup) {
        this.isCheckedEGroup = isCheckedEGroup;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= groupingEventData =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    public GroupingEventData getGroupingEventData() {
        return groupingEventData;
    }

    public void setGroupingEventData(GroupingEventData groupingEventData) {
        this.groupingEventData = groupingEventData;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= directSelectionEventArrayList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] [getter] DirectSelectedEventArrayList
     */
    public ArrayList<Event> getDirectSelectedEventArrayList() {

        final String METHOD_NAME = "[getDirectSelectedEventArrayList] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** DirectSelectedEventArrayList 확인 ****");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, directSelectedEventArrayList);

        return directSelectedEventArrayList;

    } // End of method [getDirectSelectedEventArrayList]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= noSelectedEventArrayList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    /**
     * [method] [getter] noSelectedEventArrayList
     */
    public ArrayList<Event> getNoSelectedEventArrayList() {


        final String METHOD_NAME = "[getNoSelectedEventArrayList] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** noSelectedEventArrayList 확인 ****");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, noSelectedEventArrayList);


        return noSelectedEventArrayList;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= direct selection util =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    /**
     * [method] 직접 선택한 리스트를 설정한다.
     */
    public void selectDirectEvent() {

        final String METHOD_NAME = "[selectDirectEvent] ";

        // [check 1] : aGroupEventArrayList, bGroupEventArrayList, cGroupEventArrayList, dGroupEventArrayList, eGroupEventArrayList 의 객체가 생성되었다.
        if ((this.groupingEventData.getAGroupEventArrayList() != null)
                && (this.groupingEventData.getBGroupEventArrayList() != null)
                && (this.groupingEventData.getCGroupEventArrayList() != null)
                && (this.groupingEventData.getDGroupEventArrayList() != null)
                && (this.groupingEventData.getEGroupEventArrayList() != null)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 모든 group 별 eventArrayList 가 입력되었습니다. <=");

            // [iv/C]ArrayList<Event> : directSelectedEventArrayList 객체 생성
            this.directSelectedEventArrayList = new ArrayList<>();

            // [iv/C]ArrayList<Event> : noSelectedEventArrayList 객체 생성
            this.noSelectedEventArrayList = new ArrayList<>();

            // [iv/C]ArrayList<Event> : aGroupEventArrayList 에서 체크된 것들 만 directSelectedEventArrayList 에 추가한다.
            this.directSelectedEventArrayList.addAll(getCheckedEventList(this.groupingEventData.getAGroupEventArrayList(), this.isCheckedAGroup));

            // [iv/C]ArrayList<Event> : bGroupEventArrayList 에서 체크된 것들 만 directSelectedEventArrayList 에 추가한다.
            this.directSelectedEventArrayList.addAll(getCheckedEventList(this.groupingEventData.getBGroupEventArrayList(), this.isCheckedBGroup));

            // [iv/C]ArrayList<Event> : cGroupEventArrayList 에서 체크된 것들 만 directSelectedEventArrayList 에 추가한다.
            this.directSelectedEventArrayList.addAll(getCheckedEventList(this.groupingEventData.getCGroupEventArrayList(), this.isCheckedCGroup));

            // [iv/C]ArrayList<Event> : dGroupEventArrayList 에서 체크된 것들 만 directSelectedEventArrayList 에 추가한다.
            this.directSelectedEventArrayList.addAll(getCheckedEventList(this.groupingEventData.getDGroupEventArrayList(), this.isCheckedDGroup));

            // [iv/C]ArrayList<Event> : eGroupEventArrayList 에서 체크된 것들 만 directSelectedEventArrayList 에 추가한다.
            this.directSelectedEventArrayList.addAll(getCheckedEventList(this.groupingEventData.getEGroupEventArrayList(), this.isCheckedEGroup));

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 일단 Group 별 eventArrayList 를 입력해주세요! <=");
        } // [check 1]

    } // End of method [selectDirectEvent]


    /**
     * [method] 그룹의 isChecked 를 체크하여 선택된 Event 목록만 반환한다.
     */
    private ArrayList<Event> getCheckedEventList(ArrayList<Event> groupEventArrayList, boolean[] isChecked) {

        // [lv/C]ArrayList<Event> : 체크된 목록만 추가하여 반환한다.
        ArrayList<Event> checkedEventArrayList = new ArrayList<>();

        // [cycle 1] : groupEventArrayList 의 size 와 isChecked 의 length 는 같다.
        for (int index = 0; index < isChecked.length; index++) {

            // [check 1] : isChecked 가 true 이다.
            if (isChecked[index]) {

                // [lv/C]ArrayList<Event> : groupEventArrayList 에서 체크된 index 번째의 데이터만 추가한다.
                checkedEventArrayList.add(groupEventArrayList.get(index));

            } else {

                // [iv/C]ArrayList<Event> : groupEventArrayList 에서 체크되지 않은 index 번째의 데이터는 noSelectedEventArrayList 에 추가한다.
                this.noSelectedEventArrayList.add(groupEventArrayList.get(index));

            } // [check 1]

        } // [cycle 1]


        return checkedEventArrayList;
    } // End of method [getCheckedEventList]


}
