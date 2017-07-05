package net.people.stoolui.modules.exception;

import java.io.Serializable;

/**
 * Title: ExceptionBean$ <br> Description: <br> Copyright (c) 传化物流版权所有 2016 <br> Created DateTime:
 * 2017/1/4$ 14:16$ <br> Created by  nongwenxue.
 */
public class ExceptionBean implements Serializable{
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