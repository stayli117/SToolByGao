package com.transfar.smarttda.localcache.database.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.transfar.smarttda.core.LogTDA;

/**
 * Created by stayli on 2017/3/30.
 */

public class TaskDataBaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private String createTaskDB;
    public static final String DEFAULT_DB_NAME = "tda.db";

    public TaskDataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DEFAULT_DB_NAME, factory, version);
        mContext = context;
    }

    public TaskDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建表
        createTaskDB = "create table task_db(" +
                "id integer primary key autoincrement, " +
                "type text, " +
                "content text," + "url text)";
        sqLiteDatabase.execSQL(createTaskDB);
        // 提示数据库创建完毕
        LogTDA.SDKinfo("TaskDataBase", "database task_db table created");
    }

    /**
     * 数据库升级
     *
     * @param sqLiteDatabase
     * @param i              旧版本
     * @param i1             新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        switch (i) {
//            case 1:
//                // 创建category表
//                sqLiteDatabase.execSQL(createCategory);
//            case 2:
//                // 向book表添加category_id列
//                sqLiteDatabase.execSQL("alter table book add column category_id integer");
//                break;
//        }
    }
}
