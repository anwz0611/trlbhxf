package com.tbsurvey.trlbhxf.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * author:jxj on 2020/11/11 09:12
 * e-mail:592296083@qq.com
 * desc  :
 */
public class DBManager {
    public SQLiteDatabase mDB;
    private static DBManager instance = null;

    public DBManager() {
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * 打开数据库
     */
    public SQLiteDatabase openDB(String dbPath) {
        File Path = new File(dbPath);
        if (Path.exists()) {
            mDB = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            return mDB;
        }
        return null;

    }
}
