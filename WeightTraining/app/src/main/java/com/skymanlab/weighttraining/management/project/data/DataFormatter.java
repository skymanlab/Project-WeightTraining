package com.skymanlab.weighttraining.management.project.data;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;

public class DataFormatter {

    // constant
    private static final String CLASS_NAME = "[PD] DataFormatter";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= welcome user format =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 회원의 count 값을 받아서 welcome_user 포맷의 문자열로 만들어서 반환한다.
     */
    public static String setWelcomeUserFormat(long count) {

        // [lv/C]StringBuilder : 포맷 문자열로 
        StringBuilder welcomeUserFormat = new StringBuilder();
        welcomeUserFormat.append("[");
        welcomeUserFormat.append(count);
        welcomeUserFormat.append("]");

        return welcomeUserFormat.toString();
    } // End of method [setWelcomeUserFormat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= top title format =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] MuscleArea 와 MuscleAreaNextActivityType 으로 TopTitle 을 반환한다.
     */
    public static String setTopTitleFormat(MuscleAreaNextActivityType muscleAreaNextActivityType, MuscleArea muscleArea) {

        final String METHOD_NAME = "[setTopTitleFormat] ";

        // [lv/C]StringBuilder : MuscleAreaNextActivityType 과 MuscleArea 으로 TopTile 결정한다.
        StringBuilder topTitle = new StringBuilder();

        // [lv/C]StringBuilder : muscleArea 를 판별하여 해당 문자열을 추가
        topTitle.append(DataManager.convertHanguleOfMuscleArea(muscleArea));
        topTitle.append("운동");

        // [check 1] : muscleAreaNextActivityType 이 뭐냐?
        switch (muscleAreaNextActivityType) {

            case EVENT_ADD:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EVENT_ADD  <=");
                // [lv/C]StringBuilder : muscleAreaNextActivityType 를 판별하여 해당 문자열을 추가
                topTitle.append(" 추가");
                break;
            case EVENT_LIST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EVENT_LIST  <=");
                // [lv/C]StringBuilder : muscleAreaNextActivityType 를 판별하여 해당 문자열을 추가
                topTitle.append(" 목록");
                break;
            case EVENT_PROGRAM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EVENT_PROGRAM  <=");
                // [lv/C]StringBuilder : muscleAreaNextActivityType 를 판별하여 해당 문자열을 추가
                topTitle.append(" 프로그램");
                break;
            default:
                break;

        } // [check 1]

        return topTitle.toString();
    } // End of method [setTopTitleFormat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= weight format =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] HOME 에서 넘어온 muscleAreaNextActivityType 값을 받아서 subtitle 포맷의 문자열로 만들어서 반환한다.
     */
    public static String setSubtitleFormat(MuscleAreaNextActivityType muscleAreaNextActivityType) {

        // [lv/C]StringBuilder : 포맷 문자열로 
        StringBuilder subtitleFormat = new StringBuilder();
        subtitleFormat.append("<");
        subtitleFormat.append(DataManager.convertHanguleOfMuscleAreaNextActivityType(muscleAreaNextActivityType));
        subtitleFormat.append(">");

        return subtitleFormat.toString();
    } // End of method [setSubtitleFormat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= weight format =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 해당 문자열을 받아서 중량 포맷의 문자열로 만들어서 반환한다.
     */
    public static String setWeightFormat(String weight) {

        // [lv/C]StringBuilder : 포맷 문자열로 
        StringBuilder weightFormat = new StringBuilder();
        weightFormat.append(weight);
        weightFormat.append("kg");

        return weightFormat.toString();
    } // End of method [setWeightFormat]


    /**
     * [method] 해당 문자열을 받아서 중량 포맷의 문자열로 만들어서 반환한다.
     */
    public static String setWeightFormat(int weight) {

        // [lv/C]StringBuilder : 포맷 문자열로 
        StringBuilder weightFormat = new StringBuilder();
        weightFormat.append(weight);
        weightFormat.append("kg");

        return weightFormat.toString();
    } // End of method [setWeightFormat]


    /**
     * [method] 해당 문자열을 받아서 중량 포맷의 문자열로 만들어서 반환한다.
     */
    public static String setWeightFormat(float weight) {

        // [lv/C]StringBuilder : 포맷 문자열로 
        StringBuilder weightFormat = new StringBuilder();
        weightFormat.append(weight);
        weightFormat.append("kg");

        return weightFormat.toString();
    } // End of method [setWeightFormat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= email format =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 해당 문자열을 받아서 email 포맷의 문자열로 만들어서 반환한다.
     */
    public static String setEmailFormat(String emailName, String emailHost) {

        // [lv/C]StringBuilder : 포맷 문자열로
        StringBuilder emailFormat = new StringBuilder();
        emailFormat.append(emailName);
        emailFormat.append("@");
        emailFormat.append(emailHost);

        return emailFormat.toString();
    } // End of method [setEmailFormat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= time format =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] millisecond 를 분, 초로 구분하여 time 포멧으로 변경한다.
     */
    public static String setTimeFormat(int millisecond) {

        // [lv/C]StringBuilder : 포맷 문자열로
        StringBuilder timeFormat = new StringBuilder();
        timeFormat.append(String.format("%02d", ((millisecond / (1000 * 60)) % 60)));
        timeFormat.append(" : ");
        timeFormat.append(String.format("%02d", ((millisecond / 1000) % 60)));

        return timeFormat.toString();

    } // End of method [setTimeFormat]


    /**
     * [method] millisecond 를 분, 초로 구분하여 time 포멧으로 변경한다.
     */
    public static String setTimeFormat(int minute, int second) {

        // [lv/C]StringBuilder : 포맷 문자열로
        StringBuilder timeFormat = new StringBuilder();
        timeFormat.append(String.format("%02d", minute));
        timeFormat.append(" : ");
        timeFormat.append(String.format("%02d", second));

        return timeFormat.toString();

    } // End of method [setTimeFormat]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= set number format =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * setNumber 를 'n회' format 으로 변환하여 반환한다.
     * @param setNumber
     * @return
     */
    public static String setSetNumberFormat(int setNumber) {

        return new StringBuilder().append(setNumber).append("세트").toString();
    }
}

