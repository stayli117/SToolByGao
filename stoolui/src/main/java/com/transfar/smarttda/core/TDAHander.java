package com.transfar.smarttda.core;

import android.os.Handler;
import android.os.Message;

import com.transfar.smarttda.warehouse.feature.FeatureData;

/**
 * Created by stayli on 2017/3/30.
 */

public class TDAHander implements Handler.Callback {
    public TDAHander() {
    }

    @Override
    public boolean handleMessage(Message msg) {
        int var2 = msg.what;
        if (var2 == FEATURE_DATA_WHAT) {
            FeatureData data = FeatureData.instance;
            FeatureData.agentType = data.getAgentTypeReal();
            FeatureData.agentVersion = data.getAgentVersionReal();
        }

        return false;
    }

    public static int FEATURE_DATA_WHAT = 10000;
}
