package net.people.stoolui.tool;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Title: StringFormatUtils <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2017 <br>
 * Created DateTime: 2017-1-4 16:07
 * Created by Wentao.Shi.
 */
public class StringFormatUtils {

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
                    stringBuilder.append(inputString.substring(0,length)+"\r\n");
                    inputString=inputString.substring(length);
                }
                if (inputString.length()>0)
                    stringBuilder.append(inputString);
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
        inputString=inputString.replaceAll("\\n","\r\n");
        return inputString;
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
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
