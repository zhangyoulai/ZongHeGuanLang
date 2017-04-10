package com.mingjiang.android.equipmonitor.entity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wdongjia on 2016/9/2.
 * date: 日期
 time: 当前时间
 number: 计数器
 height:焊接厚度
 time1：焊接持续时间
 Energy:能量
 */
public class WeldmentQuery {

    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NUMBER = "number";
    public static final String HEIGHT = "height";
    public static final String TIME1 = "time1";
    public static final String ENERGY = "energy";

    public String date;
    public String time;
    public String number;
    public String height;
    public String time1;
    public String energy;

    public WeldmentQuery(){

    }

    //将实体类数据转换为Map数据。
    public Map<String,String> setData(){
        Map<String,String> retMap = new HashMap<String,String>();
        retMap.put(DATE,this.date);
        retMap.put(TIME,this.time);
        retMap.put(NUMBER,this.number);
        retMap.put(HEIGHT,this.height);
        retMap.put(TIME1,this.time1);
        retMap.put(ENERGY,this.energy);
        return retMap;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
