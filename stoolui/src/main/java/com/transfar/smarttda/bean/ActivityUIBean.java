package com.transfar.smarttda.bean;

import android.text.TextUtils;

/**
 * Created by wulei
 * Data: 2017/1/9.
 */

public class ActivityUIBean implements Comparable<ActivityUIBean>{
    private String name;

    private String onCreateTime;

    private String onResumeTime;

    /**
     * 从onCreate到onResume的加载时间
     */
    private String showUiPeriod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnCreateTime() {
        return onCreateTime;
    }

    public void setOnCreateTime(String onCreateTime) {
        this.onCreateTime = onCreateTime;
    }

    public String getOnResumeTime() {
        return onResumeTime;
    }

    public void setOnResumeTime(String onResumeTime) {
        this.onResumeTime = onResumeTime;
    }

    public String getShowUiPeriod() {
        return showUiPeriod;
    }

    public void setShowUiPeriod(String showUiPeriod) {
        this.showUiPeriod = showUiPeriod;
    }

    @Override
    public String toString() {
        return "ActivityUIBean{" +
                "name='" + name + '\'' +
                ", onCreateTime='" + onCreateTime + '\'' +
                ", onResumeTime='" + onResumeTime + '\'' +
                ", showUiPeriod='" + showUiPeriod + '\'' +
                '}';
    }

    @Override
    public int compareTo(ActivityUIBean o) {
        if (TextUtils.isEmpty(onCreateTime)||o==null||TextUtils.isEmpty(o.onCreateTime))
            return 0;
        return o.getOnCreateTime().compareTo(onCreateTime);
    }
}
