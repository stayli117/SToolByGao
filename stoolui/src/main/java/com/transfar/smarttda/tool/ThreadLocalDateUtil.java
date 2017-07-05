package com.transfar.smarttda.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title: ThreadLocalDateUtil  SimpleDateFormat安全的时间格式化<br>
 * Description: 使用ThreadLocal, 也是将共享变量变为独享，线程独享肯定能比方法独享在并发环境中能减少不少创建对象的开销。如果对性能要求比较高的情况下，一般推荐使用这种方法<br>
 *              此类用于解决短时间内多次调用DateFormat引发的进程不安全，导致输出时间字符串错乱的问题。
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/11/25 9:12
 * Created by Wentao.Shi.
 */
public class ThreadLocalDateUtil {
    public static final String  DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public static DateFormat getDateFormat()
    {
        DateFormat df = threadLocal.get();
        if(df==null){
            df = new SimpleDateFormat(DATE_FORMAT);
            threadLocal.set(df);
        }
        return df;
    }

    public static String formatDate(Date date) throws ParseException {
        return getDateFormat().format(date);
    }
    public static String formatDate(long time) throws ParseException {
        Date date = new Date(time);
        return getDateFormat().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }

}
