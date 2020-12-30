package com.skymanlab.weighttraining.management.event.data;

import android.provider.BaseColumns;

import com.skymanlab.weighttraining.management.project.data.type.EquipmentType;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.io.Serializable;

public class Event implements Serializable {

    // instance variable
    private String key;                     // 0. key
    private String eventName;               // 1. event name = 종목 이름
    private MuscleArea muscleArea;          // 2. muscle area  = 근육 부위
    private EquipmentType equipmentType;    // 3. equipment type = 운동기구 종류
    private GroupType groupType;            // 4. group type = 그룹 유형
    private float properWeight;             // 5. proper weight = 적정 중량
    private float maxWeight;                // 6. max weight = 최대 중량
    private long selectionCounter;          // 7. selection counter = 선택 횟수

    // method : getter, setter

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public MuscleArea getMuscleArea() {
        return muscleArea;
    }

    public void setMuscleArea(MuscleArea muscleArea) {
        this.muscleArea = muscleArea;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public float getProperWeight() {
        return properWeight;
    }

    public void setProperWeight(float properWeight) {
        this.properWeight = properWeight;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public long getSelectionCounter() {
        return selectionCounter;
    }

    public void setSelectionCounter(long selectionCounter) {
        this.selectionCounter = selectionCounter;
    }

    public static class Entry implements BaseColumns {

        public static String COLUMN_NAME_KEY = "key";                           // 0. key
        public static String COLUMN_NAME_EVENT_NAME = "eventName";              // 1. event name
        public static String COLUMN_NAME_MUSCLE_AREA = "muscleArea";            // 2. muscle Area
        public static String COLUMN_NAME_EQUIPMENT_TYPE = "equipmentType";      // 3. equipment type
        public static String COLUMN_NAME_GROUP_TYPE = "groupType";              // 4. group type
        public static String COLUMN_NAME_PROPER_WEIGHT = "properWeight";        // 5. proper weight
        public static String COLUMN_NAME_MAX_WEIGHT = "maxWeight";              // 6. max weight
        public static String COLUMN_NAME_SELECTION_COUNTER = "selectionCounter";// 7. selection counter

    }
}
