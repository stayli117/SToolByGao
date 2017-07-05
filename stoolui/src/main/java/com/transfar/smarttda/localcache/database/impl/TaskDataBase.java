package com.transfar.smarttda.localcache.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.transfar.smarttda.bean.TaskDB;
import com.transfar.smarttda.core.LogTDA;
import com.transfar.smarttda.warehouse.idata.ITaskDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stayli on 2017/3/30.
 */

public class TaskDataBase implements ITaskDatabase {
    private TaskDataBaseHelper mDBOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public TaskDataBase(Context context) {
        //创建数据库
        mDBOpenHelper = new TaskDataBaseHelper(context, null, 1);
        // 以可写入的方式获取数据库
        mSqLiteDatabase = mDBOpenHelper.getWritableDatabase();
    }

    /**
     * 插入一个缓存任务
     *
     * @param taskDB
     * @return
     */
    @Override
    public boolean insertTask(TaskDB taskDB) {
        long result = -1;
        // 创建ContentValues并写入数据
        // 开启事务
        mSqLiteDatabase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("type", taskDB.getType());
            values.put("content", taskDB.getContent());
            values.put("url", taskDB.getUrl());
            // 插入一条数据
            result = mSqLiteDatabase.insert("task_db", null, values);
            // 清空ContentValues
            values.clear();
            // 操作成功
            mSqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            LogTDA.SDKinfo("TaskDataBase", "database insert wrong：", e);
        } finally {
            //结束事务
            mSqLiteDatabase.endTransaction();
        }
        if (result > 0)
            return true;
        else
            return false;
    }

    /**
     * 删除一个缓存任务
     *
     * @param id 任务ID
     * @return
     */
    @Override
    public boolean deleteTask(int id) {
        int result = 0;
        // 开启事务
        mSqLiteDatabase.beginTransaction();
        try {
            result = mSqLiteDatabase.delete("task_db", "id = ?", new String[]{String.valueOf(id)});
            // 操作成功
            mSqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            LogTDA.SDKinfo("TaskDataBase", "database task delete wrong：", e);
        } finally {
            //结束事务
            mSqLiteDatabase.endTransaction();
        }
        if (result > 0)
            return true;
        else
            return false;
    }

    /**
     * 清空所有的缓存任务
     *
     * @return
     */
    @Override
    public boolean deleteTasks() {
        int result = -1;
        // 开启事务
        mSqLiteDatabase.beginTransaction();
        try {
            result = mSqLiteDatabase.delete("task_db", "id > ?", new String[]{String.valueOf(0)});
            // 操作成功y
            mSqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            LogTDA.SDKinfo("TaskDataBase", "database all task delete wrong：", e);
        } finally {
            //结束事务
            mSqLiteDatabase.endTransaction();
        }
        if (result > -1)
            return true;
        else
            return false;
    }

    /**
     * 删除指定类型的任务
     *
     * @param type
     * @return
     */
    @Override
    public boolean deleteTasks(String type) {
        int result = -1;
        // 开启事务
        mSqLiteDatabase.beginTransaction();
        try {
            result = mSqLiteDatabase.delete("task_db", "type = ?", new String[]{type});
            // 操作成功
            mSqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            LogTDA.SDKinfo("TaskDataBase", "database custom task delete wrong：", e);
        } finally {
            //结束事务
            mSqLiteDatabase.endTransaction();
        }
        if (result > -1)
            return true;
        else
            return false;
    }

    /**
     * 获取所有的缓存任务
     *
     * @return
     */
    @Override
    public List<TaskDB> getTaskList() {
        List<TaskDB> list = null;
        // 开启事务
        mSqLiteDatabase.beginTransaction();
        try {
            list = new ArrayList<>();
            // 查询表中所有数据
            Cursor cursor = mSqLiteDatabase.query("task_db", null, null, null, null, null, null);
            // 遍历Cursor对象，取出数据。
            while (cursor.moveToNext()) {
                TaskDB taskdb = new TaskDB();
                taskdb.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                taskdb.setType(cursor.getString(cursor.getColumnIndex("type")));
                taskdb.setContent(cursor.getString(cursor.getColumnIndex("content")));
                taskdb.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                list.add(taskdb);
            }
            // 记得关闭资源
            cursor.close();
            // 操作成功
            mSqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            LogTDA.SDKinfo("TaskDataBase", "database get all task wrong：", e);
        } finally {
            //结束事务
            mSqLiteDatabase.endTransaction();
        }
        return list;
    }

    /**
     * 获取所有的指定类型的任务缓存任务
     *
     * @return
     */
    @Override
    public List<TaskDB> getTaskListByType(String type) {
        List<TaskDB> list = null;
        // 开启事务
        mSqLiteDatabase.beginTransaction();
        try {
            list = new ArrayList<>();
            // 查询表中所有数据
            Cursor cursor = mSqLiteDatabase.query("task_db", null, "type = ?", new String[]{type}, null, null, null);
            // 遍历Cursor对象，取出数据。
            while (cursor.moveToNext()) {
                TaskDB taskdb = new TaskDB();
                taskdb.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                taskdb.setType(cursor.getString(cursor.getColumnIndex("type")));
                taskdb.setContent(cursor.getString(cursor.getColumnIndex("content")));
                taskdb.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                list.add(taskdb);
            }
            // 记得关闭资源
            cursor.close();
            // 操作成功
            mSqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            LogTDA.SDKinfo("TaskDataBase", "database get custom type task list wrong：", e);
        } finally {
            //结束事务
            mSqLiteDatabase.endTransaction();
        }

        return list;
    }
}
