package com.transfar.smarttda.tool;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Title: NetWorkUtils <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/11/10 14:08
 * Created by Wentao.Shi.
 */
public class NetWorkUtils {
    /**
     * Unknown network class
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;
    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;
    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;
    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;
    /**
     * 判断手机是否有网络连接
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前是否是WIFI连接状态
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * wifi下获取的int 类型Ip转化为字符串
     * @param i
     * @return
     */
    public static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    /**
     * 判断手机连接的网络类型(2G,3G,4G)”的方法
     * @param context
     * @return
     */
    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * 判断当前手机的网络类型(WIFI还是2,3,4G)(需要用到“判断手机连接的网络类型(2G,3G,4G)”的方法)
     * @param context
     * @return
     */
    public static int getNetWorkStatus(Context context) {
        int netWorkType = NETWORK_CLASS_UNKNOWN;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }
        return netWorkType;
    }
}
