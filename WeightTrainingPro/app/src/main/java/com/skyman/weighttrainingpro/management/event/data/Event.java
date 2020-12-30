package com.skyman.weighttrainingpro.management.event.data;


import com.skyman.weighttrainingpro.management.project.data.type.EquipmentType;
import com.skyman.weighttrainingpro.management.project.data.type.GroupType;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;

import java.io.Serializable;

public class Event implements Serializable {

    // instance variable
    private long count;                     // 0. count = 몇 번쩨 레코드인지
    private long userCount;                 // 1. user count = 몇 번째 유저인지
    private String eventName;               // 2. event name = 종목 이름
    private MuscleArea muscleArea;          // 3. muscle area  = 근육 부위
    private EquipmentType equipmentType;    // 4. equipment type = 운동기구 종류
    private GroupType groupType;            // 5. group type = 그룹 유형
    private float properWeight;             // 6. proper weight = 적정 중량
    private float maxWeight;                // 7. max weight = 최대 중량

    // method : getter, setter
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
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
}
