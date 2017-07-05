package com.transfar.smarttda.warehouse.idata;

import com.transfar.smarttda.bean.ActivityUIBean;
import com.transfar.smarttda.bean.UIDataBean;

import java.util.List;

/**
 * Created by stayli on 2017/3/30.
 */

public interface IPerformance {
    /**
     * 获取路径列表
     */
    List<UIDataBean> getPathList();

    /**
     * 清空路径列表
     */
    void clearPathList();

    /**
     * 启动时间
     * @return
     */
    long getStartTime();

    /**
     * 结束时间
     * @return
     */
    long getEndTime();
    void setEndTime(long time);

    /**
     * 判断app是否在后台运行
     * @return
     */
    boolean isAppBackground();

    /**
     * 判断app是否结束
     * @return
     */
    boolean isAppEnd();
    void setAppEnd(boolean appEnd);

    /**
     * 获取Activity生命周期的加载时间
     * @return
     */
    List<ActivityUIBean> getActivityUIBeanList();
}
