package com.transfar.smarttda.bean;

/**
 * Created by wulei
 * Data: 2016/11/9.
 */

public class AuthResponse {

    /**
     * result : success
     * data : {"token":"20110f204b25692a57ff125f4d939d0b","uuid":"0521de2449dede4f50ab703a24073b6e"}
     * code : 0
     * msg : 鉴权成功
     */

    private String result;
    private DataBean data;
    private String code;
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * token : 20110f204b25692a57ff125f4d939d0b
         * startInterval: 30
         * uuid : 0521de2449dede4f50ab703a24073b6e
         */
        private String token;
        private String startInterval;
        private String uuid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getStartInterval() {
            return startInterval;
        }

        public void setStartInterval(String startInterval) {
            this.startInterval = startInterval;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
