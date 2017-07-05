package com.transfar.smarttda.tool;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by wulei
 * Data: 2016/12/1.
 */

public class StringUtils {
    /**
     *
     * @param tr
     * @return
     */
    public static String getExceptionString(Throwable tr){
        StringWriter sw=new   StringWriter();
        tr.printStackTrace(new PrintWriter(sw,true));
        String errStr=sw.toString();
        StringBuffer sb = new StringBuffer(errStr);
        return sb.toString();
    }

}
