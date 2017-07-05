package com.transfar.smarttda.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Title: LogBean <br>
 * Description: 存储log信息数据的bean<br>
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/11/9 16:22
 * Created by Wentao.Shi.
 */
public class LogBean {
    String logTime;
    String logLevel;
    String logTag;
    String logMsg;

    public LogBean(String logLevel, String logTag, String logMsg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time=sdf.format(new Date());
        this.logTime=time;
        this.logLevel = logLevel;
        this.logTag = logTag;
        this.logMsg = logMsg;
    }

    public LogBean(String logTime, String logLevel, String logTag, String logMsg) {
        this.logTime = logTime;
        this.logLevel = logLevel;
        this.logTag = logTag;
        this.logMsg = logMsg;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogTag() {
        return logTag;
    }

    public void setLogTag(String logTag) {
        this.logTag = logTag;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    @Override
    public String toString() {
        return "["+this.logTime+"]["+this.logLevel+"]["+this.logTag+"]["+this.logMsg+"]";
    }
}
