package com.mingjiang.android.equipmonitor.entity;

import com.google.gson.Gson;

/**
 * Created by wdongjia on 2016/9/2.
 */
public class WeldmentValue {

    public String date;
    public String time;
    public String number;
    public String height;
    public String time1;
    public String energy;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
