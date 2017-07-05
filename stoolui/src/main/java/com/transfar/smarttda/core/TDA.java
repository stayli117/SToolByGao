package com.transfar.smarttda.core;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.transfar.smarttda.configuration.Configuration;
import com.transfar.smarttda.warehouse.apperror.AppErrorData;
import com.transfar.smarttda.tool.StringLaunch;
import com.transfar.smarttda.tool.ThreadFactory;

import static com.transfar.smarttda.tool.StringLaunch.printWelcome;

/**
 * Created by wulei
 * Data: 2016/11/8.
 * <p>
 * 统一入口
 */

public class TDA {
    private static Context context;
    private Handler mhandler;

    private TDA() {

    }

    private static class TDACoreInstance {
        public static final TDA INSTANCE = new TDA();
    }

    public static TDA getInstance() {
        return TDACoreInstance.INSTANCE;
    }

    public void init(Context c) {
        Log.i("TDA_SDK"," Main Thread name:" + Thread.currentThread().toString());
        this.context = c;

        mhandler = new Handler(new TDAHander());
        Thread.setDefaultUncaughtExceptionHandler(new AppErrorData.AppUncaughtExceptionHandler());

        ThreadFactory.executor(new Runnable() {
            @Override
            public void run() {
                printWelcome(StringLaunch.LOG_WIDTH, context);

                //初始化模块
                DataAgent.getInstance().init();
            }
        });
    }

    /**
     * 调试模式默认打印SDK日志 & 控制台日志
     * @param c
     * @param isDebug
     */
    public void init(Context c, boolean isDebug) {
        Configuration.LOG_SDK_TOGGLE = isDebug;
        Configuration.LOG_LOGCAT_TOGGLE = isDebug;

        init(c);
    }

    public Context getContext() {
        return context;
    }

    /**
     * 会员名称
     * @param name
     */
    public void setPartId(String name){
        Configuration.PART_NAME = name;
    }

    public Handler getHandler(){
        return mhandler;
    }
}
