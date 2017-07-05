package com.transfar.smarttda.bean;

/**
 * Created by wulei
 * Data: 2017/1/4.
 */

public class ExceptionBean {
    private String time;
    private String simpleMessage;
    private String detailMessage;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSimpleMessage() {
        return simpleMessage;
    }

    public void setSimpleMessage(String simpleMessage) {
        this.simpleMessage = simpleMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
