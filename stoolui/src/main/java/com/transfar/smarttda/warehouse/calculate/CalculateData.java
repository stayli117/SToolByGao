package com.transfar.smarttda.warehouse.calculate;

import android.Manifest;
import android.content.Context;
import android.location.Location;

import com.transfar.smarttda.core.LogTDA;
import com.transfar.smarttda.core.TDA;
import com.transfar.smarttda.tool.LocationUtils;
import com.transfar.smarttda.tool.PermissionsCheckerUtils;
import com.transfar.smarttda.warehouse.idata.ICalculate;

/**
 * Created by stayli on 2017/3/30.
 */

public class CalculateData implements ICalculate {
    private static Location mLocation;
    private final Context mContext;
    private static final String[] PERMISSIONS  = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static PermissionsCheckerUtils sPermissionsCheckerUtils;
    public CalculateData() {
        mContext = TDA.getInstance().getContext();
        sPermissionsCheckerUtils=new PermissionsCheckerUtils(mContext);
    }


    @Override
    public String getLongitude() {
        // 缺少权限时, 进入权限配置页面
        if (sPermissionsCheckerUtils.lacksPermissions(PERMISSIONS)) {
            LogTDA.SDKinfo("CalculateData","无法定位，请授予定位权限");
        }else {
            mLocation= LocationUtils.getLocation(mContext);
        }
        if (mLocation!= null)
            return String.valueOf(mLocation.getLongitude());
        else
            return "";
    }

    @Override
    public String getLatitude() {
        // 缺少权限时, 进入权限配置页面
        if (sPermissionsCheckerUtils.lacksPermissions(PERMISSIONS)) {
            LogTDA.SDKinfo("CalculateData","无法定位，请授予定位权限");
        }else {
            mLocation=LocationUtils.getLocation(mContext);
        }
        if (mLocation!= null)
            return String.valueOf(mLocation.getLatitude());
        else
            return "";
    }
}
