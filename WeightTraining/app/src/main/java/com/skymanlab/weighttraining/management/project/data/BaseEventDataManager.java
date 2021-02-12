package com.skymanlab.weighttraining.management.project.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.EquipmentType;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseEventDataManager {

    // constant
    public static final String EVENT_KEY = "event";

    // 구분
    // 1. 가슴
    // 2. 어깨
    // 3. 등
    // 4. 허리와 복근
    // 5. 하체
    // 6. 팔

    // Event
    // 0. count
    // 1. userCount
    // 2. eventName
    // 3. muscleArea
    // 4. EquipmentType
    // 5. groupType
    // 6. properWeight
    // 7. maxWeight(1RM)

    // constance
    static final float DEFAULT_WEIGHT = 0.0f;
    // constant
    private static final String CLASS_NAME = "[PD] BaseEventDataManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private String uid;

    // instance variable
    private ArrayList<Event> chest;
    private ArrayList<Event> shoulder;
    private ArrayList<Event> lat;
    private ArrayList<Event> lowerBody;
    private ArrayList<Event> arm;
    private ArrayList<Event> etc;


    // constructor
    public BaseEventDataManager(String uid) {
        this.uid = uid;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 외부에서 저장하기 위한 메소드 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    public void init() {
        final String METHOD_NAME = "[saveContent] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< method > 모든 부위에 초기 데이터를 생성합니다.");

        // [method] : chest 데이터 만들기
        setChest();

        // [method] : shoulder 데이터 만들기
        setShoulder();

        // [method] : lat 데이터 만들기
        setLat();

        // [method] : leg 데이터 만들기
        setLowerBody();

        // [method] : arm 데이터 만들기
        setArm();

    }


    public void saveContent(DatabaseReference.CompletionListener completionListener) {
        final String METHOD_NAME = "[saveContent] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< method > 모든 부위에 초기 데이터를 저장합니다.");

        DatabaseReference reference = FirebaseDatabase
                .getInstance()
                .getReference(
                        FirebaseConstants.DATABASE_FIRST_NODE_EVENT
                );

        HashMap<String, Object> childUpdateList = new HashMap<>();
        // chest
        childUpdateList.putAll(getHashMapOfEventArrayList(reference, chest, MuscleArea.CHEST));
        // shoulder
        childUpdateList.putAll(getHashMapOfEventArrayList(reference, shoulder, MuscleArea.SHOULDER));
        // lat
        childUpdateList.putAll(getHashMapOfEventArrayList(reference, lat, MuscleArea.LAT));
        // lowerBody
        childUpdateList.putAll(getHashMapOfEventArrayList(reference, lowerBody, MuscleArea.LOWER_BODY));
        // arm
        childUpdateList.putAll(getHashMapOfEventArrayList(reference, arm, MuscleArea.ARM));

        reference.updateChildren(childUpdateList, completionListener);


    }

    /**
     * [method] 해당 부위의 eventArrayList 을 BaseEventRegister 객체를 이용하여 외부, 내부 데이터베이스에 저장
     */
    public HashMap<String, Object> getHashMapOfEventArrayList(DatabaseReference reference, ArrayList<Event> eventArrayList, MuscleArea muscleArea) {
        final String METHOD_NAME = "[getHashMapOfEventArrayList] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< HashMap> " +
                DataManager.convertHanguleOfMuscleArea(muscleArea) +
                " 의 데이터를 이용하여 childUpdateList 를 생성 중입니다."
        );

        HashMap<String, Object> childUpdateList = new HashMap<>();

        // [cycle 1] : chest 에 관한 event 데이터 저장
        for (int index = 0; index < eventArrayList.size(); index++) {

            // [lv/C]HashMap<String, Object> : eventName, muscleArea, equipmentType, groupType properWeight, maxWeight 담기
            HashMap<String, Object> event = new HashMap<>();
            event.put(Event.EVENT_NAME, eventArrayList.get(index).getEventName());
            event.put(Event.MUSCLE_AREA, eventArrayList.get(index).getMuscleArea());
            event.put(Event.EQUIPMENT_TYPE, eventArrayList.get(index).getEquipmentType());
            event.put(Event.GROUP_TYPE, eventArrayList.get(index).getGroupType());
            event.put(Event.PROPER_WEIGHT, eventArrayList.get(index).getProperWeight());
            event.put(Event.MAX_WEIGHT, eventArrayList.get(index).getMaxWeight());

            String key = reference
                    .child(uid)
                    .child(muscleArea.toString())
                    .push()
                    .getKey();
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< KEY > key 를 확인하겠습니다. = " + key);

            StringBuilder path = new StringBuilder()
                    .append("/")
                    .append(uid)
                    .append("/")
                    .append(muscleArea.toString())
                    .append("/")
                    .append(key);

            childUpdateList.put(path.toString(), event);

            // [lv/C]DatabaseReference : 'event/uid/muscleArea/key' 에 저장한다.
//            databaseReference.child(uid).child(eventArrayList.get(index).getMuscleArea().toString()).push().setValue(event);
//            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "uid="+uid + "//muscleArea=" + muscleArea.toString()+ "//key="+databaseReference.getKey());

        } // [cycle 1]

        return childUpdateList;

    } // End of method [saveEventArrayList]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= getter =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public ArrayList<Event> getChest() {

        final String METHOD_NAME = "[getChest] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CHEST 가슴 확인 <<<<<<<<<<<<<<<<<<<<<<<< ");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, chest);

        for (int index = 0; index < chest.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> event name = " + chest.get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> group type = " + chest.get(index).getGroupType());
        }
        return chest;
    }

    public ArrayList<Event> getShoulder() {
        final String METHOD_NAME = "[getShoulder] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SHOULDER 어깨 확인 <<<<<<<<<<<<<<<<<<<<<<<< ");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, shoulder);

        for (int index = 0; index < shoulder.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> event name = " + shoulder.get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> group type = " + shoulder.get(index).getGroupType());
        }

        return shoulder;
    }

    public ArrayList<Event> getLat() {
        final String METHOD_NAME = "[getLat] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> LAT 등 확인 <<<<<<<<<<<<<<<<<<<<<<<< ");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, lat);
        for (int index = 0; index < lat.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> event name = " + lat.get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> group type = " + lat.get(index).getGroupType());
        }
        return lat;
    }

    public ArrayList<Event> getLowerBody() {
        final String METHOD_NAME = "[getLowerBody] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> LOWER_BODY 다리 확인 <<<<<<<<<<<<<<<<<<<<<<<< ");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, leg);
        for (int index = 0; index < lowerBody.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> event name = " + lowerBody.get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> group type = " + lowerBody.get(index).getGroupType());
        }
        return lowerBody;
    }

    public ArrayList<Event> getArm() {
        final String METHOD_NAME = "[getArm] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ARM 팔 확인 <<<<<<<<<<<<<<<<<<<<<<<< ");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, arm);
        for (int index = 0; index < arm.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> event name = " + arm.get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "번째> group type = " + arm.get(index).getGroupType());
        }
        return arm;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= chest =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void setChest() {

        // A_GROUP :
        // 1. 바벨 벤치프레스 : 전체 / 바벨
        // 2. 덤벨 벤치프레스 : 전체 / 덤벨
        // 3. 시티드 체스트 프레스머신 : 전체 / 머신
        // 5. 푸시업(중량 푸쉬업) : 전체 / 맨몸

        // B_GROUP :
        // 1. 인클라인 바벨 벤치 프레스 : 상부 / 바벨
        // 2. 인클라인 덤벨 벤치 프레스 : 상부 / 덤벨
        // 3. 인클라인 덤벨 플라이 : 상부 / 덤벨
        // 4. 케이블 크로스오버 플라이 : 상부 / 케이블
        // 5. 인클라인 푸쉬업 : 상부 / 맨몸

        // C_GROUP :
        // 1. 디클라인 바벨 벤치프레스 : 하부 / 바벨
        // 2. 디클라인 덤벨 벤치프레스 : 하부 / 덤벨
        // 3. 케이블 크로스오버 플라이(하부) : 하부 / 케이블
        // 4. 체스트 딥스 (맨몸 or 중량 or 어시스티드) : 하부 / 맨몸
        // 5. 디클라인 푸쉬업 : 하부 / 맨몸

        // D_GROUP :
        // 1. 덤벨 체스트 플라이 : 중앙부 / 덤벨
        // 2. 케이블 크로스오버 플라이(중앙부) :
        // 3. 플라이 머신 체스트 플라이
        // 4. 클로즈그립 벤치프레스 : 중앙부 / 바벨


        final MuscleArea CHEST = MuscleArea.CHEST;

        this.chest = new ArrayList<>();

        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // A_GROUP
        // 1. 바벨 벤치프레스 : 전체 / 바벨
        // 2. 덤벨 벤치프레스 : 전체 / 덤벨
        // 3. 시티드 체스트 프레스머신 : 전체 / 머신
        // 5. 푸시업(중량 푸쉬업) : 전체 / 맨몸

        // [lv/C]Event : 1. 바벨 벤치 프레스
        Event barbellBenchPress = new Event();
        barbellBenchPress.setEventName("바벨 벤치 프레스");
        barbellBenchPress.setMuscleArea(CHEST);
        barbellBenchPress.setEquipmentType(EquipmentType.BARBELL);
        barbellBenchPress.setGroupType(GroupType.A_GROUP);
        barbellBenchPress.setProperWeight(DEFAULT_WEIGHT);
        barbellBenchPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(barbellBenchPress);

        // [lv/C]Event : 2. 덤벨 벤치 프레스
        Event dumbbellBenchPress = new Event();
        dumbbellBenchPress.setEventName("덤벨 벤치 프레스");
        dumbbellBenchPress.setMuscleArea(CHEST);
        dumbbellBenchPress.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellBenchPress.setGroupType(GroupType.A_GROUP);
        dumbbellBenchPress.setProperWeight(DEFAULT_WEIGHT);
        dumbbellBenchPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(dumbbellBenchPress);

        // [lv/C]Event : 3. 시티드 체스트 프레스
        Event seatedChestPress = new Event();
        seatedChestPress.setEventName("시티드 체스트 프레스");
        seatedChestPress.setMuscleArea(CHEST);
        seatedChestPress.setEquipmentType(EquipmentType.MACHINE);
        seatedChestPress.setGroupType(GroupType.A_GROUP);
        seatedChestPress.setProperWeight(DEFAULT_WEIGHT);
        seatedChestPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(seatedChestPress);

        // [lv/C]Event : 4. 푸쉬업
        Event pushUp = new Event();
        pushUp.setEventName("푸쉬업");
        pushUp.setMuscleArea(CHEST);
        pushUp.setEquipmentType(EquipmentType.FREE_WEIGHT);
        pushUp.setGroupType(GroupType.A_GROUP);
        pushUp.setProperWeight(DEFAULT_WEIGHT);
        pushUp.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(pushUp);


        // +++++++++++++++++++++++++++++++++++++++++++++ B_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // B_GROUP
        // 1. 인클라인 바벨 벤치 프레스 : 상부 / 바벨
        // 2. 인클라인 덤벨 벤치 프레스 : 상부 / 덤벨
        // 3. 인클라인 덤벨 플라이 : 상부 / 덤벨
        // 4. 케이블 크로스오버 플라이 : 상부 / 케이블
        // 5. 인클라인 푸쉬업 : 상부 / 맨몸

        // [lv/C]Event : 1. 인클라인 바벨 벤치 프레스
        Event inclineBarbellBenchPress = new Event();
        inclineBarbellBenchPress.setEventName("인클라인 바벨 벤치 프레스");
        inclineBarbellBenchPress.setMuscleArea(CHEST);
        inclineBarbellBenchPress.setEquipmentType(EquipmentType.BARBELL);
        inclineBarbellBenchPress.setGroupType(GroupType.B_GROUP);
        inclineBarbellBenchPress.setProperWeight(DEFAULT_WEIGHT);
        inclineBarbellBenchPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(inclineBarbellBenchPress);

        // [lv/C]Event : 2. 인클라인 덤벨 벤치 프레스
        Event inclineDumbbellBenchPress = new Event();
        inclineDumbbellBenchPress.setEventName("인클라인 덤벨 벤치 프레스");
        inclineDumbbellBenchPress.setMuscleArea(CHEST);
        inclineDumbbellBenchPress.setEquipmentType(EquipmentType.DUMBBELL);
        inclineDumbbellBenchPress.setGroupType(GroupType.B_GROUP);
        inclineDumbbellBenchPress.setProperWeight(DEFAULT_WEIGHT);
        inclineDumbbellBenchPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(inclineDumbbellBenchPress);

        // [lv/C]Event : 3. 인클라인 덤벨 플라이
        Event inclineDumbbellFly = new Event();
        inclineDumbbellFly.setEventName("인클라인 덤벨 플라이");
        inclineDumbbellFly.setMuscleArea(CHEST);
        inclineDumbbellFly.setEquipmentType(EquipmentType.DUMBBELL);
        inclineDumbbellFly.setGroupType(GroupType.B_GROUP);
        inclineDumbbellFly.setProperWeight(DEFAULT_WEIGHT);
        inclineDumbbellFly.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(inclineDumbbellFly);

        // [lv/C]Event : 4. 케이블 크로스오버 플라이(상부)
        Event cableCrossoverFlyUpper = new Event();
        cableCrossoverFlyUpper.setEventName("케이블 크로스오버 플라이(상부)");
        cableCrossoverFlyUpper.setMuscleArea(CHEST);
        cableCrossoverFlyUpper.setEquipmentType(EquipmentType.CABLE);
        cableCrossoverFlyUpper.setGroupType(GroupType.B_GROUP);
        cableCrossoverFlyUpper.setProperWeight(DEFAULT_WEIGHT);
        cableCrossoverFlyUpper.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(cableCrossoverFlyUpper);

        // [lv/C]Event : 5. 인클라인 푸쉬업
        Event inclinePushUp = new Event();
        inclinePushUp.setEventName("인클라인 푸쉬업");
        inclinePushUp.setMuscleArea(CHEST);
        inclinePushUp.setEquipmentType(EquipmentType.FREE_WEIGHT);
        inclinePushUp.setGroupType(GroupType.B_GROUP);
        inclinePushUp.setProperWeight(DEFAULT_WEIGHT);
        inclinePushUp.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(inclinePushUp);


        // +++++++++++++++++++++++++++++++++++++++++++++ C_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // C_GROUP :
        // 1. 디클라인 바벨 벤치프레스 : 하부 / 바벨
        // 2. 디클라인 덤벨 벤치프레스 : 하부 / 덤벨
        // 3. 케이블 크로스오버 플라이(하부) : 하부 / 케이블
        // 4. 체스트 딥스 (맨몸 or 중량 or 어시스티드) : 하부 / 맨몸
        // 5. 디클라인 푸쉬업 : 하부 / 맨몸

        // [lv/C]Event : 1. 디클라인 바벨 벤치프레스
        Event declineBarbellBenchPress = new Event();
        declineBarbellBenchPress.setEventName("디클라인 바벨 벤치프레스");
        declineBarbellBenchPress.setMuscleArea(CHEST);
        declineBarbellBenchPress.setEquipmentType(EquipmentType.BARBELL);
        declineBarbellBenchPress.setGroupType(GroupType.C_GROUP);
        declineBarbellBenchPress.setProperWeight(DEFAULT_WEIGHT);
        declineBarbellBenchPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(declineBarbellBenchPress);

        // [lv/C]Event : 2. 디클라인 덤벨 벤치프레스
        Event declineDumbbellBenchPress = new Event();
        declineDumbbellBenchPress.setEventName("디클라인 덤벨 벤치프레스");
        declineDumbbellBenchPress.setMuscleArea(CHEST);
        declineDumbbellBenchPress.setEquipmentType(EquipmentType.DUMBBELL);
        declineDumbbellBenchPress.setGroupType(GroupType.C_GROUP);
        declineDumbbellBenchPress.setProperWeight(DEFAULT_WEIGHT);
        declineDumbbellBenchPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(declineDumbbellBenchPress);

        // [lv/C]Event : 3. 케이블 크로스오버 플라이(하부)
        Event cableCrossoverFlyLower = new Event();
        cableCrossoverFlyLower.setEventName("케이블 크로스오버 플라이(하부)");
        cableCrossoverFlyLower.setMuscleArea(CHEST);
        cableCrossoverFlyLower.setEquipmentType(EquipmentType.CABLE);
        cableCrossoverFlyLower.setGroupType(GroupType.C_GROUP);
        cableCrossoverFlyLower.setProperWeight(DEFAULT_WEIGHT);
        cableCrossoverFlyLower.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(cableCrossoverFlyLower);

        // [lv/C]Event : 4. 체스트 딥스
        Event chestDips = new Event();
        chestDips.setEventName("체스트 딥스");
        chestDips.setMuscleArea(CHEST);
        chestDips.setEquipmentType(EquipmentType.FREE_WEIGHT);
        chestDips.setGroupType(GroupType.C_GROUP);
        chestDips.setProperWeight(DEFAULT_WEIGHT);
        chestDips.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(chestDips);

        // [lv/C]Event : 5. 디클라인 푸쉬업
        Event declinePushUp = new Event();
        declinePushUp.setEventName("디클라인 푸쉬업");
        declinePushUp.setMuscleArea(CHEST);
        declinePushUp.setEquipmentType(EquipmentType.FREE_WEIGHT);
        declinePushUp.setGroupType(GroupType.C_GROUP);
        declinePushUp.setProperWeight(DEFAULT_WEIGHT);
        declinePushUp.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(declinePushUp);


        // +++++++++++++++++++++++++++++++++++++++++++++ D_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // D_GROUP :
        // 1. 덤벨 체스트 플라이 : 중앙부 / 덤벨
        // 2. 케이블 크로스오버 플라이(중앙부) :
        // 3. 플라이 머신 체스트 플라이
        // 4. 클로즈그립 벤치프레스 : 중앙부 / 바벨

        // [lv/C]Event : 1. 덤벨 체스트 플라이
        Event dumbbellChestFly = new Event();
        dumbbellChestFly.setEventName("덤벨 체스트 플라이");
        dumbbellChestFly.setMuscleArea(CHEST);
        dumbbellChestFly.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellChestFly.setGroupType(GroupType.D_GROUP);
        dumbbellChestFly.setProperWeight(DEFAULT_WEIGHT);
        dumbbellChestFly.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(dumbbellChestFly);

        // [lv/C]Event : 2. 케이블 크로스오버 플라이(중앙부)
        Event cableCrossoverFlyMiddle = new Event();
        cableCrossoverFlyMiddle.setEventName("케이블 크로스오버 플라이(중앙부)");
        cableCrossoverFlyMiddle.setMuscleArea(CHEST);
        cableCrossoverFlyMiddle.setEquipmentType(EquipmentType.CABLE);
        cableCrossoverFlyMiddle.setGroupType(GroupType.D_GROUP);
        cableCrossoverFlyMiddle.setProperWeight(DEFAULT_WEIGHT);
        cableCrossoverFlyMiddle.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(cableCrossoverFlyMiddle);

        // [lv/C]Event : 3. 플라이 머신 체스트 플라이
        Event flyMachineChestFly = new Event();
        flyMachineChestFly.setEventName("플라이 머신 체스트 플라이");
        flyMachineChestFly.setMuscleArea(CHEST);
        flyMachineChestFly.setEquipmentType(EquipmentType.MACHINE);
        flyMachineChestFly.setGroupType(GroupType.D_GROUP);
        flyMachineChestFly.setProperWeight(DEFAULT_WEIGHT);
        flyMachineChestFly.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(flyMachineChestFly);

        // [lv/C]Event : 4. 클로즈 그립 벤치프레스
        Event closeGripBenchPress = new Event();
        closeGripBenchPress.setEventName("클로즈 그립 벤치프레스");
        closeGripBenchPress.setMuscleArea(CHEST);
        closeGripBenchPress.setEquipmentType(EquipmentType.BARBELL);
        closeGripBenchPress.setGroupType(GroupType.D_GROUP);
        closeGripBenchPress.setProperWeight(DEFAULT_WEIGHT);
        closeGripBenchPress.setMaxWeight(DEFAULT_WEIGHT);
        chest.add(closeGripBenchPress);

    } // End of method [setChest]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= shoulder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void setShoulder() {

        // 참고 : 전면은 쇄골에서, 측면은 견갑골의 견봉에서, 후면은 견갑골 윗부분에서 시작해 팔 바깥쪽에서 부채꼴처럼 하나로 합쳐집니다.
        // <1> 미는 복합운동 : 프레스(press) <2> 고립성 모멘트 운동 : 레이즈(Raise), <3> 당기는 복합운동 : 업라이트 로우(upright row)

        // A_GROUP :
        // 1. 바벨 오버헤드(밀리터리) 프레스 : 전체 / 바벨
        // 2. 덤벨 오버헤드 프레스 : 전체 / 덤벨
        // 3. 스미스 머신 오버헤드 프레스 : 전체 / 머신
        // 4. 바벨 비하인드넥 프레스 : 전체 / 바벨
        // 5. 스미스 머신 비하인드넥 프레스 : 전체 / 머신
        // 6. 아놀드 프레스 : 전체 / 덤벨

        // B_GROUP :
        // 1. 덤벨 프론트 레이즈 : 전면 / 덤벨
        // 2. 케이블 프론트 레이즈 : 전면 / 케이블

        // C_GROUP :
        // 1. 덤벨 사이드 레터럴 레이즈 : 측면 / 덤벨
        // 2. 케이블 사이드 레터럴 레이즈 : 측면 / 케이블

        // D_GROUP :
        // 1. 덤벨 벤트오버 레터럴 레이즈(리버스 플라이) : 후면 / 덤벨
        // 2. 케이블 벤트오버 레터럴 레이즈(리버스 플라이) : 후면 / 케이블
        // 3. 페이스 풀 : 후면 / 케이블 / 벹트오버 래터럴 레이즈 + 업라이트 로우

        // E_GROUP :
        // 1. 업라이트 로우 : 전체 / 덤벨

        final MuscleArea SHOULDER = MuscleArea.SHOULDER;

        this.shoulder = new ArrayList<>();

        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // A_GROUP :
        // 1. 바벨 오버헤드(밀리터리) 프레스 : 전체 / 바벨
        // 2. 덤벨 오버헤드 프레스 : 전체 / 덤벨
        // 3. 스미스 머신 오버헤드 프레스 : 전체 / 머신
        // 4. 바벨 비하인드넥 프레스 : 전체 / 바벨
        // 5. 스미스 머신 비하인드넥 프레스 : 전체 / 머신
        // 6. 아놀드 프레스 : 전체 / 덤벨

        // [lv/C]Event : 1. 바벨 오버헤드 프레스
        Event barbellOverheadPress = new Event();
        barbellOverheadPress.setEventName("바벨 오버헤드 프레스");
        barbellOverheadPress.setMuscleArea(SHOULDER);
        barbellOverheadPress.setEquipmentType(EquipmentType.BARBELL);
        barbellOverheadPress.setGroupType(GroupType.A_GROUP);
        barbellOverheadPress.setProperWeight(DEFAULT_WEIGHT);
        barbellOverheadPress.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(barbellOverheadPress);

        // [lv/C]Event : 2. 덤벨 오버헤드 프레스
        Event dumbbellOverheadPress = new Event();
        dumbbellOverheadPress.setEventName("덤벨 오버헤드 프레스");
        dumbbellOverheadPress.setMuscleArea(SHOULDER);
        dumbbellOverheadPress.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellOverheadPress.setGroupType(GroupType.A_GROUP);
        dumbbellOverheadPress.setProperWeight(DEFAULT_WEIGHT);
        dumbbellOverheadPress.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(dumbbellOverheadPress);

        // [lv/C]Event : 3. 스미스 머신 오버헤드 프레스
        Event smithMachineOverheadPress = new Event();
        smithMachineOverheadPress.setEventName("스미스 머신 오버헤드 프레스");
        smithMachineOverheadPress.setMuscleArea(SHOULDER);
        smithMachineOverheadPress.setEquipmentType(EquipmentType.MACHINE);
        smithMachineOverheadPress.setGroupType(GroupType.A_GROUP);
        smithMachineOverheadPress.setProperWeight(DEFAULT_WEIGHT);
        smithMachineOverheadPress.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(smithMachineOverheadPress);

        // [lv/C]Event : 4. 바벨 비하인드넥 프레스
        Event barbellBehindNeckPress = new Event();
        barbellBehindNeckPress.setEventName("바벨 비하인드넥 프레스");
        barbellBehindNeckPress.setMuscleArea(SHOULDER);
        barbellBehindNeckPress.setEquipmentType(EquipmentType.BARBELL);
        barbellBehindNeckPress.setGroupType(GroupType.A_GROUP);
        barbellBehindNeckPress.setProperWeight(DEFAULT_WEIGHT);
        barbellBehindNeckPress.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(barbellBehindNeckPress);

        // [lv/C]Event : 5. 스미스 머신 비하인드넥 프레스
        Event smithMachineBehindNeckPress = new Event();
        smithMachineBehindNeckPress.setEventName("스미스 머신 비하인드넥 프레스");
        smithMachineBehindNeckPress.setMuscleArea(SHOULDER);
        smithMachineBehindNeckPress.setEquipmentType(EquipmentType.MACHINE);
        smithMachineBehindNeckPress.setGroupType(GroupType.A_GROUP);
        smithMachineBehindNeckPress.setProperWeight(DEFAULT_WEIGHT);
        smithMachineBehindNeckPress.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(smithMachineBehindNeckPress);

        // [lv/C]Event : 6. 아놀드 프레스
        Event arnoldPress = new Event();
        arnoldPress.setEventName("아놀드 프레스");
        arnoldPress.setMuscleArea(SHOULDER);
        arnoldPress.setEquipmentType(EquipmentType.DUMBBELL);
        arnoldPress.setGroupType(GroupType.A_GROUP);
        arnoldPress.setProperWeight(DEFAULT_WEIGHT);
        arnoldPress.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(arnoldPress);


        // +++++++++++++++++++++++++++++++++++++++++++++ B_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // B_GROUP :
        // 1. 덤벨 프론트 레이즈 : 전면 / 덤벨
        // 2. 케이블 프론트 레이즈 : 전면 / 케이블

        // [lv/C]Event : 1. 덤벨 프론트 레이즈
        Event dumbbellFrontRaise = new Event();
        dumbbellFrontRaise.setEventName("덤벨 프론트 레이즈");
        dumbbellFrontRaise.setMuscleArea(SHOULDER);
        dumbbellFrontRaise.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellFrontRaise.setGroupType(GroupType.B_GROUP);
        dumbbellFrontRaise.setProperWeight(DEFAULT_WEIGHT);
        dumbbellFrontRaise.setProperWeight(DEFAULT_WEIGHT);
        shoulder.add(dumbbellFrontRaise);

        // [lv/C]Event : 2. 케이블 프론트 레이즈
        Event cableFrontRaise = new Event();
        cableFrontRaise.setEventName("케이블 프론트 레이즈");
        cableFrontRaise.setMuscleArea(SHOULDER);
        cableFrontRaise.setEquipmentType(EquipmentType.CABLE);
        cableFrontRaise.setGroupType(GroupType.B_GROUP);
        cableFrontRaise.setProperWeight(DEFAULT_WEIGHT);
        cableFrontRaise.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(cableFrontRaise);


        // +++++++++++++++++++++++++++++++++++++++++++++ C_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // C_GROUP :
        // 1. 덤벨 사이드 레터럴 레이즈 : 측면 / 덤벨
        // 2. 케이블 사이드 레터럴 레이즈 : 측면 / 케이블

        // [lv/C]Event : 1. 덤벨 사이드 레터럴 레이즈
        Event dumbbellSideLateralRaise = new Event();
        dumbbellSideLateralRaise.setEventName("덤벨 사이드 레터럴 레이즈");
        dumbbellSideLateralRaise.setMuscleArea(SHOULDER);
        dumbbellSideLateralRaise.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellSideLateralRaise.setGroupType(GroupType.C_GROUP);
        dumbbellSideLateralRaise.setProperWeight(DEFAULT_WEIGHT);
        dumbbellSideLateralRaise.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(dumbbellSideLateralRaise);

        // [lv/C]Event : 2. 케이블 사이드 레터럴 레이즈
        Event cableSideLateralRaise = new Event();
        cableSideLateralRaise.setEventName("케이블 사이드 레터럴 레이즈");
        cableSideLateralRaise.setMuscleArea(SHOULDER);
        cableSideLateralRaise.setEquipmentType(EquipmentType.CABLE);
        cableSideLateralRaise.setGroupType(GroupType.C_GROUP);
        cableSideLateralRaise.setProperWeight(DEFAULT_WEIGHT);
        cableSideLateralRaise.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(cableSideLateralRaise);


        // +++++++++++++++++++++++++++++++++++++++++++++ D_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // D_GROUP :
        // 1. 덤벨 벤트오버 레터럴 레이즈(리버스 플라이) : 후면 / 덤벨
        // 2. 케이블 벤트오버 레터럴 레이즈(리버스 플라이) : 후면 / 케이블
        // 3. 페이스 풀 : 후면 / 케이블 / 벹트오버 레터럴 레이즈 + 업라이트 로우

        // [lv/C]Event : 1. 덤벨 벤트오버 레터럴 레이즈(리버스 플라이)
        Event dumbbellBentOverLateralRaise = new Event();
        dumbbellBentOverLateralRaise.setEventName("덤벨 벤트오버 레터럴 레이즈(리버스 플라이)");
        dumbbellBentOverLateralRaise.setMuscleArea(SHOULDER);
        dumbbellBentOverLateralRaise.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellBentOverLateralRaise.setGroupType(GroupType.D_GROUP);
        dumbbellBentOverLateralRaise.setProperWeight(DEFAULT_WEIGHT);
        dumbbellBentOverLateralRaise.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(dumbbellBentOverLateralRaise);

        // [lv/C]Event : 2. 케이블 벤트오버 레터럴 레이즈(리버스 플라이)
        Event cableBentOverLateralRaise = new Event();
        cableBentOverLateralRaise.setEventName("케이블 벤트오버 레터럴 레이즈(리버스 플라이)");
        cableBentOverLateralRaise.setMuscleArea(SHOULDER);
        cableBentOverLateralRaise.setEquipmentType(EquipmentType.CABLE);
        cableBentOverLateralRaise.setGroupType(GroupType.D_GROUP);
        cableBentOverLateralRaise.setProperWeight(DEFAULT_WEIGHT);
        cableBentOverLateralRaise.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(cableBentOverLateralRaise);

        // [lv/C]Event : 3. 페이스 풀
        Event facePull = new Event();
        facePull.setEventName("페이스 풀");
        facePull.setMuscleArea(SHOULDER);
        facePull.setEquipmentType(EquipmentType.CABLE);
        facePull.setGroupType(GroupType.D_GROUP);
        facePull.setProperWeight(DEFAULT_WEIGHT);
        facePull.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(facePull);


        // +++++++++++++++++++++++++++++++++++++++++++++ E_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // E_GROUP :
        // 1. 업라이트 로우 : 전체 / 덤벨

        // [lv/C]Event : 1. 업라이트 로우
        Event uprightRow = new Event();
        uprightRow.setEventName("업라이트 로우");
        uprightRow.setMuscleArea(SHOULDER);
        uprightRow.setEquipmentType(EquipmentType.BARBELL);
        uprightRow.setGroupType(GroupType.E_GROUP);
        uprightRow.setProperWeight(DEFAULT_WEIGHT);
        uprightRow.setMaxWeight(DEFAULT_WEIGHT);
        shoulder.add(uprightRow);


    } // End of method [setShoulder]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= lat =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void setLat() {

        // 참고 : 등의 중앙과 하부를 이루는 광배근과 등 상부를 이루는 승모근, 능형근, 대원


        // A_GROUP :
        // 1. 바벨 로우 (벤트오버 로우) / 바벨
        // 2. 덤벨 로우 (벤트오버 로우) / 덤벨
        // 3. T바 로우 (벤트오버 로우) / 바벨(T바)
        // 4. 시티드 케이블 로우 (페럴렐 로우) /
        // 5. 시티드 머신 로우
        // 6. 스탠딩 케이블 로우 (페럴렐 로우) /
        // 7. 머신 로우 (페럴렐 로우) / 원판 끼우고 하는 것
        // 8. 인버티드(리버스) 로우

        // B_GROUP :
        // 1. 랫 풀다운 (프론트 or 비하인드)
        // 2. 덤벨 풀오버
        // 3. 케이블 스트레이트 암 풀다운(스탠딩 풀오버)
        // 4. 풀업 (오버그립, 언더그립, 뉴트럴그립, 비하인드넥, 어시스티드)

        // C_GROUP :
        // 16. 바벨 슈러그
        // 17. 데드리프트
        // 18. 백 익스텐션

        final MuscleArea LAT = MuscleArea.LAT;

        this.lat = new ArrayList<>();

        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // A_GROUP :
        // 1. 벤트 오버 바벨 로우 (벤트오버 로우) / 바벨
        Event barbellRow = new Event();
        barbellRow.setEventName("바벨 로우");
        barbellRow.setMuscleArea(LAT);
        barbellRow.setEquipmentType(EquipmentType.BARBELL);
        barbellRow.setGroupType(GroupType.A_GROUP);
        barbellRow.setProperWeight(DEFAULT_WEIGHT);
        barbellRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(barbellRow);

        // 2. 덤벨 로우 (벤트오버 로우) / 덤벨
        Event dumbbellRow = new Event();
        dumbbellRow.setEventName("덤벨 로우");
        dumbbellRow.setMuscleArea(LAT);
        dumbbellRow.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellRow.setGroupType(GroupType.A_GROUP);
        dumbbellRow.setProperWeight(DEFAULT_WEIGHT);
        dumbbellRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(dumbbellRow);

        // 3. T바 로우 (벤트오버 로우) / 바벨(T바)
        Event tBarRow = new Event();
        tBarRow.setEventName("T바 로우");
        tBarRow.setMuscleArea(LAT);
        tBarRow.setEquipmentType(EquipmentType.BARBELL);
        tBarRow.setGroupType(GroupType.A_GROUP);
        tBarRow.setProperWeight(DEFAULT_WEIGHT);
        tBarRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(tBarRow);

        // 4. 시티드 케이블 로우 (페럴렐 로우) /
        Event seatedCableRow = new Event();
        seatedCableRow.setEventName("시티드 케이블 로우");
        seatedCableRow.setMuscleArea(LAT);
        seatedCableRow.setEquipmentType(EquipmentType.CABLE);
        seatedCableRow.setGroupType(GroupType.A_GROUP);
        seatedCableRow.setProperWeight(DEFAULT_WEIGHT);
        seatedCableRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(seatedCableRow);

        // 5. 시티드 머신 로우
        Event seatedMachineRow = new Event();
        seatedMachineRow.setEventName("시티드 머신 로우");
        seatedMachineRow.setMuscleArea(LAT);
        seatedMachineRow.setEquipmentType(EquipmentType.MACHINE);
        seatedMachineRow.setGroupType(GroupType.A_GROUP);
        seatedMachineRow.setProperWeight(DEFAULT_WEIGHT);
        seatedMachineRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(seatedMachineRow);

        // 6. 스탠딩 케이블 로우 (페럴렐 로우) /
        Event standingCableRow = new Event();
        standingCableRow.setEventName("스탠딩 케이블 로우");
        standingCableRow.setMuscleArea(LAT);
        standingCableRow.setEquipmentType(EquipmentType.CABLE);
        standingCableRow.setGroupType(GroupType.A_GROUP);
        standingCableRow.setProperWeight(DEFAULT_WEIGHT);
        standingCableRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(standingCableRow);

        // 7. 머신 로우 (페럴렐 로우) / 원판 끼우고 하는 것
        Event machineRow = new Event();
        machineRow.setEventName("머신 로우(원판)");
        machineRow.setMuscleArea(LAT);
        machineRow.setEquipmentType(EquipmentType.MACHINE);
        machineRow.setGroupType(GroupType.A_GROUP);
        machineRow.setProperWeight(DEFAULT_WEIGHT);
        machineRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(machineRow);

        // 8. 인버티드(리버스) 로우
        Event invertedRow = new Event();
        invertedRow.setEventName("인버티드(리버스) 로우");
        invertedRow.setMuscleArea(LAT);
        invertedRow.setEquipmentType(EquipmentType.FREE_WEIGHT);
        invertedRow.setGroupType(GroupType.A_GROUP);
        invertedRow.setProperWeight(DEFAULT_WEIGHT);
        invertedRow.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(invertedRow);


        // +++++++++++++++++++++++++++++++++++++++++++++ B_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // 1. 랫 풀다운 (프론트 or 비하인드)
        Event latPullDown = new Event();
        latPullDown.setEventName("랫 풀다운");
        latPullDown.setMuscleArea(LAT);
        latPullDown.setEquipmentType(EquipmentType.MACHINE);
        latPullDown.setGroupType(GroupType.B_GROUP);
        latPullDown.setProperWeight(DEFAULT_WEIGHT);
        latPullDown.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(latPullDown);

        // 2. 덤벨 풀오버
        Event dumbbellPullover = new Event();
        dumbbellPullover.setEventName("덤벨 풀오버");
        dumbbellPullover.setMuscleArea(LAT);
        dumbbellPullover.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellPullover.setGroupType(GroupType.B_GROUP);
        dumbbellPullover.setProperWeight(DEFAULT_WEIGHT);
        dumbbellPullover.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(dumbbellPullover);

        // 3. 케이블 스트레이트 암 풀다운(스탠딩 풀오버)
        Event cableStraightArmPullDown = new Event();
        cableStraightArmPullDown.setEventName("케이블 스트레이트 암 풀다운");
        cableStraightArmPullDown.setMuscleArea(LAT);
        cableStraightArmPullDown.setEquipmentType(EquipmentType.CABLE);
        cableStraightArmPullDown.setGroupType(GroupType.B_GROUP);
        cableStraightArmPullDown.setProperWeight(DEFAULT_WEIGHT);
        cableStraightArmPullDown.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(cableStraightArmPullDown);

        // 4. 풀업 (오버그립, 언더그립, 뉴트럴그립, 비하인드넥, 어시스티드)
        Event pullUp = new Event();
        pullUp.setEventName(" 풀업");
        pullUp.setMuscleArea(LAT);
        pullUp.setEquipmentType(EquipmentType.FREE_WEIGHT);
        pullUp.setGroupType(GroupType.B_GROUP);
        pullUp.setProperWeight(DEFAULT_WEIGHT);
        pullUp.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(pullUp);

        // +++++++++++++++++++++++++++++++++++++++++++++ C_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // 16. 바벨 슈러그
        Event barbellShrug = new Event();
        barbellShrug.setEventName("바벨 슈러그");
        barbellShrug.setMuscleArea(LAT);
        barbellShrug.setEquipmentType(EquipmentType.BARBELL);
        barbellShrug.setGroupType(GroupType.C_GROUP);
        barbellShrug.setProperWeight(DEFAULT_WEIGHT);
        barbellShrug.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(barbellShrug);

        // 17. 데드리프트
        Event deadLift = new Event();
        deadLift.setEventName("데드리프트");
        deadLift.setMuscleArea(LAT);
        deadLift.setEquipmentType(EquipmentType.BARBELL);
        deadLift.setGroupType(GroupType.C_GROUP);
        deadLift.setProperWeight(DEFAULT_WEIGHT);
        deadLift.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(deadLift);

        // 18. 백 익스텐션
        Event backExtension = new Event();
        backExtension.setEventName("백 익스텐션");
        backExtension.setMuscleArea(LAT);
        backExtension.setEquipmentType(EquipmentType.FREE_WEIGHT);
        backExtension.setGroupType(GroupType.C_GROUP);
        backExtension.setProperWeight(DEFAULT_WEIGHT);
        backExtension.setMaxWeight(DEFAULT_WEIGHT);
        lat.add(backExtension);

    } // End of method [setLat]

//
//    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= leg =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//    private void setLeg() {
//
//        // A_GROUP :
//        // 1. 스쿼트(풀, 패럴렐, 하프), 프론트 스쿼트,
//        // 2. 인클라인 스쿼트 머신
//        // 3. 레그 프레스 / 머신
//        // 4. 레그 익스텐션 / 머신
//        // 5. 레그 컬 / 머신
//        // 6. 런지 / 맨몸 of 스미스머신
//        // 7. 힙 애드덕터(안쪽, 모으기)
//        // 8. 힙 어브덕터(바깥쪽, 벌리기) / 없음
//        // 9. 힙 쓰러스트
//
//        final MuscleArea LEG = MuscleArea.LEG;
//
//        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
//        // 1. 스쿼트(풀, 패럴렐, 하프), 프론트 스쿼트,
//        Event squat = new Event();
//        squat.setEventName("스쿼트");
//        squat.setMuscleArea(LEG);
//        squat.setEquipmentType(EquipmentType.BARBELL);
//        squat.setGroupType(GroupType.A_GROUP);
//        squat.setProperWeight(DEFAULT_WEIGHT);
//        squat.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(squat);
//
//
//        // 2. 인클라인 스쿼트 머신
//        Event inclineSquatMachine = new Event();
//        inclineSquatMachine.setEventName("인클라인 스쿼트 머신");
//        inclineSquatMachine.setMuscleArea(LEG);
//        inclineSquatMachine.setEquipmentType(EquipmentType.MACHINE);
//        inclineSquatMachine.setGroupType(GroupType.A_GROUP);
//        inclineSquatMachine.setProperWeight(DEFAULT_WEIGHT);
//        inclineSquatMachine.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(inclineSquatMachine);
//
//        // 3. 레그 프레스 / 머신
//        Event legPress = new Event();
//        legPress.setEventName("레그 프레스");
//        legPress.setMuscleArea(LEG);
//        legPress.setEquipmentType(EquipmentType.MACHINE);
//        legPress.setGroupType(GroupType.A_GROUP);
//        legPress.setProperWeight(DEFAULT_WEIGHT);
//        legPress.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(legPress);
//
//        // 4. 레그 익스텐션 / 머신
//        Event legExtension = new Event();
//        legExtension.setEventName("레그 익스텐션");
//        legExtension.setMuscleArea(LEG);
//        legExtension.setEquipmentType(EquipmentType.MACHINE);
//        legExtension.setGroupType(GroupType.A_GROUP);
//        legExtension.setProperWeight(DEFAULT_WEIGHT);
//        legExtension.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(legExtension);
//
//        // 5. 레그 컬 / 머신
//        Event legCurl = new Event();
//        legCurl.setEventName("레그 컬");
//        legCurl.setMuscleArea(LEG);
//        legCurl.setEquipmentType(EquipmentType.MACHINE);
//        legCurl.setGroupType(GroupType.A_GROUP);
//        legCurl.setProperWeight(DEFAULT_WEIGHT);
//        legCurl.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(legCurl);
//
//        // 6. 런지 / 맨몸 of 스미스머신
//        Event lunge = new Event();
//        lunge.setEventName("런지");
//        lunge.setMuscleArea(LEG);
//        lunge.setEquipmentType(EquipmentType.MACHINE);
//        lunge.setGroupType(GroupType.A_GROUP);
//        lunge.setProperWeight(DEFAULT_WEIGHT);
//        lunge.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(lunge);
//
//        // 7. 힙 어덕터(대퇴골 내전근 모으기)
//        Event hipAdductor = new Event();
//        hipAdductor.setEventName("힙 어덕터(모으기)");
//        hipAdductor.setMuscleArea(LEG);
//        hipAdductor.setEquipmentType(EquipmentType.MACHINE);
//        hipAdductor.setGroupType(GroupType.A_GROUP);
//        hipAdductor.setProperWeight(DEFAULT_WEIGHT);
//        hipAdductor.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(hipAdductor);
//
//
//        // 8. 힙 앱덕터(대퇴골 외전근 벌리기) / 없음
//        Event hipAbductor = new Event();
//        hipAbductor.setEventName("힙 앱덕터(벌리기)");
//        hipAbductor.setMuscleArea(LEG);
//        hipAbductor.setEquipmentType(EquipmentType.MACHINE);
//        hipAbductor.setGroupType(GroupType.A_GROUP);
//        hipAbductor.setProperWeight(DEFAULT_WEIGHT);
//        hipAbductor.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(hipAbductor);
//
//        // 9. 힙 쓰러스트
//        Event hipThrust = new Event();
//        hipThrust.setEventName("힙 쓰러스트");
//        hipThrust.setMuscleArea(LEG);
//        hipThrust.setEquipmentType(EquipmentType.BARBELL);
//        hipThrust.setGroupType(GroupType.A_GROUP);
//        hipThrust.setProperWeight(DEFAULT_WEIGHT);
//        hipThrust.setMaxWeight(DEFAULT_WEIGHT);
//        leg.add(hipThrust);
//
//    } // End of method [setLeg]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= lower body =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void setLowerBody() {

        // A_GROUP :
        // 1. 스쿼트(풀, 패럴렐, 하프), 프론트 스쿼트,
        // 2. 인클라인 스쿼트 머신
        // 3. 레그 프레스 / 머신
        // 4. 레그 익스텐션 / 머신
        // 5. 레그 컬 / 머신
        // 6. 런지 / 맨몸 of 스미스머신
        // 7. 힙 애드덕터(안쪽, 모으기)
        // 8. 힙 어브덕터(바깥쪽, 벌리기) / 없음
        // 9. 힙 쓰러스트

        final MuscleArea LOWER_BODY = MuscleArea.LOWER_BODY;

        this.lowerBody = new ArrayList<>();

        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // 1. 스쿼트(풀, 패럴렐, 하프), 프론트 스쿼트,
        Event squat = new Event();
        squat.setEventName("스쿼트");
        squat.setMuscleArea(LOWER_BODY);
        squat.setEquipmentType(EquipmentType.BARBELL);
        squat.setGroupType(GroupType.A_GROUP);
        squat.setProperWeight(DEFAULT_WEIGHT);
        squat.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(squat);


        // 2. 인클라인 스쿼트 머신
        Event inclineSquatMachine = new Event();
        inclineSquatMachine.setEventName("인클라인 스쿼트 머신");
        inclineSquatMachine.setMuscleArea(LOWER_BODY);
        inclineSquatMachine.setEquipmentType(EquipmentType.MACHINE);
        inclineSquatMachine.setGroupType(GroupType.A_GROUP);
        inclineSquatMachine.setProperWeight(DEFAULT_WEIGHT);
        inclineSquatMachine.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(inclineSquatMachine);

        // 3. 레그 프레스 / 머신
        Event legPress = new Event();
        legPress.setEventName("레그 프레스");
        legPress.setMuscleArea(LOWER_BODY);
        legPress.setEquipmentType(EquipmentType.MACHINE);
        legPress.setGroupType(GroupType.A_GROUP);
        legPress.setProperWeight(DEFAULT_WEIGHT);
        legPress.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(legPress);

        // 4. 레그 익스텐션 / 머신
        Event legExtension = new Event();
        legExtension.setEventName("레그 익스텐션");
        legExtension.setMuscleArea(LOWER_BODY);
        legExtension.setEquipmentType(EquipmentType.MACHINE);
        legExtension.setGroupType(GroupType.A_GROUP);
        legExtension.setProperWeight(DEFAULT_WEIGHT);
        legExtension.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(legExtension);

        // 5. 레그 컬 / 머신
        Event legCurl = new Event();
        legCurl.setEventName("레그 컬");
        legCurl.setMuscleArea(LOWER_BODY);
        legCurl.setEquipmentType(EquipmentType.MACHINE);
        legCurl.setGroupType(GroupType.A_GROUP);
        legCurl.setProperWeight(DEFAULT_WEIGHT);
        legCurl.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(legCurl);

        // 6. 런지 / 맨몸 of 스미스머신
        Event lunge = new Event();
        lunge.setEventName("런지");
        lunge.setMuscleArea(LOWER_BODY);
        lunge.setEquipmentType(EquipmentType.MACHINE);
        lunge.setGroupType(GroupType.A_GROUP);
        lunge.setProperWeight(DEFAULT_WEIGHT);
        lunge.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(lunge);

        // 7. 힙 어덕터(대퇴골 내전근 모으기)
        Event hipAdductor = new Event();
        hipAdductor.setEventName("힙 어덕터(모으기)");
        hipAdductor.setMuscleArea(LOWER_BODY);
        hipAdductor.setEquipmentType(EquipmentType.MACHINE);
        hipAdductor.setGroupType(GroupType.A_GROUP);
        hipAdductor.setProperWeight(DEFAULT_WEIGHT);
        hipAdductor.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(hipAdductor);


        // 8. 힙 앱덕터(대퇴골 외전근 벌리기) / 없음
        Event hipAbductor = new Event();
        hipAbductor.setEventName("힙 앱덕터(벌리기)");
        hipAbductor.setMuscleArea(LOWER_BODY);
        hipAbductor.setEquipmentType(EquipmentType.MACHINE);
        hipAbductor.setGroupType(GroupType.A_GROUP);
        hipAbductor.setProperWeight(DEFAULT_WEIGHT);
        hipAbductor.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(hipAbductor);

        // 9. 힙 쓰러스트
        Event hipThrust = new Event();
        hipThrust.setEventName("힙 쓰러스트");
        hipThrust.setMuscleArea(LOWER_BODY);
        hipThrust.setEquipmentType(EquipmentType.BARBELL);
        hipThrust.setGroupType(GroupType.A_GROUP);
        hipThrust.setProperWeight(DEFAULT_WEIGHT);
        hipThrust.setMaxWeight(DEFAULT_WEIGHT);
        lowerBody.add(hipThrust);

    } // End of method [setLowerBody]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= arm =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void setArm() {

        // A_GROUP :
        // 상완운동
        // 이두
        // 1. 바벨 컬
        // 2. 덤벨 컬
        // 3. 해머 컬
        // 4. 리버스 컬
        // 5. 프리쳐 컬 / 받침대 + 바벨
        // 6. 컨센트레이션 컬 / 허벅지에 받치고 하는
        // 7. 인클라인 덤벨 컬
        // 8. 머신 컬
        // 9. 케이블 컬

        // B_GROUP :
        // 상완운동
        // 삼두근
        // 1. 트라이셉스 딥스
        // 2. 벤치 딥스
        // 3. 클로즈업 푸시업
        // 4. 바벨 오버헤드 익스텐션 =====
        // 5. 덤벨 오버헤드 익스텐션
        // 6. 라잉 트라이셉스 익스텐션
        // 7. 케이블 트라이셉스 익스텐션
        // 8. 트라이셉스 프레스 다운(숏바 or 로프) ======
        // 9. 덤벨 킥백
        // 10. 케이블 킥백

        // C_GROUP :
        // 전완 운동
        // 전완근
        // 11. 리스트 컬 ====
        // 12. 리스트 익스텐션

        final MuscleArea ARM = MuscleArea.ARM;

        this.arm = new ArrayList<>();

        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // 1. 바벨 컬
        Event barbellCurl = new Event();
        barbellCurl.setEventName("바벨 컬");
        barbellCurl.setMuscleArea(ARM);
        barbellCurl.setEquipmentType(EquipmentType.BARBELL);
        barbellCurl.setGroupType(GroupType.A_GROUP);
        barbellCurl.setProperWeight(DEFAULT_WEIGHT);
        barbellCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(barbellCurl);


        // 2. 덤벨 컬
        Event dumbbellCurl = new Event();
        dumbbellCurl.setEventName("덤벨 컬");
        dumbbellCurl.setMuscleArea(ARM);
        dumbbellCurl.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellCurl.setGroupType(GroupType.A_GROUP);
        dumbbellCurl.setProperWeight(DEFAULT_WEIGHT);
        dumbbellCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(dumbbellCurl);

        // 3. 해머 컬
        Event hammerCurl = new Event();
        hammerCurl.setEventName("해머 컬");
        hammerCurl.setMuscleArea(ARM);
        hammerCurl.setEquipmentType(EquipmentType.DUMBBELL);
        hammerCurl.setGroupType(GroupType.A_GROUP);
        hammerCurl.setProperWeight(DEFAULT_WEIGHT);
        hammerCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(hammerCurl);

        // 4. 리버스 컬
        Event reverseCurl = new Event();
        reverseCurl.setEventName("리버스 컬");
        reverseCurl.setMuscleArea(ARM);
        reverseCurl.setEquipmentType(EquipmentType.BARBELL);
        reverseCurl.setGroupType(GroupType.A_GROUP);
        reverseCurl.setProperWeight(DEFAULT_WEIGHT);
        reverseCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(reverseCurl);

        // 5. 프리쳐 컬 / 받침대 + 바벨
        Event preacherCurl = new Event();
        preacherCurl.setEventName("프리쳐 컬");
        preacherCurl.setMuscleArea(ARM);
        preacherCurl.setEquipmentType(EquipmentType.BARBELL);
        preacherCurl.setGroupType(GroupType.A_GROUP);
        preacherCurl.setProperWeight(DEFAULT_WEIGHT);
        preacherCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(preacherCurl);

        // 6. 컨센트레이션 컬 / 허벅지에 받치고 하는
        Event concentrationCurl = new Event();
        concentrationCurl.setEventName("컨센트레이션 컬");
        concentrationCurl.setMuscleArea(ARM);
        concentrationCurl.setEquipmentType(EquipmentType.DUMBBELL);
        concentrationCurl.setGroupType(GroupType.A_GROUP);
        concentrationCurl.setProperWeight(DEFAULT_WEIGHT);
        concentrationCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(concentrationCurl);

        // 7. 인클라인 덤벨 컬
        Event inclineDumbbellCurl = new Event();
        inclineDumbbellCurl.setEventName("인클라인 덤벨 컬");
        inclineDumbbellCurl.setMuscleArea(ARM);
        inclineDumbbellCurl.setEquipmentType(EquipmentType.DUMBBELL);
        inclineDumbbellCurl.setGroupType(GroupType.A_GROUP);
        inclineDumbbellCurl.setProperWeight(DEFAULT_WEIGHT);
        inclineDumbbellCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(inclineDumbbellCurl);

        // 8. 머신 컬
        Event machineCurl = new Event();
        machineCurl.setEventName("머신 컬");
        machineCurl.setMuscleArea(ARM);
        machineCurl.setEquipmentType(EquipmentType.MACHINE);
        machineCurl.setGroupType(GroupType.A_GROUP);
        machineCurl.setProperWeight(DEFAULT_WEIGHT);
        machineCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(machineCurl);

        // 9. 케이블 컬
        Event cableCurl = new Event();
        cableCurl.setEventName("케이블 컬");
        cableCurl.setMuscleArea(ARM);
        cableCurl.setEquipmentType(EquipmentType.MACHINE);
        cableCurl.setGroupType(GroupType.A_GROUP);
        cableCurl.setProperWeight(DEFAULT_WEIGHT);
        cableCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(cableCurl);


        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // 1. 트라이셉스 딥스
        Event tricepsDips = new Event();
        tricepsDips.setEventName("트라이셉스 딥스");
        tricepsDips.setMuscleArea(ARM);
        tricepsDips.setEquipmentType(EquipmentType.FREE_WEIGHT);
        tricepsDips.setGroupType(GroupType.B_GROUP);
        tricepsDips.setProperWeight(DEFAULT_WEIGHT);
        tricepsDips.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(tricepsDips);

        // 2. 벤치 딥스
        Event benchDips = new Event();
        benchDips.setEventName("벤치 딥스");
        benchDips.setMuscleArea(ARM);
        benchDips.setEquipmentType(EquipmentType.FREE_WEIGHT);
        benchDips.setGroupType(GroupType.B_GROUP);
        benchDips.setProperWeight(DEFAULT_WEIGHT);
        benchDips.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(benchDips);

        // 3. 클로즈업 푸시업
        Event closeUpPushUp = new Event();
        closeUpPushUp.setEventName("클로즈업 푸시업");
        closeUpPushUp.setMuscleArea(ARM);
        closeUpPushUp.setEquipmentType(EquipmentType.FREE_WEIGHT);
        closeUpPushUp.setGroupType(GroupType.B_GROUP);
        closeUpPushUp.setProperWeight(DEFAULT_WEIGHT);
        closeUpPushUp.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(closeUpPushUp);

        // 4. 바벨 오버헤드 트라이셉스 익스텐션 =====
        Event barbellOverheadTricepsExtension = new Event();
        barbellOverheadTricepsExtension.setEventName("바벨 오버헤드 트라이셉스 익스텐션");
        barbellOverheadTricepsExtension.setMuscleArea(ARM);
        barbellOverheadTricepsExtension.setEquipmentType(EquipmentType.BARBELL);
        barbellOverheadTricepsExtension.setGroupType(GroupType.B_GROUP);
        barbellOverheadTricepsExtension.setProperWeight(DEFAULT_WEIGHT);
        barbellOverheadTricepsExtension.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(barbellOverheadTricepsExtension);

        // 5. 덤벨 오버헤드 익스텐션
        Event dumbbellOverheadExtension = new Event();
        dumbbellOverheadExtension.setEventName("덤벨 오버헤드 익스텐션");
        dumbbellOverheadExtension.setMuscleArea(ARM);
        dumbbellOverheadExtension.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellOverheadExtension.setGroupType(GroupType.B_GROUP);
        dumbbellOverheadExtension.setProperWeight(DEFAULT_WEIGHT);
        dumbbellOverheadExtension.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(dumbbellOverheadExtension);

        // 6. 라잉 트라이셉스 익스텐션
        Event lyingTricepsExtension = new Event();
        lyingTricepsExtension.setEventName("라잉 트라이셉스 익스텐션");
        lyingTricepsExtension.setMuscleArea(ARM);
        lyingTricepsExtension.setEquipmentType(EquipmentType.BARBELL);
        lyingTricepsExtension.setGroupType(GroupType.B_GROUP);
        lyingTricepsExtension.setProperWeight(DEFAULT_WEIGHT);
        lyingTricepsExtension.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(lyingTricepsExtension);

        // 7. 케이블 트라이셉스 익스텐션
        Event cableTricepsExtension = new Event();
        cableTricepsExtension.setEventName("케이블 트라이셉스 익스텐션");
        cableTricepsExtension.setMuscleArea(ARM);
        cableTricepsExtension.setEquipmentType(EquipmentType.CABLE);
        cableTricepsExtension.setGroupType(GroupType.B_GROUP);
        cableTricepsExtension.setProperWeight(DEFAULT_WEIGHT);
        cableTricepsExtension.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(cableTricepsExtension);

        // 8. 트라이셉스 프레스 다운(숏바 or 로프) ======
        Event tricepsPressDown = new Event();
        tricepsPressDown.setEventName("트라이셉스 프레스 다운");
        tricepsPressDown.setMuscleArea(ARM);
        tricepsPressDown.setEquipmentType(EquipmentType.CABLE);
        tricepsPressDown.setGroupType(GroupType.B_GROUP);
        tricepsPressDown.setProperWeight(DEFAULT_WEIGHT);
        tricepsPressDown.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(tricepsPressDown);

        // 9. 덤벨 킥백
        Event dumbbellKickback = new Event();
        dumbbellKickback.setEventName("덤벨 킥백");
        dumbbellKickback.setMuscleArea(ARM);
        dumbbellKickback.setEquipmentType(EquipmentType.DUMBBELL);
        dumbbellKickback.setGroupType(GroupType.B_GROUP);
        dumbbellKickback.setProperWeight(DEFAULT_WEIGHT);
        dumbbellKickback.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(dumbbellKickback);

        // 10. 케이블 킥백
        Event cableKickback = new Event();
        cableKickback.setEventName("케이블 킥백");
        cableKickback.setMuscleArea(ARM);
        cableKickback.setEquipmentType(EquipmentType.CABLE);
        cableKickback.setGroupType(GroupType.B_GROUP);
        cableKickback.setProperWeight(DEFAULT_WEIGHT);
        cableKickback.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(cableKickback);


        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // 11. 리스트 컬 ====
        Event wristCurl = new Event();
        wristCurl.setEventName("리스트 컬");
        wristCurl.setMuscleArea(ARM);
        wristCurl.setEquipmentType(EquipmentType.BARBELL);
        wristCurl.setGroupType(GroupType.C_GROUP);
        wristCurl.setProperWeight(DEFAULT_WEIGHT);
        wristCurl.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(wristCurl);

        // 12. 리스트 익스텐션
        Event wristExtension = new Event();
        wristExtension.setEventName("리스트 익스텐션");
        wristExtension.setMuscleArea(ARM);
        wristExtension.setEquipmentType(EquipmentType.BARBELL);
        wristExtension.setGroupType(GroupType.C_GROUP);
        wristExtension.setProperWeight(DEFAULT_WEIGHT);
        wristExtension.setMaxWeight(DEFAULT_WEIGHT);
        arm.add(wristExtension);

    } // End of method [setArm]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void setEtc() {

        final MuscleArea ETC = MuscleArea.ETC;

        this.etc = new ArrayList<>();

        // +++++++++++++++++++++++++++++++++++++++++++++ A_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++ B_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++ C_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++ D_GROUP +++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++ E_GROUP +++++++++++++++++++++++++++++++++++++++++++++

    }
}
