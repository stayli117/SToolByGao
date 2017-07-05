package com.transfar.smarttda.bean;

import com.transfar.smarttda.tool.TimeUtils;

/**
 * Created by wulei
 * Data: 2016/11/14.
 */

public class UIDataBean {
    private String name;
    private long startTime;
    private long endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object obj) {
        UIDataBean uiDataBean = (UIDataBean) obj;
        if (name.equals(uiDataBean.getName()) && startTime == uiDataBean.getStartTime()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return  "\n" + "UIDataBean{" +
                "name='" + name + '\'' +
                ", startTime=" + TimeUtils.getTimeStr(startTime) +
                ", endTime=" + TimeUtils.getTimeStr(endTime) +
                '}';
    }
}
