package com.mingjiang.android.app.bean;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */
public class WeldmentBean {
    public String date;
    public String time;
    public String number;
    public String height;
    public String time1;
    public String energy;

    public WeldmentBean() {
    }

    public WeldmentBean(String date, String time,String number,
                        String height,String time1,String energy) {
        this.date = date;
        this.time = time;
        this.number = number;
        this.height = height;
        this.time1 = time1;
        this.energy = energy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
