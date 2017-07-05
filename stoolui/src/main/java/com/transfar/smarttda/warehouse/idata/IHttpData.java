package com.transfar.smarttda.warehouse.idata;

import com.transfar.smarttda.bean.HttpAction;
import com.transfar.smarttda.bean.HttpError;
import com.transfar.smarttda.bean.HttpFull;

import java.util.List;

/**
 * Created by stayli on 2017/3/30.
 */

public interface IHttpData {
    void addHttpAction(HttpAction var1);

    void addHttpError(HttpError var1);

    List<HttpAction> getHttpActions();

    List<HttpError> getHttpError();

    void clearHttpActions();

    void clearHttpError();

    String getStartTime(String var1);

    void addStartTime(String var1);

    List<HttpFull> getFullHttps();

    void addFullHttp(HttpFull var1);
}
