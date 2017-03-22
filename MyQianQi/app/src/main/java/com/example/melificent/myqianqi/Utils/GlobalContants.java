package com.example.melificent.myqianqi.Utils;

/**
 * Created by p on 2017/3/14.
 */

public class GlobalContants {
    public static final String HeadsSet = "Content-type:application/x-www-urlencoded;charset=UTF-8";
    public static final String getWeatherInfobyAreaIDURL = "http://v.juhe.cn/xiangji_weather/real_time_weather.php";
    public static final String getVehicleViolationInfoBaseURL = "http://v.juhe.cn/wzdj/querywz.php";
    public static  Boolean NoVehicleViolationBehavior = false;
    public static final String getTrainInfoByTrainNameBaseURL = "http://apis.juhe.cn/train/s";
    public static final String getTrainInfoByStationBaseURL = "http://apis.juhe.cn/train/s2swithprice";
    public static final String getAirPlaneInfoByNameBaseURL = "http://apis.juhe.cn/plan/snew";
    public static final String getAirPlaneInfoByStationBaseURL = "http://apis.juhe.cn/plan/bc";
    public static final String getExpressInfoBaseURL = "http://v.juhe.cn/exp/index";
    public static final String getAirQualityBaseURL = "http://v.juhe.cn/xiangji_weather/live_air.php";
    public static final String realNameRegexBaseURL="http://op.juhe.cn/idcard/query";
    public static boolean FirstLoadWebpage = true;
    public static boolean FirstClick = true;
    public static final String getStrockIndexBaseURL = "http://web.juhe.cn:8080/finance/stock/hs";
    public static final String getEveryHourWeatherBaseURL= "http://v.juhe.cn/xiangji_weather/weather_byHour_areaid.php";

}
