package com.transfar.smarttda.bean;

/**
 * Created by wulei
 * Data: 2016/11/21.
 *
 * 设备基本信息
 */

public class DeviceInfoBean {

    /**
     * app : {"appName":"","appVsersion":""}
     * dev : {"deviceName":"","deviceType":"","osType":"","osVersion":"","cusOsType":"","cusOsVersion":"","agentType":"","agentVersion":"","imei":""}
     * longitude :经度
     * latitude :纬度
     * ip :ip信息
     * uuid :设备唯一标示
     * startTime :启动时间
     * endTime :结束时间
     * dataId :标示每条记录的id,客户端生成，md5(uuid+当前时间戳)
     * partyId :会员id
     */

    private AppBean app;
    private DevBean dev;
    private String longitude;
    private String latitude;
    private String ip;
    private String uuid;
    private String startTime;
    private String endTime;
    private String dataId;
    private String partyId;

    /**
     * 设置app信息
     * @return      app信息
     */
    public AppBean getApp() {
        return app;
    }

    /**
     * 设置app信息
     * @param app   app信息
     */
    public void setApp(AppBean app) {
        this.app = app;
    }

    public DevBean getDev() {
        return dev;
    }

    public void setDev(DevBean dev) {
        this.dev = dev;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
    public static class AppBean {
        /**
         * appName :app名称
         * appVersion :app版本
         * appKey:appKey
         */

        private String appName;
        private String appVersion;
        private String appKey;

        /**
         * 获取app名称
         * @return          app名称
         */
        public String getAppName() {
            return appName;
        }

        /**
         * 设置app名称
         * @param appName   app名称
         */
        public void setAppName(String appName) {
            this.appName = appName;
        }

        /**
         * 获取app版本
         * @return               app版本
         */
        public String getAppVersion() {
            return appVersion;
        }

        /**
         * 设置app版本
         * @param appVersion
         */
        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
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
    }

    public static class DevBean {
        /**
         * deviceName :设备名称
         * deviceType :设备型号
         * osType :OS类型
         * osVersion :OS版本
         * cusOsType :定制OS类型
         * cusOsVersion :定制OS版本
         * agentType :agent类型
         * agentVersion :版本
         * imei :imei号
         */

        private String deviceName;
        private String deviceType;
        private String osType;
        private String osVersion;
        private String cusOsType;
        private String cusOsVersion;
        private String agentType;
        private String agentVersion;
        private String imei;

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getOsType() {
            return osType;
        }

        public void setOsType(String osType) {
            this.osType = osType;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public String getCusOsType() {
            return cusOsType;
        }

        public void setCusOsType(String cusOsType) {
            this.cusOsType = cusOsType;
        }

        public String getCusOsVersion() {
            return cusOsVersion;
        }

        public void setCusOsVersion(String cusOsVersion) {
            this.cusOsVersion = cusOsVersion;
        }

        public String getAgentType() {
            return agentType;
        }

        public void setAgentType(String agentType) {
            this.agentType = agentType;
        }

        public String getAgentVersion() {
            return agentVersion;
        }

        public void setAgentVersion(String agentVersion) {
            this.agentVersion = agentVersion;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        @Override
        public String toString() {
            return  "\n" + "DevBean{" +
                    "deviceName='" + deviceName + '\'' +
                    ", deviceType='" + deviceType + '\'' +
                    ", osType='" + osType + '\'' +
                    ", osVersion='" + osVersion + '\'' +
                    ", cusOsType='" + cusOsType + '\'' +
                    ", cusOsVersion='" + cusOsVersion + '\'' +
                    ", agentType='" + agentType + '\'' +
                    ", agentVersion='" + agentVersion + '\'' +
                    ", imei='" + imei + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "\n" + "DeviceInfoBean{" +
                "app=" + app +
                ", dev=" + dev +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", ip='" + ip + '\'' +
                ", uuid='" + uuid + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", dataId='" + dataId + '\'' +
                ", partyId='" + partyId + '\'' +
                '}';
    }
}
