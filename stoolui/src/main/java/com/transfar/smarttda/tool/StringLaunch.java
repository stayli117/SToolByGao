package com.transfar.smarttda.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.transfar.smarttda.core.LogTDA;
import com.transfar.smarttda.localcache.file.impl.FileEngin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wulei
 * Data: 2016/11/9.
 *
 * 拼接生成规定格式的字符串
 */

public class StringLaunch {
    //每行log msg的最大宽度
    public static final int LOG_WIDTH=120;
    //log内容缩进距离
    public static final int LOG_CONTENT_PADDING_LEFT=50;
    /**
     * 从log信息拼接String
     * @param level     日志等级
     * @param tag       日志TAG
     * @param msg       日志消息
     * @return
     */
    public static String getStringFromInfo(String level,String tag,String msg){
        String date=TimeUtils.getThreadLocalCurrentTimeStr();
        StringBuilder head=new StringBuilder();
        head.append("["+date+"]["+level+"]["+tag+"]");
        if (head.toString().length()<=LOG_CONTENT_PADDING_LEFT){
            head.append(getBlankByLength(LOG_CONTENT_PADDING_LEFT-head.toString().length(),' '));
        }
        head.append(msg);
        if (head.toString().length()<=(LOG_WIDTH+LOG_CONTENT_PADDING_LEFT))
            return delN(head.toString());
        else
            return head.toString();
    }


    /**
     * 打印欢迎信息
     * @param prewidth  打印宽度
     * @param context   app上下文
     */
    public static void printWelcome(int prewidth, Context context) {
        int width=prewidth;
        FileEngin fileEngin=new FileEngin();
        StringBuilder stringBuilder=new StringBuilder();
        String temp=null;
        //head
        for (int i=0;i<width;i++){
            stringBuilder.append("*");
        }
        temp=stringBuilder.toString();
        LogTDA.SDKinfo("StringLaunch", temp);
        //输出空行
        stringBuilder.delete(0,stringBuilder.length());
        for (int i=0;i<width;i++){
            if (i==0||i==width-1)
                stringBuilder.append("*");
            else
                stringBuilder.append(" ");
        }
        temp=stringBuilder.toString();
        LogTDA.SDKinfo("StringLaunch", temp);
        stringBuilder.delete(0,stringBuilder.length());
        String title="DataAnalyze Start";
        for (int i=0;i<width;i++){
            if (i==0||i==width-1)
                stringBuilder.append("*");
            else if (i==(width-title.length())/2) {
                stringBuilder.append(title);
                i+=title.length()-1;
            }
            else
                stringBuilder.append(" ");
        }
        temp=stringBuilder.toString();
        LogTDA.SDKinfo("StringLaunch", temp);
        stringBuilder.delete(0,stringBuilder.length());
        try {
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            String version = packInfo.versionName;
            String ver="Version:"+version;
            for (int i=0;i<width;i++){
                if (i==0||i==width-1)
                    stringBuilder.append("*");
                else if (i==(width-ver.length())/2) {
                    stringBuilder.append(ver);
                    i+=ver.length()-1;
                }
                else
                    stringBuilder.append(" ");
            }
            temp=stringBuilder.toString();
            LogTDA.SDKinfo("StringLaunch", temp);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        stringBuilder.delete(0,stringBuilder.length());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date=sdf.format(new Date());
        String datestr="Time:"+date;
        for (int i=0;i<width;i++){
            if (i==0||i==width-1)
                stringBuilder.append("*");
            else if (i==(width-datestr.length())/2) {
                stringBuilder.append(datestr);
                i+=datestr.length()-1;
            }
            else
                stringBuilder.append(" ");
        }
        temp=stringBuilder.toString();
        LogTDA.SDKinfo("StringLaunch", temp);
        stringBuilder.delete(0,stringBuilder.length());
        //输出空行
        for (int i=0;i<width;i++){
            if (i==0||i==width-1)
                stringBuilder.append("*");
            else
                stringBuilder.append(" ");
        }
        temp=stringBuilder.toString();
        LogTDA.SDKinfo("StringLaunch", temp);
        stringBuilder.delete(0,stringBuilder.length());
        //end
        for (int i=0;i<width;i++){
            stringBuilder.append("*");
        }
        temp=stringBuilder.toString();
        LogTDA.SDKinfo("StringLaunch", temp);
        stringBuilder.delete(0,stringBuilder.length());
    }

    /**
     * 获取添加换行后的字符串
     * @param inputString   源字符串
     * @param length        每行长度
     * @return
     */
    public static String getFormatedString(String inputString, int length){
        inputString=delN(inputString);
        StringBuilder stringBuilder=new StringBuilder();
            if (isJson(inputString)){//若是json格式化输出
                stringBuilder.append(formatJson(inputString));
            }else if (isEndWithJson(inputString)) {
                String jsonstr=inputString.substring(inputString.indexOf("{"));
                stringBuilder.append(inputString.substring(0,inputString.indexOf("{")));
                stringBuilder.append(formatJson(jsonstr));
            }else if(inputString.startsWith("*")&&inputString.endsWith("*")) {
                stringBuilder.append(inputString);
            }else {
                if (inputString.length()>length){
                    stringBuilder.append(inputString.substring(0,length)+"\r\n");
                    inputString=inputString.substring(length);
                    while (inputString.length()>length){
                        stringBuilder.append(getBlankByLength(LOG_CONTENT_PADDING_LEFT,' ')+inputString.substring(0,length)+"\r\n");
                        inputString=inputString.substring(length);
                    }
                    if (inputString.length()>0)
                        stringBuilder.append(getBlankByLength(LOG_CONTENT_PADDING_LEFT,' ')+inputString);
                }else {
                    stringBuilder.append(inputString);
                }
            }
        return stringBuilder.toString();
    }
    /**
     * 获取格式化后的错误信息字符串
     * @param inputString   源字符串
     * @return
     */
    public static String getFormatedErrString(String inputString){
        inputString=inputString.replaceAll("\\r","");
        inputString=inputString.replaceAll("\\n","\r\n"+getBlankByLength(LOG_CONTENT_PADDING_LEFT,' '));
        return inputString;
//        return inputString.replaceAll("\\r\\n|\\n",getBlankByLength(LOG_CONTENT_PADDING_LEFT,' ')+"\n");
    }

    /**
     * 去掉\n换行符,和首尾空格
     * @param src       源字符串
     * @return          目标字符串
     */
    public static String delN(String src){
        return src.replaceAll("\r|\n", "").trim();
    }
    /**
     * 生成指定长度填充字符串
     * @param len       长度
     * @param c         填充字符
     * @return          填充后的字符串
     */
    public static String getBlankByLength(int len,char c){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<len;i++)
            stringBuilder.append(c);
        return stringBuilder.toString();
    }

    /**
     * 判断字符串是否为Json
     * @param src           源字符串
     * @return              检测结果
     */
    public static boolean isJson(String src){
        boolean flag=false;
        try {
            new JsonParser().parse(src);
            if (src.startsWith("{")&&src.endsWith("}"))
                flag=true;
        } catch (JsonParseException e) {
            flag=false;
        }
        return flag;
    }

    /**
     * 判断字符串是否以json结尾
     * @param src       源字符串
     * @return          检测结果
     */
    public static boolean isEndWithJson(String src){
        boolean flag=false;
        if (src.endsWith("}")&&src.contains("{")){
            String temp=src.substring(src.indexOf("{"));
            if (isJson(temp)){
                flag=true;
            }
        }
        return flag;
    }
    /**
     * 得到格式化json数据  退格用\t 换行用\r
     * @param jsonStr       json串
     * @return              格式化后的json
     */
    public static String formatJson(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        jsonForMatStr.append("\r\n");
        jsonForMatStr.append(getBlankByLength(LOG_CONTENT_PADDING_LEFT,' '));
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\r\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c+"\r\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\r\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        jsonForMatStr.append("\r\n");
        return jsonForMatStr.toString();
    }

    private static String getLevelStr(int level){
        StringBuffer levelStr = new StringBuffer();
        levelStr.append(getBlankByLength(LOG_CONTENT_PADDING_LEFT,' '));
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

}
