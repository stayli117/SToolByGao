package com.transfar.smarttda.tool;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.transfar.smarttda.core.LogTDA;

/**
 * Title: LocationUtils <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/11/25 13:40
 * Created by Wentao.Shi.
 */
public class LocationUtils {
    private static  LocationManager  mLocationManager;

    private LocationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断Gps是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断定位是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLocationEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 获取坐标信息
     * @param context
     * @return
     */
    public static Location getLocation(Context context){
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!isLocationEnabled(context)) {
            LogTDA.SDKinfo("LocationUtils","无法定位，请授予获取位置信息权限");
        }
        if (!isGpsEnabled(context)){
            LogTDA.SDKinfo("LocationUtils","无法定位，请打开GPS");
        }
        String provider = mLocationManager.getBestProvider(getCriteria(), true);
        LogTDA.SDKinfo("LocationUtils","Best Provider:"+provider);
        Location location = mLocationManager.getLastKnownLocation(provider);
        return location;
    }

    /**
     * 设置定位参数
     *
     * @return {@link Criteria}
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }
    public static boolean haveBaiduLocationSDK(){
        try {
            Class c=Class.forName("com.baidu.location.LocationClient");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
