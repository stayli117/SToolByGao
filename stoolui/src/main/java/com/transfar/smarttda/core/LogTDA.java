package com.transfar.smarttda.core;

import android.util.Log;

import com.transfar.smarttda.configuration.Configuration;
import com.transfar.smarttda.tool.SDPath;
import com.transfar.smarttda.localcache.file.impl.FileEngin;
import com.transfar.smarttda.tool.StringLaunch;
import com.transfar.smarttda.tool.StringUtils;
import com.transfar.smarttda.tool.ThreadFactory;

import java.io.File;

/**
 * Created by wulei
 * Data: 2016/11/15.
 * <p>
 * * 日志级别
 * ALL<DEBUG<INFO<WARN<ERROR<FATAL<OFF
 */

public class LogTDA {
    private static FileEngin fileEngin = new FileEngin();
    private static File file = new File(SDPath.TEMP + fileEngin.newFileName());

    /**
     * 记录详细的重要信息
     *
     * @param tag
     * @param message
     */
    public static void debug(String tag, String message) {
        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.d(tag, message);
        }

        printFileLog(Level.DEBUG.name(), tag, message);
    }

    /**
     * 记录简略的重要信息
     *
     * @param tag
     * @param message
     */
    public static void info(String tag, String message) {
        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.i(tag, message);
        }
        printFileLog(Level.INFO.name(), tag, message);
    }

    /**
     * 记录异常数据信息，如无数据，服务器连接超时
     *
     * @param tag
     * @param message
     */
    public static void warn(String tag, String message) {
        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.w(tag, message);
        }

        printFileLog(Level.WARN.name(), tag, message);
    }

    /**
     * 记录严重的异常
     *
     * @param tag
     * @param message
     */
    public static void errer(String tag, String message) {
        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.e(tag, message);
        }

        printFileLog(Level.ERROR.name(), tag, message);
    }

    /**
     * 记录严重的异常,如exception
     * <p>
     * message 为“”可能导致日志丢失
     *
     * @param tag
     * @param message
     */
    public static void errer(String tag, String message, Throwable tr) {
        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.e(tag, message, tr);
        }

        printFileLog(Level.ERROR.name(), tag, message, tr);
    }

    /**
     * 系统崩溃日志
     *
     * @param tag
     * @param message
     */
    public static void fatal(String tag, String message) {
        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.e(tag, message);
        }

        printFileLog(Level.FATAL.name(), tag, message);
    }

    /**
     * 系统崩溃日志
     *
     * @param tag
     * @param message
     * @param tr      崩溃异常堆栈
     */
    public static void fatal(String tag, String message, Throwable tr) {
        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.e(tag, message, tr);
        }

        printFileLog(Level.FATAL.name(), tag, message, tr);
    }

    /**
     * SDK 信息
     *
     * @param tag
     * @param message
     */
    public static void SDKinfo(String tag, String message, Throwable tr) {
        try {
            if (!Configuration.LOG_SDK_TOGGLE) {
                return;
            }

            if (Configuration.LOG_LOGCAT_TOGGLE) {
                Log.e("TDA_" + tag, message, tr);
            }

            printFileLog(Level.SDK.name(), tag, message, tr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SDK 信息
     *
     * @param tag
     * @param message
     */
    public static void SDKinfo(String tag, String message) {
        if (!Configuration.LOG_SDK_TOGGLE) {
            return;
        }

        if (Configuration.LOG_LOGCAT_TOGGLE) {
            Log.i("TDA_" + tag, message);
        }

        printFileLog(Level.SDK.name(), tag, message);
    }

    /**
     * 打印文件日志
     *
     * @param level
     * @param tag
     * @param message
     */
    private static void printFileLog(final String level, final String tag, final String message) {
        if (Configuration.LOG_FILE_TOGGLE) {
            ThreadFactory.executor(new Runnable() {
                @Override
                public void run() {
                    fileEngin.printInstant(StringLaunch.getStringFromInfo(level, tag, StringLaunch.getFormatedString(message, StringLaunch.LOG_WIDTH)), file);
                }
            });
        }
    }

    /**
     * 打印文件日志
     *
     * @param level
     * @param tag
     * @param message
     */
    private static void printFileLog(String level, String tag, String message, Throwable tr) {
        if (Configuration.LOG_FILE_TOGGLE) {
            fileEngin.printInstant(StringLaunch.getStringFromInfo(level, tag,  StringLaunch.getFormatedErrString(StringUtils.getExceptionString(tr))), file);
        }
    }

    private enum Level {
        ALL, DEBUG, INFO, WARN, ERROR, FATAL, OFF, SDK
    }
}
