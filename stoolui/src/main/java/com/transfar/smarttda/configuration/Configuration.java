package com.transfar.smarttda.configuration;

/**
 * Created by stayli on 2017/3/30.
 */

public class Configuration {
    /**
     * SDK 任务延时启动
     *
     * 默认 5s
     */
    public static long SDK_START_DELAY = 5 * 1000;

    /**
     * Activity在后台运行阈值，超过重新计算PV
     *
     * 默认 30s
     */
    public static long ACTIVITY_BACKGROUND_TIME = 30 * 1000;

    /**
     * 定时任务：鉴权，启动
     *
     * 默认 60s
     */
    public static long AUTH_TIMER = 60 * 1000;

    /**
     * app在后台运行阈值，超过阈值，算app关闭
     *
     * 默认 20min
     */
    public static long APP_BACKGROUND_END = 20 * 60 * 1000;

    /**
     * 定时任务后台检查app是否结束
     *
     * 默认 10min
     */
    public static long ACTIVITY_END_TIMER = 10 * 60 * 1000;

    /**
     * 定时任务 PV 和 网络信息上传的时间间隔
     *
     * 默认 60s
     */
    public static long PV_AND_NET_TIMER = 60 * 1000;

    /**
     * 本地缓存任务，重新上传时间间隔
     *
     * 默认 2min
     */
    public static long RE_UPLOAD_CACHE_TASK_PERIOD = 2 * 60 * 1000;

    /**
     * 是否打印SDK内部调试日志
     */
    public static boolean LOG_SDK_TOGGLE = false;

    /**
     * 是否打印文件日志
     */
    public static boolean LOG_FILE_TOGGLE = true;

    /**
     * 是否打印控制台日志
     */
    public static boolean LOG_LOGCAT_TOGGLE = false;

    /**
     * appkey
     */
    public static String APP_KEY = "";

    /**
     * secretKey
     */
    public static String APP_SECRETKEY= "";

    /**
     * 正式服务器地址
     */
    public static String RELEASE_URL = "";

    /**
     * 测试服务地址
     */
    public static String DEBUG_URL = "http://sitetest.tf56.com/logCollectionApi/";

    /**
     * 会员名称
     */
    public static String PART_NAME = "";

}
