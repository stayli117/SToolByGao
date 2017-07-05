package com.transfar.smarttda.warehouse.performance;

import com.transfar.smarttda.bean.HttpAction;
import com.transfar.smarttda.bean.HttpError;
import com.transfar.smarttda.bean.HttpFull;
import com.transfar.smarttda.tool.TimeUtils;
import com.transfar.smarttda.warehouse.idata.IHttpData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stayli on 2017/3/30.
 */

public class HttpData implements IHttpData {
    List<HttpAction> httpActionsList;
    List<HttpError> httpErrorsList;
    List<HttpFull> httpFullsList;
    Map<String, String> urlMaps;

    public HttpData(){
        httpActionsList = new ArrayList<>();
        httpErrorsList = new ArrayList<>();
        httpFullsList = new ArrayList<>();
        urlMaps = new HashMap<>();
    }

    @Override
    public void addHttpAction(HttpAction httpAction) {
        httpActionsList.add(httpAction);
        //只存最近10条
        if (httpActionsList.size() > 10){
            int index = httpActionsList.size() - 10;
            for (int i=0; i < index; i++){
                httpActionsList.remove(i);
            }
        }
    }

    @Override
    public void addHttpError(HttpError httpError) {
        httpErrorsList.add(httpError);
        //只存最近10条
        if (httpErrorsList.size() > 10){
            int index = httpErrorsList.size() - 10;
            for (int i=0; i < index; i++){
                httpErrorsList.remove(i);
            }
        }
    }

    @Override
    public List<HttpAction> getHttpActions() {
        return httpActionsList;
    }

    @Override
    public List<HttpError> getHttpError() {
        return httpErrorsList;
    }

    @Override
    public void clearHttpActions() {
        httpActionsList.clear();
    }

    @Override
    public void clearHttpError() {
        httpErrorsList.clear();
    }

    @Override
    public String getStartTime(String url) {
        return urlMaps.get(url);
    }

    @Override
    public void addStartTime(String url) {
        urlMaps.put(url, TimeUtils.getCurrentTimeStr());
    }

    @Override
    public List<HttpFull> getFullHttps() {
        return httpFullsList;
    }

    @Override
    public void addFullHttp(HttpFull httpFull) {
        httpFullsList.add(httpFull);
    }
}
