package com.skyman.weighttrainingpro.management.user.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbConnector;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbManager;
import com.skyman.weighttrainingpro.management.user.data.User;

public class UserDbManager extends ProjectDbManager {

    // constant
    private static final String CLASS_NAME = "[UD]_UserDbManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constructor
    public UserDbManager(ProjectDbConnector projectDbConnector) {
        super(projectDbConnector);
    }


    /**
     * [method] [insert] user table 에 해당 데이터를 저장한다.
     *
     * @param count
     * @param name
     * @param email
     * @param salt
     * @param password
     * @return
     */
    public long saveContent(long count, String name, String email, String salt, String password) {

        final String METHOD_NAME = "[saveContent] ";

        // [lv/l]result : 결과를 반납
        long result = 0;

        // [check 1] : 데이터베이스 이용 준비가 되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 데이터베이스 사용준비가 되었습니다.<=");

            // [check 2] : 매개변수 형식이 일치한다.
            if ((0 < count)                         // 0. count
                    && !name.equals("")             // 1. name
                    && !email.equals("")            // 2. email
                    && !salt.equals("")             // 3. salt
                    && !password.equals("")) {      // 5. password

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 매개변수들의 형식이 일치합니다. <=");

                // [lv/C]SQLiteDatabase : writable database 가져오기
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 매개변수 담기
                ContentValues values = new ContentValues();
                values.put(UserTableSetting.Entry._COUNT, count);                   // 0. count
                values.put(UserTableSetting.Entry.COLUMN_NAME_NAME, name);          // 1. name
                values.put(UserTableSetting.Entry.COLUMN_NAME_EMAIL, email);        // 2. email
                values.put(UserTableSetting.Entry.COLUMN_NAME_SALT, salt);          // 3. salt
                values.put(UserTableSetting.Entry.COLUMN_NAME_PASSWORD, password);  // 4. password

                // [lv/l]result : user table 에 저장 후 몇 번째 row 인지 반환받기 / 데이터베이스 입력 성공 여부도 체크
                result = db.insert(UserTableSetting.Entry.TABLE_NAME, null, values);

                // [lv/C]SQLiteDatabase : 사용 후 닫기
                db.close();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 이상한 값이 입력되었어! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 데이터베이스 사용준비가 되지 않았어! <=");

        } // [check 1]

        return result;

    } // End of method [saveContent]


    /**
     * [method] [insert] user table 에 해당 데이터를 저장한다.
     *
     * @param user
     * @return
     */
    public long saveContent(User user) {

        final String METHOD_NAME = "[saveContent] ";

        // [lv/l]result : 결과를 반납
        long result = 0;

        // [check 1] : 데이터베이스 이용 준비가 되었다.
        if (getProjectDbConnector().isConnectedDB()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 데이터베이스 사용준비가 되었습니다.<=");

            // [check 2] : 매개변수 형식이 일치한다.
            if ((0 < user.getCount())                       // 0. count
                    && !user.getName().equals("")           // 1. name
                    && !user.getEmail().equals("")          // 2. email
                    && !user.getSalt().equals("")           // 3. salt
                    && !user.getPassword().equals("")) {    // 4. password

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 매개변수들의 형식이 일치합니다. <=");

                // [lv/C]SQLiteDatabase : writable database 가져오기
                SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 매개변수 담기
                ContentValues values = new ContentValues();
                values.put(UserTableSetting.Entry._COUNT, user.getCount());                     // 0. count
                values.put(UserTableSetting.Entry.COLUMN_NAME_NAME, user.getName());            // 1. name
                values.put(UserTableSetting.Entry.COLUMN_NAME_EMAIL, user.getEmail());          // 2. email
                values.put(UserTableSetting.Entry.COLUMN_NAME_SALT, user.getSalt());            // 3. salt
                values.put(UserTableSetting.Entry.COLUMN_NAME_PASSWORD, user.getPassword());    // 4. password

                // [lv/l]result : user table 에 저장 후 몇 번째 row 인지 반환받기 / 데이터베이스 입력 성공 여부도 체크
                result = db.insert(UserTableSetting.Entry.TABLE_NAME, null, values);

                // [lv/C]SQLiteDatabase : 사용 후 닫기
                db.close();

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 이상한 값이 입력되었어! <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 데이터베이스 사용준비가 되지 않았어! <=");

        } // [check 1]

        return result;

    } // End of method [saveContent]


    /**
     * [method] [select] user table 의 데이터를 모두 읽어온다.
     * <p>
     * 정확히는 1개만 저장되어있고 그 1개만 가져오는 것!
     */
    public User loadContent() {

        final String METHOD_NAME = "[loadContent] ";

        // [lv/C]User : my User data 가져오기
        User user = null;

        // [check 1] : 데이터베이스 사용준비가 되었습니다.
        if (getProjectDbConnector().isConnectedDB()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 데이터 가져올 준비가 되었습니다. <= ");
            // [lv/C]SQLiteDatabase : readable database 가져오기
            SQLiteDatabase db = getProjectDbConnector().getProjectDbOpenHelper().getReadableDatabase();

            // [lv/C]Cursor : query 문 실행 후 결과를 Cursor 객체로 받기
            Cursor cursor = db.rawQuery(UserTableSetting.QUERY_SELECT_ALL, null);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** cursor 의 count 값 확인 ***");
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. count = " + cursor.getCount());

            // [check 2] : 첫번째 레코드가 있어요!
            if (cursor.moveToFirst()) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 첫 번째 레코드가 있습니다. <= ");
                // [lv/C]User : 객체 생성 및 cursor 를 통해 객체 저장
                user = new User();
                user.setCount(cursor.getLong(0));       // 0. count
                user.setName(cursor.getString(1));      // 1. name
                user.setEmail(cursor.getString(2));     // 2. email
                user.setSalt(cursor.getString(3));      // 3. salt
                user.setPassword(cursor.getString(4));  // 4. password

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> 1. count = " + user.getCount());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> 2. name = " + user.getName());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> 3. email = " + user.getEmail());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> 4. salt = " + user.getSalt());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 5. password = " + user.getPassword());

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 아무 데이터도 없는데! <=");
            } // [check 2]

            db.close();

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 데이터베이스 사용 준비가 되지 않았습니다. <=");
        } // [check 1]

        return user;
    } // End of method [loadContent]


}
