package com.transfar.smarttda.bean;

import java.util.List;

/**
 * Created by wulei
 * Data: 2016/11/21.
 *
 * 奔溃等信息
 */

public class AnrBean {

    /**
     * dataId :标示每条记录的id，客户端生成md5(uuid+当前时间戳)
     * partyId :会员id
     * dev : {"networkType":"","operator":"","uuid":"","longitude":"","latitude":"","ip":""}
     * data : [{"type":"crashPerfMetrics","content":{"errs":[{"name":"","startTime":"","des":""}]}}]
     */

    private String dataId;
    private String appKey;
    private String partyId;
    private DevBean dev;
    private List<DataBean> data;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
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

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
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

        @Override
        public String toString() {
            return "\n" + "DevBean{" +
                    "networkType='" + networkType + '\'' +
                    ", operator='" + operator + '\'' +
                    ", uuid='" + uuid + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", ip='" + ip + '\'' +
                    '}';
        }
    }

    public static class DataBean {
        /**
         * type : crashPerfMetrics监控类型
         * content : {"errs":[{"name":"","startTime":"","des":""}]}
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
            private List<ErrsBean> errs;

            public List<ErrsBean> getErrs() {
                return errs;
            }

            public void setErrs(List<ErrsBean> errs) {
                this.errs = errs;
            }

            public static class ErrsBean {
                /**
                 * name :崩溃类型
                 * startTime : 时间
                 * des :崩溃卡顿等异常信息
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
                return "\n" + "ContentBean{" +
                        "errs=" + errs +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "\n" + "DataBean{" +
                    "type='" + type + '\'' +
                    ", content=" + content +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "\n" + "AnrBean{" +
                "dataId='" + dataId + '\'' +
                ", partyId='" + partyId + '\'' +
                ", dev=" + dev +
                ", data=" + data +
                '}';
    }
}
