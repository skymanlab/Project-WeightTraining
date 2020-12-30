package com.skyman.weighttrainingpro.management.user.database;

import android.provider.BaseColumns;

public class UserTableSetting {

    // constant
    public static final String QUERY_CREATE_TABLE =
            "CREATE TABLE " + Entry.TABLE_NAME + " ( " +                    // create table user (
                    Entry._COUNT + " INTEGER PRIMARY KEY NOT NULL, " +      // 0. count
                    Entry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +           // 1. name
                    Entry.COLUMN_NAME_EMAIL + " TEXT NOT NULL, " +          // 2. email
                    Entry.COLUMN_NAME_SALT + " TEXT NOT NULL, " +           // 3. salt
                    Entry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL )";        // 4. password )

    // constant : if exists drop table
    public static final String QUERY_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // constant
    public static final String QUERY_SELECT_ALL =
            "SELECT * FROM " + Entry.TABLE_NAME;


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= inner class : Entry =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [innerClass] Table 의 column 을 나타내는 Entry
     */
    public class Entry implements BaseColumns {

        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";           // 1. name = 이름
        public static final String COLUMN_NAME_EMAIL = "email";         // 2. email = 이메일
        public static final String COLUMN_NAME_SALT = "salt";           // 3. salt = 나수 salt
        public static final String COLUMN_NAME_PASSWORD = "password";   // 4. password = 비밀번호

    } // End of innerClass
}
