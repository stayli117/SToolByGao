package com.transfar.smarttda.warehouse.apperror;

import com.transfar.smarttda.bean.DataType;
import com.transfar.smarttda.bean.ExceptionBean;
import com.transfar.smarttda.bean.TaskDB;
import com.transfar.smarttda.core.DataAgent;
import com.transfar.smarttda.core.LogTDA;
import com.transfar.smarttda.core.TDA;
import com.transfar.smarttda.localcache.database.impl.TaskDataBase;
import com.transfar.smarttda.tool.TimeUtils;
import com.transfar.smarttda.warehouse.idata.IAppError;
import com.transfar.smarttda.warehouse.idata.ITaskDatabase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stayli on 2017/3/30.
 */

public class AppErrorData implements IAppError {
    private static final String TAG = "AppErrorData";
    private static List<ExceptionBean> list = new ArrayList<>();
    private static ITaskDatabase dataBase;
    private static final String DIVISION = "&&";

    public AppErrorData(){
        dataBase = new TaskDataBase(TDA.getInstance().getContext());
        addExceptionListFromDB();
    }

    private void addExceptionListFromDB() {
        List<TaskDB> datas = dataBase.getTaskListByType(DataType.Exceptions.name());
        for(TaskDB taskDB: datas){
            buildException(taskDB);
        }
    }

    private void buildException(TaskDB taskDB) {
        try {
            ExceptionBean bean = new ExceptionBean();
            bean.setTime(taskDB.getUrl());
            String[] contents = taskDB.getContent().split(DIVISION);
            bean.setSimpleMessage(contents[0]);
            bean.setDetailMessage(contents[1]);
            list.add(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ExceptionBean> getExceptionLists() {
        return list;
    }

    @Override
    public void delAllException() {
        dataBase.deleteTasks(DataType.Exceptions.name());
        list.clear();
    }

    /**
     * 统计SDK中未捕获异常
     */
    public static class AppUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, final Throwable ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw, true));
            String errStr = sw.toString();
            LogTDA.SDKinfo(TAG, "TDA UncaughtException", ex);

            //未捕获异常导致应用退出
            DataAgent.getInstance().setAppEnd(true);
            DataAgent.getInstance().setEndTime(TimeUtils.getCurrentTime());

            insertExceptionBean(ex.toString(), errStr);
        }
    }

    private static void insertExceptionBean(String simpleMessage, String detailMessage) {
        TaskDB taskDB = new TaskDB();
        taskDB.setType(DataType.Exceptions.name());
        taskDB.setContent(simpleMessage + DIVISION + detailMessage);
        taskDB.setUrl(TimeUtils.getThreadLocalCurrentTimeStr());
        dataBase.insertTask(taskDB);
    }
}
