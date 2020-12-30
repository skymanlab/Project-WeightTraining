package com.skymanlab.weighttraining.management.project.data;


import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.EquipmentType;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;
import com.skymanlab.weighttraining.management.user.data.User;

public class RightDataChecker {


    // constant
    private static final String CLASS_NAME = "[PD]_RightDataChecker";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= User =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] User 데이터 객체가 있고 할당받은 count 가 있는지 판별하여 그 결과를 true 혹은 false 를 반환한다.
     */
    public static boolean checkWhetherRightUser(User user) {

        final String METHOD_NAME = "[checkWhetherRightUser] ";

        // [lv/b]isRightUser : 정확한 user 냐?
        boolean isRightUser = false;

        // [check 1] : User 객체 있습니다.
        if (user != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : user 데이터는 생성되었습니다. 이제 내용만 확인하면 됩니다.  <=");

            // [check 2] : count 값을 할당 받은 회원이다.
            if (0 < user.getCount()) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 당신은 [" + user.getCount() + "] 번째 유저입니다. <=");

                // [lv/b]isRightUser : 이 앱에 등록한 회원이 맞군요!
                isRightUser = true;

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 넌 이 앱의 회원 아니야! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : User 객체도 생성되지 않았어! <=");
        } // [check 1]

        return isRightUser;
    } // End of method [checkWhetherRightUser]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Event =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] event 데이터 객체가 있고 할당 받은 count 값이 있는지 판별하여 그 결과를 true 혹은 false 를 반환한다.
     */
    public static boolean checkWhetherRightEvent(Event event) {

        final String METHOD_NAME = "[checkWhetherRightEvent] ";

        // [lv/b]isRightUser : 정확한 event 냐?
        boolean isRightEvent = false;

        // [check 1] : Event 객체 있습니다.
        if (event != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : event 데이터는 생성되었습니다. 이제 내용만 확인하면 됩니다.  <=");


            // [lv/b]isRightEvent : 너의 데이터가 등록되었어!
            isRightEvent = true;


        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : User 객체도 생성되지 않았어! <=");
        }// [check 1]


        return isRightEvent;
    } // End of method [checkWhetherRightEvent]


    /**
     * [method] Event 객체의 내용이 형식에 맞는지를 판별하여 그 결과를 true 혹은 false 로 반환한다.
     */
    public static boolean checkWhetherRightFormatOfEventData(Event event) {

        final String METHOD_NAME = "[checkWhetherRightFormatOfEventData] ";

        // [lv/b]isRightEventDate : event 객체의 모든 데이터가 형식이 올바르냐?
        boolean isRightEventDate = false;

        // [check 1] : event 객체가 생성되었다.
        if (event != null) {

            // [check 2] : event 객체의 모든 데이터 형식 검사
            if (!event.getEventName().equals("")
                    && !event.getMuscleArea().toString().equals("")
                    && !event.getEquipmentType().toString().equals("")
                    && !event.getGroupType().toString().equals("")
                    && (0 < event.getProperWeight())
                    && (0 < event.getMaxWeight())
            ) {

                return true;
            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : event 데이터의 형식이 맞지 않아! <=");
                return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : event 객체도 생성되지 않았어! <=");
            return false;
        } // [check 1]


    } // End of method [checkWhetherRightFormatOfEventData]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= MuscleAreaType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] muscleArea 가 CHEST, SHOULDER, LAT, LEG, ARM 중에 있는 지 판별하여 5가지 중 있으면 true 그 외의 값이면 false 를 반환한다.
     */
    public static boolean checkWhetherRightMuscleArea(MuscleArea muscleArea) {

        final String METHOD_NAME = "[checkWhetherRightMuscleArea] ";

        // [check 1] : muscleArea 객체가 있다.
        if (muscleArea != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : MuscleArea 객체는 생성되었습니다. 이제 내용만 확인하면 됩니다. <=");
            // [check 2] : CHEST, SHOULDER, LAT, LEG, ARM 중에 있음
            switch (muscleArea) {
                case CHEST:
                case SHOULDER:
                case LAT:
                case LEG:
                case ARM:
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/ : CHEST, SHOULDER, LAT, LEG, ARM 중에 하나입니다. <=");
                    return true;
                default:
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/default : 해당 값이 존재하지 않지! <=");
                    return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : MuscleArea 객체도 생성되지 않았어! <=");
            return false;
        } // [check 1]


    } // End of method [checkWhetherRightMuscleArea]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= EquipmentType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] equipmentType 가 BARBELL, DUMBBELL, MACHINE, CABLE, KETTLEBELL, FREE_WEIGHT 중 에 하나이면 true, 아니면 false
     */
    public static boolean checkWhetherRightEquipmentType(EquipmentType equipmentType) {

        final String METHOD_NAME = "[checkWhetherRightEquipmentType] ";

        // [check 1] : EquipmentType 객체가 생성되었다.
        if (equipmentType != null) {

            // [check 2] : BARBELL
            switch (equipmentType) {
                case BARBELL:
                case DUMBBELL:
                case MACHINE:
                case CABLE:
                case KETTLEBELL:
                case FREE_WEIGHT:
                    return true;
                default:
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : equipmentType 해당 목록 중에 없어! <=");
                    return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : equipmentType 객체도 생성되지 않았어! <=");
            return false;
        } // [check 1]

    } // End of method [checkWhetherRightEquipmentType]


    /**
     * [method] 해당 문자열이 BARBELL, DUMBBELL, MACHINE, CABLE, KETTLEBELL, FREE_WEIGHT 중 에 하나이면 true, 아니면 false
     *
     * @param equipmentType 문자열로 받은 EquipmentType
     * @return 해당 목록 중에 하나이면 true 이고 없으면 false 를 반환한다.
     */
    public static boolean checkWhetherRightEquipmentType(String equipmentType) {

        final String METHOD_NAME = "[checkWhetherRightEquipmentType] ";

        // [check 1] : equipmentType 객체가 생성되었다.
        if (equipmentType != null) {

            // [check 2] : equipmentType 문자열이 빈 문자열이 아니다.
            if (!equipmentType.equals("")) {

                // [check 3] : BARBELL
                switch (equipmentType) {
                    case "바벨":
                    case "덤벨":
                    case "머신":
                    case "케이블":
                    case "케틀벨":
                    case "맨몸":
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> EquipmentType 통과");
                        return true;
                    default:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/false : equipmentType 해당 목록 중에 없어! <=");
                        return false;
                } // [check 3]

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : equipmentType 빈 문자열이야! <=");
                return false;
            } // [check 2]
        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : equipmentType 객체도 생성되지 않았어! <=");
            return false;
        } // [check 1]

    } // End of method [checkWhetherRightEquipmentType]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= GroupType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] GroupType 의  A_GROUP, B_GROUP, C_GROUP, D_GROUP, E_GROUP 중에 하나가 있으면 true 아니면 false 를 반환한다.
     *
     * @param groupType GroupType 값
     * @return 목록 중에 있으면 true, 없으면 false 를 반환
     */
    public static boolean checkWhetherRightGroupType(GroupType groupType) {

        final String METHOD_NAME = "[checkWhetherRightGroupType] ";

        // [check 1] : groupType 객체가 생성되었는지
        if (groupType != null) {

            // [check 2] : A_GROUP
            switch (groupType) {
                case A_GROUP:
                case B_GROUP:
                case C_GROUP:
                case D_GROUP:
                case E_GROUP:
                    return true;
                default:
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : groupType 해당 목록 중에 없어! <=");
                    return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : groupType 객체도 생성되지 않았어! <=");
            return false;
        } // [check 1]

    } // End of method [checkWhetherRightGroupType]


    /**
     * [method] 해당 문자열이  A_GROUP, B_GROUP, C_GROUP, D_GROUP, E_GROUP 중에 하나가 있으면 true 아니면 false 를 반환한다.
     *
     * @param groupType GroupType 값
     * @return 목록 중에 있으면 true, 없으면 false 를 반환
     */
    public static boolean checkWhetherRightGroupType(String groupType) {

        final String METHOD_NAME = "[checkWhetherRightGroupType] ";

        // [check 1] : groupType 객체가 생성되었는지
        if (groupType != null) {

            // [check 2] : A_GROUP
            switch (groupType) {
                case "A 그룹":
                case "B 그룹":
                case "C 그룹":
                case "D 그룹":
                case "E 그룹":
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GroupType 통과");
                    return true;
                default:
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : groupType 해당 목록 중에 없어! <=");
                    return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : groupType 객체도 생성되지 않았어! <=");
            return false;
        } // [check 1]

    } // End of method [checkWhetherRightGroupType]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= MuscleAreaNextActivityType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] muscleAreaNextActivityType 이 EVENT_ADD, EVENT_LIST, EVENT_PROGRAM 중에 있는 값인지 판별하여, 포함되면 true 아니면 false 를 반환한다.
     */
    public static boolean checkWhetherRightMuscleAreaNextActivityType(MuscleAreaNextActivityType muscleAreaNextActivityType) {

        final String METHOD_NAME = "[checkWhetherRightMuscleAreaNextActivityType] ";

        // [check 1] : MuscleAreaNextActivityType 객체가 생성되었다.
        if (muscleAreaNextActivityType != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : MuscleAreaNextActivityType 객체는 생성되었습니다. 이제 내용만 확인하면 됩니다. <=");
            // [check 2] : EVENT_ADD, EVENT_LIST, EVENT_PROGRAM 중에 있음
            switch (muscleAreaNextActivityType) {
                case EVENT_ADD:
                case EVENT_LIST:
                case EVENT_PROGRAM:
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/ : EVENT_ADD, EVENT_LIST, EVENT_PROGRAM 중에 하나입니다. <=");
                    return true;
                default:
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/default : 해당 값이 존재하지 않지! <=");
                    return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : MuscleAreaNextActivityType 객체도 생성되지 않았어! <=");
            return false;
        } // [check 1]

    } // End of method [checkWhetherRightMuscleAreaNextActivityType]


}
