package com.skyman.weighttrainingpro.management.event.database;

import android.provider.BaseColumns;

public class EventTableSetting {

    // constant
    public static final String QUERY_CREATE_TABLE =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +                         // create table event
                    Entry._COUNT + " INTEGER PRIMARY KEY, " +                   // 0. count
                    Entry.COLUMN_NAME_USER_COUNT + " INTEGER NOT NULL, " +      // 1. userCount
                    Entry.COLUMN_NAME_EVENT_NAME + " TEXT NOT NULL, " +         // 2. eventName
                    Entry.COLUMN_NAME_MUSCLE_AREA + " TEXT NOT NULL, " +        // 3. muscleArea
                    Entry.COLUMN_NAME_EQUIPMENT_TYPE + " TEXT NOT NULL, " +     // 4. equipmentType
                    Entry.COLUMN_NAME_GROUP_TYPE + " TEXT NOT NULL, " +         // 5. targetMuscleType
                    Entry.COLUMN_NAME_PROPER_WEIGHT + " REAL, " +               // 6. properWeight
                    Entry.COLUMN_NAME_MAX_WEIGHT + " REAL )";                   // 7. maxWeight


    // class constant : if exists drop table
    public static final String QUERY_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // constant
    public static final String QUERY_SELECT_ALL =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // constant
    public static final String QUERY_SELECT_WHERE_MUSCLES_AREA =
            "SELECT * FROM " + Entry.TABLE_NAME +
                    " WHERE " + Entry.COLUMN_NAME_MUSCLE_AREA + "=";
    // constant
    public static final String QUERY_SELECT_WHERE =
            "SELECT * FROM " + Entry.TABLE_NAME +
                    " WHERE ";

    // constant
    public static final String QUERY_UPDATE_WHERE =
            "UPDATE FROM " + Entry.TABLE_NAME + " WHERE ";


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= method =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] [select] select * from event where muscleArea="" 형태의 문자열로 mapping
     */
    public static String mappingSelectWhereQuery(String muscleArea) {

        // [lv/C]StringBuilder : select query 을 만들 때 where 에 userCount 와 muscleArea 값을 추가한다.
        StringBuilder mappingQuery = new StringBuilder();

        // 1. select Where muscleArea 추가
        mappingQuery.append(QUERY_SELECT_WHERE);

        // 2. muscleArea 추가
        mappingQuery.append(Entry.COLUMN_NAME_MUSCLE_AREA);
        mappingQuery.append("=");
        mappingQuery.append(muscleArea);

        return mappingQuery.toString();
    } // End of method [mappingSelectWhereQuery]

    /**
     * [method] [select] select * from event where userCount='' and muscleArea='' 형태로 mapping
     *
     * @param userCount  [3] 몇 번째 user 인지?
     * @param muscleArea [4] 근육 부위
     * @return 해당 형태로 mapping 된 query
     */
    public static String mappingSelectWhereQueryBy(long userCount, String muscleArea) {

        // [lv/C]StringBuilder : select query 을 만들 때 where 에 userCount 와 muscleArea 값을 추가한다.
        StringBuilder mappingQuery = new StringBuilder();

        // 1. select where 문 추가
        mappingQuery.append(QUERY_SELECT_WHERE);

        // 2. userCount 추가
        mappingQuery.append(Entry.COLUMN_NAME_USER_COUNT);
        mappingQuery.append("=");
        mappingQuery.append("'");
        mappingQuery.append(userCount);
        mappingQuery.append("'");

        mappingQuery.append(" AND ");

        // 3. muscleArea 추가
        mappingQuery.append(Entry.COLUMN_NAME_MUSCLE_AREA);
        mappingQuery.append("=");
        mappingQuery.append("'");
        mappingQuery.append(muscleArea);
        mappingQuery.append("'");

        return mappingQuery.toString();

    } // End of method [mappingSelectWhereQuery]


    /**
     * [method] [update] update from event where count ='' and userCount='' 형태 중
     * Where 절의 [where count='' and userCount=''] 의 count 와 userCount 를 mapping 하여 해당 문자열을 반환한다.
     *
     * @param count     event table 의 count column
     * @param userCount event table 의 userCount column
     * @return 해당 Query 의 Where 절에 매핑된 문자열 반환한다.
     */
    public static String mappingWhereQueryBy(long count, long userCount) {

        // [lv/C]StringBuilder : mapping query
        StringBuilder mappingQuery = new StringBuilder();

        // 1. count 추가
        mappingQuery.append(Entry._COUNT);
        mappingQuery.append("=");
        mappingQuery.append(count);

        mappingQuery.append(" AND ");

        // 2. userCount 추가
        mappingQuery.append(Entry.COLUMN_NAME_USER_COUNT);
        mappingQuery.append("=");
        mappingQuery.append(userCount);

        return mappingQuery.toString();

    } // End of method [mappingWhereQueryBy]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= inner class : Entry =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [innerClass] Table 의 column 을 나타내는 Entry
     */
    public static class Entry implements BaseColumns {

        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_USER_COUNT = "userCount";            // 1. user count = 몇 번째 유저인지
        public static final String COLUMN_NAME_EVENT_NAME = "eventName";            // 2. event name = 종목 이름
        public static final String COLUMN_NAME_MUSCLE_AREA = "muscleArea";          // 3. muscle area = 근육 부위
        public static final String COLUMN_NAME_EQUIPMENT_TYPE = "equipmentType";    // 4. equipment type = 운동기기 종류
        public static final String COLUMN_NAME_GROUP_TYPE = "groupType";            // 5. group type = 그룹 유형
        public static final String COLUMN_NAME_PROPER_WEIGHT = "properWeight";      // 6. proper weight = 적정 중량
        public static final String COLUMN_NAME_MAX_WEIGHT = "maxWeight";            // 7. max weight = 최대 중량 (1RM)

    } // End of innerClass
}
