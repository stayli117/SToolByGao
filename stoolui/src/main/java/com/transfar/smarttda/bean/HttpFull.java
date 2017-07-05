package com.transfar.smarttda.bean;

import android.text.TextUtils;

import okhttp3.Request;

/**
 * Created by wulei
 * Data: 2017/1/3.
 */

public class HttpFull implements Comparable<HttpFull>,Cloneable{
    private String name;
    private String startTime;
    private String endTime;
    private String protocolType;
    private String requestType;
    private String contentLength;
    //响应码
    private String code;
    //响应内容
    private String content;
    //错误信息描述
    private String errDes;
    //完整的请求信息，包括头文件信息
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErrDes() {
        return errDes;
    }

    public void setErrDes(String errDes) {
        this.errDes = errDes;
    }

    @Override
    public int compareTo(HttpFull o) {
        if (TextUtils.isEmpty(startTime)||o==null||TextUtils.isEmpty(o.startTime))
            return 0;
        return o.getStartTime().compareTo(startTime);
    }

    @Override
    public Object clone() {
        HttpFull newHttpFull=new HttpFull();
        newHttpFull.setName(name);
        newHttpFull.setStartTime(startTime);
        newHttpFull.setEndTime(endTime);
        newHttpFull.setProtocolType(protocolType);
        newHttpFull.setRequestType(requestType);
        newHttpFull.setRequest(request);
        newHttpFull.setCode(code);
        newHttpFull.setContent(content);
        newHttpFull.setContentLength(contentLength);
        newHttpFull.setErrDes(errDes);
        return newHttpFull;
    }
}
