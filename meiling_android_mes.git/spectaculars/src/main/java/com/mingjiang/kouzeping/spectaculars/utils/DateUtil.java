package com.mingjiang.kouzeping.spectaculars.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wdongjia on 2016/9/14.
 */
public class DateUtil {
    protected final static String TAG = DateUtil.class.getSimpleName();

    public static void main(String[] args) {

        int currentMaxDays = getCurrentMonthDay();

        int maxDaysByDate = getDaysByYearMonth(2012, 11);

        String week = getDayOfWeekByDate("2012-12-25");

        System.out.println("本月天数：" + currentMaxDays);
        System.out.println("2012年11月天数：" + maxDaysByDate);
        System.out.println("2012-12-25是：" + week);
    }

    /**
     * 获取当月的 天数
     * */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;

        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }

    /**
     * 得到本月的第一天
     * @return
     */
    public static String getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
        String a2=dateformat.format(calendar.getTime());
        Log.i(TAG,"getMonthFirstDay:"+a2);
        return a2;
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static String getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
        String a2=dateformat.format(calendar.getTime());
        Log.i(TAG,"getMonthLastDay:"+a2);
        return a2;
    }


}
