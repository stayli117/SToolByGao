package com.transfar.smarttda.aop;

import com.transfar.smarttda.bean.HttpAction;
import com.transfar.smarttda.bean.HttpError;
import com.transfar.smarttda.bean.HttpFull;
import com.transfar.smarttda.core.DataAgent;
import com.transfar.smarttda.core.LogTDA;
import com.transfar.smarttda.tool.StringUtils;
import com.transfar.smarttda.tool.TimeUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.NoAspectBoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;

import static com.transfar.smarttda.tool.TimeUtils.getThreadLocalCurrentTimeStr;

/**
 * Created by stayli on 2017/3/30.
 */


@Aspect
public class HttpAspectJ {
    private static final String TAG = "HttpAspectJ";
    private DataAgent dataAgent = DataAgent.getInstance();
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private HttpAspectJ() {
    }

    /**
     * okhttp3 异步网络请求 过滤SDK中请求
     */
    @Pointcut(value = "execution(* *..Callback+.onResponse(okhttp3.Call, okhttp3.Response)) && " +
            "args(call, response) && !within(com.transfar.tda..*)", argNames = "call, response")
    public void okHttp3_onResponse(Call call, Response response) {
    }


    @Before(value = "okHttp3_onResponse(call, response)", argNames = "call, response")
    public void onOkHttp3OnResponse(Call call, Response response) {
        try {
            String url = call.request().url().toString();
            String startTime = dataAgent.getStartTime(url);
            String endTime = TimeUtils.getCurrentTimeStr();

            buildHttpAction(response, startTime, endTime);

            buildHttpFull(response, startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * okhttp3 异步请求异常 过滤SDK中请求
     *
     * @param call
     * @param e
     */
    @Pointcut(value = "execution(* *..Callback+.onFailure(okhttp3.Call, java.io.IOException)) && args(call, e) && !within(com.transfar.tda..*)", argNames = "call, e")
    public void okHttp3_onFailure(Call call, IOException e) {
    }

    ;

    @Before(value = "okHttp3_onFailure(call, e)", argNames = "call, e")
    public void onOkHttp3OnFailure(Call call, IOException e) {
        try {
            String url = call.request().url().toString();
            String startTime = dataAgent.getStartTime(url);
            String des = StringUtils.getExceptionString(e);

            buildHttpErr(url, startTime, des);

            buildHttpFullErr(url, call.request(), startTime, des);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * okhttp3 异步入口函数 过滤SDK中请求
     */
    @Pointcut("call(* okhttp3.Call+.enqueue(..)) && !within(com.transfar.tda..*)")
    public void okHttp3_enter() {

    }


    @Before(value = "okHttp3_enter()", argNames = "joinPoint")
    public void onOkHttp3Enter(JoinPoint joinPoint) {

        LogTDA.SDKinfo(TAG, "------------------ okhttp3 start request  ------------------");
        if (joinPoint.getTarget() instanceof Call) {
            Call call = (Call) joinPoint.getTarget();
            dataAgent.addStartTime(call.request().url().toString());
            LogTDA.SDKinfo(TAG, "request Url: " + call.request().url());
        }
    }

    /**
     * okhttp3 同步步入口函数 过滤SDK中请求
     */
    @Pointcut("call(* okhttp3.Call+.execute()) && !within(com.transfar.tda..*)")
    public void okHttp3_sync() {
    }

    ;

    @Around(value = "okHttp3_sync()", argNames = "joinPoint")
    public Object onOkHttp3Sync(ProceedingJoinPoint joinPoint) {
        LogTDA.SDKinfo(TAG, "------------------ okhttp3 start request sync  ------------------");
        Call call = (Call) joinPoint.getTarget();
        LogTDA.SDKinfo(TAG, "request Url: " + call.request().url());

        try {
            String startTime = TimeUtils.getCurrentTimeStr();
            Response response = (Response) joinPoint.proceed();
            String endTime = TimeUtils.getCurrentTimeStr();

            buildHttpAction(response, startTime, endTime);
            buildHttpFull(response, startTime, endTime);

            return response;

        } catch (Throwable throwable) {
            throwable.printStackTrace();

            String url = call.request().url().toString();
            String startTime = dataAgent.getStartTime(url);
            String des = StringUtils.getExceptionString(throwable);

            buildHttpErr(url, startTime, des);
            buildHttpFullErr(url, call.request(), startTime, des);
        }

        return null;
    }

    //===========================================  retrofit ===============================================================================
    @Pointcut("call(* retrofit2.Retrofit.Builder+.build())")
    public void retrofit_build() {
    }

    @Before(value = "retrofit_build()", argNames = "joinPoint")
    public void retrofitBuild(JoinPoint joinPoint) {
        LogTDA.SDKinfo(TAG, "------------------ retrofit build  ------------------");

        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new RetrofitInterceptor())
                    .build();
            Retrofit.Builder builder = (Retrofit.Builder) joinPoint.getTarget();
            Class c = builder.getClass();
            Field field = c.getDeclaredField("callFactory");
            field.setAccessible(true);
            if (field.get(builder) == null) {
                field.set(builder, client);
            } else {
                OkHttpClient clientObject = (OkHttpClient) field.get(builder);
                OkHttpClient okHttpClient2 = clientObject.newBuilder().addInterceptor(new RetrofitInterceptor()).build();
                field.set(builder, okHttpClient2);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    //===========================================  非AOP  ==================================================================================
    private void buildHttpAction(Response response, String startTime, String endTime) {
        String url = response.request().url().toString();
        String protocolType = response.protocol().name();
        String requestType = response.request().method();
        String contentLengrth = "";
        if (response.body() != null) {
            contentLengrth = String.valueOf(response.body().contentLength());
        }
        String code = String.valueOf(response.code());

        LogTDA.SDKinfo(TAG, "------------------ okhttp3 onResponse  ------------------");
        LogTDA.SDKinfo(TAG, "url: " + url);
        LogTDA.SDKinfo(TAG, "startTime: " + startTime);
        LogTDA.SDKinfo(TAG, "endTime: " + endTime);
        LogTDA.SDKinfo(TAG, "protocolType: " + protocolType);
        LogTDA.SDKinfo(TAG, "requestType: " + requestType);
        LogTDA.SDKinfo(TAG, "contentLengrth: " + contentLengrth);
        LogTDA.SDKinfo(TAG, "code: " + code);

//            LogTDA.SDKinfo(TAG, "sync: " + response.body().string());
        HttpAction action = new HttpAction();
        action.setName(url);
        action.setStartTime(startTime);
        action.setEndTime(endTime);
        action.setProtocolType(protocolType);
        action.setRequestType(requestType);
        action.setContentLength(contentLengrth);
        action.setCode(code);
        dataAgent.addHttpAction(action);
    }

    private void buildHttpErr(String url, String startTime, String des) {
        LogTDA.SDKinfo(TAG, "------------------ okhttp3 onFailure  ------------------");
        LogTDA.SDKinfo(TAG, "name: " + url);
        LogTDA.SDKinfo(TAG, "startTime: " + startTime);
        LogTDA.SDKinfo(TAG, "des: " + des);

        HttpError error = new HttpError();
        error.setName(url);
        error.setStartTime(startTime);
        error.setDes(des);
        dataAgent.addHttpError(error);
    }

    private void buildHttpFullErr(String url, Request request, String startTime, String des) {
        HttpFull httpFull = new HttpFull();
        httpFull.setName(url);
        httpFull.setStartTime(startTime);
        httpFull.setEndTime(getThreadLocalCurrentTimeStr());
        httpFull.setErrDes(des);
        httpFull.setRequest(request);

        dataAgent.addFullHttp(httpFull);
    }

    private void buildHttpFull(Response response, String startTime, String endTime) {
        String url = response.request().url().toString();
        String protocolType = response.protocol().name();
        String requestType = response.request().method();
        String contentLengrth = String.valueOf(response.body().contentLength());
        String code = String.valueOf(response.code());

        HttpFull httpFull = new HttpFull();
        httpFull.setName(url);
        httpFull.setStartTime(startTime);
        httpFull.setEndTime(getThreadLocalCurrentTimeStr());
        httpFull.setProtocolType(protocolType);
        httpFull.setRequestType(requestType);
        httpFull.setContentLength(contentLengrth);
        httpFull.setCode(code);
        httpFull.setContent(getHttpContent(response.body()));
        httpFull.setRequest(response.request());

        dataAgent.addFullHttp(httpFull);
    }

    /**
     * 获取okhttp3请求的体部信息
     *
     * @param body
     * @return
     */
    private String getHttpContent(ResponseBody body) {
        BufferedSource source = body.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            return buffer.clone().readString(UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Throwable initFailureCause;
    private static HttpAspectJ Instance;

    static {
        try {
            postClinit();
        } catch (Throwable var1) {
            initFailureCause = var1;
        }
    }

    public static HttpAspectJ aspectOf() {
        if (Instance == null) {
            throw new NoAspectBoundException("com.transfar.smarttda.aop.HttpAspectJ", initFailureCause);
        } else {
            if (hasAspect()) {
                return Instance;
            } else {
                postClinit();
                return Instance;
            }
        }
    }

    private static boolean hasAspect() {
        return Instance != null;
    }

    private static synchronized void postClinit() {
        Instance = new HttpAspectJ();
    }

    public class RetrofitInterceptor implements Interceptor {
        private static final String TAG = "RetrofitInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException {
            LogTDA.SDKinfo(TAG, "-------------------- RetrofitInterceptor start --------------------------");
            String startTime = TimeUtils.getCurrentTimeStr();
            Request request = chain.request();
            try {
                Response response = chain.proceed(request);
                String endTime = TimeUtils.getCurrentTimeStr();

                buildHttpFull(response, startTime, endTime);

                return response;
            } catch (Exception e) {
                e.printStackTrace();

                String des = StringUtils.getExceptionString(e);
                String url = request.url().toString();
                buildHttpFullErr(url, request, startTime, des);
            }
            return null;
        }
    }

}
