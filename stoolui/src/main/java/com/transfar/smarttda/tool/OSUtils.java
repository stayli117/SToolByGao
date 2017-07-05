package com.transfar.smarttda.tool;

import android.text.TextUtils;

/**
 * Title: OSUtils <br>
 * Description: 判断系统是不是miui，flyme，emui,ColorOs<br>
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/11/10 10:31
 * Created by Wentao.Shi.
 */
public class OSUtils {
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.build.version.incremental";
    private static final String KEY_COLOROS_VERSION_CODE="ro.build.version.opporom";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
    private static final String KEY_FLYME_VERSION_CODE = "ro.build.mask.id";
    private static final String KEY_EUI_VERSION_CODE = "ro.letv.release.version";
    private static final String KEY_FUNTOUCH_VERSION_NAME = "ro.vivo.os.build.display.id";

    /**
     * 遍历BuildProperties文件的所有键值对
     * @param keys
     * @return
     */
    private static boolean isPropertiesExist(BuildProperties properties,String... keys) {
        if (properties!=null){
            for (String key : keys) {
                String str = properties.getProperty(key);
                if (str == null)
                    return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为EMUI
     * @return
     */
    public static boolean isEMUI(BuildProperties properties) {
        return isPropertiesExist(properties,KEY_EMUI_VERSION_CODE);
    }

    /**
     * 获取EMUI版本号
     * @return
     */
    public static String getEMUIVersion(BuildProperties properties){
        String version="";
        if (properties!=null){
            version=properties.getProperty(KEY_EMUI_VERSION_CODE,"");
            if (version.contains("_"))
                version=version.substring(version.indexOf("_")+1,version.length());
        }
        return version;
    }

    /**
     * 判断是否为FunTouchOS
     * @return
     */
    public static boolean isFunTouchOS(BuildProperties properties) {
        return isPropertiesExist(properties,KEY_FUNTOUCH_VERSION_NAME);
    }

    /**
     * 获取FunTouchOS版本号
     * @return
     */
    public static String getFunTouchOSVersion(BuildProperties properties){
        String version="";
        if (properties!=null){
            version=properties.getProperty(KEY_FUNTOUCH_VERSION_NAME,"");
            if (version.contains("_"))
                version=version.substring(version.indexOf("_")+1,version.length());
        }
        return version;
    }

    /**
     * 判断是否为MIUI
     * @return
     */
    public static boolean isMIUI(BuildProperties properties) {
        return isPropertiesExist(properties,KEY_MIUI_VERSION_NAME);
    }

    /**
     * 获取MIUI版本号
     * @return
     */
    public static String getMIUIVersion(BuildProperties properties){
        String version="";
        if (properties!=null){
            version=properties.getProperty(KEY_MIUI_VERSION_CODE,"");
        }
        return version;
    }

    /**
     * 判断是否为乐视EUI
     * @return
     */
    public static boolean isEUI(BuildProperties properties) {
        return isPropertiesExist(properties,KEY_EUI_VERSION_CODE);
    }

    /**
     * 获取EUI版本号
     * @return
     */
    public static String getEUIVersion(BuildProperties properties){
        String version="";
        if (properties!=null){
            version=properties.getProperty(KEY_EUI_VERSION_CODE,"");
        }
        return version;
    }

    /**
     * 判断是否为Flyme
     * @return
     */
    public static boolean isFlyme(BuildProperties properties) {
        boolean result=false;
        if (properties!=null){
            String str = properties.getProperty(KEY_FLYME_VERSION_NAME);
            if (TextUtils.isEmpty(str)) ;
            else if (str.contains("flyme")||str.toLowerCase().contains("flyme")){
                result=true;
            }
        }
        return result;
    }

    /**
     * 获取Flyme版本号
     * @return
     */
    public static String getFlymeVersion(BuildProperties properties){
        String version="";
        if (properties!=null){
            version=properties.getProperty(KEY_FLYME_VERSION_CODE,"");
        }
        return version;
    }

    /**
     * 判断是否为OPPO的ColorOS
     * @return
     */
    public static boolean isColorOS(BuildProperties properties){
        return isPropertiesExist(properties,KEY_COLOROS_VERSION_CODE);
    }

    /**
     * 获取ColorOS版本号
     * @return
     */
    public static String getColorOSVersion(BuildProperties properties){
        String version="";
        if (properties!=null){
            version=properties.getProperty(KEY_COLOROS_VERSION_CODE,"");
        }
        return version;
    }
}
