package com.transfar.smarttda.bean;

/**
 * Title: UploadResponse <br>
 * Description: 上传返回的gson<br>
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/11/21 18:12
 * Created by Wentao.Shi.
 */
public class UploadResponse {

    /**
     * result : success
     * data :
     * code : 0
     * msg : 上传成功
     */

    private String result;
    private String data;
    private String code;
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
