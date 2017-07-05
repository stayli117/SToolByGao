package com.transfar.smarttda.warehouse.feature;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.transfar.smarttda.core.LogTDA;
import com.transfar.smarttda.core.TDA;
import com.transfar.smarttda.core.TDAHander;
import com.transfar.smarttda.tool.BuildProperties;
import com.transfar.smarttda.tool.NetWorkUtils;
import com.transfar.smarttda.tool.OSUtils;
import com.transfar.smarttda.tool.PermissionsCheckerUtils;
import com.transfar.smarttda.warehouse.idata.IFeature;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by stayli on 2017/3/30.
 */

public class FeatureData implements IFeature {
    public static String agentType = "", agentVersion = "", appName = "", appVersion = "", osVersion = "", cusOsType = "", cusOsVersion = "", IMEI = "", operator = "";
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    private static PermissionsCheckerUtils sPermissionsCheckerUtils;
    private static BuildProperties prop;
    public static FeatureData instance;

    public FeatureData() {
        instance = this;

        TDA.getInstance().getHandler().sendEmptyMessage(TDAHander.FEATURE_DATA_WHAT);
//        agentType = getAgentTypeReal();
//        agentVersion = getAgentVersionReal();
        try {
            prop = BuildProperties.newInstance();
        } catch (IOException e) {
            LogTDA.SDKinfo("FeatureData", "Read Build Prop Error", e);
        }
    }

    /**
     * 获取APP名称
     *
     * @return
     */
    @Override
    public String getAppName() {
        if (TextUtils.isEmpty(appName)) {
            PackageManager packageManager = TDA.getInstance().getContext().getPackageManager();
            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = packageManager.getApplicationInfo(TDA.getInstance().getContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                LogTDA.SDKinfo("FeatureData", "Package Name Not Found:", e);
            }
            appName = (String) packageManager.getApplicationLabel(applicationInfo);
        }
        return appName;
    }

    /**
     * 获取APP版本号
     *
     * @return
     */
    @Override
    public String getAppVersion() {
        if (TextUtils.isEmpty(appVersion)) {
            PackageManager packageManager = TDA.getInstance().getContext().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = null;
            try {
                packInfo = packageManager.getPackageInfo(TDA.getInstance().getContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                LogTDA.SDKinfo("FeatureData", "Package Name Not Found:", e);
            }
            appVersion = packInfo.versionName;
        }
        return appVersion;
    }

    /**
     * 获取手机品牌信息
     *
     * @return
     */
    @Override
    public String getPhoneBrand() {
        String result = "";
        if (prop != null) {
            result = prop.getProperty("ro.product.brand", "Unknown Brand");
        }
        return result;
    }

    /**
     * 获取手机名称
     *
     * @return
     */
    @Override
    public String getPhoneName() {
        String result = "";
        if (prop != null) {
            result = prop.getProperty("ro.product.name", "Unknown Name");
        }
        return result;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    @Override
    public String getPhoneModel() {
        String result = "";
        if (prop != null) {
            result = prop.getProperty("ro.product.model", "Unknown Model");
        }
        return result;
    }

    /**
     * 获取操作系统类型
     *
     * @return
     */
    @Override
    public String getOSType() {
        return "Android";
    }

    /**
     * 获取操作系统内核版本
     *
     * @return
     */
    @Override
    public String getOSVersion() {
        if (TextUtils.isEmpty(osVersion)) {
            osVersion = android.os.Build.VERSION.RELEASE;
        }
        return osVersion;
    }

    /**
     * 获取定制化OS类型
     *
     * @return
     */
    @Override
    public String getCusOsType() {
        if (TextUtils.isEmpty(cusOsType)) {
            if (OSUtils.isEMUI(prop))
                cusOsType = "EMUI";
            else if (OSUtils.isColorOS(prop))
                cusOsType = "ColorOS";
            else if (OSUtils.isFlyme(prop))
                cusOsType = "Flyme";
            else if (OSUtils.isFunTouchOS(prop))
                cusOsType = "FunTouchOS";
            else if (OSUtils.isMIUI(prop))
                cusOsType = "MIUI";
            else if (OSUtils.isEUI(prop))
                cusOsType = "EUI";
        }
        return cusOsType;
    }

    /**
     * 获取定制化OS版本号
     *
     * @return
     */
    @Override
    public String getCusOsVersion() {
        if (TextUtils.isEmpty(cusOsVersion)) {
            if (OSUtils.isEMUI(prop))
                cusOsVersion = OSUtils.getEMUIVersion(prop);
            else if (OSUtils.isColorOS(prop)) {
                cusOsVersion = OSUtils.getColorOSVersion(prop);
            } else if (OSUtils.isFlyme(prop))
                cusOsVersion = OSUtils.getFlymeVersion(prop);
            else if (OSUtils.isFunTouchOS(prop))
                cusOsVersion = OSUtils.getFunTouchOSVersion(prop);
            else if (OSUtils.isMIUI(prop))
                cusOsVersion = OSUtils.getMIUIVersion(prop);
            else if (OSUtils.isEUI(prop))
                cusOsVersion = OSUtils.getEUIVersion(prop);
        }

        return cusOsVersion;
    }

    /**
     * 获取代理字符串
     *
     * @return
     */
    @Override
    public String getAgentType() {
        if (TextUtils.isEmpty(agentType)) {
            agentType = getAgentTypeReal();
        }
        return agentType;
    }

    /**
     * 获取代理字符串
     *
     * @return
     */
    public String getAgentTypeReal() {
        try {
            WebView webView = new WebView(TDA.getInstance().getContext());
//            webView.layout(0, 0, 0, 0);
            // 得到WebSettings对象
            WebSettings settings = webView.getSettings();
            // 如果访问的页面中有JavaScript，则WebView必须设置支持JavaScript，否则显示空白页面
//            webView.getSettings().setJavaScriptEnabled(true);
            // 获取到UserAgentString
            String userAgent = settings.getUserAgentString();
            if (userAgent != null)
                return userAgent;
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取代理信息中Chrome版本号
     *
     * @return
     */
    @Override
    public String getAgentVersion() {
        if (TextUtils.isEmpty(agentVersion)) {
            agentVersion = getAgentVersionReal();
        }
        return agentVersion;
    }

    public String getAgentVersionReal() {
        try {
            WebView webView = new WebView(TDA.getInstance().getContext());
//            webView.layout(0, 0, 0, 0);
            // 得到WebSettings对象
            WebSettings settings = webView.getSettings();
//            // 如果访问的页面中有JavaScript，则WebView必须设置支持JavaScript，否则显示空白页面
//            webView.getSettings().setJavaScriptEnabled(true);
            // 获取到UserAgentString
            String userAgent = settings.getUserAgentString();
            if (userAgent != null) {
                Pattern p = Pattern.compile("Chrome.*?\\s");
                Matcher m = p.matcher(userAgent);
                if (m.find())
                    return m.group().substring(7);
                return "";
            } else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取手机IMEI号
     *
     * @return
     */
    @Override
    public String getIMEI() {
        if (TextUtils.isEmpty(IMEI)) {
            sPermissionsCheckerUtils = new PermissionsCheckerUtils(TDA.getInstance().getContext());
            // 缺少权限时, 进入权限配置页面
            if (sPermissionsCheckerUtils.lacksPermissions(PERMISSIONS)) {
                LogTDA.SDKinfo("FeatureData", "未获取手机状态读取权限，无法读取IMEI");
            } else {
                TelephonyManager tm = (TelephonyManager) TDA.getInstance().getContext().getSystemService(TELEPHONY_SERVICE);
                IMEI = tm.getDeviceId();
            }
        }
        return IMEI;
    }

    /**
     * 获取IP字符串
     *
     * @return
     */
    @Override
    public String getIP() {
        String ip = "";
        Context context = TDA.getInstance().getContext();
        if (NetWorkUtils.isOnline(context)) {
            if (NetWorkUtils.isWifiConnected(context)) {
                //获取wifi服务
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                ip = NetWorkUtils.intToIp(ipAddress);
            } else {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                ip = inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    LogTDA.SDKinfo("FeatureData", "SocketException", e);
                }
            }
        }
        return ip;
    }


    /**
     * 获取设备ID，即IMEI，与getIMEI方法相同
     *
     * @return
     */
    @Override
    public String getDeviceId() {
        return getIMEI();
    }

    /**
     * 获取网络类型
     *
     * @return
     */
    @Override
    public String getNetworkType() {
        String result = "Unknown";
        int networkType = NetWorkUtils.getNetWorkStatus(TDA.getInstance().getContext());
        switch (networkType) {
            case 1:
                result = "WIFI";
                break;
            case 2:
                result = "2G";
                break;
            case 3:
                result = "3G";
                break;
            case 4:
                result = "4G";
                break;
            default:
                result = "Unknown";
                break;
        }
        return result;
    }

    /**
     * 获取运营商
     *
     * @return
     */
    @Override
    public String getOperator() {
        if (TextUtils.isEmpty(operator)) {
            sPermissionsCheckerUtils = new PermissionsCheckerUtils(TDA.getInstance().getContext());
            // 缺少权限时, 进入权限配置页面
            if (sPermissionsCheckerUtils.lacksPermissions(PERMISSIONS)) {
                LogTDA.SDKinfo("FeatureData", "Did Not Have Authority To Read Phone State,Cant Get Operator");
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) TDA.getInstance().getContext().getSystemService(TELEPHONY_SERVICE);
                operator = telephonyManager.getNetworkOperatorName();
            }
        }
        return operator;
    }
}
