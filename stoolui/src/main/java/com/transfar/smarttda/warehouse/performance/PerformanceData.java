package com.transfar.smarttda.warehouse.performance;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import com.transfar.smarttda.bean.ActivityUIBean;
import com.transfar.smarttda.bean.UIDataBean;
import com.transfar.smarttda.configuration.Configuration;
import com.transfar.smarttda.core.LogTDA;
import com.transfar.smarttda.core.TDA;
import com.transfar.smarttda.tool.TimeUtils;
import com.transfar.smarttda.warehouse.idata.IPerformance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by stayli on 2017/3/30.
 */

public class PerformanceData implements IPerformance {
    private static final String TAG = "PerformanceData";
    private long appStartTime;
    private long appEndTime;
    private static final String PackageName = "com.transfar.smarttoolui";

    /**
     * app未捕获异常退出
     */
    private boolean isAppEnd = false;
    /**
     * 前台运行activity数量判断app是否在前台
     */
    private int runAcitivityCount;
    /**
     * 路径集合
     */
    Map<Activity, UIDataBean> maps;
    /**
     * 规避重复路径问题
     */
    List<UIDataBean> list;
    /**
     * 结果路径
     */
    List<UIDataBean> resultList;
    /**
     * activity生命周期相关
     */
    List<ActivityUIBean> activityUIList;

    Activity currentActivity;

    ActivityUIBean recentlyActivitUIBean;

    public PerformanceData() {
        ((Application) TDA.getInstance().getContext()).registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks());
        appStartTime = TimeUtils.getCurrentTime();

        maps = new HashMap<>();
        list = new ArrayList<>();
        resultList = new CopyOnWriteArrayList<>();
        activityUIList = new ArrayList<>();
    }

    @Override
    public synchronized List<UIDataBean> getPathList() {
        resultList.clear();
        resultList.addAll(list);
        UIDataBean curBean = maps.get(currentActivity);
        for (Map.Entry<Activity, UIDataBean> entry : maps.entrySet()) {
            UIDataBean bean = entry.getValue();
            if (bean != curBean){
                resultList.add(bean);
            }
        }
        return resultList;
    }

    /**
     * 路径信息上传成功清理缓存
     */
    @Override
    public void clearPathList() {
        UIDataBean bean = maps.get(currentActivity);
        maps.clear();
        list.clear();
        maps.put(currentActivity, bean);
    }

    @Override
    public long getStartTime() {
        return appStartTime;
    }

    @Override
    public long getEndTime() {
        return appEndTime;
    }

    @Override
    public void setEndTime(long time) {
        appEndTime = time;
    }

    @Override
    public boolean isAppBackground() {
        return runAcitivityCount == 0;
    }

    /**
     * 1.有未捕获的异常信息
     * 2.app在后台运行超过阈值
     *
     * @return
     */
    @Override
    public boolean isAppEnd() {
        if (isAppEnd) {
            return true;
        }

        if (isAppBackground()) {
            long period = TimeUtils.getTimePeriod(appEndTime, TimeUtils.getCurrentTime());
            if (period > Configuration.APP_BACKGROUND_END) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setAppEnd(boolean appEnd) {
        isAppEnd = appEnd;
    }

    @Override
    public List<ActivityUIBean> getActivityUIBeanList() {
        for(ActivityUIBean bean: activityUIList){
            if (TextUtils.isEmpty(bean.getShowUiPeriod()) && !TextUtils.isEmpty(bean.getOnResumeTime())){
                long period = TimeUtils.getTimePeriod(bean.getOnCreateTime(), bean.getOnResumeTime());
                bean.setShowUiPeriod(String.valueOf(period));
            }
        }
        return activityUIList;
    }

    /**
     * 统计PV，页面停留时间
     */
    private class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            currentActivity = activity;
            addUIDataBean(activity);
            addActivitUIBean(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            setActivitUIBeanOnResume(activity);

            currentActivity = activity;
            runAcitivityCount++;

            UIDataBean oldBean = getActivity(activity);
            if (oldBean == null) {
                return;
            }

            long period = TimeUtils.getTimePeriod(oldBean.getStartTime(), TimeUtils.getCurrentTime());
            if (period > Configuration.ACTIVITY_BACKGROUND_TIME) {
                addUIDataBean(activity);
            }

            printLog("Resumed", activity);
            LogTDA.SDKinfo(TAG, "runAcitivityCount:  " + runAcitivityCount);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            runAcitivityCount--;

            UIDataBean oldBean = getActivity(activity);
            if (oldBean == null) {
                return;
            }

            oldBean.setEndTime(TimeUtils.getCurrentTime());

            printLog("Paused", activity);
            LogTDA.SDKinfo(TAG, "runAcitivityCount:  " + runAcitivityCount);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            appEndTime = TimeUtils.getCurrentTime();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            UIDataBean oldBean = getActivity(activity);
            if (oldBean == null) {
                return;
            }

            oldBean.setEndTime(TimeUtils.getCurrentTime());
        }
    }

    /**
     * 设置ActivitUIBean的onResumed时间
     */
    private void setActivitUIBeanOnResume(Activity activity) {
        if(recentlyActivitUIBean == null){
            return;
        }
        String name = activity.getClass().getName();
        if (name.contains(PackageName)){
            return;
        }
        if(TextUtils.isEmpty(recentlyActivitUIBean.getOnResumeTime())){
            recentlyActivitUIBean.setOnResumeTime(TimeUtils.getThreadLocalCurrentTimeStr());
        }
    }

    /**
     * 添加ActivitUIBean
     */
    private void addActivitUIBean(Activity activity) {
        String name = activity.getClass().getName();
        if (name.contains(PackageName)){
            return;
        }
        recentlyActivitUIBean = new ActivityUIBean();
        recentlyActivitUIBean.setName(name);
        recentlyActivitUIBean.setOnCreateTime(TimeUtils.getThreadLocalCurrentTimeStr());
        activityUIList.add(recentlyActivitUIBean);
    }

    private void printLog(String activityLifecycle, Activity activity) {
        LogTDA.SDKinfo(TAG, "---------------------------------Path----------------------------------------------" + "\n");

        LogTDA.SDKinfo(TAG, "Page      : " + activity.getClass().getName());
        LogTDA.SDKinfo(TAG, "Lifecycle : " + activityLifecycle);
        for (UIDataBean bean : getPathList()) {
            if (bean!=null)
                LogTDA.SDKinfo(TAG, "Path      :(name)->" + bean.getName() + " (startTime)->" + TimeUtils.getTimeStr(bean.getStartTime()) + " (endTime)->" + TimeUtils.getTimeStr(bean.getEndTime()));
        }
        LogTDA.SDKinfo(TAG, "\n");
        LogTDA.SDKinfo(TAG, "-----------------------------------------------------------------------------------");
    }

    /**
     * 获取列表中的activity
     *
     * @param activity
     * @return
     */
    private UIDataBean getActivity(Activity activity) {
        return maps.get(activity);
    }

    /**
     * 添加一个轨迹
     *
     * @param activity
     */
    private void addUIDataBean(Activity activity) {
        if (getActivity(activity) != null) {
            list.add(getActivity(activity));
        }
        UIDataBean uiDataBean = new UIDataBean();
        uiDataBean.setName(activity.getClass().getName());
        uiDataBean.setStartTime(TimeUtils.getCurrentTime());
        maps.put(activity, uiDataBean);
    }
}
