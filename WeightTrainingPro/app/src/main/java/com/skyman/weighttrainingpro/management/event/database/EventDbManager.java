package com.skyman.weighttrainingpro.management.event.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.project.data.type.EquipmentType;
import com.skyman.weighttrainingpro.management.project.data.type.GroupType;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbConnector;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbManager;

import java.util.ArrayList;


public class EventDbManager extends ProjectDbManager {

    // constant
    private static final String CLASS_NAME = "[ED]_EventDbManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constructor
    public EventDbManager(ProjectDbConnector projectDbConnector) {
        super(projectDbConnector);
    }


    /**
     * [method] [query:insert] event 데이터를 event table 에 저장하기 / count 입력 안 함 -> 자동 등록
     *
     * @param eventName     [1] 종목 이름
     * @param userCount     [2] 몇 번째 유저인지
     * @param muscleArea    [3] 근육 부위
     * @param equipmentType [4] 운동기구 종류
     * @param groupType     [5] 그룹 유형
     * @param properWeight  [6] 적정 중량
     * @param maxWeight     [7] 최대 중량
     * @return 데이터베이스 저장 실패 성공 여부
     */
    public long saveContent(String eventName, long userCount, MuscleArea muscleArea, EquipmentType equipmentType, GroupType groupType, float properWeight, float maxWeight) {

        final String METHOD_NAME = "[saveContent] ";

        // [lv/l]result : event table 에 데이터를 insert 결과값
        long result = -100;

        // [check 1] : database 와 연결되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=>> step 1. database 사용 준비가 되었습니다.");

            // [check 2] : 매개변수 값의 형식이 올바르다.
            if ((0 < userCount)                                 // 1. user count
                    && !eventName.equals("")                    // 2. event name
                    && !muscleArea.toString().equals("")        // 3. muscle area
                    && !equipmentType.toString().equals("")     // 4. equipment type
                    && !groupType.toString().equals("")         // 5. group type
                    && (0 <= properWeight)                      // 6. proper weight
                    && (0 <= maxWeight)) {                      // 7. max weight

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=>> step 2. 매개변수 검사가 완료되었습니다.");

                // [lv/C]SQLiteDatabase : 연결된 databases 의 projectDbOpenHelper 를 통해 writable database 를 가져오기
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getWritableDatabase();

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=>> step 3. writable db 가져오기가 성공했습니다.");

                // [lv/C]ContentValues : event table 에 저장하기 위한 데이터를 담기
                ContentValues storageData = new ContentValues();
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_USER_COUNT, userCount);                         // 1. user count
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_EVENT_NAME, eventName);                         // 2. event name
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_MUSCLE_AREA, muscleArea.toString());            // 3. muscle area
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_EQUIPMENT_TYPE, equipmentType.toString());      // 4. equipment type
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_GROUP_TYPE, groupType.toString());              // 5. target muscle type
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_PROPER_WEIGHT, properWeight);                   // 6. proper weight
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_MAX_WEIGHT, maxWeight);                         // 7. max weight

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=>> step 4. 매개변수의 값을 ContentValues 객체에 입력하였습니다.");

                // [lv/l]result : database 저장 실패, 성공 여부
                result = db.insert(EventTableSetting.Entry.TABLE_NAME, null, storageData);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=>> step 5. 해당 데이터를 event table 에 query 를 입력하는데 성공하였습니다. / 결과 = " + result);

                // [lv/C]SQLiteDatabase : 사용 후 db 는 close
                db.close();

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=>> step 5. 사용한 db 는 반납하였습니다.");

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 매개변수 형식이 이상한데! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : database 를 사용할 준비가 되어있지 않아! <=");
        } // [check 1]

        return result;

    } // End of method [saveContent]


    /**
     * [method] [query:insert] event 데이터를 event table 에 저장하기
     *
     * @param event event table 에 저장할 데이터가 담긴 Event 객체
     * @return 데이터베이스 저장 실패 성공 여부
     */
    public long saveContent(Event event) {

        final String METHOD_NAME = "[saveContent] ";

        // [lv/l]result : event table 에 데이터를 insert 결과값
        long result = -100;

        // [check 1] : database 와 연결되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            // [check 2] : 매개변수 값의 형식이 올바르다.
            if ((0 < event.getUserCount())                              // 1. user count
                    && !event.getEventName().equals("")                 // 2. event name
                    && !event.getMuscleArea().equals("")                // 3. muscle area
                    && !event.getEquipmentType().equals("")             // 4. equipment type
                    && !event.getGroupType().toString().equals("")      // 5. group type
                    && (0 <= event.getProperWeight())                   // 6. proper weight
                    && (0 <= event.getMaxWeight())) {                   // 7. max weight

                // [lv/C]SQLiteDatabase : 연결된 databases 의 projectDbOpenHelper 를 통해 writable database 를 가져오기
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getWritableDatabase();

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> count =  " + event.getCount());

                // [lv/C]ContentValues : event table 에 저장하기 위한 데이터를 담기
                ContentValues storageData = new ContentValues();
                storageData.put(EventTableSetting.Entry._COUNT, event.getCount());                                          // 0. count
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_USER_COUNT, event.getUserCount());                      // 1. user count
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_EVENT_NAME, event.getEventName());                      // 2. event name
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_MUSCLE_AREA, event.getMuscleArea().toString());         // 3. muscle area
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_EQUIPMENT_TYPE, event.getEquipmentType().toString());   // 4. equipment type
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_GROUP_TYPE, event.getGroupType().toString());           // 5. group type
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_PROPER_WEIGHT, event.getProperWeight());                // 6. proper weight
                storageData.put(EventTableSetting.Entry.COLUMN_NAME_MAX_WEIGHT, event.getMaxWeight());                      // 7. max weight

                // [lv/l]result : database 저장 실패, 성공 여부 / row 번호 = 몇 번째 레코드야?
                result = db.insert(EventTableSetting.Entry.TABLE_NAME, null, storageData);

                // [lv/C]SQLiteDatabase : 사용 후 db 는 close
                db.close();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 매개변수 형식이 이상한데! <=");
            } // [check 1]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : database 를 사용할 준비가 되어있지 않아! <=");
        } // [check 1]

        return result;

    } // End of method [saveContent]


    /**
     * [method] [query:select] event table 의 모든 내용을 가져오기
     *
     * @return event table 의 모든 Event 가 추가된 배열
     */
    public ArrayList<Event> loadContent() {

        final String METHOD_NAME = "[loadContent] ";

        // [lv/C]ArrayList<Event> : 해당 데이터를 담을 객체 선언
        ArrayList<Event> eventArrayList = null;

        // [check 1] : database 와 연결되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            // [lv/C]ArrayList<Event> : 가져올 준비가 되었으므로 객체 생성하기
            eventArrayList = new ArrayList<Event>();

            // [lv/C]SQLiteDatabase : 연결된 databases 의 projectDbOpenHelper 를 통해 readable database 를 가져오기
            SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getReadableDatabase();

            // [lv/C]Cursor :
            Cursor readCursor = db.rawQuery(EventTableSetting.QUERY_SELECT_ALL, null);

            // [cycle 1] : entry table 에서 가져온 데이터가 있다.
            while (readCursor.moveToNext()) {

                // [lv/C]Event : 하나의 레코드를 담을 Event 객체 생성 및 내용 담기
                Event event = new Event();
                event.setCount(readCursor.getLong(0));                                                  // [0] count
                event.setUserCount(readCursor.getLong(1));                                              // [1] user count
                event.setEventName(readCursor.getString(2));                                            // [2] event name
                event.setMuscleArea(DataManager.convertMuscleArea(readCursor.getString(3)));            // [3] muscle area
                event.setEquipmentType(DataManager.convertEquipmentType(readCursor.getString(4)));      // [4] equipment type
                event.setGroupType(DataManager.convertGroupType(readCursor.getString(5)));              // [5] group type
                event.setProperWeight(readCursor.getInt(6));                                            // [6] proper weight
                event.setMaxWeight(readCursor.getInt(7));                                               // [7] max weight

                // [lv/C]ArrayList<Event> : 반환값에 추가
                eventArrayList.add(event);

            } // [cycle 1]

            // [lv/C]SQLiteDatabase : database close
            db.close();

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : database 를 사용할 준비가 되어있지 않아! <=");
        } // [check 1]

        return eventArrayList;
    } // End of method [loadContent]


    /**
     * [method] [query:select] event table 의 모든 내용을 가져오기
     *
     * @param muscleArea 특정 muscleArea 에 해당하는 데이터만 검색하기 위함
     * @return event table 에서 해당 muscleArea 와 같은 event 데이터가 추가된 배열
     */
    public ArrayList<Event> loadContentBy(String muscleArea) {

        final String METHOD_NAME = "[loadContentBy] ";

        // [lv/C]ArrayList<Event> : 해당
        ArrayList<Event> eventArrayList = new ArrayList<Event>();

        // [check 1] : database 와 연결되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            // [check 2] : 매개변수 값의 형식이 올바르다.
            if (!muscleArea.equals("")) {

                // [lv/C]SQLiteDatabase : 연결된 databases 의 projectDbOpenHelper 를 통해 readable database 를 가져오기
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getReadableDatabase();

                // [lv/C]String : select * from event where muscleArea=?
                final String QUERY = EventTableSetting.QUERY_SELECT_WHERE_MUSCLES_AREA + "'" + muscleArea + "'";

                // [lv/C]Cursor :
                Cursor readCursor = db.rawQuery(QUERY, null);

                // [cycle 1] : entry table 에서 가져온 데이터가 있다.
                while (readCursor.moveToNext()) {

                    // [lv/C]Event : 하나의 레코드를 담을 Event 객체 생성 및 내용 담기
                    Event event = new Event();
                    event.setCount(readCursor.getLong(0));                                                  // [0] count
                    event.setUserCount(readCursor.getLong(1));                                              // [1] user count
                    event.setEventName(readCursor.getString(2));                                            // [2] event name
                    event.setMuscleArea(DataManager.convertMuscleArea(readCursor.getString(3)));            // [3] muscle area
                    event.setEquipmentType(DataManager.convertEquipmentType(readCursor.getString(4)));      // [4] equipment type
                    event.setGroupType(DataManager.convertGroupType(readCursor.getString(5)));              // [5] group type
                    event.setProperWeight(readCursor.getInt(6));                                            // [6] proper weight
                    event.setMaxWeight(readCursor.getInt(7));                                               // [7] max weight

                    // [lv/C]ArrayList<Event> : 반환값에 추가
                    eventArrayList.add(event);

                } // [cycle 1]

                // [lv/C]SQLiteDatabase : database close
                db.close();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 매개변수 형식이 이상한데! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : database 를 사용할 준비가 되어있지 않아! <=");
        } // [check 1]

        return eventArrayList;
    } // End of method [loadContentBy]


    /**
     * [method] [query:select] event table 의 모든 내용을 가져오기
     *
     * @param muscleArea 특정 muscleArea 에 해당하는 데이터만 검색하기 위함
     * @return event table 에서 해당 muscleArea 와 같은 event 데이터가 추가된 배열
     */
    public ArrayList<Event> loadContentBy(long userCount, MuscleArea muscleArea) {

        final String METHOD_NAME = "[loadContentBy] ";

        // [lv/C]ArrayList<Event> : 해당
        ArrayList<Event> eventArrayList = new ArrayList<Event>();

        // [check 1] : database 와 연결되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            // [check 2] : 매개변수 값의 형식이 올바르다.
            if ((0 < userCount)
                    && !muscleArea.equals("")) {

                // [lv/C]SQLiteDatabase : 연결된 databases 의 projectDbOpenHelper 를 통해 readable database 를 가져오기
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getReadableDatabase();

                // [lv/C]String : select * from event where muscleArea=?
                final String QUERY = EventTableSetting.mappingSelectWhereQueryBy(userCount, muscleArea.toString());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** query 확인 ***");
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. query = " + QUERY);

                // [lv/C]Cursor :
                Cursor readCursor = db.rawQuery(QUERY, null);

                // [cycle 1] : entry table 에서 가져온 데이터가 있다.
                while (readCursor.moveToNext()) {

                    // [lv/C]Event : 하나의 레코드를 담을 Event 객체 생성 및 내용 담기
                    Event event = new Event();
                    event.setCount(readCursor.getLong(0));                                                  // [0] count
                    event.setUserCount(readCursor.getLong(1));                                              // [1] user count
                    event.setEventName(readCursor.getString(2));                                            // [2] event name
                    event.setMuscleArea(DataManager.convertMuscleArea(readCursor.getString(3)));            // [3] muscle area
                    event.setEquipmentType(DataManager.convertEquipmentType(readCursor.getString(4)));      // [4] equipment type
                    event.setGroupType(DataManager.convertGroupType(readCursor.getString(5)));              // [5] group type
                    event.setProperWeight(readCursor.getInt(6));                                            // [6] proper weight
                    event.setMaxWeight(readCursor.getInt(7));                                               // [7] max weight

                    // [lv/C]ArrayList<Event> : 반환값에 추가
                    eventArrayList.add(event);

                } // [cycle 1]

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 매개변수 형식이 이상한데! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : database 를 사용할 준비가 되어있지 않아! <=");
        } // [check 1]

        return eventArrayList;
    } // End of method [loadContentBy]


    /**
     * [method] [query:update] event table 에서 count 와 userCount 에 해당하는 레코드를 수정한다.
     */
    public int updateContentByCountAndUserCount(Event event) {

        final String METHOD_NAME = "[updateContentByCountAndUserCount] ";

        // [lv/i]result : update 실행 결과
        int result = 0;

        // [check 1] : 데이터베이스를 사용할 준비가 되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            // [check 2] : 해당 event 의 정보가 제대로 입력되었다.
            if ((0 < event.getCount())
                    && (0 < event.getUserCount())
                    && !event.getEventName().equals("")
                    && !event.getMuscleArea().toString().equals("")
                    && !event.getEquipmentType().toString().equals("")
                    && !event.getGroupType().toString().equals("")
                    && (0 <= event.getProperWeight())
                    && (0 <= event.getMaxWeight())
            ) {

                // [lv/C]SQLiteDatabase : 쓰기용으로 가져오기
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getWritableDatabase();

                // [lv/C]String : 해당 Query 문 만들기
                final String QUERY_WHERE = EventTableSetting.mappingWhereQueryBy(event.getCount(), event.getUserCount());

                // [lv/C]ContentValues : update 할 column 들을 담는다.
                ContentValues values = new ContentValues();
                values.put(EventTableSetting.Entry.COLUMN_NAME_EVENT_NAME, event.getEventName());                       // 2. event name
                values.put(EventTableSetting.Entry.COLUMN_NAME_MUSCLE_AREA, event.getMuscleArea().toString());          // 3. muscle area
                values.put(EventTableSetting.Entry.COLUMN_NAME_EQUIPMENT_TYPE, event.getEquipmentType().toString());    // 4. equipment type
                values.put(EventTableSetting.Entry.COLUMN_NAME_GROUP_TYPE, event.getGroupType().toString());            // 5. group type
                values.put(EventTableSetting.Entry.COLUMN_NAME_PROPER_WEIGHT, event.getProperWeight());                 // 6. proper weight
                values.put(EventTableSetting.Entry.COLUMN_NAME_MAX_WEIGHT, event.getMaxWeight());                       // 7. max weight

                // [lv/i]result : db 의 update method 를 실행하여 그 결과값(업데이된 레코드의 개수)을 가져온다.
                result = db.update(EventTableSetting.Entry.TABLE_NAME, values, QUERY_WHERE, null);

                // [lv/C]SQLiteDatabase : close
                db.close();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 매개변수 형식이 이상한데! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : database 를 사용할 준비가 되어있지 않아! <=");
        } // [check 1]

        return result;

    } // End of method [updateContentByCountAndUserCount]


    /**
     * [method] [delete] event table 의 특정 count 와 userCount 에 해당하는 레코드를 삭제한다.
     */
    public int deleteContentBy(long count, long userCount) {

        final String METHOD_NAME = "[deleteContentBy] ";

        // [lv/i]result : 해당 레코드를 삭제했을 때 삭제한 레코드 개수 이거나, 데이터베이스 성공/실패 여부
        int result = -100;

        // [check 1] : 데이터베이스를 사용할 준비가 되어있다.
        if (getProjectDbConnector().isConnectedDB()) {

            // [check 2] : 매개변수의 형식을 검사한다.
            if ((0 < count) && (0 < userCount)) {

                // [lv/C]SQLiteDatabase : 쓰기용 데이터베이스를 가져온다.
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getWritableDatabase();

                // [lv/C]String : delete query 문의 where 절에 관한 -> count='' and userCount=''
                String QUERY_WHERE = EventTableSetting.mappingWhereQueryBy(count, userCount);

                // [lv/i]result : 해당 내용을 데이터 삭제한다.
                result = db.delete(EventTableSetting.Entry.TABLE_NAME, QUERY_WHERE, null);

                // [lv/C]SQLiteDatabase : close
                db.close();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 매개변수 형식이 이상한데! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : database 를 사용할 준비가 되어있지 않아! <=");
        } // [check 1]

        return result;

    } // End of method [deleteContentBy]

}
