package net.people.stool;

import android.app.Application;

import net.people.stoolui.core.SmartToolCore;


/**
 * Title: MyApplication <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2017 <br>
 * Created DateTime: 2017-1-13 12:19
 * Created by Wentao.Shi.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmartToolCore.getInstance().init(this);
    }

}
