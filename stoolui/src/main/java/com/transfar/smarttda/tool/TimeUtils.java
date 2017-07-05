package com.transfar.smarttda.tool;

import com.transfar.smarttda.core.LogTDA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wulei
 * Data: 2016/11/14.
 */

public class TimeUtils {
    /**
     * 获取当前的时间
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static String getCurrentTimeStr() {
        SimpleDateFormat f = new SimpleDateFormat(ThreadLocalDateUtil.DATE_FORMAT);
        return f.format(getCurrentTime());
    }

    /**
     * 获取当前的时间字符串
     *
     * @return
     */
    public static String getThreadLocalCurrentTimeStr() {
        String returnStr = null;
        Date date = new Date(getCurrentTime());
        try {
            returnStr = ThreadLocalDateUtil.formatDate(date);
        } catch (ParseException e) {
            LogTDA.SDKinfo("TimeUtils", "ParseException", e);
        }
        return returnStr;
    }

    /**
     * 任意时间字符串
     *
     * @return
     */
    public static String getTimeStr(long time) {
        String returnStr = null;
        if (time == 0)
            return "";
        try {
            returnStr = ThreadLocalDateUtil.formatDate(time);
        } catch (ParseException e) {
            LogTDA.SDKinfo("TimeUtils", "ParseException", e);
        }
        return returnStr;
    }

    /**
     * 任意时间的long类型
     *
     * @return
     */
    public static long convertStrToLong(String time) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(ThreadLocalDateUtil.DATE_FORMAT);
            Date date = f.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            LogTDA.SDKinfo("TimeUtils", "ParseException：", e);
        }
        return 0;
    }

    /**
     * 获取时间间隔
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getTimePeriod(long startTime, long endTime) {
        return endTime - startTime;
    }

    /**
     * 获取时间字符串的时间间隔
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getTimePeriod(String startTime, String endTime) {
        return convertStrToLong(endTime) - convertStrToLong(startTime);
    }

}
