package com.transfar.smarttda.bean;

import java.util.List;

/**
 * Created by wulei
 * Data: 2016/11/21.
 *app启动后每分钟上报一次
 * 表示网络与UI监控的指标
 */

public class NetAndUiBean {

    /**
     * dataId :标示每条记录的id，客户端生成md5(uuid+当前时间戳)
     * partyId :会员id
     * interval : 60   发送时间间隔
     * dev : {"networkType":"","operator":"","uuid":"","longitude":"","latitude":"","ip":""}
     * data : [{"type":"networkPerfMetrics","content":{"actions":[{"name":"","startTime":"","endTime":"","protocolType":"","requestType":"","contentLength":"","code":""}],"errs":[{"name":"","startTime":"","des":""}]}},{"type":"uiPerfMetrics","content":{"actions":[{"name":"","startTime":"","endTime":""}]}}]
     */

    private String dataId;
    private String partyId;
    private String appKey;
    private String interval;
    private DevBean dev;
    private List<DataBean> data;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    /**
     * 获取appkey
     * @return
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置appkey
     * @param appKey
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public DevBean getDev() {
        return dev;
    }

    public void setDev(DevBean dev) {
        this.dev = dev;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DevBean {
        /**
         * networkType :网络类型
         * operator :运营商信息
         * uuid :设备唯一标示
         * longitude :经度
         * latitude :纬度
         * ip :ip
         */

        private String networkType;
        private String operator;
        private String uuid;
        private String longitude;
        private String latitude;
        private String ip;

        public String getNetworkType() {
            return networkType;
        }

        public void setNetworkType(String networkType) {
            this.networkType = networkType;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }

    public static class DataBean {
        /**
         * type : networkPerfMetrics  监控类型
         * content : {"actions":[{"name":"","startTime":"","endTime":"","protocolType":"","requestType":"","contentLength":"","code":""}],"errs":[{"name":"","startTime":"","des":""}]}
         */

        private String type;
        private ContentBean content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public static class ContentBean {
            private List<ActionsBean> actions;
            private List<ErrsBean> errs;

            public List<ActionsBean> getActions() {
                return actions;
            }

            public void setActions(List<ActionsBean> actions) {
                this.actions = actions;
            }

            public List<ErrsBean> getErrs() {
                return errs;
            }

            public void setErrs(List<ErrsBean> errs) {
                this.errs = errs;
            }

            public static class ActionsBean {
                /**
                 * name : URL 或界面名称
                 * startTime :发起请求时间 或开始时间
                 * endTime :响应时间 或结束时间
                 * protocolType :协议类型
                 * requestType :请求方式
                 * contentLength :响应数据大小
                 * code :状态码
                 */

                private String name;
                private String startTime;
                private String endTime;
                private String protocolType;
                private String requestType;
                private String contentLength;
                private String code;

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

                @Override
                public String toString() {
                    return "\n" + "ActionsBean{" +
                            "name='" + name + '\'' +
                            ", startTime='" + startTime + '\'' +
                            ", endTime='" + endTime + '\'' +
                            ", protocolType='" + protocolType + '\'' +
                            ", requestType='" + requestType + '\'' +
                            ", contentLength='" + contentLength + '\'' +
                            ", code='" + code + '\'' +
                            '}';
                }
            }

            public static class ErrsBean {
                /**
                 * name :URL 或界面名称
                 * startTime :发起请求时间 或开始时间
                 * des :错误堆栈信息
                 */

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

                @Override
                public String toString() {
                    return "\n" + "ErrsBean{" +
                            "name='" + name + '\'' +
                            ", startTime='" + startTime + '\'' +
                            ", des='" + des + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return  "\n" + "ContentBean{" +
                        "actions=" + actions +
                        ", errs=" + errs +
                        '}';
            }
        }

        @Override
        public String toString() {
            return  "\n" + "DataBean{" +
                    "type='" + type + '\'' +
                    ", content=" + content +
                    '}';
        }
    }

    @Override
    public String toString() {
        return  "\n" + "NetAndUiBean{" +
                "dataId='" + dataId + '\'' +
                ", partyId='" + partyId + '\'' +
                ", interval='" + interval + '\'' +
                ", dev=" + dev +
                ", data=" + data +
                '}';
    }
}
