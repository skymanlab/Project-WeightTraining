package com.skymanlab.weighttraining.management.developer;

import android.util.Log;

import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.ArrayList;

public class LogManager {

    // constant
    public static final Display ALL_LOG_DISPLAY_POWER = Display.ON;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= default =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 해당 문자열을 로그로 나타내기
     */
    public static void displayLog(Display CLASS_LOG_DISPLAY_POWER, String className, String methodName, String content) {

        // [check 1] : 화면에 출력해도 됨
        if (checkWhetherDisplayLog(CLASS_LOG_DISPLAY_POWER)) {

            // [lv/C]StringBuilder : methodName 과 content 를 합치기
            StringBuilder logContent = new StringBuilder();
            logContent.append(methodName);
            logContent.append(content);

            // [lv/C]Log : Log 출력하기
            Log.d(className, logContent.toString());

        } // [check 1]
    } // End of method [displayLog]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= user =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 해당 user 데이터의 정보를 로그로 나타내기
     */
    public static void displayLogOfUser(Display CLASS_LOG_DISPLAY_POWER, String className, String methodName, User user) {

        // [check 1] : 화면에 출력해도 됨
        if (checkWhetherDisplayLog(CLASS_LOG_DISPLAY_POWER)) {

            // [lv/C]StringBuilder : methodName 과 content 를 합치기
            StringBuilder logContent = new StringBuilder();
            logContent.append(methodName);
            logContent.append("==>> User 내용 확인/start <<==");

            // [lv/C]Log : Log 출력하기
            Log.d(className, logContent.toString());

            // [check 2] : user 객체가 있다.
            if (user != null) {

                Log.d(className, "0. count = " + user.getCount());
                Log.d(className, "1. name = " + user.getName());
                Log.d(className, "2. email = " + user.getEmail());

            } // [check 2]

            Log.d(className, "==>> User 내용 확인/end <<==");
        } // [check 1]

    } // End of method [displayLogOfUser]


    /**
     * [method] 해당 user 데이터의 정보를 로그로 나타내기
     */
    public static void displayLogOfUser(Display CLASS_LOG_DISPLAY_POWER, String className, String methodName, ArrayList<User> userArrayList) {

        // [check 1] : 화면에 출력해도 됨
        if (checkWhetherDisplayLog(CLASS_LOG_DISPLAY_POWER)) {

            // [lv/C]StringBuilder : methodName 과 content 를 합치기
            StringBuilder logContent = new StringBuilder();
            logContent.append(methodName);
            logContent.append("==>> User 내용 확인/start <<==");

            // [lv/C]Log : Log 출력하기
            Log.d(className, logContent.toString());

            // [cycle 1] : userArrayList 의 size 만큼
            for (int index = 0; index < userArrayList.size(); index++) {

                Log.d(className, "0. count = " + userArrayList.get(index).getCount());
                Log.d(className, "1. name = " + userArrayList.get(index).getName());
                Log.d(className, "2. email = " + userArrayList.get(index).getEmail());

                Log.d(className, " =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ");

            } // [cycle 1]

            Log.d(className, "==>> User 내용 확인/end <<==");
        } // [check 1]
    } // End of method [displayLog]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= event =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 해당 event 데이터의 정보를 로그로 나타내기
     */
    public static void displayLogOfEvent(Display CLASS_LOG_DISPLAY_POWER, String className, String methodName, Event event) {

        // [check 1] : 화면에 출력해도 됨
        if (checkWhetherDisplayLog(CLASS_LOG_DISPLAY_POWER)) {

            // [lv/C]StringBuilder : methodName 과 content 를 합치기
            StringBuilder logContent = new StringBuilder();
            logContent.append(methodName);
            logContent.append("==>> Event 내용 확인/start <<==");

            // [lv/C]Log : Log 출력하기
            Log.d(className, logContent.toString());

            // [check 2] : event 객체가 있다.
            if (event != null) {

                Log.d(className, "1. event name = " + event.getEventName());
                Log.d(className, "3. muscle area = " + event.getMuscleArea());
                Log.d(className, "4. equipment type = " + event.getEquipmentType());
                Log.d(className, "5. group type = " + event.getGroupType());
                Log.d(className, "6. proper weight = " + event.getProperWeight());
                Log.d(className, "7. max weight = " + event.getMaxWeight());

            } // [check 2]

            Log.d(className, "==>> Event 내용 확인/end <<==");
        } // [check 1]
    } // End of method [displayLog]


    /**
     * [method] 해당 event 데이터의 정보를 로그로 나타내기
     */
    public static void displayLogOfEvent(Display CLASS_LOG_DISPLAY_POWER, String className, String methodName, ArrayList<Event> eventArrayList) {

        // [check 1] : 화면에 출력해도 됨
        if (checkWhetherDisplayLog(CLASS_LOG_DISPLAY_POWER)) {

            // [lv/C]StringBuilder : methodName 과 content 를 합치기
            StringBuilder logContent = new StringBuilder();
            logContent.append(methodName);
            logContent.append("==>> Event 내용 확인/start <<==");

            // [lv/C]Log : Log 출력하기
            Log.d(className, logContent.toString());

            // [check 2] : event 객체가 있다.
            if (eventArrayList != null) {

                // [cycle 1] : eventArrayList 의 size 만큼
                for (int index = 0; index < eventArrayList.size(); index++) {

                    Log.d(className, "1. event name = " + eventArrayList.get(index).getEventName());
                    Log.d(className, "3. muscle area = " + eventArrayList.get(index).getMuscleArea());
                    Log.d(className, "4. equipment type = " + eventArrayList.get(index).getEquipmentType());
                    Log.d(className, "5. group type = " + eventArrayList.get(index).getGroupType());
                    Log.d(className, "6. proper weight = " + eventArrayList.get(index).getProperWeight());
                    Log.d(className, "7. max weight = " + eventArrayList.get(index).getMaxWeight());

                    Log.d(className, " =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ");

                } // [cycle 1]

            } // [check 2]

            Log.d(className, "==>> Event 내용 확인/end <<==");
        } // [check 1]
    } // End of method [displayLog]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] [check] Log 를 출력해도 되는지 판별하여 true 또는 false 를 반환한다.
     */
    private static boolean checkWhetherDisplayLog(Display CLASS_LOG_DISPLAY_POWER) {

        // [check 1] : 화면 출력을 할 것이다.
        if (ALL_LOG_DISPLAY_POWER == Display.ON) {

            // [check 2] : 이 클래스의 내용을 출력을 할 것이다.
            if (CLASS_LOG_DISPLAY_POWER == Display.ON) {

                return true;
            } else {
                return false;
            } // [check 2]

        } else {
            return false;
        } // [check 1]

    } // End of method [checkWhetherDisplayLog]


}
