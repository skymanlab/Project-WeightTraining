package com.skyman.weighttrainingpro.management.project.database;

import android.content.Context;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;

public class ProjectDbConnector {

    // constant
    private static final String CLASS_NAME = "[PD]_ProjectDbConnector";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ProjectDbOpenHelper projectDbOpenHelper;
    private Context context;
    private boolean isConnectedDB;

    // constructor
    public ProjectDbConnector(Context context) {
        this.projectDbOpenHelper = null;
        this.context = context;
        this.isConnectedDB = false;
    }


    /**
     * [method] [getter] this.projectDbOpenHelper
     */
    public ProjectDbOpenHelper getProjectDbOpenHelper() {
        return this.projectDbOpenHelper;
    }// End of method [getProjectDbOpenHelper]


    /**
     * [method] [getter] this.isInitializedDB
     */
    public boolean isConnectedDB() {
        return this.isConnectedDB;
    } // End of method [isConnectedDB]


    /**
     * [method] SQLiteOpenHelper 를 이용하여 healthManager.db 와 연결하기
     */
    public void connectProjectDB() {

        final String METHOD_NAME = "[connectProjectDB] ";

        // [iv/C]ProjectDbHelper : context 를 받아서 healthManager.db 를 사용하기 위해 open(연결) 하기
        this.projectDbOpenHelper = new ProjectDbOpenHelper(this.context);

        // [iv/b]isInitializedDb : projectDbHelper 가 연결되었음을 알리기 위해 true 로 설정하기
        this.isConnectedDB = true;

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** 연결완료 되었습니다. ***");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. isConnectedDB = " + this.isConnectedDB);


    } // End of method [connectProjectDB]


    /**
     * [method] SQLiteOpenHelper 를 모두 사용하고 종료하기
     */
    public void closeProjectDB() {

        final String METHOD_NAME = "[closeProjectDB] ";

        // [check 1] : database 가 연결되었다. isConnectedDB 가 true 이다.
        if (this.isConnectedDB) {

            // [iv/C]ProjectDbHelper : open 한 database 를 닫기
            this.projectDbOpenHelper.close();
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==> DB 연결을 종료하였습니다. <==");

        } // [check 1]

    } // End of method [closeProjectDB]

}
