package com.skyman.weighttrainingpro.management.event.listview;

import android.app.Activity;
import android.widget.ListView;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.ArrayList;

public class EventItemLvManager {

    // constructor
    private static final String CLASS_NAME = "[EL]_EventItemLvManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private EventItemLvAdapter eventItemLvAdapter;

    // instance variable
    private Activity activity;
    private ListView targetListView;

    // instance variable
    private User user;
    private MuscleArea muscleArea;

    // instance variable
    private EventDbManager eventDbManager;
    private ArrayList<Event> eventArrayList;

    // constructor
    public EventItemLvManager(Activity activity, ListView targetListView, User user, MuscleArea muscleArea, EventDbManager eventDbManager) {
        this.activity = activity;
        this.targetListView = targetListView;
        this.user = user;
        this.muscleArea = muscleArea;
        this.eventDbManager = eventDbManager;
    }


    /**
     * [method] eventArrayList 를 EventItemLvAdapter 를 통해 targetListView 에 mapping 하기
     */
    public void setListView() {

        final String METHOD_NAME = "[setListView] ";

        // [check 1] : 옳은 user 데이터이다.
        if (RightDataChecker.checkWhetherRightUser(this.user)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 옳은 user 데이터입니다. <=");

            // [check 2] : 옳은 muscleArea 데이터이다.
            if (RightDataChecker.checkWhetherRightMuscleArea(this.muscleArea)) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 옳은 muscleArea 데이터입니다. <=");
                // [method] : event table 에서 userCount 와 muscleArea 가 같은 모든 event 데이터 가져오기
                setEventArrayList();

                // [check 3] : 위에서 가져온 eventArrayList 에 데이터가 있다.
                if (this.eventArrayList.size() != 0) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/true : targetListView 에 데이터를 뿌려줄거야! <=");
                    // [iv/C]EventItemLvAdapter : ListView adapter 생성
                    this.eventItemLvAdapter = new EventItemLvAdapter(activity, eventDbManager);

                    // [iv/C]EventItemLvAdapter : eventItemLvAdapter 의 eventArrayList 를 mapping 하기
                    this.eventItemLvAdapter.setDataOfEventArrayList(this.eventArrayList);

                    // [iv/C]ListView : targetListView 와 eventItemLvAdapter 를 연결하기
                    this.targetListView.setAdapter(this.eventItemLvAdapter);

                    // [iv/C]ListView : targetListView 를 마지막을 보여준다.
                    this.targetListView.setSelection(this.eventArrayList.size());

                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/false : eventArrayList 에 데이터가 없으니 ListView 에 뿌려줄 필요가 없어! <=");
                } // [check 3]

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 정확하지 않은 muscle 데이터입니다. <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 정확하지 않은 user 데이터입니다. <=");
        } // [check 1]

    } // End of method [setListView]


    /**
     * [method] muscleArea 로 event table 에서 해당 muscleArea 로 저장되어 있는 데이터를 가져오기
     */
    private void setEventArrayList() {

        final String METHOD_NAME = "[setEventArrayList] ";

        // [iv/C]ArrayList<Event> : event table manager 를 통해 muscleArea 값으로 데이터를 가져온다.
        this.eventArrayList = this.eventDbManager.loadContentBy(this.user.getCount(), this.muscleArea);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** eventArrayList 의 데이터를 확인하겠습니다. ***");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.eventArrayList);

    } // End of method [setEventArrayList]

}
