package com.transfar.smarttda.tool;

import com.transfar.smarttda.core.TDA;

/**
 * Created by wulei
 * Data: 2016/11/8.
 * <p>
 * 记录所有本地SD卡目录
 */

public class SDPath {
    /**
     * SD卡目录
     */
//        public final static String SD = Environment.getExternalStorageDirectory().getPath();
    public final static String SD = TDA.getInstance().getContext().getExternalFilesDir("Apeople").getPath();


    /**
     * 缓存地址目录
     */
    public final static String TEMP = SD + "/TransfarDataAnalyze/";

}
