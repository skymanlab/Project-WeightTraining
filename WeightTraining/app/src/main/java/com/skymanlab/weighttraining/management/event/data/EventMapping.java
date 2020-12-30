package com.skymanlab.weighttraining.management.event.data;

import java.util.HashMap;

public class EventMapping {

    // constant
    public static final String KEY ="key";
    public static final String EVENT_NAME = "eventName";
    public static final String MUSCLE_AREA = "muscleArea";
    public static final String EQUIPMENT_TYPE = "equipmentType";
    public static final String GROUP_TYPE = "groupType";
    public static final String PROPER_WEIGHT = "properWeight";
    public static final String MAX_WEIGHT = "maxWeight";
    public static final String SELECTION_COUNTER = "selectionCounter";

    /**
     * [method] 모든 내용을 hashMap 에 매핑하기
     */
    public static HashMap<String, Object> mappingHashMapForAdd(Event event) {
        // [lv/C]HashMap<String, Object> :
        HashMap<String, Object> mappingData = new HashMap<>();

        // 1. event name
        mappingData.put(EVENT_NAME, event.getEventName());

        // 2. muscle Area
        mappingData.put(MUSCLE_AREA, event.getMuscleArea());

        // 3. equipment type
        mappingData.put(EQUIPMENT_TYPE, event.getEquipmentType());

        // 4. group type
        mappingData.put(GROUP_TYPE, event.getGroupType());

         // 5. proper weight
        mappingData.put(PROPER_WEIGHT, event.getProperWeight());

        // 6. max weight
        mappingData.put(MAX_WEIGHT, event.getMaxWeight());

        // 7. selection counter
        mappingData.put(SELECTION_COUNTER, event.getSelectionCounter());

        return mappingData;

    }

}
