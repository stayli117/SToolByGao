package com.transfar.smarttda.warehouse.idata;

/**
 * Created by stayli on 2017/3/30.
 */

public interface IFeature {
    //app名称
    String getAppName();

    //app版本
    String getAppVersion();

    //获取手机品牌
    String getPhoneBrand();

    //获取手机名称
    String getPhoneName();

    //获取手机型号
    String getPhoneModel();

    //OS类型
    String getOSType();

    //OS版本
    String getOSVersion();
    //定制OS类型
    String getCusOsType();

    //定制OS版本
    String getCusOsVersion();

    //agent类型
    String getAgentType();

    //agent版本
    String getAgentVersion();

    //IMEI
    String getIMEI();

    //IP
    String getIP();

    //设备ID
    String getDeviceId();

    //网络类型
    String getNetworkType();

    //运营商信息
    String getOperator();
}
