package com.transfar.smarttda.bean;

/**
 * Created by wulei
 * Data: 2016/11/30.
 *
 * 网络请求错误
 */

public class HttpError {
    //url
    private String name;
    private String startTime;
    private String des;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
