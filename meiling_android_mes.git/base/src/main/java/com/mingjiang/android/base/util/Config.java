package com.mingjiang.android.base.util;

import android.content.Context;

/**
 * 备注：配置文件。
 * 作者：wangzs on 2016/2/20 10:43
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class Config {

    public final static String BAUD_1 = "9600";
    public final static String BAUD_2 = "9600";
    public final static String BAUD_3 = "9600";
    public final static String BAUD_4 = "9600";
    public final static int PAPER_NUMBER = 4000;
    public final static String BASE_URL = "http://mes.meiling.com";
    public final static String BASE_URL_NAME = "BASE_URL";
    public final static String BASE_BAUD1_NAME = "BASE_BAUD1";
    public final static String BASE_BAUD2_NAME = "BASE_BAUD2";
    public final static String BASE_BAUD3_NAME = "BASE_BAUD3";
    public final static String BASE_BAUD4_NAME = "BASE_BAUD4";
    public final static String PAPER_NUMBER_NAME = "PAPER_NUMBER";
    public final static String HTTP = "http://";
    private static String baseUrl = BASE_URL;

    private static String baseBaud1 = BAUD_1;
    private static String baseBaud2 = BAUD_2;
    private static String baseBaud3 = BAUD_3;
    private static String baseBaud4 = BAUD_4;
    private static int paperNum = PAPER_NUMBER;


    public static void setBaseUrl(Context context,String url){
        if(!url.startsWith(HTTP)) {
            baseUrl = HTTP + url;
        }else{
            baseUrl = url;
        }
        SharedPrefsUtil.setValue(context,BASE_URL_NAME,baseUrl);
    }
    public static String getBaseUrl(Context context){
        baseUrl = SharedPrefsUtil.getString(context,BASE_URL_NAME);
        if(null == baseUrl || "".equals(baseUrl)){
            baseUrl = BASE_URL;
        }
        return baseUrl;
    }
    public static void setBaseBaud1(Context context,String baud1){
        baseBaud1 = baud1;
        SharedPrefsUtil.setValue(context,BASE_BAUD1_NAME,baseBaud1);
    }
    public static String getBaseBaud1(Context context){
        baseBaud1 = SharedPrefsUtil.getString(context,BASE_BAUD1_NAME);
        if(null == baseBaud1 || "".equals(baseBaud1)){
            baseBaud1 = BAUD_1;
        }
        return baseBaud1;
    }
    public static void setBaseBaud2(Context context,String baud2){
        baseBaud2 = baud2;
        SharedPrefsUtil.setValue(context,BASE_BAUD2_NAME,baseBaud2);
    }
    public static String getBaseBaud2(Context context){
        baseBaud2 = SharedPrefsUtil.getString(context,BASE_BAUD2_NAME);
        if(null == baseBaud2 || "".equals(baseBaud2)){
            baseBaud2 = BAUD_2;
        }
        return baseBaud2;
    }
    public static void setBaseBaud3(Context context,String baud3){
        baseBaud3 = baud3;
        SharedPrefsUtil.setValue(context,BASE_BAUD3_NAME,baseBaud3);
    }
    public static String getBaseBaud3(Context context){
        baseBaud3 = SharedPrefsUtil.getString(context,BASE_BAUD3_NAME);
        if(null == baseBaud3 || "".equals(baseBaud3)){
            baseBaud3 = BAUD_3;
        }
        return baseBaud3;
    }
    public static void setBaseBaud4(Context context,String baud4){
        baseBaud4 = baud4;
        SharedPrefsUtil.setValue(context,BASE_BAUD4_NAME,baseBaud4);
    }
    public static String getBaseBaud4(Context context){
        baseBaud4 = SharedPrefsUtil.getString(context,BASE_BAUD4_NAME);
        if(null == baseBaud4 || "".equals(baseBaud4)){
            baseBaud4 = BAUD_4;
        }
        return baseBaud4;
    }

    public static void setPaperNum(Context context,int papernum){
        paperNum = papernum;
        SharedPrefsUtil.setValue(context,PAPER_NUMBER_NAME,paperNum);
    }

    public static int getPaperNum(Context context){
        paperNum = SharedPrefsUtil.getInt(context,PAPER_NUMBER_NAME);
        if (null == (Integer)paperNum){
            paperNum = PAPER_NUMBER;
        }
        return paperNum;
    }
}
