package com.skymanlab.weighttraining.management.project.data;

import com.skymanlab.weighttraining.EventAddActivity;
import com.skymanlab.weighttraining.EventListActivity;
import com.skymanlab.weighttraining.EventProgramActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.type.EquipmentType;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.data.type.MuscleAreaNextActivityType;

public class DataManager {

    // constant
    private static final String CLASS_NAME = "[PD] DataManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 문자열 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 해당 문자열의 앞뒤 공백을 제거하고 그 문자열을 반환한다.
     */
    public static String removeWhitespace(String content) {

        return content.trim();

    } // End of method [removeWhiteSpace]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= EquipmentType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] [EquipmentType] 문자열 형태의 값을 EquipmentType 값으로 변환한다.
     *
     * @param equipmentType 문자열 형태의 EquipmentType 값
     * @return 변경된 EquipmentType 값
     */
    public static EquipmentType convertEquipmentType(String equipmentType) {

        final String METHOD_NAME = "[convertEquipmentType] ";

        // [lv/C]EquipmentType : 문자열 값을 EquipmentType 값으로 변경
        EquipmentType convertedEquipmentType = null;

        // [check 1] : 어떤 값이냐?
        switch (equipmentType) {

            case "BARBELL":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/BARBELL 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : "BARBELL" -> EquipmentType.BARBELL
                convertedEquipmentType = EquipmentType.BARBELL;
                break;

            case "DUMBBELL":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/DUMBBELL 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : "DUMBBELL" -> EquipmentType.DUMBBELL
                convertedEquipmentType = EquipmentType.DUMBBELL;
                break;

            case "MACHINE":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/MACHINE 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : "MACHINE" -> EquipmentType.MACHINE
                convertedEquipmentType = EquipmentType.MACHINE;
                break;

            case "CABLE":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/CABLE 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : "CABLE" -> EquipmentType.CABLE
                convertedEquipmentType = EquipmentType.CABLE;
                break;

            case "KETTLEBELL":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/KETTLEBELL 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : "KETTLEBELL" -> EquipmentType.KETTLEBELL
                convertedEquipmentType = EquipmentType.KETTLEBELL;
                break;

            case "FREE_WEIGHT":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/FREE_WEIGHT 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : "FREE_WEIGHT" -> EquipmentType.FREE_WEIGHT
                convertedEquipmentType = EquipmentType.FREE_WEIGHT;
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 없는 데이터입니다. <=");
                break;

        } // [check 1]

        return convertedEquipmentType;
    } // End of method [convertEquipmentType]


    /**
     * [method] [EquipmentType] 위치에 해당하는 EquipmentType 값으로 변환한다.
     *
     * @param position EquipmentType 의 위치 (Spinner 의 위치)
     * @return 변경된 EquipmentType 값
     */
    public static EquipmentType convertEquipmentType(int position) {

        final String METHOD_NAME = "[convertEquipmentType] ";

        // [lv/C]EquipmentType : 위치에 따른 EquipmentType 값을
        EquipmentType convertedEquipmentType = null;

        // [check 1] : 어떤 값이냐?
        switch (position) {

            case 0:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/BARBELL 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : 0 -> EquipmentType.BARBELL
                convertedEquipmentType = EquipmentType.BARBELL;
                break;

            case 1:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/DUMBBELL 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : 1 -> EquipmentType.DUMBBELL
                convertedEquipmentType = EquipmentType.DUMBBELL;
                break;

            case 2:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/MACHINE 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : 2 -> EquipmentType.MACHINE
                convertedEquipmentType = EquipmentType.MACHINE;
                break;

            case 3:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/CABLE 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : 3 -> EquipmentType.CABLE
                convertedEquipmentType = EquipmentType.CABLE;
                break;

            case 4:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/KETTLEBELL 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : 4 -> EquipmentType.KETTLEBELL
                convertedEquipmentType = EquipmentType.KETTLEBELL;
                break;

            case 5:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/FREE_WEIGHT 가 입력되었습니다. <=");
                // [lv/C]EquipmentType : 5 -> EquipmentType.FREE_WEIGHT
                convertedEquipmentType = EquipmentType.FREE_WEIGHT;
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 없는 데이터입니다. <=");
                break;

        } // [check 1]

        return convertedEquipmentType;
    } // End of method [convertEquipmentType]


    /**
     * [method] [EquipmentType] 위치에 해당하는 EquipmentType 값으로 변환한다.
     *
     * @param equipmentType EquipmentType 의 값
     * @return 해당 EquipmentType 의 위치값
     */
    public static int convertPositionOfEquipmentType(EquipmentType equipmentType) {

        final String METHOD_NAME = "[convertPositionOfEquipmentType] ";

        // [lv/i]convertedPosition : EquipmentType 값을 위치 값으로 변경
        int convertedPosition = -1;

        // [check 1] : 어떤 값이냐?
        switch (equipmentType) {

            case BARBELL:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/BARBELL 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : EquipmentType.BARBELL -> 0
                convertedPosition = 0;
                break;

            case DUMBBELL:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/DUMBBELL 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : EquipmentType.DUMBBELL -> 1
                convertedPosition = 1;
                break;

            case MACHINE:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/MACHINE 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : EquipmentType.MACHINE -> 2
                convertedPosition = 2;
                break;

            case CABLE:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/CABLE 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : EquipmentType.CABLE -> 3
                convertedPosition = 3;
                break;

            case KETTLEBELL:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/KETTLEBELL 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : EquipmentType.KETTLEBELL -> 4
                convertedPosition = 4;
                break;

            case FREE_WEIGHT:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/FREE_WEIGHT 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : EquipmentType.FREE_WEIGHT -> 5
                convertedPosition = 5;
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 없는 데이터입니다. <=");
                break;

        } // [check 1]

        return convertedPosition;
    } // End of method [convertPositionOfEquipmentType]


    /**
     * [method] [EquipmentType] EquipmentType 값을 한글로 변환한다.
     *
     * @param equipmentType 운동기구 유형
     * @return 변경된 EquipmentType 의 한글 문자열
     */
    public static String convertHanguleOfEquipmentType(EquipmentType equipmentType) {

        final String METHOD_NAME = "[convertHanguleOfEquipmentType] ";

        // [lv/C]String : EquipmentType 을 한글로 변환
        String convertedHangule = null;

        // [check 1] : 어떤 값이냐?
        switch (equipmentType) {

            case BARBELL:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/BARBELL 가 입력되었습니다. <=");
                // [lv/C]String : EquipmentType.BARBELL -> "바벨"
                convertedHangule = "바벨";
                break;

            case DUMBBELL:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/DUMBBELL 가 입력되었습니다. <=");
                // [lv/C]String : EquipmentType.DUMBBELL -> "덤벨"
                convertedHangule = "덤벨";
                break;

            case MACHINE:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/MACHINE 가 입력되었습니다. <=");
                // [lv/C]String : EquipmentType.MACHINE -> "머신"
                convertedHangule = "머신";
                break;

            case CABLE:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/CABLE 가 입력되었습니다. <=");
                // [lv/C]String : EquipmentType.CABLE -> "케이블"
                convertedHangule = "케이블";
                break;

            case KETTLEBELL:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/KETTLEBELL 가 입력되었습니다. <=");
                // [lv/C]String : EquipmentType.KETTLEBELL -> "케틀벨"
                convertedHangule = "케틀벨";
                break;

            case FREE_WEIGHT:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/FREE_WEIGHT 가 입력되었습니다. <=");
                // [lv/C]String : EquipmentType.FREE_WEIGHT -> "맨몸"
                convertedHangule = "맨몸";
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 없는 데이터입니다. <=");
                break;

        } // [check 1]

        return convertedHangule;
    } // End of method [convertHanguleOfEquipmentType]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= MuscleArea =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] 문자열 형태의 값을 MuscleArea 값으로 변환한다.
     *
     * @param muscleArea 문자열 형태의 MuscleArea 값
     * @return 변경된 MuscleArea 값
     */
    public static MuscleArea convertMuscleArea(String muscleArea) {

        final String METHOD_NAME = "[convertMuscleArea] ";

        // [lv/C]MuscleArea : 문자열 값을 MuscleArea 값으로 변경
        MuscleArea convertedMuscleArea = null;

        // [check 1] : 어떤 값이냐?
        switch (muscleArea) {
            case "CHEST":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/CHEST 가 입력되었습니다. <=");
                // [lv/C]MuscleArea : "CHEST" -> MuscleArea.CHEST
                convertedMuscleArea = MuscleArea.CHEST;
                break;
            case "SHOULDER":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/SHOULDER 가 입력되었습니다. <=");
                // [lv/C]MuscleArea : "SHOULDER" -> MuscleArea.SHOULDER
                convertedMuscleArea = MuscleArea.SHOULDER;
                break;
            case "LAT":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LAT 가 입력되었습니다. <=");
                // [lv/C]MuscleArea : "LAT" -> MuscleArea.LAT
                convertedMuscleArea = MuscleArea.LAT;
                break;
//            case "LEG":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LEG 가 입력되었습니다. <=");
//                // [lv/C]MuscleArea : "LEG" -> MuscleArea.LEG
//                convertedMuscleArea = MuscleArea.LEG;
//                break;
            case "LOWER_BODY":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LOWER_BODY 가 입력되었습니다. <=");
                // [lv/C]MuscleArea : "UPPER_BODY" -> MuscleArea.UPPER_BODY
                convertedMuscleArea = MuscleArea.LOWER_BODY;
                break;
            case "ARM":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ARM 가 입력되었습니다. <=");
                // [lv/C]MuscleArea : "ARM" -> MuscleArea.ARM
                convertedMuscleArea = MuscleArea.ARM;
                break;
            case "ETC":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ETC 가 입력되었습니다. <=");
                // [lv/C]MuscleArea : "ETC" -> MuscleArea.ETC
                convertedMuscleArea = MuscleArea.ETC;
                break;
            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
                break;
        } // [check 1]

        return convertedMuscleArea;
    } // End of method [convertMuscleArea]


    /**
     * [method] [MuscleArea] MuscleArea 값을 한글로 변환한다.
     *
     * @param muscleArea MuscleArea 값
     * @return 변경된 MuscleArea 의 한글 문자열
     */
    public static String convertHanguleOfMuscleArea(MuscleArea muscleArea) {

        final String METHOD_NAME = "[convertHanguleOfMuscleArea] ";

        // [lv/C]String : TargetMuscleType 값을 한글 형태의 문자열로 변환
        String convertedHangule = null;

        // [check 1] : 어떤 값이냐?
        switch (muscleArea) {
            case CHEST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/CHEST 가 입력되었습니다. <=");
                // [lv/C]String : MuscleArea.CHEST -> "가슴"
                convertedHangule = "가슴";
                break;
            case SHOULDER:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/SHOULDER 가 입력되었습니다. <=");
                // [lv/C]String : MuscleArea.SHOULDER -> "어깨"
                convertedHangule = "어깨";
                break;
            case LAT:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LAT 가 입력되었습니다. <=");
                // [lv/C]String : MuscleArea.LAT -> "등"
                convertedHangule = "등";
                break;
//            case LEG:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LEG 가 입력되었습니다. <=");
//                // [lv/C]String : MuscleArea.LEG -> "다리"
//                convertedHangule = "다리";
            case LOWER_BODY:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/UPPER_BODY 가 입력되었습니다. <=");
                // [lv/C]String : MuscleArea.LOWER_BODY -> "하체"
                convertedHangule = "하체";
                break;
            case ARM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ARM 가 입력되었습니다. <=");
                // [lv/C]String : MuscleArea.ARM -> "팔"
                convertedHangule = "팔";
                break;
            case ETC:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ETC 가 입력되었습니다. <=");
                // [lv/C]String : MuscleArea.ETC -> "기타"
                convertedHangule = "기타";
                break;
            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
                break;
        } // [check 1]

        return convertedHangule;

    } // End of method [convertHanguleOfMuscleArea]


    /**
     * [method] [MuscleArea] MuscleArea 값을 한글로 변환한다.
     *
     * @param muscleArea MuscleArea 값
     * @return 변경된 MuscleArea 의 한글 문자열
     */
    public static int convertColorIntOfMuscleArea(MuscleArea muscleArea) {

        final String METHOD_NAME = "[convertColorIntOfMuscleArea] ";

        // [lv/i]convertedColorInt : TargetMuscleType 값을 R.color 의 int 값으로 변환
        int convertedColorInt = -1;

        // [check 1] : 어떤 값이냐?
        switch (muscleArea) {
            case CHEST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/CHEST 가 입력되었습니다. <=");
                // [lv/i]convertedColorInt : MuscleArea.CHEST -> R.color.colorBackgroundFirst
                convertedColorInt = R.color.colorBackgroundFirst;
                break;
            case SHOULDER:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/SHOULDER 가 입력되었습니다. <=");
                // [lv/i]convertedColorInt : MuscleArea.SHOULDER -> R.color.colorBackgroundSecond
                convertedColorInt = R.color.colorBackgroundSecond;
                break;
            case LAT:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LAT 가 입력되었습니다. <=");
                // [lv/i]convertedColorInt : MuscleArea.LAT -> R.color.colorBackgroundThird
                convertedColorInt = R.color.colorBackgroundThird;
                break;
//            case LEG:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LEG 가 입력되었습니다. <=");
//                // [lv/i]convertedColorInt : MuscleArea.LEG -> R.color.colorBackgroundFourth
//                convertedColorInt = R.color.colorBackgroundFourth;
            case LOWER_BODY:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LOWER_BODY 가 입력되었습니다. <=");
                // [lv/i]convertedColorInt : MuscleArea.LOWER_BODY -> R.color.colorBackgroundFourth
                convertedColorInt = R.color.colorBackgroundFourth;
                break;
            case ARM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ARM 가 입력되었습니다. <=");
                // [lv/i]convertedColorInt : MuscleArea.ARM -> R.color.colorBackgroundFifth
                convertedColorInt = R.color.colorBackgroundFifth;
                break;
            case ETC:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ETC 가 입력되었습니다. <=");
                // [lv/i]convertedColorInt : MuscleArea.ETC -> R.color.colorBackgroundSixth
                convertedColorInt = R.color.colorBackgroundSixth;
                break;
            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
        } // [check 1]

        return convertedColorInt;

    } // End of method [convertColorIntOfMuscleArea]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= GroupType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] [GroupType] 문자열 형태의 값을 GroupType 으로 변환한다.
     *
     * @param groupType 문자열 형태의 GroupType 값
     * @return 변경된 GroupType 값
     */
    public static GroupType convertGroupType(String groupType) {

        final String METHOD_NAME = "[convertGroupType] ";

        // [lv/C]GroupType : 문자열을 GroupType 값으로 변환
        GroupType convertedGroupType = null;

        // [check 1] : 어떤 값이냐?
        switch (groupType) {
            case "A_GROUP":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/A_GROUP 가 입력되었습니다. <=");
                // [lv/C]GroupType : "A_GROUP" -> convertedGroupType.A_GROUP
                convertedGroupType = convertedGroupType.A_GROUP;
                break;
            case "B_GROUP":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/B_GROUP 가 입력되었습니다. <=");
                // [lv/C]GroupType : "B_GROUP" -> convertedGroupType.B_GROUP
                convertedGroupType = convertedGroupType.B_GROUP;
                break;
            case "C_GROUP":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/C_GROUP 가 입력되었습니다. <=");
                // [lv/C]GroupType : "MIDDLE" -> convertedGroupType.C_GROUP
                convertedGroupType = convertedGroupType.C_GROUP;
                break;
            case "D_GROUP":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/D_GROUP 가 입력되었습니다. <=");
                // [lv/C]GroupType : "D_GROUP" -> convertedGroupType.D_GROUP
                convertedGroupType = convertedGroupType.D_GROUP;
                break;
            case "E_GROUP":
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/E_GROUP 가 입력되었습니다. <=");
                // [lv/C]GroupType : "E_GROUP" -> TargetMuscleType.E_GROUP
                convertedGroupType = convertedGroupType.E_GROUP;
                break;
            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
                break;
        }

        return convertedGroupType;

    } // End of method [convertGroupType]


    /**
     * [method] [GroupType] 위치에 해당하는 GroupType 값으로 변환한다.
     *
     * @param position GroupType 의 위치 (Spinner 의 위치)
     * @return 변경된 GroupType 값
     */
    public static GroupType convertGroupType(int position) {

        final String METHOD_NAME = "[convertTargetMuscleType] ";

        // [lv/C]GroupType : 위치에 해당하는 GroupType 값으로 변환
        GroupType convertedGroupType = null;

        // [check 1] : 어떤 값이냐?
        switch (position) {
            case 0:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/A_GROUP 가 입력되었습니다. <=");
                // [lv/C]TargetMuscleType : 0 -> GroupType.A_GROUP
                convertedGroupType = GroupType.A_GROUP;
                break;
            case 1:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/B_GROUP 가 입력되었습니다. <=");
                // [lv/C]TargetMuscleType : 1 -> GroupType.B_GROUP
                convertedGroupType = GroupType.B_GROUP;
                break;
            case 2:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/C_GROUP 가 입력되었습니다. <=");
                // [lv/C]TargetMuscleType : 2 -> GroupType.C_GROUP
                convertedGroupType = GroupType.C_GROUP;
                break;
            case 3:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/D_GROUP 가 입력되었습니다. <=");
                // [lv/C]TargetMuscleType : 3 -> GroupType.D_GROUP
                convertedGroupType = GroupType.D_GROUP;
                break;
            case 4:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/E_GROUP 가 입력되었습니다. <=");
                // [lv/C]TargetMuscleType : 4 -> GroupType.E_GROUP
                convertedGroupType = GroupType.E_GROUP;
                break;
            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
                break;
        }

        return convertedGroupType;

    } // End of method [convertTargetMuscleType]

    /**
     * [method] [GroupType] GroupType 값을 한글로 변환한다.
     *
     * @param groupType GroupType 값
     * @return 변경된 GroupType 의 한글 문자열
     */
    public static int convertPositionOfGroupType(GroupType groupType) {

        final String METHOD_NAME = "[convertPositionOfGroupType] ";

        // [lv/i]convertedPosition : GroupType 값을 position 으로 변환
        int convertedPosition = -1;

        // [check 1] : 어떤 값이냐?
        switch (groupType) {
            case A_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/0 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : GroupType.A_GROUP -> 0
                convertedPosition = 0;
                break;
            case B_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/1 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : GroupType.B_GROUP -> 1
                convertedPosition = 1;
                break;
            case C_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/2 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : GroupType.C_GROUP -> 2
                convertedPosition = 2;
                break;
            case D_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/3 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : GroupType.D_GROUP -> 3
                convertedPosition = 3;
                break;
            case E_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/4 가 입력되었습니다. <=");
                // [lv/i]convertedPosition : GroupType.E_GROUP -> 4
                convertedPosition = 4;
                break;
            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/5 : 잘못된 값이 입력되었습니다. <=");
                break;
        } // [check 1]

        return convertedPosition;

    } // End of method [convertPositionOfGroupType]


    /**
     * [method] [GroupType] GroupType 값을 한글로 변환한다.
     *
     * @param groupType GroupType 값
     * @return 변경된 GroupType 의 한글 문자열
     */
    public static String convertHanguleOfGroupType(GroupType groupType) {

        final String METHOD_NAME = "[convertHanguleOfGroupType] ";

        // [lv/C]String : TargetMuscleType 값을 한글 형태의 문자열로 변환
        String convertedHangule = null;

        // [check 1] : 어떤 값이냐?
        switch (groupType) {
            case A_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/A_GROUP 가 입력되었습니다. <=");
                // [lv/C]String : GroupType.A_GROUP -> "A 그룹"
                convertedHangule = "A 그룹";
                break;
            case B_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/B_GROUP 가 입력되었습니다. <=");
                // [lv/C]String : GroupType.B_GROUP -> "B 그룹"
                convertedHangule = "B 그룹";
                break;
            case C_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/C_GROUP 가 입력되었습니다. <=");
                // [lv/C]String : GroupType.C_GROUP -> "C 그룹"
                convertedHangule = "C 그룹";
                break;
            case D_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/D_GROUP 가 입력되었습니다. <=");
                // [lv/C]String : GroupType.D_GROUP -> "D 그룹"
                convertedHangule = "D 그룹";
                break;
            case E_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/E_GROUP 가 입력되었습니다. <=");
                // [lv/C]String : GroupType.E_GROUP -> "E 그룹"
                convertedHangule = "E 그룹";
                break;
            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
                break;
        } // [check 1]

        return convertedHangule;

    } // End of method [convertHanguleOfGroupType]


    /**
     * [method] section_1_direct 에서 해당 그룹의 id 를 추가하기 위한 기본 id 를 가져온다.
     */
    public static int getDefaultIdOfGroupType(GroupType groupType) {

        final String METHOD_NAME = "[getDefaultIdOfGroupType] ";
        // [lv/i]defaultId : 해당 group 의 기본 아이디 값을 가져온다.
        int defaultId = 0;

        // [check 1] : groupType 은 뭐냐?
        switch (groupType) {

            case A_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/A_GROUP <=");
                // [lv/i]default : 기본 아이디값 120
                defaultId = 120;
                break;

            case B_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/B_GROUP <=");
                // [lv/i]default : 기본 아이디값 220
                defaultId = 220;
                break;

            case C_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/C_GROUP <=");
                // [lv/i]default : 기본 아이디값 320
                defaultId = 320;
                break;

            case D_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/D_GROUP <=");
                // [lv/i]default : 기본 아이디값 420
                defaultId = 420;
                break;

            case E_GROUP:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/E_GROUP <=");
                // [lv/i]default : 기본 아이디값 520
                defaultId = 520;
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 없는 유형인데! <=");
                break;

        } // [check 1]

        return defaultId;
    } // End of method [getDefaultIdOfGroupType]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= TargetMuscleType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//    /**
//     * [method] [TargetMuscleType] 문자열 형태의 값을 TargetMuscleType 으로 변환한다.
//     *
//     * @param targetMuscleType 문자열 형태의 TargetMuscleType 값
//     * @return 변경된 TargetMuscleType 값
//     */
//    public static TargetMuscleType convertTargetMuscleType(String targetMuscleType) {
//
//        final String METHOD_NAME = "[convertTargetMuscleType] ";
//
//        // [lv/C]TargetMuscleType : 문자열을 TargetMuscleType 값으로 변환
//        TargetMuscleType convertedTargetMuscleType = null;
//
//        // [check 1] : 어떤 값이냐?
//        switch (targetMuscleType) {
//            case "ALL":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ALL 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : "ALL" -> TargetMuscleType.ALL
//                convertedTargetMuscleType = TargetMuscleType.ALL;
//                break;
//            case "UPPER":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/UPPER 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : "UPPER" -> TargetMuscleType.UPPER
//                convertedTargetMuscleType = TargetMuscleType.UPPER;
//                break;
//            case "MIDDLE":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/MIDDLE 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : "MIDDLE" -> TargetMuscleType.MIDDLE
//                convertedTargetMuscleType = TargetMuscleType.MIDDLE;
//                break;
//            case "LOWER":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LOWER 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : "LOWER" -> TargetMuscleType.LOWER
//                convertedTargetMuscleType = TargetMuscleType.LOWER;
//                break;
//            case "FRONT":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/FRONT 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : "FRONT" -> TargetMuscleType.FRONT
//                convertedTargetMuscleType = TargetMuscleType.FRONT;
//                break;
//            case "SIDE":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/SIDE 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : "SIDE" -> TargetMuscleType.SIDE
//                convertedTargetMuscleType = TargetMuscleType.SIDE;
//                break;
//            case "REAR":
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/REAR 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : "REAR" -> TargetMuscleType.REAR
//                convertedTargetMuscleType = TargetMuscleType.REAR;
//                break;
//            default:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
//                break;
//        }
//
//        return convertedTargetMuscleType;
//
//    } // End of method [convertTargetMuscleType]
//
//
//    /**
//     * [method] [TargetMuscleType] 위치에 해당하는 TargetMuscleType 값으로 변환한다.
//     *
//     * @param position TargetMuscleType 의 위치 (Spinner 의 위치)
//     * @return 변경된 TargetMuscleType 값
//     */
//    public static TargetMuscleType convertTargetMuscleType(int position) {
//
//        final String METHOD_NAME = "[convertTargetMuscleType] ";
//
//        // [lv/C]TargetMuscleType : 위치에 해당하는 TargetMuscleType 값으로 변환
//        TargetMuscleType convertedTargetMuscleType = null;
//
//        // [check 1] : 어떤 값이냐?
//        switch (position) {
//            case 0:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ALL 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : 0 -> TargetMuscleType.ALL
//                convertedTargetMuscleType = TargetMuscleType.ALL;
//                break;
//            case 1:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/UPPER 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : 1 -> TargetMuscleType.UPPER
//                convertedTargetMuscleType = TargetMuscleType.UPPER;
//                break;
//            case 2:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/MIDDLE 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : 2 -> TargetMuscleType.MIDDLE
//                convertedTargetMuscleType = TargetMuscleType.MIDDLE;
//                break;
//            case 3:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LOWER 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : 3 -> TargetMuscleType.LOWER
//                convertedTargetMuscleType = TargetMuscleType.LOWER;
//                break;
//            case 4:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/FRONT 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : 4 -> TargetMuscleType.FRONT
//                convertedTargetMuscleType = TargetMuscleType.FRONT;
//                break;
//            case 5:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/SIDE 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : 5 -> TargetMuscleType.SIDE
//                convertedTargetMuscleType = TargetMuscleType.SIDE;
//                break;
//            case 6:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/REAR 가 입력되었습니다. <=");
//                // [lv/C]TargetMuscleType : 6 -> TargetMuscleType.REAR
//                convertedTargetMuscleType = TargetMuscleType.REAR;
//                break;
//            default:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
//                break;
//        }
//
//        return convertedTargetMuscleType;
//
//    } // End of method [convertTargetMuscleType]
//
//
//    /**
//     * [method] [TargetMuscleType] TargetMuscleType 값을 한글로 변환한다.
//     *
//     * @param targetMuscleType TargetMuscleType 값
//     * @return 변경된 EquipmentType 의 한글 문자열
//     */
//    public static String convertHanguleOfTargetMuscleType(TargetMuscleType targetMuscleType) {
//
//        final String METHOD_NAME = "[convertHanguleOfTargetMuscleType] ";
//
//        // [lv/C]String : TargetMuscleType 값을 한글 형태의 문자열로 변환
//        String convertedHangule = null;
//
//        // [check 1] : 어떤 값이냐?
//        switch (targetMuscleType) {
//            case ALL:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/ALL 가 입력되었습니다. <=");
//                // [lv/C]String : TargetMuscleType.ALL -> "전체"
//                convertedHangule = "전체";
//                break;
//            case UPPER:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/UPPER 가 입력되었습니다. <=");
//                // [lv/C]String : TargetMuscleType.UPPER -> "상부"
//                convertedHangule = "상부";
//                break;
//            case MIDDLE:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/MIDDLE 가 입력되었습니다. <=");
//                // [lv/C]String : TargetMuscleType.MIDDLE -> "중앙부"
//                convertedHangule = "중앙부";
//                break;
//            case LOWER:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/LOWER 가 입력되었습니다. <=");
//                // [lv/C]String : TargetMuscleType.LOWER -> "하부"
//                convertedHangule = "하부";
//                break;
//            case FRONT:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/FRONT 가 입력되었습니다. <=");
//                // [lv/C]String : TargetMuscleType.FRONT -> "전면"
//                convertedHangule = "전면";
//                break;
//            case SIDE:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/SIDE 가 입력되었습니다. <=");
//                // [lv/C]String : TargetMuscleType.SIDE -> "측면"
//                convertedHangule = "측면";
//                break;
//            case REAR:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/REAR 가 입력되었습니다. <=");
//                // [lv/C]String : TargetMuscleType.REAR -> "후면"
//                convertedHangule = "후면";
//                break;
//            default:
//                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 잘못된 값이 입력되었습니다. <=");
//                break;
//        }
//
//        return convertedHangule;
//
//    } // End of method [convertHanguleOfTargetMuscleType]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= muscleAreaNextActivityType =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] muscleAreaNextActivityType 을 구별하여 해당 Activity 의 Class 를 반환한다.z
     *
     * @param muscleAreaNextActivityType MuscleAreaActivity 에서 넘어온 진행반향의  Activity
     * @return 이동하는 Activity 의 class
     */
    public static Class getNextActivity(MuscleAreaNextActivityType muscleAreaNextActivityType) {

        final String METHOD_NAME = "[getNextActivity] ";

        // [lv/C]Class : 그래서 다음 Activity 의 Class 는 ?
        Class nextActivity = null;

        // [check 1] : 다음에 보여줘야 할 Activity 는 뭐니?
        switch (muscleAreaNextActivityType) {
            case EVENT_ADD:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EVENT_ADD : EventActivity 로 이동 <=");
                // [lv/C]Class : MuscleAreaNextActivityType.EVENT_ADD -> EventAddActivity.class
                nextActivity = EventAddActivity.class;
                break;

            case EVENT_LIST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EVENT_LIST : EventListActivity 로 이동 <=");
                // [lv/C]Class : MuscleAreaNextActivityType.EVENT_LIST -> EventListActivity.class
                nextActivity = EventListActivity.class;
                break;

            case EVENT_PROGRAM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/Event_PROGRAM : EventProgramActivity 로 이동 <=");
                // [lv/C]Class : MuscleAreaNextActivityType.EVENT_PROGRAM -> EventProgramActivity.class
                nextActivity = EventProgramActivity.class;
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 다음 Activity 에는 없는 유형이요! <=");
                break;

        } // [check 1]

        return nextActivity;
    } // End of method [getNextActivity]


    /**
     * [method] muscleAreaNextActivityType 을 구별하여 해당 Activity 의 이름을 한글로 변환한다.
     */
    public static String convertHanguleOfMuscleAreaNextActivityType(MuscleAreaNextActivityType muscleAreaNextActivityType) {

        final String METHOD_NAME = "[convertHanguleOfMuscleAreaNextActivityType] ";

        // [lv/C]String : 한글로 변경된 muscleAreaNextActivityType
        String hangule = null;

        // [check 1] : 다음에 보여줘야 할 Activity 는 뭐니?
        switch (muscleAreaNextActivityType) {
            case EVENT_ADD:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EVENT_ADD : '종목 추가' 로 반환한다. <=");
                // [lv/C]String : MuscleAreaNextActivityType.EVENT_ADD -> "종목 추가"
                hangule = "종목 추가";
                break;

            case EVENT_LIST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EVENT_LIST : '목록 보기' 로 반환한다. 로 이동 <=");
                // [lv/C]String : MuscleAreaNextActivityType.EVENT_LIST -> "목록 보기"
                hangule = "목록 보기";
                break;

            case EVENT_PROGRAM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/Event_PROGRAM : '운동 프로그램' 으로 반환한다. <=");
                // [lv/C]String : MuscleAreaNextActivityType.EVENT_PROGRAM -> "운동 프로그램'
                hangule = "운동 프로그램";
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 다음 Activity 에는 없는 유형이요! <=");
                break;
        } // [check 1]

        return hangule;

    } // End of method [convertHanguleOfMuscleAreaNextActivityType]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= restTime =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] int type 으로 받은 분, 초를 millisecond 으로 변환한다.
     */
    public static int convertMillisecondOfRestTime(int minute, int second) {

        // 1초 = 1000 millisecond
        // 1분 = 1000*60 millisecond
        return (minute * 1000 * 60) + (second * 1000);

    } // End of method [convertMillisecondOfRestTime]

}
