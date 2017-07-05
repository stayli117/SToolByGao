package com.transfar.smarttda.core;

import com.transfar.smarttda.bean.ExceptionBean;
import com.transfar.smarttda.bean.HttpAction;
import com.transfar.smarttda.warehouse.apperror.AppErrorData;
import com.transfar.smarttda.warehouse.feature.FeatureData;
import com.transfar.smarttda.warehouse.idata.IFeature;
import com.transfar.smarttda.warehouse.idata.IPerformance;
import com.transfar.smarttda.bean.ActivityUIBean;
import com.transfar.smarttda.bean.HttpError;
import com.transfar.smarttda.bean.HttpFull;
import com.transfar.smarttda.bean.UIDataBean;
import com.transfar.smarttda.warehouse.calculate.CalculateData;
import com.transfar.smarttda.warehouse.idata.IAppError;
import com.transfar.smarttda.warehouse.idata.ICalculate;
import com.transfar.smarttda.warehouse.idata.IHttpData;
import com.transfar.smarttda.warehouse.performance.HttpData;
import com.transfar.smarttda.warehouse.performance.PerformanceData;

import java.util.List;

/**
 * Created by wulei
 * Data: 2016/11/9.
 *
 * 获取数据信息统一入口
 */

public class DataAgent implements IFeature,IPerformance,ICalculate,IAppError,IHttpData{
    private IFeature featureData;
    private ICalculate calculateData;
    private IPerformance performanceData;
    private IAppError appErrorData;
    private IHttpData httpData;

    private DataAgent() {
    }

    @Override
    public List<ExceptionBean> getExceptionLists() {
        return appErrorData.getExceptionLists();
    }

    @Override
    public void delAllException() {
        appErrorData.delAllException();
    }

    private static class InstanceHolder {
        public static final DataAgent INSTANCE = new DataAgent();
    }

    public static DataAgent getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void init(){
        featureData = new FeatureData();
        calculateData = new CalculateData();
        performanceData = new PerformanceData();
        appErrorData = new AppErrorData();
        httpData = new HttpData();
    }

    @Override
    public String getLongitude() {
        return calculateData.getLongitude();
    }

    @Override
    public String getLatitude() {
        return calculateData.getLatitude();
    }

    @Override
    public String getAppName() {
        return featureData.getAppName();
    }

    @Override
    public String getAppVersion() {
        return featureData.getAppVersion();
    }

    @Override
    public String getPhoneBrand() {
        return featureData.getPhoneBrand();
    }

    @Override
    public String getPhoneName() {
        return featureData.getPhoneName();
    }

    @Override
    public String getPhoneModel() {
        return featureData.getPhoneModel();
    }

    @Override
    public String getOSType() {
        return featureData.getOSType();
    }

    @Override
    public String getOSVersion() {
        return featureData.getOSVersion();
    }

    @Override
    public String getCusOsType() {
        return featureData.getCusOsType();
    }

    @Override
    public String getCusOsVersion() {
        return featureData.getCusOsVersion();
    }

    @Override
    public String getAgentType() {
        return featureData.getAgentType();
    }

    @Override
    public String getAgentVersion() {
        return featureData.getAgentVersion();
    }

    @Override
    public String getIMEI() {
        return featureData.getIMEI();
    }

    @Override
    public String getIP() {
        return featureData.getIP();
    }

    @Override
    public String getDeviceId() {
        return featureData.getDeviceId();
    }

    @Override
    public String getNetworkType() {
        return featureData.getNetworkType();
    }

    @Override
    public String getOperator() {
        return featureData.getOperator();
    }

    @Override
    public List<UIDataBean> getPathList() {
        return performanceData.getPathList();
    }

    @Override
    public void clearPathList() {
        performanceData.clearPathList();
    }

    @Override
    public long getStartTime() {
        return performanceData.getStartTime();
    }

    @Override
    public long getEndTime() {
        return performanceData.getEndTime();
    }

    @Override
    public void setEndTime(long time) {
        performanceData.setEndTime(time);
    }

    @Override
    public boolean isAppBackground() {
        return performanceData.isAppBackground();
    }

    @Override
    public boolean isAppEnd() {
        return performanceData.isAppEnd();
    }

    @Override
    public void setAppEnd(boolean appEnd) {
        performanceData.setAppEnd(appEnd);
    }

    @Override
    public List<ActivityUIBean> getActivityUIBeanList() {
        return performanceData.getActivityUIBeanList();
    }

    @Override
    public void addHttpAction(HttpAction httpAction) {
        httpData.addHttpAction(httpAction);
    }

    @Override
    public void addHttpError(HttpError httpError) {
        httpData.addHttpError(httpError);
    }

    @Override
    public List<HttpAction> getHttpActions() {
        return httpData.getHttpActions();
    }

    @Override
    public List<HttpError> getHttpError() {
        return httpData.getHttpError();
    }

    @Override
    public void clearHttpActions() {
        httpData.clearHttpActions();
    }

    @Override
    public void clearHttpError() {
        httpData.clearHttpError();
    }

    @Override
    public String getStartTime(String url) {
        return httpData.getStartTime(url);
    }

    @Override
    public void addStartTime(String url) {
        httpData.addStartTime(url);
    }

    @Override
    public List<HttpFull> getFullHttps() {
        return httpData.getFullHttps();
    }

    @Override
    public void addFullHttp(HttpFull httpFull) {
        httpData.addFullHttp(httpFull);
    }
}
