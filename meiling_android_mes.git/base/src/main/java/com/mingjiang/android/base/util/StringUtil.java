package com.mingjiang.android.base.util;

import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 * Created by wangdongjia on 2016/7/7.
 */
public class StringUtil {

    /**
     * 合并6个String
     * @param args0
     * @param args1
     * @param args2
     * @param args3
     * @param args4
     * @param args5
     * @return
     */
    public static String StringMerger(String args0,String args1,String args2,String args3,String args4,String args5){
        String value = args0+args1+args2+args3+args4+args5;
        return value;
    }

    /* 日期格式转换成  2016年1月12 60112
         * @return
         */
    public static String systemDate5(Date date){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");//60113
        System.out.print(df);
        String s = String.valueOf(df.format(date));
        s = s.substring(3, 8);
        return s;
        //截取后5位
    }

    /**
     * @func 格式化顺序号0000-9999
     * @param num
     * @return result
     */
    public static String formatNumberToXXXX(int num)
    {
        num++;
        String result = "";
        switch ((num+"").length()) {
            case 1:
                result = "000" + num;
                break;
            case 2:
                result = "00" + num;
                break;
            case 3:
                result = "0" + num;
                break;
            case 4:
                result = "" + num;
                break;
            //此处代表编号已经超过了9999，从0重新开始
            default:
                result = "0000";
                break;
        }
        return result;
    }

    /**
     * 获取DatePicker的值,格式是2016-07-07
     * @param datePicker
     * @return string
     */
    public static String getTheDatePicker(DatePicker datePicker){
        String strMonth;
        String strDay;
        int beginYear = datePicker.getYear();
        int beginMonth = datePicker.getMonth();
        int beginDay = datePicker.getDayOfMonth();
        beginMonth++;
        if(beginMonth<10){
            strMonth = "0"+beginMonth;
        }else {
            strMonth = beginMonth + "";
        }
        if(beginDay<10){
            strDay = "0"+beginDay;
        }else {
            strDay = beginDay + "";
        }
        String string = beginYear + "-" + strMonth + "-" + strDay;
        return string;
    }


}
