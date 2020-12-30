package com.skyman.weighttrainingpro.management.project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.database.EventTableSetting;
import com.skyman.weighttrainingpro.management.user.database.UserTableSetting;

public class ProjectDbOpenHelper extends SQLiteOpenHelper {

    // constant
    private static final String CLASS_NAME = "[PD]_ProjectDbOpenHelper";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant : database information
    private static final String DATABASE_NAME = "weightTrainingPro";
    private static final int DATABASE_VERSION = 1;

    // constructor
    public ProjectDbOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String METHOD_NAME = "[onCreate] ";

        // [lv/C]SQLiteDatabase : user table 생성
        db.execSQL(UserTableSetting.QUERY_CREATE_TABLE);

        // [lv/C]SQLiteDatabase : event table 생성
        db.execSQL(EventTableSetting.QUERY_CREATE_TABLE);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** 데이터베이스에 user, event table 을 생성하였습니다. ***");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        final String METHOD_NAME = "[onUpgrade] ";

        // [lv/C]SQLiteDatabase : 기존 user table 삭제
        db.execSQL(UserTableSetting.QUERY_DROP_TABLE_IF_EXISTS);

        // [lv/C]SQLiteDatabase : 기존 event table 삭제
        db.execSQL(EventTableSetting.QUERY_DROP_TABLE_IF_EXISTS);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** 기존 user, event table 삭제 완료하였습니다. ***");

        // [method] : 다시 테이블 생성
        onCreate(db);

    } // End of method [onUpgrade]


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);

        final String METHOD_NAME = "[onDowngrade] ";

        // [lv/C]SQLiteDatabase : 기존 user table 삭제
        db.execSQL(UserTableSetting.QUERY_DROP_TABLE_IF_EXISTS);

        // [lv/C]SQLiteDatabase : 기존 event table 삭제
        db.execSQL(EventTableSetting.QUERY_DROP_TABLE_IF_EXISTS);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** 기존 user, event table 삭제 완료하였습니다. ***");

        // [method] : 다시 테이블 생성
        onCreate(db);

    } // End of method [onDowngrade]

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        final String METHOD_NAME = "[onOpen] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "데이터베이스에 user, event table 을 사용할 준비가 되었습니다. ***");
    }
}
