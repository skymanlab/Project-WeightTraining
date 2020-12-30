package com.skymanlab.weighttraining.management.project.data;

import android.content.Intent;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;


public class SessionManager {

    // constance
    private static final String CLASS_NAME = "[PD]_SessionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= user =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//
//    /**
//     * [method] intent 에 user 으로 추가한다.
//     */
//    public static void setUserInIntent(Intent intent, User user) {
//
//        // [lv/C]Intent : content 를 'user' 로 넣기
//        intent.putExtra("user", user);
//
//    } // End of method [setUserInIntent]
//
//
//    /**
//     * [method] intent 에 추가되어 넘어온 데이터 중 user 로 값 가져오기
//     */
//    public static User getUserInIntent(Intent intent) {
//
//        return (User) intent.getSerializableExtra("user");
//
//    } // End of method [getUserInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= event =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 event 으로 추가한다.
     */
    public static void setEventInIntent(Intent intent, Event event) {

        // [lv/C]Intent : content 를 'event' 로 넣기
        intent.putExtra("event", event);

    } // End of method [setEventInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 event 로 값 가져오기
     */
    public static Event getEventInIntent(Intent intent) {

        return (Event) intent.getSerializableExtra("event");

    } // End of method [getEventInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= muscleAreaNextActivityType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 muscleAreaNextActivityType 으로 추가한다.
     */
    public static void setMuscleAreaNextActivityTypeInIntent(Intent intent, MuscleAreaNextActivityType activityType) {

        // [lv/C]Intent : content 를 'user' 로 넣기
        intent.putExtra("muscleAreaNextActivityType", activityType);

    } // End of method [setActivityTypeInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 muscleAreaNextActivityType 로 값 가져오기
     */
    public static MuscleAreaNextActivityType getMuscleAreaNextActivityTypeInIntent(Intent intent) {

        return (MuscleAreaNextActivityType) intent.getSerializableExtra("muscleAreaNextActivityType");

    } // End of method [getMuscleAreaNextActivityTypeInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= muscleArea =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 muscleArea 로 추가하기
     */
    public static void setMuscleAreaInIntent(Intent intent, MuscleArea muscleArea) {

        // [lv/C]Intent : content 를 'muscleArea' 로 넣기
        intent.putExtra("muscleArea", muscleArea);

    } // End of method [setMuscleAreaInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 muscleArea 로 값 가져오기
     */
    public static MuscleArea getMuscleAreaInIntent(Intent intent) {

        return (MuscleArea) intent.getSerializableExtra("muscleArea");

    } // End of method [getMuscleAreaInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= eventArrayList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 eventArrayList 로 추가하기
     */
    public static void setEventArrayListInIntent(Intent intent, ArrayList<Event> eventArrayList) {

        // [lv/C]Intent : content 를 'eventArrayList' 로 넣기
        intent.putExtra("eventArrayList", eventArrayList);

    } // End of method [setEventArrayListInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 eventArrayList 로 값 가져오기
     */
    public static ArrayList<Event> getEventArrayListInIntent(Intent intent) {

        return (ArrayList<Event>) intent.getSerializableExtra("eventArrayList");

    } // End of method [getEventArrayListInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= selectedEventArrayList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 selectedEventArrayList 로 추가하기
     */
    public static void setSelectedEventArrayListInIntent(Intent intent, ArrayList<Event> eventArrayList) {

        // [lv/C]Intent : content 를 'eventArrayList' 로 넣기
        intent.putExtra("selectedEventArrayList", eventArrayList);

    } // End of method [setEventArrayListInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 selectedEventArrayList 로 값 가져오기
     */
    public static ArrayList<Event> getSelectedEventArrayListInIntent(Intent intent) {

        return (ArrayList<Event>) intent.getSerializableExtra("selectedEventArrayList");

    } // End of method [getEventArrayListInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= noSelectedEventArrayList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 noSelectedEventArrayList 로 추가하기
     */
    public static void setNoSelectedEventArrayListInIntent(Intent intent, ArrayList<Event> eventArrayList) {

        // [lv/C]Intent : content 를 'eventArrayList' 로 넣기
        intent.putExtra("noSelectedEventArrayList", eventArrayList);

    } // End of method [setNoSelectedEventArrayListInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 noSelectedEventArrayList 로 값 가져오기
     */
    public static ArrayList<Event> getNoSelectedEventArrayListInIntent(Intent intent) {

        return (ArrayList<Event>) intent.getSerializableExtra("noSelectedEventArrayList");

    } // End of method [getNoSelectedEventArrayListInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= setNumber =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 setNumber 로 추가하기
     */
    public static void setSetNumberInIntent(Intent intent, int setNumber) {

        // [lv/C]Intent : content 를 'setNumber' 로 넣기
        intent.putExtra("setNumber", setNumber);

    } // End of method [setSetNumberInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 setNumber 로 값 가져오기
     */
    public static int getSetNumberInIntent(Intent intent) {

        return intent.getIntExtra("setNumber", 0);

    } // End of method [getSetNumberInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= restTime =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 restTime 로 추가하기
     */
    public static void setRestTimeInIntent(Intent intent, int restTime) {

        // [lv/C]Intent : content 를 'restTime' 로 넣기
        intent.putExtra("restTime", restTime);

    } // End of method [setRestTimeInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 restTime 로 값 가져오기
     */
    public static int getRestTimeInIntent(Intent intent) {

        return intent.getIntExtra("restTime", 0);

    } // End of method [getRestTimeInIntent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= eventPosition =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] intent 에 eventPosition 로 추가하기
     */
    public static void setEventPositionInIntent(Intent intent, int eventPosition) {

        // [lv/C]Intent : content 를 'eventPosition' 로 넣기
        intent.putExtra("eventPosition", eventPosition);

    } // End of method [setEventPositionInIntent]


    /**
     * [method] intent 에 추가되어 넘어온 데이터 중 eventPosition 로 값 가져오기
     */
    public static int getEventPositionInIntent(Intent intent) {

        return intent.getIntExtra("eventPosition", 0);

    } // End of method [getEventPositionInIntent]



}


